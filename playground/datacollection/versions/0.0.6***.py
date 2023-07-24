import os
import re

def atoi(text): 
    # Convert string to integer if possible.
    return int(text) if text.isdigit() else text

def natural_keys(text): 
    # Split text into a list of strings and integers. Strings containing numbers are converted to integers.
    return [ atoi(c) for c in re.split(r'(\d+)', text) ]

def file_to_dict(filepath):
    with open(filepath, 'r') as f:
        # Treat each line number and its content as a key-value pair in the dictionary.
        return {i: line.strip() for i, line in enumerate(f.readlines(), 1)}

base_dict = file_to_dict("base/base.txt")

directory = "M"
files = sorted(os.listdir(directory), key=natural_keys)  

for file in files:
    if file.endswith(".txt"):
        file_path = os.path.join(directory, file)
        compare_dict = file_to_dict(file_path)
        if base_dict == compare_dict:
            print(f"\n{file}: Same\n")
        else:
            print(f"\n{file}: Different")
            print(f"------------------------------------------------------------")
            for line_num, value in compare_dict.items():
                if line_num in base_dict:
                    if base_dict[line_num] != value:
                        print(f"Different value at line {line_num}:\n\tbase={base_dict[line_num]}\n\t{file}={value}")
                else:
                    print(f"Extra line in {file} at line {line_num}: {value}")
            for line_num in base_dict:
                if line_num not in compare_dict:
                    print(f"Missing line in {file} at line {line_num}: {base_dict[line_num]}")
            print(f"------------------------------------------------------------")
