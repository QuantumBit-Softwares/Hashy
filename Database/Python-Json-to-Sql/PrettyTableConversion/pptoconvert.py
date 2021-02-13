

#################################################
# Importing modules
import re 
import tweepy 
from tweepy import OAuthHandler 
from textblob import TextBlob 
from prettytable import PrettyTable
import json
from cherrypicker import CherryPicker
import pandas as pd
import twitter
import csv_to_sqlite
import csv, sys
import os
from subprocess import call


#############################################
def pretty_table_to_tuples(input_str):
        lines = input_str.split("\n")
        num_columns = len(re.findall("\+", lines[0])) - 1
        line_regex = r"\|" + (r" +(.*?) +\|"*num_columns)
        for line in lines:
            m = re.match(line_regex, line.strip())
            if m:
                yield m.groups()
            
#with open('Dumps/Freq_Words.txt') as fp:
fp = open('Dumps/Freq_Words.txt')
input_string = fp.read()
with open('CSV/Freq_Words.csv', 'w') as outcsv:
    writer = csv.writer(outcsv)
    writer.writerows(pretty_table_to_tuples(input_string))
    
#with open('Dumps/ScreenNames.txt') as fp:    
fp = open('Dumps/ScreenNames.txt')
input_string = fp.read()
with open('CSV/ScreenNames.csv', 'w') as outcsv:
    writer = csv.writer(outcsv)
    writer.writerows(pretty_table_to_tuples(input_string))
    
#with open('Dumps/related_hashtags_with_count.txt') as fp:
fp = open('Dumps/related_hashtags_with_count.txt')
input_string = fp.read()
with open('CSV/related_hashtags_with_count.csv', 'w') as outcsv:
    writer = csv.writer(outcsv)
    writer.writerows(pretty_table_to_tuples(input_string))
