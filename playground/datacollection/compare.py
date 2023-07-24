import os
import re
import pandas as pd

def atoi(text): 
    return int(text) if text.isdigit() else text

def natural_keys(text): 
    return [ atoi(c) for c in re.split(r'(\d+)', text) ]

def file_to_dict(filepath):
    with open(filepath, 'r') as f:
        return {i: line.strip() for i, line in enumerate(f.readlines(), 1)}

base_dict = file_to_dict("base/base.txt")

directory = "M"
files = sorted(os.listdir(directory), key=natural_keys)  

diff_data = [] 

for file in files:
    if file.endswith(".txt"):
        file_path = os.path.join(directory, file)
        compare_dict = file_to_dict(file_path)
        
        if base_dict != compare_dict:
            print(f"\n{file}: Different")
            print(f"------------------------------------------------------------")
            
            for line_num, value in compare_dict.items():
                if line_num in base_dict:
                    if base_dict[line_num] != value:
                        print(f"Different value at line {line_num}:\n\tbase={base_dict[line_num]}\n\t{file}={value}")
                        diff_data.append([file, line_num, base_dict[line_num], value, 'Different Content'])
                else:
                    print(f"Extra line in {file} at line {line_num}: {value}")
                    diff_data.append([file, line_num, "", value, 'Extra Line'])
                    
            for line_num in base_dict:
                if line_num not in compare_dict:
                    print(f"Missing line in {file} at line {line_num}: {base_dict[line_num]}")
                    diff_data.append([file, line_num, base_dict[line_num], "", 'Missing Line'])

            print(f"------------------------------------------------------------")
        else:
            print(f"\n{file}: Same\n")

df = pd.DataFrame(diff_data, columns=["File", "Line Number", "Base File Line", "Compared File Line", "Difference Type"])

df.to_excel("Differences.xlsx", index=False)
