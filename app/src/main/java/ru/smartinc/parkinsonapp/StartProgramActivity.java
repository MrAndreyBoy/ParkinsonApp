package ru.smartinc.parkinsonapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import ru.smartinc.parkinsonapp.data.Exercise;
import ru.smartinc.parkinsonapp.recycler.RecyclerAdapter;
import ru.smartinc.parkinsonapp.recycler.RecyclerFiller;

public class StartProgramActivity extends AppCompatActivity {

    RecyclerView rvProgram;
    TextView tvProgramTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_program);
        Toolbar tbMain = (Toolbar) findViewById(R.id.tbMain);
        setSupportActionBar(tbMain);
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Exercise> progList = RecyclerFiller.fill(this, RecyclerFiller.READ_PROGRAM);

        rvProgram = findViewById(R.id.rvProgram);
        rvProgram.setHasFixedSize(true);
        RecyclerView.LayoutManager rvManager = new LinearLayoutManager(this);
        rvProgram.setLayoutManager(rvManager);
        RecyclerAdapter rAdapter = new RecyclerAdapter(this, progList);
        rvProgram.setAdapter(rAdapter);

        int progTime = 0;
        for(Exercise e : progList){
            progTime += e.getTime();
        }
        tvProgramTime = (TextView) findViewById(R.id.tvProgramTime);
        tvProgramTime.setText("It will take " + (progTime+59)/60 + " minutes");
    }

    public void onClickStart(View view) {
        startActivity(new Intent(this, ExerCardWelcomeActivity.class));
    }

    public void onClickCorrectProgram(View view) {
        startActivity(new Intent(this, CorrectProgramActivity.class));
    }

    public void onClickCancel(View view) {
        finish();
    }
}
