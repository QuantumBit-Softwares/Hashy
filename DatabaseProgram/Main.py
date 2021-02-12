

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

#################################################



class TwitterClient(object): 
	def __init__(self): 
		consumer_key = 'kqUMGE1g9Ye6mZ9IdIxWNamTK'
		consumer_secret = 'ffMqQa8dlAsd4zysRFW47JM7Vk0M5MwSP0QKVOLjrufLSHsEMA'
		access_token = '1156902832269774848-w0E7G6H024TGXg7ef6aM8KmCezVwoK'
		access_token_secret = 'Bvos2WEu0uTzszmTsdiPS1l3PLu9n1uSfIZJd7FjpYAEr'
		try: 
			# create OAuthHandler object 
			self.auth = OAuthHandler(consumer_key, consumer_secret) 
			# set access token and secret 
			self.auth.set_access_token(access_token, access_token_secret) 
			# create tweepy API object to fetch tweets 
			self.api = tweepy.API(self.auth) 
		except: 
			print("Error: Authentication Failed") 


	def clean_tweet(self, tweet): 
		''' 
		Utility function to clean tweet text by removing links, special characters 
		using simple regex statements. 
		'''
		return ' '.join(re.sub("(@[A-Za-z0-9]+)|([^0-9A-Za-z \t]) |(\w+:\/\/\S+)", " ", tweet).split()) 


	def get_tweet_sentiment(self, tweet): 
		''' 
		Utility function to classify sentiment of passed tweet 
		using textblob's sentiment method 
		'''
		# create TextBlob object of passed tweet text 
		analysis = TextBlob(self.clean_tweet(tweet)) 
		# set sentiment 
		if analysis.sentiment.polarity > 0: 
			return 'positive'
		elif analysis.sentiment.polarity == 0: 
			return 'neutral'
		else: 
			return 'negative'

	def get_tweets(self, query, count = 10): 
		''' 
		Main function to fetch tweets and parse them. 
		'''
		# empty list to store parsed tweets 
		tweets = [] 

		try: 
			# call twitter api to fetch tweets 
			fetched_tweets = self.api.search(q = query, count = count) 

			# parsing tweets one by one 
			for tweet in fetched_tweets: 
				# empty dictionary to store required params of a tweet 
				parsed_tweet = {} 

				# saving text of tweet 
				parsed_tweet['text'] = tweet.text 
				# saving sentiment of tweet 
				parsed_tweet['sentiment'] = self.get_tweet_sentiment(tweet.text) 

				# appending parsed tweet to tweets list 
				if tweet.retweet_count > 0: 
					# if tweet has retweets, ensure that it is appended only once 
					if parsed_tweet not in tweets: 
						tweets.append(parsed_tweet) 
				else: 
					tweets.append(parsed_tweet) 

			# return parsed tweets 
			return tweets 

		except tweepy.TweepError as e: 
			# print error (if any) 
			print("Error : " + str(e)) 



#################################################
def main(): 
	# creating object of TwitterClient Class 
    api = TwitterClient() 
	# calling function to get tweets
    query = 'elonmusk' 
    tweets = api.get_tweets(query, count = 200) 

	# picking positive tweets from tweets && # percentage of positive tweets 
    ptweets = [tweet for tweet in tweets if tweet['sentiment'] == 'positive']
    PP = format(100*len(ptweets)/len(tweets))

    # picking negative tweets from tweets && # percentage of negative tweets 
    ntweets = [tweet for tweet in tweets if tweet['sentiment'] == 'negative'] 
    PN = format(100*len(ntweets)/len(tweets))

    # picking neutral tweets from tweets && # percentage of neutral tweets 
    ytweets = [tweet for tweet in tweets if tweet['sentiment'] == 'neutral'] 
    # format(100*(len(tweets) -(len( ntweets )+len( ptweets)))/len(tweets)))
    PY = format(100*len(ytweets)/len(tweets))


 #############################################
 # GET TWEETS AND CLASSIFY THEM INTO THE THREE CATEGORIES:   
    # FIRST 5 POSITIVE TWEETS
    PT = []
    for tweet in ptweets[:10]: 
        #print(tweet['text'])
        PT.append(tweet['text'])
  
    # FIRST 5 NEUTRAL TWEETS 
    YT = []
    for tweet in ytweets[:10]: 
        #print(tweet['text'])
        YT.append(tweet['text'])
        
    # FIRST 5 NEGATIVE TWEETS
    NT = []
    for tweet in ntweets[:10]: 
       #print(tweet['text'])
       NT.append(tweet['text'])

 #############################################
 # Put the gathered data into JSON Format for dumping 
    d = {
    "SENTIMENTS": {
    "positivesenti" : PP,
    "neutralsenti" : PN,
    "negativesenti" : PY
        },
        
    "PICKEDTWEETS": {
    "positivetweet" : PT,
    "neutraltweets" : YT,
    "negativetweets" : NT
        }
    }
    
