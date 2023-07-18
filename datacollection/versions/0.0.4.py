import os  # For file and directory operations
import re  # For regular expressions

def check_in_order(text): 
    # This function is used for converting a string that contains digits to an integer
    # text.isdigit() checks if the text string consists of digits only
    return int(text) if text.isdigit() else text

def natural_keys(text):
    # It splits the filename at each digit and applies the atoi function 
    return [ check_in_order(c) for c in re.split(r'(\d+)', text) ]

def file_to_dict(filepath):
    # This function is used for converting each file to a dictionary
    # The dictionary key is the line number (before the comma)
    # The dictionary value is the rest of the line (after the comma)
    with open(filepath, 'r') as f:
        return {line.split(',')[0]: line.split(',')[1].strip() for line in f.readlines()}

base_dict = file_to_dict("base/base.txt")  # Convert base file to dictionary

directory = "M"
files = sorted(os.listdir(directory), key=natural_keys)  # Get all files in natural/human order

for file in files:
    if file.endswith(".txt"):  # We only want to process .txt files
        file_path = os.path.join(directory, file)  
        compare_dict = file_to_dict(file_path)  # Convert file to dictionary
       
        if base_dict == compare_dict:  # If the dictionaries are the same, the files are the same
            print(f"\n{file}: Same\n")
       
        else:  # If the dictionaries are different, the files are different
            print(f"\n{file}: Different")
            print(f"------------------------------------------------------------")
           
            # For differences, print the specific differing lines
            for key, value in compare_dict.items():
                if key in base_dict:  # Check if the line exists in the base file
                    if base_dict[key] != value:  # If the line content is different, print it
                        print(f"Different value at line {key}:\n\tbase={base_dict[key]}\n\t{file}={value}")
           
                else:  # If the line doesn't exist in the base file, it's an extra line
                    print(f"Extra line in {file}: {key},{value}")
          
            for key in base_dict:  # Check if any lines are missing in the compared file
                if key not in compare_dict:
                    print(f"Missing line in {file}: {key},{base_dict[key]}")
          
            print(f"------------------------------------------------------------")
