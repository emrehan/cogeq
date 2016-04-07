from datetime import datetime
from flask import Flask, request, jsonify
from flask.ext.pymongo import PyMongo

import bson
import json
import os
import requests 

app = Flask(__name__)
mongo = PyMongo(app)
config = json.load(open('config.json'))

@app.route("/", methods=['GET'])
def hello():
    return "COGEQ!"

@app.route("/db", methods=['GET', 'POST', 'DELETE'])
def dummy_db():
    if request.method == 'GET':
        return bson.dumps( {'travels': list( mongo.db.travels.find() )} )
    if request.method == 'POST':
        travel = {'city': 'Istanbul, Turkey'}
        travel_id = mongo.db.travels.insert_one(travel).inserted_id
        print (travel_id)
        return bson.dumps( {'id': travel_id} )
    if request.method == 'DELETE':
        r = mongo.db.travels.delete_many({})
        return bson.dumps( {'deleted_count': r.deleted_count } )

@app.route("/cities", methods=['GET'])
def search_cities():
    query = request.args.get('query')

    if query:
        r = requests.get('https://maps.googleapis.com/maps/api/place/autocomplete/json?types=(cities)&key=' + config['keys']['google_places'] + '&input=' + query )        
        if (r.status_code == 200):     
            cities = list( map( lambda x: x['description'], r.json()['predictions'] ) )
            return bson.dumps( {'cities': cities}, ensure_ascii=False )
        else:
            return bson.dumps( {'Error': 'Error while getting Google Place API response'}, ensure_ascii=False )
    else:
        default_cities = ['Istanbul, Turkey', 'London, United Kingdom', 'Izmir, Turkey', 'Singapore, Singapore', 'NYC, United States']
        return bson.dumps( {'cities': default_cities}, ensure_ascii=False)
        
@app.route("/travels", methods=['POST'])
def create_travel():
    timeFormat = "%Y-%m-%dT%H:%M:%S";
    try:
        city = request.args.get('city')
        ffrom = datetime.strptime(request.args.get('from'), timeFormat)
        to = datetime.strptime(request.args.get('to'), timeFormat)
        if (not city or not ffrom or not to):
            return bson.dumps( {'Error': 'city, from and to must be provided.'} )
        else:
            activities = []
            travel = { 'city': city, 'from': ffrom.strftime(timeFormat), 'to': to.strftime(timeFormat), 'activities': activities }
            travel_id = mongo.db.travels.insert_one(travel).inserted_id
            return bson.dumps( { 'travel_id': travel_id, 'from': ffrom.strftime(timeFormat), 'to': to.strftime(timeFormat), 'activities': activities } )
    except:
        return bson.dumps({'Error': 'Error occured'})

@app.route("/travels/<travel_id>", methods=['GET', 'PUT'])
def travel(travel_id):
    if request.method == 'GET':
        return bson.dumps( {'travels': list( mongo.db.travels.find({"_id": bson.ObjectId(oid=str(travel_id))}) )} )
    if request.method == 'PUT':
        timeFormat = "%Y-%m-%dT%H:%M:%S";
        city = request.args.get('city')
        ffrom = datetime.strptime(request.args.get('from'), timeFormat)
        to = datetime.strptime(request.args.get('to'), timeFormat)
        mongo.db.travels.update_one({"_id": bson.ObjectId(oid=str(travel_id))}, {"$set": {"city": city, "from": ffrom.strftime(timeFormat), "to": to.strftime(timeFormat) }})
        return bson.dumps( { 'travel_id': travel_id, 'from': ffrom.strftime(timeFormat), 'to': to.strftime(timeFormat) } )

if __name__ == "__main__":
    app.debug = True 
    port = int(os.environ.get("PORT", 5000))   
    app.run(port=port)
