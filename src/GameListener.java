import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class GameListener extends JFrame implements KeyListener {
    private static final long serialVersionUID = 1L;
    private GameComponent gameComponent;

    public GameListener(GameComponent component) {
        super();
        this.gameComponent = component;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (!this.gameComponent.getisPaused()) {
            if (e.getKeyCode() == KeyEvent.VK_U) {
                if (this.gameComponent.getLevelnum() == 6) {
                    return;
                }
                this.gameComponent.setLevel(this.gameComponent.getLevelnum() + 1);
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                if (this.gameComponent.getLevelnum() == 1) {
                    return;
                }
                this.gameComponent.setLevel(this.gameComponent.getLevelnum() - 1);
            } else if(e.getKeyCode() == KeyEvent.VK_DOWN){
                this.gameComponent.hero.down = true;
            } else if(e.getKeyCode() == KeyEvent.VK_UP){
                this.gameComponent.hero.up = true;
            } else if(e.getKeyCode() == KeyEvent.VK_LEFT){
                this.gameComponent.hero.left = true;
            } else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                this.gameComponent.hero.right = true;
            } else if(e.getKeyCode() == KeyEvent.VK_SPACE){
                this.gameComponent.hero.attack = true;
            }
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (!this.gameComponent.getisPaused()) {
            if(e.getKeyCode() == KeyEvent.VK_DOWN){
                this.gameComponent.hero.down = false;
            } else if(e.getKeyCode() == KeyEvent.VK_UP){
                this.gameComponent.hero.up = false;
            } else if(e.getKeyCode() == KeyEvent.VK_LEFT){
                this.gameComponent.hero.left = false;
            } else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                this.gameComponent.hero.right = false;
            }else if(e.getKeyCode() == KeyEvent.VK_SPACE){
                this.gameComponent.hero.attack = false;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }
}