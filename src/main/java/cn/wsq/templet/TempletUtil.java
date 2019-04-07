package cn.wsq.templet;

import cn.wsq.entity.Templet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TempletUtil {
    /*
     * 根据目录查找所有模板文件
     * */
    public static List<Templet> getTempletList(String basePath) {
    List<Templet> list = null;
    File root = new File(basePath);
    try{
        list=showAllFiles(basePath,root);
    }catch (Exception e){
        e.printStackTrace();
    }
    return list;
    }
    private static List<Templet> showAllFiles(String basePath, File dir) {
        List<Templet> list=new ArrayList<>();
        File[] fs=dir.listFiles();
        for(File element:fs){
            Templet templet=new Templet();
            templet.setAllPath(element.getAbsolutePath());//原目录
            File file=new File(element.getAbsolutePath());
            templet.setPath(file.getPath().replace(basePath,""));//相对目录
            templet.setFileName(file.getName());//文件名
            if(element.isDirectory()){
                try{
                    list.addAll(showAllFiles(basePath,element));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                list.add(templet);
            }

        }
        return list;
    }

    public static void main(String[] args) {
        List<Templet> templetList = getTempletList("e:\\test");
        System.out.println(templetList);
    }
}
