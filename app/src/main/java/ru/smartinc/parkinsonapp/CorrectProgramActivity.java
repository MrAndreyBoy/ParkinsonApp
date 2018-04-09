package ru.smartinc.parkinsonapp;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import ru.smartinc.parkinsonapp.data.Exercise;
import ru.smartinc.parkinsonapp.recycler.RecyclerAdapter;
import ru.smartinc.parkinsonapp.recycler.RecyclerFiller;
import ru.smartinc.parkinsonapp.recycler.RecyclerItemTouchHelper;

public class CorrectProgramActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    //final String ATTRIBUTE_NAME_EXERCISES = "exercises";

    CoordinatorLayout clMain;
    RecyclerView rvCorrProgram;
    RecyclerAdapter rvAdapter;
    private List<Exercise> progList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct_program);
        Toolbar tbMain = (Toolbar) findViewById(R.id.tbMain);
        setSupportActionBar(tbMain);

        clMain = (CoordinatorLayout)findViewById(R.id.clMain);

        progList = RecyclerFiller.fill(this, RecyclerFiller.READ_PROGRAM);

        rvCorrProgram = findViewById(R.id.rvCorrProgram);
        rvCorrProgram.setHasFixedSize(true);
        RecyclerView.LayoutManager rvManager = new LinearLayoutManager(this);
        rvCorrProgram.setLayoutManager(rvManager);
        rvAdapter = new RecyclerAdapter(this, progList);
        rvCorrProgram.setAdapter(rvAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelper =
                new RecyclerItemTouchHelper(ItemTouchHelper.DOWN|ItemTouchHelper.UP, ItemTouchHelper.LEFT, this);
        ItemTouchHelper rvItemTouchHelper = new ItemTouchHelper(itemTouchHelper);
        rvItemTouchHelper.attachToRecyclerView(rvCorrProgram);
        rvAdapter.setItemTouchHelper(rvItemTouchHelper);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_correct_program, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(this, ExerListActivity.class);
                intent.putExtra("MODE", 1);
                startActivityForResult(intent, 0);
                return true;
            case R.id.action_generate:
                Toast.makeText(this, "Shoud be dialog, but... :)", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    int id = data.getIntExtra("id", 0);
                    Exercise e = RecyclerFiller.fill(this, RecyclerFiller.READ_FULL).get(id-1);
                    if (e != null) {
                        progList.add(e);
                        rvAdapter.notifyItemInserted(progList.size()-1);
                    }
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof RecyclerAdapter.ViewHolder) {
            final String text = ((RecyclerAdapter.ViewHolder) viewHolder)
                    .getTvName().getText().toString();


            final int deletedIndex = viewHolder.getAdapterPosition();

            final Exercise deletedItem = rvAdapter.removeItem(deletedIndex);

            Snackbar snackbar = Snackbar.make(clMain, text + " deleted", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rvAdapter.restoreItem(deletedIndex, deletedItem);
                }
            });
            snackbar.show();
        }
    }

    @Override
    public void onMoved(int fromPos, int toPos) {
        rvAdapter.moveItem(fromPos, toPos);
    }

    public void onClickSaveChanges(View view) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput("Program", MODE_PRIVATE)));
            for (Exercise e: progList) {
                bw.write(String.format("%1$d\n",e.getId()));
            }
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        finish();
    }
}
