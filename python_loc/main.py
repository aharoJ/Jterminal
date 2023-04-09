import re

def extract_methods(file_content):
    methods = []
    stack = []
    method_start = -1

    for index, line in enumerate(file_content.split('\n')):
        if re.match(r'\s*(public|private|protected)?\s+\w+\s+\w+\s*\(', line):
            method_start = index
            stack.append('{')

        if method_start != -1:
            opening_braces = line.count('{')
            closing_braces = line.count('}')

            for _ in range(opening_braces - 1):
                stack.append('{')

            for _ in range(closing_braces):
                stack.pop()

            if not stack:
                method_end = index
                method_body = '\n'.join(file_content.split('\n')[method_start:method_end + 1])
                method_name = re.search(r'\s*(public|private|protected)?\s+\w+\s+(\w+)\s*\(', method_body).group(2)
                methods.append((method_name, method_body))
                method_start = -1
    return methods

def count_loc(method_body):
    lines = method_body.split('\n')
    non_empty_lines = [line.strip() for line in lines if line.strip() and not line.strip().startswith('}') and not line.strip().startswith('{')]
    return len(non_empty_lines)

# def count_loc(method_body):
#     lines = method_body.split('\n')
#     non_empty_lines = [line.strip() for line in lines if line.strip() and not line.strip().startswith('}') and not line.strip().startswith('{') and not re.match(r'\s*(public|private|protected)?\s+\w+\s+\w+\s*\(', line)]
#     return len(non_empty_lines) - 1

def main():
    with open('Dummy.java', 'r') as file:
        file_content = file.read()

    methods = extract_methods(file_content)

    for method in methods:
        loc = count_loc(method[1])
        print(f"Method '{method[0]}' has {loc} lines of code.")

if __name__ == "__main__":
    main()
