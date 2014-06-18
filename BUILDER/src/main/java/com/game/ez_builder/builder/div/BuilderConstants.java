package com.game.ez_builder.builder.div;

import android.content.Context;
import android.view.ContextMenu;
import android.widget.PopupMenu;
import android.widget.PopupWindow;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Kristoffer on 13.06.2014.
 */
public class BuilderConstants {

    private static int rotation;

    private static int sizeW;
    private static int sizeH;

    public static int getScreenSizeW() {
        return screenSizeW;
    }

    public static void setScreenSizeW(int screenSizeW) {
        BuilderConstants.screenSizeW = screenSizeW;
    }

    private static int screenSizeW;
    private static int screenSizeY;
    private static  int radius;
    private static int editType=0;
    private static int editID;

    public static PopupWindow getBuilderMenu() {
        return builderMenu;
    }

    private static PopupWindow builderMenu;
    public static Context context;


    public static void setBuilderMenu(PopupWindow builderMenu) {
        BuilderConstants.builderMenu = builderMenu;
    }


    public static void setBuilderContext(Context context) {
       BuilderConstants.context = context;

    }
    public static Context getBuilderContext() {
        return context;
    }

    public static int getScreenSize() {
        return screenSizeY;
    }
    public static void setScreenSizeY(int screenSizeY) {
        BuilderConstants.screenSizeY = screenSizeY;
    }
    public static int getRadius() {
        return radius;
    }

    public static void setRadius(int radius) {
        BuilderConstants.radius = radius;
    }

    public static int getEditType() {
        return editType;
    }

    public static void setEditType(int editType) {
        BuilderConstants.editType = editType;
    }
    public static int getEditID() {
        return editID;
    }

    public static void setEditID(int editID) {
        BuilderConstants.editID = editID;
    }


    public static void setRotation(int rotation) {
        BuilderConstants.rotation = rotation;
    }

    public static void setSizeW(int sizeW) {
        BuilderConstants.sizeW = sizeW;
    }

    public static void setSizeH(int sizeH) {
        BuilderConstants.sizeH = sizeH;
    }
    public static int getSizeH() {
        return sizeH;
    }

    public static int getSizeW() {
        return sizeW;
    }

    public static int getRotation() {
        return rotation;
    }
}
