package com.game.ez_builder.builder.main;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.game.ez_builder.builder.div.BuilderConstants;
import com.game.ez_builder.builder.div.Constants;


/**
 * Created by Kristoffer on 06.06.2014.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    //Inflating the Popup using xml file

    private final MyRenderer mRenderer;
    Context tempContext;

    public MyGLSurfaceView(Context context) {
        super(context);
        tempContext = context;
        BuilderConstants.setBuilderContext(context);

        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);


        // act.registerForContextMenu(mGLView);
        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new MyRenderer();
        mRenderer.setContext(context);
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {


            case MotionEvent.ACTION_DOWN: {

                switch (Constants.getOptionChoser()) {


                    case 0:
                        int id = WorldHandler.targetTouchCheckStatic((int) x, (int) y);
                        if (id > -1) {
                            BuilderConstants.setEditID(id);
                            BuilderConstants.getBuilderMenu()
                                    .showAtLocation(this, Gravity.LEFT, (int) x, 0);


                        }
                        break;

                    case 1:
                        mRenderer.moveCamera((int) x, (int) y);
                        break;
                    case 2:
                        mRenderer.SpawnBox((int) x, (int) y);
                        break;
                    case 3:
                        mRenderer.SpawnCircle((int) x, (int) y);
                        break;
                    case 4:
                        mRenderer.createVertice(x, y);
                        Toast.makeText(tempContext, "Vertices at :" + x + " , " + y + " made", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        //  Constants.setOptionChoser(0);
                        // mRenderer.SpawnManMade((int) x, (int) y);
                        mRenderer.builderJson();
                        Constants.setOptionChoser(0);
                        break;
                    case 6:
                        mRenderer.SpawnTriangle((int) x, (int) y);
                        //mRenderer.builderJson();

                        break;
                }
                break;
            }


        }


        return true;
    }

}