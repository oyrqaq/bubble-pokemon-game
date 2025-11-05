import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Hero extends BasisMovement{
    private double dx, dy; 
    double size, basesize;
    GameComponent component;
    private ArrayList<Level> lvs = new ArrayList<>();
    private ArrayList<Bubble> bubbles;
    private static final double MOVESPEED = 6;
    private static final double GRAVITY = 0.6;
    private static final double JUMPSPEED = -8.5*2;
    private static final double MAXFALLSPEED = 6;
    public boolean isLeft, isFront = true, invincible = false, isJump = false, isFall = false, wasOutOfBricks = true, isOutOfBricks;
    boolean up, down, left, right, attack, isMoved;
    private static final int attackCD = 30;
    private int lastAttack = attackCD;
    private boolean isKillable;
    private int invincibleTimer = 0;
    int life = 5;
    int score = 0;
    private Rectangle2D hitbox;
    private BufferedImage heroL, heroR;
    
    public Hero(GameComponent gameComponent, double basesize) {
        super(gameComponent, basesize);
        this.component = gameComponent;
        this.bubbles = component.getBubbles();
        this.lvs = this.component.bricks;
        this.basesize = basesize;
        this.size = basesize;
        this.x = 80;
        this.y = 480;
        this.hitbox = new Rectangle2D.Double(x + 10, y + 10, size - 20, size - 20);
            try {
                this.heroL = ImageIO.read(new File("Images/hero.png"));
                this.heroR = ImageIO.read(new File("Images/heroR.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }    

    public void jump() {
        if (isJump == false && isFall == false) {
            dy = JUMPSPEED;
            isFall = true;
            isJump = true;
            wasOutOfBricks = false;
        }
    }

    public void fall() {
        if (isFall == true) {
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
    
    public void updatePosition() {
        if (invincible) {
            invincibleTimer++;
            if (invincibleTimer >= 200 && isMoved) {
                invincible = false;
            }
        }
        if (up == true) {
            jump();
            isMoved = true;
            isFront = false;
        }
        if (left == true) {
            moveLeft();
            isMoved = true;
            isFront = false;
        }
        if (right == true) {
            moveRight();
            isMoved = true;
            isFront = false;
        }
        if (((left && right) == true) || ((left || right) == false)) {
            dx = 0;
        }
        isFall = true; fall();
        isOutOfBricks = (!isJump) ? true : false;
        Rectangle2D nextHerox = new Rectangle2D.Double(x + dx, y, 40, 40);
        Rectangle2D nextHeroy = new Rectangle2D.Double(x, y + dy, 40, 40);
        for (Level lv : this.lvs) {
            Shape rectLv = lv.getShape();
            if (rectLv.intersects(nextHerox)) dx = 0;
            if (rectLv.intersects(nextHeroy) && !isJump) {
                isOutOfBricks = false;
            }
        }
        wasOutOfBricks = isOutOfBricks || wasOutOfBricks || y == 40;
        for (Level lv : this.lvs) {
            if (lv.getShape().intersects(nextHeroy) && !isJump && wasOutOfBricks) {
                isFall = false;
                dy = 0;
            }
        }
        x += dx;
        y = Math.max(40, y + dy);
        hitbox = new Rectangle2D.Double(x + 15, y + 15, size - 20, size - 20);
    }
    
    public void drawOn(Graphics2D g) {
        shootBubble();
        if ((invincible && invincibleTimer % 20 >= 10) || !invincible) {
            g.drawImage((isLeft) ? heroL : heroR, (int) x, (int) y, (int) size, (int) size,
                    null);
        }
    }
    
    public void shootBubble() {
        if (lastAttack == attackCD) {
            lastAttack = attackCD;
            if (attack) {
                isMoved = true;
                isFront = false;
                lastAttack = 0;
                Bubble bubble = new Bubble(this.x, this.y, this.isLeft, this.component, size);
                this.bubbles.add(bubble);
            }
        } else {
            lastAttack = Math.min(attackCD, lastAttack + 1);
        }
    }
    
    public Shape getShape() {
        return hitbox;
    }
    
    public boolean isKillable() {
        return life == 0;
    }

    public int getScore() {
        return this.score;
    }
    
    public void die() {
        if (!invincible) {
            this.x = 80;
            this.y = 480;
            isMoved = false;
            isFront = true;
            invincibleTimer = 0;
            this.life -= 1;
            invincible = true;
        }
    }
}