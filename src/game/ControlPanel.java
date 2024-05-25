package game;

import javax.swing.*;

/**
 * Control Panel for the GUI in the game.
 */
public class ControlPanel {
    private Game game;
    private JButton pauseButton;
    private JButton resumeButton;
    private JButton restartButton;
    private JButton quitButton;
    private JPanel mainPanel;
    private JButton saveButton;
    private JButton loadButton;

    /**
     * Returns the mainPanel.
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Creates an instance of the ControlPanel.
     */
    public ControlPanel(Game game) {
        this.game = game;
        pauseButton.addActionListener(e -> game.pause());
        resumeButton.addActionListener(e -> game.resume());
        restartButton.addActionListener(e -> game.restart());
        quitButton.addActionListener(e -> System.exit(0));
        saveButton.addActionListener(e -> game.save());
        loadButton.addActionListener(e -> game.load());
    }
}

