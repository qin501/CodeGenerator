package cn.wsq.templet;

import java.io.*;

public class FileUtil {
    /*
    * 获取文件内容
    * */
    public static String getContent(String fileName) {
        File file=new File(fileName);
        String result="";
        try{
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader read=new BufferedReader(inputStreamReader);
            String s=null;
            while((s=read.readLine())!=null){//使用readLine方法，一次读一行
                result+=s+"\n";
            }
            read.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    /*
    * 设置文件内容
    * */
    public static void setContent(String filePath,String content){
        try{
            File f=new File(filePath);
            if(f.exists()){
                f.delete();
            }
            File pathf=f.getParentFile();
            if(!pathf.exists()){
                pathf.mkdirs();
            }
            f.createNewFile();//不存在则创建
            FileOutputStream fout=new FileOutputStream(f);
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(fout,"UTF-8"));
            writer.write(content);
            writer.close();
            System.out.println("生成代码："+filePath);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*
    * 获取文件扩展名
    * */
    public static String getExtensionName(String filename){
        if(filename!=null&&filename.length()>0){
            int dot=filename.lastIndexOf('.');
            if(dot>-1&&dot<filename.length()-1){
                return filename.substring(dot+1);
            }
        }
        return filename;
    }
    /*
    * 判断文件是否为模板类文件
    * */
    public static boolean isTemplatFile(String filename){
        String extensionName = getExtensionName(filename);
        if (extensionName.equals("html")){
            return true;
        }
        if (extensionName.equals("java")){
            return true;
        }
        if (extensionName.equals("xml")){
            return true;
        }
        if (extensionName.equals("htm")){
            return true;

        }
        if (extensionName.equals("jsp")){
            return true;
        }
        if (extensionName.equals("project")){
            return true;
        }
        if (extensionName.equals("component")){
            return true;
        }
        if (extensionName.indexOf("(TFILE)")>0){
            return true;
        }
        return false;
    }
    /*
    * 复制文件
    * */
    public static void copyFile(String oldPath,String newPath){
        try{
            int bytesum=0;
            int byteread=0;
            File oldfile=new File(oldPath);
            if(oldfile.exists()){//当文件存在时操作
                File newpath=new File(newPath);
                if(!newpath.getParentFile().exists()){
                    newpath.getParentFile().mkdirs();
                }
                InputStream inputStream=new FileInputStream(oldPath);
                OutputStream outputStream=new FileOutputStream(newPath);
                byte[] b=new byte[1024];
                while((byteread=inputStream.read(b))!=-1){
                    bytesum+=byteread;//字节数大小，可以不用
                    outputStream.write(b,0,byteread);
                }
                inputStream.close();
                outputStream.close();

            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("复制文件出错原文件："+oldPath+"新文件："+newPath);
        }

    }


    public static void main(String[] args) {
        String extensionName = getExtensionName("789.text");
        System.out.println(extensionName);
    }
}
