package com.example.mvp.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理公共类
 */
public class StringUtils {

    public static String SubString(String sourStr, int length) {
        String reMsg = "";

        if (sourStr.length() <= length) {
            reMsg = sourStr;
        } else {
            reMsg = sourStr.substring(0, length - 1) + "...";
        }
        return reMsg;
    }

    /**
     * 判断空字符串
     * 字符串为“null”时也判断为空
     *
     * @param str
     * @return
     */
    public static boolean IsStringNull(String str) {
        if (str == null || "null".equalsIgnoreCase(str) || str.trim().length() == 0) {
            return true;
        }

        return false;
    }

    /**
     * 针对用户银行账户隐藏特定位数，以"*"代替
     *
     * @param sStr
     * @param showLastSize
     * @return
     */
    public static String HideCardNum(String sStr, int showLastSize) {
        if (sStr != null) {
            int size = sStr.length();

            if (showLastSize < size) {
                String tmpStr = sStr.substring(size - showLastSize);
                String tmpStr2 = sStr.substring(0, 4);
                StringBuffer hideSb = new StringBuffer();

                for (int i = 4; i < size - showLastSize; i++) {
                    hideSb.append('*');
                    if ((i + 1) % 4 == 0) {
                        hideSb.append(' ');
                    }
                }
                return tmpStr2 + " " + hideSb.toString() + tmpStr;
            }
        }

        return "";
    }

    /**
     * 隐蔽手机号码中间部分，以"*"代替
     *
     * @param cellPhoneStr
     * @return
     */
    public static String HideCellPhone(String cellPhoneStr) {
        if (cellPhoneStr != null && cellPhoneStr.length() == 11) {
            StringBuffer sb = new StringBuffer(cellPhoneStr);
            int hideSize = 8;

            for (int i = 3; i <= hideSize; i++) {
                sb.setCharAt(i, '*');
            }

            return sb.toString();
        }

        return "";
    }

    /**
     * 银行卡账号格式化
     *
     * @param str
     * @return
     */
    public static String bankFormat(String str) {
        if (IsStringNull(str)) {
            return "";
        }

        StringBuilder sb = new StringBuilder(str);
        int length = str.length() / 4 + str.length();

        for (int i = 0; i < length; i++) {
            if (i % 5 == 0) {
                sb.insert(i, " ");
            }
        }
        sb.deleteCharAt(0);
        return sb.toString();
    }


    /**
     * 转换字符串为浮点型，并且保留2位小数
     *
     * @param strVal
     * @return
     */
    public static float CovertStringToValue(String strVal) {
        float reVal = 0.0f;

        try {
            reVal = Float.parseFloat(strVal);
//			reVal = (float)(Math.round(reVal*100)/100);
            BigDecimal b = new BigDecimal(reVal);
            reVal = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        } catch (Exception e) {

        }

        return reVal;
    }

    /**
     * 将浮点型数据转化为保留2位小数
     *
     * @param value
     * @return
     */
    public static String FormatFloat(float value) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String reStr = decimalFormat.format(value);//format 返回的是字符串

