package cn.wsq.util;

import cn.wsq.database.Util;

import java.io.File;
import java.sql.SQLException;

public class GeneratorAll {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        File file = new File("");
        Util util=new Util();
        util.setUserName("root");
        util.setPassword("admin");
        util.setUrl("jdbc:mysql://localhost:3306/admin?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
        util.setDriverName("com.mysql.cj.jdbc.Driver");
        String path=file.getAbsolutePath();
        boolean b = GeneratorController.generatorController(util.getDbInfo(), path + File.separator + "controller", "cn.wsq.entity", "cn.wsq.service", "cn.wsq.controller");

        GeneratorMapperAndXml.generatorXml(util.getDbInfo(), file.getAbsolutePath()+File.separator+"mapper", "cn.wsq.mapper");

        GeneratorMapperAndXml.generatorMapper(util.getDbInfo(),file.getAbsolutePath()+File.separator+"mapper","cn.wsq.mapper","cn.wsq.entity");

        GeneratorPage.generatorPage(util.getDbInfo(),path+File.separator+"page");

        GeneratorPojo.createPojo(util.getDbInfo(),new File("").getAbsolutePath()+File.separator+"entity","cn.wsq.entity");

        GeneratorService.generatorService(util.getDbInfo(), path + "/service", path + "/service/impl", "cn.wsq.entity", "cn.wsq.mapper",
                "cn.wsq.service", "cn.wsq.serviceImpl");

    }
}
