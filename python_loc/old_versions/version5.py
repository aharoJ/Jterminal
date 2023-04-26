def count_loc_and_add_comments(file_path):
    with open(file_path, 'r') as java_file:
        lines = java_file.readlines()

    loc = 0
    new_lines = []

    for line in lines:
        stripped_line = line.strip()
        if stripped_line and not stripped_line.startswith("//"):
            loc += 1
            new_lines.append(f"{line.rstrip()} // {loc}\n")
        else:
            new_lines.append(line)

    with open(file_path, 'w') as java_file:
        java_file.writelines(new_lines)

    return loc


if __name__ == "__main__":
    java_file_path = "Dummy.java"
    loc = count_loc_and_add_comments(java_file_path)
    print(f"Total LOC in {java_file_path}: {loc}")
