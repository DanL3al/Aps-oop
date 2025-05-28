package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int[][] mapTileNum;


    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.getMAXWORLDCOL()][gp.getMAXWORLDROW()];
        getTileImage();
        loadMap("/maps/world01.txt");
    }

    private void getTileImage(){
        setup(0,"grass",false);
        setup(1,"wall",true);
        setup(2,"water",true);
        setup(3,"earth",false);
        setup(4,"tree",true);
        setup(5,"sand",false);
        /*setup(0,"grama",false);
        setup(1,"pedra",false);*/
    }

    private void setup(int index,String imageName,boolean collision){
        UtilityTool uTool = new UtilityTool();
        try{
            tile[index] = new Tile();
            tile[index].setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/" + imageName + ".png")));
            tile[index].setImage(uTool.scaledImage(tile[index].getImage(),gp.getTileSize(), gp.getTileSize()));
            tile[index].collision = collision;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        int worldCol = 0;
        int worldRow = 0;
        int screenX = 0,screenY = 0;

        while(worldCol < gp.getMAXWORLDCOL() && worldRow < gp.getMAXWORLDROW()){
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.getTileSize();
            int worldY = worldRow * gp.getTileSize();

            if(gp.getGameState() != gp.getSpectatingState()){
                screenX = worldX - gp.getPlayerWorldX() + gp.getPlayerSCREENX();
                screenY = worldY - gp.getPlayerWorldY() + gp.getPlayerSCREENY();
            }else{
                screenX = worldX - gp.getTarget().getWorldX() + gp.getTarget().getSCREENX();
                screenY = worldY - gp.getTarget().getWorldY() + gp.getTarget().getSCREENY();
            }

            if(gp.getGameState() != gp.getSpectatingState()){
                if(     worldX + gp.getTileSize() > gp.getPlayerWorldX() - gp.getPlayerSCREENX()&&
                        worldX - gp.getTileSize()< gp.getPlayerWorldX() + gp.getPlayerSCREENX()&&
                        worldY + gp.getTileSize()> gp.getPlayerWorldY() - gp.getPlayerSCREENY()&&
                        worldY - gp.getTileSize()< gp.getPlayerWorldY() + gp.getPlayerSCREENY()){
                    g2.drawImage(tile[tileNum].getImage(),screenX,screenY, null);
                }
            }else{
                if(worldX + gp.getTileSize()> gp.getTarget().getWorldX() - gp.getTarget().getSCREENX()&&
                        worldX - gp.getTileSize()< gp.getTarget().getWorldX() + gp.getTarget().getSCREENX()&&
                        worldY + gp.getTileSize()> gp.getTarget().getWorldY() - gp.getTarget().getSCREENX()&&
                        worldY - gp.getTileSize()< gp.getTarget().getWorldY() + gp.getTarget().getSCREENY()){
                    g2.drawImage(tile[tileNum].getImage(),screenX,screenY, null);
                }
            }


            worldCol++;

            if(worldCol == gp.getMAXWORLDCOL()){
                worldCol = 0;
                worldRow++;
            }
        }
    }

    private void loadMap(String filePath){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.getMAXWORLDCOL() && row < gp.getMAXWORLDROW()){
                String line = br.readLine();
                while(col < gp.getMAXWORLDCOL()){
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.getMAXWORLDCOL()){
                    col = 0;
                    row++;
                }
            }
            br.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getMapTileNum(int col, int row) {
        return mapTileNum[col][row];
    }

    public Tile getTile(int index){
        return tile[index];
    }

}
