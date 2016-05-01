###Find cos-sim and rating estimations
import numpy
from scipy import spatial
import operator

prePath = "/home/remzican/Documents/checkins/"
cityName = "London/"
expertListFileName = "experts"
fileSuffix = ".csv"
delimiter = ","

expertsFilePath = prePath + cityName + expertListFileName + fileSuffix
expertCheckinsPrefix = prePath + cityName


#Turkish Restaurant,Restaurant,Museum

#TODO Find category-checkin count vector of the user
userCategoryCheckinCounts = [26, 77, 40]

#Iterate through experts
expertsFile = open(expertsFilePath, "r")
lines = expertsFile.readlines()
cosineSimilarities = {}

for line in lines:
    tokens = line.split(delimiter)
    expertCategoryCheckinCounts = tokens[1:]
    expertCategoryCheckinCounts = [int(numeric_string) for numeric_string in expertCategoryCheckinCounts]
    cosineSimilarity = 1 - spatial.distance.cosine(userCategoryCheckinCounts, expertCategoryCheckinCounts)
    cosineSimilarities[tokens[0]] = cosineSimilarity

estimatedRankings = {}
#Find estimated rankings
for expert, similarity in cosineSimilarities.items():
    expertCheckinsPath = expertCheckinsPrefix + expert + fileSuffix
    checkinsFile = open(expertCheckinsPath, "r")
    lines = checkinsFile.readlines()

    for line in lines:
        tokens = line.split(delimiter)
        venueId = tokens[0]
        venueCheckinCount = float(tokens[1])
        estimatedRanking = cosineSimilarities[expert] * venueCheckinCount

        #TODO Need decision?
        if venueId in estimatedRankings:
            estimatedRankings[venueId] = max(estimatedRankings[venueId], estimatedRanking)
        else:
            estimatedRankings[venueId] = estimatedRanking

sortedEstimatedRankings = sorted(estimatedRankings.items(), key=operator.itemgetter(1))
sortedEstimatedRankings.reverse()

print(sortedEstimatedRankings)