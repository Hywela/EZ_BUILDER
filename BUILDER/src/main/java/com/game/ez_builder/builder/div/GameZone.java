package com.game.ez_builder.builder.div;

import android.util.Log;

import com.game.ez_builder.builder.main.WorldHandler;
import com.game.ez_builder.builder.objects.BoxObject;
import com.game.ez_builder.builder.rendering.Background;

import org.jbox2d.common.Vec2;

/**
 * Created by Kristoffer on 15.06.2014.
 */
public class GameZone {

    private int zoneID;

    private int zoneHeight, zoneWidth;
    private int wallWidth=0, wallHeight=0;
    private int spawnPostionSideH=0;
    private int startPOSW, startPOSH;
    private BoxObject leftWall, rigthWall;
    private Background background;


    public GameZone(int startPostionWidth, int startPostionHeight, int zoneWidth, int zoneHeight) {

        this.zoneHeight = startPostionHeight + zoneHeight;

        this.zoneWidth = startPostionWidth + zoneWidth;
        this.wallHeight = this.zoneHeight - startPostionHeight;

        this.wallWidth = (int) ( this.zoneWidth * 0.02 );
if(startPostionHeight != 0){
        spawnPostionSideH = this.zoneHeight - (startPostionHeight/2)  ;
} else  spawnPostionSideH = this.zoneHeight/2;
        Log.d("start P", "P " + startPostionHeight);

        Log.d("POSTION WALL", "P " + spawnPostionSideH);
        startPOSW = 0;
        startPOSH = startPostionHeight;
        background = new Background(Constants.getLigthShader());

        zoneID = Level.getZoneID();
        WorldHandler.zoneActor.add(this);

        initiateZone();
    }
   public void initiateZone(){

       leftWall = new BoxObject(wallWidth, wallHeight);
       leftWall.setTexture(Constants.getTextureMotherBoard());
       rigthWall = new BoxObject(wallWidth, wallHeight);
       rigthWall.setTexture(Constants.getTextureMotherBoard());
       leftWall.color = new Color3();
       leftWall.color.r = (int) (Math.random() * 255);
       leftWall.color.g = (int) (Math.random() * 255);
       leftWall.color.b = (int) (Math.random() * 255);
       rigthWall.color = new Color3();
       rigthWall.color.r = (int) (Math.random() * 255);
       rigthWall.color.g = (int) (Math.random() * 255);
       rigthWall.color.b = (int) (Math.random() * 255);
       postion();
   }
    public void postion(){
        rigthWall.setPosition(new Vec2(10, spawnPostionSideH));
        rigthWall.createPhysicsBody(0, 0.5f, 0.8f);

        leftWall.setPosition(new Vec2( zoneWidth  - 10,  spawnPostionSideH));
        leftWall.createPhysicsBody(0, 0.5f, 0.8f);

        background.setVertices( startPOSW, startPOSH ,zoneWidth,zoneHeight);

    }
    public void loadZoneObjects(){


    }
    public void drawZone(){

        background.draw(Constants.getBackGroundTexture());
    }

    public int getZone() {
        return zoneID;
    }

    public boolean isItinTheZone(Vec2 pos) {
        return Calculation.checkIfinZone(pos, zoneHeight, zoneWidth, zoneHeight - (startPOSH), zoneWidth);
    }
}
