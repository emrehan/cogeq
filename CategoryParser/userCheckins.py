import os

dirs = [d for d in os.listdir('C:/checkins') if os.path.join('C:/checkins',d) ]

cityName = "Ankara"
userId = 95222

countX = 0
dict = {}
for dir in dirs:
    if dir == cityName:
        for root, dirs, files in os.walk( "C:/checkins/" + dir, topdown=False):
            for name in files:
                if name.endswith('txt'):
                    f = open(os.path.join(root, name), "r")
                    lines = f.readlines()
                    path = root.split( "/")
                    categoryName = path[ len(path)-1]
                    for line in lines:
                        tokens = line.split( "\t")
                        if int(tokens[0]) == userId:
                            countX += 1
                            if categoryName in dict:
                                dict[categoryName] += 1
                            else:
                                dict[categoryName] = 1

for key, value in dict.items():
    dict[key] = float( value) / countX





