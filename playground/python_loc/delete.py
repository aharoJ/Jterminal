import itertools

# the distances between the cities
distances = {
    ('A', 'B'): 10,
    ('A', 'C'): 15,
    ('A', 'D'): 20,
    ('B', 'C'): 35,
    ('B', 'D'): 25,
    ('C', 'D'): 30
}

cities = ['A', 'B', 'C', 'D']

# generate all possible routes
routes = list(itertools.permutations(cities))

# calculate the total distance of each route
distances_of_routes = {}
for route in routes:
    distance = 0
    for i in range(len(route) - 1):
        distance += distances[(route[i], route[i+1])]
    distance += distances[(route[-1], route[0])]
    distances_of_routes[route] = distance

# choose the shortest route
shortest_route = min(distances_of_routes, key=distances_of_routes.get)
shortest_distance = distances_of_routes[shortest_route]

print(f"All possible routes: {routes}")
print(f"Distances of all routes: {distances_of_routes}")
print(f"Shortest route: {shortest_route}")
print(f"Shortest distance: {shortest_distance}")

