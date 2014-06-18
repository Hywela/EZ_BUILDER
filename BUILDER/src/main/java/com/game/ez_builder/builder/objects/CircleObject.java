package com.game.ez_builder.builder.objects;

import android.util.Log;

/**
 * Created by Kristoffer on 11.06.2014.
 */
public class CircleObject extends BaseDynamicObjects {
    private float width;
    private float height;
    private float radius;

    public CircleObject(float _width, float _height, float radius) {
        super(); // Just assigns an ID

        // 4 points, 3 coords, 12 elements, 9000 problems


        this.width = _width;
        this.height = _height;
        this.radius = radius;
        setRadius(radius);
        refreshVertices();

    }

    private void refreshVertices() {

        int vertexCount = 30;

        float center_x = 0.0f;
        float center_y = 0.0f;

//create a buffer for vertex data
        vertices = new float[vertexCount*2]; // (x,y) for each vertex
        int idx = 0;

//center vertex for triangle fan
        vertices[idx++] = center_x;
        vertices[idx++] = center_y;

//outer vertices of the circle
        int outerVertexCount = vertexCount-1;

        for (int i = 0; i < outerVertexCount; ++i){
            float percent = (i / (float) (outerVertexCount-1));
            float rad = percent * 2*(float)Math.PI;

            //vertex position
            float outer_x = center_x + radius * (float)Math.cos(rad);
            float outer_y = center_y + radius * (float)Math.sin(rad);

            vertices[idx++] = outer_x;
            vertices[idx++] = outer_y;
        }

        // Update!
        Log.d("circle", "new Circle");
        setVertices(vertices);
    }

    // Rebuild our vertices on modification
    public void setWidth(float _width) {
        this.width = _width;
        refreshVertices();
    }

    public void setHeight(float _height) {
        this.height = _height;
        refreshVertices();
    }

    public float getWidth() { return width; }
    public float getHeight() { return height; }

}