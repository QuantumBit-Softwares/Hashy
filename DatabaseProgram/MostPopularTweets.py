import re, csv, sys
def pretty_table_to_tuples(input_str):
    lines = input_str.split("\n")
    num_columns = len(re.findall("\+", lines[0])) - 1
    line_regex = r"\|" + (r" +(.*?) +\|"*num_columns)
    for line in lines:
        m = re.match(line_regex, line.strip())
        if m:
            yield m.groups()


with open('Dumps/most_popular_tweets.bin') as fp:
      input_string = fp.read()


with open('CSV/most_popular_tweets.csv', 'w') as outcsv:
    writer = csv.writer(outcsv)
    writer.writerows(pretty_table_to_tuples(input_string))

