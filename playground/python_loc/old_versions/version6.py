import re

def count_loc_and_add_comments(file_path):
    with open(file_path, 'r') as java_file:
        lines = java_file.readlines()

    loc = 0
    func_loc = 0
    current_function = None
    function_locs = {}
    new_lines = []

    for line in lines:
        stripped_line = line.strip()

        if current_function and stripped_line == "}":
            function_locs[current_function] = func_loc
            current_function = None
            func_loc = 0

        if stripped_line and not stripped_line.startswith("//"):
            loc += 1
            new_lines.append(f"{line.rstrip()} // {loc}\n")

            if current_function:
                func_loc += 1

        else:
            new_lines.append(line)

        function_declaration = re.match(r"public .* (\w+)\(.*\)", stripped_line)
        if function_declaration:
            current_function = function_declaration.group(1)
            func_loc = 1

    with open(file_path, 'w') as java_file:
        java_file.writelines(new_lines)

    return loc, function_locs

if __name__ == "__main__":
    java_file_path = "Dummy.java"
    loc, function_locs = count_loc_and_add_comments(java_file_path)
    print(f"Total LOC in {java_file_path}: {loc}")
    for function, func_loc in function_locs.items():
        print(f"{function} = {func_loc}")
