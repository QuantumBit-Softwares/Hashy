package hashy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AnalysisWindow extends HashyWindow {
    JLabel title = new JLabel("Analyzing hashtag");
    JLabel subtitle = new JLabel("Loading...");
    ReadAnalysis data = null;

    // Constructor
    AnalysisWindow(String hashtag) {
        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                window.dispose();
            }
        });

        window.setTitle("Loading...");
        window.setLayout(null);

        panel.setSize(300, 150);
        panel.setLayout(null);

        Font titleFont = new Font("Sans_Serif", Font.BOLD, 18);
        Font subtitleFont = new Font("Sans_Serif", Font.PLAIN, 10);

        title.setBounds(0, 5, 300, 50);
        title.setFont(titleFont);
        title.setHorizontalAlignment(JTextField.CENTER);
        panel.add(title);
        
        subtitle.setBounds(0, 40, 300, 25);
        subtitle.setFont(subtitleFont);
        subtitle.setHorizontalAlignment(JTextField.CENTER);
        panel.add(subtitle);

        window.add(panel);

        window.setSize(300, 150);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        try {
            data = analyzeData(hashtag);
        } catch (IOException io) {
            System.out.println("Error");
        }
    }

    /* Called algorithm for analysis */
    public ReadAnalysis analyzeData(String hashtag) throws IOException {
        try {
            // Remove the database folders (necessary for python code execution)
            removeFolder("Databases\\");
            removeFolder("CSV\\");
            removeFolder("Dumps\\");

            /** The text display here isn't actually working 
             *  Some finicky stuff with the windows
            */
            File query = new File("query.txt");
            query.delete();
            query.createNewFile();
            subtitle.setText("Creating query file at runtime location...");

            FileWriter h = new FileWriter(query);
            h.write(hashtag);
            h.close();
            subtitle.setText("Wrote to file. Getting data...");

            runAnalysis();
            subtitle.setText("Data from Twitter stored in database. Retrieving data...");

            
        } catch (Exception e) {
            System.out.println("Error");
        }
        // Create the object that will automatically store info from all provided databases
        ReadAnalysis analysis = new ReadAnalysis();
        window.setTitle("Success!");
        title.setText("Hashtag Analyzed");
        subtitle.setText("Successfully retrieved data from databases.");

        // Add the button for exiting this window
        addOk();

        return analysis;
    }

    /* Run the Python program for the analysis */
    static public void runAnalysis() {
        String command = "Analysis.exe";
        try {
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // String command = "python Main.py";
        // try {
        //     Process process = Runtime.getRuntime().exec(command);

        //     BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        //     String line;
        //     while ((line = reader.readLine()) != null) {
        //         System.out.println(line);
        //     }

        //     reader.close();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }

    /* Creates a button to remove the window */
    public void addOk() {
        window.setVisible(false);
        JButton confirm = new JButton("OK");
        confirm.setBounds(120, 80, 60, 25);
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                window.dispose();
            }
        });

        panel.add(confirm);
        window.setVisible(true);
    }

    /* Clear the database folders for re-analysis */
    public void removeFolder(String location) {
        String[] entries;
        File folder = new File(location);
        if (folder.exists()) {
            entries = folder.list();
            for (String s : entries) {
                File currentFile = new File(folder.getPath(), s);
                if (currentFile.exists())
                    currentFile.delete();
            }
            folder.delete();
        }
    }
}