package hashy;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HashtagsList extends HashyWindow {
    String inputHashtag;

    HashtagsList(ReadAnalysis data) {
        JFrame result = new JFrame("Related Hashtags");
        result.setSize(300, 480);


        JScrollPane twtListScroll = new JScrollPane(createList(data));
        twtListScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        result.add(twtListScroll, BorderLayout.CENTER);
        result.setLocationRelativeTo(null);
        result.setVisible(true);
    }

    /* Creates the panel for displaying all of the data. The scroll pane is added to this panel. */
    public JPanel createList(ReadAnalysis data) {
        ArrayList<String> hashtags = data.r_hashtags.getList();
        ArrayList<String> count = data.r_hashtags.getCount();
        JPanel hashtagPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(hashtagPanel, BoxLayout.Y_AXIS);
        hashtagPanel.setLayout(boxlayout);

        Font nameFont = new Font("Sans_Serif", Font.BOLD, 18);
        Font bodyFont = new Font("Sans_Serif", Font.PLAIN, 16);

        // Panel elements
        for(int i = 0; i < hashtags.size(); i++) {            
            GridBagConstraints gbc = new GridBagConstraints();

            JPanel hashResult = new JPanel(new GridBagLayout());
            hashResult.setBackground(Color.WHITE);
            hashResult.setPreferredSize(new Dimension(240, 75));
            hashResult.setMaximumSize(new Dimension(240, 100));
    
            JLabel hashName = new JLabel(hashtags.get(i));
            hashName.setFont(nameFont);
            hashName.setForeground(Color.BLACK);
            hashName.setHorizontalAlignment(JLabel.CENTER);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(0,20,0,20);
            gbc.weightx = 0;
            gbc.gridx = 1;
            gbc.gridy = 0;
            hashResult.add(hashName, gbc);
    
            JLabel countText = new JLabel(count.get(i) + " mentions");
            countText.setFont(bodyFont);
            countText.setHorizontalAlignment(JLabel.CENTER);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0,20,0,20);
            gbc.weightx = 0.5;
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            hashResult.add(countText, gbc);
    
            hashtagPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            hashtagPanel.add(hashResult);
        }
        
        return hashtagPanel;
    }

    // public static void main(String[] args) {
    //     new HashtagsList(new ReadAnalysis());
    // }
}
