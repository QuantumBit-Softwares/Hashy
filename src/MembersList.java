package hashy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.net.*;

public class MembersList extends HashyWindow {
    MembersList() {
        JFrame result = new JFrame("Group Members");
        result.setSize(300, 480);


        JScrollPane twtListScroll = new JScrollPane(createList());
        twtListScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        result.add(twtListScroll, BorderLayout.CENTER);
        result.setLocationRelativeTo(null);
        result.setVisible(true);
    }

    public JPanel createList() {
        ArrayList<String> lName = new ArrayList<String>();
        ArrayList<String> fName = new ArrayList<String>();

        lName.add("Manangan");
        fName.add("Asher");

        lName.add("Sorino");
        fName.add("Isaac");

        lName.add("Artita");
        fName.add("Alain Nevin");

        lName.add("Corpuz");
        fName.add("Charmaine Lourdes");

        lName.add("Sanchez");
        fName.add("Dominic");

        JPanel trendPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(trendPanel, BoxLayout.Y_AXIS);
        trendPanel.setLayout(boxlayout);

        Font nameFont = new Font("Sans_Serif", Font.BOLD, 20);
        Font bodyFont = new Font("Sans_Serif", Font.PLAIN, 16);

        GridBagConstraints titlegbc = new GridBagConstraints();
        JPanel title = new JPanel(new GridBagLayout());
        title.setBackground(Color.GRAY);
        title.setPreferredSize(new Dimension(240, 50));
        title.setMaximumSize(new Dimension(240, 50));

        // Title objects
        JLabel titleText = new JLabel("HASHY");
        titleText.setFont(nameFont);
        titleText.setForeground(Color.BLACK);
        titleText.setHorizontalAlignment(JTextField.CENTER);
        titlegbc.fill = GridBagConstraints.HORIZONTAL;
        titlegbc.insets = new Insets(0, 0, 0, 0);
        titlegbc.weightx = 0;
        titlegbc.gridx = 1;
        titlegbc.gridy = 0;
        title.add(titleText, titlegbc);

        JLabel groupText = new JLabel("Group 4");
        groupText.setFont(bodyFont);
        groupText.setHorizontalAlignment(JTextField.CENTER);
        titlegbc.fill = GridBagConstraints.HORIZONTAL;
        titlegbc.anchor = GridBagConstraints.CENTER;
        titlegbc.insets = new Insets(0, 0, 0, 0);
        titlegbc.weightx = 0.5;
        titlegbc.gridx = 0;
        titlegbc.gridy = 1;
        titlegbc.gridwidth = 2;
        title.add(groupText, titlegbc);

        trendPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        trendPanel.add(title);

        // Panel elements
        for(int i = 0; i < lName.size(); i++) {            
            GridBagConstraints gbc = new GridBagConstraints();

            JPanel trend = new JPanel(new GridBagLayout());
            trend.setBackground(Color.WHITE);
            trend.setPreferredSize(new Dimension(240, 75));
            trend.setMaximumSize(new Dimension(240, 100));
    
            JLabel lNText = new JLabel(lName.get(i));
            lNText.setFont(nameFont);
            lNText.setForeground(Color.BLACK);
            lNText.setHorizontalAlignment(JTextField.CENTER);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(0,0,0,0);
            gbc.weightx = 0;
            gbc.gridx = 1;
            gbc.gridy = 0;
            trend.add(lNText, gbc);
    
            JLabel fNText = new JLabel(fName.get(i));
            fNText.setFont(bodyFont);
            fNText.setHorizontalAlignment(JTextField.CENTER);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0,0,0,0);
            gbc.weightx = 0.5;
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            trend.add(fNText, gbc);
    
            trendPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            trendPanel.add(trend);
        }
        
        return trendPanel;
    }

    // public static void main(String[] args) {
    //     new MembersList();
    // }
}
