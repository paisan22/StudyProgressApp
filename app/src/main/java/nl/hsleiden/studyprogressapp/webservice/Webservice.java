package nl.hsleiden.studyprogressapp.webservice;

import java.util.List;

import javax.inject.Singleton;

import nl.hsleiden.studyprogressapp.database.Models.Course;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Maakt gebruik van Retrofit om veel boilerplate code te voorkomen.
 */
@Singleton
public class Webservice {

    private static final String HOST_URL = "http://fuujokan.nl";

    public Call<List<Course>> getCourseRequest() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CourseWebservice courseWebservice = retrofit.create(CourseWebservice.class);
        return courseWebservice.getCourses();

    }

}
