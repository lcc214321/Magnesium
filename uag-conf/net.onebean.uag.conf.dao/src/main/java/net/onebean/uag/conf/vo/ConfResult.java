package net.onebean.uag.conf.vo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成配置以后的结果，此结果主要处理nginx需要用到
 * @author 0neBean
 */
public class ConfResult implements Serializable {

    private static final long serialVersionUID = 14262496830063573L;

    // 涉及更新的条目，只能是文件，不能是文件夹
    private List<String> coverFiles = new ArrayList<String>();
    // 需要删除的条目,只能是文件，不能是文件夹
    private List<String> removeFiles = new ArrayList<String>();

    public List<String> getCoverFiles() {
        return coverFiles;
    }

    public List<String> getRemoveFiles() {
        return removeFiles;
    }

    public void addCoverFile(String coverFile) {
        coverFiles.add(coverFile);
    }

    public void addRemoveFile(String removeFile) {
        removeFiles.add(removeFile);
    }

    public void addCoverFiles(List<String> addCoverFiles){
        if(this.coverFiles == null){
            this.coverFiles = new ArrayList<>();
        }
        if(addCoverFiles != null){
            this.coverFiles.addAll(addCoverFiles);
        }
    }

    public void addRemoveFiles(List<String> addRemoveFiles){
        if(this.removeFiles == null){
            this.removeFiles = new ArrayList<>();
        }
        if(addRemoveFiles != null){
            // 为了兼容以前的代码，这里使用remove方式去重复，推荐使用hashSet
            this.removeFiles.removeAll(addRemoveFiles);
            this.removeFiles.addAll(addRemoveFiles);
        }
    }

}
