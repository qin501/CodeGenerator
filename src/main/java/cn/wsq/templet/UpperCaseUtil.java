package cn.wsq.templet;

public class UpperCaseUtil {
    /*
    * 首字母变大写
    * */
    public static String getClassName(String str){
        str=String.valueOf(str.charAt(0)).toUpperCase()+str.substring(1);
        return str;
    }

    public static void main(String[] args) {
        String aBBACdddsf = getClassName("asdf");
        System.out.println(aBBACdddsf);
    }
}
