package nl.hsleiden.studyprogressapp.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Objects;

import nl.hsleiden.studyprogressapp.R;
import nl.hsleiden.studyprogressapp.database.Models.Course;
import nl.hsleiden.studyprogressapp.databinding.ActivityAddCourseBinding;
import nl.hsleiden.studyprogressapp.menu.MenuHandler;
import nl.hsleiden.studyprogressapp.repository.CourseRepository;

public class AddCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityAddCourseBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_add_course);

        //toolbar setup
        setSupportActionBar(binding.addCourseToolbar);

        binding.btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = Objects.requireNonNull(binding.addCourseNameValue.getText().toString());
                    Integer ects = Integer.parseInt(Objects.requireNonNull(binding.addCourseEctsValue.getText().toString()));
                    double grade = Double.parseDouble(Objects.requireNonNull(binding.addCourseGradeValue.getText()).toString());
                    int year = Integer.parseInt(Objects.requireNonNull(binding.addCourseStudyYearValue.getText()).toString());
                    int period = Integer.parseInt(binding.addCoursePeriodValue.getText().toString());
                    String notes = Objects.requireNonNull(binding.addCourseNotesValue.getText()).toString();
                    boolean required = binding.addCourseRequiredValue.isChecked();

                    new CourseRepository(getApplicationContext())
                            .insertCourse(new Course(name, ects, grade, notes, period, year, required));

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                } catch (NumberFormatException e) {
                    Toast.makeText(
                            getApplicationContext(),
                            "De ingevoerde gegevens zijn onjuist, probeer het opnieuw",
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuHandler.handle(this, item);
        return super.onOptionsItemSelected(item);
    }
}
