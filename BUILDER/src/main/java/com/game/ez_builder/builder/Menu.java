package com.game.ez_builder.builder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.game.energyzone.R;
import com.game.ez_builder.builder.div.BuilderConstants;
import com.game.ez_builder.builder.div.Constants;
import com.game.ez_builder.builder.main.MyGLSurfaceView;



public class Menu extends Activity {
    private boolean rendering = false;
    private MyGLSurfaceView mGLView;
    Button rotation;
    Button size;
    Button radius;
    Button submit;
    PopupWindow builder_edit_input;
    EditText input_field;
    int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_menu);


        final View builder_view;
        LayoutInflater b_inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        builder_view = b_inflater.inflate(R.layout.builder_edit, null);
        builder_edit_input = new PopupWindow(builder_view, 600, 200, true);

        input_field = (EditText) builder_view.findViewById(R.id.editText);

        submit = (Button) builder_view.findViewById(R.id.button_submit);


        submit.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                builder_edit_input.dismiss();

                if (type == 1) {
                    BuilderConstants.setRadius(Integer.parseInt(input_field.getText().toString()));
                    BuilderConstants.setEditType(1);
                    type = 0;
                } else if (type == 2) {
                    BuilderConstants.setRotation(Integer.parseInt(input_field.getText().toString()));
                    BuilderConstants.setEditType(2);
                    type = 0;
                } else if (type == 3) {

                    BuilderConstants.setSizeW(Integer.parseInt(input_field.getText().toString()));
                    BuilderConstants.setEditType(3);
                    type = 4;
                    builder_edit_input.showAtLocation(builder_view, Gravity.CENTER, 0, 0);
                } else if (type == 4) {
                    BuilderConstants.setSizeH(Integer.parseInt(input_field.getText().toString()));
                    BuilderConstants.setEditType(4);
                    type = 0;
                }
            }
        });

        final PopupWindow pw;
        final View popupView;
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.popup, null);
        pw = new PopupWindow(popupView, 400, 400, true);


        final Button start = (Button) findViewById(R.id.dummy_button);
        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Constants.setOptionChoser(0);
                rendering = true;


                mGLView = new MyGLSurfaceView(getBaseContext());

                setContentView(mGLView);


            }
        });


        rotation = (Button) popupView.findViewById(R.id.button_builder_roatation);

        size = (Button) popupView.findViewById(R.id.button_builder_size);

        radius = (Button) popupView.findViewById(R.id.button_builder_radius);


        radius.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                type = 1;
                pw.dismiss();
                builder_edit_input.showAtLocation(builder_view, Gravity.CENTER, 0, 0);
            }
        });


        rotation.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                type = 2;
                pw.dismiss();
                builder_edit_input.showAtLocation(builder_view, Gravity.CENTER, 0, 0);
            }
        });
        size.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                type = 3;
                pw.dismiss();
                builder_edit_input.showAtLocation(builder_view, Gravity.CENTER, 0, 0);

            }
        });
        BuilderConstants.setBuilderMenu(pw);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


    }

    public void inputBox(int type, String text) {


    }
//
//    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            if (AUTO_HIDE) {
////                Constants.setOptionChoser(0);
////                rendering = true;
////                mGLView = new MyGLSurfaceView(getBaseContext());
//
////                setContentView(mGLView);
//
//                pw.showAtLocation(findViewById(R.id.dummy_button), Gravity.CENTER, 0, 0);
//               // BuilderConstants.setBuilderMenu(pw);
//                delayedHide(AUTO_HIDE_DELAY_MILLIS);
//            }
//            return false;
//        }
//    };


    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        if (rendering) mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        if (rendering) mGLView.onResume();
    }

    // Initiating Menu XML file (menu.xml)
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.button_builder_size:
                // Single menu item is selected do something
                // Ex: launching new activity/screen or show alert message
                Toast.makeText(Menu.this, "TEMPTEMP", Toast.LENGTH_SHORT).show();

                return true;


            case R.id.Move:
                // Single menu item is selected do something
                // Ex: launching new activity/screen or show alert message
                Toast.makeText(Menu.this, "You can Move", Toast.LENGTH_SHORT).show();

                Constants.setOptionChoser(1);
                return true;

            case R.id.menu_spawn_box:
                Constants.setOptionChoser(2);
                Toast.makeText(Menu.this, "Can now Spawn Box", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menu_spawn_circle:
                Constants.setOptionChoser(3);
                Toast.makeText(Menu.this, "Spawn Circle", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menue_create_vertices:
                Constants.setOptionChoser(4);
                Toast.makeText(Menu.this, "Create a Vertice", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menu_build_the_modell:
                Constants.setOptionChoser(5);
                Toast.makeText(Menu.this, "Building the object", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menu_spawn_triangle:
                Constants.setOptionChoser(6);
                Toast.makeText(Menu.this, "Spawn Triangle", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.edit_mode:


                Constants.setOptionChoser(0);
                Toast.makeText(Menu.this, "you can now edit objects", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
