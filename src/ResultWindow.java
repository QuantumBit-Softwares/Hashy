package hashy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.ArrayList;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ResultWindow extends HashyWindow {
    String inputHashtag;
    MainWindow mw = null;
    ReadAnalysis data = null;

    ResultWindow(MainWindow h, ReadAnalysis d, JFrame f, JPanel result, String s) {
        // Transfer arguments to local variables
        mw = h;
        inputHashtag = s;
        window = f;
        panel = result;
        data = d;
        result.setBounds(0, 0, 1280, 720);

        // Panel elements
        DecimalFormat df = new DecimalFormat("###.##");

        Font hashtagFont = new Font("Sans_Serif", Font.BOLD, 36);
        Font subtitleFont = new Font("Sans_Serif", Font.PLAIN, 20);
        Font tweetFont = new Font("Sans_Serif", Font.BOLD, 24);
        Font sentimentFont = new Font("Sans_Serif", Font.BOLD, 50);

        Color positiveColor = new Color(146, 208, 80);
        Color neutralColor = new Color(166, 166, 166);
        Color negativeColor = new Color(192, 0, 0);

        try {
            BufferedImage hash = ImageIO.read(new File("Images\\hashtag.png"));
            JLabel hashImage = new JLabel(new ImageIcon(hash));
            hashImage.setBounds(160, 390, 320, 100);
            hashImage.setHorizontalAlignment(JLabel.CENTER);
            // hashImage.addMouseListener(new MouseAdapter() {
            //     public void mouseClicked(MouseEvent e) {
            //
            //         System.out.println("hi");
            //     }
            // });
            result.add(hashImage);

            BufferedImage mention = ImageIO.read(new File("Images\\mention.png"));
            JLabel mentionImage = new JLabel(new ImageIcon(mention));
            mentionImage.setBounds(480, 390, 320, 100);
            mentionImage.setHorizontalAlignment(JLabel.CENTER);
            result.add(mentionImage);

            BufferedImage word = ImageIO.read(new File("Images\\word.png"));
            JLabel wordImage = new JLabel(new ImageIcon(word));
            wordImage.setBounds(800, 390, 320, 100);
            wordImage.setHorizontalAlignment(JLabel.CENTER);
            result.add(wordImage);
        } catch (IOException e) {
            System.out.println("Error");
        }

        JButton back = new JButton("Back");
        back.setBounds(50, 50, 80, 60);
        back.setFont(subtitleFont);
        // Add action for going back to main window
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                removePanel();
                mw.showHome(mw);
            }
        });
        result.add(back);

        JTextField inputTweet = new JTextField();
        inputTweet.setBounds(320, 50, 650, 60);
        inputTweet.setHorizontalAlignment(JTextField.CENTER);
        inputTweet.setFont(tweetFont);
        // Show the tweet analysis and result window when user inputs data
        inputTweet.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        Action analyzeHashtag = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inputTweet.getText().equals(""))
                    JOptionPane.showMessageDialog(null, "Please enter a valid hashtag!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                else {
                    // Get string from user input
                    String input = inputTweet.getText();
                    input = input.replaceAll("#", "");

                    // Create the analysis object, will automatically analyze the hashtag
                    AnalysisWindow load = new AnalysisWindow(input);

                    // Remove the main window and show the result window
                    removePanel();
                    new ResultWindow(mw, load.data, window, panel, input);
                    window.setVisible(true);
                }
            }
        };
        inputTweet.getActionMap().put("Enter", analyzeHashtag);
        inputTweet.addActionListener(analyzeHashtag);
        result.add(inputTweet);

        JLabel resultTitle1 = new JLabel("Reception for:");
        resultTitle1.setBounds(320, 90, 650, 100);
        resultTitle1.setFont(subtitleFont);
        resultTitle1.setHorizontalAlignment(JTextField.CENTER);
        result.add(resultTitle1);

        JLabel resultTitle2 = new JLabel("#" + inputHashtag);
        resultTitle2.setBounds(320, 120, 650, 100);
        resultTitle2.setFont(hashtagFont);
        resultTitle2.setHorizontalAlignment(JTextField.CENTER);
        result.add(resultTitle2);

        /*************** SHOW RESULTS OF ANALYSIS ***************/ 

        JLabel sentimentPositive = new JLabel(df.format(Double.parseDouble(data.s.getPositive())) + "% POSITIVE");
        sentimentPositive.setBounds(320, 180, 650, 100);
        sentimentPositive.setFont(sentimentFont);
        sentimentPositive.setHorizontalAlignment(JTextField.CENTER);
        sentimentPositive.setForeground(positiveColor);
        result.add(sentimentPositive);

        JLabel sentimentNeutral = new JLabel(df.format(Double.parseDouble(data.s.getNeutral())) + "% NEUTRAL");
        sentimentNeutral.setBounds(320, 230, 650, 100);
        sentimentNeutral.setFont(sentimentFont);
        sentimentNeutral.setHorizontalAlignment(JTextField.CENTER);
        sentimentNeutral.setForeground(neutralColor);
        result.add(sentimentNeutral);

        JLabel sentimentNegative = new JLabel(df.format(Double.parseDouble(data.s.getNegative())) + "% NEGATIVE");
        sentimentNegative.setBounds(320, 275, 650, 100);
        sentimentNegative.setFont(sentimentFont);
        sentimentNegative.setHorizontalAlignment(JTextField.CENTER);
        sentimentNegative.setForeground(negativeColor);
        result.add(sentimentNegative);

        String most_related_hashtag = "";
        ArrayList<String> r_temp = data.r_hashtags.getList();
        String h_temp;
        String h_get;
        int i = 0;
        do {
            h_get = r_temp.get(i);
            h_temp = h_get;
            h_temp = h_temp.replaceAll("#","");
            h_temp = h_temp.toLowerCase();
            if (h_temp.equals(s.toLowerCase())) i++;
            else most_related_hashtag = h_get;
        } while (most_related_hashtag.equals(""));
        JLabel mR_hashtag = new JLabel("#" + most_related_hashtag);
        mR_hashtag.setBounds(160, 450, 320, 100);
        mR_hashtag.setFont(tweetFont);
        mR_hashtag.setHorizontalAlignment(JTextField.CENTER);
        result.add(mR_hashtag);
        JLabel mR_h_Title = new JLabel("Most related hashtag");
        mR_h_Title.setBounds(160, 480, 320, 100);
        mR_h_Title.setFont(subtitleFont);
        mR_h_Title.setHorizontalAlignment(JTextField.CENTER);
        result.add(mR_h_Title);

        JLabel mention = new JLabel("@" + data.names.getTop());
        mention.setBounds(480, 450, 320, 100);
        mention.setFont(tweetFont);
        mention.setHorizontalAlignment(JTextField.CENTER);
        result.add(mention);
        JLabel mentionTitle = new JLabel("Most frequent mention");
        mentionTitle.setBounds(480, 480, 320, 100);
        mentionTitle.setFont(subtitleFont);
        mentionTitle.setHorizontalAlignment(JTextField.CENTER);
        result.add(mentionTitle);

        JLabel word = new JLabel("\"" + data.fw.getList().get(1) + "\"");
        word.setBounds(800, 450, 320, 100);
        word.setFont(tweetFont);
        word.setHorizontalAlignment(JTextField.CENTER);
        result.add(word);
        JLabel wordTitle = new JLabel("Most frequent word");
        wordTitle.setBounds(800, 480, 320, 100);
        wordTitle.setFont(subtitleFont);
        wordTitle.setHorizontalAlignment(JTextField.CENTER);
        result.add(wordTitle);

        JButton showHashtagList = new JButton("View related hashtags");
        showHashtagList.setBounds(180, 580, 260, 50);
        showHashtagList.setFont(subtitleFont);
        showHashtagList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                new HashtagsList(d);
            }
        });
        result.add(showHashtagList);

        JButton showTweetList = new JButton("View list of tweets");
        showTweetList.setBounds(500, 580, 260, 50);
        showTweetList.setFont(subtitleFont);
        showTweetList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                new TweetsList(data, s, data.tweet_lists.getPositiveTweets());
            }
        });
        result.add(showTweetList);
        

        JButton showTrendsList = new JButton("See PH trends");
        showTrendsList.setBounds(820, 580, 260, 50);
        showTrendsList.setFont(subtitleFont);
        showTrendsList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                new TrendsList(data);
            }
        });
        panel.add(showTrendsList);
    }
}
