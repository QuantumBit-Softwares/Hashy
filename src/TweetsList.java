package hashy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TweetsList extends HashyWindow {
    String inputHashtag;

    TweetsList(ReadAnalysis data, String s, ArrayList<String> tweets) {
        inputHashtag = s;

        Font btnFont = new Font("Sans_Serif", Font.PLAIN, 24);

        JFrame window = new JFrame("Tweet List: " + inputHashtag);
        window.setSize(640, 720);
        window.setLocationRelativeTo(null);

        // Button bar elements
        JPanel btnPanel = new JPanel();
        
        JButton btnPositive = new JButton("Positive");
        btnPositive.setFont(btnFont);
        btnPositive.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                removePanel(); 
                new TweetsList(data, s, data.tweet_lists.getPositiveTweets());
                window.dispose();
            }
        });
        btnPanel.add(btnPositive);

        JButton btnNeutral = new JButton("Neutral");
        btnNeutral.setFont(btnFont);
        btnNeutral.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                removePanel();
                new TweetsList(data, s, data.tweet_lists.getNeutralTweets());
                window.dispose();
            }
        });
        btnPanel.add(btnNeutral);

        JButton btnNegative = new JButton("Negative");
        btnNegative.setFont(btnFont);
        btnNegative.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                removePanel();
                new TweetsList(data, s, data.tweet_lists.getNegativeTweets());
                window.dispose();
            }
        });
        btnPanel.add(btnNegative);

        JScrollPane twtListScroll = new JScrollPane(twtList(tweets));        
        twtListScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        twtListScroll.getViewport().setViewPosition(new Point(0,0));

        window.add(btnPanel, BorderLayout.NORTH);
        window.add(twtListScroll, BorderLayout.CENTER);
        window.setVisible(true);
    }

    /* Creates the panel for displaying all of the data. The scroll pane is added to this panel. */
    public JPanel twtList(ArrayList<String> tweets) {
        JPanel twtList = new JPanel();
        BoxLayout boxlayout = new BoxLayout(twtList, BoxLayout.Y_AXIS);
        twtList.setLayout(boxlayout);

        Font nameFont = new Font("Sans_Serif", Font.BOLD, 16);
        Font handleFont = new Font("Sans_Serif", Font.PLAIN, 16);
        Font bodyFont = new Font("Sans_Serif", Font.PLAIN, 16);

        for(int i = 0; i < tweets.size(); i++) {
            if(!tweets.get(i).equals("0")){
                GridBagConstraints gbc = new GridBagConstraints();
    
                JPanel twtresult = new JPanel(new GridBagLayout());
                twtresult.setBackground(Color.WHITE);
                twtresult.setPreferredSize(new Dimension(480, 270));
                twtresult.setMaximumSize(new Dimension(480, 270));
                
                /*
                JLabel twtName = new JLabel("Tweet Author");
                twtName.setFont(nameFont);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.anchor = GridBagConstraints.LINE_START;
                gbc.insets = new Insets(0,40,0,10);
                gbc.weightx = 0;
                gbc.gridx = 0;
                gbc.gridy = 0;
                twtresult.add(twtName, gbc);
        
                JLabel twtHandle = new JLabel("@Username");
                twtHandle.setFont(handleFont);
                twtHandle.setForeground(Color.GRAY);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(0,0,0,0);
                gbc.weightx = 0;
                gbc.gridx = 1;
                gbc.gridy = 0;
                twtresult.add(twtHandle, gbc);
                */
    
                JTextArea twtBody = new JTextArea(tweets.get(i));
                twtBody.setFont(bodyFont);
                twtBody.setLineWrap(true);
                twtBody.setEditable(false);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.anchor = GridBagConstraints.CENTER;
                gbc.insets = new Insets(0,40,0,40);
                gbc.weightx = 0.5;
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 2;
                twtresult.add(twtBody, gbc);
        
                twtList.add(Box.createRigidArea(new Dimension(0, 40)));
                twtList.add(twtresult);
            }     
        }
        return twtList;
    }

    // public static void main(String[] args) { 
    //     ReadAnalysis data = new ReadAnalysis();
    //     new TweetsList(data, "gamestop", data.tweet_lists.getPositiveTweets());
    // }
}
