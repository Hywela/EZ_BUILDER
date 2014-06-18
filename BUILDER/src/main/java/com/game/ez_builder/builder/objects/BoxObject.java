package com.game.ez_builder.builder.objects;

/**
 * Created by Kristoffer on 07.06.2014.
 */
public class BoxObject extends BaseStaticObject {
    private float width;
    private float height;

  public BoxObject(float _width, float _height) {
    super(); // Just assigns an ID
      setType(1);
    // 4 points, 3 coords, 12 elements, 9000 problems
    vertices = new float[8];

    this.width = _width;
    this.height = _height;

    refreshVertices();
    setWidthBUILDER((int)_width);
    setHeigthBUILDER((int)_height);
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


        vertices[6] = width * 0.5f;
        vertices[7] = height * 0.5f;

        objectType(1);
        // Update!
        setVertices(vertices);
    }

    // Rebuild our vertices on modification
    public void setWidth(float _width) {
        this.width = _width;
        setWidthBUILDER((int)_width);

        refreshVertices();
    }

    public void setHeight(float _height) {
        this.height = _height;
        setHeigthBUILDER((int)_height);
        refreshVertices();
    }



}
