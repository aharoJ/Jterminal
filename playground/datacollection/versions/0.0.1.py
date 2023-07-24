import os
import hashlib

def hash_file(filename):
    # This function returns the SHA-1 hash of the file
    h = hashlib.sha1()
    with open(filename, 'rb') as file:
        chunk = 0
        while chunk != b'':
            # Read only 1024 bytes at a time
            chunk = file.read(1024)
            h.update(chunk)
    return h.hexdigest()

base_file = "/Users/aharo/desk/research/datacollection/base/base.txt"
base_hash = hash_file(base_file)

directory = "/Users/aharo/desk/research/datacollection/M"
files = os.listdir(directory)

for file in files:
    if file.endswith(".txt"):
        file_path = os.path.join(directory, file)
        file_hash = hash_file(file_path)
        if base_hash == file_hash:
            print(f"{file}: Same")
        else:
            print(f"{file}: Different")
