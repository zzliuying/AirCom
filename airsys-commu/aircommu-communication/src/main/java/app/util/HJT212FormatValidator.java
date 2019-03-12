package app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 匹配212协议数据包的正则表达式
 */
public class HJT212FormatValidator {
	public static boolean validate(String str)
	{
		//Pattern p = Pattern.compile("##.+");
		//Matcher m = p.matcher(str);
		//return m.matches();
		String regex="(\\w+=\\d+;)+CP=&&\\w+=\\d+(;\\w+=\\d+)*(;\\w+-\\w+=\\d+(\\.\\d+)?,\\w+-\\w+=\\w+)*&&";
		return str.matches(regex);//only matches segment part 
	
	}
}
