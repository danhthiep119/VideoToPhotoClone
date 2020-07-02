package com.example.videotophotoclone.Model;

import java.io.Serializable;

public class Video implements Serializable {
    private String title;
    private String duration;
    private String time;
    private String videoPath;

    public Video() {
    }

    public Video(String title, String duration, String time,String videoPath) {
        this.title = title;
        this.duration = duration;
        this.time = time;
        this.videoPath = videoPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
}
