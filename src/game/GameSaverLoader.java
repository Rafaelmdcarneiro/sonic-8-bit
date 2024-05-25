package game;

import city.cs.engine.DynamicBody;
import city.cs.engine.StaticBody;
import org.jbox2d.common.Vec2;

import java.io.*;

/**
 * Advanced game saver and loader.
 */
public class GameSaverLoader {

    /**
     * Save the game.
     */
    public static void save(GameLevel level, String file)
            throws IOException {
        boolean append = true;
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, append);
            writer.write(level.getLevel() + "\n");

            for (DynamicBody body : level.getDynamicBodies()) {
                if (body instanceof Sonic) {
                    writer.write("Sonic,"
                            + body.getPosition().x + ","
                            + body.getPosition().y + ","
                            + level.getSonic().ringCount + ","
                            + level.getSonic().lives + ","
                            + level.getSonic().megaringCount + "\n");
                } else if (body instanceof Ring) {
                    writer.write("Rings,"
                            + body.getPosition().x + ","
                            + body.getPosition().y + "\n");
                } else if (body instanceof Megaring) {
                    writer.write("Megaring,"
                            + body.getPosition().x + ","
                            + body.getPosition().y + "\n");
                }
            }
            for (StaticBody body : level.getStaticBodies()) {

                if (body instanceof MegaringSpring) {
                    writer.write("MegaringSpring,"
                            + body.getPosition().x + ","
                            + body.getPosition().y + "\n");
                } else if (body instanceof Goalpost) {
                    writer.write("Goalpost,"
                            + body.getPosition().x + ","
                            + body.getPosition().y + "\n");
                }
            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Load the game.
     */
    public static GameLevel load(Game game, String file)
            throws IOException {
        FileReader fr = null;
        BufferedReader reader = null;
        try {
            System.out.println("Reading " + file + " ...");
            fr = new FileReader(file);
            reader = new BufferedReader(fr);
            String line = reader.readLine();

            GameLevel level = null;
            if (line.equals("Level1")) {
                level = new Level1(game);
            }

            else if (line.equals("Level2")) {
                level = new Level2(game);
            }

            else if (line.equals("Level3")) {
                level = new Level3(game);
            }

            line = reader.readLine();
            while (line != null) {
                System.out.println("Reading!");
                String[] tokens = line.split(",");

                if (tokens[0].equals("Sonic")) {
                    Sonic sonic = new Sonic(level);
                    Float xPos = Float.parseFloat(tokens[1]);
                    Float yPos = Float.parseFloat(tokens[2]);
                    level.setSonic(sonic);
                    sonic.setPosition(new Vec2(xPos, yPos));
                    int ringCount = Integer.parseInt(tokens[3]);
                    int livesCount = Integer.parseInt(tokens[4]);
                    int megaringCount = Integer.parseInt(tokens[5]);
                    sonic.ringCount = ringCount;
                    sonic.lives = livesCount;
                    sonic.megaringCount = megaringCount;
                }
                else if (tokens[0].equals("Rings")) {
                    Ring ring = new Ring(level);
                    Float xPos = Float.parseFloat(tokens[1]);
                    Float yPos = Float.parseFloat(tokens[2]);
                    ring.setPosition(new Vec2(xPos, yPos));
                }
                else if (tokens[0].equals("Megaring")) {
                    Megaring megaring = new Megaring(level);
                    Float xPos = Float.parseFloat(tokens[1]);
                    Float yPos = Float.parseFloat(tokens[2]);
                    level.setMegaring(megaring);
                    megaring.setPosition(new Vec2(xPos, yPos));
                }
                else if (tokens[0].equals("MegaringSpring")) {
                    MegaringSpring megaringSpring = new MegaringSpring(level);
                    Float xPos = Float.parseFloat(tokens[1]);
                    Float yPos = Float.parseFloat(tokens[2]);
                    level.setMegaringSpring(megaringSpring);
                    megaringSpring.setPosition(new Vec2(xPos, yPos));
                }
                else if (tokens[0].equals("Goalpost")) {
                    Goalpost goalpost = new Goalpost(level);
                    Float xPos = Float.parseFloat(tokens[1]);
                    Float yPos = Float.parseFloat(tokens[2]);
                    level.setGoalpost(goalpost);
                    goalpost.setPosition(new Vec2(xPos, yPos));
                }
                line = reader.readLine();
            }

            System.out.println("Finished loading!");
            return level;

        } finally {
            if (reader != null) {
                reader.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
    }
}