package nl.hsleiden.studyprogressapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import nl.hsleiden.studyprogressapp.R;
import nl.hsleiden.studyprogressapp.databinding.ActivityMainBinding;
import nl.hsleiden.studyprogressapp.menu.MenuHandler;
import nl.hsleiden.studyprogressapp.viewModels.MainActivityViewModel;
import nl.hsleiden.studyprogressapp.viewModels.MainActivityViewModelFactory;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Toepassing van Data binding om boilerplate code te voorkomen.
        ActivityMainBinding mainBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);

        //toolbar setup
        setSupportActionBar(mainBinding.toolbar);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        viewModelFactory = new MainActivityViewModelFactory(this);

        MainActivityViewModel viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(MainActivityViewModel.class);

        mainBinding.btnGetDataFromWebservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.resetCoursesFromWebservice();
            }
        });

        CourseListAdapter courseListAdapter = new CourseListAdapter(viewModel.getAllCourses().getValue());
        mainBinding.recycleViewCourseList.setAdapter(courseListAdapter);
        mainBinding.recycleViewCourseList.setHasFixedSize(true);
        mainBinding.recycleViewCourseList.setLayoutManager(linearLayoutManager);

        viewModel.getAllCourses().observe(this, list ->{
            courseListAdapter.setCourses(list);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuHandler.handle(this,item);
        return super.onOptionsItemSelected(item);
    }
}
