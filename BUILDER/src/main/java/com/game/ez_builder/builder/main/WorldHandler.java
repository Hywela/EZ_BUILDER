package com.game.ez_builder.builder.main;


import android.opengl.GLES20;
import android.util.Log;


import com.game.energyzone.R;

import com.game.ez_builder.builder.div.BuilderConstants;
import com.game.ez_builder.builder.div.Calculation;
import com.game.ez_builder.builder.div.Color3;
import com.game.ez_builder.builder.div.Constants;
import com.game.ez_builder.builder.div.GameZone;
import com.game.ez_builder.builder.div.Level;
import com.game.ez_builder.builder.div.xmlBuilder;
import com.game.ez_builder.builder.objects.BaseDynamicObjects;
import com.game.ez_builder.builder.objects.BaseStaticObject;
import com.game.ez_builder.builder.objects.BoxObject;
import com.game.ez_builder.builder.objects.CircleObject;
import com.game.ez_builder.builder.rendering.Shader;
import com.game.ez_builder.builder.rendering.Texture;

import org.jbox2d.common.Vec2;

import java.util.ArrayList;

/**
 * Created by Kristoffer on 12.06.2014.
 */
public class WorldHandler {
    private static  int screenW,screenH;
    private static int nextId = 0;
    private static int nextParticleId = 0;
    public static ArrayList<BaseStaticObject> staticActors = new ArrayList<BaseStaticObject>();
    public static ArrayList<BaseDynamicObjects> playerActors = new ArrayList<BaseDynamicObjects>();
    public static ArrayList<GameZone> zoneActor = new ArrayList<GameZone>();
    private static float PPM = 128.0f;
    private static final String TAG = "World";

    private BoxObject bottom,leftside, rigthside;

    private static int projectionHandle;
    //Shader
    Shader ligthShader, colorShader;
    //Texture
    private Texture texture, bgTexture;
    //Draw Objects
    private Level lvl;
    private static int shaderProg;

    public WorldHandler() {
        //Load Shaders
        ligthShader = new Shader(MyRenderer.getActivityContext(),
                "LigthShader", R.raw.ver, R.raw.frag );
        colorShader = new Shader(MyRenderer.getActivityContext(),
                "ColorShader",R.raw.colorshadervert, R.raw.colorshaderfrag );
        // Load Texture
        texture = new Texture(ligthShader.getShader(),
                MyRenderer.getActivityContext(), R.drawable.motherboardedit);
        bgTexture = new Texture(ligthShader.getShader(),
                MyRenderer.getActivityContext(), R.drawable.shiphull);
        // Make the Background

        // send the projection matric to the shader.
        projectionHandle = GLES20.glGetUniformLocation(ligthShader.getShader(),
                "mProjectionMatrix");
        // sets the shader so we can get it from anywhere
        Constants.setBackGroundTexture(bgTexture.getTexture());
        Constants.setTextureMotherBoard(texture.getTexture());

        Constants.setLigthShader(ligthShader.getShader());
        Constants.setColorShader(colorShader.getShader());
        // Makes the bottom wall.
        bottom = new BoxObject(1600, 50);
        bottom.color = new Color3();
        bottom.color.r = (int) (Math.random() * 255);
        bottom.color.g = (int) (Math.random() * 255);
        bottom.color.b = (int) (Math.random() * 255);
        bottom.setTexture(Constants.getTextureMotherBoard());
        createParticle();
    }
    public void createParticle(){
        CircleObject temp = new CircleObject(0,0,2);
        temp.color = new Color3();
        temp.color.r = 0;
        temp.color.g = 0;
        temp.color.b = 255;
        temp.setTexture(Constants.getTextureMotherBoard());
        temp.setPosition(new Vec2(screenW/2, screenH / 2));
        temp.createPhysicsBody(0.5f, 0.5f, 0.8f);
    }
    public void drawBoxActors(){
        for (BaseStaticObject obj : staticActors) {
            obj.draw();
            //Log.d("Drawing", "actor:"+i);
        }
    }
    public void drawParticleActors(){
        for (BaseDynamicObjects obj : playerActors) {
            obj.draw();
            //Log.d("Drawing", "actor:"+i);
        }
    }
    public void useLigthShader(){
        GLES20.glUseProgram(ligthShader.getShader());
    }
    public void useColorShader(){
        GLES20.glUseProgram(colorShader.getShader());
    }
    public int getProjectionHandle(){
        return projectionHandle;
    }
    public void drawBackGround(){

        for (GameZone obj : zoneActor) {
            obj.drawZone();

        }

    }
    public void onResize(int width, int height){
        screenW = width;
        screenH = height;

        if(lvl == null) {
            lvl = new Level(1);

            lvl.startLevel();

               // lvl.loadNewZone();
        }


        bottom.setPosition(new Vec2(screenW / 2, 100));
        bottom.createPhysicsBody(0, 0.5f, 0.8f);


    }

