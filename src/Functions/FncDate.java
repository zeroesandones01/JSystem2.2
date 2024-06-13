package Functions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import DateChooser._JDateChooser;

public class FncDate {

	public static String format(Date date){
		return new SimpleDateFormat("MMM dd, yyyy").format(date);
	}
	
	public static String format(String format, Date date){
		return new SimpleDateFormat(format).format(date);
	}
	
	public static Date getLastDateOfMonth(int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, month - 1);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		return cal.getTime();
	}

	public static Date getLastDayOfMonth(Date from){
		try {
			Calendar calendar = Calendar.getInstance();  
			calendar.setTime(from);  

			calendar.add(Calendar.MONTH, 1);  
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.DATE, -1);

			if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
				calendar.add(Calendar.DATE, -1);
			}
			if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
				calendar.add(Calendar.DATE, -1);
			}

			return calendar.getTime();
		} catch (NullPointerException e) {
			return null;
		}
	}

	public static String getLastDayOfMonth(String format, Date from){
		try {
			Calendar calendar = Calendar.getInstance();  
			calendar.setTime(from);  

			calendar.add(Calendar.MONTH, 1);  
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.DATE, -1);

			if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
				calendar.add(Calendar.DATE, -1);
			}
			if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
				calendar.add(Calendar.DATE, -1);
			}

			return new SimpleDateFormat(format).format(calendar.getTime());
		} catch (NullPointerException e) {
			return "";
		}
	}

	public static void setLastDayOfMonth(Date from, _JDateChooser dateTo){
		try {
			Calendar calendar = Calendar.getInstance();  
			calendar.setTime(from);  

			calendar.add(Calendar.MONTH, 1);  
			calendar.set(Calendar.DAY_OF_MONTH, 1);  
			calendar.add(Calendar.DATE, -1);  

			Date firstDayOfMonth = calendar.getTime();  

			dateTo.setDate(firstDayOfMonth);
		} catch (NullPointerException e) {
			dateTo.setDate(null);
		}
	}

	public static Date getDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);

		return cal.getTime();
	}
	
	public static int getNumberOfDays(Date dateFrom, Date dateTo) {
		Calendar calLooper = Calendar.getInstance();
		calLooper.setTime(dateFrom);

		int numberOfDays = 0;
		
		while(!calLooper.getTime().equals(dateTo)){
			calLooper.add(Calendar.DATE, 1);

			if(calLooper.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
				//calLooper.add(Calendar.DAY_OF_MONTH, 1);
			}else if(calLooper.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
				//calLooper.add(Calendar.DAY_OF_MONTH, 1);
			}else if(FncGlobal.listHolidays.contains(calLooper.getTime())){
				//calLooper.add(Calendar.DAY_OF_MONTH, 1);
			}else{
				//System.out.println(FncDate.format("MMM dd, yyyy EEEEE", calLooper.getTime()));
				numberOfDays++;
			}
		}
		
		return numberOfDays;
	}
	
	public static int getNumberOfDays2(Date dateFrom, Date dateTo) {
		Calendar calLooper = Calendar.getInstance();
		calLooper.setTime(dateFrom);

		int numberOfDays = 0;
		
		while(!calLooper.getTime().equals(dateTo)){
			calLooper.add(Calendar.DATE, 1);

			if(calLooper.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
				//calLooper.add(Calendar.DAY_OF_MONTH, 1);
			}else if(calLooper.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
				//calLooper.add(Calendar.DAY_OF_MONTH, 1);
			}else if(FncGlobal.listHolidays.contains(calLooper.getTime())){
				//calLooper.add(Calendar.DAY_OF_MONTH, 1);
			}else{
				//System.out.println(FncDate.format("MMM dd, yyyy EEEEE", calLooper.getTime()));
				numberOfDays++;
			}
		}
		
		return numberOfDays;
	}
	
	public static Date getFirstDayOfYear(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		return cal.getTime();
	}
	
	public static Date getFirstDateOfMonth(Date or_date) {
		Calendar gCal = Calendar.getInstance();
		gCal.setTime(or_date);
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, gCal.get(Calendar.YEAR));
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		return cal.getTime();
	}
	
	public static Date getFirstDateOfMonth(int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, month - 1);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		return cal.getTime();
	}
	
	public static Date getFirstDateOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		return cal.getTime();
	}
	
	public static Date getLastDateOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month + 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		return cal.getTime();
	}
	
	public static String getMonth(String format, Date date) {
		return new SimpleDateFormat(format).format(date);
	}
	
	public static String getYear(String format, Date date) {
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * Added 2014-10-07 by Alvin Gonzales
	 * 
	 * @param days
	 * @return
	 */
	public static Date add(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, days);
		
		return cal.getTime();
	}
	
	/**
	 * Added 2014-10-07 by Alvin Gonzales
	 * 
	 * @param birthdate
	 * @return
	 */
	public static int getAge(final Date birthdate) {
		return getAge(Calendar.getInstance().getTime(), birthdate);
	}
	
	/**
	 * Added 2014-10-07 by Alvin Gonzales
	 * 
	 * @param current
	 * @param birthdate
	 * @return
	 */
	public static int getAge(Date current, Date birthdate) {
		if (birthdate == null) {
			return 0;
		}
		if (current == null) {
			return getAge(birthdate);
		} else {
			final Calendar calend = new GregorianCalendar();
			calend.set(Calendar.HOUR_OF_DAY, 0);
			calend.set(Calendar.MINUTE, 0);
			calend.set(Calendar.SECOND, 0);
			calend.set(Calendar.MILLISECOND, 0);

			calend.setTimeInMillis(current.getTime() - birthdate.getTime());

			float result = 0;
			result = calend.get(Calendar.YEAR) - 1970;
			result += (float) calend.get(Calendar.MONTH) / (float) 12;
			return (int) result;
		}
	}

}
