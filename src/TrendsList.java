package hashy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.net.*;

public class TrendsList extends HashyWindow {
    String inputHashtag;

    /* Constructor */
    TrendsList(ReadAnalysis data) {
        JFrame result = new JFrame("PH Trends");
        result.setSize(360, 480);


        JScrollPane twtListScroll = new JScrollPane(createList(data));
        twtListScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        result.add(twtListScroll, BorderLayout.CENTER);
        result.setLocationRelativeTo(null);
        result.setVisible(true);
    }

    /* Creates the panel for displaying all of the data. The scroll pane is added to this panel. */
    public JPanel createList(ReadAnalysis data) {
        ArrayList<String> name = data.trends.getTrendNames();
        ArrayList<String> url = data.trends.getTrendUrl();
        ArrayList<String> count = data.trends.getTrendCount();
        JPanel trendPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(trendPanel, BoxLayout.Y_AXIS);
        trendPanel.setLayout(boxlayout);

        Font nameFont = new Font("Sans_Serif", Font.BOLD, 16);
        Font bodyFont = new Font("Sans_Serif", Font.PLAIN, 16);

        for(int i = 0; i < name.size(); i++) {            
            GridBagConstraints gbc = new GridBagConstraints();

            JPanel trend = new JPanel(new GridBagLayout());
            trend.setBackground(Color.WHITE);
            trend.setPreferredSize(new Dimension(300, 75));
            trend.setMaximumSize(new Dimension(300, 100));
    
            JLabel hashtag = new JLabel(name.get(i));
            hashtag.setFont(nameFont);
            hashtag.setForeground(Color.BLACK);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(0,10,0,0);
            gbc.weightx = 0;
            gbc.gridx = 1;
            gbc.gridy = 0;
            trend.add(hashtag, gbc);
    
            if (!count.get(i).equals("")) { 
                JLabel countText = new JLabel(count.get(i) + " tweets");
                countText.setFont(bodyFont);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.anchor = GridBagConstraints.CENTER;
                gbc.insets = new Insets(0,40,0,40);
                gbc.weightx = 0.5;
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 2;
                trend.add(countText, gbc);
            }

            JButton urlButton = new JButton("View");
            urlButton.setFont(bodyFont);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 170, 25, 30);
            gbc.weightx = 0.2;
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            final String temp_url = url.get(i);
            urlButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent a) {
                    try {
                        Desktop desktop = java.awt.Desktop.getDesktop();
                        URI oURL = new URI(temp_url);
                        desktop.browse(oURL);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            trend.add(urlButton, gbc);
    
            trendPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            trendPanel.add(trend);
        }
        
        return trendPanel;
    }

    // public static void main(String[] args) {
    //     new TrendsList(new ReadAnalysis());
    // }
}
