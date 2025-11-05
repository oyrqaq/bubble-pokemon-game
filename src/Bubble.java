import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Bubble{
    private static final double MOVESPEED = 3.5;
    private static final double FLOATSPEED = -3.5;
    private static final int MOVETIME = 60/2;
    private static final int FLOATTIME = 150;
    private int lifetime;
    private double x, y, dx, dy;
    private boolean isLeft;
    private Shape hitbox;
    private Shape pic;
    private ArrayList<Bubble> bubbles;
    private boolean isKillable;
    private GameComponent component;
    private double size, basesize;
    protected BufferedImage image;

    public Bubble(double x, double y, boolean isLeft, GameComponent component, double basesize) {
        this.x = x;
        this.y = y;
        this.basesize = basesize;
        this.size = basesize;
        this.component = component;
        this.isLeft = isLeft;
        this.bubbles = component.getBubbles();
        this.hitbox = new Rectangle2D.Double(this.x + 10, this.y + 10, 20, 20);
        this.pic = new Ellipse2D.Double(x, y, size, size);
    }

    public Shape getShape() {
        return pic;
    }

    public void updatePosition() {
        if (lifetime < MOVETIME) {
            this.dx = (isLeft) ? -MOVESPEED : MOVESPEED;
            this.dy = 0;
        } else {
            this.dx = 0;
            this.dy = (lifetime < MOVETIME + FLOATTIME) ? FLOATSPEED : 0;
        }
        x += dx;
        y += dy;
        x = Math.max(x, basesize * 2);
        x = Math.min(x, (30 - 2) * basesize - size);
        y = Math.max(y, basesize);
        this.pic = new Ellipse2D.Double(x, y, size, size);
        if (lifetime < MOVETIME) {
            this.hitbox = new Rectangle2D.Double(x + size / 2, y + size / 2, size / 2, size / 2);
        } else {
            this.hitbox = new Rectangle2D.Double(0, 0, 0, 0);
        }
        lifetime += 1;
    }

    public Shape getHitbox() {
        return this.hitbox;
    }

    public void drawOn(Graphics2D g) {
        updatePosition();
        try {
            this.image = ImageIO.read(new File("Images//pokeballOpen.png"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        g.drawImage(this.image, (int) this.x, (int) this.y, (int)size, (int)size, null);
    }

    public void kill() {
        this.isKillable = true;
    }

    public boolean isKillable() {
        return (lifetime > MOVETIME + FLOATTIME) || isKillable;
    }
}