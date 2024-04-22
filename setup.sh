#!/bin/bash

# Function to print a banner message
print_banner() {
    echo "--------------------------------------------------------------------------------"
    echo ""
    echo "$1"
    echo ""
    echo "--------------------------------------------------------------------------------"
}

# Function to check Java version
check_java_version() {
    local version=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | awk -F '.' '{print $1}')
    echo "Java major version: $version"
    if [[ "$version" -ne "17" ]]; then
        echo "Java version is not 17. The script requires Java 17 to run correctly."
        exit 1
    fi
}

# Determine OS and validate
if [ "$(uname)" == "Darwin" ]; then
    echo "Running on MacOS."
elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
    echo "Running on Linux."
else
    echo "Unsupported operating system. This script only supports MacOS and Linux."
    exit 1
fi

# Step 0: Check Java version
check_java_version

# Step 1: Run the Maven commands if the POM file exists
if [ -f pom.xml ]; then
    mvn clean package compile
    print_banner "Maven commands executed"
else
    print_banner "No pom.xml file found. Make sure you're in the right directory and that the project is a Maven project"
fi

# Step 2: Check if pip3 is installed
if command -v pip3 &>/dev/null; then
    print_banner "pip3 is installed"
else
    print_banner "pip3 is not installed, installing now"
    curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py
    python3 get-pip.py
fi

# Step 3: Install littledarwin
pip3 install littledarwin
print_banner "littledarwin installed"

# Step 4: Run the LittleDarwin command if the source directory exists
if [ -d "./src/main" ]; then
    python3 -m littledarwin -m -b -t ./ -p ./src/main
    print_banner "LittleDarwin command executed"
else
    print_banner "The source directory ./src/main does not exist. Please check your directory structure."
fi
