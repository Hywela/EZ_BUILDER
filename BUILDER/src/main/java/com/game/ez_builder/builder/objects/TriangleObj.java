package com.game.ez_builder.builder.objects;

/**
 * Created by Kristoffer on 12.06.2014.
 */
public class TriangleObj extends BaseStaticObject {
    private float width;
    private float height;

    public TriangleObj(float _width, float _height) {
        super(); // Just assigns an ID
        setType(2);
        // 4 points, 3 coords, 12 elements, 9000 problems
        vertices = new float[6];

        this.width = _width;
        this.height = _height;

        refreshVertices();
    }

    private void refreshVertices() {

        // Modify our own vertex array, and pass it to setVertices
        // We'll define our box centered around the origin
        // The z cord could potentially be used to specify a layer to render on. Food for thought.
        vertices[0] = width * -0.5f;
        vertices[1] = height * -0.5f;


        vertices[2] = width * -0.5f;
        vertices[3] = height * 0.5f;


        vertices[4] = width * 0.5f;
        vertices[5] = height * -0.5f;





        // Update!
        objectType(2);
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

