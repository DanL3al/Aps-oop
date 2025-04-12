package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
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
        try {
            tile[0] = new Tile();
            tile[0].setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/grass.png")));

            tile[1] = new Tile();
            tile[1].setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/wall.png")));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/water.png")));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/earth.png")));

            tile[4] = new Tile();
            tile[4].setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/tree.png")));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/sand.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.getMAXWORLDCOL() && worldRow < gp.getMAXWORLDROW()){
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.getTileSize();
            int worldY = worldRow * gp.getTileSize();
            int screenX = worldX - gp.getPlayerWorldX() + gp.getPlayerSCREENX();
            int screenY = worldY - gp.getPlayerWorldY() + gp.getPlayerSCREENY();

            if(worldX + gp.getTileSize()> gp.getPlayerWorldX() - gp.getPlayerSCREENX()&&
               worldX - gp.getTileSize()< gp.getPlayerWorldX() + gp.getPlayerSCREENX()&&
               worldY + gp.getTileSize()> gp.getPlayerWorldY() - gp.getPlayerSCREENY()&&
               worldY - gp.getTileSize()< gp.getPlayerWorldY() + gp.getPlayerSCREENY()){
                g2.drawImage(tile[tileNum].getImage(),screenX,screenY,gp.getTileSize(), gp.getTileSize(), null);
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
