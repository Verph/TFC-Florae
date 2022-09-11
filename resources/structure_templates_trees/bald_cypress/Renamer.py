import os
import argparse

parser = argparse.ArgumentParser(description="Small script that replaces a substring in a file name")
parser.add_argument("-i", "--input", help="substring to replace", required=True)
parser.add_argument("-o", "--output", help="substring to replace with", required=True)
args = parser.parse_args()

replaceStr = args.input;
replaceWith = args.output;

for filename in os.listdir("."):
    if replaceStr in filename:
        os.rename(filename, filename.replace(replaceStr, replaceWith))