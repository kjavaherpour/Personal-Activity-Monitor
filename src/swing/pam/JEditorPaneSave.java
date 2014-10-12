package swing.pam;

import java.io.*;
import java.nio.*;
import java.awt.event.*;
import javax.swing.*;
public class JEditorPaneSave implements ActionListener {
    
   JFrame myFrame = null;
   JEditorPane myPane = null;
   
   public static void main(String[] a) {
      (new JEditorPaneSave()).run();
   }
   
   public void run() {
       
      myFrame = new JFrame("Diary");
      myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      myFrame.setSize(400,400);
      myPane = new JEditorPane();
      myPane.setContentType("text/plain");
      myFrame.setContentPane(myPane);
      try{
      FileReader in = new FileReader(new File("data/diary.txt"));
            char[] buffer = new char[1024];
            int n = in.read(buffer);
            String text = new String(buffer, 0, n);
            myPane.setText(text);
            in.close();
      }catch(Exception e){
          e.printStackTrace();
      }
      
      //myPane.setText("Set text to what was saved");
      
      

      JMenuBar myBar = new JMenuBar();
      JMenu myMenu = getFileMenu();
      myBar.add(myMenu); 
      myFrame.setJMenuBar(myBar);
      myFrame.setVisible(true);
   }
   private JMenu getFileMenu() {
      JMenu myMenu = new JMenu("File");
      JMenuItem myItem = new JMenuItem("Open");
      myItem.addActionListener(this);
      myMenu.add(myItem);
      
      

      myItem = new JMenuItem("Save");
      myItem.addActionListener(this);
      myMenu.add(myItem);
      return myMenu;
   }
   public void actionPerformed(ActionEvent e) {
      String cmd = ((AbstractButton) e.getSource()).getText();
      try {
         if (cmd.equals("Open")) {
            FileReader in = new FileReader(new File("data/diary.txt"));
            char[] buffer = new char[1024];
            int n = in.read(buffer);
            String text = new String(buffer, 0, n);
            myPane.setText(text);
            in.close();
         } else if (cmd.equals("Save")) {
            FileWriter out = new FileWriter(new File("data/diary.txt"));
            out.write(myPane.getText());
            out.close();
         }
      } catch (Exception f) {
      	 f.printStackTrace();
      }
   }
}