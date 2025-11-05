import javax.swing.Timer;

public class Main {
    protected static GameComponent component;
    public static enum STATE {
        MENU, GAME, HELP, SCORE
    }
    
    public static void main(String[] args) {
        component = new GameComponent(1);
        GameComponent.State = GameComponent.STATE.MENU;
        component.setWindow();
        GameAdvancedListener advancedListener = new GameAdvancedListener(component);
        Timer timer = new Timer(0, advancedListener);
        timer.start();
    }
}