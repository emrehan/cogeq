###Find cos-sim and rating estimations
import numpy
from scipy import spatial
import operator
import json

prePath = "/home/remzican/Documents/checkins/"
cityName = "London/"
expertListFileName = "experts"
fileSuffix = ".csv"
categoriesFileName = "categories"
delimiter = ","

categoriesFilePath = prePath + categoriesFileName + fileSuffix
expertsFilePath = prePath + cityName + expertListFileName + fileSuffix
expertCheckinsPrefix = prePath + cityName

#Create TF-IDF vector of the user
tfDictionary = {}

with open(prePath + 'response.json') as data_file:
    data = json.load(data_file)
    count = data["response"]["response"]["checkins"]["count"]
    items = data["response"]["response"]["checkins"]["items"]

    for item in items:
        categories = item["venue"]["categories"]
        for category in categories:
            categoryName = category["name"]
            if categoryName in tfDictionary:
                tfDictionary[categoryName] += 1
            else:
                tfDictionary[categoryName] = 1

    for key, value in tfDictionary.items():
        tfDictionary[key] = float(value) / count

categoriesFile = open(categoriesFilePath, "r")
lines = categoriesFile.readlines()
userCategoryTFIDF = []

for line in lines:
    tokens = line.split(delimiter)
    categoryName = tokens[0]

    if categoryName in tfDictionary:
        categoryTF = tfDictionary[categoryName]
    else:
        categoryTF = 0

    categoryCheckinCount = int(tokens[1])
    categoryIDF = 1.0/categoryCheckinCount
    userCategoryTFIDF.append(categoryTF*categoryIDF)

#Iterate through experts to find similarities
expertsFile = open(expertsFilePath, "r")
lines = expertsFile.readlines()
expertCosineSimilarities = {}

for line in lines:
    tokens = line.split(delimiter)
    #TODO after Emrehan's change
    expertCategoryTFIDF = tokens[1:]
    expertCategoryTFIDF = [int(numeric_string) for numeric_string in expertCategoryTFIDF]
    cosineSimilarity = 1 - spatial.distance.cosine(userCategoryTFIDF, expertCategoryTFIDF)
    expertCosineSimilarities[tokens[0]] = cosineSimilarity

sortedExpertCosineSimilarities = sorted(expertCosineSimilarities.items(), key=operator.itemgetter(1))
sortedExpertCosineSimilarities.reverse()
estimatedRankings = {}

#TODO decision need?
selectionRatio = 1
numberOfExperts = len(sortedExpertCosineSimilarities)
numberOfSelectedExperts = max(1,int(numberOfExperts/selectionRatio))

#Find estimated rankings
for expert, similarity in sortedExpertCosineSimilarities[:numberOfSelectedExperts]:
    expertCheckinsPath = expertCheckinsPrefix + expert + fileSuffix
    checkinsFile = open(expertCheckinsPath, "r")
    lines = checkinsFile.readlines()

    for line in lines:
        tokens = line.split(delimiter)
        venueId = tokens[0]
        venueCheckinCount = float(tokens[1])
        estimatedRanking = similarity * venueCheckinCount

        #TODO Need decision?
        if venueId in estimatedRankings:
            estimatedRankings[venueId] += estimatedRanking
        else:
            estimatedRankings[venueId] = estimatedRanking

sortedEstimatedRankings = sorted(estimatedRankings.items(), key=operator.itemgetter(1))
sortedEstimatedRankings.reverse()

print(sortedEstimatedRankings)