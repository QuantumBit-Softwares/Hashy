/** All of the info is gathered here in this class.
 *  Refer to each class's implementation (all of them are included in the public class)
 *  on how the data is gathered on each class.
 */ 

package hashy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ReadAnalysis
{
    Connection db = null;

    /* All of the databases are stored within these objects */
    Sentiment s = new Sentiment(db);
    RelatedHashtags r_hashtags = new RelatedHashtags(db);
    Trends trends = new Trends(db);
    TweetList tweet_lists = new TweetList(db);
    FreqWords fw = new FreqWords(db);
    ScreenNames names = new ScreenNames(db);
    LexicalDiversity ld = new LexicalDiversity(db);
}

class Sentiment {
    private String positive;
    private String neutral;
    private String negative;

    public Sentiment(Connection c) {
        try {
            // create a database connection
            c = DriverManager.getConnection("jdbc:sqlite:Databases\\SENTIMENTS.db");

            // create an sql statement variable
            Statement statement = c.createStatement();
            statement.setQueryTimeout(10);

            // store results in a set
            ResultSet rs = statement.executeQuery("select * from SENTIMENTS"); // output is the name of the table
            while (rs.next()) { // go through every row
                // read the columns
                this.positive = rs.getString("positivesenti");
                this.neutral = rs.getString("neutralsenti");
                this.negative = rs.getString("negativesenti");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (c != null)
                    c.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    public String getPositive() {
        return this.positive;
    }

    public String getNeutral() {
        return this.neutral;
    }

    public String getNegative() {
        return this.negative;
    }
}

class RelatedHashtags {
    private ArrayList<String> rt = new ArrayList<String>();
    private ArrayList<String> count = new ArrayList<String>();

    public RelatedHashtags(Connection c) {
        try {
            // create a database connection
            c = DriverManager.getConnection("jdbc:sqlite:Databases\\related_hashtags_with_count.db");

            // create an sql statement variable
            Statement statement = c.createStatement();
            statement.setQueryTimeout(5);

            // store results in a set
            ResultSet rs = statement.executeQuery("select * from related_hashtags_with_count"); // output is the name of the table
            while (rs.next()) { // go through every row
                this.rt.add(rs.getString("Hashtag"));
                this.count.add(rs.getString("count"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (c != null)
                    c.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    public ArrayList<String> getList() {
        return rt;
    }

    public ArrayList<String> getCount() {
        return count;
    }
}

class Trends {
    private ArrayList<String> trendName = new ArrayList<String>();
    private ArrayList<String> trendUrl = new ArrayList<String>();
    private ArrayList<String> trendCount = new ArrayList<String>();

    public Trends(Connection c) {
        try {
            // create a database connection
            c = DriverManager.getConnection("jdbc:sqlite:Databases\\PH_trends.db");

            // create an sql statement variable
            Statement statement = c.createStatement();
            statement.setQueryTimeout(5);

            // store results in a set
            ResultSet rs = statement.executeQuery("select * from PH_trends");
            while (rs.next()) { // go through every row
                this.trendName.add(rs.getString("name"));
                this.trendUrl.add(rs.getString("url"));
                this.trendCount.add(rs.getString("tweet_volume"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (c != null)
                    c.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    public ArrayList<String> getTrendNames() {
        return this.trendName;
    }

    public ArrayList<String> getTrendUrl() {
        return this.trendUrl;
    }

    public ArrayList<String> getTrendCount() {
        return this.trendCount;
    }
}

class TweetList {
    private ArrayList<String> positive_tweets = new ArrayList<String>();
    private ArrayList<String> neutral_tweets = new ArrayList<String>();
    private ArrayList<String> negative_tweets = new ArrayList<String>();

    public TweetList(Connection c) {
        try {
            // create a database connection
            c = DriverManager.getConnection("jdbc:sqlite:Databases\\PICKEDTWEETS.db");

            // create an sql statement variable
            Statement statement = c.createStatement();
            statement.setQueryTimeout(5);

            // store results in a set
            ResultSet rs = statement.executeQuery("select * from PICKEDTWEETS");
            while (rs.next()) { // go through every row
                this.positive_tweets.add(rs.getString("POSITIVETWEETS"));
                this.neutral_tweets.add(rs.getString("NEUTRALTWEETS"));
                this.negative_tweets.add(rs.getString("NEGATIVETWEETS"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (c != null)
                    c.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    public ArrayList<String> getPositiveTweets() {
        return this.positive_tweets;
    }

    public ArrayList<String> getNeutralTweets() {
        return this.neutral_tweets;
    }

    public ArrayList<String> getNegativeTweets() {
        return this.negative_tweets;
    }
}

class FreqWords {
    private ArrayList<String> word = new ArrayList<String>();
    private ArrayList<String> count = new ArrayList<String>();

    public FreqWords(Connection c) {
        try {
            // create a database connection
            c = DriverManager.getConnection("jdbc:sqlite:Databases\\Freq_Words.db");

            // create an sql statement variable
            Statement statement = c.createStatement();
            statement.setQueryTimeout(5);

            // store results in a set
            ResultSet rs = statement.executeQuery("select * from Freq_Words");
            while (rs.next()) { // go through every row
                this.word.add(rs.getString("Word"));
                this.count.add(rs.getString("Count"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (c != null)
                    c.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    public String getTop() {
        return this.word.get(0);
    }

    public ArrayList<String> getList() {
        return this.word;
    }

    public ArrayList<String> getCountList() {
        return this.count;
    }
}

class ScreenNames {
    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<String> count = new ArrayList<String>();

    public ScreenNames(Connection c) {
        try {
            // create a database connection
            c = DriverManager.getConnection("jdbc:sqlite:Databases\\ScreenNames.db");

            // create an sql statement variable
            Statement statement = c.createStatement();
            statement.setQueryTimeout(5);

            // store results in a set
            ResultSet rs = statement.executeQuery("select * from ScreenNames");
            while (rs.next()) { // go through every row
                this.name.add(rs.getString("Screen Name"));
                this.count.add(rs.getString("Count"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (c != null)
                    c.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    public String getTop() {
        return this.name.get(0);
    }

    public ArrayList<String> getList() {
        return this.name;
    }

    public ArrayList<String> getCountList() {
        return this.count;
    }
}

class LexicalDiversity {
    private String words;
    private String screenNames;
    private String lexHashtags;
    private String averageWords;

    public LexicalDiversity(Connection c) {
        try {
            // create a database connection
            c = DriverManager.getConnection("jdbc:sqlite:Databases\\LexicalDiversity.db");

            // create an sql statement variable
            Statement statement = c.createStatement();
            statement.setQueryTimeout(5);

            // store results in a set
            ResultSet rs = statement.executeQuery("select * from LexicalDiversity");
            words = rs.getString("Words");
            screenNames = rs.getString("ScreenNames");
            lexHashtags = rs.getString("LexHashtags");
            averageWords = rs.getString("aveWords");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (c != null)
                    c.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    public String getWords() {
        return this.words;
    }

    public String getScreenNames() {
        return this.screenNames;
    }

    public String getLexHashtags() {
        return this.lexHashtags;
    }

    public String getAveWords() {
        return this.averageWords;
    }
}

