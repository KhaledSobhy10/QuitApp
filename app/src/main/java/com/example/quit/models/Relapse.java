package com.example.quit.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Relapse implements Comparable<Relapse>{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private long relapseDate;
    private String comment;
    private int addictionId;

    @Ignore
    public Relapse() {
    }

    public Relapse(int id, long relapseDate, String comment, int addictionId) {
        this.id = id;
        this.relapseDate = relapseDate;
        this.comment = comment;
        this.addictionId = addictionId;
    }
    @Ignore
    public Relapse(long relapseDate, String comment, int addictionId) {
        this.relapseDate = relapseDate;
        this.comment = comment;
        this.addictionId = addictionId;
    }

    @Ignore
    public Relapse(long relapseDate){
        this.relapseDate = relapseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getRelapseDate() {
        return relapseDate;
    }

    public void setRelapseDate(long relapseDate) {
        this.relapseDate = relapseDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getAddictionId() {
        return addictionId;
    }

    public void setAddictionId(int addictionId) {
        this.addictionId = addictionId;
    }

    @Ignore
    @Override
    public int compareTo(Relapse o) {
        return Long.compare(this.relapseDate, o.relapseDate);
    }
}
