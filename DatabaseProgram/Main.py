

#################################################
# Importing modules
import re 
import tweepy 
from tweepy import OAuthHandler 
from textblob import TextBlob 
import json

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
    d1 = {
    "SENTIMENTS": [
                   {
    "positivesenti" : PP,
    "neutralsenti" : PN,
    "negativesenti" : PY
                   }]    
    }
    #d1 sentiments percentage
    print(json.dumps(d1, indent = 1))


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
    pickedtweets1 = [
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
    #all dumper PICKEDTWEETS
    print(json.dumps(pickedtweets1, indent = 1))
    print("\n\n\n")
    print( Positive[0][0])


 
if __name__ == "__main__": 
	# calling main function 
	main()
