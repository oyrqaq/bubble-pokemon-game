import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;

public class EndListener implements ActionListener {
    private String name;
    private ArrayList<JFrame> frames;
    public boolean clicked;

    public EndListener(String name) {
        this.name = name;
    }

    public EndListener(String name, ArrayList<JFrame> frames) {
        this.name = name;
        this.frames = frames;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        for (JFrame f : this.frames) {
            f.setVisible(false);
            f.dispose();
        }
        this.clicked = true;
        if (name == "restart") {
            Main.main(new String[] { "something" });
        }
    }
}