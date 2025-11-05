import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Menu {
    private Rectangle playButton = new Rectangle(50, 340, 100, 70);
    private Rectangle quitButton = new Rectangle(215, 340, 100, 70);
    private Rectangle selectButton = new Rectangle(385, 340, 100, 70);
    private Rectangle helpButton = new Rectangle(400, 430, 120, 40);
    private Rectangle scoreButton = new Rectangle(400, 480, 120, 40);

    public void render(Graphics g) throws IOException {
        Graphics2D g2 = (Graphics2D) g;
        g.setFont(new Font("TimesRoman", Font.BOLD, 100));
        g.setColor(Color.black);
        g.drawImage(ImageIO.read(new File("Images/BBLogo.png")), 140, 50, 300, 290, null);
        g.drawImage(ImageIO.read(new File("Images/PokemonLogo.png")), 240, 0, 220, 200, null);
        g.drawImage(ImageIO.read(new File("Images/heroR.png")), 120, 240, 70, 70, null);
        g.drawImage(ImageIO.read(new File("Images/pikachuL.png")), 400, 95, 80, 80, null);
        
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        g2.draw(this.playButton);
        g2.draw(this.helpButton);
        g2.draw(this.quitButton);
        g2.draw(this.scoreButton);
        g2.draw(this.selectButton);
        
        g.drawImage(ImageIO.read(new File("Images/pokeball.png")), 40, 360, 30, 30, null);
        g.drawImage(ImageIO.read(new File("Images/pokeball.png")), 205, 360, 30, 30, null);
        g.drawImage(ImageIO.read(new File("Images/pokeball.png")), 375, 360, 30, 30, null);
        g.drawImage(ImageIO.read(new File("Images/pokeball.png")), 395, 440, 20, 20, null);
        
        g.setColor(Color.white);
        g.drawString("PLAY", this.playButton.x + 25, this.playButton.y + 45);
        g.drawString("QUIT", this.quitButton.x + 25, this.quitButton.y + 45);
        g.drawString("HELP", this.selectButton.x + 25, this.selectButton.y + 45);

        g.setFont(new Font("Ink Free", Font.BOLD, 15));
        g.drawString("HIGH SCORES", this.helpButton.x + 20, this.helpButton.y + 25);
        
        g2.fillRect(0, 0, 900, 20);
        g2.fillRect(0, 720, 900, 10);
        g2.fillRect(0, 530, 900, 80);
        
        g.setColor(Color.black);
    }
}