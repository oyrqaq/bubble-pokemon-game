import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameAdvancedListener implements ActionListener {
    private GameComponent gameComponent;

    public GameAdvancedListener(GameComponent gameComponent) {
        this.gameComponent = gameComponent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        advanceOneTick();
    }

    public void advanceOneTick() {
        this.gameComponent.updateAll();
        this.gameComponent.drawAll();
    }
}
