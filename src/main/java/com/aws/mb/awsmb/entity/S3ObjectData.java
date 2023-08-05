package com.aws.mb.awsmb.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class S3ObjectData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private Long size;
    private String extension;

    private Date getLastModified;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Date getGetLastModified() {
        return getLastModified;
    }

    public void setGetLastModified(Date getLastModified) {
        this.getLastModified = getLastModified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
