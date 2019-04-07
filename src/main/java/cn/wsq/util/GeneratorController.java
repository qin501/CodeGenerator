package cn.wsq.util;

import cn.wsq.database.Util;
import cn.wsq.entity.Table;
import cn.wsq.templet.FileUtil;
import cn.wsq.templet.UpperCaseUtil;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class GeneratorController {
    /*
    * 生成controller层
    * */
    public static boolean generatorController(List<Table> tables, String filePath,String basePackageEntity,String basePackageService,String basePackageController){
        try {
            String templetPath=new File("").getAbsolutePath();
            String content = FileUtil.getContent(templetPath+File.separator+"templet"+File.separator+"controller.txt");
            content=content.replace("[basePackageEntity]",basePackageEntity);
            content=content.replace("[basePackageService]",basePackageService);
            content=content.replace("[basePackageController]",basePackageController);
            for (Table table:tables){
                String c1=content;
                String tableName=table.getName();
                c1=c1.replace("[table]",tableName);
                c1=c1.replace("[Table]", UpperCaseUtil.getClassName(tableName));
                c1=c1.replace("[keyType]",table.getKeyType());
                //生成controller
                FileUtil.setContent(filePath+File.separator+UpperCaseUtil.getClassName(tableName)+"Controller.java",c1);
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
        boolean b = generatorController(util.getDbInfo(), path + File.separator + "controller", "cn.wsq.entity", "cn.wsq.service", "cn.wsq.controller");
        System.out.println(b);
    }

}
