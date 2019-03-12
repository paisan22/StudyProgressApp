package nl.hsleiden.studyprogressapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import nl.hsleiden.studyprogressapp.database.DAOs.CourseDAO;
import nl.hsleiden.studyprogressapp.database.Models.Course;

/**
 * Room wordt als abstractie laag gebruikt boven op SQLLite.
 */
@Database(entities = {Course.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CourseDAO courseDAO();

}
