package nl.hsleiden.studyprogressapp.webservice;

import java.util.List;

import nl.hsleiden.studyprogressapp.database.Models.Course;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CourseWebservice {

    @GET("/subject_lijst.json")
    Call<List<Course>> getCourses();

}
