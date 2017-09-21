package com.tacktile.contassist.helper.classes;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Main on 8/10/2017.
 */

/**
 * Here are all the Helper Static Methods
 */
public class HelperStaticMethods {
    private static final String TAG = HelperStaticMethods.class.getSimpleName();


    public static void setStatusBarColor(AppCompatActivity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    public static void hideSoftKeyboard(Context context, View view) {
        if (view != null && context != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } else {
            Log.e(TAG, "hideSoftKeyboard: Null @ View: " + view + " Context : " + context);
        }
    }


    public static String getCurrentDate() {
        return new SimpleDateFormat(Constants.DATE_FORMAT_DD_MMM_YYYY, Locale.ENGLISH).format(new Date());
    }

    /**
     * Format the Sting date
     *
     * @param inputDate    //date
     * @param inputFormat  //format of the input date
     * @param outputFormat output format you want
     */
    public static String FormatDate(String inputDate, String inputFormat, String outputFormat) {

        String finalDate = "Error @date Format";
        SimpleDateFormat sdf = new SimpleDateFormat(inputFormat, Locale.ENGLISH);
        try {
            Date date = sdf.parse(inputDate);
            DateFormat format2 = new SimpleDateFormat(outputFormat, Locale.ENGLISH);
            finalDate = format2.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return finalDate;
    }

    /**
     * Add Number of days to the given date
     *
     * @param date      date which is to be incremented
     * @param noOfDates number of days/dates to be added
     * @param format    output format
     */
    public static String addNoOfDays(Date date, int noOfDates, String format) {
        Log.d(TAG, "date: " + date);
        Log.d(TAG, "noOfDates : " + noOfDates);
        String strAddedDate = "";
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
            Calendar c = Calendar.getInstance();
            c.setTime(date); // Now use today date.
            c.add(Calendar.DATE, noOfDates); // Adding No. of days
            strAddedDate = sdf.format(c.getTime());
            Log.d(TAG, "strAddedDate : " + strAddedDate);
        }
        return strAddedDate;
    }

}
