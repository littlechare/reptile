package com.advance.reptile.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.UUID;

/**
 * 定义一些常用的处理方法
 * @author advance
 */
public class CommonUtils {

    Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    private CommonUtils() {

    }

    /**
     * 主键生成器
     * @return
     */
    public static String getUuid(){
        return UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
    }

    /**
     * 数据密码加盐加密工具
     */
    public static String PasswordEncoder(String password, String salt){
        byte[] saltBytes = salt.getBytes();
        try {
            MessageDigest msgDigest  = MessageDigest.getInstance("SHA");
            if (saltBytes != null && saltBytes.length > 0){
                msgDigest.update(saltBytes);
            }

            byte[] digest = msgDigest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

//    public static void main(String[] args) {
//        System.out.print(PasswordEncoder("123456",8));
//    }

    /**
     * 判断字符串是否为空 为空:true 不为空:false;
     *
     * @param targetvalue
     *            需要判断的值
     * @return
     */
    public static boolean isEmpty(String targetvalue) {
        if (StringUtils.isEmpty(targetvalue)) {
            return true;
        }
        return StringUtils.isEmpty(targetvalue.trim());
    }

    /**
     * 判断字符串是否不为空 不为空:true 为空:false;
     *
     * @param targetvalue
     *            需要判断的值
     * @return
     */
    public static boolean isNotEmpty(String targetvalue) {
        return !isEmpty(targetvalue);
    }

    /**
     * 去空
     *
     * @param obj
     * @return
     */
    public static String hanldNull(Object obj) {
        if (null == obj) {
            return "";
        }
        if ("null".equals(obj.toString())) {
            return "";
        }
        return obj.toString().trim();
    }
    /**
     * <p>
     * 方法描述: 处理字符串长度
     * </p>
     * @param str
     * @param length
     * @return
     * @author laihb
     */
    public static String handString(String str, int length){
        if(isEmpty(str)){
            return str;
        }
        if(str.length()>length && length >0){
            return str.substring(0, length);
        }
        return str;
    }
    /**
     * <p>
     * 方法描述：时间戳转为字符串
     * </p>
     * @param timestamp
     * @return
     * @author laihb
     */
    public static String handTimestamp(Timestamp timestamp) {
        if (null == timestamp || "null".equals(timestamp.toString())) {
            return "";
        }
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.format(timestamp);
        } catch (Exception e) {

        }
        return "";
    }
    /**
     * Long类型NULL值变0
     *
     * @return
     */
    public static Long LongNull(Long val) {
        if (null == val) {
            return 0L;
        }
        if ("null".equals(val.toString())) {
            return 0L;
        }
        return val;
    }

    /**
     *
     * <p>
     * 判断Object类型的值是否不为空(不为空返回true)
     * </p>
     * @param obj
     * @return
     */
    public static boolean objNotEmpty(Object obj){
        if (null != obj && !"".equals(hanldNull(obj))) {
            return true;
        }
        return false;
    }

    /**
     *
     * <p>
     * 判断Object类型的值是否为空(为空返回true)
     * </p>
     * @param obj
     * @return
     */
    public static boolean isObjEmpty(Object obj){
        return !objNotEmpty(obj);
    }

    /**
     *
     * <p>
     * 判断字符串数组是否为空,不为空true,为空false
     * </p>
     * @param strs
     * @return
     */
    public static boolean arrayisNotEmpty(String[] strs) {
        if (strs != null && strs.length > 0 && !strs[0].equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串数组是否包含一个元素,包含为true,不包含为false
     * @param arr 一个String数组
     * @param str 一个字符串元素
     * @return
     */
    public static boolean arrayContain(String[] arr,String str){
        if(arrayisNotEmpty(arr) && isNotEmpty(str)){
            for (String string : arr) {
                if(str.equals(string)){
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 将字符串转换成整型(为空时返回0)
     * @param value 要转换的字符串
     * @return
     * @throws NumberFormatException
     */
    public static Integer getIntValue(String value) throws RuntimeException {
        return isEmpty(value) ? 0 : Integer.valueOf(hanldNull(value));
    }

}
