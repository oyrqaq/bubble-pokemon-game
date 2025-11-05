import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Trap {
    private GameComponent world;
    private static final double FLOATSPEED = -2;
    private static final int FLOATTIME = 450;
    private double x, y, dx, dy;
    private Color color = Color.RED;
    private boolean shootable, isKillable;
    private ArrayList<Trap> traps;
    private ArrayList<Fruit> fruits;
    private int lifetime;
    private Shape hitbox;
    private Shape pic;
    private Hero hero;
    private double size, basesize;
    private BufferedImage pokeball;

    public Trap(double x, double y, boolean shootable, GameComponent world, double basesize) {
        this.x = x;
        this.y = y;
        this.world = world;
        this.basesize = basesize;
        this.size = basesize;
        this.shootable = shootable;
        this.traps = world.getTraps();
        this.traps.add(this);
        this.dx = 0;
        this.dy = FLOATSPEED;
        this.world = world;
        this.hero = world.getHero();
        this.fruits = world.getFruits();
        try {
            this.pokeball = ImageIO.read(new File("Images//pokeball.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updatePosition() {
        y = Math.max(y + dy, basesize);
        this.pic = new Ellipse2D.Double(x, y, size, size);
        this.hitbox = new Rectangle2D.Double(this.x, this.y, this.size, this.size);
        if (lifetime > 300) {
            if (lifetime%30==0)
                try {
                    this.pokeball = ImageIO.read(new File("Images//pokeball.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (lifetime%30==15) this.pokeball = null;
        }
        lifetime++;
        if (this.hero.getShape().intersects((Rectangle2D) this.hitbox)) {
            isKillable = true;
            Fruit fruit = new Fruit(x, y, world, basesize, shootable);
            fruits.add(fruit);
        }
        if (lifetime == FLOATTIME) {
            Monster monster = new Monster(x, y, world, shootable, basesize);
            world.getMonsters().add(monster);
        }
    }

    public void drawOn(Graphics2D g) {
        updatePosition();
        g.drawImage(pokeball, (int) x, (int) y, (int) size, (int) size, null);
    }

    public boolean isKillable() {
        return (lifetime > FLOATTIME) || isKillable;
    }
}