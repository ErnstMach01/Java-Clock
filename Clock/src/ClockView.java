import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

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

public class ClockView extends JFrame{

    private int clockWidth = 675;                                           //Width of screen
    private int clockHeight = 725;                                          //Height of screen
    public boolean NTPButton = false;                                       //Boolean for action listener of button
    private AnalogClock clockFace;
    private int _diameter;                                                  //Variable for the diameter of the clock
    private Image dbImage;                                                  //Variable to print old image
    private Graphics dbg;                                                   //Variable to hold new image
    private static final double TWO_PI   = 2.0 * Math.PI;                   //Used for angle calculations
    private int _centerX;                                                   // x coord of middle of clock
    private int _centerY;                                                   // y coord of middle of clock
    public JButton NTP;
    ClockView() {
        //Give Window Frame a Title...
        super("Clock");

        //Basic window setup
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(clockWidth, clockHeight);
        this.setResizable(true);

        clockFace = new AnalogClock();          //Creates new clock object

        NTP = new JButton("NTP");       //Creates new button

        //Creates the action listener for the button
        NTP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NTPButton = !NTPButton;
            }
        });


        JPanel clockPanel = new JPanel();                               //Content pane for the clock face
        clockPanel.setLayout(new BorderLayout());                       //Sets the layout of the panel
        clockPanel.add(clockFace,BorderLayout.CENTER);                  //Add the clock to the panel
        clockPanel.add(NTP, BorderLayout.SOUTH);                        //Add the button to the panel
        this.setContentPane(clockPanel);
        this.setVisible(true);
    }

    //This method updates screen to display the time it is given.
    public void updateDisplay(int h, int m, int s)
    {
        clockFace.paint(clockFace.getGraphics(), h, m, s);
        if (NTPButton == false) {
            NTP.setText("NTP");
        } else {
            NTP.setText("Normal");
        }

    }

    private class AnalogClock extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;                                                              //2D graphics
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);     //Antialiasing, smooths out the circle
            g2.setStroke(new BasicStroke(2));                                                            //Set size of line
            g2.drawOval(0, 0, _diameter, _diameter);                                                     //Draw the clock shape

            int radius = _diameter / 2;
            for (int sec = 0; sec < 60; sec++) {                                // Draw 60 tick marks around the circumference.
                int tickStart;
                if (sec%5 == 0) {
                    tickStart = radius - 20;                                    // Draw a long tick mark every 5 ticks.
                } else {
                    tickStart = radius - 5;                                     // Standard tick mark.
                }
                drawRadius(g2, sec / 60.0, tickStart , radius);                 // Draw each tick
            }
        }

        //Image buffering to avoid flickering
        public void paint(Graphics g, int h, int m, int s) {
            dbImage = createImage(getWidth(), getHeight());
            dbg = dbImage.getGraphics();
            drawHands(dbg, h, m, s);
            g.drawImage(dbImage, 0, 0, this);
        }

        //Function used to calculate the angles for the arms
        public void drawRadius(Graphics2D g2, double percent, int minRadius, int maxRadius) {
            int w = getWidth();
            int h = getHeight();
            _diameter = ((w < h) ? w : h);
            _centerX = _diameter / 2;
            _centerY = _diameter / 2;

            double radians = (0.5 - percent) * TWO_PI;
            double sine   = Math.sin(radians);
            double cosine = Math.cos(radians);

            int dxmin = _centerX + (int)(minRadius * sine);
            int dymin = _centerY + (int)(minRadius * cosine);

            int dxmax = _centerX + (int)(maxRadius * sine);
            int dymax = _centerY + (int)(maxRadius * cosine);
            g2.drawLine(dxmin, dymin, dxmax, dymax);
        }

        public void drawHands(Graphics g, int h, int m, int s) {
            this.paintComponent(g);                                                                             //Draws the clock
            Graphics2D g2 = (Graphics2D) g;                                                                     //2D Graphics
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);            //Antialiasing, smooths out the lines

            //Draw the second hand
            int handMax = _diameter / 2;                          // Set the outer length of the arm
            drawRadius(g2, s/60.0, 0, handMax);                   //Draw the arm

            //Draw the minute hand
            g2.setStroke(new BasicStroke(3));                     //Set the thickness of the line, helps distinguish the minute hand from the hour and second hand
            handMax = _diameter / 3;                              //Set the outer length of the arm
            drawRadius(g2, m/60.0, 0, handMax);                   //Draw the arm

            //Draw the hour hand
            g2.setStroke(new BasicStroke(6));                     //Set the thickness of the line, helps distinguish the hour hand from the minute and second hand
            handMax = _diameter / 4;                              //Set the outer length of the arm

            int hourm = h*60;                                     //Converts the current hour into minutes
            int hminutes = 0;                                     //Variable used to hold the number of minutes so they only change every 12 minutes

                    if(m > 0 && m < 12) {                         //Check if the minute is between 0 and 12
                        hminutes=0;                               //Set the holding time to 0
                    } else if (m >= 12 && m <24) {                //Check if the minutes is between 12 and 24
                        hminutes = 12;                            //Set the holding time to 12
                    } else if (m >= 24 && m < 36) {               //Check if the minutes is between 24 and 36
                        hminutes = 24;                            //Set the holding time to 24
                    } else if(m >= 36 && m < 48) {                //Check if the minutes is between 36 and 48
                        hminutes = 36;                            //Set the holding time to 36
                    } else if(m >= 48) {                          //Checks if the minutes is between 48 and 0
                        hminutes = 48;                            //Sets the holding time to 48
                    }

            int tminutes = hourm + hminutes;                      //Variable that holds the total time in minutes

                drawRadius(g2, tminutes/ 720.0, 0, handMax);      //Draw the arm

        }
    }
}