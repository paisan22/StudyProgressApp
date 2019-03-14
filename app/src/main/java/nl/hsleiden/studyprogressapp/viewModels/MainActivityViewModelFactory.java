package nl.hsleiden.studyprogressapp.viewModels;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import nl.hsleiden.studyprogressapp.repository.CourseRepository;

/**
 * Zorgt ervoor dat de CourseRepository meegegeven kan worden. Dit heb je dus niet nodig wanneer je
 * een no-arguments viewModel hebt.
 */
public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private CourseRepository courseRepository;

    public MainActivityViewModelFactory(Context context) {
        courseRepository = new CourseRepository(context);
    }

    @NonNull
    @Override
    @SuppressWarnings(value = "unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if(modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel(courseRepository);
        } else {
            throw new IllegalArgumentException("Unknown class");
        }
    }

    public CourseRepository getCourseRepository() {
        return courseRepository;
    }

    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
}
