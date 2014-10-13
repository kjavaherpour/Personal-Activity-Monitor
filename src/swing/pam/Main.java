package swing.pam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import javax.swing.*;

/**
 *
 * @author Ricky Sidhu
 * @author Kamron Javaherpour
 */
public class Main {
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        PasswordDialogue pd = new PasswordDialogue();
        pd.setLocationByPlatform(true);
        pd.setVisible(true);
    }
}
