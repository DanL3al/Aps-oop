package keyhandler;

import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private GamePanel gp;
    private boolean upPressed,downPressed,leftPressed,rightPressed,ePressed,tPressed;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //PLAY STATE
        if(gp.getGameState() == gp.getPlayState()){
            if(code == KeyEvent.VK_W){
                upPressed = true;
            }else if(code == KeyEvent.VK_S){
                downPressed = true;
            }else if(code == KeyEvent.VK_A){
                leftPressed = true;
            }else if(code == KeyEvent.VK_D){
                rightPressed = true;
            }else if(code == KeyEvent.VK_E){
                ePressed = true;
            }else if(code == KeyEvent.VK_T){
                tPressed = true;
            }else if(code == KeyEvent.VK_P){
                gp.setGameState(gp.getPauseState());
            }
        }
        //PAUSE STATE
        else if(gp.getGameState() == gp.getPauseState()){
            if(code == KeyEvent.VK_P){
                gp.setGameState(gp.getPlayState());
            }
        }
        //DIALOGUE STATE
        else if(gp.getGameState() == gp.getDialogueState()){
            if(code == KeyEvent.VK_T){
                gp.setGameState(gp.getPlayState());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            upPressed = false;
        }else if(code == KeyEvent.VK_S){
            downPressed = false;
        }else if(code == KeyEvent.VK_A){
            leftPressed = false;
        }else if(code == KeyEvent.VK_D){
            rightPressed = false;
        }else if(code == KeyEvent.VK_E){
            ePressed = false;
        }else if(code == KeyEvent.VK_T){
            tPressed = false;
        }
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isePressed() {
        return ePressed;
    }

    public boolean istPressed() {
        return tPressed;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
