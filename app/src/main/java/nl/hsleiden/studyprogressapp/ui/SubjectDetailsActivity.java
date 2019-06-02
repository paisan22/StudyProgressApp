package nl.hsleiden.studyprogressapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Objects;

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

        detailsBinding.btnSaveDelete.setOnClickListener(v -> {

            new AlertDialog.Builder(this)
                    .setTitle("Verwijderen vak")
                    .setMessage(String.format("Weet je zeker dat je het vak %s wilt verwijderen", course.getName()))
                    .setPositiveButton("Ja", (dialog, which) -> {
                        viewModel.deleteCourse(course);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    })
                    .setNegativeButton("Nee", null)
                    .show();
        });

        detailsBinding.btnSaveDetails.setOnClickListener(v -> {

            try {
                String name = Objects.requireNonNull(detailsBinding.subjectDetailsNameValue.getText()).toString();
                int ects = Integer.parseInt(detailsBinding.subjectDetailsEctsValue.getText().toString());
                double grade = Double.parseDouble(Objects.requireNonNull(detailsBinding.subjectDetailsGradeValue.getText()).toString());
                int studyYearValueText = Integer.parseInt(Objects.requireNonNull(detailsBinding.subjectDetailsStudyYearValue.getText()).toString());
                int period = Integer.parseInt(detailsBinding.subjectDetailsPeriodValue.getText().toString());
                String notes = Objects.requireNonNull(detailsBinding.subjectDetailsNotesValue.getText()).toString();
                boolean required = detailsBinding.subjectDetailsRequiredValue.isChecked();

                course.setName(name);
                course.setEcts(ects);
                course.setGrade(grade);
                course.setStudyYear(studyYearValueText);
                course.setPeriod(period);
                course.setNotes(notes);
                course.setRequired(required);

                viewModel.updateCourse(course, v.getContext());
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "De wijzigingen zijn niet gelukt door onjuiste invoer.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setView(Course course, ActivitySubjectDetailsBinding detailsBinding) {

        detailsBinding.subjectDetailsNameValue.setText(course.getName());
        detailsBinding.subjectDetailsEctsValue.setText(String.valueOf(course.getEcts()));
        detailsBinding.subjectDetailsGradeValue.setText(String.valueOf(course.getGrade()));
        detailsBinding.subjectDetailsPeriodValue.setText(String.valueOf(course.getPeriod()));
        detailsBinding.subjectDetailsStudyYearValue.setText(String.valueOf(course.getStudyYear()));

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