# Dumping d into JSON formatted data
    json.dumps(d)
    #percentage sentiment first:
    d1 = """{
    "SENTIMENTS": [
                   {
    "positivesenti" : PP,
    "neutralsenti" : PN,
    "negativesenti" : PY
                   }]    
    }"""
    #d1 sentiments percentage
    #print(json.dumps(d1, indent = 1))


##############################################
    # PRINTING 10 POSITIVE PICKED TWEETS
    w, h = 10, 10;
    Positive = [[0 for x in range(w)] for y in range(h)]
    i = 0
    for tweet in ptweets[:10]: 
       #print(tweet['text'])
        Positive[0][i] = tweet['text']
        i = i+1

        positivetweets = {
        "POSITIVETWEETS" : {
        "p1" : Positive[0][0],
        "p2" : Positive[0][1],
        "p3" : Positive[0][2],
        "p4" : Positive[0][3],
        "p5" : Positive[0][4],
        "p6" : Positive[0][5],
        "p7" : Positive[0][6],
        "p8" : Positive[0][7],
        "p9" : Positive[0][8],
        "p10" : Positive[0][9]
        }
    }


    # PRINTING 10 NEUTRAL PICKED TWEETS
    w, h = 10, 10;
    Neutral = [[0 for x in range(w)] for y in range(h)]
    i = 0
    for tweet in ytweets[:10]: 
       #print(tweet['text'])
        Neutral[0][i] = tweet['text']
        i = i+1

        neutraltweets = {
        "NEUTRALTWEETS" : {
        "y1" : Neutral[0][0],
        "y2" : Neutral[0][1],
        "y3" : Neutral[0][2],
        "y4" : Neutral[0][3],
        "y5" : Neutral[0][4],
        "y6" : Neutral[0][5],
        "y7" : Neutral[0][6],
        "y8" : Neutral[0][7],
        "y9" : Neutral[0][8],
        "y10" : Neutral[0][9]
        }
    }

    # PRINTING 10 NEGATIVE PICKED TWEETS
    w, h = 10, 10;
    Matrix = [[0 for x in range(w)] for y in range(h)]
    i = 0
    #print("\n\nNegative tweets:") 
    for tweet in ntweets[:10]: 
       #print(tweet['text'])
        Matrix[0][i] = tweet['text']
        i = i+1

        negativetweets = {
        "NEGATIVETWEETS" : {
        "n1" : Matrix[0][0],
        "n2" : Matrix[0][1],
        "n3" : Matrix[0][2],
        "n4" : Matrix[0][3],
        "n5" : Matrix[0][4],
        "n6" : Matrix[0][5],
        "n7" : Matrix[0][6],
        "n8" : Matrix[0][7],
        "n9" : Matrix[0][8],
        "n10" : Matrix[0][9]
        }
    }


