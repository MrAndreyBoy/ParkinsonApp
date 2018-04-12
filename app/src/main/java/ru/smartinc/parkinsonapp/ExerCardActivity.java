package ru.smartinc.parkinsonapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import ru.smartinc.parkinsonapp.data.Exercise;
import ru.smartinc.parkinsonapp.recycler.RecyclerFiller;

public class ExerCardActivity extends AppCompatActivity {

    TextView tvExerName;
    TextView tvTime;
    ImageView ivExerIcon;
    Button btnNext;

    CountDownTimer cdTimer;
    long exerTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exer_card);
        Toolbar tbMain = (Toolbar) findViewById(R.id.tbMain);
        tbMain.setTitle("Exercise name");
        setSupportActionBar(tbMain);

        final List<Exercise> exerList = RecyclerFiller.fill(this, RecyclerFiller.READ_FULL);

        tvTime = (TextView) findViewById(R.id.tvTime);
        ivExerIcon = (ImageView) findViewById(R.id.ivExerIcon);
        btnNext = (Button) findViewById(R.id.btnNext);

        Intent intent = getIntent();

        int id = intent.getIntExtra("id", 0);
        Exercise exercise = null;
        for (Exercise e: exerList) {
            if (e.getId() == id) {
                exercise = e;
                break;
            }
        }
        if (exercise == null) {
            Toast.makeText(this, "Error: Exercise not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tbMain.setTitle(exercise.getName());
        ivExerIcon.setImageResource(getResources()
                .getIdentifier(exercise.getIcon(), "drawable", "ru.smartinc.parkinsonapp"));

        exerTime = exercise.getTime();
        tvTime.setText((exerTime/60) + ":" + (exerTime%60));

        cdTimer = new CountDownTimer(exerTime*1000, 1000) {

            @Override
            public void onTick(long l) {
                exerTime = l/1000;
                tvTime.setText(String.format("%1$d:%2$02d", exerTime/60, exerTime%60));
            }

            @Override
            public void onFinish() {
                tvTime.setText("0:00");
                btnNext.setEnabled(true);
            }
        }.start();
    }

    public void onPause() {
        //TODO get value, save to bundle
        if(cdTimer != null) cdTimer.cancel();
        cdTimer = null;

        super.onPause();
    }

    public void onResume() {
        super.onResume();

        //TODO shitcode
        if((cdTimer == null) && (exerTime != 0))
            cdTimer = new CountDownTimer(exerTime * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    exerTime = l / 1000;
                    tvTime.setText(String.format("%1$d:%2$02d", exerTime/60, exerTime%60));
                }

                @Override
                public void onFinish() {
                    tvTime.setText("0:00");
                    btnNext.setEnabled(true);
                }
            }.start();
    }

    public void onClickPause(View view) {
    }

    public void onClickStop(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onClickNext(View view) {
        setResult(RESULT_OK);
        finish();
    }
}
