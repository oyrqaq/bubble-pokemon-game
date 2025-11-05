import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener{
    
    public void mouseClicked(MouseEvent arg0) {
    }

    public void mouseEntered(MouseEvent arg0) {
    }

    public void mouseExited(MouseEvent arg0) {
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if (GameComponent.State == GameComponent.STATE.SCORE) {
            if (mx > 210 && mx < 360) {
                if (my > 470 && my < 510) {
                    GameComponent.State = GameComponent.STATE.MENU;
                }
            }
        }
        if (GameComponent.State == GameComponent.STATE.HELP) {
            if (mx != 0 && my != 0) {
                GameComponent.State = GameComponent.STATE.MENU;
            }
        }
        if (GameComponent.State == GameComponent.STATE.MENU) {
            if (my > 340 && my < 410) {
                if (mx > 50 && mx < 150) {
                    GameComponent.State = GameComponent.STATE.GAME;
                }
                if (mx > 385 && mx < 485) {
                    GameComponent.State = GameComponent.STATE.HELP;
                }
                if (mx > 215 && mx < 315) {
                    System.exit(0);
                }
            }
            if (mx > 400 && mx < 520) {
                if (my > 430 && my < 470) {
                    GameComponent.State = GameComponent.STATE.SCORE;
                }
            }
        }
    }

    public void mouseReleased(MouseEvent arg0) {
    }
}