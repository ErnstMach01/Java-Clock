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

public class Clock {
public static void main(String[] args) {
          ClockController Controller = new ClockController(new ClockView(), new ClockModel());
      }
}

