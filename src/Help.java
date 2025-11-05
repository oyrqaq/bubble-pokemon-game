import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Help {
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Font fnt0 = new Font("Arial", Font.BOLD, 40);
        g.setFont(fnt0);
        g.setColor(Color.white);
        g.drawString("HELP", 225, 100);
        g2.fillRect(0, 0, 900, 20);
        g2.fillRect(0, 720, 900, 10);
        g2.fillRect(0, 530, 900, 80);
        Font fnt2 = new Font("Arial", Font.BOLD, 25);
        g.setFont(fnt2);
        g.drawString("up arrrow - jump ", 50, 180);
        g.drawString("left arrow - left ", 50, 250);
        g.drawString("right arrow - right ", 50, 320);
        g.drawString("space - shoot bubble ", 50, 390);
        g.drawString("U - go to upper level ", 300, 180);
        g.drawString("D - go to lower level ", 300, 250);
        g.drawString("P - pause / restart ", 300, 320);
        g.drawString("Click Anywhere to Back", 145, 500);
    }
}