        return reStr;
    }


    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 对字符串处理:将指定位置到指定位置的字符以星号代替
     *
     * @param content 传入的字符串
     * @param begin   开始位置
     * @param end     结束位置
     * @return
     */
    public static String getStarString(String content, int begin, int end) {

        if (begin >= content.length() || begin < 0) {
            return content;
        }
        if (end >= content.length() || end < 0) {
            return content;
        }
        if (begin >= end) {
            return content;
        }
        String starStr = "";
        for (int i = begin; i < end; i++) {
            starStr = starStr + "*";
        }
        return content.substring(0, begin) + starStr + content.substring(end, content.length());

    }

    /**
     * 对字符加星号处理：除前面几位和后面几位外，其他的字符以星号代替
     *
     * @param content  传入的字符串
     * @param frontNum 保留前面字符的位数
     * @param endNum   保留后面字符的位数
     * @return 带星号的字符串
     */

    public static String getStarString2(String content, int frontNum, int endNum) {

        if (frontNum >= content.length() || frontNum < 0) {
            return content;
        }
        if (endNum >= content.length() || endNum < 0) {
            return content;
        }
        if (frontNum + endNum >= content.length()) {
            return content;
        }
        String starStr = "";
        for (int i = 0; i < (content.length() - frontNum - endNum); i++) {
            starStr = starStr + "*";
        }
        return content.substring(0, frontNum) + starStr
                + content.substring(content.length() - endNum, content.length());

    }

    /*
     * 反格式化金额
     * */
    public static String formatMoneyData(String money) {
        if (money.indexOf(",") == -1) {
            return money.substring(1);
        } else {
            return money.substring(1, money.indexOf(",")) + money.substring(money.indexOf(",") + 1);
        }

    }

    /*
     * 格式化金额
     * */
    public static String formatMoney(String money) {
        try {
            NumberFormat defForm = NumberFormat.getInstance();
            Object o = covertStringToValue(money);
            return defForm.format((o + "").indexOf(".") == -1 ? Integer.parseInt(o + "") : Long.parseLong(o + ""));
        } catch (Exception e) {
            return money;
        }

    } /*
     * 格式化金额
     * */

    public static String formatMoney(Integer money) {
        NumberFormat defForm = NumberFormat.getInstance();
        return defForm.format((money));
    }

    public static String formatSMoney(String money) {
        try {
            NumberFormat defForm = NumberFormat.getInstance();
            Number parse = defForm.parse(money);
            return parse.toString();
        } catch (Exception e) {
            return "";
        }

    }

    /*
     * 转换字符串为浮点型，并且保留2位小数，如果小数为零，则不显示
     * @param strVal
     * @return
     */
    public static Object covertStringToValue(String strVal) {
        String reStr = strVal;

        if (strVal.contains(".")) {
            String[] sourArray = strVal.split("\\.");
            String intPar = sourArray[0];//整数部分
            String decPar = null;

            //小数部分处理
            if (sourArray.length == 2) {


                String decParTmp = sourArray[1];

                try {
                    int decParVal = Integer.parseInt(decParTmp);
                    int decParResu = removeLastZero(decParVal);
                    if (decParResu > 0) {
                        decPar = decParResu + "";

                        char[] tmpChars = decParTmp.toCharArray();
                        for (char c : tmpChars) {
                            if (c == '0') {
                                decPar = "0" + decPar;
                            } else {
                                break;
                            }
                        }
                    }
                } catch (NumberFormatException e) {

                }
            }

            if (decPar != null) {
                reStr = intPar + "." + decPar;
            } else {
                reStr = intPar;
            }
        }

        return reStr;
    }

    /**
     * 把有效数据之后的0删掉
     *
     * @param sourVal
     * @return
     */
    public static int removeLastZero(int sourVal) {
        int tmpVal = sourVal;
        if (tmpVal > 0) {
            while (tmpVal % 10 == 0) {
                tmpVal = tmpVal / 10;
            }
        }
        return tmpVal;
    }


    /**
     * 复制
     */
    public static ClipboardManager mClipboard = null;

    public static void copyString(String msg, String label, Context context) {
        // Gets a handle to the clipboard service.
        if (null == mClipboard) {
            mClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        }
        // Creates a new text clip to put on the clipboard
        ClipData clip = ClipData.newPlainText(label,
                msg);
        // Set the clipboard's primary clip.
        mClipboard.setPrimaryClip(clip);
    }

    /**
     * 判断密码强度
     *
     * @return Z = 字母 S = 数字 T = 特殊字符
     */
    public static int passwordStrong(String str) {
        if (TextUtils.equals("", str)) {
            return 0;
        }
        String regex = ".*[a-z]+.*";
        String regex2 = ".*[A-Z]+.*";
        String regex3 = ".*[0-9]+.*";
        //输入的纯数字为弱
        if (str.matches("^[0-9]{1,32}")) {
            return 1;
        }
        //输入的纯小写字母为弱
        else if (str.matches("^[a-z]{1,32}")) {
            return 1;
        }
        //输入的纯大写字母为弱
        else if (str.matches("^[A-Z]{1,32}")) {
            return 1;
        }
        //输入的纯字符为弱
        else if (str.matches("^[\"`~!@#$%^&*()_\\-+=<>?:\\\"{}|,./;'\\[\\]·~！@#￥%……&*（）——+={}|《》？：“”【】、；‘'，。、]{1,32}")) {
            return 1;
        }
        //输入的大写字母和数字，输入的字符密码为二级
        else if (str.matches("^[A-Z0-9]{1,32}")) {
            return 2;
        }
        //输入的小写字母和数字，输入的字符小于7个密码为二级
        else if (str.matches("^[a-z0-9]{1,32}")) {
            return 2;
        }
        //输入的大写字母和小写字母，输入的字符密码为二级
        else if (str.matches("^[A-Za-z]{1,32}")) {
            return 2;
        }
        //输入的大写字母和字符，输入的字符密码为二级
        else if (str.matches("^[A-Z\"`~!@#$%^&*()_\\-+=<>?:\\\"{}|,./;'\\[\\]·~！@#￥%……&*（）——+={}|《》？：“”【】、；‘'，。、]{1,32}")) {
            return 2;
        }
        //输入的小写字母和字符，输入的字符密码为二级
        else if (str.matches("^[a-z\"`~!@#$%^&*()_\\-+=<>?:\\\"{}|,./;'\\[\\]·~！@#￥%……&*（）——+={}|《》？：“”【】、；‘'，。、]{1,32}")) {
            return 2;
        }
//输入的小写字母和字符，输入的字符密码为二级
        else if (str.matches("^[0-9\"`~!@#$%^&*()_\\-+=<>?:\\\"{}|,./;'\\[\\]·~！@#￥%……&*（）——+={}|《》？：“”【】、；‘'，。、]{1,32}")) {
            return 2;
        }

        //输入的大写字母和小写字母和数字，输入的字符个密码为三级
        else if (str.matches("^[A-Za-z0-9]{1,32}")) {
            return 3;
        }
        //输入的大写字母和小写字母和字符，输入的字符个密码为三级
        else if (str.matches("^[A-Za-z\"`~!@#$%^&*()_\\-+=<>?:\\\"{}|,./;'\\[\\]·~！@#￥%……&*（）——+={}|《》？：“”【】、；‘'，。、]{1,32}")) {
            return 3;
        }
        //输入的大写字母和字符和数字，输入的字符大于8个密码为强
        else if (str.matches("^[A-Z\"`~!@#$%^&*()_\\-+=<>?:\\\"{}|,./;'\\[\\]·~！@#￥%……&*（）——+={}|《》？：“”【】、；‘'，。、0-9]{1,32}")) {
            return 3;
        }
        //输入的字符和小写字母和数字，输入的字符大于8个密码为强
        else if (str.matches("^[\"`~!@#$%^&*()_\\-+=<>?:\\\"{}|,./;'\\[\\]·~！@#￥%……&*（）——+={}|《》？：“”【】、；‘'，。、a-z0-9]{1,32}")) {
            return 3;
        }
        //输入的字符大写字母和小写字母和数字，输入的字符大于8个密码为强
        else if (str.matches("^[A-Z\"`~!@#$%^&*()_\\-+=<>?:\\\"{}|,./;'\\[\\]·~！@#￥%……&*（）——+={}|《》？：“”【】、；‘'，。、a-z0-9]{1,32}")) {
            return 4;
        } else if ((Pattern.compile(regex).matcher(str).matches() && Pattern.compile(regex2).matcher(str).matches() && Pattern.compile(regex3).matcher(str).matches())
        ) {
            return 4;
        } else if ((Pattern.compile(regex).matcher(str).matches() && Pattern.compile(regex2).matcher(str).matches())
                | (Pattern.compile(regex).matcher(str).matches() && Pattern.compile(regex3).matcher(str).matches())
                | (Pattern.compile(regex2).matcher(str).matches() && Pattern.compile(regex3).matcher(str).matches())
        ) {
            return 3;
        } else if (Pattern.compile(regex).matcher(str).matches() | Pattern.compile(regex2).matcher(str).matches() | Pattern.compile(regex3).matcher(str).matches()) {

            return 2;
        }
        return 1;
    }

    public static boolean isDoubleOrFloat(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断邮箱是否合法
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }


    public static String decimal8(String money) {
        if (IsStringNull(money)) return "";
        if (Float.parseFloat(money) > 0) {
            // NumberFormat nf = NumberFormat.getInstance();
            return new BigDecimal(money).setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
        }
        return "0";
        // int indox = decimal.indexOf(".");
        // return ((decimal.length() > (indox + 9)) ? decimal.substring(0, indox + 9) : decimal);

    }

    public static String decimal(String money, int i) {
        if (IsStringNull(money)) return "";
        if (Float.parseFloat(money) > 0) {
            // NumberFormat nf = NumberFormat.getInstance();
            return new BigDecimal(money).setScale(i, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
        }
        return "0";
        // int indox = decimal.indexOf(".");
        // return ((decimal.length() > (indox + 9)) ? decimal.substring(0, indox + 9) : decimal);

    }

    /**
     * 金钱乘法
     */
    public static String multiplyMoney(String s1, String s2, String s3) {
        if (IsStringNull(s1) | IsStringNull(s2) | IsStringNull(s3)) return "";
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        BigDecimal b3 = new BigDecimal(s3);
        return decimal(b1.multiply(b2).multiply(b3).toPlainString(), 2);

    }

    /**
     * 金钱乘法
     */
    public static String multiplyMoney(String s1, String s2, String s3,int i) {
        if (IsStringNull(s1) | IsStringNull(s2) | IsStringNull(s3)) return "";
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        BigDecimal b3 = new BigDecimal(s3);
        return decimal(b1.multiply(b2).multiply(b3).toPlainString(), i);

    }

    /**
     * 金钱加法
     */
    public static String addMoney(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).setScale(2, BigDecimal.ROUND_DOWN).toPlainString();
    }

}
