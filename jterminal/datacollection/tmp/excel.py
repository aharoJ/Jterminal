import os
import hashlib
import pandas as pd

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

base_file = "base/base.txt"
base_hash = hash_file(base_file)

directory = "M"
files = os.listdir(directory)

results = []

for file in files:
    if file.endswith(".txt"):
        file_path = os.path.join(directory, file)
        file_hash = hash_file(file_path)
        comparison = "Same" if base_hash == file_hash else "Different"
        results.append({"File": file, "Comparison": comparison})

# Convert the results to a pandas DataFrame and write it to an Excel file
df = pd.DataFrame(results)
df.to_excel("comparison_results.xlsx", index=False)
