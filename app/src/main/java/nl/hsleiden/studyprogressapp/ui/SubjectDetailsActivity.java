package nl.hsleiden.studyprogressapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import nl.hsleiden.studyprogressapp.R;
import nl.hsleiden.studyprogressapp.database.Models.Course;
import nl.hsleiden.studyprogressapp.databinding.ActivitySubjectDetailsBinding;
import nl.hsleiden.studyprogressapp.menu.MenuHandler;
import nl.hsleiden.studyprogressapp.viewModels.SubjectDetailsActivityViewModel;
import nl.hsleiden.studyprogressapp.viewModels.SubjectDetailsActivityViewModelFactory;

public class SubjectDetailsActivity extends AppCompatActivity {

    private SubjectDetailsActivityViewModelFactory viewModelFactory;

    private static final String TAG = "SubjectDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_details);

        ActivitySubjectDetailsBinding detailsBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_subject_details);

        setSupportActionBar(detailsBinding.subjectDetailsToolbar);

        viewModelFactory = new SubjectDetailsActivityViewModelFactory(
                this,
                (Course) getIntent().getSerializableExtra("course"));

        SubjectDetailsActivityViewModel viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(SubjectDetailsActivityViewModel.class);

        Course course = viewModel.getCourse();
        setView(course, detailsBinding);




        detailsBinding.btnSaveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: bind the whole Course object
                Editable gradeValueText = detailsBinding.subjectDetailsGradeValue.getText();
                Editable notesValueText = detailsBinding.subjectDetailsNotesValue.getText();
                boolean required = detailsBinding.subjectDetailsRequiredValue.isChecked();

                double grade = Double.parseDouble(gradeValueText.toString());
                String notes = notesValueText.toString();

                course.setGrade(grade);
                course.setNotes(notes);
                course.setRequired(required);
                viewModel.updateCourse(course, v.getContext());
            }
        });
    }

    private void setView(Course course, ActivitySubjectDetailsBinding detailsBinding) {

        detailsBinding.subjectDetailsNameValue.setText(course.getName());
        detailsBinding.subjectDetailsEctsValue.setText(String.valueOf(course.getEcts()));
        detailsBinding.subjectDetailsGradeValue.setText(String.valueOf(course.getGrade()));
        detailsBinding.subjectDetailsPeriodValue.setText(String.valueOf(course.getPeriod()));

        if(course.getNotes() != null) {
            detailsBinding.subjectDetailsNotesValue.setText(course.getNotes());
        } else {
            detailsBinding.subjectDetailsNotesValue.setText(getString(R.string.null_notes));
        }
        detailsBinding.subjectDetailsRequiredValue.setChecked(course.isRequired());
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
