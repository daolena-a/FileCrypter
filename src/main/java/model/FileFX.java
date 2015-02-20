package model;

import java.io.File;
import java.util.List;

/**
 * Created by adao-lena on 29/01/2015.
 */
public class FileFX{
    private File file;
    private Boolean isProcessed;
    private String fileName;
    FileFX parent;
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

    public FileFX getParent() {
        return parent;
    }

    public void setParent(FileFX parent) {
        this.parent = parent;
    }

    /**
     *
     * @return
     */
    public String getParentPath(){
        if(parent == null) return "";
        StringBuilder sb = new StringBuilder();
        FileFX temp = this;
        while(temp.getParent() != null){
            sb.insert(0,File.separator+temp.getParent().getFile().getName());
            temp = temp.getParent();
        }
        return sb.toString();
    }
}
