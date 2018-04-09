package ru.smartinc.parkinsonapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

import ru.smartinc.parkinsonapp.data.Exercise;
import ru.smartinc.parkinsonapp.recycler.DemoRecyclerAdapter;
import ru.smartinc.parkinsonapp.recycler.RecyclerFiller;

public class ExerListActivity extends AppCompatActivity {

    //final String ATTRIBUTE_NAME_EXERCISES = "exercises";

    RecyclerView rvExerList;
    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exer);
        Toolbar tbMain = (Toolbar) findViewById(R.id.tbMain);
        setSupportActionBar(tbMain);

        final List<Exercise> exerList = RecyclerFiller.fill(this, RecyclerFiller.READ_FULL);

        rvExerList = findViewById(R.id.rvExerList);
        rvExerList.setHasFixedSize(true);
        RecyclerView.LayoutManager rvManager = new LinearLayoutManager(this);
        rvExerList.setLayoutManager(rvManager);
        DemoRecyclerAdapter rAdapter = new DemoRecyclerAdapter(this, exerList);
        rvExerList.setAdapter(rAdapter);

        mode = getIntent().getIntExtra("MODE", 0);

        rAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mode == 0) {
                    Intent intent = new Intent(getBaseContext(), ExerCardWelcomeActivity.class);
                    intent.putExtra("name", ((TextView)(view.findViewById(R.id.tvName))).getText());//data.get(i).get(ATTRIBUTE_NAME_NAME).toString());
                    //intent.putExtra("icon", Integer.parseInt(data.get(i).get(ATTRIBUTE_NAME_ICON).toString()));
                    //intent.putExtra("time", Integer.parseInt(data.get(i).get(ATTRIBUTE_NAME_TIME).toString()));
                    startActivity(intent);
                } else {
                    String name = ((TextView)(view.findViewById(R.id.tvName))).getText().toString();
                    int id = 0;
                    for (Exercise e: exerList) {
                        if (e.getName() == name) {
                           id = e.getId();
                           break;
                        }
                    }
                    Intent intent = new Intent(getBaseContext(), ExerCardWelcomeActivity.class);
                    intent.putExtra("id", id);//data.get(i).get(ATTRIBUTE_NAME_NAME).toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
    }

    public void onClickReturn(View view) {
        finish();
    }
}
