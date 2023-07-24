# Function to print a banner message
Function Print-Banner {
    param([string]$message)

    Write-Host "--------------------------------------------------------------------------------"
    Write-Host $message
    Write-Host "--------------------------------------------------------------------------------"
}

########################################################################################################################
# Determine OS
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

# Function to check Java version
check_java_version() {
    local version=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
    echo "Java version: $version"
    if [[ "$version" < "11" ]]; then
        echo "Java version is less than 11. Please upgrade to at least Java 11."
        exit 1
    elif [[ "$version" > "11" ]]; then
        echo "Java version is greater than 11. The script may not work correctly."
        exit 1
    fi
}

# Step 0: Check Java version
check_java_version


# Step 1: Ensure the ~/desk directory exists
$deskPath = Join-Path $env:USERPROFILE "desk"
if (!(Test-Path $deskPath)) {
    New-Item -ItemType Directory -Force -Path $deskPath
    Print-Banner "Directory ~/desk created"
}

# Step 2: Clone the git repository if it doesn't already exist
$repoPath = Join-Path $deskPath "research_mutations"
if (!(Test-Path $repoPath)) {
    Set-Location $deskPath
    git clone git@github.com:aharoJ/research_mutations.git
    Print-Banner "Repository research_mutations cloned"
} else {
    Print-Banner "The research_mutations repository has already been cloned"
}

# Step 3: Run the Maven commands if the POM file exists
Set-Location $repoPath
if (Test-Path "pom.xml") {
    mvn clean
    mvn package
    mvn compile
    Print-Banner "Maven commands executed"
} else {
    Print-Banner "No pom.xml file found. Make sure you're in the right directory and that the project is a Maven project"
}

# Step 4: Check if pip3 is installed
if ((Get-Command "pip3" -ErrorAction SilentlyContinue)) {
    Print-Banner "pip3 is installed"
} else {
    Print-Banner "pip3 is not installed, installing now"
    Invoke-WebRequest "https://bootstrap.pypa.io/get-pip.py" -OutFile "get-pip.py"
    python get-pip.py
}

# Step 4 continued: Install littledarwin
pip3 install littledarwin
Print-Banner "littledarwin installed"

# Step 5: Change directory to jterminal or research/jterminal
Set-Location $env:USERPROFILE
if (Test-Path "jterminal") {
    Set-Location "jterminal"
} elseif (Test-Path "research/jterminal") {
    Set-Location "research/jterminal"
} else {
    Print-Banner "Neither jterminal nor research/jterminal directory exists. Please check your directory structure."
}

# Step 6: Run the LittleDarwin command if the source directory exists
if (Test-Path "./src/main") {
    python -m littledarwin -m -b -t ./ -p ./src/main
    Print-Banner "LittleDarwin command executed"
} else {
    Print-Banner "The source directory ./src/main does not exist. Please check your directory structure."
}
