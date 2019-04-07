package cn.wsq.pojo;

import cn.wsq.database.Util;
import cn.wsq.entity.Column;
import cn.wsq.entity.Table;
import cn.wsq.templet.FileUtil;
import cn.wsq.templet.UpperCaseUtil;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class PojoUtil {
    public static boolean createPojo(String filePath) throws SQLException, ClassNotFoundException {
        Util util = new Util();
        util.setUserName("root");
        util.setPassword("admin");
        util.setUrl("jdbc:mysql://localhost:3306/security?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
        util.setDriverName("com.mysql.cj.jdbc.Driver");
//        List<String> schemas = util.getSchemas();
        List<Table> dbInfo = util.getDbInfo();
        for (Table table:dbInfo){
            String content="";
            content="public class "+UpperCaseUtil.getClassName(table.getName())+"{"+"\n";
            List<Column> columns = table.getColumns();
            for(Column column:columns){
                content+="\t"+"private "+column.getColumnType()+" "+column.getColumnName()+";"+"\n";
            }
            for(Column c2:columns){
                content+="\t"+"public void set"+UpperCaseUtil.getClassName(c2.getColumnName())+"("+c2.getColumnType() +" "+c2.getColumnName()+"){"+"\n";
                content+="\t\t"+"this."+c2.getColumnName()+"="+c2.getColumnName()+";\n";
                content+="\t}\n";
                content+="\t"+"public "+c2.getColumnType()+ " get"+UpperCaseUtil.getClassName(c2.getColumnName())+"(){"+"\n";
                content+="\t\treturn this."+c2.getColumnName()+";\n";
                content+="\t}"+"\n";
            }
            content+="}";
            FileUtil.setContent(filePath+File.separator+ UpperCaseUtil.getClassName(table.getName())+".java",content);
        }

        return true;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        File file = new File("");
        String filePath=file.getAbsolutePath()+File.separator+"cn"+File.separator+"wsq"+File.separator+"pojo";
        createPojo(filePath);

    }
}
