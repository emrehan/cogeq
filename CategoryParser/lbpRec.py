import json
from pprint import pprint
tfDictionary = {}

with open('response.json') as data_file:
    data = json.load(data_file)
    count = data["response"]["response"]["checkins"]["count"]
    items = data["response"]["response"]["checkins"]["items"]

    for item in items:
        categories = item["venue"]["categories"]
        for category in categories:
            categoryName = category["name"]
            if tfDictionary.has_key(categoryName):
                tfDictionary[categoryName] += 1
            else:
                tfDictionary[categoryName] = 1

    for key, value in tfDictionary.iteritems():
        tfDictionary[key] = float(value) / count;

    print count
