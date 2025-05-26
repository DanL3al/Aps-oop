package trashcan;

import main.GamePanel;

public class MetalCan extends TrashCan{
    public MetalCan(GamePanel gp, TrashCanManager trashCanManager,int worldX, int worldY) {
        super(gp, trashCanManager, "metal",worldX,worldY);
    }
}
