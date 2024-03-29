package cn.core.framework.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
/**
 * Create on : 2016年11月3日 上午9:43:48  <br>
 * Description : 字符串工具类 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
public class StrUtils {

	private StringBuffer sb = null;
	private String sysLineSeparator = null;

	public StrUtils() {
		if (sb != null) {
			sb = null;
		} else {
			sb = new StringBuffer();
		}
		sysLineSeparator = System.getProperty("line.separator");
	}

	/**
	 * 输入指定字串返回非空字符
	 * 
	 * @param str
	 *            指定字串
	 * @return 返回类型 String 返回非空字符
	 * 
	 */
	public static String requote(String str) {
		if (str == null)
			str = "";
		if ("null".equalsIgnoreCase(str))
			str = "";
		return str;
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:44:20 <br>
	 * Description : 非空返回false <br>
	 * @param str str
	 * @return boolean
	 */
	public static boolean isBlankOrNull(String str) {
		return "".equals(requote(str)) ? true : false;
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:44:54 <br>
	 * Description : 对象为null <br>
	 * @param obj obj
	 * @return boolean
	 */
	public static boolean isNull(Object obj) {
		return null == obj ? true : false;
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:52:23 <br>
	 * Description : 非""返回TRUE <br>
	 * @param str str
	 * @return boolean
	 */
	public static boolean isBlank(String str) {
		return "".equals(str) ? true : false;
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:45:42 <br>
	 * Description : GBK编码的字符串转为ISO885_1 <br>
	 * @param str GBK编码的字符
	 * @return String 返回类型 String 转化后的ISO885_1字符串
	 * @throws Exception
	 */
	public static String GBKToISO(String str) throws Exception {
		if (str != null) {
			return new String(str.getBytes("GBK"), "ISO8859_1");
		} else {
			return "";
		}
	}

	/**
	 * ISO8859_1编码的字符串转为GBK
	 * 
	 * @param str
	 *            GBK编码的字符
	 * @throws Exception
	 * @return 返回类型 String 转化后的GBK字符串
	 * 
	 */
	public static String ISOToGBK(String str) throws Exception {
		if (str != null) {
			return new String(str.getBytes("ISO8859_1"), "GBK");
		} else {
			return "";
		}
	}

	/**
	 * GB编码的字符串转为UTF8
	 * 
	 * @param src
	 *            GB编码的字符
	 * @throws Exception
	 * @return 返回类型 String 转化后的UTF8字符串
	 * 
	 */
	public static String gbToUtf8(String src) {
		byte[] b = src.getBytes();
		char[] c = new char[b.length];
		for (int i = 0; i < b.length; i++) {
			c[i] = (char) (b[i] & 0x00FF);
		}
		return new String(c);
	}

	/**
	 * 将指定对象转化为非空String，如果为空返回“0”
	 * 
	 * @param obj
	 *            指定对象
	 * @return 返回类型 String 非空String
	 * 
	 */
	public static String null2Zero(Object obj) {

		if ((obj == null) || (obj.equals("")) || (obj.equals("null"))) {
			return "0";
		} else {
			return obj.toString().trim();
		}
	}

	/**
	 * 将指定对象转化为非空String，如果为空返回“”
	 * 
	 * @param obj
	 *            指定对象
	 * @return 返回类型 String 非空String
	 * 
	 */
	public static String null2Str(Object obj) {

		if ((obj == null) || (obj.equals("null")) || (obj.equals(""))) {
			return "";
		} else {
			return obj.toString().trim();
		}
	}

	/**
	 * 将一个字符串按给定分割字符分割成数组
	 * 
	 * @param source
	 *            字符串
	 * @param useChar
	 *            分割字符
	 * @return 返回类型 String[] 数组字符串
	 * 
	 */

	public static String[] split(String source, char useChar) {
		List<String> list = new ArrayList<String>();
		String sub;
		String[] result;

		if (source.charAt(0) == useChar)
			source = source.substring(1, source.length());
		if (source.charAt(source.length() - 1) == useChar)
			source = source.substring(0, source.length() - 1);

		int start = 0;
		int end = source.indexOf(useChar);
		while (end > 0) {
			sub = source.substring(start, end);
			list.add(sub);
			start = end + 1;
			end = source.indexOf(useChar, start);
		}

		sub = source.substring(start, source.length());
		list.add(sub);

		result = new String[list.size()];

		Iterator<String> iter = list.iterator();
		int i = 0;
		while (iter.hasNext()) {
			result[i++] = iter.next();
		}
		return result;
	}

	/**
	 * 将一个字符串按给定分割字符分割成List
	 * 
	 * @param source
	 *            字符串
	 * @param useChar
	 *            分割字符
	 * @return 返回类型 List 分割成的List
	 * 
	 */
	public static List<String> splitList(String source, char useChar) {
		List<String> list = new ArrayList<String>();
		String sub;
		if(source.equals(""))
			return list;
		if (source.charAt(0) == useChar)
			source = source.substring(1, source.length());
		if (source.charAt(source.length() - 1) == useChar)
			source = source.substring(0, source.length() - 1);

		int start = 0;
		int end = source.indexOf(useChar);
		while (end > 0) {
			sub = source.substring(start, end);
			list.add(sub);
			start = end + 1;
			end = source.indexOf(useChar, start);
		}

		sub = source.substring(start, source.length());
		list.add(sub);

		return list;
	}

	/**
	 * 判断给定子字符串<code>subStr</code>是否在大字符<code>str</code>
	 * 
	 * @param subStr
	 *            子字符串
	 * @param str
	 *            大字符串
	 * @return 返回类型 如果存在则返回true,否则返回false
	 * 
	 */
	public static boolean isIn(String subStr, String str) {
		if (subStr == null || str == null) {
			return false;
		}
		if (str.indexOf(subStr) == -1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 将数组中的每一项用“，”连接起来
	 * 
	 * @param str
	 *            数组
	 * @return 返回类型 String 字符串
	 * 
	 */
	public static String join(String[] str) {
		return join(str, ",");
	}

	/**
	 * 将数组中的每一项用指定字符串连接起来
	 * 
	 * @param str
	 *            数组
	 * @param join
	 *            指定字符串
	 * @return 返回类型 String 字符串
	 * 
	 */
	public static String join(String[] str, String join) {
		if (str == null || join == null) {
			return "";
		}
		String rtnStr = "";
		for (int i = 0; i < str.length; i++) {
			rtnStr += join + str[i];
		}
		if (rtnStr.indexOf(join) != -1) {
			rtnStr = rtnStr.substring(1);
		}
		return rtnStr;
	}

	/**
	 * 字符串替换
	 * 
	 * @param line
	 *            源字符串
	 * @param oldString
	 *            将要被替换掉的子字符串
	 * @param newString
	 *            将要用来替换旧子字符串的字符串
	 * @return 返回类型 String 返回替换后的结果字符串
	 * 
	 */
	public static final String replace(String line, String oldString,
			String newString) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * 字符串替换，忽略大小写
	 * 
	 * @param line
	 *            源字符串
	 * @param oldString
	 *            将要被替换掉的子字符串
	 * @param newString
	 *            将要用来替换旧子字符串的字符串
	 * @return 返回类型 String 返回替换后的结果字符串
	 * 
	 */
	public static final String replaceIgnoreCase(String line, String oldString,
			String newString) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * 字符串替换，并且统计替换的个数
	 * 
	 * @param line
	 *            源字符串
	 * @param oldString
	 *            将要被替换掉的子字符串
	 * @param newString
	 *            将要用来替换旧子字符串的字符串
	 * @param count
	 *            统计替换的个数
	 * @return 返回类型 String 返回替换后的结果字符串
	 * 
	 */
	public static final String replace(String line, String oldString,
			String newString, int[] count) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			int counter = 0;
			counter++;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		}
		return line;
	}

	/**
	 * 将数字型日期转换为带间隔符的字符串型
	 * 
	 * @param date
	 *            指定日期
	 * @param interval
	 *            间隔符
	 * @return 返回类型 String 带间隔符的字符串型
	 * 
	 */
	public static final String int2Date(Integer date, String interval) {
		String line = String.valueOf(date);
		if (line.length() != 8) {
			return null;
		} else {
			StringBuffer buf = new StringBuffer(10);
			buf.append(line.substring(0, 4));
			buf.append(interval);
			buf.append(line.subSequence(4, 6));
			buf.append(interval);
			buf.append(line.substring(6, 8));
			return buf.toString();
		}

	}

	/**
	 * 将数字型日期转换为带间隔符的字符串型
	 * 
	 * @param date
	 *            指定日期
	 * @param interval
	 *            间隔符
	 * @return 返回类型 String 带间隔符的字符串型
	 * 
	 */
	public static final String long2Date(Long date, String interval) {
		String line = String.valueOf(date);
		if (line.length() != 8) {
			return null;
		} else {
			StringBuffer buf = new StringBuffer(10);
			buf.append(line.substring(0, 4));
			buf.append(interval);
			buf.append(line.subSequence(4, 6));
			buf.append(interval);
			buf.append(line.substring(6, 8));
			return buf.toString();
		}

	}

	/**
	 * 查一个字符串是否全部为空
	 * 
	 * @param input
	 *            字符串
	 * @return 返回类型 boolean 如果字符串为空格则返回false,否则返回strTemp.length()!
	 * 
	 */
	public static boolean checkDataValid(String input) {
		String strTemp = new String(input);
		if (strTemp == null || strTemp.length() == 0) {
			return false;
		}
		strTemp = strTemp.trim();
		return strTemp.length() != 0;
	}
	/**
	 * 生成word得数据源
	 * 替换输入字符串中的非法字符串
	 * @param Str 是要替换的字符串
	 * @return 返回类型 String 
	 * 
	 */
	public static String escapeWord(String Str) {

		if (Str == null || Str.length() == 0) {
			return "";
		}
		Str = Str.trim();
		StringBuffer buf = new StringBuffer();
		char ch = ' ';
		for (int i = 0; i < Str.length(); i++) {
			ch = Str.charAt(i);
			if (ch == '<') {
				buf.append("&lt;");
			} else if (ch == '>') {
				buf.append("&gt;");
			} else if (ch == '&') {
				buf.append("&amp;");
			} else if (ch == '"') {
				buf.append("&quot;");
			} else if (ch == '\'') {
				buf.append("&apos;");
			}else {
				buf.append(ch);
			}
		} // end for loop
		return buf.toString();
	}
	/**
	 * 
	 * 替换输入字符串中的非法字符串
	 * 
	 * @param input
	 *            是要替换的字符串
	 * @return 返回类型 String 如果字符串为空则返回input,否则返回buf.toString()替换后的字符串
	 * 
	 */
	public static String escapeHTML(String input) {

		if (input == null || input.length() == 0) {
			return input;
		}
		input = input.trim();
		StringBuffer buf = new StringBuffer();
		char ch = ' ';
		for (int i = 0; i < input.length(); i++) {
			ch = input.charAt(i);
			if (ch == '<') {
				buf.append("&lt;");
			} else if (ch == '>') {
				buf.append("&gt;");
			} else if (ch == '\n') {
				buf.append("<br/>");
			} else if (ch == ' ') {
				buf.append("&nbsp;");
			} else if (ch == '\'') {
				buf.append("''");
			} else {
				buf.append(ch);
			}
		} // end for loop
		return buf.toString();
	}

/**
	 * 转换个包含有HTML标志(ie, &lt;b&gt;,&lt;table&gt;, etc)的字符串，将里面�?'><'转换�?'&lt''
	 * and '&gt;'
	 * 
	 * @param input
	 *            篇用于转换的text
	 * @return 返回类型 String 转换后的字符串
	 * 
	 */
	public static final String escapeHTMLTags(String input) {
		if (input == null || input.length() == 0) {
			return input;
		}
		StringBuffer buf = new StringBuffer(input.length());
		char ch = ' ';
		for (int i = 0; i < input.length(); i++) {
			ch = input.charAt(i);
			if (ch == '<') {
				buf.append("&lt;");
			} else if (ch == '>') {
				buf.append("&gt;");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	/**
	 * 追加字符串到字符串缓冲器
	 * 
	 * @param str
	 *            要追加的字符串
	 * 
	 */
	public void append(String str) {
		sb.append(str);
	}

	/**
	 * 追加字符串到字符串缓冲器 并追加一个缺省的换行符\n
	 * 
	 * @param str
	 *            要追加的字符串
	 * 
	 */
	public void appendln(String str) {
		appendln(str, false);
	}

	/**
	 * 追加字符串到字符串缓冲器 并根据<code>isSysLineSeparator</code>不同追加缺省的换行符\n或系统换行符
	 * 
	 * @param str
	 *            要追加的字符串
	 * @param useSysLineSeparator
	 *            是否追加系统换行符表
	 * 
	 */
	public void appendln(String str, boolean useSysLineSeparator) {
		if (useSysLineSeparator) {
			sb.append(str);
			sb.append(this.sysLineSeparator);
		} else {
			sb.append(str);
			sb.append("\n");
		}
	}

	/**
	 * 转换字符串缓冲器中的数据为字符串表示
	 * 
	 * @return 返回类型 String 表示字符串缓冲器中的数据的字符串
	 * 
	 */
	public String toStr() {
		return sb.toString();
	}

	/**
	 * 
	 * 把一篇文档分割成小写的，以 , .\r\n:/\+为分割符的数组
	 * 
	 * @param text
	 *            要被分割成数组的文档
	 * @return 返回类型 String[] 返回分割成的数组
	 * 
	 */
	public static final String[] toLowerCaseWordArray(String text) {
		if (text == null || text.length() == 0) {
			return new String[0];
		}
		StringTokenizer tokens = new StringTokenizer(text, " ,\r\n.:/\\+");
		String[] words = new String[tokens.countTokens()];
		for (int i = 0; i < words.length; i++) {
			words[i] = tokens.nextToken().toLowerCase();
		}
		return words;
	}

	/**
	 * 将对象数组转为字符串数组
	 * 
	 * @param objs
	 *            对象数组
	 * @return 返回类型 String[] 字符串数组
	 * 
	 */
	public static String[] objectArrayToStringArray(Object[] objs) {
		if (objs == null)
			return null;
		String[] s = new String[objs.length];
		System.arraycopy(objs, 0, s, 0, s.length);
		return s;
	}

	/**
	 * 字符串加密
	 * 
	 * @param str
	 *            字符串
	 * @return 返回类型 String 加密后字符串
	 * 
	 */
	public static final String encrypt(String str) {

		String string_in =  "-YN8K1JOZVURB3MDETS5GPL27AXWIHQ94C6F0";
		String string_out = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-";
		String outpass = "";
		try {
			if (str != null) {
				int offset = 0;
				Calendar calendar = Calendar.getInstance();
				int ss = calendar.get(Calendar.SECOND);
				offset = ss % 38;
				/*
				 * ResultSetrs=dataconn.executeQuery( "select
				 * MOD(to_number(to_char(sysdate,'ss')),70) from dual");
				 * if(rs.next()) offset=rs.getInt(1); rs.close();
				 * dataconn.close();
				 */
				if (offset > 0)
					offset = offset - 1;
				outpass = string_in.substring(offset, offset + 1);
				string_in = string_in + string_in;
				string_in = string_in.substring(offset, offset + 39);
				outpass = outpass + translate(str, string_in, string_out);
				return outpass;
			} else {
				return "";
			}
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 字符串解密
	 * 
	 * @param str
	 *            字符串
	 * @return 返回类型 String 解密后字符串
	 * 
	 */
	public static final String disencrypt(String str) {

		String string_in = "-YN8K1JOZVURB3MDETS5GPL27AXWIHQ94C6F0";
		String string_out = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-";
		try {
			int offset = 0;
			char c = str.charAt(0);
			offset = string_in.indexOf(c);
			string_in = string_in + string_in;
			string_in = string_in.substring(offset, offset + 38);
			String s = str.substring(1);
			s = s.toUpperCase();
			String inpass = translate(s, string_out, string_in);
			return inpass;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 
	 * 取得str字符串转换为大写字母再取出里面的每一个字符，看这个字符在string_in中出现的位置，
	 * 再取出string_out字符串在这个位置上的每个字符。
	 * 
	 * @param str
	 * @param string_in
	 * @param string_out
	 * @return 返回类型 String 返回转换后的字符串
	 */
	private static final String translate(String str, String string_in,
			String string_out) {

		String s = str.toUpperCase();
		char[] outc = new char[s.length()];
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int j = string_in.indexOf(c);
			outc[i] = string_out.charAt(j);

		}
		String outs = new String(outc);

		return outs;
	}

	/**
	 * 对字符串进行编码,目的是解决数据库中文问题
	 * 
	 * @param inputStr
	 *            指定字符串
	 * @return 返回类型 String 返回编码后的字符串
	 * 
	 */
	public static String encode(String inputStr) {

		String tempStr = "";
		try {
			tempStr = new String(inputStr.getBytes("ISO-8859-1"));
		} catch (Exception ex) {
			System.out.print("encode() error: " + ex.toString());
		}
		return tempStr;
	}

	/**
	 * 将指定url用GBK编码
	 * 
	 * @param url
	 *            指定url
	 * @return 返回类型 String 编码后url
	 * 
	 */
	public static String urlEncode(String url) {
		if (url == null || "".equals(url))
			return "";
		try {
			return java.net.URLEncoder.encode(url, "GBK");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * 将指定url用GBK解码
	 * 
	 * @param encodedUrl
	 *            指定url
	 * @return 返回类型 String 解码后url
	 * 
	 */
	public static String urlDecode(String encodedUrl) {
		if (encodedUrl == null || "".equals(encodedUrl))
			return "";
		try {
			return java.net.URLDecoder.decode(encodedUrl, "GBK");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * 字符串是否为空
	 * 
	 * @param str
	 *            字符串
	 * @return 返回类型 boolean 为空则为true，否则为false
	 * 
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str) || "null".equalsIgnoreCase(str))
			return true;
		return false;
	}

	/**
	 * 将浮点型小数转换为百分数，小数点后第三位四舍五入
	 * 
	 * @param f
	 *            浮点型小数
	 * @return 返回类型 String 浮点型小数转换为百分数，小数点后第三位四舍五入后的字符串
	 * 
	 */
	public static String getRoundPercent(double f) {
		DecimalFormat df = new DecimalFormat("#####.00");

		return df.format(f);
	}

	/**
	 * 将浮点型小数转换为百分数，小数点后第三位四舍五入
	 * 
	 * @param f
	 *            浮点型小数
	 * @return 返回类型 double 浮点型小数转换为百分数，小数点后第三位四舍五入后的浮点型小数
	 * 
	 */
	public static double getDoubledigit(double f) {
		DecimalFormat df = new DecimalFormat("#####.00");
		return Double.parseDouble(df.format(f));
	}

	/**
	 * 将浮点型小数转换为整数
	 * 
	 * @param f
	 *            浮点型小数
	 * @return 返回类型 int 转换后的整数
	 * 
	 */

	public static int getIntRoundPercent(double f) {

		DecimalFormat df = new DecimalFormat("#####");
		Double.parseDouble(df.format(f));

		return Integer.parseInt(df.format(f));
	}

	/**
	 * 将浮点型小数转换为百分数
	 * 
	 * @param f
	 *            浮点型小数
	 * @return 返回类型 int 转换后的点型小数
	 * 
	 */
	public static int getRoundPercent(float f) {
		double r = f * 100;
		String round = String.valueOf(r);
		if (round.indexOf(".") > 0) {
			round = round.substring(0, round.indexOf("."));
			int intValue = Integer.parseInt(round);
			if (r - intValue >= 0.5)
				intValue += 1;
			round = String.valueOf(intValue);
		}
		return Integer.parseInt(round);
	}

	/**
	 * 得到YYYY-12200203型字串 YYYY：2009
	 * 
	 * @return 返回类型 String YYYY-12200203型字串
	 * 
	 */
	public static String getYYYYString() {
		Calendar now = Calendar.getInstance();
		String number = "" + now.get(Calendar.YEAR) + "-"
				+ now.getTimeInMillis();
		return number;
	}

	/**
	 * 将指定日期去掉“_”
	 * 
	 * @param data
	 *            指定日期
	 * @return 返回类型 String 去掉“_”后的日期
	 * 
	 */
	public static String removeUnderScores(String data) {
		String temp = null;
		StringBuffer out = new StringBuffer();
		temp = data;

		StringTokenizer st = new StringTokenizer(temp, "_");
		while (st.hasMoreTokens()) {
			String element = (String) st.nextElement();
			out.append(capitalize(element));
		}
		return out.toString();
	}

	/**
	 * 将指定字符串大写
	 * 
	 * @param data
	 *            指定字符串
	 * @return 返回类型 String 返回新字串
	 * 
	 */
	public static String capitalize(String data) {
		StringBuffer sbuf = new StringBuffer(data.length());
		sbuf.append(data.substring(0, 1).toUpperCase()).append(
				data.substring(1));
		return sbuf.toString();
	}

	/**
	 * 将指定字符串的第pos个字符大写
	 * 
	 * @param data
	 *            指定字符串
	 * @param pos
	 *            第pos个字符
	 * @return 返回类型 String 返回改变后的字符串
	 * 
	 */
	public static String capitalize(String data, int pos) {
		StringBuffer buf = new StringBuffer(data.length());
		buf.append(data.substring(0, pos - 1));
		buf.append(data.substring(pos - 1, pos).toUpperCase());
		buf.append(data.substring(pos, data.length()));
		return buf.toString();
	}

	/**
	 * 将指定字符串的第pos个字符小写
	 * 
	 * @param data
	 *            指定字符串
	 * @param pos
	 *            第pos个字符
	 * @return 返回类型 String 返回改变后的字符窜
	 * 
	 */
	public static String unCapitalize(String data, int pos) {
		StringBuffer buf = new StringBuffer(data.length());
		buf.append(data.substring(0, pos - 1));
		buf.append(data.substring(pos - 1, pos).toLowerCase());
		buf.append(data.substring(pos, data.length()));
		return buf.toString();
	}

	/**
	 * 将一个字符串按给定分割字符串分割成数组
	 * 
	 * @param text
	 *            字符串
	 * @param separator
	 *            分割字符串
	 * @return 返回类型 String[] 数组字符串
	 * 
	 */
	public static String[] split(String text, String separator) {
		StringTokenizer st = new StringTokenizer(text, separator);
		String[] values = new String[st.countTokens()];
		int pos = 0;
		while (st.hasMoreTokens()) {
			values[pos++] = st.nextToken();
		}
		return values;
	}

	/**
	 * 将字符串中所有的tag全部替换成为指定的info
	 * 
	 * @param source
	 *            原来的字符串
	 * @param info
	 *            替换tag的字符串
	 * @param tag
	 *            要被替换掉的tag
	 * @return 返回类型 String 替换后的内容
	 * 
	 */
	public static String replaceAll(String source, String info, String tag) {
		if ((source == null) || (source.length() == 0))
			return "";

		if ((info == null) || (tag == null) || (tag.length() == 0))
			return source;

		int index = source.indexOf(tag);
		boolean valid = (index >= 0);
		if (!valid)
			return source;

		StringBuffer ret = new StringBuffer();
		int start = 0;
		int length = tag.length();
		while (valid) {
			ret.append(source.substring(start, index)).append(info);
			start = index + length;
			index = source.indexOf(tag, start);
			valid = (index >= 0);
		}
		ret.append(source.substring(start));
		return ret.toString();
	}

	/**
	 * 将字符串中所有的tag全部替换成为指定的info
	 * 
	 * @param source
	 *            原来的字符串
	 * @param info
	 *            替换tag的字符串
	 * @param startTag
	 *            被替换字符串起始点
	 * @param endTag
	 *            被替换字符串结束点
	 * @return 返回类型 String 替换后的字符串
	 * 
	 */
	public static String replaceAll(String source, String info,
			String startTag, String endTag) {
		if ((source == null) || (source.length() == 0)) {
			return "";
		}
		if ((info == null) || (startTag == null) || (startTag.length() == 0)
				|| (endTag == null) || (endTag.length() == 0)) {
			return source;
		}
		int sIndex = source.indexOf(startTag);
		int eIndex = source.indexOf(endTag);
		boolean valid = (sIndex >= 0 && eIndex >= 0);
		if (!valid) {
			return source;
		} else {
			if (sIndex > eIndex) {
				eIndex = source.indexOf(endTag, sIndex);
			}
		}
		// int sLength = startTag.length();
		// int eLength = endTag.length();
		StringBuffer ret = new StringBuffer();
		int start = 0;
		while (valid) {
			info = source.substring(sIndex + 1, eIndex).trim();
			ret.append(source.substring(start, sIndex + 1)).append(info)
					.append(endTag);
			start = eIndex + 1;
			sIndex = source.indexOf(startTag, start);
			eIndex = source.indexOf(endTag, start);
			valid = (sIndex >= 0 && eIndex >= 0 && eIndex > sIndex);
		}
		ret.append(source.substring(start));
		return ret.toString();
	}

	/**
	 * 将输入字符中的SQL保留字进行替换，目前只替换英文半角的单引号'
	 * 单引号替换方法：一个单引号换成连续的两个单引号，例如'ABC'D替换成''ABC''D
	 * 
	 * @param s
	 *            源字符串
	 * @return 返回类型 String 替换后字符串
	 * 
	 */
	public static String getSQLencode(String s) {
		if ((s == null) || (s.length() == 0))
			return "";
		StringBuffer sb = new StringBuffer();
		char c;
		for (int i = 0; i < s.length(); i++) {
			c = s.charAt(i);
			switch (c) {
			case '\'':
				sb.append("''");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 将输入字符中的格式化成precision指定的程度,截掉的部分用“..”补齐
	 * 
	 * @param s
	 *            被格式化字符串
	 * @param precision
	 *            格式化长度
	 * @return 返回类型 String 格式化后字符串
	 * 
	 */
	public static String getFormatString(String s, int precision) {
		String retValue = "";
		if ((s == null) || (s.length() == 0))
			retValue = "";
		if (s.length() <= precision)
			retValue = s;
		if (s.length() == precision + 1)
			retValue = s;
		if (s.length() > precision + 1)
			retValue = s.substring(0, precision - 1) + "..";
		return retValue;
	}

	/**
	 * 使用MD5算法进行加密
	 * 
	 * @param sourcePassword
	 *            待加密的明文密码
	 * @return 返回类型 String 返回加密后的32位字符串
	 * 
	 * 
	 *         public static String getEncodedPassword(String sourcePassword) {
	 *         String unionPassword = ""; if (sourcePassword != null)
	 *         unionPassword = new String(sourcePassword); MD5 md = new MD5();
	 *         md.Update(unionPassword); return md.asHex(); }
	 */

	/**
	 * 生成树形CODE码
	 * 
	 * @param title
	 *            头
	 * @param tail
	 *            尾
	 * @param tailLength
	 *            长度
	 * @return 返回类型 String 返回生成的code码
	 * 
	 */
	public static String ensureLengthWith0(String title, String tail,
			int tailLength) {
		int len = tail.length();

		if (len == tailLength)
			return title.concat(tail);
		if (len > tailLength)
			return title.concat(tail.substring(0, tailLength));

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < tailLength - len; i++)
			sb.append('0');

		return title.concat(sb.toString().concat(tail));
	}

	public static final String filterHalfWord(String sourceStr)
			throws UnsupportedEncodingException {
		String result = "";
		char[] cc = sourceStr.toCharArray();
		for (int i = 0; i < cc.length; i++) {
			byte[] c = String.valueOf(cc[i]).getBytes("UTF-8");
			String hex = encodeHex(c);

			if (!"0".equals(hex.trim())) {
				result += String.valueOf(cc[i]);
			}
		}
		return result;
	}

	public static final String encodeHex(byte[] bytes) {
		StringBuffer buff = new StringBuffer(bytes.length * 2);
		String b;
		for (int i = 0; i < bytes.length; i++) {
			b = Integer.toHexString(bytes[i]);
			buff.append(b.length() > 2 ? b.substring(6, 8) : b);
			buff.append(" ");
		}
		return buff.toString();
	}

	public static <T extends Object> List<T> getRandomTFromList(List<T> tArray,
			int count) {
		List<T> tList = new ArrayList<T>();
		int[] randomNums = randNum(count, 0, tArray.size());
		if (null != randomNums && 0 < randomNums.length) {
			for (int i = 0; i < randomNums.length; i++) {
				tList.add(tArray.get(randomNums[i]));
			}
		}
		return tList;
	}

	public static List<String> getRandomStringUUID(String[] uuids, int count) {
		List<String> retUUIDs = new ArrayList<String>();
		int[] randomNums = randNum(count, 0, uuids.length);
		if (null != randomNums && 0 < randomNums.length) {
			for (int i = 0; i < randomNums.length; i++) {
				retUUIDs.add(uuids[randomNums[i]]);
			}
		}
		return retUUIDs;
	}

	public static int[] randNum(int num, int min, int max) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Random rand = new Random();
		while (true) {
			int rm = (rand.nextInt(max - min) + min);
			if (!list.contains(rm)) {
				list.add(rm);
				if (list.size() >= num)
					break;
			}
		}
		int result[] = new int[num];
		for (int i = 0; i < list.size(); i++) {
			result[i] = list.get(i);
		}
		return result;
	}

	public static String changeCamelToCol(String camelStr) {
		String colStr = "";
		List<Integer> acronymPos = isAcronymPos(camelStr);
		if (null != acronymPos && 0 < acronymPos.size()) {
			if (0 != acronymPos.get(0)) {
				colStr += camelStr.substring(0, acronymPos.get(0));
			} else if (1 == acronymPos.size()) {
				return camelStr;
			}
			for (int i = 0; i < acronymPos.size(); i++) {
				if (acronymPos.get(i) != 0 && i != acronymPos.size() - 1) {
					colStr += "_"
							+ camelStr.substring(acronymPos.get(i),
									acronymPos.get(i + 1));
				} else if (i == acronymPos.size() - 1) {
					colStr += "_"
							+ camelStr.substring(acronymPos.get(i),
									camelStr.length());
				} else {
					colStr += camelStr.substring(0, acronymPos.get(i + 1));
				}
			}
		} else {
			colStr = camelStr;
		}
		return colStr;
	}

	public static List<String> delSameElement(List<String> elementList) {
		Set<String> strSet = new LinkedHashSet<String>(elementList);
		List<String> returnElementList = new ArrayList<String>(strSet);
		return returnElementList;
	}

	public static String[] delSameElement(String[] strArray) {
		Set<String> strSet = new LinkedHashSet<String>(Arrays.asList(strArray));
		List<String> returnElementList = new ArrayList<String>(strSet);
		return returnElementList.toArray(new String[0]);
	}

	private static List<Integer> isAcronymPos(String word) {
		List<Integer> returnAconym = new ArrayList<Integer>();
		if (null != word && 0 < word.length()) {
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				if (!Character.isLowerCase(c)) {
					returnAconym.add(i);
				}
			}
		}
		return returnAconym;
	}

	public static byte[] objectToByteArray(Object obj) {
		byte[] bytes = new byte[1024];
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);
			bytes = bo.toByteArray();
			bo.close();
			oo.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes.clone();
	}

	public static Object byteArrayToObject(byte[] bytes) {
		java.lang.Object obj = new java.lang.Object();
		try {
			ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
			ObjectInputStream oi = new ObjectInputStream(bi);
			obj = oi.readObject();
			bi.close();
			oi.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:47:25 <br>
	 * Description : 分割字符串中带有.的第0位 <br>
	 * @param str str
	 * @return String
	 */
	public static String split0(String str) {
		if (null == str || str.trim().length() == 0) {
			return "";
		}
		if (str.indexOf(".") > -1) {
			return split(str, ".")[0];
		}
		return str;
	}

	/**
	 * 判断字符串非空并且非null
	 * 
	 * @param str
	 * @return 返回判断结果
	 */
	public static Boolean isNotBlankOrNull(String str) {
		return !(isBlankOrNull(str));
	}

	/**
	 * 判断字符串数组中是否包含任意一个空字符串
	 * 
	 * @param strs
	 * @return 返回判断结果
	 */
	public static Boolean isNotAnyNullAndBlank(String[] strs) {
		Boolean isOk = Boolean.TRUE;
		for (String str : strs) {
			if (isBlankOrNull(str)) {
				isOk = Boolean.FALSE;
			}
		}
		return isOk;
	}

	/**
	 * 产出字符串中的最后一个字符
	 * 
	 * @param cs
	 * @return 返回判断结果
	 */
	public static String deleteLastChar(CharSequence cs) {
		StringBuffer sb = new StringBuffer(cs);
		deleteLastChar(sb);
		return sb.toString();
	}

	/**
	 * 删除StringBuffer中的最后一个字符
	 * 
	 * @param sb
	 */
	public static void deleteLastChar(StringBuffer sb) {
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
	}

	/**
	 * 转换字符串首字母为小写
	 * 
	 * @param str str
	 * @return String
	 */
	public static String toLowerCaseOfFirst(String str) {
		str = str.substring(0, 1).toLowerCase() + str.substring(1);
		return str;
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:46:49 <br>
	 * Description : 除StringBuilder中的最后一个字符 <br>
	 * @param sb sb
	 */
	public static void deleteLastChar(StringBuilder sb) {
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
	}

	/**
	 * MD5加密
	 * 
	 * @param str
	 * @return 返回加密结果
	 */
	public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			return str;
		}
	}
	/**
	 * 标准下 字符串格式化
	 * 主要又空格，中文字符转英文等
	 * @param s
	 * @param precision
	 * @return
	 */
	public static String getFmt4Stand(String s) {
		if ((s == null) || (s.length() == 0)) {
			s = "";
		}else {
			s=s.replaceAll("　", " ").replaceAll("_", "-").replaceAll("（", "(").replaceAll("）", ")").replaceAll("－", "-").trim();
			s=s.replaceAll(",", "，");
		}
		return s;
	}
	/**
	 * 标准下 字符串格式化
	 * 英文转中文
	 * @param s
	 * @param precision
	 * @return
	 */
	public static String getFmt4Cn(String s) {
		if ((s == null) || (s.length() == 0)) {
			s = "";
		}else {
			s=s.replaceAll(",", "，");
			s=s.replaceAll(";", "；");
		}
		s=s.trim();
		return s;
	}
	/**
	 * 标准下 字符串格式化
	 * 中文英文转英文
	 * 标点符合
	 * @param s
	 * @param precision
	 * @return
	 */
	public static String getFmt4En(String s) {
		if ((s == null) || (s.length() == 0)) {
			s = "";
		}else {
			s=s.replaceAll("，", ",");
			s=s.replaceAll("；", ";");
		}
		return s;
	}
	/**
	 * 将数字转换为汉字
	 * @param n
	 * @return
	 */
	public static String getFmt2Cn(int n) {
		String v="";
		if(n==0) {
			v="零";
		}else if(n==1) {
			v="壹";
		}else if(n==2) {
			v="贰";
		}else if(n==3) {
			v="叁";
		}else if(n==4) {
			v="肆";
		}else if(n==5) {
			v="伍";
		}else if(n==6) {
			v="陆";
		}else if(n==7) {
			v="柒";
		}else if(n==8) {
			v="捌";
		}else if(n==9) {
			v="玖";
		}else if(n==10) {
			v="拾";
		}else if(n>10&&n<100) {
			if(n%10==0) {
				v=getFmt2Cn(n/10)+"拾";
			}else {
				v=getFmt2Cn(n/10)+"拾"+getFmt2Cn(n%10);
			}
		}else if(n>=100&&n<1000) {
			if(n%100==0) {
				v=getFmt2Cn(n/100)+"佰";
			}else {
				int b=n/100;
				if(b%10==0) {
					v=getFmt2Cn(n/100)+"佰"+getFmt2Cn(b/10)+"拾";
				}else {
					v=getFmt2Cn(n/100)+"佰"+getFmt2Cn(b/10)+"拾"+getFmt2Cn(b%10);
				}
			}
		}
		return v;
	}
	/**
	 * 将数字转换为中文
	 * @param n
	 * @return
	 */
	public static String getFmt4Cn(int n) {
		String v="";
		if(n==0) {
			v="零";
		}else if(n==1) {
			v="一";
		}else if(n==2) {
			v="二";
		}else if(n==3) {
			v="三";
		}else if(n==4) {
			v="四";
		}else if(n==5) {
			v="五";
		}else if(n==6) {
			v="六";
		}else if(n==7) {
			v="七";
		}else if(n==8) {
			v="八";
		}else if(n==9) {
			v="九";
		}else if(n==10) {
			v="十";
		}else if(n>10&&n<100) {
			if(n%10==0) {
				v=getFmt2Cn(n/10)+"十";
			}else {
				v=getFmt2Cn(n/10)+"十"+getFmt2Cn(n%10);
			}
		}else if(n>=100&&n<1000) {
			if(n%100==0) {
				v=getFmt2Cn(n/100)+"百";
			}else {
				int b=n/100;
				if(b%10==0) {
					v=getFmt2Cn(n/100)+"百"+getFmt2Cn(b/10)+"十";
				}else {
					v=getFmt2Cn(n/100)+"百"+getFmt2Cn(b/10)+"十"+getFmt2Cn(b%10);
				}
			}
		}
		return v;
	}
	public static Double findNum4Str(String str) {
		String ressult = "";
		int n=0;
        char [] arr = str.toCharArray();
        for (int i= 0 ;i<arr.length;i++){
            if('0'<=arr[i] && '9'>= arr[i]||arr[i]=='.'){//当前的是数字
            	ressult+=arr[i];
            	n++;
            }else if(n>0) {
            	break;
            }
        }
        Double d=0.0;
        try {
			d=Double.valueOf(ressult);
		} catch (NumberFormatException e) {
			d=0.0;
		}
        return d;
	}
	public static void main(String[] args) {
		// byte[] bytes = new byte[1024];
		// List<BarCodeContent> sL = new ArrayList<BarCodeContent>();
		// BarCodeContent b = new BarCodeContent();
		// b.setKey("key1111");
		// sL.add(b);
		// bytes = objectToByteArray(sL);
		//
		// List<BarCodeContent> xxx =
		// (List<BarCodeContent>)byteArrayToObject(bytes);
		//
		// System.out.println(xxx.get(0).getKey());
	}
}