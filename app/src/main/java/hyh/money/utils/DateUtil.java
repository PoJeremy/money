package hyh.money.utils;

import android.annotation.SuppressLint;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * @ProjectName：YuePin4Employee   
 * @ClassName：DateUtil   
 * @Description：日期公共类
 * @Author：LiuZR
 * @Date：2014-12-29
 * @version 1.0   
 *
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {
	
	public static String calendar2String(Calendar date) {
		return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
				.format(date.getTime());
	}

	public static String date2String(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
				.format(date);
	}

	public static Calendar string2Calendar(String dateStr) {
		String pattern = "yyyy-MM-dd";
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	
	public static String[] dateArea(ArrayList<String> list) {
		Calendar maxC = null;
		Calendar minC = null;
		maxC = minC = string2Calendar(list.get(0));
		for(String s : list) {
			Calendar  c = string2Calendar(s);
			if(maxC.before(c))
				maxC = c;
			if(minC.after(c))
				minC = c;
		}
		String[] ss = new String[]{calendar2String(minC),calendar2String(maxC)};
		return ss;
	}
}
