package com.game.ez_builder.builder.main;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;


import com.game.ez_builder.builder.div.BuilderConstants;
import com.game.ez_builder.builder.div.Color3;
import com.game.ez_builder.builder.div.Constants;
import com.game.ez_builder.builder.objects.BoxObject;
import com.game.ez_builder.builder.objects.ManMadeObject;
import com.game.ez_builder.builder.objects.TriangleObj;
import com.game.ez_builder.builder.physics.PhysicWorld;

import org.jbox2d.common.Vec2;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Kristoffer on 06.06.2014.
 */
public class MyRenderer implements GLSurfaceView.Renderer {
    //Variables
    private static boolean Spawn = false;
    private static boolean spawnCircle = false;
    private static boolean spawnManMade = false;
    private static boolean spawnTriangle = false;
    private static int screenW;
    private static int screenH;
    private  int SpawnX;
    private  int SpawnY;
//temp
    private int tempOffset=0;

    private  int cameraX=0;
    private  int cameraY=0;
    private  int offsetA=0;
    private  int OffsetB=0;

    private WorldHandler world;

    private static final String TAG = "MyGLRenderer";

    private static Context ActivityContext;

    private final float[] mProjectionMatrix = new float[16];

    //Level Builder stuff
    Vector<Float> buildObject;

    public void SpawnTriangle(int x, int y) {
    SpawnX = x;
    SpawnY = y;
    spawnTriangle = true;
    }
    public void SpawnBox(int x, int y) {
    SpawnX = x;
    SpawnY = y;
    Spawn = true;

    }
    public void SpawnManMade(int x, int y) {
    SpawnX = x;
    SpawnY = y;
    spawnManMade = true;

    }
    public void SpawnCircle(int x, int y) {
    SpawnX = x;
    SpawnY = y;
    spawnCircle = true;

    }



    @Override
    public void onSurfaceCreated(GL10 unused, javax.microedition.khronos.egl.EGLConfig config) {
        //  mActivityContext = NULL;
        // Set the background frame color
        buildObject = new Vector<Float>();

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        world = new WorldHandler();
        PhysicWorld.setGravity(new Vec2(0, -2));


    }

    @Override
    public void onDrawFrame(GL10 gl) {

        // Note when we begin
        long startTime = System.currentTimeMillis();

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        if(BuilderConstants.getEditType() > 0){
            world.levelBuilderManipulateObject(BuilderConstants.getEditID());


        }

        if (Spawn) {
            buildBox(SpawnX, screenH - SpawnY);

            Spawn = false;
        }
        if(spawnCircle){
            world.spawnCircle(SpawnX, screenH - SpawnY, 15);
            spawnCircle = false;
        }
        if(spawnManMade){
           buildObject( SpawnX , SpawnY);
            spawnManMade = false;
        }
        if(spawnTriangle){
            buildTriangle(SpawnX, screenH - SpawnY);
            spawnTriangle = false;
        }
        //world.useLigthShader();
       world.useLigthShader();
        GLES20.glUniformMatrix4fv(world.getProjectionHandle(), 1, false, mProjectionMatrix, 0);

        world.drawBackGround();
        // Draw everything
        world.useColorShader();
        world.drawBoxActors();
        GLES20.glUniformMatrix4fv(world.getProjectionHandle(), 1, false, mProjectionMatrix, 0);
       // world.useColorShader();
       // world.useLigthShader();
     //   GLES20.glUniformMatrix4fv(world.getProjectionHandle(), 1, false, mProjectionMatrix, 0);
        world.drawParticleActors();
        // Calculate how much time rendering took
        long drawTime = System.currentTimeMillis() - startTime;

        // If we didn't take enough time, sleep for the difference
        // 1.0f / 60.0f ~= 0.016666666f -> 0.016666666f * 1000 = 16.6666666f
        // Since currentTimeMillis() returns a ms value, we convert our elapsed to ms
        //
        // It's also 1000.0f / 60.0f, but meh
        if (drawTime < 16) {
            try {
                Thread.sleep(16 - drawTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void buildBox(int x, int y) {

        BoxObject obj = new BoxObject(50, 50);
        obj.color = new Color3();
        obj.color.r = (int) (Math.random() * 255);
        obj.color.g = (int) (Math.random() * 255);
        obj.color.b = (int) (Math.random() * 255);

        obj.setTexture(Constants.getTextureMotherBoard());
        obj.setPosition(new Vec2(x, y+tempOffset));
        Log.d(" BUILDBOX ", "x " +x);
        obj.createPhysicsBody(0.0f, 0.2f, 0.5f);

    }
    private void buildTriangle(int spawnX, int spawnY) {

        TriangleObj obj = new TriangleObj(50, 50);
        obj.color = new Color3();
        obj.color.r = (int) (Math.random() * 255);
        obj.color.g = (int) (Math.random() * 255);
        obj.color.b = (int) (Math.random() * 255);

        obj.setTexture(Constants.getTextureMotherBoard());
        obj.setPosition(new Vec2(spawnX, spawnY));
        obj.createPhysicsBody(0.0f, 0.2f, 0.5f);
    }
    public void buildObject(int x, int y) {



        if(buildObject.size() >= 5) {
            ManMadeObject manMade = new ManMadeObject(50, 50, buildObject);
            manMade.color = new Color3();
            manMade.color.r = (int) (Math.random() * 255);
            manMade.color.g = (int) (Math.random() * 255);
            manMade.color.b = (int) (Math.random() * 255);
            manMade.setPosition(new Vec2(x, y+tempOffset));
            manMade.createPhysicsBody(0.0f, 0.2f, 0.5f);
        }
        buildObject.removeAllElements();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        // Set ortho projection

        Matrix.orthoM(mProjectionMatrix, 0, 0, width, 0, height, -10, 10);
        GLES20.glUniformMatrix4fv(world.getProjectionHandle(), 1, false, mProjectionMatrix, 0);

        screenW = width;
        screenH = height;

        BuilderConstants.setScreenSizeY(height);
        BuilderConstants.setScreenSizeW(width);
        world.onResize(width, height);
    }
    /**
     * Utility method for debugging OpenGL calls. Provide the name of the call
     * just after making it:
     * <p/>
     * <pre>
     * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
     * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
     *
     * If the operation is not successful, the check throws an error.
     *
     * @param glOperation - Name of the OpenGL call to check.
     */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

    public void moveCamera(int x, int y) {

        int xOffset,yOffset;
        if(x > screenW/2){
            if(y>screenH){

            }

        }else{

        }

        screenH += 20;
        Matrix.translateM(mProjectionMatrix,0,
                0,-20,0);


    }
    public void createVertice(float verticeX, float verticeY) {
        buildObject.add(verticeX);
        buildObject.add(verticeY);
    }
    public void setContext(Context context) {
        ActivityContext = context;
    }
    public static Context getActivityContext() {
        return ActivityContext;
    }
    public void builderJson(){
        world.writeJson();
    }

}
