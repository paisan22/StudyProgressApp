package nl.hsleiden.studyprogressapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import nl.hsleiden.studyprogressapp.R;
import nl.hsleiden.studyprogressapp.databinding.ActivityMainBinding;
import nl.hsleiden.studyprogressapp.viewModels.MainActivityViewModel;
import nl.hsleiden.studyprogressapp.viewModels.MainActivityViewModelFactory;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toepassing van Data binding om boilerplate code te voorkomen.
        ActivityMainBinding mainBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        CourseListAdapter courseListAdapter = new CourseListAdapter(this);
        mainBinding.recycleViewCourseList.setAdapter(courseListAdapter);
        mainBinding.recycleViewCourseList.setLayoutManager(linearLayoutManager);

        viewModelFactory = new MainActivityViewModelFactory(this);

        MainActivityViewModel viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(MainActivityViewModel.class);

        viewModel.getAllCourses().observe(this, list ->{
            courseListAdapter.setCourses(list);
        });

    }
}
