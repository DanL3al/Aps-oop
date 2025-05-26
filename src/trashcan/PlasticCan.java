package trashcan;

import main.GamePanel;

import java.awt.*;

public class PlasticCan extends TrashCan{

    String type = "plastic";

    public PlasticCan(GamePanel gp,TrashCanManager trashCanManager,int worldX, int worldY) {
        super(gp, trashCanManager,"plastic",worldX,worldY);
    }

}
