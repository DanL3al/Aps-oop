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
    double milli;
    int seconds;
    int minutes;
    DecimalFormat dFormat = new DecimalFormat("#.00");
    String currentDialogue = "";
    private int commandNum = 0;

    public UI(GamePanel gp){
        this.gp = gp;
        arial_28 = new Font("Arial",Font.PLAIN,28);
        setImage();
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(arial_28);
        g2.setColor(Color.WHITE);

        if(gp.getGameState() == gp.getMenuState()){
            drawMenuScreen();
        }else if(gp.getGameState() == gp.getPauseState()){
            drawPauseScreen();
        }else if(gp.getGameState() == gp.getDialogueState()){
            drawDialogueScreen();
        }else{

            g2.setColor(Color.white);
            g2.fillOval(-2, -2,gp.getTileSize() * 2 + 4, gp.getTileSize() * 2 + 4);
            g2.setColor(Color.black);
            g2.fillOval(0,0,gp.getTileSize() * 2, gp.getTileSize() * 2);
            g2.drawImage(gp.getTargetImage(),gp.getTileSize() / 2,gp.getTileSize() / 2,gp.getTileSize(),gp.getTileSize(),null);
            g2.setColor(Color.white);


            milli += (double)1/60;
            if(milli >= 1){
                seconds++;
                milli = 0;
            }if(seconds >= 60){
                minutes++;
                seconds = 0;
            }
            if(minutes <= 0){
                g2.drawString(seconds + dFormat.format(milli), gp.getTileSize() * 12, 65);
            }else{
                g2.drawString(minutes + "." + seconds + dFormat.format(milli),gp.getTileSize() * 12, 65);
            }

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

    private void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.getScreenHeight() / 2;

        g2.drawString(text,x,y);
    }

    private void drawMenuScreen(){
        g2.setColor(new Color(0,0,0));
        g2.fillRect(0,0,gp.getScreenWidth(),gp.getScreenHeight());
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,66F));
        String text = "Escombros da memória";
        int x = getXForCenteredText(text);
        int y = gp.getTileSize() * 3;

        g2.setColor(Color.gray);
        g2.drawString(text,x+5,y+5);


        g2.setColor(Color.white);
        g2.drawString(text,x,y);

        x = gp.getScreenWidth() / 2 - (gp.getTileSize()*2)/2;
        y+= gp.getTileSize()*2;
        g2.drawImage(gp.getPlayer().getDown1(),x,y,gp.getTileSize()*2,gp.getTileSize()*2,null);


        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,48F));

        text = "New Game";
        x = getXForCenteredText(text);
        y += gp.getTileSize()*3;
        g2.drawString(text,x,y);
        if(commandNum == 0){
            g2.drawString(">",x-gp.getTileSize(),y);
        }

        text = "Quit";
        x = getXForCenteredText(text);
        y += gp.getTileSize();
        g2.drawString(text,x,y);
        if(commandNum == 1){
            g2.drawString(">",x-gp.getTileSize(),y);
        }

    }

    private int getXForCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        return gp.getScreenWidth() / 2 - length/2;
    }

    private void drawDialogueScreen(){
        int x = gp.getTileSize()*2;
        int y = gp.getTileSize()/2;
        int width = gp.getScreenWidth() - (gp.getTileSize() * 4);
        int height = gp.getTileSize() * 5;
        drawSubWindow(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,20));
        x += gp.getTileSize();
        y += gp.getTileSize();
        for (String line: currentDialogue.split("\n")){
            g2.drawString(line,x,y);
            y += 40;
            x -= 5;
        }

    }

    private void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0,0,0,220);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);
        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public void setCommandNum(int num){
        this.commandNum = num;
    }

    public int getCommandNum() {
        return commandNum;
    }

    public void setCurrentDialogue(String dialogue){
        this.currentDialogue = dialogue;
    }
}
