package cn.wsq.util;

import cn.wsq.database.Util;
import cn.wsq.entity.Column;
import cn.wsq.entity.Table;
import cn.wsq.templet.FileUtil;
import cn.wsq.templet.UpperCaseUtil;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
/*
* 生成实体类对象
* */
public class GeneratorPojo {
    /*
    创建pojo,filePath是目的地,basePath是包名
     */
    public static boolean createPojo(List<Table> tables,String filePath,String basePackageEntity){
        try {
            String templetPath=new File("").getAbsolutePath()+File.separator+"templet";
            //private模板内容
            String privateContent = FileUtil.getContent(templetPath + File.separator + "private.txt");
            //setter模板内容
            String setterContent = FileUtil.getContent(templetPath + File.separator + "setter.txt");
            //表循环
            for (Table table:tables){
                StringBuffer buffer=new StringBuffer();
                //首字母大写的类名
                String T= UpperCaseUtil.getClassName(table.getName());
                buffer.append("package "+basePackageEntity+";\n");
                buffer.append("import java.io.Serializable;\n\n");
                if(table.getComment()!=null&&!table.getComment().equals("")){
                    buffer.append("/**\n*"+table.getComment()+"\n*/\n");
                }
                buffer.append("public class "+T+" implements Serializable {\n");
                //列循环
                for(Column column:table.getColumns()){
                    //列大写
                    String C=UpperCaseUtil.getClassName(column.getColumnName());
                    //private模板内容
                    String c1 = privateContent;
                    c1=c1.replace("[column]",column.getColumnName());
                    if(column.getColumnComment()!=null&&!column.getColumnComment().equals("")) {
                        //有注释
                        c1 = c1.replace("[columnComment]", column.getColumnComment());
                    }else{
                        //没有注释
                        c1=c1.replace("//[columnComment]","");
                    }
                    c1=c1.replace("[type]",column.getColumnType());
                    buffer.append(c1+"\n");

                }
                for(Column column:table.getColumns()){
                    //列大写
                    String C=UpperCaseUtil.getClassName(column.getColumnName());
                    String c2 = setterContent;
                    c2 = c2.replace("[Column]", C);
                    c2=c2.replace("[column]",column.getColumnName());
                    c2=c2.replace("[type]",column.getColumnType());
                    buffer.append(c2);
                }
                buffer.append("}");
                FileUtil.setContent(filePath+File.separator+T+".java",buffer.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Util util = new Util();
        util.setUserName("root");
        util.setPassword("admin");
        util.setUrl("jdbc:mysql://localhost:3306/security?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
        util.setDriverName("com.mysql.cj.jdbc.Driver");
        createPojo(util.getDbInfo(),new File("").getAbsolutePath()+File.separator+"entity","cn.wsq.entity");
    }
}
