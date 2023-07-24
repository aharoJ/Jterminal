# Quick Guide
1. `Git Clone https://github.com/aharoJ/research_mutations.git`
2. [RUN THE SCRIPT](https://github.com/aharoJ/research_mutations#how-to-run-the-script)

## Run the Script
### For Unix-based systems (Linux, MacOS)
To run the setup script on Unix-based systems:

1. Save the script into a file named `setup_unix.sh`.
2. Make the file executable using the command `chmod +x setup_unix.sh`.
3. Execute the script using `./setup_unix.sh`.

### For Windows systems
To run the setup script on Windows systems:

1. Save the script into a file named `setup_windows.ps1`.
2. Make sure your PowerShell execution policy allows the execution of scripts. You might need to change it using `Set-ExecutionPolicy -ExecutionPolicy Unrestricted`.
3. Execute the script using `./setup_windows.ps1`.

## Script Docs
This project includes a shell script to automate the setup and execution process. Here's what the script does:

### 1. Create a 'desk' directory
The script creates a directory named 'desk' in your home directory.

### 2. Clone Git Repository
The script clones the git repository aharoJ/research_mutations into the 'desk' directory.

### 3. Execute Maven Commands
The script navigates into the cloned repository and executes a series of Maven commands. These commands include: `mvn clean`, `mvn package`, and `mvn compile`.

### 4. Install Pip3 and littledarwin
The script checks if pip3 (Python's package installer) is installed. If not, the script will download and install it. After this, it will use pip3 to install 'littledarwin'.

### 5. Change Directory
The script changes the working directory to 'jterminal' (if it exists in your home directory) or 'research/jterminal' (if 'jterminal' doesn't exist). 

### 6. Run littledarwin
Finally, the script runs the command `python3 -m littledarwin -m -b -t ./ -p ./src/main`.

**NOTE:** Be sure to replace 'jterminal' and 'research/jterminal' with the correct paths according to your directory structure.



**CAUTION:** Always review any scripts before running them to ensure they're safe and won't harm your system or data. These scripts assume you're okay with them making changes to your system (like installing `pip3` and creating directories).
