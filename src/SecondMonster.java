import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class SecondMonster{
    private static final double WIDTH = 20, HEIGHT = 20;
    private static final double MOVESPEED = 8;
    private double x, y, dx, dy;
    private boolean isLeft;
    private Shape pic;
    private Color color = Color.BLACK;
    private boolean isKillable;
    private GameComponent world;
    private ArrayList<Level> bricks;
    private Hero hero;
    private BufferedImage image;

    public SecondMonster(double centerX, double centerY, boolean isLeft, GameComponent world) {
        this.x = centerX;
        this.y = centerY;
        this.isLeft = isLeft;
        pic = new Rectangle2D.Double(x,y,WIDTH,HEIGHT);
        this.world = world;
        this.bricks = world.getLevels();
        this.hero = world.getHero();
        try {
            this.image = ImageIO.read(new File("Images//light.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Shape getShape() {
        return pic;
    }

    public void updatePosition() {
        x += (isLeft) ? -MOVESPEED : MOVESPEED;
        for (Level brick : bricks)
        if(pic.intersects((Rectangle2D) brick.getShape())) {
            isKillable = true;
        }
        if(pic.intersects((Rectangle2D) hero.getShape())) {
            isKillable = true;
            hero.die();
        }
        pic = new Rectangle2D.Double(x,y,WIDTH,HEIGHT);
    }
    
    public Shape getHitbox() {
        return this.pic;
    }

    public void drawOn(Graphics2D g) throws IOException {
        updatePosition();
        g.drawImage(this.image, (int) x, (int) y, 20, 20, null);
    }

    public void kill() {
        this.isKillable = true;
    }

    public boolean isKillable() {
        return isKillable;
    }
}