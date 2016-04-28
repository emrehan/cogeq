import re
import json

# *** Tree representation ***

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
        if tfDictionary.has_key(self.title):
            self.idf *= tfDictionary[self.title]
        else:
            self.idf *= 0

        for child in self.children:
            child.traverse4IDF(tfDictionary)

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
            for i in xrange(0, self.depth - depth):
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

        if d.has_key(title):
            inserter(title, d[title], tabs)
        else:
            inserter(title, 0, tabs)

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
        tfDictionary[key] = float(value) / count

tree.traverse4IDF(tfDictionary)

j=354

