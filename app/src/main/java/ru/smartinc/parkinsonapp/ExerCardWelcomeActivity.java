package ru.smartinc.parkinsonapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ExerCardWelcomeActivity extends AppCompatActivity {

    TextView tvExerName;
    ImageView ivExerIcon;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exer_card_welcome);
        Toolbar tbMain = (Toolbar) findViewById(R.id.tbMain);
        setSupportActionBar(tbMain);

        tvExerName = (TextView) findViewById(R.id.tvExerName);
        ivExerIcon = (ImageView) findViewById(R.id.ivExerIcon);

        intent = getIntent();
        tvExerName.setText(intent.getStringExtra("name"));
        ivExerIcon.setImageResource(intent.getIntExtra("icon", R.mipmap.ic_launcher_round));
    }

    public void onClickExerStart(View view) {
        //TODO load exercise
        intent.setClass(this, ExerCardActivity.class);
        startActivity(intent);
    }

    public void onClickExerReturn(View view) {
        //TODO if it stop program, it is needed to return to main window and ask "are you sure?"
        finish();
    }
}
