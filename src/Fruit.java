import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Fruit{
    private double size, basesize;
    private GameComponent world;
    private double x, y, dx, dy;
    private static final double GRAVITY = 0.4;
    private static final double MAXFALLSPEED = 6;
    private ArrayList<Level> bricks;
    private Hero hero;
    private Shape hitbox = new Rectangle2D.Double(0, 0, 0, 0);
    private Shape pic;
    private int score = 200;
    private boolean isFalling = true;
    private boolean isJump = true;
    private boolean isKillable, isGot;
    private int lifetime, gotTime;
    private BufferedImage fruit;

    public Fruit(double x, double y, GameComponent world, double basesize, boolean shootable) {
        this.x = x;
        this.y = y;
        this.basesize = basesize;
        this.size = basesize;
        this.dy = -Math.random() * 2;
        this.dx = (Math.random() - 0.5) * 3;
        this.world = world;
        this.score = (shootable) ? score * 2 : score;
        this.bricks = world.getLevels();
        this.hero = world.getHero();
        try {
            this.fruit = ImageIO.read(new File("Images//Star.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fall() {
        if (isFalling == true) {
            dy = Math.min(dy + GRAVITY, MAXFALLSPEED);
        } else {
            if (isJump == false)
                dy = 0;
        }
        if (dy > 0)
            isJump = false;
    }

    public void updatePosition() {
        fall();
        if (isGot) {
            gotTime++;
        }
        if (this.y < size || isJump) {
            if (x <= size || x >= 28 * basesize - size) {
                dx = -dx;
            }
        } else{
            Rectangle2D rectx = new Rectangle2D.Double(x + dx, y, size, size);
            Rectangle2D recty = new Rectangle2D.Double(x, y + dy + size, size, 0.1);
            for (Level brick : bricks) {
                if (brick.getShape().intersects(rectx)) {
                    dx = -dx;
                }
                if (brick.getShape().intersects(recty) && !isJump) {
                    dy = 0;
                    dx = 0;
                    isFalling = false;
                }
            }
        }
        x += dx;
        y += dy;
        x = Math.max(2 * basesize, x);
        x = Math.min(28 * basesize - size, x);
        if (!isFalling) {
            hitbox = new Rectangle2D.Double(x, y, size, size);
            if (hero.getShape().intersects((Rectangle2D) hitbox))
                this.isGot = true;
        }
        pic = new Rectangle2D.Double(x, y, size, size);
        lifetime += 1;
    }

    public void drawOn(Graphics2D g) {
        updatePosition();
        if (!isGot) {
            if (lifetime < 300 || lifetime % 20 >= 10) {
                g.drawImage(fruit, (int) x, (int) y, (int) size, (int) size, null);
            }
        }
    }

    public int getScore() {
        return (isGot && gotTime == 1) ? score + 400 - lifetime : 0;
    }

    public boolean isKillable() {
        return (gotTime == 0 && lifetime > 400) || gotTime > 80;
    }
}