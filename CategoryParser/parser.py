import re

# *** Tree representation ***
class Node(object):
    def __init__(self, title):
        self.title = title
        self.parent = None
        self.children = []

    def add(self, child):
        self.children.append(child)
        child.parent = self

# *** Node insertion logic ***
class Inserter(object):
    def __init__(self, node, depth = 0):
        self.node = node
        self.depth = depth

    def __call__(self, title, depth):
        newNode = Node(title)
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
with open(r"/Users/cangiracoglu/smarttravelling/CategoryParser/tree.txt", 'r') as f:
    tree = Node(f.readline().rstrip('\n'))
    inserter = Inserter(tree)

    for line in f:
        line = line.rstrip('\n')
        # note there's a bug with your original tab parsing code:
        # it would count all tabs in the string, not just the ones
        # at the beginning
        tabs = re.match('\t*', line).group(0).count('\t')
        title = line[tabs:]
        inserter(title, tabs)

j=3

