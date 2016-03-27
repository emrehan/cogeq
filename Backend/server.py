from flask import Flask
app = Flask(__name__)

@app.route("/", methods=['GET'])
def hello():
    return "COGEQ!"

@app.route("/cities", methods=['GET'])
def search_cities():
    return "Here be an array of cities!"

if __name__ == "__main__":
    app.run()
