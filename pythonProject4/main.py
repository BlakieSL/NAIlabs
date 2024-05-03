import math
import random


def load_data(filepath):
    data = []
    with open(filepath, 'r') as file:
        for line in file:
            if line.strip():  # Ensure the line is not empty
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


def initialize_centroids(data, k):
    indices = random.sample(range(len(data)), k)  # k-random indices from the data
    centroids = []
    for x in indices:
        centroids.append(data[x][0])
    return centroids


def assign_clusters(data, centroids):
    clusters = {}
    for i in range(len(centroids)):
        clusters[i] = []

    for features, label in data:
        distances = []
        for centroid in centroids:
            distances.append(euclidean_distance(features, centroid))

        closest_centroid_index = 0
        min_distance = distances[0]
        for i in range(1, len(distances)):
            if distances[i] < min_distance:
                min_distance = distances[i]
                closest_centroid_index = i

        clusters[closest_centroid_index].append((features, label))

    return clusters


def recalculate_centroids(clusters):
    new_centroids = []
    for cluster in clusters.values():
        if cluster:
            mean = []
            num_dimensions = len(cluster[0][0])
            num_points = len(cluster)
            for i in range(num_dimensions):
                total = 0
                for point, _ in cluster:
                    total += point[i]
                mean.append(total / num_points)
            new_centroids.append(mean)
    return new_centroids


def calculate_total_distance(clusters, centroids):
    total_distance = 0
    for centroid_index, cluster in clusters.items():
        for point, _ in cluster:
            centroid = centroids[centroid_index]
            total_distance += euclidean_distance(point, centroid)
    return total_distance



def calculate_purity(clusters):
    """ Calculate the purity of each cluster by counting the most frequent label. """
    purity_output = []
    for cluster_index, cluster in clusters.items():
        if cluster:
            label_counts = {}
            for item in cluster:
                label = item[1]
                if label in label_counts:
                    label_counts[label] += 1
                else:
                    label_counts[label] = 1

            total = sum(label_counts.values())
            purity = ""
            first = True
            for label, count in label_counts.items():
                percentage = int((count / total) * 100)
                if not first:
                    purity += ", "
                purity += str(percentage) + "% " + label
                first = False

            purity_output.append("Cluster " + str(cluster_index + 1) + ": " + purity)

    return purity_output



def k_means(data, k):
    """ The main k-means clustering algorithm. """
    centroids = initialize_centroids(data, k)
    while True:
        clusters = assign_clusters(data, centroids)
        new_centroids = recalculate_centroids(clusters)
        if new_centroids == centroids:  # Check for convergence
            break
        centroids = new_centroids
        print("Total Sum of Distances:", calculate_total_distance(clusters, centroids))
        for purity in calculate_purity(clusters):
            print(purity)


def main():
    filepath = 'iris_kmeans.txt'
    data = load_data(filepath)
    k = int(input("Enter the number of clusters (k): "))
    k_means(data, k)


if __name__ == "__main__":
    main()
