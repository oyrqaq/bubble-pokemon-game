import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class Score {
    private Rectangle backpanel = new Rectangle(210, 470, 150, 40);
    private String[] names;
    private int[] scores;

    @SuppressWarnings("null")
    public Score() {
        Scanner scanner = null;
        this.names = new String[5];
        this.scores = new int[5];

        try {
            scanner = new Scanner(new File("HighScores"));
            int count = 0;
            while (scanner.hasNextLine()) {
                String next = scanner.nextLine();
                if (count < 5) {
                    this.names[count] = next;
                }
                if (count >= 5) {
                    this.scores[count - 5] = Integer.parseInt(next);
                }
                count++;
            }
        } catch (FileNotFoundException exception) {
            System.out.println("You gave a bad file name.");
        } finally {
            scanner.close();
        }
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.black);
        g2d.draw(this.backpanel);
        Font fnt0 = new Font("Arial", Font.BOLD, 40);
        g.setFont(fnt0);
        g.setColor(Color.white);
        g.fillRect(0, 0, 900, 20);
        g.fillRect(0, 720, 900, 10);
        g.fillRect(0, 530, 900, 80);
        g.drawString("High Scores", 170, 100);
        Font fnt2 = new Font("Arial", Font.BOLD, 25);
        g.setFont(fnt2);
        try {
            g.drawImage(ImageIO.read(new File("Images//pokeball.png")), 217, 485, 30, 30, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawString("<<Back", 250, 510);
        g.drawString("Rank", 30, 160);
        g.drawString("Player Name", 200, 160);
        g.drawString("Score", 430, 160);
        g.drawString("1", 40, 210);
        g.drawString("2", 40, 270);
        g.drawString("3", 40, 330);
        g.drawString("4", 40, 390);
        g.drawString("5", 40, 450);
        g.drawString(this.names[0], 220, 210);
        g.drawString(this.names[1], 220, 270);
        g.drawString(this.names[2], 220, 330);
        g.drawString(this.names[3], 220, 390);
        g.drawString(this.names[4], 220, 450);
        g.drawString(Integer.toString(this.scores[0]), 430, 210);
        g.drawString(Integer.toString(this.scores[1]), 430, 270);
        g.drawString(Integer.toString(this.scores[2]), 430, 330);
        g.drawString(Integer.toString(this.scores[3]), 430, 390);
        g.drawString(Integer.toString(this.scores[4]), 430, 450);
    }
}
