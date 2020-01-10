package ru.tpu.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import static ru.tpu.lab3.MainActivity.EXTRA_INDEX;
import static ru.tpu.lab3.MainActivity.EXTRA_STUDENT;

public class AddStudentActivity extends AppCompatActivity {

    EditText etFirstName;
    EditText etLastName;
    EditText etPatronymic;
    TextInputLayout tilFirstName;
    TextInputLayout tilLastName;
    TextInputLayout tilPatronymic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etPatronymic = findViewById(R.id.et_patronymic);
        tilFirstName = findViewById(R.id.input_first_name);
        tilLastName = findViewById(R.id.input_last_name);
        tilPatronymic = findViewById(R.id.input_patronymic);

        Student student = getIntent().getParcelableExtra(EXTRA_STUDENT);
        if (student != null) {
            etFirstName.setText(student.firstName);
            etLastName.setText(student.lastName);
            etPatronymic.setText(student.patronymic);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_student, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_save:
                if (validate()) {
                    Student student = new Student();
                    student.firstName = etFirstName.getText().toString();
                    student.lastName = etLastName.getText().toString();
                    student.patronymic = etPatronymic.getText().toString();

                    Intent intent = getIntent();
                    intent.putExtra(EXTRA_STUDENT, student);
                    intent.putExtra(EXTRA_INDEX, getIntent().getIntExtra(EXTRA_INDEX, 0));
                    setResult(RESULT_OK, intent);
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validate() {
        boolean valid = true;
        if (etFirstName.getText().toString().isEmpty()) {
            tilFirstName.setError(getResources().getString(R.string.empty));
            valid = false;
        }
        if (etLastName.getText().toString().isEmpty()) {
            tilLastName.setError(getResources().getString(R.string.empty));
            valid = false;
        }
        if (etPatronymic.getText().toString().isEmpty()) {
            tilPatronymic.setError(getResources().getString(R.string.empty));
            valid = false;
        }
        return valid;
    }
}
