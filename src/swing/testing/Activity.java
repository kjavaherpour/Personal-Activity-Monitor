package swing.testing;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Activity implements Comparable<Activity>{
    private String name;
    private Date date;
    private String time;
    private char dayOfWeek;
    private String lengthOfTime;
    private int weekNumber;
    
    public String toString(){
        return "[" + getName() + " " + getDateString() + " " + getTime() + " " + getDayOfWeek() + " " + getLengthOfTime() + " " + getWeekNumber() + "]";
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

    public String getLengthOfTime() {
        return lengthOfTime;
    }

    public void setLengthOfTime(String lengthOfTime) {
        this.lengthOfTime = lengthOfTime;
    }
}
