package game;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.*;

/**
 * Very basic polygon editor.
 * Use the main method to launch the editor. See below for how it works.
 * 
 * THIS IS A QUICK DIRTY HACK.
 * IT IS NOT PROPERLY DOCUMENTED AND WILL NOT BE SUPPORTED. GOOD LUCK.
 * 
 * Each time a point is added, deleted or moved, a comma-separated list of coordinates
 * is printed to the console; this text can be copied and pasted as a parameter list
 * for the city.cs.engine.PolygonShape(float... coords) constructor BUT you
 * will have problems if the polygon is not convex (remember though that a Body
 * can have multiple shapes, so you can get pretty much any shaped body by using
 * overlapping convex polygons).
 * 
 * If the editor is initialised with a non-null file name parameter, the editor
 * will attempt to open the image file and display a scaled up version as
 * background (allowing you to trace a polygon approximation of the image outline).
 *
 * To add the next point to the polygon, left click where you want it. 
 * To delete an existing point, right click on it. 
 * To move a point, drag it. It is not currently possible to insert a new 
 * point between two existing points; sorry.
 * 
 * If the editor is started from the command line, the output from a previous edit
 * can be fed back in for re-editing (after the image file name, if any). The image
 * file name can also be optionally followed by a scale specifier.
 * 
 * Example 1:
 * 
 *  java PolygonEditor yellow-bird.gif -height 2.25
 * 
 * lets you draw around the yellow-bird.gif image, generating polygon vertex coordinates on the
 * assumption that the image will be scaled to a height of 2.25 world units (metres) when used
 * in the game (this can be useful if your game involves zooming the display, otherwise
 * the zoomed image will look blocky).  If you don't specify the height, it defaults to 1.
 * 
 * Example 2:
 * 
 *  java PolygonEditor yellow-bird.gif -height 2.25 0.149f,0.975f, 0.775f,0.193f, 0.772f,-0.099f, 0.401f,-0.928f, -0.36f,-0.922f, -0.719f,-0.025f, -0.725f,0.163f, -0.14f,0.972f
 * 
 * lets you edit the polygon drawn in a previous session around the image yellow-bird.gif).
 */
public class PolygonEditor extends JPanel
{   private static double WIDTH = 1000;
    private static double HEIGHT = 1000;
    private static int SCALE = 8;

    private List<Point2D.Float> points;
    private int currentVertex;

    private ImageIcon icon;
    /** height of the the icon in the world (in metres) */
    private float boxHeight;
    private int powerOf10;

    private double canvasWidth;
    private double canvasHeight;
    private double bitmapWidth;
    private double bitmapHeight;
    private double centreX, centreY;
    /** screen pixels per bitmap pixel */
    private double pixelScale;
    /** screen pixels per world metre */
    private double scale;

    /**
     * Initialise a new editor with no background image.
     */
    private PolygonEditor()
    {
        this(null, 1.0f);
    }

    private float round(float x) {
        return Math.round(powerOf10*x)/(float)powerOf10;
    }

