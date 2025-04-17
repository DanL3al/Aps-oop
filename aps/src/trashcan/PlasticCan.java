package trashcan;

import main.GamePanel;

import java.awt.*;

public class PlasticCan extends TrashCan{

    String type = "plastic";

    public PlasticCan(GamePanel gp) {
        super(gp, gp.getTrashCanManager(),"plastic");
    }

}
