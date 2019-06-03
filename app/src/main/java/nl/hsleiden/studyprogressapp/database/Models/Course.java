package nl.hsleiden.studyprogressapp.database.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

// TODO: make it parcelable for better performance.

@Entity(tableName = "course")
public class Course implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private int ects;

    private double grade;
    private String notes;
    private int period;
    private boolean required;
    private int studyYear;

    /**
     * Wordt gebruikt door Room.
     */
    public Course(int id, String name, int ects, double grade, String notes, int period, int studyYear, boolean required) {
        this.id = id;
        this.name = name;
        this.ects = ects;
        this.grade = grade;
        this.notes = notes;
        this.period = period;
        this.studyYear = studyYear;
        this.required = required;
    }

    /**
     * Omdat room niet met meerdere constructors kan omgaan wordt hier ignore gebruikt.
     */
    @Ignore
    public Course(String name, int ects, double grade, String notes, int period, int studyYear, boolean required) {
        this.name = name;
        this.ects = ects;
        this.grade = grade;
        this.notes = notes;
        this.period = period;
        this.studyYear = studyYear;
        this.required = required;
    }

    public int getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(int studyYear) {
        this.studyYear = studyYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
