/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package swing.testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import javax.swing.*;

/**
 *
 * @author Ricky
 */
public class SwingTesting {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        mainFrame mf = new mainFrame();
        mf.setLocationByPlatform(true);
        mf.setVisible(true);
    }
    
}
