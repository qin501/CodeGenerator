package cn.wsq.util;

import cn.wsq.database.Util;
import cn.wsq.entity.Column;
import cn.wsq.entity.Table;
import cn.wsq.templet.FileUtil;
import cn.wsq.templet.UpperCaseUtil;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class GeneratorPage {
    public static boolean generatorPage(List<Table> tables, String filePath){
        try{
            String pageTemplet=new File("").getAbsolutePath()+File.separator+"templet";
            String content= FileUtil.getContent(pageTemplet+File.separator+"page.txt");
            String pageForm = FileUtil.getContent(pageTemplet + File.separator + "pageForm.txt");
            String pageSearch = FileUtil.getContent(pageTemplet + File.separator + "pageSearch.txt");
            String pageColumns =FileUtil.getContent(pageTemplet + File.separator+"pagecolumns.txt");
            for(Table table:tables){
                String tableName= UpperCaseUtil.getClassName(table.getName());
                String c=content;
                c=c.replace("[key]",table.getKey());
                c=c.replace("[Comment]",table.getComment());
                c=c.replace("[Table]",tableName);
                c=c.replace("[table]",table.getName());
                StringBuffer buffer=new StringBuffer();
                StringBuffer buffer1=new StringBuffer();
                StringBuffer buffer2=new StringBuffer();
                for(Column column:table.getColumns()){
                    String c1=pageForm;
                    String c2=pageSearch;
                    String c3=pageColumns;
                    //是不是主键
                    if(column.getColumnKey().equals("PRI")){
                        c1=c1.replace("[textType]","hidden");
                        c1=c1.replace("[columnComment]","");
                    }else {
                        c1=c1.replace("[textType]","text");
                        c1=c1.replace("[columnComment]",column.getColumnComment());
                    }
                    c3=c3.replace("[columnComment]",column.getColumnComment());
                    c3=c3.replace("[column]",column.getColumnName());
                    c2=c2.replace("[columnComment]",column.getColumnComment());
                    c2=c2.replace("[column]",column.getColumnName());
                    c1=c1.replace("[column]",column.getColumnName());
                    buffer.append(c1);
                    buffer1.append(c2);
                    buffer2.append(c3);
                }

                c=c.replace("[pageForm]",buffer.toString());
                c=c.replace("[pageSearch]",buffer1.toString());
                c=c.replace("[pagecolumns]",buffer2.toString());
                FileUtil.setContent(filePath+File.separator+table.getName()+".html",c);
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        File file = new File("");
        Util util=new Util();
        util.setUserName("root");
        util.setPassword("admin");
        util.setUrl("jdbc:mysql://localhost:3306/security?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
        util.setDriverName("com.mysql.cj.jdbc.Driver");
        String path=file.getAbsolutePath();
        generatorPage(util.getDbInfo(),path+File.separator+"error");
    }
}
