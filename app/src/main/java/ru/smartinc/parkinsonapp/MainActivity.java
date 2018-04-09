package ru.smartinc.parkinsonapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

import ru.smartinc.parkinsonapp.data.Exercise;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnStartProgram;
    Button btnExerList;
    Button btnResults;
    Button btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar tbMain = (Toolbar) findViewById(R.id.tbMain);
        setSupportActionBar(tbMain);

        btnStartProgram = (Button) findViewById(R.id.btnStartProgram);
        btnExerList = (Button) findViewById(R.id.btnExerList);
        btnResults = (Button) findViewById(R.id.btnResults);
        btnSettings = (Button) findViewById(R.id.btnSettings);

        btnStartProgram.setOnClickListener(this);
        btnExerList.setOnClickListener(this);
        btnResults.setOnClickListener(this);
        btnSettings.setOnClickListener(this);

        //TODO stupid code for now))
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput("Program", MODE_PRIVATE)));
            bw.write("1\n2\n4\n3\n5\n7\n");
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnStartProgram:
                intent = new Intent(this, StartProgramActivity.class);
                break;
            case R.id.btnExerList:
                intent = new Intent(this, ExerListActivity.class);
                break;
            case R.id.btnResults:
                intent = new Intent(this, ResultsActivity.class);
                break;
            case R.id.btnSettings:
                intent = new Intent(this, SettingsActivity.class);
                break;
            default:
                intent = null;
        }
        if (intent != null) startActivity(intent);
    }
}
