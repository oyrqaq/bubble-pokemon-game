import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class BasisMovement {
    protected double size;
    protected int lvnum;
    protected double x, y;
    protected double width = 40;
    protected double height = 40;
    protected Point2D.Double position;
    protected boolean dead = false;
    protected int score, direction;
    protected Color color;
    protected Rectangle.Double shape;
    protected File file;
    protected BufferedImage image;
    protected int vx, vy;

    public BasisMovement(GameComponent component, double size) {
        this.lvnum = component.getLevelnum();
        this.size = size;
        this.position = new Point2D.Double(this.x, this.y);
        this.shape = new Rectangle.Double(this.x, this.y, this.width, this.height);
    }
    
    public void drawOn(Graphics2D g2) {
        if (!this.dead) {
            try {
                this.image = ImageIO.read(this.file);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            g2.drawImage(this.image, (int) this.x, (int) this.y, (int)this.width, (int)this.height, null);
        }
    }

    public Point2D getPosition() {
        return this.position;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public Shape getShape() {
        return this.shape;
    }

    public void setShape(Rectangle.Double newshape) {
        this.shape = newshape;
    }

    public void updatePosition() {
        this.position = new Point2D.Double(this.x, this.y);
        this.shape = new Rectangle.Double(this.x, this.y, this.width, this.height);
    }

    public void die() {
        this.dead = true;
    }
}