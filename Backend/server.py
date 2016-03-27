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
    query = request.args.get('query')

    if query:
        r = requests.get('https://maps.googleapis.com/maps/api/place/autocomplete/json?types=(cities)&key=' + config['keys']['google_places'] + '&input=' + query )        
        if (r.status_code == 200):     
            cities = list( map( lambda x: x['description'], r.json()['predictions'] ) )
            return json.dumps( {'cities': cities}, ensure_ascii=False )
        else:
            return json.dumps( {'Error': 'Error while getting Google Place API response'}, ensure_ascii=False )
    else:
        default_cities = ['Istanbul, Turkey', 'London, United Kingdom', 'Izmir, Turkey', 'Singapore, Singapore', 'NYC, United States']
        return json.dumps( {'cities': default_cities}, ensure_ascii=False)
        
if __name__ == "__main__":
    app.debug = True
    app.run()
