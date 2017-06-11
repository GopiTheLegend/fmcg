package app.legend.gopiking.fmcg.appLib;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateDialog extends Activity {

    public static int Fyear;
    public static int Fmonth;
    public static int Fday;
    public static String DFdate;
    final int DATE_PICKER_ID = 1111;

    public static int Tyear;
    public static int Tmonth;
    public static int Tday;
    public static String DTdate;

    static int inte;

    public static SimpleDateFormat dateSaveFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat dateDispFormat = new SimpleDateFormat("dd-MM-yyyy");
    public static SimpleDateFormat timeDisplayFormat = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat timeDisplayFormat1 = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat timeDisplayHrs = new SimpleDateFormat("HH");
    public static SimpleDateFormat timeDisplayMin = new SimpleDateFormat("mm");
    public static SimpleDateFormat timeDisplaySec = new SimpleDateFormat("ss");


    static Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        setContentView();
        today();
        showDialog(DATE_PICKER_ID);

    }

    public static void today(){
        Calendar From = Calendar.getInstance();
        Fyear = From.get(Calendar.YEAR);
        Fmonth = From.get(Calendar.MONTH);
        Fday = From.get(Calendar.DAY_OF_MONTH);

        Calendar to = Calendar.getInstance();
        Tyear = to.get(Calendar.YEAR);
        Tmonth = to.get(Calendar.MONTH);
        Tday = to.get(Calendar.DAY_OF_MONTH);
    }

    public static String newDate(Date newDate){
        String date = dateDispFormat.format(newDate);
        return date;
    }

    public static String newFullTime(Date newTime){
        String time = timeDisplayFormat.format(newTime);
        return time;
    }

    public static String newTime(Date newTime){
        String time = timeDisplayFormat1.format(newTime);
        return time;
    }

    @Override
    protected Dialog onCreateDialog(int id){
        switch(id){
            case DATE_PICKER_ID:
                return new DatePickerDialog(this, mDateSetListener, Fyear, Fmonth, Fday);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int month, int day) {
            Fyear = year;
            Fmonth = month;
            Fday = day;
            returnDate();
        }
    };


    /*
     * Package up the data and return it back to the calling intent
     */
    private void returnDate(){
        Intent intent = getIntent();    // calling/parent intent //

        Bundle bundle = new Bundle();
        bundle.putInt("year", Fyear);
        bundle.putInt("month", Fmonth+1);
        bundle.putInt("day", Fday);
        intent.putExtra("set_date", bundle);

        setResult(Activity.RESULT_OK, intent);

        finish();
    }

    public static String GetTimeNow() throws ParseException {
        Calendar cal = Calendar.getInstance();
        String time = timeDisplayFormat.format(cal.getTime());
        return time;
    }

    public static String GetTime() throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setLenient(true);
        String time = timeDisplayFormat1.format(cal.getTime());
        return time;
    }

    public static String GetHrs() throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setLenient(true);
        String time = timeDisplayHrs.format(cal.getTime());
        return time;
    }

    public static String GetMin() throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setLenient(true);
        String time = timeDisplayMin.format(cal.getTime());
        return time;
    }

    public static String GetSec() throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setLenient(true);
        String time = timeDisplaySec.format(cal.getTime());
        return time;
    }

    public static String DateFormatter(String date) throws ParseException {
//        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
//        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
//        String inputDateStr = date;
        Date getDate = dateSaveFormat.parse(date);
        String outDate = dateDispFormat.format(getDate);
        return outDate;
    }

/**
 * copy past below code where you calling DateDialog for result
 * startActivityForResult(new Intent(this,DateDialog.class),REQUEST_GET_FDATE);//start activity like this
 * get result using below commanded code
 */

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        try {
//
//            //DateDialog request code Example
//            //private final static int REQUEST_GET_FDATE = 3;
//            //private final static int REQUEST_GET_TDATE = 4;
//            if(requestCode == REQUEST_GET_FDATE){
//                if (resultCode == Activity.RESULT_OK) {
//                    Bundle setDate = data.getBundleExtra("set_date");
//                    int setDay = setDate.getInt("day");
//                    int setMonth = setDate.getInt("month");
//                    int setYear = setDate.getInt("year");
//                    //Assign value to any component textview or edittext or anything here
//                    Fdate.setText(String.format("%04d-%02d-%02d", setYear, setMonth, setDay));
//                }
//            }else if(requestCode == REQUEST_GET_TDATE){
//                if (resultCode == Activity.RESULT_OK) {
//                    Bundle setDate = data.getBundleExtra("set_date");
//                    int setDay = setDate.getInt("day");
//                    int setMonth = setDate.getInt("month");
//                    int setYear = setDate.getInt("year");
//                    //Assign value to any component textview or edittext or anything here
//                    Tdate.setText(String.format("%04d-%02d-%02d", setYear, setMonth, setDay));
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}

