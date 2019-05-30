package nl.hsleiden.studyprogressapp.viewModels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import nl.hsleiden.studyprogressapp.repository.CourseRepository;

public class GradeBarChartActivityViewModelFactory implements ViewModelProvider.Factory {

    private CourseRepository courseRepository;

    public GradeBarChartActivityViewModelFactory(Context context) {
        courseRepository = new CourseRepository(context);
    }

    @NonNull
    @Override
    @SuppressWarnings(value = "unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if(modelClass.isAssignableFrom(GradeBarChartActivityViewModel.class)) {
            return (T) new GradeBarChartActivityViewModel(courseRepository);
        } else {
            throw new IllegalArgumentException("Unknown class");
        }
    }
}
