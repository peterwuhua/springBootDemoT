package cn.core.framework.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.core.framework.exception.GlobalException;
/**
 * Create on : 2016年11月3日 上午8:49:04  <br>
 * Description :  DateUtils <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
public class DateUtils {
	public final static String YYYY = "yyyy";
	public final static String MM = "MM";
	public final static String DD = "dd";
	public final static String YYYY_MM_DD = "yyyy-MM-dd";
	public final static String YYYY_MM = "yyyy-MM";
	public final static String HH_MM_SS = "HH:mm:ss";
	public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static final String formatStr_yyyyMMddHHmmssS = "yyyy-MM-dd HH:mm:ss.S";
	public static final String formatStr_yyyyMMddHHmmssS1 = "yyyyMMddHHmmssS";
	public static final String formatStr_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
	public static final String formatStr_yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
	public static final String formatStr_yyyyMMddHH = "yyyy-MM-dd HH";
	public static final String formatStr_yyyyMMdd = "yyyy-MM-dd";
	public static final String[] formatStr = { formatStr_yyyyMMddHHmmss,
			formatStr_yyyyMMddHHmm, formatStr_yyyyMMddHH, formatStr_yyyyMMdd };

	public static String begin = "";
	public static String end = "";
	public static String now = new java.sql.Date(new Date().getTime())
			.toString();

	private static final int DAY_MILLISECOND = 86400000;

	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:49:21 <br>
	 * Description :  日期格式化－将<code>Date</code>类型的日期格式化为<code>String</code>型 <br>
	 * @param date 日期
	 * @param pattern 格式化字串
	 * @return String 返回类型 String 日期字符串
	 */
	public static String format(Date date, String pattern) {
		if (date == null)
			return "";
		else
			return getFormatter(pattern).format(date);
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:50:18 <br>
	 * Description : string2Long <br>
	 * @param sourceTime sourceTime
	 * @param dataFormat dataFormat
	 * @return long
	 * @throws GlobalException
	 */
	public static long string2Long(String sourceTime, String dataFormat)
			throws GlobalException {
		long longTime = 0L;
		DateFormat f = new SimpleDateFormat(dataFormat);
		Date d = null;
		try {
			d = f.parse(sourceTime);
		} catch (ParseException e) {
			// e.printStackTrace();
			throw new GlobalException("" + e.getMessage());
		}
		longTime = d.getTime();
		return longTime;

	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:50:48 <br>
	 * Description :  long2String <br>
	 * @param longTime longTime
	 * @param dataFormat dataFormat
	 * @return String
	 */
	public static String long2String(long longTime, String dataFormat) {
		String str = "";
		try {
			Date d = new Date(longTime);
			SimpleDateFormat s = new SimpleDateFormat(dataFormat);
			str = s.format(d);
		} catch (Exception e) {
			str = "";
		}
		return str;

	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:51:09 <br>
	 * Description : 默认把日期格式化成yyyy-MM-dd格式 <br>
	 * @param date 被格式化的时间
	 * @return String 返回类型 String 日期字符串
	 */
	public static String format(Date date) {
		if (date == null)
			return "";
		else
			return getFormatter(YYYY_MM_DD).format(date);
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:51:43 <br>
	 * Description : 日期解析－将<code>String<	类型的日期解析为<code>Date</code>型 <br>
	 * @param strDate 日期字串
	 * @param pattern 格式化字串
	 * @return Date 返回类型 Date 一个被格式化了的<code>Date</code>日期
	 * @throws ParseException
	 */
	public static Date parse(String strDate, String pattern)
			throws ParseException {
		try {
			return getFormatter(pattern).parse(strDate);
		} catch (ParseException pe) {
			throw new ParseException(
					"Method parse in Class DateUtils  err: parse strDate fail.",
					pe.getErrorOffset());
		}
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:52:26 <br>
	 * Description : 获取当前日期 <br>
	 * @return Date 返回类型 Date 返回类型 一个包含年月日的<code>Date</code>型日期
	 */
	public static synchronized Date getCurrDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}
	 /**
     * 函数功能描述:UTC时间转本地时间格式
     * @param utcTime UTC时间
     * @param utcTimePatten UTC时间格式
     * @param localTimePatten   本地时间格式
     * @return 本地时间格式的时间
     * eg:utc2Local("2017-06-14 09:37:50.788+08:00", "yyyy-MM-dd HH:mm:ss.SSSXXX", "yyyy-MM-dd HH:mm:ss.SSS")
     */
    public static String utc2Local(String utcTime, String utcTimePatten, String localTimePatten) {
        SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten);
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));//时区定义并进行时间获取
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return utcTime;
        }
        SimpleDateFormat localFormater = new SimpleDateFormat(localTimePatten);
        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(gpsUTCDate.getTime());
        return localTime;
    }

    /**
     * 函数功能描述:UTC时间转本地时间格式
     * @param utcTime UTC时间"yyyy-MM-dd hh:mm:ss"
     * @param localTimePattern 本地时间格式(要转换的本地时间格式)
     * @return 本地时间格式的时间
     */
    public static String utc2Local(String utcTime, String localTimePattern){
        String utcTimePattern = "yyyy-MM-dd";
        String subTime = utcTime.substring(10);//UTC时间格式以 yyyy-MM-dd 开头,将utc时间的前10位截取掉,之后是含有多时区时间格式信息的数据

        //处理当后缀为:+8:00时,转换为:+08:00 或 -8:00转换为-08:00
        if(subTime.indexOf("+") != -1){
            subTime = changeUtcSuffix(subTime, "+");
        }
        if(subTime.indexOf("-") != -1){
            subTime = changeUtcSuffix(subTime, "-");
        }
        utcTime = utcTime.substring(0, 10) + subTime;

        //依据传入函数的utc时间,得到对应的utc时间格式
        //步骤一:处理 T
        if(utcTime.indexOf("T") != -1){
            utcTimePattern = utcTimePattern + "'T'";
        }

        //步骤二:处理毫秒SSS
        if(utcTime.indexOf(".") != -1){
            utcTimePattern = utcTimePattern + " HH:mm:ss.SSS";
        }else{
            utcTimePattern = utcTimePattern + " HH:mm:ss";
        }

        //步骤三:处理时区问题
        if(subTime.indexOf("+") != -1 || subTime.indexOf("-") != -1){
            utcTimePattern = utcTimePattern + "XXX";
        }
        else if(subTime.indexOf("Z") != -1){
            utcTimePattern = utcTimePattern + "'Z'";
        }

        if("yyyy-MM-dd HH:mm:ss".equals(utcTimePattern) || "yyyy-MM-dd HH:mm:ss.SSS".equals(utcTimePattern)){
            return utcTime;
        }

        SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePattern);
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUtcDate = null;
        try {
            gpsUtcDate = utcFormater.parse(utcTime);
        } catch (Exception e) {
            return null;
        }
        SimpleDateFormat localFormater = new SimpleDateFormat(localTimePattern);
        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(gpsUtcDate.getTime());
        return localTime;
    }

    /**
     * 函数功能描述:修改时间格式后缀
     * 函数使用场景:处理当后缀为:+8:00时,转换为:+08:00 或 -8:00转换为-08:00
     * @param subTime
     * @param sign
     * @return
     */
    private static String changeUtcSuffix(String subTime, String sign){
        String timeSuffix = null;
        String[] splitTimeArrayOne = subTime.split("\\" + sign);
        String[] splitTimeArrayTwo = splitTimeArrayOne[1].split(":");
        if(splitTimeArrayTwo[0].length() < 2){
            timeSuffix = "+" + "0" + splitTimeArrayTwo[0] + ":" + splitTimeArrayTwo[1];
            subTime = splitTimeArrayOne[0] + timeSuffix;
            return subTime;
        }
        return subTime;
    }
    /**
     * 函数功能描述:获取本地时区的表示(比如:第八区-->+08:00)
     * @return
     */
    public static String getTimeZoneByNumExpress(){
        Calendar cal = Calendar.getInstance();
        TimeZone timeZone = cal.getTimeZone();
        int rawOffset = timeZone.getRawOffset();
        int timeZoneByNumExpress = rawOffset/3600/1000;
        String timeZoneByNumExpressStr = "";
        if(timeZoneByNumExpress > 0 && timeZoneByNumExpress < 10){
            timeZoneByNumExpressStr = "+" + "0" + timeZoneByNumExpress + ":" + "00";
        }
        else if(timeZoneByNumExpress >= 10){
            timeZoneByNumExpressStr = "+" + timeZoneByNumExpress + ":" + "00";
        }
        else if(timeZoneByNumExpress > -10 && timeZoneByNumExpress < 0){
            timeZoneByNumExpress = Math.abs(timeZoneByNumExpress);
            timeZoneByNumExpressStr = "-" + "0" + timeZoneByNumExpress + ":" + "00";
        }else if(timeZoneByNumExpress <= -10){
            timeZoneByNumExpress = Math.abs(timeZoneByNumExpress);
            timeZoneByNumExpressStr = "-" + timeZoneByNumExpress + ":" + "00";
        }else{
            timeZoneByNumExpressStr = "Z";
        }
        return timeZoneByNumExpressStr;
    }
    /**
     * 函数功能描述:UTC时间转本地时间格式
     * @param utcTime UTC时间
     * @param utcTimePatten UTC时间格式
     * @param localTimePatten   本地时间格式
     * @return 本地时间格式的时间
     * eg:utc2Local("2017-06-14 09:37:50.788+08:00", "yyyy-MM-dd HH:mm:ss.SSSXXX", "yyyy-MM-dd HH:mm:ss.SSS")
     */
    public static Date local2Utc(String localTime, String localTimePatten, String utcTimePatten) {
        SimpleDateFormat utcFormater = new SimpleDateFormat(localTimePatten);
        utcFormater.setTimeZone(TimeZone.getDefault());//时区定义并进行时间获取
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(localTime);
        } catch (ParseException e) {
            return null;
        }
        SimpleDateFormat localFormater = new SimpleDateFormat(utcTimePatten);
        localFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        String utcTime = localFormater.format(gpsUTCDate.getTime());
        try {
			return parse(utcTime, utcTimePatten);
		} catch (ParseException e) {
			return null;
		}
    }
	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:52:53 <br>
	 * Description : 将日期类型转换为simpleDateFormat类型 <br>
	 * @param parttern 要转换的日期类型
	 * @return SimpleDateFormat 返回类型 SimpleDateFormat 返回一个SimpleDateFormat类型的日期字符串
	 */
	private static SimpleDateFormat getFormatter(String parttern) {
		return new SimpleDateFormat(parttern);
	}

	/**
	 * 
	 * 获取当前日期
	 * 
	 * @return 返回类型 String 一个包含年月日的<code>String</code>型日期，但不包含时分秒。yyyy-MM-dd
	 */
	public static String getCurrDateStr() {
		return format(getCurrDate(), YYYY_MM_DD);
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:53:22 <br>
	 * Description : 获取当前时间 <br>
	 * @return String 返回类型 String 一个包含年月日时分秒的<code>String</code>型日期。hh:mm:ss
	 */
	public static String getCurrTimeStr() {
		return format(getCurrDate(), HH_MM_SS);
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:53:48 <br>
	 * Description : 获取当前完整时间 <br>
	 * @return String 返回类型 String 一个包含年月日时分秒的<code>String</code>型日期。yyyy-MM-dd hh:mm:ss
	 */
	public static String getCurrDateTimeStr() {
		return format(getCurrDate(), YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:54:15 <br>
	 * Description : 当前时间 <br>
	 * @return String
	 */
	public static String getCurrDateTime() {
		return format(getCurrDate(), formatStr_yyyyMMddHHmmssS1);
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:54:41 <br>
	 * Description : 获取当前年份样式 <br>
	 * @return String 返回类型 String 当前年分 yyyy
	 */
	public static String getYear() {
		return format(getCurrDate(), YYYY);
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:55:03 <br>
	 * Description : 获取当前季度的第一个月 <br>
	 * @param quarter 第几季度:如 1,2,3,4
	 * @return String 返回类型 String 当前季度的第一个月数
	 */
	public static String getFirstMonthOfQuarter(String quarter) {
		int qu = Integer.parseInt(quarter);
		String month = "";
		if (qu == 1) {
			month = "01";
		} else if (qu == 2) {
			month = "04";
		} else if (qu == 3) {
			month = "07";
		} else if (qu == 4) {
			month = "10";
		}
		return month;
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:55:38 <br>
	 * Description : 获取当前季度的最后一个月 <br>
	 * @param quarter 第几季度 如 1,2,3,4
	 * @return String 返回类型 String 当前季度的最后一个月
	 */
	public static String getLastMonthOfQuarter(String quarter) {
		int qu = Integer.parseInt(quarter);
		String month = "";
		if (qu == 1) {
			month = "03";
		} else if (qu == 2) {
			month = "06";
		} else if (qu == 3) {
			month = "09";
		} else if (qu == 4) {
			month = "12";
		}
		return month;
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:56:07 <br>
	 * Description : 获取当前季度 <br>
	 * @return String 返回类型 String 当前季度 如1,2,3,4
	 */
	public static String getQuarter() {
		String quar = "";
		if (Integer.parseInt(getMonth()) == 1
				|| Integer.parseInt(getMonth()) == 2
				|| Integer.parseInt(getMonth()) == 3) {
			quar = "1";
		} else if (Integer.parseInt(getMonth()) == 4
				|| Integer.parseInt(getMonth()) == 5
				|| Integer.parseInt(getMonth()) == 6) {
			quar = "2";
		} else if (Integer.parseInt(getMonth()) == 7
				|| Integer.parseInt(getMonth()) == 8
				|| Integer.parseInt(getMonth()) == 9) {
			quar = "3";
		} else if (Integer.parseInt(getMonth()) == 10
				|| Integer.parseInt(getMonth()) == 11
				|| Integer.parseInt(getMonth()) == 12) {
			quar = "4";
		}
		return quar;
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:56:35 <br>
	 * Description : 获取当前月 <br>
	 * @return String 返回类型 String 获取当前月
	 */
	public static String getMonth() {
		return format(getCurrDate(), MM);
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:57:02 <br>
	 * Description : 获取当前周 <br>
	 * @return String 返回类型 String 获取当前周
	 */
	public static String getWeek() {
		Calendar cal = Calendar.getInstance();
		int weekofmonth = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		return String.valueOf(weekofmonth);
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:57:27 <br>
	 * Description : 根据输入的年月周数来取该周第一天 <br>
	 * @param year 年份(> 0)
	 * @param month 月份(1-12)
	 * @param week 当月周数(1-5)
	 * @return String 返回类型 String 该周第一天（周日）
	 */
	public static String getFirstDayByMonthWeek(int year, int month, int week) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.WEEK_OF_MONTH, week);

		int firstDayofweek = c.getFirstDayOfWeek();

		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.YEAR, year);
		c1.set(Calendar.MONTH, month - 1);
		c1.set(Calendar.WEEK_OF_MONTH, week);
		c1.set(Calendar.DAY_OF_WEEK, firstDayofweek);
		Date d1 = new Date(c1.getTimeInMillis());
		return df.format(d1);
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:58:06 <br>
	 * Description : 根据输入的年月周数来取该周最后一天 <br>
	 * @param year 年份(> 0)
	 * @param month 月份(1-12)
	 * @param week 当月周数(1-5)
	 * @return String 返回类型 String 该周最后一天（周六）
	 */
	public static String getLastDayByMonthWeek(int year, int month, int week) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.WEEK_OF_MONTH, week);

		int firstDayofweek = c.getFirstDayOfWeek();
		int lastDayofweek = firstDayofweek + 6;

		Calendar c2 = Calendar.getInstance();
		c2.set(Calendar.YEAR, year);
		c2.set(Calendar.MONTH, month - 1);
		c2.set(Calendar.WEEK_OF_MONTH, week);
		c2.set(Calendar.DAY_OF_WEEK, lastDayofweek);
		Date d2 = new Date(c2.getTimeInMillis());
		return df.format(d2);
	}

	/**
	 * Description : 计算两个日期之间的月差 <br>
	 * @param strDate1 日期字串1
	 * @param strDate2 日期字串2
	 * @return 返回类型 int 两个日期之间的月差
	 * @throws int GlobalException
	 */
	public static int getIntevalMonth(String strDate1, String strDate2)
			throws GlobalException {
		int iMonth = 0;
		int flag = 0;
		try {
			Calendar objCalendarDate1 = Calendar.getInstance();
			objCalendarDate1.setTime(DateFormat.getDateInstance().parse(
					strDate1));

			Calendar objCalendarDate2 = Calendar.getInstance();
			objCalendarDate2.setTime(DateFormat.getDateInstance().parse(
					strDate2));

			if (objCalendarDate2.equals(objCalendarDate1))
				return 0;
			if (objCalendarDate1.after(objCalendarDate2)) {
				Calendar temp = objCalendarDate1;
				objCalendarDate1 = objCalendarDate2;
				objCalendarDate2 = temp;
			}
			if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1
					.get(Calendar.DAY_OF_MONTH))
				flag = 1;
			if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1
					.get(Calendar.YEAR)) {
				iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1
						.get(Calendar.YEAR))
						* 12
						+ objCalendarDate2.get(Calendar.MONTH) - flag)
						- objCalendarDate1.get(Calendar.MONTH);
			} else {
				iMonth = objCalendarDate2.get(Calendar.MONTH)
						- objCalendarDate1.get(Calendar.MONTH) - flag;
			}
		} catch (Exception e) {
			System.err.print(e);
			throw new GlobalException("" + e.getMessage());
		}
		return iMonth;
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:59:32 <br>
	 * Description :获取当前星期  <br>
	 * @return String 返回类型 String 当前星期几(一、二、三、四等)
	 */
	public static String getDayOfWeek() {
		String s = "";
		Calendar aCalendar = Calendar.getInstance();
		int x = aCalendar.get(Calendar.DAY_OF_WEEK);
		String a = Integer.toString(x - 1);
		if ("1".equals(a)) {
			s = "一";
		} else if ("2".equals(a)) {
			s = "二";
		} else if ("3".equals(a)) {
			s = "三";
		} else if ("4".equals(a)) {
			s = "四";
		} else if ("5".equals(a)) {
			s = "五";
		} else if ("6".equals(a)) {
			s = "六";
		} else if ("7".equals(a)) {
			s = "日";
		}
		return s;
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午8:59:54 <br>
	 * Description : 获取当前日期号样式：dd <br>
	 * @return String 返回类型 String 当前日期号
	 */
	public static String getDay() {
		return format(getCurrDate(), DD);
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:00:21 <br>
	 * Description : 按给定日期样式判断给定字符串是否为合法日期数据 <br>
	 * @param strDate 要判断的日期
	 * @param pattern 日期样式
	 * @return isDate 返回类型 boolean 如果是 返回true,否则返回false
	 */
	public static boolean isDate(String strDate, String pattern) {
		try {
			parse(strDate, pattern);
			return true;
		} catch (ParseException pe) {
			return false;
		}
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:00:50 <br>
	 * Description : 判断给定字符串是否为特定格式年份（格式：yyyy）数据 <br>
	 * @param strDate 要判断的日期
	 * @return boolean 返回类型 boolean 如果是返回true，否则返回false
	 */
	public static boolean isYYYY(String strDate) {
		try {
			parse(strDate, YYYY);
			return true;
		} catch (ParseException pe) {
			return false;
		}
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:01:15 <br>
	 * Description : 判断给定字符串是否为特定格式年月（格式：yyyy-MM）数据 <br>
	 * @param strDate 要判断的日期字串
	 * @return boolean 返回类型 boolean 如果是返回true，否则返回false
	 */
	public static boolean isYYYY_MM(String strDate) {
		try {
			parse(strDate, YYYY_MM);
			return true;
		} catch (ParseException pe) {
			return false;
		}
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:01:43 <br>
	 * Description : 判断给定字符串是否为特定格式的年月日（格式：yyyy-MM-dd）数据 <br>
	 * @param strDate 要判断的日期字串
	 * @return boolean 返回类型 boolean 如果是返回true，否则返回false
	 */
	public static boolean isYYYY_MM_DD(String strDate) {
		try {
			parse(strDate, YYYY_MM_DD);
			return true;
		} catch (ParseException pe) {
			return false;
		}
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:02:10 <br>
	 * Description : 判断给定字符串是否为特定格式年月日时分秒（格式：yyyy-MM-dd HH:mm:ss）数据 <br>
	 * @param strDate 要判断的日期
	 * @return boolean 返回类型 boolean 如果是返回true，否则返回false
	 */
	public static boolean isYYYY_MM_DD_HH_MM_SS(String strDate) {
		try {
			parse(strDate, YYYY_MM_DD_HH_MM_SS);
			return true;
		} catch (ParseException pe) {
			return false;
		}
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:02:37 <br>
	 * Description : 判断给定字符串是否为特定格式时分秒（格式：HH:mm:ss）数据 <br>
	 * @param strDate 要判断的日期
	 * @return boolean 返回类型 boolean 如果是返回true，否则返回false
	 */
	public static boolean isHH_MM_SS(String strDate) {
		try {
			parse(strDate, HH_MM_SS);
			return true;
		} catch (ParseException pe) {
			return false;
		}
	}
	/**
	 * Description : 获取给定日期字串的后intevalYears年的日期 <br>
	 * @param refenceDate 给定日期（格式为：yyyy-MM-dd）
	 * @param intevalYears 间隔月数
	 * @return String 返回类型 String 计算后的日期
	 */
	public static String getNextDate4Y(String refenceDate, int intevalYears) {
		try {
			int year=Integer.valueOf(refenceDate.substring(0, 4))+intevalYears;
			String month=refenceDate.substring(5, 7);
			String day=refenceDate.substring(8, 10);
			return year+"-"+month+"-"+day;
		} catch (Exception ee) {
			return "";
		}
	}
	/**
	 * Description : 获取给定日期字串的后intevalMonths月的日期 <br>
	 * @param refenceDate 给定日期（格式为：yyyy-MM-dd）
	 * @param intevalMonths 间隔月数
	 * @return String 返回类型 String 计算后的日期
	 */
	public static String getNextDate4M(String refenceDate, int intevalMonths) {
		try {
			int year=Integer.valueOf(refenceDate.substring(0, 4));
			int month=Integer.valueOf(refenceDate.substring(5, 7))+intevalMonths;
			String day=refenceDate.substring(8, 10);
			if(month>12) {
				year=year+month/12;
				month=month%12;
			}
			return year+"-"+(month<10?"0"+month:month)+"-"+day;
		} catch (Exception ee) {
			return "";
		}
	}
	/**
	 * Description : 获取给定日期字串的前intevalMonths月的日期 <br>
	 * @param refenceDate 给定日期（格式为：yyyy-MM-dd）
	 * @param intevalMonths 间隔月数
	 * @return String 返回类型 String 计算后的日期
	 */
	public static String getBeforeDate4M(String refenceDate, int intevalMonths) {
		try {
			int year=Integer.valueOf(refenceDate.substring(0, 4));
			int month=Integer.valueOf(refenceDate.substring(5, 7))-intevalMonths;
			String day=refenceDate.substring(8, 10);
			if(month<0) {
				month=-month;
				year=year-(month/12+1);
				month=month%12;
			}else if(month==0) {
				year=year-1;
				month=12;
			}
			return year+"-"+month+"-"+day;
		} catch (Exception ee) {
			return "";
		}
	}
	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:03:03 <br>
	 * Description : 获取给定日期字串的后intevalDay天的日期 <br>
	 * @param refenceDate 给定日期（格式为：yyyy-MM-dd）
	 * @param intevalDays 间隔天数
	 * @return String 返回类型 String 计算后的日期
	 */
	public static String getNextDate(String refenceDate, int intevalDays) {
		try {
			return getNextDate(parse(refenceDate, YYYY_MM_DD), intevalDays);
		} catch (Exception ee) {
			return "";
		}
	}
	/**
	 * Description : 获取给定日期字串的后intevalDay天的日期 <br>
	 * 排除周六周末
	 * @param refenceDate 给定日期（格式为：yyyy-MM-dd）
	 * @param intevalDays 间隔天数
	 * @return String 返回类型 String 计算后的日期
	 */
	public static String getNextWorkDate(String refenceDate, int intevalDays) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		String c=null;
		try {
			Date d = df.parse(refenceDate);
			ca.setTime(d);//设置当前时间
			c = addDateByWorkDay(ca,intevalDays);
		} catch (ParseException e) {
			c="";
		}
		return c;
	}
	/**
	 * 获取给定日期前n个工作日
	 * @param refenceDate
	 * @param intevalDays
	 * @return
	 */
	public static String getBeforeWorkDate(String refenceDate, int intevalDays) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		String c=null;
		try {
			Date d = df.parse(refenceDate);
			ca.setTime(d);//设置当前时间
			c = subDateByWorkDay(ca,intevalDays);
		} catch (ParseException e) {
			c="";
		}
		return c;
	}
	/**
	 * 获取给定日期前n个工作日
	 * @param refenceDate
	 * @param intevalDays
	 * @return
	 */
	public static String getBeforeDate(String refenceDate, int intevalDays) {
		try {
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			calendar.setTime(parse(refenceDate, YYYY_MM_DD));
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-intevalDays);
			return format(calendar.getTime(), YYYY_MM_DD);
		} catch (ParseException e) {
			return null;
		}
	}
	/**
	 * 给定日期加 day天后的日期
	 * @param calendar 给定日期
	 * @param day 加日期
	 * @return
	 */
	 public static String addDateByWorkDay(Calendar calendar,int day){
			 try {
				for (int i = 0; i < day; i++) {
					 calendar.add(Calendar.DAY_OF_MONTH, 1);
					 if(checkHoliday(calendar)){
						 i--;
					 }
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return  df.format(calendar.getTime());
	 }
	 /**
	 * 给定日期加 day天前的日期
	 * @param calendar 给定日期
	 * @param day 加日期
	 * @return
	 */
	 public static String subDateByWorkDay(Calendar calendar,int day){
			 try {
				for (int i = 0; i < day; i++) {
					 calendar.add(Calendar.DAY_OF_MONTH, -1);
					 if(checkHoliday(calendar)){
						 i--;
					 }
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return  df.format(calendar.getTime());
	 }
	 /**
	  * 
	  * <p>Title: checkHoliday </P>
	  * <p>Description: TODO 验证日期是否是节假日</P>
	  * @param calendar  传入需要验证的日期
	  * @return 
	  * return boolean    返回类型  返回true是节假日，返回false不是节假日
	  * throws 
	  * date 2014-11-24 上午10:13:07
	  */
	 public static boolean checkHoliday(Calendar calendar) throws Exception{
		 //判断日期是否是周六周日
		 if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || 
				 calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
			 return true;
		 }
		 return false;
	 }
	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:03:44 <br>
	 * Description : 获取给定日期的后intevalDay天的日期 <br>
	 * @param refenceDate 给定日期
	 * @param intevalDays 间隔天数
	 * @return String 返回类型 String 计算后的日期
	 */
	public static String getNextDate(Date refenceDate, int intevalDays) {
		try {
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			calendar.setTime(refenceDate);
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)
					+ intevalDays);
			return format(calendar.getTime(), YYYY_MM_DD);
		} catch (Exception ee) {
			return "";
		}
	}
	/**
	 * Description : 获取给定日期的后intevalDay小时的日期 <br>
	 * @param refenceDate 给定日期
	 * @param intevalDays 间隔小时
	 * @return String 返回类型 String 计算后的日期
	 */
	public static String getNextDate4Hours(Date refenceDate, int intevalDays) {
		try {
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			calendar.setTime(refenceDate);
			calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR)+ intevalDays);
			return format(calendar.getTime(), YYYY_MM_DD_HH_MM_SS);
		} catch (Exception ee) {
			return "";
		}
	}
	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:04:24 <br>
	 * Description : 获取给定日期字串的间隔天数 <br>
	 * @param startDate 起始时间字串 如 "2009-10-10"
	 * @param endDate 结束时间字串 如 "2009-12-10"
	 * @return long 返回类型 long 间隔天数
	 */
	public static long getIntevalDays(String startDate, String endDate) {
		try {
			return getIntevalDays(parse(startDate, YYYY_MM_DD),
					parse(endDate, YYYY_MM_DD));
		} catch (Exception ee) {
			return 0l;
		}
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:04:58 <br>
	 * Description : 获取给定日期的间隔天数 <br>
	 * @param startDate 起始时间字串,如:new Date()
	 * @param endDate 结束时间字串,如:new Date()
	 * @return long 返回类型 long 间隔天数
	 */
	public static long getIntevalDays(Date startDate, Date endDate) {
		try {
			java.util.Calendar startCalendar = java.util.Calendar.getInstance();
			java.util.Calendar endCalendar = java.util.Calendar.getInstance();

			startCalendar.setTime(startDate);
			endCalendar.setTime(endDate);
			long diff = endCalendar.getTimeInMillis()
					- startCalendar.getTimeInMillis();

			return (diff / (1000 * 60 * 60 * 24));
		} catch (Exception ee) {
			return 0l;
		}
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:05:34 <br>
	 * Description : 求当前日期和指定字符串日期的相差天数 <br>
	 * @param date 指定字符串日期 如: "2009-10-12"
	 * @return long 返回类型 long 当前日期和指定字符串日期的相差天数
	 */
	public static long getTodayIntevalDays(String date) {
		try {
			Date currentDate = new Date();
			SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date theDate = myFormatter.parse(date);
			long days = (currentDate.getTime() - theDate.getTime())
					/ (24 * 60 * 60 * 1000);

			return days;
		} catch (Exception ee) {
			return 0l;
		}
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:06:01 <br>
	 * Description : 求当前日期和指定日期的相差天数 <br>
	 * @param date 指定字符串日期,如: new Date()
	 * @return long 返回类型 long 当前日期和指定字符串日期的相差天数
	 */
	public static long getTodayIntevalDays(Date date) {
		try {

			Date currentDate = new Date();
			long days = (currentDate.getTime() - date.getTime())
					/ (24 * 60 * 60 * 1000);

			return days;
		} catch (Exception ee) {
			return 0l;
		}
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:06:30 <br>
	 * Description : 把指定时间字符串转换成日期型 <br>
	 * @param dateTimeStr 指定时间字符
	 * @return Date 返回类型 Date 转换后的日期
	 */
	public static Date parseToDate(String dateTimeStr) {
		if (dateTimeStr == null)
			return null;
		Date d = null;
		int formatStrLength = formatStr.length;
		for (int i = 0; i < formatStrLength; i++) {
			d = parseToDate2(dateTimeStr, formatStr[i]);
			if (d != null) {
				break;
			}
		}
		return d;
	}

	/**
	 * Description : 把Long型时间转换成指定字符型日期 <br>
	 * @param millSec Long型日期
	 * @return String 时间字符
	 */
	public static String parseToDateStr(long millSec) {
		SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		Date date = new Date(millSec);
		return sdf.format(date);
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:07:23 <br>
	 * Description : 把指定时间字符串转换成指定日期型 <br>
	 * @param dateTimeStr 指定时间字符
	 * @param formatString 指定时间日期类型,如：yyyy_MM_dd
	 * @return Date 返回类型 Date 转换后的日期
	 */
	private static Date parseToDate2(String dateTimeStr, String formatString) {
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat(formatString);
		try {
			d = sdf.parse(dateTimeStr);
		} catch (ParseException pe) {

		}
		return d;
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:07:56 <br>
	 * Description : 把指定日期时间转换为字符串 <br>
	 * @param datetime 指定日期时间
	 * @return String 返回类型 String 日期时间字符串
	 */
	public static String dateTimeToString(Date datetime) {
		java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();
		calendar.setTime(datetime);
		String dateTime = calendar.get(Calendar.YEAR) + ""
				+ (calendar.get(Calendar.MONTH) + 1 > 9 ? "" : "0")
				+ (calendar.get(Calendar.MONTH) + 1) + ""
				+ (calendar.get(Calendar.DATE) > 9 ? "" : "0")
				+ calendar.get(Calendar.DATE) + ""
				+ (calendar.get(Calendar.HOUR_OF_DAY) > 9 ? "" : "0")
				+ calendar.get(Calendar.HOUR_OF_DAY) + ""
				+ (calendar.get(Calendar.MINUTE) > 9 ? "" : "0")
				+ calendar.get(Calendar.MINUTE) + ""
				+ (calendar.get(Calendar.SECOND) > 9 ? "" : "0")
				+ calendar.get(Calendar.SECOND);
		return dateTime;
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:08:24 <br>
	 * Description : 根据指定年,月,日得到一周的第几天 <br>
	 * @param year 指定年
	 * @param month 指定月
	 * @param day 指定日
	 * @return int 返回类型 int 得到一周的第几天
	 */
	public static int getDayOfWeek(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(year, month - 1, day);
		int dayofWeek = cal.get(Calendar.DAY_OF_WEEK);
		switch (dayofWeek) {
		case 1:
			dayofWeek = 7;
			break;
		case 2:
			dayofWeek = 1;
			break;
		case 3:
			dayofWeek = 2;
			break;
		case 4:
			dayofWeek = 3;
			break;
		case 5:
			dayofWeek = 4;
			break;
		case 6:
			dayofWeek = 5;
			break;
		case 7:
			dayofWeek = 6;
			break;
		}

		return dayofWeek;
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:08:56 <br>
	 * Description : 根据指定年,月,日得到一年的第几周 <br>
	 * @param year 指定年
	 * @param month 指定月
	 * @param day 指定日
	 * @return 回类型 int 得到一年的第几周
	 */
	public static int getWeekOfYear(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(year, month - 1, day);
		// SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
		// System.err.println(formatter.format(cal.getTime()));
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:09:22 <br>
	 * Description : 根据指定年,月,日得到一月的第几天 <br>
	 * @param year 指定年
	 * @param month 指定月
	 * @param day 指定日
	 * @return int 返回类型 int 得到一月的第几天
	 */
	public static int getDayOfMonth(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(year, month - 1, day);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:09:53 <br>
	 * Description : 根据指定年,得到上一月 <br>
	 * @param month 指定月
	 * @return String 返回类型 String 返回上月是第几月
	 */
	public static String getFrontMonth(String month) {
		int mon = Integer.parseInt(month);
		String frontmonth = "";
		if (mon == 1) {
			frontmonth = "12";
		} else if (mon < 11) {
			frontmonth = "0" + String.valueOf(mon - 1);
		} else {
			frontmonth = String.valueOf(mon - 1);
		}
		return frontmonth;
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:10:16 <br>
	 * Description : 根据指定年,月得到下一月 <br>
	 * @param month 指定月
	 * @return String 返回类型 String 返回下月是第几月
	 */
	public static String getNextMonth(String month) {
		if (month.contains("-")) {
			month = month.replace("-", "");
		}
		int year = Integer.parseInt(month.substring(0, 4));
		int mon = Integer.parseInt(month.substring(4, 6));
		String nextmonth = "";
		String nextyear = String.valueOf(year);
		if (mon == 12) {
			nextmonth = "01";
			nextyear = String.valueOf(year + 1);
		} else if (mon < 9) {
			nextmonth = "0" + String.valueOf(mon + 1);
		} else {
			nextmonth = String.valueOf(mon + 1);
		}
		return nextyear + nextmonth;
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:10:39 <br>
	 * Description : 根据指定年月字符串得到下个季度的月份 <br>
	 * @param yearAndMonth 指定年月字符 如"200910"
	 * @return String 返回类型 String 下个季度的月份 如"201001"
	 */
	public static String getMonthOfNextQuarter(String yearAndMonth) {
		int year = Integer.parseInt(yearAndMonth.substring(0, 4));
		int mon = Integer.parseInt(yearAndMonth.substring(4, 6));
		String nextmonth = "";
		String nextyear = String.valueOf(year);
		if (mon == 10) {
			nextmonth = "01";
			nextyear = String.valueOf(year + 1);
		} else if (mon == 11) {
			nextmonth = "02";
			nextyear = String.valueOf(year + 1);
		} else if (mon == 12) {
			nextmonth = "03";
			nextyear = String.valueOf(year + 1);
		} else if (mon < 7) {
			nextmonth = "0" + String.valueOf(mon + 3);
		} else {
			nextmonth = String.valueOf(mon + 3);
		}
		return nextyear + nextmonth;
	}
	/**
	 * Description : 根据指定年月字符串得到上个季度对应的月份 <br>
	 * @param yearAndMonth 指定年月字符 如"200910"
	 * @return String 返回类型 String 下个季度的月份 如"200907"
	 */
	public static String getMonthOfBeforeQuarter(String yearAndMonth) {
		int year = Integer.parseInt(yearAndMonth.substring(0, 4));
		int mon = Integer.parseInt(yearAndMonth.substring(4, 6));
		String nextmonth = "";
		String nextyear = String.valueOf(year);
		if (mon ==1) {
			nextmonth = "10";
			nextyear = String.valueOf(year - 1);
		} else if (mon ==2) {
			nextmonth = "11";
			nextyear = String.valueOf(year - 1);
		} else if (mon ==3) {
			nextmonth = "12";
			nextyear = String.valueOf(year - 1);
		} else{
			nextmonth = "0" + String.valueOf(mon - 3);
		}
		return nextyear + nextmonth;
	}
	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:19:18 <br>
	 * Description : 求当前日期和指定字符串日期的相差小时数 <br>
	 * @param date 指定字符串日期
	 * @return long 返回类型 long 当前日期和指定字符串日期的相差小时数
	 */
	public static long getIntevalHours(String date) {
		try {
			Date currentDate = new Date();

			SimpleDateFormat myFormatter = new SimpleDateFormat(
					YYYY_MM_DD_HH_MM_SS);
			java.util.Date theDate = myFormatter.parse(date);

			long hours = (currentDate.getTime() - theDate.getTime())
					/ (60 * 60 * 1000);

			return hours;
		} catch (Exception ee) {
			return 0l;
		}
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:20:03 <br>
	 * Description : 求两日期对象间的小时数间隔 <br>
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 * @return long 返回类型 long 两日期对象间的小时数间隔
	 */
	public static long getIntevalHours(Date startDate, Date endDate) {
		try {
			long hours = 0;
			if (endDate == null) {
				hours = ((new Date()).getTime() - startDate.getTime())
						/ (60 * 60 * 1000);
			} else {
				hours = (endDate.getTime() - startDate.getTime())
						/ (60 * 60 * 1000);
			}

			return hours;
		} catch (Exception ee) {
			return 0;
		}
	}

	/**
	 * Description : 将指定日期格式化成"yyyy年M月d日"的形式，如将2000-01-01转换为2000年1月1日 <br>
	 * @param date 指定日期
	 * @return String 返回类型 String 格式化后日期字串
	 */
	public static String getChineseDate(Date date) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG,
				Locale.CHINESE);
		String strDate = df.format(date);
		return strDate;
	}
	/**
	 * Description : 将指定日期格式化成"yyyy年M月d日"的形式
     * 如将2000-01-01转换为2000年1月1日 <br>
	 * @param date 指定日期  YYYY_MM_DD
	 * @return String 返回类型 String yyyy年M月d日
	 */
	public static String getChineseDate(String date) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG,Locale.CHINESE);
		String strDate=null;
		try {
			strDate = df.format(parse(date, YYYY_MM_DD));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return strDate;
	}
	/**
	 * Description : 将指定日期格式化成"yyyy年M月d日"的形式
     * 如将2000-01-01转换为2000年1月1日 <br>
	 * @param date 指定日期  YYYY_MM_DD
	 * @return String 返回类型 String yyyy年M月d日 上午23时 15 分21秒
	 */
	public static String getChineseDateTime(String date) {
		//DateFormat df = DateFormat.getDateInstance(DateFormat.LONG,Locale.CHINESE);
		DateFormat df =DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.CHINESE);
		String strDate=null;
		try {
			if(date.length()>=16) {
				strDate = df.format(parse(date, formatStr_yyyyMMddHHmm));
			}else{
				strDate = df.format(parse(date, YYYY_MM_DD));
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return strDate;
	}
	/**
	 * Description : 将日期格式化成"yyyy-MM-dd HH:mm:ss"的形式，如"2000-12-3 12:53:48" <br>
	 * @param date 指定日期
	 * @return String 返回类型 String 格式化后日期字串
	 */
	public static String getLongFormatTime(java.util.Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = df.format(date);
		return strDate;
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:21:35 <br>
	 * Description : 将指定日期格式化成"yyyy-MM-dd"的形式，如"2000-12-3" <br>
	 * @param date 指定日期
	 * @return String 返回类型 String 格式化后的时间字串
	 */
	public static String getyyyyMMddDateStr(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = df.format(date);
		return strDate;
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:22:01 <br>
	 * Description : 获得今天 <br>
	 * @param begin 开始，如""
	 * @param end 结束，如:""
	 * @param now 现在，如:""
	 * @param calendar GregorianCalendar对象
	 */
	public static void calcToday(String begin, String end, String now,
			GregorianCalendar calendar) {

		DateUtils.begin = now;
		DateUtils.end = now;

	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:22:46 <br>
	 * Description : 获得昨天 <br>
	 * @param begin 开始 如""
	 * @param end 结束 如:""
	 * @param now 现在 如:""
	 * @param calendar GregorianCalendar对象
	 */
	public static void calcYesterday(String begin, String end, String now,
			GregorianCalendar calendar) {

		calendar.add(Calendar.DATE, -1);
		begin = new java.sql.Date(calendar.getTime().getTime()).toString();
		end = begin;
		DateUtils.begin = begin;
		DateUtils.end = end;

	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:23:15 <br>
	 * Description : 获得本周 <br>
	 * @param begin 开始 如""
	 * @param end 结束 如:""
	 * @param now 现在 如:""
	 * @param calendar GregorianCalendar对象
	 */
	public static void calcThisWeek(String begin, String end, String now,
			GregorianCalendar calendar) {
		end = now;
		int minus = calendar.get(Calendar.DAY_OF_WEEK) - 2;
		if (minus < 0) {

		} else {

			calendar.add(Calendar.DATE, -minus);
			begin = new java.sql.Date(calendar.getTime().getTime()).toString();
			DateUtils.begin = begin;
			DateUtils.end = end;

		}
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:23:48 <br>
	 * Description :  获得上一周 <br>
	 * @param begin 开始 如""
	 * @param end 结束 如:""
	 * @param now 现在 如:""
	 * @param calendar  GregorianCalendar对象
	 */
	public static void calcLastWeek(String begin, String end, String now,
			GregorianCalendar calendar) {

		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		int minus = calendar.get(Calendar.DAY_OF_WEEK);

		calendar.add(Calendar.DATE, -minus + 1);
		end = new java.sql.Date(calendar.getTime().getTime()).toString();
		calendar.add(Calendar.DATE, -6);
		begin = new java.sql.Date(calendar.getTime().getTime()).toString();
		DateUtils.begin = begin;
		DateUtils.end = end;

	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:24:17 <br>
	 * Description : 获得本月 <br>
	 * @param begin 开始 如""
	 * @param end 结束 如:""
	 * @param now 现在 如:""
	 * @param calendar GregorianCalendar对象
	 */
	public static void calcThisMonth(String begin, String end, String now,
			GregorianCalendar calendar) {
		end = now;
		int dayOfMonth = calendar.get(Calendar.DATE);
		calendar.add(Calendar.DATE, -dayOfMonth + 1);
		begin = new java.sql.Date(calendar.getTime().getTime()).toString();
		DateUtils.begin = begin;
		DateUtils.end = end;

	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:24:43 <br>
	 * Description : 获得上月 <br>
	 * @param begin 开始 如""
	 * @param end 结束 如:""
	 * @param now 现在 如:""
	 * @param calendar GregorianCalendar对象
	 */
	public static void calcLastMonth(String begin, String end, String now,
			GregorianCalendar calendar) {

		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				1);
		calendar.add(Calendar.DATE, -1);
		end = new java.sql.Date(calendar.getTime().getTime()).toString();

		int month = calendar.get(Calendar.MONTH) + 1;
		begin = calendar.get(Calendar.YEAR) + "-" + month + "-01";
		DateUtils.begin = begin;
		DateUtils.end = end;

	}

	/**
	 * 当返回值为true时表示指定时间与当前时间之差在24小时内，若为false则表示不在24小时之内
	 * 
	 * @param time
	 *            指定时间串 如:"12000293293"
	 * @return 返回类型 boolean 指定时间与当前时间之差在24小时内，若为false则表示不在24小时之内
	 */
	public static boolean dateCompare(String time) {
		Date date = new Date();

		Long dateLongValue = date.getTime();
		Long timeLongVlaue = 0L;
		if (time != null && !time.equals("")) {

			timeLongVlaue = Long.parseLong(time);
		}

		if ((dateLongValue - timeLongVlaue) <= DAY_MILLISECOND) {
			return true;
		}
		return false;
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:25:11 <br>
	 * Description : 当返回值为true时表示指定时间与当前时间之差在同一月，若为false则表示不在同一月 <br>
	 * @param time 指定时间串 如:"12000293293"
	 * @return boolean 返回类型 boolean 指定时间与当前时间之差在同一月，若为false则表示不在同一月
	 */
	public static boolean compare(String time) {
		Date currentDate = new Date();
		int currentDay;
		int compareDay;

		Long timeLongVlaue = 0L;
		if (time != null && !time.equals("")) {
			timeLongVlaue = Long.parseLong(time);
		}
		Date compareDate = new Date(timeLongVlaue);
		Calendar compareCalendar = Calendar.getInstance();
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(currentDate);
		compareCalendar.setTime(compareDate);
		currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
		compareDay = compareCalendar.get(Calendar.DAY_OF_MONTH);
		int temp = currentDay - compareDay;
		if (temp == 1 || temp == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Description : 求当前日期和指定字符串日期的相差小时数 <br>
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return long
	 */
	public static long getIntevalHours(Timestamp startDate, Timestamp endDate) {
		try {
			long hours = 0;
			if (endDate == null) {
				hours = ((new Date()).getTime() - startDate.getTime())/ (60 * 60 * 1000);
			} else {
				hours = (endDate.getTime() - startDate.getTime())/ (60 * 60 * 1000);
			}
			return hours;
		} catch (Exception ee) {
			System.out.println(ee);
			return 0;
		}
	}
	/**
	 * Description : 求当前日期和指定字符串日期的相差小时数 <br>
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return long
	 */
	public static long getIntevalHours(String startDate, String endDate) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(startDate.length()==16) {
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			}else if(startDate.length()==13) {
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			}
			Date d1 = sdf.parse(startDate);
			Date d2 = sdf.parse(endDate);
			long hours = (d2.getTime() - d1.getTime())/ (60 * 60 * 1000);
			return hours;
		} catch (Exception ee) {
			System.out.println(ee);
			return 0;
		}
	}
	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:26:17 <br>
	 * Description : 求两个日期相差的分钟数 <br>
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return long
	 */
	public static long getIntevalMinutes(String startDate, String endDate) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Date d1 = sdf.parse(startDate);
			Date d2 = sdf.parse(endDate);
			long minute = (d2.getTime() - d1.getTime()) / 1000 / 60;
			return minute;
		} catch (Exception ee) {
			System.out.println(ee);
			return 0;
		}
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:26:48 <br>
	 * Description : 得到连个日期间的月份差 <br>
	 * @param fromDate 起始日期
	 * @param toDate 结束日期
	 * @return int
	 */
	public static int getMonthsBetweenTwoDate(Date fromDate, Date toDate) {
		int iMonth = 0;
		int flag = 0;
		try {
			Calendar objCalendarDate1 = Calendar.getInstance();
			objCalendarDate1.setTime(fromDate);

			Calendar objCalendarDate2 = Calendar.getInstance();
			objCalendarDate2.setTime(toDate);

			if (objCalendarDate2.equals(objCalendarDate1))
				return 0;
			if (objCalendarDate1.after(objCalendarDate2)) {
				Calendar temp = objCalendarDate1;
				objCalendarDate1 = objCalendarDate2;
				objCalendarDate2 = temp;
			}
			if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1
					.get(Calendar.DAY_OF_MONTH))
				flag = 1;

			if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1
					.get(Calendar.YEAR))
				iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1
						.get(Calendar.YEAR))
						* 12
						+ objCalendarDate2.get(Calendar.MONTH) - flag)
						- objCalendarDate1.get(Calendar.MONTH);
			else
				iMonth = objCalendarDate2.get(Calendar.MONTH)
						- objCalendarDate1.get(Calendar.MONTH) - flag;

		} catch (Exception e) {
			// e.printStackTrace();
		}
		return iMonth;
	}
	
	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:27:11 <br>
	 * Description : 日期提醒：与当前时间比较，是在今天昨天还是前天还是几天之前   <br>
	 * @param strDate 传入完整日期字符串
	 * @return String 返回字符串，格式为 今天/昨天/前天 上午/下午HH:mm 或者1~7天前 上午/下午HH:mm
	 * @throws GlobalException 传入字符串转换成Date 失败
	 */
	public static String getRemindDate(String strDate) throws GlobalException { 
		String time = "";  
		try {
			Date date =  DateUtils.parseToDate(strDate);
		    String todySDF = "aHH:mm";  
		    String yesterDaySDF = "昨天 aHH:mm";  
		    String otherSDF = "MM月dd日 aHH:mm";
		    String beforeYesterDay = "前天 aHH:mm";
		    String beyondYearSDF = "yyyy年MM月dd日 aHH:mm";
		    long betwween = 0L;
		    String betweenDay = "";
		    SimpleDateFormat sfd = null;  
		    Calendar dateCalendar = Calendar.getInstance();  
		    dateCalendar.setTime(date);  
		    Date now = new Date();  
		    Calendar targetCalendar = Calendar.getInstance();  
		    targetCalendar.setTime(now);  
		    targetCalendar.set(Calendar.HOUR_OF_DAY, 0);  
		    targetCalendar.set(Calendar.MINUTE, 0);  
		    if (dateCalendar.after(targetCalendar)) {  
		        sfd = new SimpleDateFormat(todySDF);  
		        time = sfd.format(date);  
		        return time;  
		    } else {  
		        targetCalendar.add(Calendar.DATE, -1);  
		        if (dateCalendar.after(targetCalendar)) {  
		            sfd = new SimpleDateFormat(yesterDaySDF);  
		            time = sfd.format(date);  
		            return time;  
		        }else {
		        	targetCalendar.add(Calendar.DATE, -1);
		        	 if (dateCalendar.after(targetCalendar)) {  
		 	            sfd = new SimpleDateFormat(beforeYesterDay);  
		 	            time = sfd.format(date);  
		 	            return time;  
		 	        }else {
		 	        	targetCalendar.setTime(now);
			 	        long diff = targetCalendar.getTimeInMillis()
			 	   				- dateCalendar.getTimeInMillis();
			 	   		betwween =  (diff / (1000 * 60 * 60 * 24));
			 	   		betweenDay = betwween+"天前 aHH:mm";
			 	   		if (betwween <= 7) {
			 	   			sfd = new SimpleDateFormat(betweenDay);  
			 	               time = sfd.format(date);  
			 	               return time;  
			 	   		}else {
				 	   		int year = targetCalendar.get(Calendar.YEAR)-dateCalendar.get(Calendar.YEAR);
				 	   		if (year!=0) {
				 	   		sfd = new SimpleDateFormat(beyondYearSDF);  
			 	               time = sfd.format(date);  
			 	               return time;  
							}
						}
						
					}
				} 
		    }
		    
		    sfd = new SimpleDateFormat(otherSDF);  
		    time = sfd.format(date); 
		} catch (Exception e) {
			throw new GlobalException("日期转换异常" + e.getMessage());
		}
	    return time;  
	}
	/**
	 * Create on : Paddy Zhang 2016年12月20日 下午7:28:36 <br>
	 * Description : 检验日期格式是否为0000-00-00 <br>
	 * @param date 日期字符串
	 * @return
	 */
	public static boolean checkDateFormat(String date) {
		if(StrUtils.isBlankOrNull(date))return false;
		//String eL = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
		String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		Pattern p = Pattern.compile(rexp);
		Matcher m = p.matcher(date);
		boolean dateFlag = m.matches();
		return dateFlag;
	}
	/**
	 * Create on : Paddy Zhang 2017年5月24日 上午11:15:38 <br>
	 * Description : 获取 n个工作日后的日期 <br>
	 * @param date 日期类型的时间
	 * @param num 工作日
	 * @return 返回  	yyyy-MM-dd 格式的字符串日期
	 */
	public String getWorkDay(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int mod = num % 5;
		int other = num / 5 * 7;
		for (int i = 0; i < mod;) {
			cal.add(Calendar.DATE, 1);
			switch (cal.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SUNDAY:
			case Calendar.SATURDAY:
				break;
			default:
				i++;
				break;
			}
		}
		if (other > 0)
			cal.add(Calendar.DATE, other);
		return DateUtils.format(cal.getTime(), DateUtils.formatStr_yyyyMMdd);
	}
	 /**
     * create date:2010-5-22下午03:32:31
     * 描述：将源字符串中的阿拉伯数字格式化为汉字
     * @param sign 源字符串中的字符
     * @return
     */
	public static char formatDigit(char sign){
       if(sign == '0')
           sign = '○';
       if(sign == '1')
           sign = '一';
       if(sign == '2')
           sign = '二';
       if(sign == '3')
           sign = '三';
       if(sign == '4')
           sign = '四';
       if(sign == '5')
           sign = '五';
       if(sign == '6')
           sign = '六';
       if(sign == '7')
           sign = '七';
       if(sign == '8')
           sign = '八';
       if(sign == '9')
           sign = '九';
       return sign;
	}
	/**
	 * 将日期转换为中文日期
	 * @param dateStr 2007-10-05
	 * @return  二○○七年十月五日
	 */
	public static String formatToChineseDate(String dateStr) {
		StringBuffer sb = new StringBuffer();
		if(null!=dateStr&&dateStr.length()>=10) {
			for(int i = 0; i < 4; i++){
	            sb.append(formatDigit(dateStr.charAt(i)));
	        }
			sb.append("年");
			for(int i = 5; i < 7; i++){
				char c=dateStr.charAt(i);
				if(c=='0') {
					continue;
				}else if(c=='1'&&i==5){
					sb.append("十");
				}else {
					sb.append(formatDigit(c));
				}
	        }
			sb.append("月");
			for(int i = 8; i < 10; i++){
				char c=dateStr.charAt(i);
				if(c=='0') {
					continue;
				}else if(c=='1'&&i==8){
					sb.append("十");
				}else if(i==8){
					sb.append(formatDigit(c)+"十");
				}else {
					sb.append(formatDigit(c));
				}
	        }
			sb.append("日");
		}
		return sb.toString();
	}
	/**
	 * 获取某月天数
	 * @param yyyy-mm-dd
	 * @return
	 */
	public static int getDaysOfMonth(String ymd) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(parse(ymd,YYYY_MM_DD));
			return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		} catch (ParseException e) {
			return 0;
		}
	}

	public static void main(String[] args) throws GlobalException {
		try {
			Date now = new Date();
			// String t = getDayOfWeek();
			//System.out.println(getRemindDate("2016-8-31 10:35:55"));
			System.out.println(format(now));
		} catch (Exception e) {
			// e.printStackTrace();
			throw new GlobalException("" + e.getMessage());
		}
	}
}