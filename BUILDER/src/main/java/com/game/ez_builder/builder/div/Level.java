package com.game.ez_builder.builder.div;

import android.os.Build;

/**
 * Created by Kristoffer on 15.06.2014.
 */
public class Level {
    private GameZone  zone;
    private int lvlID;

    private static int zoneID=1;
    private int previousWidth, previousHeigth;


    public Level(int id) {
        lvlID = id;
        //Dumy var

        previousHeigth = BuilderConstants.getScreenSize();
        previousWidth = BuilderConstants.getScreenSizeW();
        zone = new GameZone(0, 0, previousWidth, previousHeigth );
        loadNewZone();
        loadNewZone();

    }

    public static int getZoneID() {
        return zoneID++;
    }

    public void loadNewZone(){

        int h = BuilderConstants.getScreenSize();
        int w = 0;

        GameZone obj = new GameZone(previousWidth ,previousHeigth, w,h );
        previousHeigth += h;
        previousWidth  += w;
    }
    public void startLevel(){
        zone.initiateZone();
    }

    private static int getNextZoneId(){
       return zoneID++;

    }

    public int getID() {
        return lvlID;
    }
}
