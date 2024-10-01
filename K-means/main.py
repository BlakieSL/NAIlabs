import math
import random


def loadData():
    data = [] #list(tuple(list(float),str))
    with open('iris_kmeans.txt', 'r') as file:
        for line in file:
            if line.strip():
                parts = line.strip().split(',')
                features = [float(part) for part in parts[:-1]]
                label = parts[-1]
                data.append((features, label))
    return data


def euclidean_distance(point1, point2):
    sum = 0
    for i in range(len(point1)):
        sum += (point1[i] - point2[i]) ** 2
    return math.sqrt(sum)


def initCentroids(data, k):

    centroids = [] # list(list(int))
    indices = random.sample(range(len(data)), k)
    for x in indices:
        centroids.append(data[x][0])
    return centroids


def initClusters(data, centroids):
    clusters = {} # dictionary(int, list(tuple(list(float), str)))
    for i in range(len(centroids)):
        clusters[i] = []

    for features, label in data:
        distances = []  # List[float]
        for i in centroids:
            distances.append(euclidean_distance(features, i))
        closestIndex = 0
        minDist = distances[0]
        for i in range(len(distances)):
            if distances[i] < minDist:
                minDist = distances[i]
                closestIndex = i
        clusters[closestIndex].append((features, label))
    return clusters


def reInitCentroids(clusters):
    newCentroids = [] # list(list(float))
    for cluster in clusters.values():
        if cluster:
            avg = []
            featuresLength = len(cluster[0][0])
            pointsLength = len(cluster)
            for i in range(featuresLength):
                total = 0
                for point, _ in cluster:
                    total += point[i]
                avg.append(total / pointsLength)
            newCentroids.append(avg)
    return newCentroids


def calcTotalDist(clusters, centroids):
    total = 0
    for x, cluster in clusters.items():
        for point, _ in cluster:
            centroid = centroids[x]
            total += euclidean_distance(point, centroid)
    return round(total, 2)


def calcPurity(clusters):
    result = []
    for x, cluster in clusters.items():
        if cluster:
            count = {} # dictionary
            for i in cluster:
                label = i[1]
                if label in count:
                    count[label] += 1
                else:
                    count[label] = 1

            total = sum(count.values())
            purity = ""

            for label, count in count.items():
                purity += " | " + label + "-" + str(round(((count / total) * 100), 2)) + "% "
            result.append("Cluster " + str(x + 1) + ": " + purity)

    return result


def k_means(data, k):
    centroids = initCentroids(data, k)
    while True:
        clusters = initClusters(data, centroids)
        new_centroids = reInitCentroids(clusters)
        if new_centroids == centroids:
            break
        centroids = new_centroids
        print("Sum:", calcTotalDist(clusters, centroids))
        for purity in calcPurity(clusters):
            print(purity)


def main():
    data = loadData()
    k = int(input("Enter the number of clusters (k): "))
    k_means(data, k)


main()
