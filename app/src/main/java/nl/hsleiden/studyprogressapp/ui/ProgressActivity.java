package nl.hsleiden.studyprogressapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import nl.hsleiden.studyprogressapp.R;
import nl.hsleiden.studyprogressapp.databinding.ActivityProgressBinding;
import nl.hsleiden.studyprogressapp.menu.MenuHandler;
import nl.hsleiden.studyprogressapp.viewModels.ProgressActivityViewModel;
import nl.hsleiden.studyprogressapp.viewModels.ProgressActivityViewModelFactory;

public class ProgressActivity extends AppCompatActivity {

    private ProgressActivityViewModelFactory viewModelFactory;
    private static final String TAG = "ProgressActivity";
    private ProgressActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        ActivityProgressBinding progressBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_progress);

        setSupportActionBar(progressBinding.progressToolbar);

        viewModelFactory = new ProgressActivityViewModelFactory(this);

        this.viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(ProgressActivityViewModel.class);



        this.viewModel.getCourses().observe(this, list -> {
           this.viewModel.setCurrent_ECTS_total(list);
           progressBinding.progressBar.setMax(this.viewModel.getMAX_ECTS());
           progressBinding.maxEctsValue.setText("/ " + this.viewModel.getMAX_ECTS());



            this.viewModel.setCurrent_ECTS(progressBinding.progressBar.getProgress());

            inflateProgressBar(progressBinding);

        });
    }

    public void inflateProgressBar(ActivityProgressBinding progressBinding) {

        Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (viewModel.getCurrent_ECTS() < viewModel.getCurrent_ECTS_total()) {
                    viewModel.incrementCurrentECTS();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBinding.progressBar.setProgress(viewModel.getCurrent_ECTS());
                            progressBinding.currentEctsValue.setText(String.valueOf(viewModel.getCurrent_ECTS()));
                        }
                    });

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
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
