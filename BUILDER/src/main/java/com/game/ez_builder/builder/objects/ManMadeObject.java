package com.game.ez_builder.builder.objects;

import android.util.Log;

import java.util.Vector;

/**
 * Created by Kristoffer on 12.06.2014.
 */
public class ManMadeObject
    extends BaseStaticObject {
        private float width;
        private float height;

        public ManMadeObject(float _width, float _height, Vector<Float> inputVec) {
            super(); // Just assigns an ID

            // 4 points, 3 coords, 12 elements, 9000 problems
            vertices = new float[inputVec.size()];
            Log.d("ManMADE", "" + inputVec.size());
            this.width = _width;
            this.height = _height;

            refreshVertices(inputVec);
        }

    private void refreshVertices(Vector<Float> inputVec) {

        for (int i = 0; i <inputVec.size(); i+=2 ) {
            vertices[i] =   inputVec.elementAt(i);
            vertices[i+1] = inputVec.elementAt(i+1);
            Log.d("ManMADE", "" + i);
            Log.d("ManMADE", "" + vertices[i]);
        }


        // Update!
       setVertices(vertices);
    }


    public float getWidth() { return width; }
    public float getHeight() { return height; }


}
