package ru.tpu.lab3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.tpu.lab3.helper.SimpleItemTouchHelperCallback;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements RvAdapter.onLongClickListener {

    public static final String EXTRA_STUDENT = "student";
    public static final String EXTRA_INDEX = "index";
    public static final int    CREATING = 0;
    public static final int    EDITING = 1;

    private RecyclerView rv;
    StudentsManager studentsManager;
    private RvAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.rv);
        FloatingActionButton fab = findViewById(R.id.fab);

        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        studentsManager = App.getInstance().getStudentsManager();
        rv.setAdapter(rvAdapter = new RvAdapter(this));
        rvAdapter.setStudents(studentsManager.getStudents());

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(rvAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rv);

        fab.setOnClickListener((v) ->
                startActivityForResult(new Intent(getBaseContext(), AddStudentActivity.class), CREATING));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Student student = data.getParcelableExtra(EXTRA_STUDENT);
            switch (requestCode) {
                case CREATING:
                    studentsManager.addStudent(student);
                    rvAdapter.setStudents(studentsManager.getStudents());
                    rvAdapter.notifyDataSetChanged();
                    rv.scrollToPosition(rvAdapter.getItemCount() - 1);
                    break;
                case EDITING:
                    int index = data.getIntExtra(EXTRA_INDEX, 0);
                    studentsManager.removeStudent(index);
                    studentsManager.addStudent(student);
                    rvAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onLongClick(int index) {
        Student student = studentsManager.getStudents().get(index);
        Intent intent = new Intent(getBaseContext(), AddStudentActivity.class);
        intent.putExtra(EXTRA_STUDENT, student);
        intent.putExtra(EXTRA_INDEX, index);
        startActivityForResult(intent, EDITING);
    }
}
