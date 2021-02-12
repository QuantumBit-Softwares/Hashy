#https://zblesk.net/blog/csv-to-sqlite-2-1/
import csv_to_sqlite

# all the usual options are supported
options = csv_to_sqlite.CsvOptions(typing_style="full", encoding="utf-8")
input_files = ["CSV/mostpopulartweets.csv"] # pass in a list of CSV files
csv_to_sqlite.write_csv(input_files, "Databases/mostpopulartweets.db", options)

print("Success Most populat tweets to sqlite")
