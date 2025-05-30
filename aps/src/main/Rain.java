package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Rain {

    GamePanel gp;
    BufferedImage rainImage;
    int counter = 0;
    int rainingTimer = 0;

    public Rain(GamePanel gp){
        this.gp = gp;
        setRainImage();
    }

    public void draw(Graphics2D g2){
        int state = gp.getRainingStage();
        Random random = new Random();
        Color rain = null;

        if(state == gp.getClimateChange()){
            if(rainingTimer <= 80){
                rain = new Color(144, 145, 149, 18);
            } else if (rainingTimer <= 140) {
                rain = new Color(44, 76, 211, 18);
            }else{
                rain = new Color(44, 76, 211, 31);
            }
            g2.setColor(rain);
            g2.fillRect(0,0,gp.getScreenWidth(),gp.getScreenHeight());
        }else if(state == gp.getClimateTotallyChanged()){
            if(rainingTimer <= 80){
                rain = new Color(44, 76, 211, 42);
            }else{
                rain = new Color(19, 54, 197, 52);
            }
            g2.setColor(rain);
            g2.fillRect(0,0,gp.getScreenWidth(),gp.getScreenHeight());
        }
        else if(state == gp.getWeakRain()){

            rain = new Color(19, 54, 197, 62);
            g2.setColor(rain);
            g2.fillRect(0,0,gp.getScreenWidth(),gp.getScreenHeight());
            int screenX = gp.getTileSize() * 22 - gp.getPlayerSCREENX();
            int screenY = gp.getTileSize() * 18 - gp.getPlayerSCREENY();
            if(rainingTimer >= 20){
                for (int i = 0; i < 12; i++) {
                    g2.drawImage(rainImage,random.nextInt(0,screenX),random.nextInt(0,screenY),24,24,null);
                }
                rainingTimer = 0;
            }
        }else{
            rain = new Color(19, 54, 197, 70);
            g2.setColor(rain);
            g2.fillRect(0,0,gp.getScreenWidth(),gp.getScreenHeight());
            int screenX = gp.getTileSize() * 22 - gp.getPlayerSCREENX();
            int screenY = gp.getTileSize() * 18 - gp.getPlayerSCREENY();
            for (int i = 0; i < 6; i++) {
                g2.drawImage(rainImage,random.nextInt(0,screenX),random.nextInt(0,screenY),24,24,null);
            }
        }
    }

    public void update(){
        counter++;
        if(gp.getRainingStage() == 0){
            //after 5 seconds state changes
            if(counter == 300){
                gp.setRaining(true);
                gp.setRainingStage(gp.getClimateChange());
                counter = 0;
            }
        }else if(gp.getRainingStage() == gp.getClimateChange()){
            rainingTimer++;
            if(counter == 600){
                gp.setRainingStage(gp.getClimateTotallyChanged());
                rainingTimer = 0;
                counter = 0;
            }
        } else if (gp.getRainingStage() == gp.getClimateTotallyChanged()) {
            rainingTimer++;
            if(counter == 300){
                gp.setRainingStage(gp.getWeakRain());
                counter = 0;
                rainingTimer = 0;
            }
        } else if(gp.getRainingStage() == gp.getWeakRain()){
            rainingTimer++;
            if (counter == 800){
                gp.setRainingStage(gp.getTempest());
                counter = 0;
                rainingTimer = 0;
            }
        }
    }

    private void setRainImage(){
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objectAssets/rain.png"));
        }catch (IOException e){
            e.printStackTrace();
        }

        this.rainImage = image;
    }
}
