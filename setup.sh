#!/bin/bash

# Step 1
mkdir ~/desk

# Step 2
cd ~/desk
git clone git@github.com:aharoJ/research_mutations.git

# Step 3
cd research_mutations
mvn clean
mvn package
mvn compile

# Step 4
if command -v pip3 &>/dev/null
then
    echo "pip3 is installed"
else
    echo "pip3 is not installed, installing now"
    curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py
    python3 get-pip.py
fi

# Step 4 continued
pip3 install littledarwin

# Step 5
cd ~
if [ -d "jterminal" ]
then
    cd jterminal
else
    cd research/jterminal
fi

# Step 6
python3 -m littledarwin -m -b -t ./ -p ./src/main
