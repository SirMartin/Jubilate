package app.jubilate.piribisoft.com.jubilate;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DataHelper {

    public static final String PREFS = "key_preferences";
    public static final String WORKED_DAYS = "key_worked_days";
    private final float HOURS_LEFT = 234.280f;

    Context mContext;

    public DataHelper(Context context) {
        this.mContext = context;
    }

    public String getFinalDay() {
        float hoursLeft = getHoursLeft();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        while (hoursLeft > 0) {
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            if (!isHoliday(calendar) && day != 1) {
                float hours = getWorkedHours(day);
                hoursLeft -= hours;
            }

            calendar.add(Calendar.DATE, 1);
        }

        calendar.add(Calendar.DATE, -1);
        return formatCalendar(calendar);
    }

    public ArrayList<String> getWorkedDays() {
        SharedPreferences settings = this.mContext.getSharedPreferences(PREFS, 0);
        String workedDays = settings.getString(WORKED_DAYS, "");
        ArrayList<String> result = new ArrayList<String>();

        if (workedDays != null && workedDays != "") {
            result = new ArrayList<String>(Arrays.asList(workedDays.split(";")));
        }

        return result;
    }

    public int calculateDays() {
        float hoursLeft = getHoursLeft();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        int days = 0;
        while (hoursLeft > 0) {
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            if (!isHoliday(calendar) && day != 1) {
                float hours = getWorkedHours(day);
                hoursLeft -= hours;
                days++;
            }

            calendar.add(Calendar.DATE, 1);
        }

        return days;
    }

    private float getHoursLeft() {
        float hoursLeft = HOURS_LEFT;

        List<String> workedDays = getWorkedDays();
        for (String day : workedDays) {
            Calendar c = parseCalendar(day);
            hoursLeft -= getWorkedHours(c.get(Calendar.DAY_OF_WEEK));
        }

        return hoursLeft;
    }

    private List<String> GetHolidays() {
        List<String> holidayList = new ArrayList<String>();
        holidayList.add("24/11/2018");
        holidayList.add("06/12/2018");
        holidayList.add("07/12/2018");
        holidayList.add("08/12/2018");
        holidayList.add("22/12/2018");
        holidayList.add("24/12/2018");
        holidayList.add("25/12/2018");
        holidayList.add("26/12/2018");
        holidayList.add("27/12/2018");
        holidayList.add("28/12/2018");
        holidayList.add("29/12/2018");
        holidayList.add("31/12/2018");
        holidayList.add("01/01/2019");
        holidayList.add("05/01/2019");
        holidayList.add("07/01/2019");

        return holidayList;
    }

    private boolean isHoliday(Calendar c) {
        String theDate = formatCalendar(c);
        return GetHolidays().contains(theDate);
    }

    public String formatCalendar(Calendar c) {
        String y = c.get(Calendar.YEAR) + "";

        String m = "";
        int month = c.get(Calendar.MONTH) + 1;
        if (month < 10) {
            m += "0";
        }
        m += month;

        String d = "";
        int day = c.get(Calendar.DATE);
        if (day < 10) {
            d += "0";
        }
        d += day;

        return d + "/" + m + "/" + y;
    }

    private Calendar parseCalendar(String date) {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            cal.setTime(sdf.parse(date));
            return cal;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private float getWorkedHours(int dayOfWeek) {
        if (dayOfWeek == 7) {
            return 9.63f;
        } else if (dayOfWeek == 1) {
            return 0;
        } else {
            return 7.7f;
        }
    }
}
