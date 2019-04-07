package cn.wsq.templet;

import cn.wsq.entity.Column;
import cn.wsq.entity.Table;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 模板处理类 用于替换内容
* */
public class ClientTempletUtil {
    /*
    * 根据目录查找 文件名key  内容为value
    * */
    public static Map<String,String> getTempletList(String basePath){
        Map<String,String> map=new HashMap<>();
        File root=new File(basePath);
        try{
            map=showAllFiles(basePath,root);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    private static Map<String, String> showAllFiles(String basePath, File dir) {
        Map<String,String> map=new HashMap<>();
        File[] fs=dir.listFiles();
        for(File element:fs){
            File file=new File(element.getAbsolutePath());
            //将读取的子模板的内容放在map集合中
            if(element.isDirectory()){
                try{
                    map.putAll(showAllFiles(basePath,element));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                map.put(file.getName(),FileUtil.getContent(element.getAbsolutePath()));
            }
        }
        return map;
    }
    /*
    * 替换内容把子内容加上
    * */
    public static String createContent(String oldContent, Map<String,String> map, Table table){
        for (String ks:map.keySet()){
            String thf="<"+ks+">";//替换符号
            if(oldContent.indexOf(thf)>=0){
                String foreachContent=map.get(ks);
                StringBuilder createContent=new StringBuilder();
                for(Column column:table.getColumns()){
                    boolean b=true;
                    //只循环主键
                    if(ks.indexOf(".key")>=0){
                        //不是主键
                        if (!column.getColumnName().equals("PRI")){
                            b=false;
                        }
                    }
                    //只循环非主键
                    if (ks.indexOf(".nokey")>=0){
                        if(column.getColumnKey().equals("PRI")){
                            b=false;
                        }
                    }
                    //只循环String 类型
                    if(ks.indexOf(".String")>=0){
                        if (!column.getColumnType().equals("String")){
                            b=false;
                        }
                    }
                    if(b){
                        System.out.println("替换符号内容："+foreachContent);
                        String newContent=foreachContent.replace("[column]",column.getColumnName());
                        newContent=newContent.replace("[Column]",column.getColumnName());
                        newContent=newContent.replace("[type]",column.getColumnType());//java类型
                        newContent=newContent.replace("[dbtype]",column.getColumnDbType());//数据库类型
                        newContent=newContent.replace("[columnComment]",column.getColumnComment());//备注
                        createContent.append(newContent);
                        System.out.println("替换后内容："+newContent);
                    }
                }
                oldContent=oldContent.replace(thf,createContent);
            }
        }
        oldContent=oldContent.replace("[table]",table.getName());
        oldContent=oldContent.replace("[Table",UpperCaseUtil.getClassName(table.getName()));
        oldContent.replace("[comment]",table.getComment());//备注
        if (table.getKey()!=null){
            oldContent=oldContent.replace("[key]",table.getKey());
        }
        return oldContent;
    }
    public static String createContentForTable(String oldContent, Map<String,String> map, List<Table> tables){
        //循环所有子替换符
        for(String ks:map.keySet()){
            String thf="<"+ks+">";//替换符号
            if(oldContent.indexOf(thf)>=0){
                String foreachContent=map.get(ks);
                StringBuilder createContent=new StringBuilder();
                for (Table table:tables){
                    boolean b=true;//控制开关
                    //根据模板生成新内容
                    if (b){
                        String newContent=foreachContent.replace("[table]",table.getName());
                        newContent=newContent.replace("[Table]",UpperCaseUtil.getClassName(table.getName()));;
                        if(table.getKey()!=null){
                            newContent=newContent.replace("[key]",table.getKey());
                        }
                        newContent=newContent.replace("[comment]",table.getComment());
                        createContent.append(newContent);
                    }
                }
                oldContent=oldContent.replace(thf,createContent.toString());
            }
        }
        return oldContent;
    }
    /*
    * 替换全局替换符
    * */
    public static String createContent(String oldContent,Map<String,String> map){
        //循环所有子替换符
        for (String ks:map.keySet()){
            oldContent=oldContent.replace("["+ks+"]",map.get(ks));
        }
        return oldContent;
    }


    public static void main(String[] args) {
        Map<String, String> templetList = getTempletList("E:\\test");
        System.out.println(templetList);
    }
}
