import java.io.IOException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Assignment Name: Java Clock
 * Author: Ernst Mach
 * Date: April 10, 2018
 * Description: A simple analog clock created using the MVC model that also has a button
 * which will make the clock sync to an NTP server. The hour hand will move 5 times in a
 * hour, moving every 12 minutes. The view object represents the graphical clock view, it
 * does not calculate the time It only contains the graphical interface and displays the
 * time that it is given. The model object contains the data, it is unaware of the graphical
 * interface. It simply stores the time, updates the time, and will return the time. The
 * controller object coordinates the actions between theView and theModel.
 */

public class ClockModel {
    private URLConnection conn = null;
    private Calendar today;                                              //Use a Calender to store the data and time

    public ClockModel()
    {
        this.today= Calendar.getInstance();
            }

    public void updateTime()
    {
              this.today= Calendar.getInstance();
            }     //Update the calendar

    public int getHour()
    {
              return today.get(Calendar.HOUR);
            }            //Get the hour time from the calender

    public int getMinute()
    {
              return today.get(Calendar.MINUTE);
            }        //Get the minute time from the calender

    public int getSecond()
    {
              return today.get(Calendar.SECOND);
            }        //Get the second time from the calender

    public int getNTPSecondTime() {                                      //Gets the second time from NTP
        long dateTime = conn.getHeaderFieldDate("Date", 0);
        Date receiveTime = null;
        int seconds;
        if (dateTime > 0) {
            receiveTime = new Date(dateTime);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("ss");        //Set the format to just seconds
        String formattedDate = formatter.format(receiveTime);           //Create a new string that uses the created format and store NTP header
        seconds = Integer.parseInt(formattedDate);                      //Parse the string for the number
        return seconds;
    }

    public int getNTPMinuteTime() {                                     //Gets the minute time from NTP
        long dateTime = conn.getHeaderFieldDate("Date", 0);
        Date receiveTime = null;
        int minutes;
        if (dateTime > 0) {
            receiveTime = new Date(dateTime);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("mm");        //Set the format to just minutes
        String formattedDate = formatter.format(receiveTime);           //Create a new string that uses the created format and store NTP header
        minutes = Integer.parseInt(formattedDate);                      //Parse the string for the number
        return minutes;
    }

    public int getNTPHourTime() {                                       //Gets the hour time from NTP
        long dateTime = conn.getHeaderFieldDate("Date", 0);
        Date receiveTime = null;
        int hours;
        if (dateTime > 0) {
            receiveTime = new Date(dateTime);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("HH");        //Set the format to just hours
        String formattedDate = formatter.format(receiveTime);           //Create a new string that uses the created format and store NTP header
        hours = Integer.parseInt(formattedDate);                        //Parse the string for the number
        return hours;
    }

    public void setupNTP() {                                            //Sets up access to NTP website
        URL url = null;
        try {
            url = new URL("http://www.ntp.org/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            conn = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (conn instanceof HttpURLConnection) {
            HttpURLConnection httpConn = (HttpURLConnection)conn;
            try {
                httpConn.setRequestMethod("HEAD");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
        }
    }
}