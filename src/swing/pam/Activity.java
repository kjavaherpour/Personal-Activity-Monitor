package swing.pam;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author Ricky Sidhu
 * @author Kamron Javaherpour
 */
public class Activity implements Comparable<Activity>{
    private String name;
    private Date date;
    private String time;
    private char dayOfWeek;
    private double lengthOfTime;
    private int weekNumber;
    
    public String toString(){
        return getName() + "," + getDateString() + "," + getTime() + "," + getDayOfWeek() + "," + getLengthOfTime() + "," + getWeekNumber();
    }
    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Date getDate(){
        return date;
    }
    
    /**
     * This is a string for simplicity purposes. getDate() returns an actual java Date.
     * @return 
     */
    public String getDateString() {
        return new SimpleDateFormat("MMM dd yyyy").format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public int compareTo(Activity o){
        return getDate().compareTo(o.getDate());
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public char getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(char dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public double getLengthOfTime() {
        return lengthOfTime;
    }

    public void setLengthOfTime(double lengthOfTime) {
        this.lengthOfTime = lengthOfTime;
    }
}
