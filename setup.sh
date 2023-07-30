#!/bin/bash

# Function to print a banner message
print_banner() {
    echo "--------------------------------------------------------------------------------"
    echo ""
    echo "$1"
    echo ""
    echo "--------------------------------------------------------------------------------"
}

#################################   Function to check Java version    ########################################################
check_java_version() {
    local version=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | awk -F '.' '{print $1}')
    echo "Java major version: $version"
    if [[ "$version" -ne "11" ]]; then
        echo "Java version is not 11. The script requires Java 11 to run correctly."
        exit 1
    fi
}

##############################  Determine OS  #########################################################################
if [ "$(uname)" == "Darwin" ]; then
    # MacOS
    echo "It seems you are running MacOS. Please execute the setup_macos.sh script."
    echo "Run the command: ./setup_macos.sh"
elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
    # Linux
    echo "It seems you are running Linux. Please execute the setup_macos.sh script."
    echo "Run the command: ./setup_macos.sh"
elif [ "$(expr substr $(uname -s) 1 10)" == "MINGW32_NT" ] || [ "$(expr substr $(uname -s) 1 10)" == "MINGW64_NT" ]; then
    # Windows
    echo "It seems you are running Windows. Please execute the setup_windows.ps1 script."
    echo "Run the command: ./setup_windows.ps1 (in PowerShell)"
else
    echo "Unknown operating system."
fi
########################################################################################################################


# Step 0: Check Java version
check_java_version


# Step 1: Ensure the ~/desk directory exists
if [ ! -d ~/desk ]; then
    mkdir ~/desk
    print_banner "Directory ~/desk created"
fi

# Step 2: Clone the git repository if it doesn't already exist
if [ ! -d ~/desk/research_mutations ]; then
    cd ~/desk
    git clone https://github.com/aharoJ/research_mutations.git
    print_banner "Repository research_mutations cloned"
else
    print_banner "The research_mutations repository has already been cloned"
fi

# Step 3: Run the Maven commands if the POM file exists
cd ~/desk/research_mutations/jterminal
if [ -f pom.xml ]; then
    mvn clean
    mvn package
    mvn compile
    print_banner "Maven commands executed"
else
    print_banner "No pom.xml file found. Make sure you're in the right directory and that the project is a Maven project"
fi

# Step 4: Check if pip3 is installed
if command -v pip3 &>/dev/null
then
    print_banner "pip3 is installed"
else
    print_banner "pip3 is not installed, installing now"
    curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py
    python3 get-pip.py
fi

# Step 4 continued: Install littledarwin
pip3 install littledarwin
print_banner "littledarwin installed"

# Step 5: Change directory to jterminal or research/jterminal
cd ~/desk/
if [ -d "jterminal" ]
then
    cd jterminal
elif [ -d "research/jterminal" ]
then
    cd research/jterminal
else
    print_banner "Neither jterminal nor research/jterminal directory exists. Please check your directory structure."
fi

# Step 6: Run the LittleDarwin command if the source directory exists
if [ -d "./src/main" ]; then
    python3 -m littledarwin -m -b -t ./ -p ./src/main
    print_banner "LittleDarwin command executed"
else
    print_banner "The source directory ./src/main does not exist. Please check your directory structure."
fi
