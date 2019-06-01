package nl.hsleiden.studyprogressapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

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

        // Applying data-binding to prevent boilerplate code in increasing performance
        ActivityMainBinding mainBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);

        //toolbar setup
        setSupportActionBar(mainBinding.toolbar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        viewModelFactory = new MainActivityViewModelFactory(this);

        MainActivityViewModel viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(MainActivityViewModel.class);

        String reset = getIntent().getStringExtra("reset");

        if(reset != null && reset.equals("yes")) {
            viewModel.resetCoursesFromWebservice();
        }

        CourseListAdapter courseListAdapter = new CourseListAdapter();

        mainBinding.recycleViewCourseList.setAdapter(courseListAdapter);
        mainBinding.recycleViewCourseList.setHasFixedSize(true);
        mainBinding.recycleViewCourseList.setLayoutManager(linearLayoutManager);

        viewModel.getAllCourses().observe(this, courses -> {
            courseListAdapter.setCourses(courses);
        });

        // searchView setup
        mainBinding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                courseListAdapter.getFilter().filter(newText);
                return false;
            }
        });
        mainBinding.searchBar.setQueryHint(getString(R.string.searchView_placeholder));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuHandler.handle(this,item);
        return super.onOptionsItemSelected(item);
    }
}