    /**
     * Initialise a new editor with background image specified by given file name.
     * If file name is null, no image will be loaded.
     * @param f image file name
     */
    private PolygonEditor(String f, float boxHeight)
    {
        super();
        this.boxHeight = boxHeight;
        System.out.println("height = " + boxHeight);
        powerOf10 = 1;
        while (powerOf10*boxHeight < 300) {
            powerOf10 *= 10;
        }

        if (f != null) icon = new ImageIcon(f);
        if (icon == null) {
            pixelScale = SCALE;
            bitmapWidth = WIDTH/SCALE;
            bitmapHeight = HEIGHT/SCALE;
        } else {
            Image image = icon.getImage();
            int w = icon.getIconWidth();
            int h = icon.getIconHeight();
            pixelScale = Math.min(WIDTH/w, HEIGHT/h);
            bitmapWidth = w;
            bitmapHeight = h;
            image = image.getScaledInstance((int)(w * pixelScale), -1, Image.SCALE_DEFAULT);
            icon.setImage(image);
        }
        canvasWidth = (bitmapWidth * pixelScale);
        canvasHeight = (bitmapHeight * pixelScale);
        centreX = canvasWidth/2.0f;
        centreY = canvasHeight/2.0f;
        scale = boxHeight / canvasHeight;
        setPreferredSize(new java.awt.Dimension((int)canvasWidth, (int)canvasHeight));

        points = new ArrayList<Point2D.Float>();
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Point2D.Float p = toGridPoint(e.getX(), e.getY());
                currentVertex = findVertex(p);
                if (currentVertex < 0) {
                    currentVertex = points.size();
                    points.add(p);
                } else {
                    points.set(currentVertex, p);
                }
                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                String mods = e.getMouseModifiersText(e.getModifiers());
                if (!mods.equals("Button1")) {
                    points.remove(currentVertex);
                }
                currentVertex = -1;
                updateView();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point2D.Float p = toGridPoint(e.getX(), e.getY());
                points.set(currentVertex, p);
                repaint();
            }
        });
    }

    /** Find the index of a nearby vertex, or -1 if none. */
    int findVertex(Point2D.Float p) {
        double close = 5*scale;
        for (int i = 0; i < points.size(); ++i) {
            if (points.get(i).distance(p) < close) {
                return i;
            }
        }
        return -1;
    }

    /** The current list of polygon vertex coordinates as a comma-separated string. */
    public String toString()
    {
        String s = "";
        for (Point2D.Float p : points) {
            if (s.length() > 0) s += ", ";
            s += round(p.x) + "f" + "," + round(p.y) + "f";
        }
        return s;
    }

    /**
     * The unscaled point corresponding to a screen position.
     * @param x    the x-cordinate of the screen point.
     * @param y    the y-cordinate of the screen point.
     * @return the unscaled point
     */
    private Point2D.Float toGridPoint(int x, int y) {
        return new Point2D.Float((float)((x - centreX)*scale), (float)((centreY - y)*scale));
    }

    private Point2D.Float screenPosition(Point2D.Float p) {
        return new Point2D.Float((float)(centreX + p.x/scale), (float)(centreY - p.y/scale));
    }
    
    /** Draw the image (if any) and polygon. */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        for (int i=0; i<this.getHeight(); i+=5)
            for (int j=0; j<this.getWidth(); j+=5){
                if (i % 2 == 0){
                    if (j % 2 == 0)  g.setColor(Color.pink);
                    else g.setColor(Color.yellow);
                }
                else{
                    if (j % 2 == 0)  g.setColor(Color.yellow);
                    else g.setColor(Color.pink);
                }
                g.fillRect(j,i,5,5);
            }
        if (icon != null) icon.paintIcon(this, g, 0, 0);

        // draw the polygon
        g2.setColor(Color.BLUE);
        Point2D.Float prev = null;
        float r = 4;
        for (Point2D.Float gp : points) {
            Point2D.Float p = screenPosition(gp);
            g2.fill(new Ellipse2D.Float(p.x - r, p.y - r, 2*r, 2*r));
            if (prev != null) {
                g2.draw(new Line2D.Float(prev, p));
            }
            prev = p;
        }
    }
    /**
     * Open an editor.
     * If first command-line argument is not a number, assume it is an image file name.
     * The optional image file name can be optionally followed by a scale specifier
     * of the form -scale (floating-point number)
     * Any remaining arguments are assumed to be a comma-separated list of polygon vertex coordinates,
     * in the same format as the parameter list to city.soi.platform.PolygonShape(float...).
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        String fileName = "data/spring.png";
        float boxHeight = 3;
        int firstCoordIndex = 0;
        if (args.length > 0) {
            try {
                String[] test = args[0].split("[, ]+");
                Float.parseFloat(test[0]);
            } catch (NumberFormatException e) {
                fileName = args[0];
                firstCoordIndex = 1;
                if (args.length > 2 && args[1].toLowerCase().equals("-height")) {
                    boxHeight = Float.parseFloat(args[2]);
                    firstCoordIndex = 3;
                }
            }
        }
        java.util.ArrayList<String> coordStrings = new java.util.ArrayList<String>();
        for (int i = firstCoordIndex; i < args.length; i++) {
            String[] coords = args[i].split("[, ]+");
            for (String x : coords) coordStrings.add(x);
        }
        PolygonEditor editor = new PolygonEditor(fileName, boxHeight);
        if (coordStrings.size() > 0) {
            int i = 0;
            boolean gotX = false;
            float x = 0;
            float y = 0;
            while (i < coordStrings.size()) {
                try {
                    float xy = Float.parseFloat(coordStrings.get(i));
                    if (gotX) {
                        y = xy;
                        Point2D.Float p = new Point2D.Float(x, y);
                        editor.points.add(p);
                        gotX = false;
                    } else {
                        x = xy;
                        gotX = true;
                    }
                } catch (NumberFormatException e) { }
                i++;
            }
        }
        JFrame frame = new JFrame(fileName == null ? "polygon editor" : fileName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(editor);
        frame.pack();
        frame.setVisible(true);
    }

    /** Update the display and print the current coordinate list to console. */
    private void updateView()
    {
        System.out.println(this);
        repaint();
    }

}