    public static float getPPM() {
        return PPM;
    }
    public static float getMPP() {
        return 1.0f / PPM;
    }
    public static int getNextId() {
        return nextId++;
    }
    public static int getNextParticleId() {
        return nextParticleId++;
    }
    public static Vec2 screenToWorld(Vec2 cords) {
        return new Vec2(cords.x / PPM, cords.y / PPM);
    }
    public static Vec2 worldToScreen(Vec2 cords) {
        return new Vec2(cords.x * PPM, cords.y * PPM);
    }
    public void spawnCircle(int spawnX, int spawnY, int radius) {

        CircleObject temp = new CircleObject(0,0,radius);
        temp.color = new Color3();
        temp.color.r = 0;
        temp.color.g = 0;
        temp.color.b = 255;
        temp.setTexture(Constants.getTextureMotherBoard());
        temp.setPosition(new Vec2(spawnX, spawnY));
        temp.createPhysicsBody(0.5f, 0.5f, 0.8f);
    }

    //
    public void switchZone(){


    }

    //Json read and write is coming
    public void writeJson(){


        xmlBuilder out = new xmlBuilder(lvl.getID());
        for (GameZone obj : zoneActor) {

            out.writeNewZone(obj.getZone());
            Log.d("zoneCheck", "" +obj.getZone() );
            for (BaseStaticObject sOBJ : staticActors) {

                if(obj.isItinTheZone(sOBJ.getPosition()) ) {
                        out.writeNewStatic(sOBJ.getHeigthB(), sOBJ.getWidthB(),
                                sOBJ.getPosition(),sOBJ.getobjectType(),sOBJ.getRotation(), sOBJ.getRadius(),sOBJ.getTexture());
                }

            }

        }
        out.printXML("TestLevel.xml");

    }
    public void parseJson(){}


    public static int targetTouchCheckDynamic(int checkX, int checkY) {
        for (BaseDynamicObjects obj : playerActors) {


           if(Calculation.checkIfinBound(checkX, checkY, WorldHandler.worldToScreen(obj.getPosition()))){
            return obj.getId();

           }


        }

        return -1;
    }
    public static int targetTouchCheckStatic(int checkX, int checkY) {
        for (BaseStaticObject obj : staticActors) {
            if(Calculation.checkIfinBound(checkX, screenH - checkY  , obj.getPosition() )){
                return obj.getId();

            }
        }

        return -1;
    }

    public static void levelBuilderManipulateObject(int id) {
        for (BaseStaticObject obj : staticActors) {


            if(obj.getId() == id){

                if(BuilderConstants.getEditType() == 2){

                    obj.setRotation(BuilderConstants.getRotation());
                    BuilderConstants.setEditType(0);
                }
                else if(BuilderConstants.getEditType() == 1){

                 //   obj.setRotation(BuilderConstants.get());
                   // BuilderConstants.setEditType(0);
                }
               else  if(BuilderConstants.getEditType() == 3){

                    obj.setWidthBUILDER(BuilderConstants.getSizeW());
                    BuilderConstants.setEditType(0);
                }
                else  if(BuilderConstants.getEditType() == 4){

                    obj.setHeigthBUILDER(BuilderConstants.getSizeH());
                    BuilderConstants.setEditType(0);
                }



            }
        }

    }

}
