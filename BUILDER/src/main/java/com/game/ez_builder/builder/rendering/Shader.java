package com.game.ez_builder.builder.rendering;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import com.game.ez_builder.builder.div.RawFileReader;


/**
 * Created by Kristoffer on 12.06.2014.
 */
public class Shader {
    private int shader;
    private static  String TAG = "Shader";
    public Shader(Context ActivityContext , String tag, int vertexCode, int fragmentCode) {
        TAG = tag;
        String vertexShaderCode = RawFileReader.readTextFile(ActivityContext, vertexCode);
        String fragmentShaderCode = RawFileReader.readTextFile(ActivityContext,fragmentCode);

        // prepare shaders and OpenGL program
        int vertexShader = loadShader(
                GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(
                GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        shader = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(shader, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(shader, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(shader);                  // create OpenGL program executables
    }
    private int loadShader(int type, String shaderCode) {

        int shader = GLES20.glCreateShader(type);
        if (shader != 0) {
            GLES20.glShaderSource(shader, shaderCode);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                Log.e(TAG, "Could not compile shader " + type + ":");
                Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }

        return shader;
    }

    public int getShader(){
        return shader;
    }
}
