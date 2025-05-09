package PTactics.view.GUI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.*;

public class TutorialWindow extends JDialog {

    private static final long serialVersionUID = 1L;
    private static final Color BG_COLOR = new Color(60, 40, 20); // dark brown

    public TutorialWindow() {
        setTitle("HOLDFIRE");
        setVisible(false);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null); // to center

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(BG_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // header welcome message
        contentPanel.add(createHeaderWelcomeLabel("WELCOME TO HOLDFIRE INSTRUCTIONS"));
        contentPanel.add(Box.createVerticalStrut(5));

        // header game explanation
        contentPanel.add(createHeaderLabel("GAME EXPLANATION"));

        // game description
        contentPanel.add(createSection("Game description:",
                "HOLDFIRE is a turn-based tactics game that wants the player to estrategize and use its troops to their",
                "full extent. With military-inspired ambientation and loads of game modes to try out.", null));

        // game objective
        contentPanel.add(createSection("Game objective:",
                "The objective of the game is twofold: you can eliminate all of the enemy troops or you can capture the",
                "site highlighted in the map. If you complete either objective you will win the battle.", null));

        contentPanel.add(createSection("Turn description:",
                "In a turn of HOLDFIRE you can do several things with each of your troops; you can move them to a different",
                "place, make them aim for the kill or make them use their special ability, unique to each troop type",
                null));

        // header controls
        contentPanel.add(createHeaderLabel("CONTROLS"));

        // troop selection
        contentPanel.add(createSection("Troop selection:",
                "In order to play you must command your troops to victory. To do that you simply must click on any of",
                "your troops and they will be ready for combat.", null));

        // move command
        contentPanel.add(createSection("Moving troops:",
                "You can move a selected troop by pressing 'm' on the keyboard or by clicking on the button in the screen.",
                "Each troop can move a limited amount of tiles per turn and the path they'd take will appear on the screen.",
                "/Icons/move_icon.png"));

        // aim command
        contentPanel.add(createSection("Aiming with troops:",
                "you can aim with a troop by pressing 'a' on the keyboard or by clicking on the button in the screen.",
                "Troops can aim up, down, left and right and each type has a different range in which they will be lethal.",
                "/Icons/aim_icon.png"));

        // ability command
        contentPanel.add(createSection("Using abilities with troops:",
                "you can use an ability with a troop by pressing 'b' on the keyboard or by clicking on the button in the screen.",
                "Each troop has its own unique ability, all with limited uses. Make sure to use them to the best of your ability.",
                "/Icons/ability_icon.png"));

        // endTurn command
        contentPanel.add(createSection("Ending Turn:",
                "Once you have done everything you needed to do in your turn is time to let your rivals play. To do that just",
                "press the button on the screen.", "/Icons/endTurn_icon.png"));

        contentPanel.add(Box.createVerticalStrut(35));
        contentPanel.add(createHeaderLabel("TROOPS & ABILITIES"));

        contentPanel.add(createTroopInfo("LightTroop (dash):",
                "This high speed troop can become invulnerable with a swift dash in combat. Ideal for untangling difficult situations.",
                "/Icons/LightTroop_down.png"));

        contentPanel.add(createTroopInfo("Sniper (drone):",
                "Not only can they use their absolute range sniper to decimate the enemy from afar but they control the board with their drones.",
                "/Icons/sniper_down.png"));

        contentPanel.add(createTroopInfo("Smoker (smoke):",
                "Do you want to move safely and unspotted? create a diversion? just annoy the rivals? if so, the smokes are the perfect tool.",
                "/Icons/Smoker_down.png"));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // scroll faster

        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private Component createHeaderLabel(String title) {
        JLabel label = new JLabel(title);
        label.setFont(new Font("Times New Roman", Font.BOLD, 25));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return label;
    }

    private Component createHeaderWelcomeLabel(String title) {
        JLabel label = new JLabel(title);
        label.setFont(new Font("Times New Roman", Font.BOLD, 30));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return label;
    }

    private Component createSection(String title, String line1, String line2, String iconPath) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG_COLOR);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(createBoldLabel("➙ " + title));
        panel.add(createIndentedLabel(line1));
        panel.add(createIndentedLabel(line2));

        if (iconPath != null) {
            panel.add(Box.createVerticalStrut(10));
            JLabel iconLabel = new JLabel(resizeIcon(iconPath, 130, 45));
            iconLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(iconLabel);
        }

        panel.add(Box.createVerticalStrut(20));
        return panel;
    }

    private JLabel createIndentedLabel(String text) {
        JLabel label = new JLabel("    " + text);
        label.setFont(new Font("Times New Roman", Font.PLAIN, 17));
        label.setForeground(Color.LIGHT_GRAY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JLabel createBoldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Times New Roman", Font.BOLD, 19));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private Component createTroopInfo(String title, String description, String imagePath) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG_COLOR);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(createBoldLabel("➙ " + title));
        panel.add(createIndentedLabel(description));
        panel.add(Box.createVerticalStrut(8));

        JLabel img = new JLabel(resizeIcon(imagePath, 64, 64));
        img.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(img);
        panel.add(Box.createVerticalStrut(15));

        return panel;
    }

    private ImageIcon resizeIcon(String path, int width, int height) {
        try {
            URL resource = TutorialWindow.class.getResource(path);
            if (resource == null) {
                System.err.println("[TutorialWindow] Missing icon: " + path);
                return createErrorIcon(width, height);
            }
            ImageIcon originalIcon = new ImageIcon(resource);
            Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            System.err.println("[TutorialWindow] Error loading icon: " + path);
            e.printStackTrace();
            return createErrorIcon(width, height);
        }
    }

    private ImageIcon createErrorIcon(int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.RED);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);
        g2d.drawString("ERR", 5, height/2 + 5);
        g2d.dispose();
        return new ImageIcon(img);
    }
}