package nl.hsleiden.studyprogressapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import javax.inject.Singleton;

import nl.hsleiden.studyprogressapp.database.DAOs.CourseDAO;
import nl.hsleiden.studyprogressapp.database.Models.Course;

/**
 * Room wordt als abstractie laag gebruikt boven op SQLLite.
 */
@Singleton
@Database(entities = {Course.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CourseDAO getCourseDAO();
}

