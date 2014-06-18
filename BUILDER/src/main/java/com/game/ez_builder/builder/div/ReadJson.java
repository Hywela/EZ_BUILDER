package com.game.ez_builder.builder.div;

import com.game.ez_builder.builder.div.BuilderConstants;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Kristoffer on 16.06.2014.
 */
public class ReadJson {
    private JSONObject input;

    public ReadJson() {

    }
    public void readJsonData(String params) {
        JSONParser parser = new JSONParser();
        try {


            File f = new File( BuilderConstants.getBuilderContext().getFilesDir().getPath(),params);
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String inputData = new String(buffer);
            Object obj = parser.parse(inputData);


            input = (JSONObject) obj;
//TEST TEST
          String name = (String) input.get("zone").toString();

            System.out.println(inputData);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