###############################################
#ALL DUMPER
    pickedtweets = [
    {
        "POSITIVETWEETS" : Positive[0][0],
        "NEUTRALTWEETS" :  Neutral[0][0],
        "NEGATIVETWEETS" : Matrix[0][0]
     },
     {
        "POSITIVETWEETS" : Positive[0][1],
        "NEUTRALTWEETS" :  Neutral[0][1],
        "NEGATIVETWEETS" : Matrix[0][1]
         
     },
     {
        "POSITIVETWEETS" : Positive[0][2],
        "NEUTRALTWEETS" :  Neutral[0][2],
        "NEGATIVETWEETS" : Matrix[0][2]
         
     },
     {
        "POSITIVETWEETS" : Positive[0][3],
        "NEUTRALTWEETS" :  Neutral[0][3],
        "NEGATIVETWEETS" : Matrix[0][3]
         
     },
     {
        "POSITIVETWEETS" : Positive[0][4],
        "NEUTRALTWEETS" :  Neutral[0][4],
        "NEGATIVETWEETS" : Matrix[0][4]
         
     },
     {
        "POSITIVETWEETS" : Positive[0][5],
        "NEUTRALTWEETS" :  Neutral[0][5],
        "NEGATIVETWEETS" : Matrix[0][5]
         
     },
     {
        "POSITIVETWEETS" : Positive[0][6],
        "NEUTRALTWEETS" :  Neutral[0][6],
        "NEGATIVETWEETS" : Matrix[0][6]
         
     },
     {
        "POSITIVETWEETS" : Positive[0][7],
        "NEUTRALTWEETS" :  Neutral[0][7],
        "NEGATIVETWEETS" : Matrix[0][7]
         
     },
     {
        "POSITIVETWEETS" : Positive[0][8],
        "NEUTRALTWEETS" :  Neutral[0][8],
        "NEGATIVETWEETS" : Matrix[0][8]
         
     },
     {
        "POSITIVETWEETS" : Positive[0][9],
        "NEUTRALTWEETS" :  Neutral[0][9],
        "NEGATIVETWEETS" : Matrix[0][9]
         
     }
    ]

    #############################################################
    #Second Analysis
    CONSUMER_KEY = 'kqUMGE1g9Ye6mZ9IdIxWNamTK'
    CONSUMER_SECRET = 'ffMqQa8dlAsd4zysRFW47JM7Vk0M5MwSP0QKVOLjrufLSHsEMA'
    OAUTH_TOKEN = '1156902832269774848-w0E7G6H024TGXg7ef6aM8KmCezVwoK'
    OAUTH_TOKEN_SECRET = 'Bvos2WEu0uTzszmTsdiPS1l3PLu9n1uSfIZJd7FjpYAEr'
    auth = twitter.oauth.OAuth(OAUTH_TOKEN, OAUTH_TOKEN_SECRET, CONSUMER_KEY, CONSUMER_SECRET)
    twitter_api = twitter.Twitter(auth=auth)
    print(twitter_api)
    
    #Trending Hashtags by WOE_ID
    # The Yahoo! Where On Earth ID for the entire world is 1.
    # See https://dev.twitter.com/docs/api/1.1/get/trends/place and
    # http://developer.yahoo.com/geo/geoplanet/

    PH_WOE_ID = 23424934

    # Prefix ID with the underscore for query string parameterization.
    # Without the underscore, the twitter package appends the ID value
    # to the URL itself as a special case keyword argument.
    ph_trends = twitter_api.trends.place(_id=PH_WOE_ID)
    
    #collecting search results
    #Collecting search results
    q = query
    count = 100
    # See https://dev.twitter.com/docs/api/1.1/get/search/tweets
    search_results = twitter_api.search.tweets(q=q, count=count)
    statuses = search_results['statuses']
    # Iterate through 5 more batches of results by following the cursor
    for _ in range(5):
      try:
        next_results = search_results['search_metadata']['next_results']
      except KeyError as e: # No more results when next_results doesn't exist
        break
      # Create a dictionary from next_results, which has the following form:
      # ?max_id=313519052523986943&q=NCAA&include_entities=1
      kwargs = dict([ kv.split('=') for kv in next_results[1:].split("&") ])
      search_results = twitter_api.search.tweets(**kwargs)
      statuses += search_results['statuses']
      # Show one sample search result by slicing the list...
      #print (json.dumps(statuses[0], indent=1))
      
    
    ### second analysis _ 1
    #extracting tweet entities
    status_texts = [ status['text']
                    for status in statuses ]
    screen_names = [ user_mention['screen_name']
                    for status in statuses
                      for user_mention in status['entities']['user_mentions'] ]

    hashtags = [ hashtag['text'] 
                for status in statuses
                  for hashtag in status['entities']['hashtags'] ]
    # Compute a collection of all words from all tweets
    words = [ w
             for t in status_texts
              for w in t.split() ]
    str0 = json.dumps(hashtags[0:1])
    str1 = json.dumps(hashtags[1])
    str2 = json.dumps(hashtags[2])
    str3 = json.dumps(hashtags[3])
    str4 = json.dumps(hashtags[4])
    str5 = json.dumps(hashtags[5])
    str6 = json.dumps(hashtags[6])
    str7 = json.dumps(hashtags[7])
    str8 = json.dumps(hashtags[8])
    str9 = json.dumps(hashtags[9])

    str0 = str0.replace('"', "")
    str0 = str0.replace("[", "")
    str0 = str0.replace("]", "")

    str1 = str1.replace('"', "")
    str1 = str1.replace("[", "")
    str1 = str1.replace("]", "")

    str2 = str2.replace('"', "")
    str2 = str2.replace("[", "")
    str2 = str2.replace("]", "")

    str3 = str3.replace('"', "")
    str3 = str3.replace("[", "")
    str3 = str3.replace("]", "")

    str4 = str4.replace('"', "")
    str4 = str4.replace("[", "")
    str4 = str4.replace("]", "")

    str5 = str5.replace('"', "")
    str5 = str5.replace("[", "")
    str5 = str5.replace("]", "")

    str6 = str6.replace('"', "")
    str6 = str6.replace("[", "")
    str6 = str6.replace("]", "")

    str7 = str7.replace('"', "")
    str7 = str7.replace("[", "")
    str7 = str7.replace("]", "")

    str8 = str8.replace('"', "")
    str8 = str8.replace("[", "")
    str8 = str8.replace("]", "")

    str9 = str9.replace('"', "")
    str9 = str9.replace("[", "")
    str9 = str9.replace("]", "")

    dataHH = [
              {
                  "relatedhashtags" : str0
              },
                        {
                  "relatedhashtags" : str1
              },
                        {
                  "relatedhashtags" : str2
              },
                        {
                  "relatedhashtags" : str3
              },
                        {
                  "relatedhashtags" : str4
              },          {
                  "relatedhashtags" : str5
              },
                        {
                  "relatedhashtags" : str6
              },
                        {
                  "relatedhashtags" : str7
              },          {
                  "relatedhashtags" : str8
              },
               {
                  "relatedhashtags" : str9
              }
    ]

    # print(json.dumps(dataHH, indent = 1))
    
    from collections import Counter
    for item in [words, screen_names, hashtags]:
      c = Counter(item)
    

    

    #all dumper txt and json
    
    ############################################### 
    # save as Json file
    with open('SENTIMENTS.json', 'w') as json_file:
        json.dump(d1, json_file)
    with open('PICKEDTWEETS.json', 'w') as json_file:
        json.dump(pickedtweets, json_file)
    f = open('PH_trends.json','w')
    print(json.dumps(ph_trends, indent=1), file=f) # Python 3.x
    f = open('related_hashtags_no_count.json','w')
    print(json.dumps(dataHH, indent = 1), file=f) # Python 3.x
    
    #Pretty table
    counter = 0 
    for label, data in (('Word', words),
      ('Screen Name', screen_names),
      ('Hashtag', hashtags)):
      pt = PrettyTable(field_names=[label, 'Count'])
      c = Counter(data)
      [ pt.add_row(kv) for kv in c.most_common()[:10] ]
      pt.align[label], pt.align['Count'] = 'l', 'r' # Set column alignment
      if counter == 0:
        f = open('Freq_Words.txt','w')
        print (pt, file = f)
        counter = counter + 1
      if counter == 1:
        f = open('ScreenNames.txt','w')
        print (pt, file = f)
        counter = counter + 1
      if counter ==  2:
        f = open('related_hashtags_with_count.txt','w')
        print(pt, file = f)
        counter = counter + 1
      if counter == 3:
        break
        
    # A function for computing lexical diversity
    def lexical_diversity(tokens):
      return 1.0*len(set(tokens))/len(tokens)
    # A function for computing the average number of words per tweet
    def average_words(statuses):
      total_words = sum([ len(s.split()) for s in statuses ])
      return 1.0*total_words/len(statuses)
    d = words = (lexical_diversity(words))
    e = ScreenNames =  (lexical_diversity(screen_names))
    #print(hashtags)
    f = LexHashtags =  (lexical_diversity(hashtags))
    g = AveWords =  (average_words(status_texts))


    data = [
            {
                "Words" : words,
                "ScreenNames" : ScreenNames,
                "LexHashtags" : LexHashtags,
                "AveWords" : AveWords
            }
    ]
    f = open('LexicalDiversity.json','w')
    print(json.dumps(data, indent = 1), file=f) # Python 3.x
    
    retweets = [
                # Store out a tuple of these three values ...
                (status['retweet_count'], 
                 status['retweeted_status']['user']['screen_name'],
                 status['text']) 
                
                # ... for each status ...
                for status in statuses 
                
                # ... so long as the status meets this condition.
                    if status.__contains__('retweeted_status')
               ]

    # Slice off the first 5 from the sorted results and display each item in the tuple
    pt = PrettyTable(field_names=['Count', 'Screen Name', 'Text'])
    [ pt.add_row(row) for row in sorted(retweets, reverse=True)[:5] ]
    pt.max_width['Text'] = 50
    pt.align= 'l'
    f = open('mostpopulartweets.txt','w')
    print(pt, file = f)

        
















if __name__ == "__main__": 
	# calling main function 
	main()
 
