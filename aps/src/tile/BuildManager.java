package tile;

import main.GamePanel;

import java.awt.*;

public class BuildManager {
    Build[] builds = new Build[10];
    GamePanel gp;

    public BuildManager(GamePanel gp){
        this.gp = gp;
        initializeBuilds();
    }

    private void initializeBuilds(){
        Build build1 = new Build(20*gp.getTileSize(),18*gp.getTileSize(),gp.getTileSize() * 2,gp.getTileSize()*2,"house");
        builds[0] = build1;
    }


    public void draw(Graphics2D g2){
        int screenX = 0;int screenY = 0;
        for (Build build : builds){
            if(build != null){
                if(gp.getGameState() != gp.getPlayState()){
                    screenX = build.worldX - gp.getTarget().getWorldX() + gp.getTarget().getSCREENX();
                    screenY = build.worldY - gp.getTarget().getWorldY() + gp.getTarget().getSCREENY();
                } else{
                    screenX = build.worldX - gp.getPlayerWorldX() + gp.getPlayerSCREENX();
                    screenY = build.worldY - gp.getPlayerWorldY() + gp.getPlayerSCREENY();
                }
                build.draw(g2,screenX,screenY);
            }
        }
    }

    public Build[] getBuilds() {
        return builds;
    }
}
