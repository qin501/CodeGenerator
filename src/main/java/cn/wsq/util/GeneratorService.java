package cn.wsq.util;

import cn.wsq.database.Util;
import cn.wsq.entity.Table;
import cn.wsq.templet.FileUtil;
import cn.wsq.templet.UpperCaseUtil;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class GeneratorService {
    /*
    * 生成service以及实现类
    * */
    public static boolean generatorService(List<Table> tables, String filePath,String filePath1,String basePackageEntity,String basePackageXml,
    String basePackageService,String basePackageServiceImpl){
        try{
            //模板的位置
            String templetPath=new File("").getAbsolutePath()+File.separator+"templet";
            String contentService = FileUtil.getContent(templetPath + File.separator + "service.txt");
            String contentServiceImpl = FileUtil.getContent(templetPath + File.separator + "serviceImpl.txt");
            contentService=contentService.replace("[basePackageService]",basePackageService);
            contentService=contentService.replace("[basePackageEntity]",basePackageEntity);
            contentServiceImpl=contentServiceImpl.replace("[basePackageServiceImpl]",basePackageServiceImpl);
            contentServiceImpl=contentServiceImpl.replace("[basePackageEntity]",basePackageEntity);
            contentServiceImpl=contentServiceImpl.replace("[basePackageXml]",basePackageXml);
            contentServiceImpl=contentServiceImpl.replace("[basePackageService]",basePackageService);
            for(Table table:tables){
                String c1=contentService;
                String tableName=table.getName();
                c1=c1.replace("[Key]",UpperCaseUtil.getClassName(table.getKey()));
                c1=c1.replace("[table]",tableName);
                c1=c1.replace("[Table]", UpperCaseUtil.getClassName(tableName));
                c1=c1.replace("[keyType]",table.getKeyType());
                //生成service接口
                FileUtil.setContent(filePath+File.separator+UpperCaseUtil.getClassName(tableName)+"Service.java",c1);
                //生成service实现类
                String c2=contentServiceImpl;
                c2=c2.replace("[Key]",UpperCaseUtil.getClassName(table.getKey()));
                c2=c2.replace("[table]",tableName);
                c2=c2.replace("[Table]", UpperCaseUtil.getClassName(tableName));
                c2=c2.replace("[keyType]",table.getKeyType());
                FileUtil.setContent(filePath1+File.separator+UpperCaseUtil.getClassName(tableName)+"ServiceImpl.java",c2);
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
        boolean b = generatorService(util.getDbInfo(), path + "/service", path + "/service/impl", "cn.wsq.entity", "cn.wsq.mapper",
                "cn.wsq.service", "cn.wsq.serviceImpl");
        System.out.println(b);

    }
}
