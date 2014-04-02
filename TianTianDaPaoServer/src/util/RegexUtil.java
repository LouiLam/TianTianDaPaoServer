package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
//	最短4位，最长16位
//	只能由小写英文字母、数字组合
//	不能使用任何全角字符
//	首字符必须为英文小写字母，不得为数字开头
	 private static final String ID_PATTERN = "^[a-z][a-z0-9]{3,16}$";
//	 最短4位，最长10位
//	 由英文字母、数字、符号构成
	 private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]{3,10}$";
	private static Pattern patternID,patternPassword;
static{
	patternID=Pattern.compile(ID_PATTERN);
	patternPassword=Pattern.compile(PASSWORD_PATTERN);
}
public static  boolean  validateID(final String id){
	Matcher   matcher = patternID.matcher(id);
    return matcher.matches();
}
public static  boolean  validatePassword(final String password){
	Matcher   matcher = patternPassword.matcher(password);
    return matcher.matches();
}
public static void main(String[] args) {
	System.out.println(RegexUtil.validateID("ssss"));
}
}
