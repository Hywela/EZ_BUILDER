package com.game.ez_builder.builder.rendering;

import android.opengl.GLES20;
import android.opengl.Matrix;


import com.game.ez_builder.builder.div.Color3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Kristoffer on 10.06.2014.
 */
public class Background {
    protected FloatBuffer bgBuffer;
    private FloatBuffer textBuffer;
    public Color3 color = new Color3(255, 255, 255);

    private float[] textureCoordinates =
            {
                    // Front face
                    0.0f, 0.0f,
                    0.0f, 1.0f,
                    1.0f, 0.0f,
                    0.0f, 1.0f,
                    1.0f, 1.0f,
                    1.0f, 0.0f,
            };
    protected float[] vertices;

    private int positionHandle ;
    private int textureHandle ;
    private int textureCord ;
    private int colorHandle ;
    private int modelHandle ;

    public Background(int shader) {
          vertices = new float[12];
         positionHandle = GLES20.glGetAttribLocation(shader, "Position");
         textureHandle = GLES20.glGetUniformLocation(shader, "texture");
         textureCord = GLES20.glGetAttribLocation(shader, "TexCoordinate");
            colorHandle = GLES20.glGetAttribLocation(shader, "color");
         modelHandle = GLES20.glGetUniformLocation(shader, "ModelView");

        //Allocate a new byte buffer to move the vertices into a FloatBuffer


        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(textureCoordinates.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        textBuffer = byteBuffer.asFloatBuffer();
        textBuffer.put(textureCoordinates);
        textBuffer.position(0);



    }

    public void draw( int textureData){
        // Construct mvp to be applied to every vertex
        float[] modelView = new float[16];

        // Equivalent of gl.glLoadIdentity()
        Matrix.setIdentityM(modelView, 0);




        // Load our matrix and color into our shader
        GLES20.glUniformMatrix4fv(modelHandle, 1, false, modelView, 0);

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_NEAREST);

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR);

        GLES20.glVertexAttrib3f(colorHandle, (float)color.r/ 255.0f,(float)color.g/ 255.0f, (float)color.b/ 255.0f);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
                GLES20.GL_REPEAT);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
                GLES20.GL_REPEAT);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);



        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureData);
        GLES20.glUniform1i(textureHandle, 0);

        GLES20.glVertexAttribPointer(textureCord, 2 , GLES20.GL_FLOAT, false,
                0, textBuffer);
        GLES20.glEnableVertexAttribArray(textureCord);
        GLES20.glUniform4fv(colorHandle, 1, color.toFloatArray(), 0);

        // Set up pointers, and draw using our vertBuffer as before
        GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 0, bgBuffer);
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertices.length / 3);
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(textureCord);

    }
    public void setVertices(int startW, int startH, int width, int height) {

        // Modify our own vertex array, and pass it to setVertices
        // We'll define our box centered around the origin
        // The z cord could potentially be used to specify a layer to render on. Food for thought.
        vertices[0] = startW;
        vertices[1] = startH;
        vertices[2] = 1.0f;

        vertices[3] = startW;
        vertices[4] = height;
        vertices[5] = 1.0f;

        vertices[6] = width ;
        vertices[7] = startH;
        vertices[8] = 1.0f;

        vertices[9] = width ;
        vertices[10] = height;
        vertices[11] = 1.0f;

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        bgBuffer = byteBuffer.asFloatBuffer();
        bgBuffer.put(vertices);
        bgBuffer.position(0);
    }
}
