import os
import re

def atoi(text):
    return int(text) if text.isdigit() else text

def natural_keys(text):
    return [ atoi(c) for c in re.split(r'(\d+)', text) ]

def file_to_dict(filepath):
    with open(filepath, 'r') as f:
        return {line.split(',')[0]: line.split(',')[1].strip() for line in f.readlines()}

base_dict = file_to_dict("base/base.txt")

directory = "M"
files = sorted(os.listdir(directory), key=natural_keys)  # This sorts files in natural order

for file in files:
    if file.endswith(".txt"):
        file_path = os.path.join(directory, file)
        compare_dict = file_to_dict(file_path)
        if base_dict == compare_dict:
            print(f"\n{file}: Same\n")
        else:
            print(f"\n{file}: Different")
            print(f"------------------------------------------------------------")
            # For differences, print the specific differing lines
            for key, value in compare_dict.items():
                if key in base_dict:
                    if base_dict[key] != value:
                        print(f"Different value at line {key}:\n\tbase={base_dict[key]}\n\t{file}={value}")
                else:
                    print(f"Extra line in {file}: {key},{value}")
            for key in base_dict:
                if key not in compare_dict:
                    print(f"Missing line in {file}: {key},{base_dict[key]}")
            print(f"------------------------------------------------------------")
