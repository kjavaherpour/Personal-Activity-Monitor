package swing.pam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import javax.swing.*;

/**
 * Launches the password dialogue, which then launches the mainframe if a correct password is given.
 * @author Ricky Sidhu
 * @author Kamron Javaherpour
 */
public class Main {
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        PasswordFrame pd = new PasswordFrame();
        pd.setLocationByPlatform(true);
        pd.setVisible(true);
    }
}
