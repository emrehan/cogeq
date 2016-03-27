from flask import Flask, request, jsonify
import requests 
import json
app = Flask(__name__)


@app.route("/", methods=['GET'])
def hello():
    return "COGEQ!"

@app.route("/cities", methods=['GET'])
def search_cities():
    try:
        query = request.args.get('query')
        r = requests.get('https://maps.googleapis.com/maps/api/place/autocomplete/json?input=' + query + '&types=(cities)&key=AIzaSyDbpTvwo005xWG0rGdvaFLCLX-GE-phsQA')
        
        if (r.status_code == 200):     
            cities = list( map( lambda x: x['description'], r.json()['predictions'] ) )
            return jsonify( {'cities': cities} )
        else:
            return jsonify( {'Error': 'Error while getting Google Place API response'} )
    except:
        default_cities = ['Istanbul, Turkey', 'London, United Kingdom', 'Izmir, Turkey', 'Singapore, Singapore', 'NYC, United States']
        return jsonify( {'cities': default_cities} )
        
if __name__ == "__main__":
    app.run()
