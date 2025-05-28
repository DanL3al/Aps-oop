package main;

import buttons.Button;
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
    private BufferedImage plasticImage,metalImage,paperImage,glassImage, trashCollected;
    private String message = "Inventory full";
    private int messageCounter;
    private int talkingMessageTimer;
    private int endingTimer;
    double milli;
    int seconds;
    int minutes;
    DecimalFormat dFormat = new DecimalFormat("#.00");
    String currentDialogue = "";
    private int commandNum = 0;
    private Button enterButtonExample;
    private Button tButtonExample;
    private Button eButtonExample;

    public UI(GamePanel gp){
        this.gp = gp;
        arial_28 = new Font("Arial",Font.PLAIN,28);
        setImage();
        enterButtonExample = new Button("enter");
        tButtonExample = new Button("t");
        eButtonExample = new Button("e");
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(arial_28);
        g2.setColor(Color.WHITE);

        if(gp.getGameState() == gp.getMenuState()){
            drawMenuScreen();
        }else if(gp.getGameState() == gp.getTutorialStateOne()){
            drawTutorialScreen();
        }else if (gp.getGameState() == gp.getTutorialStateTwo()){
            drawSecondTutorialScreen();
        }
        else if(gp.getGameState() == gp.getPauseState()){
            drawPauseScreen();
        }else if(gp.getGameState() == gp.getDialogueState()){
            drawDialogueScreen();
        }else if(gp.getGameState() == gp.getGameWonState()){
            drawEndingScreen();
        } else{

            /*g2.setColor(Color.white);
            g2.fillOval(-2, -2,gp.getTileSize() * 2 + 4, gp.getTileSize() * 2 + 4);
            g2.setColor(Color.black);
            g2.fillOval(0,0,gp.getTileSize() * 2, gp.getTileSize() * 2);
            g2.drawImage(gp.getTargetImage(),gp.getTileSize() / 2,gp.getTileSize() / 2,gp.getTileSize(),gp.getTileSize(),null);
            g2.setColor(Color.white);*/

            if(gp.getGameState() != gp.getGameWonState()){
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
            }

            if(gp.getGameState() == gp.getTalkingDoneState()){
                drawTalkingDoneScreen();
                talkingMessageTimer++;
                if(talkingMessageTimer == 180){
                    gp.setGameState(gp.getPlayState());
                }
            }

            if(gp.getTalkingDone() && talkingMessageTimer == 180){
                g2.setFont(g2.getFont().deriveFont(24f));
                g2.drawImage(gp.getGlassImage(),gp.getTileSize() / 2, gp.getTileSize() * 2, 48,48,null);
                g2.drawString(gp.getGlassCollected() + " / " + gp.getGlassRemaining(),gp.getTileSize() + 18, gp.getTileSize() * 2 + 40);

                g2.drawImage(gp.getPlasticImage(),gp.getTileSize() / 2, gp.getTileSize() * 3, 48,48,null);
                g2.drawString(gp.getPlasticCollected() +  " / "  + gp.getPlasticRemaining(),gp.getTileSize() + 18, gp.getTileSize() * 3 + 40);

                g2.drawImage(gp.getMetalImage(), gp.getTileSize() / 2, gp.getTileSize() * 4, 48,48,null);
                g2.drawString(gp.getMetalCollected() + " / " + gp.getMetalRemaining(), gp.getTileSize() + 18, (gp.getTileSize() * 4) + 40);

                g2.drawImage(gp.getPaperImage(), gp.getTileSize() / 2, gp.getTileSize() * 5, 48,48,null);
                g2.drawString(gp.getPaperCollected() + " / " + gp.getPaperRemaining(), gp.getTileSize() + 18, (gp.getTileSize() * 5) + 40);
            }



            if(gp.getInventoryFull()){
                g2.setFont(g2.getFont().deriveFont(20f));
                String txt = "Inventário Cheio, faça o descarte!";
                g2.drawString(txt, getXForCenteredText(txt), gp.getTileSize() * 8);
            }
        }
    }

    private void setImage(){
        try{
            trashCollected = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objectAssets/trash-collected.png"));
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
        String text = "Patrulha Verde";
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

    private void drawTutorialScreen(){
        g2.setColor(new Color(0,0,0));
        g2.fillRect(0,0,gp.getScreenWidth(),gp.getScreenHeight());
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));

        String text = "Você é um Guarda Municipal\nE uma forte chuva se aproxima.\n" +
                "Você receberá denuncias de pessoas que estão \njogando lixo na rua\n" +
                "Seu trabalho é coletar o lixo e \nConscientizar as pessoas\npara evitar a poluição da cidade\n" +
                "Evitando assim, o alagamento da cidade!";

        g2.setColor(Color.gray);
        int x = gp.getTileSize();
        int y = gp.getTileSize();

        for (String line : text.split("\n")){
            g2.drawString(line,x,y);
            y += 40;
        }

        y = gp.getScreenHeight() - gp.getTileSize();
        g2.drawString("Aperte Enter", x,y - 10);
        enterButtonExample.draw(g2,x + 200,y,32,32);

    }

    private void drawSecondTutorialScreen(){
        g2.setColor(new Color(0,0,0));
        g2.fillRect(0,0,gp.getScreenWidth(),gp.getScreenHeight());
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,24F));

        String text = "Quando interagir com objetos e npcs\nAperte a tecla que aparece nos icones para interagir";

        g2.setColor(Color.gray);
        int x = gp.getTileSize();
        int y = gp.getTileSize();
        for (String line : text.split("\n")){
            g2.drawString(line, x,y);
            y += 40;
        }


        y += gp.getTileSize() * 2;

        x += gp.getTileSize() * 3;
        eButtonExample.draw(g2,x,y,48,48);
        tButtonExample.draw(g2,x + gp.getTileSize() * 3,y,48,48);

        y = gp.getScreenHeight() - gp.getTileSize();
        g2.drawString("Aperte Enter", x,y - 10);
        enterButtonExample.draw(g2,x + 200,y,32,32);
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
        }
        //g2.drawString(currentDialogue,x,y);

    }

    private void drawTalkingDoneScreen(){
        int x = gp.getTileSize()*2;
        int y = gp.getTileSize()*2;
        int width = gp.getScreenWidth() - (gp.getTileSize() * 4);
        int height = gp.getScreenHeight() - (gp.getTileSize() * 4);
        drawSubWindow(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,20));
        x += gp.getTileSize();
        y += gp.getTileSize();
        String text = "Você terminou de falar com os npcs\nAgora colete os lixos espalhados pela cidade\n" +
                "e descate-os em uma lixeira adequada";
        for (String line : text.split("\n")){
            g2.drawString(line,x,y);
            y += 40;
        }
    }

    private void drawEndingScreen(){
        g2.setColor(Color.black);
        g2.fillRect(0,0,gp.getScreenWidth(),gp.getScreenHeight());
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,48));
        g2.setColor(Color.YELLOW);
        String text = "VITORIA";
        int x = getXForCenteredText(text);
        int y = gp.getScreenHeight() / 2;
        g2.drawString(text, x,y);
        g2.setColor(Color.gray);
        text = "Você conseguiu reduzir a poluição na cidade!";
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,20));
        x = getXForCenteredText(text);
        g2.drawString(text,x,y + gp.getTileSize());
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
