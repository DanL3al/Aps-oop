package tile;

import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage image;
    public boolean collision = false;

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage(){
        return this.image;
    }

    public boolean getCollision(){
        return collision;
    }
}
