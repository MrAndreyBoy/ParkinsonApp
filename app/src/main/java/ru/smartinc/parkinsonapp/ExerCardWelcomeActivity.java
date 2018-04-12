package ru.smartinc.parkinsonapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import ru.smartinc.parkinsonapp.data.Exercise;
import ru.smartinc.parkinsonapp.recycler.RecyclerFiller;

public class ExerCardWelcomeActivity extends AppCompatActivity {

    TextView tvExerName;
    ImageView ivExerIcon;
    Intent intent;
    List<Exercise> progList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exer_card_welcome);
        Toolbar tbMain = (Toolbar) findViewById(R.id.tbMain);
        tbMain.setTitle("Exercise name");
        setSupportActionBar(tbMain);

        final List<Exercise> exerList = RecyclerFiller.fill(this, RecyclerFiller.READ_FULL);
        progList = RecyclerFiller.fill(this, RecyclerFiller.READ_PROGRAM);

        ivExerIcon = (ImageView) findViewById(R.id.ivExerIcon);

        intent = getIntent();
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
        } else {
            tbMain.setTitle(exercise.getName());
            ivExerIcon.setImageResource(getResources()
                    .getIdentifier(exercise.getIcon(), "drawable", "ru.smartinc.parkinsonapp"));
        }
    }

    public void onClickExerStart(View view) {
        //TODO load exercise
        intent.setClass(this, ExerCardActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    int progNum = intent.getIntExtra("progNum", 0);
                    if(progNum == 0)  {
                        startActivity(new Intent(this, FinishActivity.class));
                    } else {
                        intent.putExtra("id", progList.get(progNum).getId());
                        progNum = (progNum + 1)%(progList.size());
                        intent.putExtra("progNum", progNum);
                        intent.setClass(this, ExerCardWelcomeActivity.class);
                        startActivity(intent);
                    }
                    finish();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    public void onClickExerReturn(View view) {
        //TODO if it stop program, it is needed to return to main window and ask "are you sure?"
        finish();
    }
}
