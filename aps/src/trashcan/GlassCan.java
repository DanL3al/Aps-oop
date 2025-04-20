package trashcan;

import main.GamePanel;

public class GlassCan extends TrashCan{
    public GlassCan(GamePanel gp, TrashCanManager trashCanManager, int worldX, int worldY) {
        super(gp, trashCanManager, "glass",worldX,worldY);
    }
}
