import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Level {
    private double size;
    private int row, col;
    private double x,y;
    private Rectangle2D rect;
    
    public Level(int row, int col, double size) {
        this.row = row;
        this.col = col;
        this.size = size;
        this.y = row*size;
        this.x = col*size;        
        this.rect = new Rectangle2D.Double(x,y,size,size);
    }
    
    public void drawOn(Graphics2D g) throws IOException {
        g.drawImage(ImageIO.read(new File("Images/Brown.png")), (int) x, (int) y, 40, 40, null);
    }
    
    public Shape getShape() {
        return rect;
    }
}