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
        tile = new Tile[70];
        mapTileNum = new int[gp.getMAXWORLDCOL()][gp.getMAXWORLDROW()];
        getTileImage();
        loadMap("/maps/world01.txt");
    }

    private void getTileImage(){
        setup(0,"chao1",false);
        setup(1,"chao2",false);
        setup(2,"terra",false);
        setup(3,"rua",false);
        setup(4,"teto1-1",true);
        setup(5,"teto1-2",true);
        setup(6,"teto1-3",true);
        setup(7,"teto1-4",true);
        setup(8,"teto1-5",true);
        setup(9,"teto1-6",true);
        setup(10,"predio1-1",true);
        setup(11,"predio1-2",true);
        setup(12,"predio1-3",true);
        setup(13,"predio1-4",true);
        setup(14,"predio1-5",true);
        setup(15,"predio1-6",true);
        setup(16,"predio1-7",true);
        setup(17,"predio1-8",true);
        setup(18,"predio1-9",true);
        setup(19,"predio1-10",true);
        setup(20,"teto2-1",true);
        setup(21,"teto2-2",true);
        setup(22,"teto2-3",true);
        setup(23,"teto2-4",true);
        setup(24,"teto2-5",true);
        setup(25,"teto2-6",true);
        setup(26,"teto2-7",true);
        setup(27,"teto2-8",true);
        setup(28,"teto2-9",true);
        setup(29,"teto2-10",true);
        setup(30,"teto2-11",true);
        setup(31,"predio2-1",true);
        setup(32,"predio2-2",true);
        setup(33,"predio2-3",true);
        setup(34,"predio2-4",true);
        setup(35,"predio2-5",true);
        setup(36,"predio2-6",true);
        setup(37,"predio2-7",true);
        setup(38,"predio2-8",true);
        setup(39,"teto3-1",true);
        setup(40,"teto3-2",true);
        setup(41,"teto3-3",true);
        setup(42,"teto3-4",true);
        setup(43,"teto3-5",true);
        setup(44,"teto3-6",true);
        setup(45,"teto3-7",true);
        setup(46,"teto3-8",true);
        setup(47,"predio3-1",true);
        setup(48,"predio3-2",true);
        setup(49,"predio3-3",true);
        setup(50,"predio3-4",true);
        setup(51,"predio3-5",true);
        setup(52,"predio3-6",true);
        setup(53,"predio3-7",true);
        setup(54,"predio3-8",true);
        setup(55,"predio3-9",true);
        setup(56,"grade1",true);
        setup(57,"grade2",true);
        setup(58,"predio1-11",true);
        setup(59,"teto1-7",true);
        setup(60,"teto1-8",true);
        setup(61,"arvore1-1",true);
        setup(62,"arvore1-2",true);
        setup(63,"arvore2-1",true);
        setup(64,"arvore2-2",true);
        setup(65,"arvore3-1",true);
        setup(66,"arvore3-2",true);
        setup(67,"faixa1",false);
        setup(68,"faixa2",false);
        setup(69,"chao1",true);
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
                if(worldX + gp.getTileSize()> gp.getPlayerWorldX() - gp.getPlayerSCREENX()&&
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
