# HITS algorithm to compute the authority vector "A" and hub vector "H"
# a general reference for HITS algorthm is
# http://www.math.cornell.edu/~mec/Winter2009/RalucaRemus/Lecture4/lecture4.html

from math import sqrt
import numpy as np
from scipy.linalg import norm
from scipy.sparse import csc_matrix
import os
import operator
import re
import json

class Node(object):
    def __init__(self, title, idf):
        self.title = title
        self.parent = None
        self.children = []
        self.idf = idf

    def add(self, child):
        self.children.append(child)
        child.parent = self

    def traverse4IDF(self, tfDictionary):
        if self.title in tfDictionary:
            self.idf *= tfDictionary[self.title]
        else:
            self.idf *= 0

        for child in self.children:
            child.traverse4IDF(tfDictionary)

    def traverse4Aggregation(self):
        sum = 0.0
        for child in self.children:
            child.traverse4Aggregation()
            sum += child.idf

    def traverse4LevelSeperation(self, depth):
        level[depth].append(self)
        for child in self.children:
            child.traverse4LevelSeperation(depth+1)



# *** Node insertion logic ***
class Inserter(object):
    def __init__(self, node, depth = 0):
        self.node = node
        self.depth = depth

    def __call__(self, title, idf, depth):
        newNode = Node(title, idf)
        if (depth > self.depth):
            self.node.add(newNode)
            self.depth = depth
        elif (depth == self.depth):
            self.node.parent.add(newNode)
        else:
            parent = self.node.parent
            for i in range(0, self.depth - depth):
                parent = parent.parent
            parent.add(newNode)
            self.depth = depth

        self.node = newNode

checkinsPath = '/Users/cangiracoglu/Desktop/checkins2'
dirs = [d for d in os.listdir(checkinsPath) if os.path.join(checkinsPath, d)]
categoryAuthDictionary = {}

cityName = "Ankara"
for dir in dirs:
    if dir == cityName:
        for root, dirs, files in os.walk(checkinsPath + "/" + dir, topdown=False):
            for name in files:
                if name.endswith('txt'):
                    f = open(os.path.join(root, name), "r")
                    lines = f.readlines()
                    matrix = {}
                    count = 0
                    venue = []
                    path = root.split("/")
                    categoryName = path[len(path)-1]
                    for line in lines:
                        arr = line.split("\t")
                        if not arr[0] in matrix:
                            matrix[arr[0]] = []
                            for i in range(0, count):
                                matrix[arr[0]].append(0)
                            if not arr[1] in venue:
                                for m in matrix:
                                    if not m == arr[0]:
                                        matrix[m].append(0)
                                venue.append(arr[1])
                                count += 1
                                matrix[arr[0]].append(1)
                            else:
                                for j in range(0, count):
                                    if j == venue.index(arr[1]):
                                        matrix[arr[0]][j] += 1
                        else:
                            if not arr[1] in venue:
                                for n in matrix:
                                    if not n == arr[0]:
                                        matrix[n].append(0)
                                venue.append(arr[1])
                                count += 1
                                matrix[arr[0]].append(1)
                            else:
                                for j in range(0, count):
                                    if j == venue.index(arr[1]):
                                        matrix[arr[0]][j] += 1

                    res = []

                    for i in matrix:
                        res.append(matrix[i])

                    M = len(res)
                    N = len(res[0])

                    if M > N:
                        temp = np.zeros([M, M])
                    elif N > M:
                        temp = np.zeros([N, N])

                    if M != N:
                        for i in range(0, len(res)):
                            for j in range(0, len(res[0])):
                                temp[i][j] = res[i][j]

                        res = temp

                    # Converting dense matrix to sparse matrix
                    PhiMat = csc_matrix(res)

                    # epsilon is the tolerance between the successive vectors of
                    # hubs abd authorities
                    epsilon = 0.001

                    # auth is a vector of authority score of dimension Mx1
                    # hub is a vector of hub score of dimension Mx1
                    M, N = PhiMat.shape

                    # Normalizing the authorities and hubs vector by their L2 norm
                    auth0 = (1.0 / sqrt(M)) * np.ones([M, 1])
                    hubs0 = (1.0 / sqrt(M)) * np.ones([M, 1])

                    auth1 = PhiMat.transpose() * hubs0
                    hubs1 = PhiMat * auth1

                    # Normalizing auth and hub by their L2 norm
                    auth1 = (1.0 / norm(auth1, 2)) * auth1
                    hubs1 = (1.0 / norm(hubs1, 2)) * hubs1

                    # Calculating the hub and authority vectors until convergence


                    while ((norm(auth1 - auth0, 2) > epsilon) or (norm(hubs1 - hubs0, 2) > epsilon)):
                        auth0 = auth1
                        hubs0 = hubs1
                        auth1 = PhiMat.transpose() * hubs0
                        hubs1 = PhiMat * auth1

                        auth1 = (1.0 / norm(auth1, 2)) * auth1
                        hubs1 = (1.0 / norm(hubs1, 2)) * hubs1

                    userExpertiseDict = {}
                    i = 0
                    for key, value in matrix.items():
                        userExpertiseDict[key] = auth1[i][0]
                        i += 1

                    sortedUserExpertise = sorted(userExpertiseDict.items(), key=operator.itemgetter(1))
                    sortedUserExpertise.reverse()
                    categoryAuthDictionary[categoryName] = sortedUserExpertise
                    # Printing the values of hubs and authorities vectors
                    # print("authority vector is ", "\n", auth1)
                    # print("hubs vector is ", "\n", hubs1)

                    f.close()
        break
########Tf-idf tree of the user
##IDF
# *** File iteration logic ***
d = {}
with open("CategoryWeights.txt") as f:
    for line in f:
        tokens = line.split("\t")
        key = tokens[0]
        value = tokens[2]
        d[key] = float(value)

with open(r"tree.txt", 'r') as f:
    tree = Node(f.readline().rstrip('\n'), 0)
    inserter = Inserter(tree)

    for line in f:
        line = line.rstrip('\n')
        # note there's a bug with your original tab parsing code:
        # it would count all tabs in the string, not just the ones
        # at the beginning
        tabs = re.match('\t*', line).group(0).count('\t')
        title = line[tabs:]

        if title in d:
            inserter(title, d[title], tabs)
        else:
            inserter(title, 0, tabs)

# tf values and tree traversals

tfDictionary = {}

with open('response.json') as data_file:
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

tree.traverse4IDF(tfDictionary)
tree.traverse4Aggregation()

####Candidate selection
dirs = [d for d in os.listdir(checkinsPath) if os.path.join(checkinsPath, d)]

users = set()
venues = set()
level = [[], [], [], [], []]

for dir in dirs:
    if dir == cityName:
        for root, dirs, files in os.walk(checkinsPath + '/' + dir, topdown=False):
            for name in files:
                if name.endswith('txt'):
                    f = open(os.path.join(root, name), "r")
                    lines = f.readlines()
                    path = root.split("/")
                    categoryName = path[len(path)-1]
                    for line in lines:
                        tokens = line.split("\t")
                        users.add(tokens[0])
                        venues.add(tokens[1])

tree.traverse4LevelSeperation(0)

for x in range(0, 4):
    minCategory = 10000
    for category in level[4-x]:
        if category.idf < minCategory and category.idf != 0:
            minCategory = category.idf

    for category in level[4 - x]:
        k = category.idf / minCategory