## mutation_research
research for University of Kean 




## Setup and Execution Instructions
This project includes a shell script to automate the setup and execution process. Here's what the script does:

Creates a directory named desk in your home directory.
Clones a git repository (aharoJ/research_mutations) into the desk directory.
Navigates into the cloned repository and executes a series of Maven commands (mvn clean, mvn package, mvn compile).
Checks if pip3 (Python's package installer) is installed. If it's not, the script will download and install it.
Installs littledarwin using pip3.
Changes directory to jterminal (if it exists in your home directory) or research/jterminal (if the first doesn't exist).
Runs the command python3 -m littledarwin -m -b -t ./ -p ./src/main.
Note: Replace jterminal and research/jterminal with the correct paths according to your directory structure.

To run the script:

Save the script into a file named script.sh.
Make the file executable using the command chmod +x script.sh.
Execute the script using ./script.sh.
CAUTION: Always review any scripts before running them to ensure they're safe and won't harm your system or data. This script assumes you're okay with it making changes to your system (like installing pip3 and creating directories).

