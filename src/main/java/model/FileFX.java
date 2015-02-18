package model;

import java.io.File;

/**
 * Created by adao-lena on 29/01/2015.
 */
public class FileFX{
    private File file;
    private Boolean isProcessed;
    private String fileName;

    public FileFX(File file) {
        this.file = file;
        fileName = file.getName();
        isProcessed = Boolean.FALSE;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Boolean getIsProcessed() {
        return isProcessed;
    }

    public void setIsProcessed(Boolean isProcessed) {
        this.isProcessed = isProcessed;
    }
}
