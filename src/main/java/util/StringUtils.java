package util;

public class StringUtils {
    private StringUtils() {
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String defaultStr(String str, String defaultStr) {
        return isNotEmpty(str) ? str : defaultStr;
    }
}
