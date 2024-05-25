package game;

import city.cs.engine.SoundClip;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;


/**
 * A world with some bodies.
 */
public class Game {

    /**
     * The World in which the bodies move and interact.
     */
    private GameLevel level;

    /**
     * A graphical display of the world (a specialised JPanel).
     */
    private GameView view;
    private SonicController controller;
    private SoundClip gameMusic;
    private SoundClip newLevel;

    JFrame startWindow;
    Container con;
    JPanel titleNamePanel, startButtonPanel, quitButtonPanel;
    JLabel titleNameLabel;
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 60);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 25);
    JButton startButton;
    JButton quitButton;

    /**
     * Initialise a new Game.
     */
    public Game() {
        startWindow = new JFrame();

        startWindow.setSize(637, 360);
        startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startWindow.getContentPane().setBackground(Color.black);
        ImageIcon background = new ImageIcon("data/level1.jpg");
        JLabel backgroundLabel = new JLabel(background);
        startWindow.setContentPane(backgroundLabel);

        startWindow.setLayout(null);
        startWindow.setVisible(true);
        con = startWindow.getContentPane();

        titleNamePanel = new JPanel();
        titleNamePanel.setBounds(118, 50, 401, 75);
        titleNamePanel.setBackground(Color.darkGray);
        titleNameLabel = new JLabel("8-bit Sonic");
        titleNameLabel.setForeground(Color.white);
        titleNameLabel.setFont(titleFont);

        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(243, 150, 150, 50);
        startButtonPanel.setBackground(new Color(255, 255, 255, 0) );

        quitButtonPanel = new JPanel();
        quitButtonPanel.setBounds(243, 200, 150, 50);
        quitButtonPanel.setBackground(new Color(255, 255, 255, 0));



        startButton = new JButton("START");
        startButton.setBackground(Color.white);
        startButton.setForeground(Color.black);
        startButton.setFont(normalFont);
        startButton.addActionListener(e -> createGameButton());


        quitButton = new JButton("QUIT");
        quitButton.setBackground(Color.white);
        quitButton.setForeground(Color.black);
        quitButton.setFont(normalFont);
        quitButton.addActionListener(e -> System.exit(0));

        titleNamePanel.add(titleNameLabel);
        startButtonPanel.add(startButton);
        quitButtonPanel.add(quitButton);


        con.add(titleNamePanel);
        con.add(startButtonPanel);
        con.add(quitButtonPanel);
        startWindow.setLocationRelativeTo(null);

    }
    /**
     * Create the Game window and world.
     */
    public void createGame() {
        // make the level
        level = new Level1(this); // Level now refers to Level 1
        level.populate(this); // Populate world with objects
        level.collisionListener(level.getSonic(), this);
        // make a view
        view = new GameView(level, level.getSonic(), level, 800, 450);
        view.setZoom(10); // Change the zoom
        view.Level1Image();

        // add background music
        try {
            gameMusic = new SoundClip("data/level1music.wav");   // Open an audio input stream
            gameMusic.setVolume(0.005);
            gameMusic.loop();  // Set it to continous playback (looping)
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }

        controller = new SonicController(level.getSonic());
        view.addKeyListener(controller);
        view.addMouseListener(new GiveFocus(view));

        // add the view to a frame (Java top level window)
        final JFrame frame = new JFrame("8-bit Sonic");
        frame.add(view);
        // enable the frame to quit the application when the x button is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        // don't let the frame be resized
        frame.setResizable(false);
        // add GUI buttons
        ControlPanel buttons = new ControlPanel(this);
        frame.add(buttons.getMainPanel(), BorderLayout.SOUTH);

        // size the frame to fit the world view
        frame.pack();
        // set the frame in the center of the screen
        frame.setLocationRelativeTo(null);
        // finally, make the frame visible
        frame.setVisible(true);

        // uncomment this to make a debugging view
//        Frame debugView = new DebugViewer(level, 800, 450);
        // start the level
        level.start();
    }
    /**
     * Action on clicking the "start game" button.
     */
    private void createGameButton() {
        startWindow.dispose();
        createGame();
    }
    /**
     * Go to the next level.
     */
    public void goToNextLevel() {

        if (level instanceof Level1) {

            level.stop(); // Stop the level
            gameMusic.stop(); // Stop the music
            level = new Level2(this); // Level now refers to Level 2
            level.populate(this); // Populate world with objects
            // change game music
            try {
                gameMusic = new SoundClip("data/level2music.wav");   // Open an audio input stream
                gameMusic.setVolume(0.005); // Set the volume
                gameMusic.loop();  // Set it to continous playback (looping)
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
            }
            view.setWorld(level); // Change the view to look into new level
            view.setZoom(10); // Change the zoom
            view.Level2Image(); // Change the background
            view.updateView(level.getSonic(), level); // Update the GameView
            controller.updateSonic(level.getSonic()); // Change the controller to control Sonic in the new world
            level.collisionListener(level.getSonic(), this);
            // debug viewer
//            Frame debugView = new DebugViewer(level, 800, 450);
            level.start(); // Start the level
            try {
                newLevel = new SoundClip("data/levelfinished.wav");   // Open an audio input stream
                newLevel.setVolume(0.005); // Set the volume
                newLevel.play(); // Play the sound
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
            }
        } else if (level instanceof Level2) {

            level.stop(); // Stop the level
            level = new Level3(this); // Level now refers to Level 3
            level.populate(this); // Populate world with objects
            // change game music
            try {
                gameMusic = new SoundClip("data/level3music.wav");   // Open an audio input stream
                gameMusic.setVolume(0.005);
                gameMusic.loop();  // Set it to continous playback (looping)
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
            }
            view.setWorld(level); // Change the view to look into new level
            view.setZoom(10); // Change the zoom
            view.Level3Image(); // Change the background
            view.updateView(level.getSonic(), level); // Update the GameView
            controller.updateSonic(level.getSonic()); // Change the controller to control Sonic in the new world
            level.collisionListener(level.getSonic(), this);
            // debug viewer
//            Frame debugView = new DebugViewer(level, 800, 450);
            level.start(); // Start the level
            try {
                newLevel = new SoundClip("data/levelfinished.wav");   // Open an audio input stream
                newLevel.setVolume(0.005); // Set the volume
                newLevel.play(); // Play the sound
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
            }
        } else if (level instanceof Level3) {
            System.out.println("Well done! Game complete.");
            System.exit(0); // Close the game
        }
    }

    /**
     * Pause the game.
     */
    public void pause() {
        level.stop(); // Stop the level
        gameMusic.pause(); // Pause the music
    }

    /**
     * Resume the game.
     */
    public void resume() {
        level.start(); // Start the level
        gameMusic.resume(); // Resume the music
    }

    /**
     * Restart the current level.
     */
    public void restart() {
        level.stop(); // Stop the level
        gameMusic.play(); // Play the music
        if (level instanceof Level1) {
            level = new Level1(this); // Restart the level
            level.populate(this); // Populate world with objects
            view.Level1Image();
        } else if (level instanceof Level2) {
            level = new Level2(this); // Restart the level
            level.populate(this); // Populate world with objects
            view.Level2Image();
        } else if (level instanceof Level3) {
            level = new Level3(this); // Restart the level
            level.populate(this); // Populate world with objects
            view.Level3Image();
        }
        view.setWorld(level); // Set the world to look into the new level
        view.setZoom(10); // Change the zoom
        view.updateView(level.getSonic(), level); // Update the GameView
        controller.updateSonic(level.getSonic()); // Change the controller to control Sonic in the new world
        level.collisionListener(level.getSonic(), this);
        level.start(); // Start the level
    }

    /**
     * Save the current state of the game.
     */
    public void save() {

        JFrame saveFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Dialog");
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Text Document (*.txt)", "txt");
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setSelectedFile(new File("gamesave.txt"));
        fileChooser.setCurrentDirectory(new File("data/Saves"));
        int userSelection = fileChooser.showSaveDialog(saveFrame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().toString();
            if (!filename.endsWith(".txt"))
                filename += ".txt";
            try {
                GameSaverLoader.save(level, filename);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * Load a previously saved state of the game.
     */
    public void load() {
        JFrame loadFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Dialog");
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Text Document (*.txt)", "txt");
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setCurrentDirectory(new File("data/Saves"));
        int userSelection = fileChooser.showOpenDialog(loadFrame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().toString();
            try {
                GameLevel level = GameSaverLoader.load(this, filename);
                setLevel(level); // Set the level
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * Sets the level with persistent bodies after loading.
     */
    public void setLevel(GameLevel level) {
        this.level.stop(); // Stop the level
        gameMusic.stop();
        this.level = level;
        // create the new level
        if (level instanceof Level1) {
            this.level = new Level1(this); // Level starts again
            // background music
            try {
                gameMusic = new SoundClip("data/level1music.wav");   // Open an audio input stream
                gameMusic.setVolume(0.005); // Set the volume
                gameMusic.loop();  // Set it to continous playback (looping)
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
            }
            view.Level1Image(); // Change the background
        }
        else if (level instanceof Level2) {
            this.level = new Level2(this); // Level starts again
            try {
                gameMusic = new SoundClip("data/level2music.wav");   // Open an audio input stream
                gameMusic.setVolume(0.005); // Set the volume
                gameMusic.loop(); // Set it to continous playback (looping)
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
            }
            view.Level2Image(); // Change the background
        }
        else if (level instanceof Level3) {
            this.level = new Level3(this); // Level starts again
            try {
                gameMusic = new SoundClip("data/level3music.wav");   // Open an audio input stream
                gameMusic.setVolume(0.005); // Set the volume
                gameMusic.loop();  // Set it to continous playback (looping)
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
            }
            view.Level3Image(); // Change the background
        }
        // debug viewer
        view.setWorld(level);
        view.setZoom(10); // Change the zoom
        view.updateView(level.getSonic(), level); // Update the GameView
        controller.updateSonic(level.getSonic()); // Change the controller to control Sonic in the new world
        level.collisionListener(level.getSonic(), this);
        level.start(); // start the level
    }

    /**
     * Run the game.
     */
    public static void main(String[] args) {

        new Game();
    }
}










































