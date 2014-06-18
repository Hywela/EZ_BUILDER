package com.game.ez_builder.builder;

import android.opengl.GLES20;

import java.util.Vector;

/**
 * Created by Kristoffer on 10.06.2014.
 */
public class LigthPostion {
    private static Vector<Float> postion;

    public LigthPostion() {
        postion = new Vector<Float>();
    }
    public static void addLigth(float x, float y){
        postion.add(x);
        postion.add(y);

    }
}
