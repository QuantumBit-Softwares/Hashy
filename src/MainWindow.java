package hashy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class MainWindow extends HashyWindow {
    private Image backgroundImage;
    public static void main(String[] args) throws IOException {
        MainWindow hashy = new MainWindow();
        hashy.showHome(hashy);
    }

    MainWindow() {
        // Add action for closing the window
        this.window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        // Window settings
        this.window.setTitle("Hashy");
        this.window.setSize(1280, 720);
        this.window.setLayout(null);
        this.window.setLocationRelativeTo(null);

        // Adding background image
        try {
            backgroundImage = ImageIO.read(new File("Images\\bg.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }

        panel.setBounds(0, 0, 1280, 720);

        panel = new JPanel(null) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, null);
            }
        };
    }

    /* Function for showing the main window */
    public void showHome(MainWindow h) {
        // Panel Elements
        panel.setBounds(0, 0, 1280, 720);

        Font titleFont = new Font("Sans_Serif", Font.BOLD, 96);
        Font subtitleFont = new Font("Sans_Serif", Font.PLAIN, 14);
        Font smallFont = new Font("Sans_Serif", Font.PLAIN, 18);
        Font tweetFont = new Font("Sans_Serif", Font.BOLD, 24);

        // Main icon
        try {
            BufferedImage hashy = ImageIO.read(new File("Images\\hashy.png"));
            JLabel hashyImage = new JLabel(new ImageIcon(hashy));
            hashyImage.setBounds(350, 50, 200, 200);
            hashyImage.setHorizontalAlignment(JLabel.CENTER);
            panel.add(hashyImage);
        } catch (IOException e) {
            System.out.println("Image error!");
        }

        JLabel title = new JLabel("HASHY");
        title.setBounds(580, 40, 340, 200);
        title.setFont(titleFont);
        panel.add(title);

        JLabel subtitle = new JLabel("A tweet analysis system");
        subtitle.setBounds(670, 150, 150, 100);
        subtitle.setFont(subtitleFont);
        panel.add(subtitle);

        JLabel inputTitle = new JLabel("Enter a hashtag to get started!");
        inputTitle.setBounds(520, 500, 250, 100);
        inputTitle.setFont(smallFont);
        panel.add(inputTitle);

        JTextField inputTweet = new JTextField();
        inputTweet.setBounds(320, 570, 650, 60);
        inputTweet.setHorizontalAlignment(JTextField.CENTER);
        inputTweet.setFont(tweetFont);
        // Show the tweet analysis and result window when user inputs data and presses enter
        inputTweet.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        Action analyzeHashtag = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inputTweet.getText().equals("")) JOptionPane.showMessageDialog(null, "Please enter a valid hashtag!", "Error", JOptionPane.ERROR_MESSAGE);
                else {
                    // Get string from user input
                    String input = inputTweet.getText();
                    input = input.replaceAll("#", "");

                    // Create the analysis object, will automatically analyze the hashtag
                    AnalysisWindow load = new AnalysisWindow(input);
                    
                    // Remove the main window and show the result window
                    removePanel();
                    new ResultWindow(h, load.data, window, panel, input);
                    window.setVisible(true);
                    load.window.setVisible(false);
                    load.window.setVisible(true);
                    
                }
            }
        };
        inputTweet.getActionMap().put("Enter", analyzeHashtag);
        inputTweet.addActionListener(analyzeHashtag);
        panel.add(inputTweet);

        JButton about = new JButton("About");
        about.setBounds(1150, 570, 80, 60);
        about.setFont(subtitleFont);
        // Add action for going back to main window
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                new MembersList();
            }
        });
        panel.add(about);

        // Add panel to window and display
        this.window.add(panel);
        this.window.setVisible(true);
    }
}

/** The default class for the windows used in Hashy.
 *  Contains a built-in JFrame and JPanel, and a remove option.
 */
class HashyWindow {
    JFrame window = new JFrame();
    JPanel panel = new JPanel(null);

    public void removePanel() {
        this.panel.removeAll();
        this.window.setVisible(false);
    }
}