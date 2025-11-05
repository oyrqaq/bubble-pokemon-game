import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Monster{
    private ArrayList<Level> bricks;
    private ArrayList<Bubble> bubbles;
    private GameComponent world;
    private double size, basesize;
    private static final double MOVESPEED = 4;
    private static final double GRAVITY = 0.6;
    private static final double JUMPSPEED = -8.5 *2;
    private static final double MAXFALLSPEED = 5;
    private static final int attackCD = 40;
    private double x, y, dx, dy;
    private boolean isLeft, isJump, isFalling, wasOutOfBricks = true, isOutOfBricks;
    private Rectangle2D rect;
    private int lastAttack = attackCD;
    private int timer = 0;
    private boolean isKillable;
    private Hero hero;
    private ArrayList<SecondMonster> smonsters;
    private BufferedImage monsterL, monsterR;
    boolean up, down, left, right, attack, shootable;

    public Monster(double x, double y, GameComponent world, boolean shootable, double basesize) {
        this.x = x;
        this.y = y;
        this.basesize = basesize;
        this.size = basesize;
        this.world = world;
        this.bricks = world.getLevels();
        this.bubbles = world.getBubbles();
        this.hero = world.hero;
        this.smonsters = world.getSmonsters();
        this.rect = new Rectangle2D.Double(x, y, size, size);
        this.timer = 20;
        this.shootable = shootable;
        try {
            this.monsterL = ImageIO.read(new File((shootable) ? "Images//raichuL.png" : "Images//pikachuL.png"));
            this.monsterR = ImageIO.read(new File((shootable) ? "Images//raichuL.png" : "Images//pikachuR.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Monster(int row, int col, GameComponent world, boolean shootable, double basesize) {
        this.basesize = basesize;
        this.size = basesize;
        this.x = size / 2 * col;
        this.y = size / 2 * row;
        this.world = world;
        this.bricks = world.getLevels();
        this.bubbles = world.getBubbles();
        this.smonsters = world.getSmonsters();
        this.hero = world.hero;
        this.rect = new Rectangle2D.Double(x, y, size, size);
        this.timer = 20;
        this.shootable = shootable;
        try {
            this.monsterL = ImageIO.read(new File((shootable) ? "Images//raichuL.png" : "Images//pikachuL.png"));
            this.monsterR = ImageIO.read(new File((shootable) ? "Images//raichuR.png" : "Images//pikachuR.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void jump() {
        if (isJump == false && isFalling == false) {
            dy = JUMPSPEED;
            isFalling = true;
            isJump = true;
            wasOutOfBricks = false;
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

    public void moveLeft() {
        isLeft = true;
        dx = -MOVESPEED;
    }

    public void moveRight() {
        isLeft = false;
        dx = MOVESPEED;
    }

    boolean getRandom(double prob) {
        return Math.random() < prob;
    }

    public void randomRun() {
        if (timer == 30) {
            up = getRandom(0.1);
            left = getRandom(0.3);
            right = getRandom(0.3);
            attack = getRandom(0.3);
            timer = 0;
        }
    }

    public void udpatePosition() {
        randomRun();
        if (up == true) {
            jump();
        }
        if (left == true) {
            moveLeft();
        }
        if (right == true) {
            moveRight();
        }
        if (((left && right) == true) || ((left || right) == false)) {
            dx = 0;
        }
        isFalling = true;
        fall();
        isOutOfBricks = (!isJump) ? true : false;
        Rectangle2D rectx = new Rectangle2D.Double(x + dx, y, size, size);
        Rectangle2D recty = new Rectangle2D.Double(x, y + dy + size - 1, size, 1);
        for (Level brick : bricks) {
            Shape rectBrick = brick.getShape();
            if (rectBrick.intersects(rectx))
                dx = 0;
            if (rectBrick.intersects(recty) && !isJump) {
                isOutOfBricks = false;
            }
        }
        wasOutOfBricks = isOutOfBricks || wasOutOfBricks || y == basesize;
        for (Level brick : bricks) {
            if (brick.getShape().intersects(recty) && !isJump && wasOutOfBricks) {
                isFalling = false;
                dy = 0;
            }
        }
        x += dx;
        y = Math.max(basesize, y + dy);
        rect = new Rectangle2D.Double(x, y, size, size);
        timer += 1;
        for (Bubble bubble : bubbles) {
            if (bubble.getHitbox().intersects(rect)) {
                bubble.kill();
                isKillable = true;
                Trap trap = new Trap(x, y, shootable, world, basesize);
            }
        }
        if (rect.intersects((Rectangle2D) hero.getShape())) {
            hero.die();
        }
    }

    public void drawOn(Graphics2D g) {
        udpatePosition();
        if (this.shootable)
            shootBubble();
        g.drawImage((isLeft) ? monsterL : monsterR, (int) x, (int) y, (int) size, (int) size, null);
    }
    
    public void shootBubble() {
        if (lastAttack == attackCD) {
            lastAttack = attackCD;
            if (attack) {
                lastAttack = 0;
                SecondMonster smonster = new SecondMonster(rect.getCenterX(), rect.getCenterY(), isLeft, world);
                this.smonsters.add(smonster);
            }
        } else {
            lastAttack = Math.min(attackCD, lastAttack + 1);
        }
    }

    public boolean isKillable() {
        return isKillable;
    }
}