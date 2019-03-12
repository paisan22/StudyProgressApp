package nl.hsleiden.studyprogressapp.database.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "course")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private Date startStudyYear;
    private int ECs;
    private String notes;
    private boolean done;

    /**
     * Wordt gebruikt door Room.
     */
    public Course(int id, String title, String description, Date startStudyYear, int ECs, String notes, boolean done) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startStudyYear = startStudyYear;
        this.ECs = ECs;
        this.notes = notes;
        this.done = done;
    }

    /**
     * Omdat room niet met meerdere constructors kan omgaan wordt hier ignore gebruikt.
     */
    @Ignore
    public Course(String title, String description, Date startStudyYear, int ECs, String notes, boolean done) {
        this.title = title;
        this.description = description;
        this.startStudyYear = startStudyYear;
        this.ECs = ECs;
        this.notes = notes;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartStudyYear() {
        return startStudyYear;
    }

    public void setStartStudyYear(Date startStudyYear) {
        this.startStudyYear = startStudyYear;
    }

    public int getECs() {
        return ECs;
    }

    public void setECs(int ECs) {
        this.ECs = ECs;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
