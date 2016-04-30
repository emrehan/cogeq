import os
import re
import json

# *** Tree representation ***
level = [[],[],[],[],[]]

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

# candidate selection

dirs = [d for d in os.listdir('C:/checkins') if os.path.join('C:/checkins',d) ]

cityName = "Ankara"
userId = 95222
users = set()
venues = set()
countX = 0
dict = {}
for dir in dirs:
    if dir == cityName:
        for root, dirs, files in os.walk( '/Users/cangiracoglu/Desktop/checkins/' + dir, topdown=False):
            for name in files:
                if name.endswith('txt'):
                    f = open(os.path.join(root, name), "r")
                    lines = f.readlines()
                    path = root.split( "/")
                    categoryName = path[ len(path)-1]
                    for line in lines:
                        tokens = line.split( "\t")
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
        




j=35

