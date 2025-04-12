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
        mapTileNum = new int[gp.getMaxScreenCol()][gp.getMaxScreenRow()];
        getTileImage();
        loadMap();

    }

    private void getTileImage(){
        try {
            tile[0] = new Tile();
            tile[0].setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/grass.png")));

            tile[1] = new Tile();
            tile[1].setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/wall.png")));

            tile[2] = new Tile();
            tile[2].setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/water.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.getMaxScreenCol() && row < gp.getMaxScreenRow()){
            int tileNum = mapTileNum[col][row];
            g2.drawImage(tile[tileNum].getImage(),x,y,gp.getTileSize(), gp.getTileSize(), null);
            col++;
            x += gp.getTileSize();

            if(col == gp.getMaxScreenCol()){
                col = 0;
                x = 0;
                row++;
                y += gp.getTileSize();
            }
        }
    }

    private void loadMap(){
        try{
            InputStream is = getClass().getResourceAsStream("/maps/aps-map.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.getMaxScreenCol() && row < gp.getMaxScreenRow()){
                String line = br.readLine();
                while(col < gp.getMaxScreenCol()){
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.getMaxScreenCol()){
                    col = 0;
                    row++;
                }
            }
            br.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
