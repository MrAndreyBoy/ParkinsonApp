package ru.smartinc.parkinsonapp.recycler;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ru.smartinc.parkinsonapp.data.Exercise;

/**
 * Created by AndreyBoy on 08.04.2018.
 */

public class RecyclerFiller {

    public static final int READ_FULL = 0;
    public static final int READ_PROGRAM = 1;

    public static List<Exercise> fill(Context ctx, int param) {
        final String ATTRIBUTE_NAME_EXERCISES = "exercises";

        List<Exercise> exerList = new ArrayList<>();
        StringBuilder json = new StringBuilder();

        try {
            InputStream inputStream = ctx.getAssets().open("Exercises.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String str;
            while ((str = reader.readLine()) != null) {
                json.append(str);
            }
            inputStream.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (json.length() > 0) {
            try {
                JSONObject jsonObj = new JSONObject(json.toString());
                JSONArray exerArray = jsonObj.getJSONArray(ATTRIBUTE_NAME_EXERCISES);
                exerList = new Gson().fromJson(exerArray.toString(), new TypeToken<List<Exercise>>() {
                }.getType());
            } catch (JSONException e) {
                Toast.makeText(ctx,"shit", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

        if (param == READ_FULL) { return exerList; }

        List<Exercise> progList = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    ctx.openFileInput("Program")));
            Scanner sc = new Scanner(br);
            while (sc.hasNext()) {
                int i = sc.nextInt() - 1;
                if (i < exerList.size()) {
                    progList.add(exerList.get(i));
                }
            }
            sc.close();
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return progList;
    }
}
