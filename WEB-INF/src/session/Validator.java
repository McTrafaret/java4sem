package session;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Validator
{
	public static boolean IsValidUsername(String username)
	{
		Pattern pattern = Pattern.compile("[A-Za-z0-9_]{1,255}");
		Matcher m = pattern.matcher(username);
		return m.matches();
	}

	public static boolean IsValidName(String name)
	{
		if(name.length() > 255)
		{
			return false;
		}
		Pattern pattern = Pattern.compile("([A-Z][a-z]+)( [A-Z][a-z]+)*");
		Matcher m = pattern.matcher(name);
		return m.matches();
	}

	public static String SqlEscape(String str)
	{
		Pattern pattern = Pattern.compile("\'|\\\\");
		Matcher m = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while(m.find())
		{
			if(m.group().equals("\'"))
			{
				System.out.println("hype");
				m.appendReplacement(sb, "\\\\'");
			}
			else
			{
				m.appendReplacement(sb, "\\\\");
			}
		}
		return sb.toString();
	}
}
