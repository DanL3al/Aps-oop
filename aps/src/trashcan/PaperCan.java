package trashcan;

import main.GamePanel;

public class PaperCan extends TrashCan{


    public PaperCan(GamePanel gp, TrashCanManager trashCanManager,int worldX, int worldY) {
        super(gp, trashCanManager, "paper",worldX,worldY);
    }


}
