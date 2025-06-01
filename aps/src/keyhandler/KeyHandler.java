package keyhandler;

import main.GamePanel;
import main.UI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private GamePanel gp;
    private UI ui;
    private boolean upPressed,downPressed,leftPressed,rightPressed,ePressed,tPressed;

    public KeyHandler(GamePanel gp, UI ui){
        this.gp = gp;
        this.ui = ui;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();


        //MENU STATE
        if(gp.getGameState() == gp.getMenuState()){
            if(code == KeyEvent.VK_W){
                int num = ui.getCommandNum() - 1;
                if(num < 0){
                    num = 1;
                }
                ui.setCommandNum(num);
            }else if(code == KeyEvent.VK_S){
                int num = ui.getCommandNum() + 1;
                if(num > 1){
                    num = 0;
                }
                ui.setCommandNum(num);
            }else if(code == KeyEvent.VK_ENTER){
                if(ui.getCommandNum() == 0){
                    gp.setGameState(gp.getTutorialStateOne());
                }else{
                    System.exit(0);
                }
            }
        }

        // TUTORIAL STATE ONE
        else if (gp.getGameState() == gp.getTutorialStateOne()) {
            if(code == KeyEvent.VK_ENTER){
                gp.setGameState(gp.getTutorialStateTwo());
            }
        }


        // TUTORIAL STATE TWO
        else if(gp.getGameState() == gp.getTutorialStateTwo()){
           if(code == KeyEvent.VK_ENTER){
               gp.setGameState(gp.getTutorialStateThree());
            }
        }


        //TUTORIAL STATE THREE
        else if (gp.getGameState() == gp.getTutorialStateThree()) {
            if(code == KeyEvent.VK_ENTER){
                gp.setGameState(gp.getPlayState());
            }
        }

        //PLAY STATE
        else if(gp.getGameState() == gp.getPlayState() || gp.getGameState() == gp.getSpectatingState() || gp.getGameState() == gp.getTalkingDoneState()){
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
