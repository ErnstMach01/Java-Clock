import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

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

public class ClockController {
    private ClockView View;         //Creates a view object
    private ClockModel Model;       //Creates a model object
    Timer controlTimer;             //Timer object

    //Constructor starts a timer to drive the interactions between theView and TheModel
    public ClockController(ClockView theView, ClockModel theModel) {
              this.Model = theModel;
              this.View = theView;
              //Start the Timer, timer will send an action every millisecond, and implement an object of ClockListener
              controlTimer = new Timer(1, new ClockListener());
              controlTimer.start();

    }

    //Responds to Timer Events
    class ClockListener implements ActionListener {
        //Upon construction, ask theModel to updateTime and theView to updateDisplay
        ClockListener ()
        {
            Model.updateTime();
            View.updateDisplay(Model.getHour(), Model.getMinute(), Model.getSecond());
        }

        //Every Timer Event, ask theModel to updateTime and theView to updateDisplay...
        @Override
        public void actionPerformed(ActionEvent e) {
            if(View.NTPButton == false) {
                Model.updateTime();                                                                                 //Get new instance of calender
                View.updateDisplay(Model.getHour(), Model.getMinute(), Model.getSecond());                          //Get new time
                //System.out.println("Normal " + Model.getHour() +":" + Model.getMinute()+":"+ Model.getSecond());
            }
            if (View.NTPButton == true) {
                Model.setupNTP();                                                                                   //Get new instance from NTP server
                View.updateDisplay(Model.getNTPHourTime(), Model.getNTPMinuteTime(), Model.getNTPSecondTime());     //Get new time
                //System.out.println("NTP " + Model.getNTPHourTime() + ":" + Model.getNTPMinuteTime() + ":" + Model.getNTPSecondTime());
            }
        }
    }
}