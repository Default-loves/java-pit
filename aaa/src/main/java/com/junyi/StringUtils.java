package com.junyi;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * 判断是否为空
     *
     * @param characters
     * @return
     */
    public static boolean isNullOrEempty(String characters) {
        return "".equals(characters) || null == characters ? true : false;
    }

    /**
     * 把集合的长度转化成多少位数
     *
     * @param list
     * @return
     */
    public static String convertNums(List<?> list) {
        StringBuilder str = new StringBuilder();
        list.forEach(v -> {
            str.append("0");
        });
        return str.toString();
    }

    /**
     * 获取文件的名称
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        File tempFile = new File(filePath.trim());
        String fileName = tempFile.getName();
        return fileName;
    }

    /**
     * base64数据加密
     *
     * @param str
     * @return
     */
    public static String encode(String str) {
        if (isNullOrEempty(str)) return "";
        str = Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
        return str;
    }

    /**
     * base64数据解密
     *
     * @param str
     * @return
     */
    public static String dencode(String str) {
        if (isNullOrEempty(str)) return "";
        str = new String(Base64.getDecoder().decode(str),StandardCharsets.UTF_8 );
        return str;
    }

    /**
     * 车牌转卡ID 实现方法，把字符串转16进字符取后16位 <br>
     * 逻辑 Date: 2018年5月16日 下午2:09:11 <br>
     *
     * @author wangchengxi
     */
    public static String CarNotoCardID(String carNo) {
        String result=null;
        if (carNo == null || carNo.trim().length() == 0) result= null;

        try {
            result =bytesToHex(carNo.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (result.length() >= 16) {
            if(result.length()>20){
                result=result.substring(result.length()-20).trim();
            }else {
                result = result.trim();
            }
        } else {
            int dif = 16 - result.length();
            for (int i = 0; i < dif; i++) {
                result = "0" + result;
            }
        }
        return result;
    }

    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * 生成一个uuid编号
     * @return
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }


    /**
     * Map转成实体对象
     * @param map map实体对象包含属性
     * @param clazz 实体对象类型
     * @return
     */
    public static Object map2Object(Map<String, Object> map, Class<?> clazz) {
        if (map == null) {
            return null;
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
        } catch (Exception e) {

        }
        return obj;
    }

    /**
     * 将link转换成对象
     * @param map
     * @return
     */
    public static  <T> T convertObject(LinkedHashMap<String,Object> map,Class<T> clz){
        try {
            String jsonData = JSONUtil.toJson(map);
            return JSONUtil.toBean(jsonData, clz);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算两个数的百分比
     * @param x
     * @param total
     * @return
     */
    public static String getPercent(int x,int total){
        String result="";//接受百分比的值
        double x_double=x*1.0;
        double tempresult= 0d;
        if (total != 0) {
            tempresult = x_double / total;
        }
        //NumberFormat nf   =   NumberFormat.getPercentInstance();     注释掉的也是一种方法
        //nf.setMinimumFractionDigits( 2 );        保留到小数点后几位
        DecimalFormat df1 = new DecimalFormat("0.00%");    //##.00%   百分比格式，后面不足2位的用0补齐
        //result=nf.format(tempresult);
        result= df1.format(tempresult);
        return result;
    }

    /**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches() ){
            return false;
        }
        return true;
    }

    /**
     * 根据传递过来的参数自增加
     * @param seqStr
     * @return
     */
    public static String selfGrowth(String seqStr) {
        String resultStr = "";
        String[] charStrs = seqStr.split("");
        Integer index = 0;
        //从后面开始查询
        for (int i = charStrs.length - 1; i >= 0; i--) {
            if (!isNumeric(charStrs[i]) || charStrs[i] == "0") {
                index = i + 1;
                break;
            }
        }

        String str = seqStr.substring(index);
        boolean frag = true;
        String newStr = "";
        if (!str.equals("")) {
            int oldLen = str.length();            //获取字符截取的长度
            BigInteger integer = new BigInteger(str);
            integer = integer.add(BigInteger.valueOf(1));
            newStr = integer.toString();            //自增数字            ;
            if (newStr.length() != oldLen) {
                newStr = "0" + newStr;
            }
        } else {
            newStr = "0";
        }
        resultStr = seqStr.substring(0, index) + newStr;
        return resultStr;
    }

    /**
     * 字符串以逗号分隔成单个字符串集（'1，2，3'转'1','2'，'3'）
     * @param str
     * @param symbol 分隔的字符
     * @return
     */
    public static String charaSep(String str,String symbol){
        String[] strs=str.split(symbol);
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < strs.length;i++){
            sb.append("'").append(strs[i]).append("',");
        }
        str=sb.toString();
        str=str.substring(0,str.length()-1);
        if(str.equals("''")){
            return null;
        }
        return str;
    }

    /**
     * 正数变成负数
     * @param a
     * @return
     */
    public static int unAbs(int a) {
        return (a > 0) ? -a : a;
    }

    /**
     * 随机生成6位数
     * @return
     */
    public static String randNum(){
        String ranNum= String.valueOf((int)((Math.random()*9+1)*10000000));
        return ranNum;
    }

    /**
     * 转换100
     * @param nums
     * @return
     */
    public static String retanTens(String nums){
        String str="";
        if(!nums.equals("")||nums!=null){
            Double value=Double.parseDouble(nums);
            Double newValue=value/100;
            str=newValue+"";
        }
        return str;
    }

    /**
     * 验证电话格式功能
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone){
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

    /**
     * 验证车牌格式功能
     * @param carNo
     * @return
     */
    public static boolean validateCarNoFormat(String carNo){
            return true;
    }

    public static boolean validateDateFormat(String date, String format){
        if (isNullOrEempty(date) || isNullOrEempty(format)){
            return true;
        }
        try {
            new SimpleDateFormat(format).parse(date);
        }catch (ParseException e){
            return false;
        }
        return true;
    }
    
    /**
     * 半角转全角
     * @param input String.
     * @return 全角字符串.
     */
    public static String ToSBC(String input) {
             char c[] = input.toCharArray();
             for (int i = 0; i < c.length; i++) {
               if (c[i] == ' ') {
                 c[i] = '\u3000';
               } else if (c[i] < '\177') {
                 c[i] = (char) (c[i] + 65248);

               }
             }
             return new String(c);
    }


    /**
     * FLOAT保留多少位小数
     * @param nums 转换的数
     * @param scale 多少位数
     * @return
     */
    public static Float retanFloat(Float nums,int scale){
        if(nums==null){
            return null;
        }

        int   roundingMode  =  4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        BigDecimal bd  =   new  BigDecimal((double)nums);
        bd   =  bd.setScale(scale,roundingMode);
        nums   =  bd.floatValue();
        return nums;
    }
    /**
     * FLOAT保留多少位小数
     * @param nums 转换的数
     * @param scale 多少位数
     * @return
     */
    public static Double retanDouble(Double nums,int scale){
        if(nums==null){
            return null;
        }

        int   roundingMode  =  4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        BigDecimal bd  =   new  BigDecimal(nums);
        bd   =  bd.setScale(scale,roundingMode);
        nums   =  bd.doubleValue();
        return nums;
    }

    public static void main(String[] args) {
      String a="123456";
      System.out.println(ToSBC(a));
    }
}
