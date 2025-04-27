package buttons;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Event {

    BufferedImage image1, image2;
    int spriteCounter;
    int spriteNum = 1;

    public Event(){
        setImage();
    }


    public void draw(Graphics2D g2, int worldX, int worldY, int width, int height){
        spriteCounter++;
        if(spriteCounter > 18){
            if(spriteNum == 1){
                spriteNum = 2;
            }else{
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        BufferedImage image = null;

        switch (spriteNum){
            case 1:
                image = image1;
                break;
            case 2:
                image = image2;
                break;
        }
        g2.drawImage(image,worldX + 8,worldY - 8 - width,width,height,null);

    }


    private void setImage(){
        try {
            image1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("buttonassets/exclamation-button-1.png"));
            image2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("buttonassets/exclamation-button-2.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
