import os

def file_to_dict(filepath):
    with open(filepath, 'r') as f:
        return {line.split(',')[0]: line.split(',')[1].strip() for line in f.readlines()}

base_dict = file_to_dict("base/base.txt")

directory = "M"
files = os.listdir(directory)

for file in files:
    if file.endswith(".txt"):
        file_path = os.path.join(directory, file)
        compare_dict = file_to_dict(file_path)
        if base_dict == compare_dict:
            print(f"{file}: Same")
        else:
            print(f"{file}: Different")
            # For differences, print the specific differing lines
            for key, value in compare_dict.items():
                if key in base_dict:
                    if base_dict[key] != value:
                        print(f"Different value at line {key}: base={base_dict[key]}, {file}={value}")
                else:
                    print(f"Extra line in {file}: {key},{value}")
            for key in base_dict:
                if key not in compare_dict:
                    print(f"Missing line in {file}: {key},{base_dict[key]}")
