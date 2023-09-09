from flask import Flask, jsonify, abort
from artsyAPI import *
import json

app = Flask(__name__)


@app.route('/')
@app.route('/index')
def index():
    return app.send_static_file('client.html')


@app.route("/search_list/<string:input_name>", methods=['GET'])
def searching_artist(input_name):
    artist_list = search_endpoint(input_name)
    list_to_json = jsonify({'ten_artists': artist_list})
    return list_to_json


@app.route("/info/<string:artist_id>", methods=['GET'])
def retrieve_info(artist_id):
    artist_info = artist_detail(artist_id)
    dict_to_json = jsonify(artist_info)
    return dict_to_json


if __name__ == '__main__':
    app.run(debug=True, use_reloader=True)
