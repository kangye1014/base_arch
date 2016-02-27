package com.ishidai.ischedule.business.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间格式化工具类
 * 
 * @author liujinjie
 */
public class DateUtils {

	private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

	public static final String FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_1 = "MM/dd HH:mm";
	public static final String FORMAT_2 = "yyyy-MM-dd";
	public static final String FORMAT_3 = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_4 = "yyyyMMdd";
	public static final String FORMAT_5 = "yyyy年MM月dd日";
	public static final String FORMAT_6 = "yyyyMM";

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public static DateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	/**
	 * 字符串时间相比较
	 * 
	 * @param startTime
	 * @param endTime
	 * @return endTime>=startTime true 反之 false
	 */
	public static boolean compareDate(String startTime, String endTime) {
		if (StringToLong(endTime) >= StringToLong(startTime)) {
			return true;
		}
		return false;
	}

	/**
	 * @note 格式化日期
	 * @param date
	 * @param formatStr
	 * @return
	 * @author wangmeng
	 * @date 2015年9月14日 下午10:17:27
	 */
	public static Date format(Date date, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		try {
			return sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 字符串时间相比较
	 * 
	 * @param startTime
	 * @param endTime
	 * @return endTime>startTime true 反之 false
	 */
	public static boolean compareDateNot(String startTime, String endTime) {
		if (StringToLong(endTime) > StringToLong(startTime)) {
			return true;
		}
		return false;
	}

	/**
	 * 根据Calendar 获取星期几
	 * 
	 * @param cal
	 * @return
	 */
	public static String getWeekDay(Calendar cal) {
		String retStr = "";
		int weekday = cal.get(Calendar.DAY_OF_WEEK);
		switch (weekday) {
			case Calendar.MONDAY :
				retStr = "星期一";
				break;
			case Calendar.TUESDAY :
				retStr = "星期二";
				break;
			case Calendar.WEDNESDAY :
				retStr = "星期三";
				break;
			case Calendar.THURSDAY :
				retStr = "星期四";
				break;
			case Calendar.FRIDAY :
				retStr = "星期五";
				break;
			case Calendar.SATURDAY :
				retStr = "星期六";
				break;
			case Calendar.SUNDAY :
				retStr = "星期日";
				break;
		}
		return retStr;
	}

	/**
	 * 字符串解析为Date
	 * 
	 * @param time
	 * @return
	 */
	public static Date StringToDate(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 字符串解析为Date
	 * 
	 * @param time
	 *            format
	 * @return
	 */
	public static Date StringToDate(String time, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 字符串是否解析为Date
	 * 
	 * @param time
	 *            format
	 * @return
	 */
	public static boolean parseToDateTrue(String time, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			sdf.parse(time);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * 字符串解析为long
	 * 
	 * @param time
	 * @return
	 */
	public static long StringToLong(String time) {
		return StringToDate(time).getTime();
	}

	/**
	 * 字符串解析为long
	 * 
	 * @param time
	 * @return
	 */
	public static long StringToLong(String time, String format) {
		return StringToDate(time, format).getTime();
	}

	/**
	 * long型解析为Date
	 * 
	 * @param lon
	 * @return
	 */
	public static Date longToDate(long lon) {
		return new Date(lon);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date dayFormat(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			date = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 当第一个参数Date为null时，把第二个参数当默认值返回. 注意:不是格式化类型的意思.
	 * 
	 * @param date
	 * @param defaultValue
	 *            当date为null时返回默认值
	 * @return
	 * @author liujinjie
	 */
	public static String formateDate(Date date, String defaultValue) {
		if (defaultValue == null) {
			defaultValue = "--";
		}
		return date == null ? defaultValue : sdf.format(date);
	}

	public static String formateDate(Date date) {
		return formateDate(date, null);
	}

	/**
	 * 格式化当前时间. 格式化后如:2014-04-09 18:00:00
	 * 
	 * @return
	 * @author liujinjie
	 */
	public static String formateDateTime() {
		DateFormat df = new SimpleDateFormat(FORMAT_DATE);
		String dateresult = df.format(new Date());
		return dateresult;
	}

	/**
	 * 格式化当前时间. 格式化后如:2014-04-09 18:00:00
	 * 
	 * @return
	 * @author liujinjie
	 */
	public static String formateDate() {
		DateFormat df = new SimpleDateFormat(FORMAT_2);
		String dateresult = df.format(new Date());
		return dateresult;
	}

	/**
	 * 格式化当前时间
	 * 
	 * @param formatType
	 *            格式化规则
	 * @return
	 * @author liujinjie
	 */
	public static String formateDate(String formatType) {
		DateFormat df = new SimpleDateFormat(formatType);
		String dateresult = df.format(new Date());
		return dateresult;
	}

	/**
	 * @Title: formateDateMonth
	 * @author: lengxuefei
	 * @CreateDate: 2014年4月22日
	 * @Description: 格式化到月（“yyyy-MM”）
	 * @param date
	 * @return
	 */
	public static String formateDateMonth(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM");
		return date == null ? "" : df.format(date);
	}

	/**
	 * 按指定格式格式化日期
	 * 
	 * @param date
	 * @param formatType
	 * @return
	 * @author liujinjie
	 */
	public static String formateDateByType(Date date, String formatType) {
		DateFormat df = new SimpleDateFormat(formatType);
		return df.format(date);
	}

	/**
	 * 验证time是否超时. 超过maxMin认为超时
	 * 
	 * @param time
	 *            验证时间,毫秒
	 * @param maxMin
	 *            单位分钟
	 * @return 小于指定分钟返回true;否则返回false
	 * @author liujinjie
	 */
	public static boolean checkTimeOut(long time, int maxMin) {
		try {
			long now = System.currentTimeMillis();
			long nowSec = now - time;
			logger.info("nowTime={},valTime={},sec={}", now, time, nowSec);
			if (nowSec > 0 && nowSec < (maxMin * 60 * 1000)) {
				return true;
			}
		} catch (Exception e) {
			logger.error("验证是否超时出错.{}", e.getMessage());
		}
		return false;
	}

	/**
	 * 验证Date是否超时
	 * 
	 * @param time
	 *            验证时间
	 * @param maxMin
	 *            单位分钟
	 * @return true没有超时;false已经超时
	 * @author liujinjie
	 */
	public static boolean checkTimeOut(Date date, int maxMin) {
		return checkTimeOut(date.getTime(), maxMin);
	}

	/**
	 * <pre>
	 * 得到一个日期对象＋n月后的日期
	 * 
	 * addMonths(2014-07-04, 1) = 2014-08-04
	 * </pre>
	 * 
	 * @param startDate
	 * @param month
	 * @return
	 * @author liujinjie
	 */
	public static Date addMonths(Date startDate, int month) {
		return new DateTime(startDate).plusMonths(month).toDate();
		// return org.apache.commons.lang3.time.DateUtils.addMonths(startDate,
		// month);
	}

	/**
	 * 将日期字符串转为日期 getDateByStr("2014-05-08", "yyyy-MM-dd") = 得到"2014-05-08"对应的日期
	 * 
	 * @param strDate
	 *            日期字符串
	 * @param format
	 *            日期字符串格式
	 * @return
	 * @throws ParseException
	 * @author liujinjie
	 */
	public static Date getDateByStr(String strDate, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = sdf.parse(strDate);
		return date;
	}

	/**
	 * <pre>
	 * 计算2个日期的月差值
	 * 
	 * getPlusMonth("2014-06-04", "2014-07-04", true ) = 1 
	 * getPlusMonth("2014-06-04", "2014-07-04", false) = 0 
	 * 
	 * getPlusMonth("2014-06-04", "2014-08-02", true ) = 1
	 * getPlusMonth("2014-06-04", "2014-08-02", false) = 1
	 * 
	 * getPlusMonth("2014-06-04", "2014-08-04", true ) = 2
	 * getPlusMonth("2014-06-04", "2014-08-04", false) = 1
	 * 
	 * getPlusMonth("2014-06-04", "2014-08-05", true ) = 2
	 * getPlusMonth("2014-06-04", "2014-08-05", false) = 2
	 * 
	 * </pre>
	 * 
	 * @param startDate
	 *            开始日期
	 * @param nowDate
	 *            结束日期
	 * @param isIncludeSameDay
	 *            true同一天,算一个月;false同一天,不算一个月
	 * @return
	 * @author liujinjie
	 */
	public static Integer getPlusMonth(Date startDate, Date nowDate, Boolean isIncludeSameDay) {
		if (isIncludeSameDay == null) {
			isIncludeSameDay = true;
		}
		DateTime startTime = new DateTime(startDate);
		DateTime endTime = new DateTime(nowDate);
		if (startTime.isAfter(endTime)) {
			return 0;
		}

		int dayStartTime = startTime.getDayOfMonth();
		int dayEndTime = endTime.getDayOfMonth();
		int monthStartTime = startTime.getMonthOfYear() - 1;
		int monthEndTime = endTime.getMonthOfYear() - 1;

		int subMonth = monthEndTime - monthStartTime;
		if (isIncludeSameDay) { // 6月4日到7月4日,算一个月
			if (dayEndTime < dayStartTime) {
				subMonth = subMonth - 1;
			}
		} else { // 6月4日到7月4日,不算一个月
			if (dayEndTime <= dayStartTime) {
				subMonth = subMonth - 1;
			}
		}
		return subMonth;
	}

	/**
	 * <pre>
	 * 整存宝计算期数,计算2个日期的月差值.
	 * 如:购买12个月整存宝,计算到指定日期的期数.
	 * 
	 * getPlusMonthForPlan("2014-06-04", "2014-07-04") = 1 
	 * getPlusMonthForPlan("2014-06-04", "2014-08-02") = 1
	 * getPlusMonthForPlan("2014-06-04", "2014-08-04") = 2
	 * getPlusMonthForPlan("2014-06-04", "2014-08-05") = 2
	 * 
	 * </pre>
	 * 
	 * @param startDate
	 *            开始日期
	 * @param nowDate
	 *            结束日期
	 * @return
	 * @author liujinjie
	 */
	public static Integer getPlusMonthForPlan(Date startDate, Date nowDate) {
		return getPlusMonth(startDate, nowDate, true);
	}

	/**
	 * 两个时间相差距离多少天多少小时多少分多少秒 (较大时间-较小时间)
	 * 
	 * @param maxTime
	 *            较大时间参数
	 * @param minTime
	 *            较小时间参数
	 * @return String 返回值为：xx天xx小时xx分xx秒
	 */
	public static String getDistanceHour(Date maxTime, Date minTime) {
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		try {
			long time1 = maxTime.getTime();
			long time2 = minTime.getTime();
			long diff = time1 - time2;
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		} catch (Exception e) {
			logger.error("格式化日期异常", e.getMessage());
		}
		if (day == 0 && hour == 0 && min == 0) {
			return sec + "秒";
		} else if (day == 0 && hour == 0) {
			return min + "分";
		} else if (day == 0) {
			return hour + "小时";
		} else if (day != 0 && hour == 0) {
			return day + "天";
		} else {
			return day + "天" + hour + "小时";
		}
	}

	/**
	 * 两个日期相差的天数
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 两个日期相差天数
	 */
	public static long getDateMargin(Date beginDate, Date endDate) {
		long margin = 0;

		margin = endDate.getTime() - beginDate.getTime();

		margin = margin / (1000 * 60 * 60 * 24);

		return margin;
	}

	/**
	 * @两个日期相差的秒数
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @author wangmeng
	 * @time 2015年4月28日下午11:02:21
	 */
	public static long getSecondMargin(Date beginDate, Date endDate) {
		return endDate.getTime() - beginDate.getTime();
	}

	/**
	 * 按指定格式格式化字符串返回日期
	 * 
	 * @param date
	 * @param formatType
	 * @return
	 * @author lengxuefei
	 */
	public static Date parseDateByType(String date, String formatType) {
		DateFormat df = new SimpleDateFormat(formatType);
		try {
			return df.parse(date);
		} catch (ParseException e) {
			logger.error("按指定格式格式化字符串返回日期", e.getMessage());
		}
		return null;
	}

	// public static String parseLongToStr(long lon,String formatType){
	// DateFormat df = new SimpleDateFormat(formatType);
	// }

	/**
	 * 将时间格式转换为年月日的long类型
	 * 
	 * @param d
	 * @return
	 * @author wangshaofen
	 */
	public static long chDateYmdToLong(Date d) {
		try {
			String timeStr = new DateTime(d).toString("yyyy-MM-dd");
			return new DateTime(timeStr).getMillis();
		} catch (Exception e) {
			logger.error("按指定格式格式化日期返回Long", e.getMessage());
		}
		return 0;

	}

	/**
	 * 获取当月第一天
	 * 
	 * @return
	 * @author wangzhen
	 */
	public static String getFirstMonthDay() {
		// 获取当月第一天
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		// 获取前月的第一天
		Calendar cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		return format.format(cale.getTime());
	}

	/**
	 * 获取当月最后一天
	 * 
	 * @return
	 * @author wangzhen
	 */
	public static String getLastMonthDay() {
		// 获取当月最后一天
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		// 获取前月的最后一天
		Calendar cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		return format1.format(cale.getTime());
	}

	/**
	 * 获取endTime大约startTime的天数(毫秒相差天数)
	 * 
	 * @param startTime
	 * @param endTime
	 * @return 0：代表startTme>=endTime >0:实际相差天数
	 * @author wangmeng
	 * @time 2014年12月30日下午4:52:26
	 */
	public static long getDays(Date startTime, Date endTime) {
		long startT = startTime.getTime();
		long endT = endTime.getTime();
		if (endT > startT) {
			return ((endT - startT) / (24 * 60 * 60 * 1000));
		} else {
			return 0l;
		}
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数 0：代表smdate>=bdate >0:实际相差天数
	 */
	public static long daysBetween(Date smdate, Date bdate) {
		long result = 0l;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(smdate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(bdate);
			long time2 = cal.getTimeInMillis();
			if (time2 > time1) {
				result = ((time2 - time1) / (1000 * 3600 * 24));
			} else {
				result = 0l;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 该日期是当月的第几天
	 * 
	 * @param date
	 * @return
	 * @author wangmeng
	 * @time 2014年12月31日下午3:02:29
	 */
	public static int getDays(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取当天某个时间 （小时）
	 * 
	 * @param hour
	 * @return
	 * @author yinjunlu
	 */
	public static Date getTodayOneHour(int hour) {
		return getTodayTime(hour, 0, 0);
	}

	/**
	 * 获取当天某个时间
	 * 
	 * @author yinjunlu
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date getTodayTime(int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		return calendar.getTime();
	}

	/**
	 * 判断两个日期是否是同一天
	 * 
	 * @author: yinjunlu
	 * @Date: 2015年1月14日
	 * @param date
	 * @param otherDate
	 * @return
	 */
	public static boolean isEquals(Date date, Date otherDate) {
		if (date == null || otherDate == null) {
			return false;
		}

		DateTime nowTime = new DateTime(formateDate(date));
		DateTime otherTime = new DateTime(formateDate(otherDate));

		return nowTime.equals(otherTime);
	}

	/**
	 * 判断两个日期先后 格式化为天
	 * 
	 * @author: yinjunlu
	 * @Date: 2015年1月14日
	 * @param date
	 * @param otherDate
	 * @return
	 */
	public static boolean after(Date date, Date otherDate) {
		if (date == null || otherDate == null) {
			return false;
		}

		DateTime nowTime = new DateTime(formateDate(date));
		DateTime otherTime = new DateTime(formateDate(otherDate));

		return nowTime.isAfter(otherTime);
	}

	/**
	 * （精确）判断两个日期先后
	 * 
	 * @author: Puhui
	 * @Date: 2015年6月4日
	 * @param date
	 * @param otherDate
	 * @return
	 */
	public static boolean afterTime(Date beforeDate, Date afterDate) {
		if (beforeDate == null || afterDate == null) {
			return false;
		}

		Calendar beforeTime = Calendar.getInstance();
		beforeTime.setTime(beforeDate);

		Calendar endTime = Calendar.getInstance();
		endTime.setTime(afterDate);

		return endTime.getTimeInMillis() < beforeTime.getTimeInMillis();
	}

	/**
	 * 日期加上天数的时间
	 * 
	 * @author: yinjunlu
	 * @Date: 2015年1月28日
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDays(Date date, int day) {
		return add(date, Calendar.DAY_OF_YEAR, day);
	}

	/**
	 * 日期加上分钟的时间
	 * 
	 * @author: yinjunlu
	 * @Date: 2015年1月28日
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addMinute(Date date, int minute) {
		return add(date, Calendar.MINUTE, minute);
	}

	public static long millisBetween(Date start, Date end) {
		return end.getTime() - start.getTime();
	}

	private static Date add(Date date, int type, int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, value);
		return calendar.getTime();
	}

	/**
	 * @获取date是当月的第几天
	 * @param date
	 * @return
	 * @author wangmeng
	 * @time 2015年4月13日下午3:16:03
	 */
	public static int getMonthDays(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static String dateToStr(Date date, String pattern) {
		if ((date == null) || (date.equals("")))
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}

	public static String dateToStr(Date date) {
		return dateToStr(date, "yyyy/MM/dd");
	}

	public static String dateToStr(Date date, int type) {
		switch (type) {
			case 0 :
				return dateToStr(date);
			case 1 :
				return dateToStr(date, "yyyy/MM");
			case 2 :
				return dateToStr(date, "yyyyMMdd");
			case 11 :
				return dateToStr(date, "yyyy-MM-dd");
			case 3 :
				return dateToStr(date, "yyyyMM");
			case 4 :
				return dateToStr(date, "yyyy/MM/dd HH:mm:ss");
			case 5 :
				return dateToStr(date, "yyyyMMddHHmmss");
			case 6 :
				return dateToStr(date, "yyyy/MM/dd HH:mm");
			case 7 :
				return dateToStr(date, "HH:mm:ss");
			case 8 :
				return dateToStr(date, "HH:mm");
			case 9 :
				return dateToStr(date, "HHmmss");
			case 10 :
				return dateToStr(date, "HHmm");
			case 12 :
				return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
			case 13 :
				return dateToStr(date, "yyyyMMddHHmmss");
		}
		throw new IllegalArgumentException("Type undefined : " + type);
	}

}
