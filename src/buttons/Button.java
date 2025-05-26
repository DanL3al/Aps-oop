package buttons;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Button {

    BufferedImage button1,button2,button3,button4;
    int spriteCounter;
    int spriteNum = 1;
    String type;

    public Button(String type){
        this.type = type;
        getImage(this.type);
    }



    private void getImage(String type){
        String buttonPath = "";

        switch (type){
            case "e":
                buttonPath = "buttonassets/e-button-";
                break;
            case "t":
                buttonPath = "buttonassets/t-button-";
                break;
            case "enter":
                buttonPath = "buttonassets/enter-button-";
        }

        try{
            button1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream(buttonPath+ "1.png"));
            button2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream(buttonPath+ "2.png"));
            if(!this.type.equals("enter")){
                button3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream(buttonPath+ "3.png"));
                button4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream(buttonPath+ "2.png"));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2, int worldX, int worldY, int width, int height){
        spriteCounter++;
        if(spriteCounter > 10){
            if(type.equals("enter")){
                if(spriteNum == 1){
                    spriteNum = 2;
                }else{
                    spriteNum = 1;
                }
            }else{
                if(spriteNum == 1){
                    spriteNum = 2;
                }else if(spriteNum == 2){
                    spriteNum = 3;
                }else if(spriteNum == 3){
                    spriteNum = 4;
                }else if(spriteNum == 4){
                    spriteNum = 1;
                }
            }
            spriteCounter = 0;
        }

        BufferedImage image = null;

        switch (spriteNum){
            case 1:
                image = button1;
                break;
            case 2:
                image = button2;
                break;
            case 3:
                if(!this.type.equals("enter")){
                    image = button3;
                }else{
                    image = button2;
                }
                break;
            case 4:
                if(!this.type.equals("enter")){
                    image = button4;
                }else{
                    image = button1;
                }
                break;
        }

            g2.drawImage(image,worldX + 8,worldY - 8 - width,width,height,null);

    }
}
