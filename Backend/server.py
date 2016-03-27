from flask import Flask, request, jsonify
import requests 
import json
app = Flask(__name__)

config = json.load(open('config.json'))

@app.route("/", methods=['GET'])
def hello():
    return "COGEQ!"

@app.route("/cities", methods=['GET'])
def search_cities():
    try:
        query = request.args.get('query')
        r = requests.get('https://maps.googleapis.com/maps/api/place/autocomplete/json?types=(cities)&key=' + config['keys']['google_places'] + '&input=' + query )
        
        if (r.status_code == 200):     
            cities = list( map( lambda x: x['description'], r.json()['predictions'] ) )
            return jsonify( {'cities': cities} )
        else:
            return jsonify( {'Error': 'Error while getting Google Place API response'} )
    except KeyError:
        default_cities = ['Istanbul, Turkey', 'London, United Kingdom', 'Izmir, Turkey', 'Singapore, Singapore', 'NYC, United States']
        return jsonify( {'cities': default_cities} )
        
if __name__ == "__main__":
    app.run()
