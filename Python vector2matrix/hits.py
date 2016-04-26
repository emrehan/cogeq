# HITS algorithm to compute the authority vector "A" and hub vector "H"
# a general reference for HITS algorthm is
# http://www.math.cornell.edu/~mec/Winter2009/RalucaRemus/Lecture4/lecture4.html

from math import sqrt
import numpy as np
from scipy.linalg import norm
from scipy.sparse import csc_matrix
import os
dirs = [d for d in os.listdir('/Users/cangiracoglu/Desktop/checkins') if os.path.join('/Users/cangiracoglu/Desktop/checkins',d) ]

countX = 0
for root, dirs, files in os.walk('/Users/cangiracoglu/Desktop/checkins', topdown=False):
    for name in files:
        if name.endswith('txt'):
            print(os.path.join(root, name))
            f = open(os.path.join(root, name), "r")
            lines = f.readlines()
            matrix = {}
            count = 0
            venue = []
            for line in lines:
                print(line)
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
                        count+=1
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

            if (M > N):
                temp = np.zeros([M, M])
            elif (N > M):
                temp = np.zeros([N, N])

            if (M != N):
                for i in range(0,len(res)):
                    for j in range(0,len(res[0])):
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
            auth0 = (1.0/sqrt(M)) * np.ones([M, 1])
            hubs0 = (1.0/sqrt(M)) * np.ones([M, 1])

            auth1 = PhiMat.transpose() * hubs0
            hubs1 = PhiMat * auth1

            # Normalizing auth and hub by their L2 norm
            auth1 = (1.0/norm(auth1, 2))*auth1
            hubs1 = (1.0/norm(hubs1, 2))*hubs1

            # Calculating the hub and authority vectors until convergence


            while((norm(auth1-auth0, 2) > epsilon)or(norm(hubs1-hubs0, 2) > epsilon)):
                auth0 = auth1
                hubs0 = hubs1
                auth1 = PhiMat.transpose() * hubs0
                hubs1 = PhiMat * auth1

                auth1 = (1.0/norm(auth1, 2))*auth1
                hubs1 = (1.0/norm(hubs1, 2))*hubs1

            # Printing the values of hubs and authorities vectors
            print("authority vector is ", "\n", auth1)
            print("hubs vector is ", "\n", hubs1)

        f.close()
        # print(os.path.join(root, name))


