package com.game.ez_builder.builder.div;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.jbox2d.common.Vec2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Kristoffer on 16.06.2014.
 */
public class outPutLevels {
    private JSONObject outPutFile;
    private JSONArray boxObject;
    private int currentZone;
    private int numberOfBoxes=0;
    /**
     *
     * @param level
     * @param nZones
     */
    public outPutLevels(int level, int nZones) {
        outPutFile = new JSONObject();
        boxObject = new JSONArray();
        outPutFile.put("level", new Integer(level));
        outPutFile.put("nzones", new Integer(nZones));

    }

    public void writeZone(int zone){
        currentZone= zone;

        boxObject = new JSONArray();
        Log.d("JSON", outPutFile.toString());

    }
    public void doneWithZOne(){

        outPutFile.put("nObjectsInZone"+currentZone, new Integer(numberOfBoxes));
        outPutFile.put("objecsInZone"+currentZone, boxObject);
        numberOfBoxes = 0;
        boxObject = new JSONArray();

        Log.d("JSONBo", outPutFile.toString());
    }
    public void writeStatic(int heigth, int width, Vec2 position, int type, float rotation){

        numberOfBoxes++;

        boxObject.add(new Integer(heigth));
        boxObject.add(new Integer(width));
        boxObject.add(new Float(position.x));
        boxObject.add(new Float(position.y));
        boxObject.add(new Integer(type));
        boxObject.add(new Float(rotation));


    }
    public static Boolean checkStateOfSDCard(Context mContext) {
        String auxSDCardStatus = Environment.getExternalStorageState();

        if (auxSDCardStatus.equals(Environment.MEDIA_MOUNTED))
            return true;
        else if (auxSDCardStatus.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            Toast.makeText(
                    mContext,
                    "Warning, the SDCard it's only in read mode.\nthis does not result in malfunction"
                            + " of the read aplication", Toast.LENGTH_LONG
            )
                    .show();
            return true;
        } else if (auxSDCardStatus.equals(Environment.MEDIA_NOFS)) {
            Toast.makeText(
                    mContext,
                    "Error, the SDCard can be used, it has not a corret format or "
                            + "is not formated.", Toast.LENGTH_LONG)
                    .show();
            return false;
        } else if (auxSDCardStatus.equals(Environment.MEDIA_REMOVED)) {
            Toast.makeText(
                    mContext,
                    "Error, the SDCard is not found, to use the reader you need "
                            + "insert a SDCard on the device.",
                    Toast.LENGTH_LONG).show();
            return false;
        } else if (auxSDCardStatus.equals(Environment.MEDIA_SHARED)) {
            Toast.makeText(
                    mContext,
                    "Error, the SDCard is not mounted beacuse is using "
                            + "connected by USB. Plug out and try again.",
                    Toast.LENGTH_LONG).show();
            return false;
        } else if (auxSDCardStatus.equals(Environment.MEDIA_UNMOUNTABLE)) {
            Toast.makeText(
                    mContext,
                    "Error, the SDCard cant be mounted.\nThe may be happend when the SDCard is corrupted "
                            + "or crashed.", Toast.LENGTH_LONG).show();
            return false;
        } else if (auxSDCardStatus.equals(Environment.MEDIA_UNMOUNTED)) {
            Toast.makeText(
                    mContext,
                    "Error, the SDCArd is on the device but is not mounted."
                            + "Mount it before use the app.",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
    public void outPutToJson(String filename) {

        File mediaStorage = new File(Environment.getExternalStorageDirectory()
                + "/EZLEVEL/");
        if (checkStateOfSDCard(BuilderConstants.getBuilderContext())) {
            if (!mediaStorage.exists()) {
                mediaStorage.mkdirs();
            }
            try {
                File outputPath = new File(mediaStorage, filename);
                FileWriter file = new FileWriter(outputPath);
                file.write(outPutFile.toJSONString());
                file.flush();
                file.close();
                Log.d("FileMADE","Done");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
