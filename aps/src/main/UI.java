package main;

import objects.ObjectManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

public class UI {

    private GamePanel gp;
    private Graphics2D g2;
    private Font arial_28;
    private BufferedImage plasticImage,metalImage,paperImage,glassImage;
    private String message = "Inventory full";
    private int messageCounter;
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp){
        this.gp = gp;
        arial_28 = new Font("Arial",Font.PLAIN,28);
        setImage();
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(arial_28);
        g2.setColor(Color.WHITE);

        if(gp.getGameState() == gp.getPauseState()){
            drawPauseScreen();

        }else{
            g2.drawImage(plasticImage, gp.getTileSize() / 2, gp.getTileSize() / 2, gp.getTileSize(), gp.getTileSize(), null);
            g2.drawString(" x " + gp.getPlasticCollected(),74,65);

            playTime += (double)1/60;
            g2.drawString("Time: " + dFormat.format(playTime), gp.getTileSize()*11, 65);

            if(gp.getInventoryFull()){
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gp.getTileSize() / 2, gp.getTileSize() * 5);
            }
        }
    }


    private void setImage(){
        try{
            plasticImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objectAssets/soda-can-2.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.getScreenHeight() / 2;

        g2.drawString(text,x,y);
    }

    private int getXForCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        return gp.getScreenWidth() / 2 - length/2;
    }
}
