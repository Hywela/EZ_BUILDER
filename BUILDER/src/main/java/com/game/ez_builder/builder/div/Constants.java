package com.game.ez_builder.builder.div;

import android.widget.PopupMenu;

/**
 * Created by Kristoffer on 12.06.2014.
 */
public class Constants {

    private static int ligthShader;
    private static int colorShader;
    private static int optionChoser;



    private static int backGroundTexture;
    private static int textureMotherBoard;

    public static int getOptionChoser() {
        return optionChoser;
    }

    public static void setOptionChoser(int optionChoser) {
        Constants.optionChoser = optionChoser;
    }


    public static int getColorShader() {
        return colorShader;
    }

    public static void setColorShader(int colorShader) {
        Constants.colorShader = colorShader;
    }


    public static void setLigthShader(int ligthShader) {
        Constants.ligthShader = ligthShader;
    }

    public static int getLigthShader() {
        return ligthShader;
    }


    public static void setBackGroundTexture(int backGroundTexture) {
        Constants.backGroundTexture = backGroundTexture;
    }

    public static void setTextureMotherBoard(int textureMotherBoard) {
        Constants.textureMotherBoard = textureMotherBoard;
    }
    public static int getBackGroundTexture() {
        return backGroundTexture;
    }

    public static int getTextureMotherBoard() {
        return textureMotherBoard;
    }
}
