package nl.hsleiden.studyprogressapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import nl.hsleiden.studyprogressapp.R;
import nl.hsleiden.studyprogressapp.database.Models.Course;
import nl.hsleiden.studyprogressapp.databinding.ActivitySubjectDetailsBinding;
import nl.hsleiden.studyprogressapp.menu.MenuHandler;

public class SubjectDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_details);

        ActivitySubjectDetailsBinding detailsBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_subject_details);

        setSupportActionBar(detailsBinding.subjectDetailsToolbar);

        Course course = (Course) getIntent().getSerializableExtra("course");
        detailsBinding.subjectDetailsNameValue.setText(course.getName());
        detailsBinding.subjectDetailsEctsValue.setText(String.valueOf(course.getEcts()));
        detailsBinding.subjectDetailsGradeValue.setText(String.valueOf(course.getGrade()));
        detailsBinding.subjectDetailsPeriodValue.setText(String.valueOf(course.getPeriod()));

        if(course.getNotes() != null) {
            detailsBinding.subjectDetailsNotesValue.setText(course.getNotes());
        } else {
            detailsBinding.subjectDetailsNotesValue.setText("Nog geen notities...");
        }
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
