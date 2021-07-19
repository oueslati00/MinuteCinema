package com.cinema.minute.Data.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Videodkika {
    private int id;
    private String description;
    private String videoUrl;
    private String editor;
    private String videoName;
    private UploadFile video ;

    @OneToOne
    @JoinColumn(name="video_id" , referencedColumnName = "id")
    public UploadFile getVideo() {
        return video;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "video_url", nullable = true, length = 255)
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Basic
    @Column(name = "editor", nullable = true, length = 255)
    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    @Basic
    @Column(name = "video_name", nullable = true, length = 255)
    public String getVideoName() {
        return videoName;
    }




}
