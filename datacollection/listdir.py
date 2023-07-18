import os

directory = "/Users/aharo/desk/research/jterminal/datacollection/java_txt"

for filename in os.listdir(directory):
    if filename.endswith(".java"):
        file_path = os.path.join(directory, filename)
        os.remove(file_path)
