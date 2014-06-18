package com.game.ez_builder.builder.div;

/**
 * Created by Kristoffer on 13.06.2014.
 */

import android.util.Log;
import android.widget.Toast;

import org.jbox2d.common.Vec2;

public class Calculation {


    public static boolean checkIfinBound(int checkX, int checkY, Vec2 postion){

        if(checkX > postion.x-50 && checkX < postion.x +50 ){
                if(checkY > postion.y-50 && checkY < postion.y +50){
                        Log.d("objecCHeck", "done");
                        return true;
                    }
                }




        return false;

    }
    public static boolean checkIfinZone(Vec2 pos, int h , int w, int offsetH, int offsetW){


        if(pos.x > w-offsetW && pos.x < w  ){

            if(pos.y > h-offsetH && pos.y <h ){
                Log.d("calcZOne", "x "+ pos.x +" against "+ (w-offsetW) );
                return true;
            }
        }




        return false;

    }
}
