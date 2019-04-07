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
* 生成mapper和xml
* */
public class GeneratorMapperAndXml {
    /*
    * 生成mapper 对应的xml
    * fileName是生成xml的位置
    * */
    public static boolean generatorXml(List<Table> tables, String fileName, String basePackageXml){
        try{

            String templetPath=new File("").getAbsolutePath()+File.separator+"templet";
            String mapperContent = FileUtil.getContent(templetPath+File.separator+"mapperXml.txt");
            String ifXmlContent = FileUtil.getContent(templetPath+File.separator+"ifXml.txt");
            String ifXmlContent1 = FileUtil.getContent(templetPath+File.separator+"ifXml4.txt");
            for(Table table:tables){
                //大写的表名
                String T= UpperCaseUtil.getClassName(table.getName());
                //命名空间
                String namespace=basePackageXml+"."+T+"Mapper";
                String m1= mapperContent;
                m1=m1.replace("[basePackageMapper]",namespace);
                m1=m1.replace("[Table]",T);
                m1=m1.replace("[table]",table.getName());
                m1=m1.replace("[key]",table.getKey());
                m1=m1.replace("[keyType]",table.getKeyType());
                //风格1
               StringBuffer b1=new StringBuffer();
                //风格2
               StringBuffer b2=new StringBuffer();
                //风格3
               StringBuffer b3=new StringBuffer();
                //风格4
               StringBuffer b4=new StringBuffer();
                //风格5
                StringBuffer b5=new StringBuffer();
               //风格1
                for(Column column:table.getColumns()){
                    String columnName=column.getColumnName();
                    //如果是日期类型
                    //风格为5
                    String m2="";
                    if(column.getColumnType().equals("java.util.Date")){
                        m2=ifXmlContent1;
                    }else{
                        m2=ifXmlContent;
                    }

                    //字段大写
                    String C=UpperCaseUtil.getClassName(columnName);
                    m2=m2.replace("[column]",columnName);
                    String s1,s2,s3,s4,s5;
                    s1=s2=s3=s4=s5=m2;
                    s1=s1.replace("[columnContent]","\t"+columnName+"=#{"+columnName+"},");
                    s2=s2.replace("[columnContent]","\t"+columnName+" ,");
                    s3=s3.replace("[columnContent]","#{"+columnName+"} ,");
                    s4=s4.replace("[columnContent]","and "+columnName+" like concat('%',#{"+columnName+"},'%')");
//                    name =xx and xx=#{}
                    s5=s5.replace("[columnContent]","and "+columnName+"=#{"+columnName+"}");
                    //如果是主键就continue;
                    if(column.getColumnKey()!=null&&!column.getColumnKey().equals("")){
                        //b4.append(s4);
                        //continue;
                    }
                    b1.append(s1);
                    b2.append(s2);
                    b3.append(s3);
                    b4.append(s4);
                    b5.append(s5);
                }
                m1=m1.replace("[ifXml.txt]",b1.toString());
                m1=m1.replace("[ifXml1.txt]",b2.toString());
                m1=m1.replace("[ifXml2.txt]",b3.toString());
                m1=m1.replace("[ifXml3.txt]",b4.toString());
                m1=m1.replace("[ifXml4.txt]",b5.toString());
                FileUtil.setContent(fileName+File.separator+T+"Mapper.xml",m1);
            }

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /*
    * 生成mapper接口
    * */
    public static boolean generatorMapper(List<Table> tables, String fileName, String basePackageXml,String basePackageEntity){
        try {
            String templetPath = new File("").getAbsolutePath() + File.separator + "templet";
            String mapperContent = FileUtil.getContent(templetPath + File.separator + "mapper.txt");
            mapperContent = mapperContent.replace("[basePackageXml]", basePackageXml);
            mapperContent = mapperContent.replace("[basePackageEntity]", basePackageEntity);
            //[keyType]
            for (Table table : tables) {
                String mC=mapperContent;
                String tableName = table.getName();
                mC = mC.replace("[Table]", UpperCaseUtil.getClassName(tableName));
                mC = mC.replace("[table]", tableName);
                mC = mC.replace("[key]", table.getKey());
                mC = mC.replace("[keyType]", table.getKeyType());
                FileUtil.setContent(fileName+File.separator+UpperCaseUtil.getClassName(tableName)+"Mapper.java",mC);
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
        boolean b = generatorXml(util.getDbInfo(), file.getAbsolutePath()+File.separator+"mapper", "cn.wsq.mapper");
        System.out.println(b);
        generatorMapper(util.getDbInfo(),file.getAbsolutePath()+File.separator+"mapper","cn.wsq.mapper","cn.wsq.entity");
    }
}
