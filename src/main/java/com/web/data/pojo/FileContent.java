package com.web.data.pojo;

public class FileContent {
    private String schId;
    private String fileId;
    private String fileName;
    private String fileURL;
    private String fileType;


    public String getSchId() {
        return schId;
    }

    public void setSchId(String schId) {
        this.schId = schId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileCategory() {
        return fileCategory;
    }

    public void setFileCategory(String fileCategory) {
        this.fileCategory = fileCategory;
    }

    public String getFileEnable() {
        return fileEnable;
    }

    public void setFileEnable(String fileEnable) {
        this.fileEnable = fileEnable;
    }

    private String fileCategory;
    private String fileEnable;
}
