import re
import sys

# Get the file path from the command line arguments
file_path = sys.argv[1]

# Read in the Java code from the file
with open(file_path, "r") as f:
    java_code = f.read()

# Use regular expressions to match lines of code
loc = len(re.findall(r"\n\s*[^\n\s}]+[^\n]*\n", java_code))

# Print the LOC
print(f"The file has {loc} lines of code.")
