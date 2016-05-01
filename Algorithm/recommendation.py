###Find cos-sim and rating estimations
import numpy
from scipy import spatial
import operator
import json

prePath = "/home/remzican/Documents/checkins/"
cityName = "London/"
checkinsFolderName = "expert_checkins/"
expertsCategoryTFFileName = "expert_categories.csv"
expertIDsFileName = "expert_ids.txt"
categoriesFileName = "categories.csv"
delimiter = ","

categoriesFilePath = prePath + categoriesFileName
expertsFilePath = prePath + cityName + expertsCategoryTFFileName
expertIDsFilePath = prePath + cityName + expertIDsFileName
expertCheckinsPrefix = prePath + cityName + checkinsFolderName

#Create TF-IDF vector of the user
userCategoryTFDictionary = {}

with open(prePath + 'response.json') as data_file:
    data = json.load(data_file)
    count = data["response"]["response"]["checkins"]["count"]
    items = data["response"]["response"]["checkins"]["items"]

    for item in items:
        categories = item["venue"]["categories"]
        for category in categories:
            categoryName = category["name"]
            if categoryName in userCategoryTFDictionary:
                userCategoryTFDictionary[categoryName] += 1
            else:
                userCategoryTFDictionary[categoryName] = 1

    for key, value in userCategoryTFDictionary.items():
        userCategoryTFDictionary[key] = float(value) / count

categoriesFile = open(categoriesFilePath, "r")
categoryLines = categoriesFile.readlines()
userCategoryTFIDFs = []

for line in categoryLines:
    tokens = line.split(delimiter)
    categoryName = tokens[0]

    if categoryName in userCategoryTFDictionary:
        categoryTF = userCategoryTFDictionary[categoryName]
    else:
        categoryTF = 0

    categoryCheckinCount = int(tokens[1])
    categoryIDF = 1.0/categoryCheckinCount
    userCategoryTFIDFs.append(categoryTF * categoryIDF)

#Iterate through experts to find similarities
expertsFile = open(expertsFilePath, "r")
expertIDsFile = open(expertIDsFilePath, "r")
expertsFileLines = expertsFile.readlines()
IDs = expertIDsFile.readlines()
expertCosineSimilarities = {}

for index, line in enumerate(expertsFileLines):
    expertCategoryTFs = line.split(delimiter)
    categoryCheckinCount = float(categoryLines[index].split(delimiter)[1])
    expertCategoryTFIDFs = [float(numeric_string)/categoryCheckinCount for numeric_string in expertCategoryTFs]
    cosineSimilarity = 1 - spatial.distance.cosine(userCategoryTFIDFs, expertCategoryTFIDFs)
    if cosineSimilarity > 0:
        expertID = IDs[index].split('\n')[0]
        expertCosineSimilarities[expertID] = cosineSimilarity

estimatedRankings = {}
#Find estimated rankings
for expert, similarity in expertCosineSimilarities.items():
    expertCheckinsPath = expertCheckinsPrefix + expert + ".csv"
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


print(list(map(lambda e: e[0], sortedEstimatedRankings)))
