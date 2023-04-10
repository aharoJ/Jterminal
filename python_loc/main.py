import re

def extract_methods(file_content):
    methods = []
    stack = []
    method_start = -1

    for index, line in enumerate(file_content.split('\n')):
        if re.match(r'\s*(public|private|protected)?\s+\w+\s+\w+\s*\(', line):
            method_start = index
            stack.append('{')

   