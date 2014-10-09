/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package swing.testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ricky
 */
public final class mainFrame extends javax.swing.JFrame {

    /**
     * Creates new form mainFrame
     */
    public mainFrame(){
        arrayListLoader();
        logViewLoader();
        initComponents();
        viewState = 0;
        weekNumber = 1;
        LeftButton.setEnabled(false); //Because we load the logview before initcomponents,
        //we need to disable the buttons somehow, and not before they've been instanced yet
        RightButton.setEnabled(false);
    }
    private void logViewLoader(){
        setTitle("Log View");
        if(LeftButton != null){
            LeftButton.setEnabled(false);
            RightButton.setEnabled(false);
        }
        String[] col = {"Activity", "Date", "Time", "Day Of Week", "Length Of Time"};
        tableModel = new DefaultTableModel(col, 0);
        for(Activity activity:activities){
            String[] tableRow = new String[5];
            tableRow[0] = activity.getName();
            tableRow[1] = activity.getDateString();
            tableRow[2] = activity.getTime();
            char dayOfWeek = activity.getDayOfWeek();
            switch(dayOfWeek){
                case 'M': tableRow[3]="Monday";
                break;
                case 'T': tableRow[3]="Tuesday";
                break;
                case 'W': tableRow[3]="Wednesday";
                break;
                case 'R': tableRow[3]="Thursday";
                break;
                case 'F': tableRow[3]="Friday";
                break;
                case 'S': tableRow[3]="Saturday";
                break;
                case 'U': tableRow[3]="Sunday";
                break;             
            }
            tableRow[4] = activity.getLengthOfTime();
            tableModel.addRow(tableRow);
        }
        tableModel.fireTableDataChanged();
        tableModel.fireTableStructureChanged();
        if(jTable != null){ //DayTable is null when we first start the application, and this method is called before and after initcomponents().
            jTable.setModel(tableModel);
        }
        repaint();
    }
    
    public void dayViewLoader(){       
        LeftButton.setEnabled(true);
        RightButton.setEnabled(true);
        setTitle(new SimpleDateFormat("EEE, MMM dd yyyy").format(currentDate));
        String[] col = {"Activity", "Time", "Length Of Time"};
        tableModel = new DefaultTableModel(col, 0);
        for(Activity activity: activities){
            if(currentDate.compareTo(activity.getDate()) == 0){
                String[] rowItem = new String[3];
                rowItem[0] = activity.getName();
                rowItem[1] = activity.getTime();
                rowItem[2] = activity.getLengthOfTime();
                tableModel.addRow(rowItem);
            }
        }
        tableModel.fireTableDataChanged();
        tableModel.fireTableStructureChanged();
        if(jTable != null){ //DayTable is null when we first start the application, and this method is called before and after initcomponents().
            jTable.setModel(tableModel);
        }
        repaint();
    }
    
    public void weekViewLoader(){
        LeftButton.setEnabled(true);
        RightButton.setEnabled(true);
        if(weekNumber == 1) setTitle("Week of Sep 29 - Oct 5");
        else if(weekNumber == 2) setTitle("Week of Oct 6 - Oct 12");
        else if(weekNumber == 3) setTitle("Week of Oct 13 - Oct 19");
        String[] col = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        tableModel = new DefaultTableModel(col, 0);
        
        //Construct the lists for holding column items
        ArrayList<Activity> monday = new ArrayList<>();
        ArrayList<Activity> tuesday = new ArrayList<>();
        ArrayList<Activity> wednesday = new ArrayList<>();
        ArrayList<Activity> thursday = new ArrayList<>();
        ArrayList<Activity> friday = new ArrayList<>();
        ArrayList<Activity> saturday = new ArrayList<>();
        ArrayList<Activity> sunday = new ArrayList<>();
        ArrayList[] week = {monday, tuesday, wednesday, thursday, friday, saturday, sunday};
        
        //Populate the columns
        for(Activity activity: activities){
            if(activity.getWeekNumber() == weekNumber && activity.getDayOfWeek() == 'M')
                monday.add(activity);
            if(activity.getWeekNumber() == weekNumber && activity.getDayOfWeek() == 'T')
                tuesday.add(activity);
            if(activity.getWeekNumber() == weekNumber && activity.getDayOfWeek() == 'W')
                wednesday.add(activity);
            if(activity.getWeekNumber() == weekNumber && activity.getDayOfWeek() == 'R')
                thursday.add(activity);
            if(activity.getWeekNumber() == weekNumber && activity.getDayOfWeek() == 'F')
                friday.add(activity);
            if(activity.getWeekNumber() == weekNumber && activity.getDayOfWeek() == 'S')
                saturday.add(activity);
            if(activity.getWeekNumber() == weekNumber && activity.getDayOfWeek() == 'U')
                sunday.add(activity);
        }
        
        //Get the largest one. This is how many rows we will create.
        int longestDay = 0;
        for(ArrayList weekday: week){
            if (longestDay < weekday.size())
                longestDay = weekday.size();
        }
        for(int i = 0; i < longestDay; i++){
            String[] rowData = new String[7];
            
            if(i <= monday.size() - 1) //If there is something to add in this row of this day of the week.
                rowData[0] = monday.get(i).getName();
            else rowData[0] = "";
            
            if(i <= tuesday.size() - 1)
                rowData[1] = tuesday.get(i).getName();
            else rowData[1] = "";
            
            if(i <= wednesday.size() - 1)
                rowData[2] = wednesday.get(i).getName();
            else rowData[2] = "";
            
            if(i <= thursday.size() - 1)
                rowData[3] = thursday.get(i).getName();
            else rowData[3] = "";
            
            if(i <= friday.size() - 1)
                rowData[4] = friday.get(i).getName();
            else rowData[4] = "";
            
            if(i <= saturday.size() - 1)
                rowData[5] = saturday.get(i).getName();
            else rowData[5] = "";
            
            if(i <= sunday.size() - 1)
                rowData[6] = sunday.get(i).getName();
            else rowData[6] = "";
            
            tableModel.addRow(rowData);
        }
        tableModel.fireTableDataChanged();
        tableModel.fireTableStructureChanged();
        if(jTable != null) //on first run, this is run before DayTable is instantiated
        jTable.setModel(tableModel);
        repaint();
        
    }
    
    public void arrayListLoader(){
        
        activities = new ArrayList<>();
        BufferedReader bufferedReader = null;
        File file = new File("data.txt");
        
        try{
            
            bufferedReader = new BufferedReader(new FileReader(file));
            String line;                   
            while( (line = bufferedReader.readLine())!= null ){ //Line by line, each is a row.
                String[] activity = line.split(",");
                Activity act = new Activity();
                act.setName(activity[0]);
                act.setDate(new SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH).parse(activity[1]));
                act.setTime(activity[2]);
                act.setDayOfWeek(activity[3].charAt(0));
                act.setLengthOfTime(activity[4]);
                act.setWeekNumber(Integer.parseInt(activity[5]));
                activities.add(act);
                //tableModel.addRow(activity);
            }
            bufferedReader.close();
        }
        catch(IOException | ParseException | NumberFormatException e){
        }
        Collections.sort(activities);
        currentDate = activities.get(0).getDate();
        firstDate = activities.get(0).getDate();
        lastDate = activities.get(activities.size()-1).getDate();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        weekViewButton = new javax.swing.JButton();
        logButton = new javax.swing.JButton();
        dayViewButton = new javax.swing.JButton();
        diaryButton = new javax.swing.JButton();
        LeftButton = new javax.swing.JButton();
        RightButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Log View");

        jTable.setModel(tableModel);
        jTable.setToolTipText("");
        jTable.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(jTable);
        jTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        weekViewButton.setText("Week View");
        weekViewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weekViewButtonActionPerformed(evt);
            }
        });

        logButton.setText("Log");
        logButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logButtonActionPerformed(evt);
            }
        });

        dayViewButton.setText("Day View");
        dayViewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dayViewButtonActionPerformed(evt);
            }
        });

        diaryButton.setText("Diary");
        diaryButton.setToolTipText("");
        diaryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diaryButtonActionPerformed(evt);
            }
        });

        LeftButton.setText("Previous");
        LeftButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LeftButtonActionPerformed(evt);
            }
        });

        RightButton.setText("Next");
        RightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RightButtonActionPerformed(evt);
            }
        });

        addButton.setText("Add");

        removeButton.setText("Remove");

        saveButton.setText("Save");

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(LeftButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RightButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(weekViewButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dayViewButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(logButton, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                            .addComponent(diaryButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addButton, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addComponent(removeButton, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(saveButton))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {LeftButton, RightButton, addButton, dayViewButton, diaryButton, logButton, removeButton, saveButton, weekViewButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LeftButton)
                            .addComponent(RightButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(dayViewButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(weekViewButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(diaryButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {dayViewButton, weekViewButton});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {LeftButton, RightButton, addButton, diaryButton, logButton, removeButton, saveButton});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logButtonActionPerformed
        viewState = 0;
        logViewLoader();
    }//GEN-LAST:event_logButtonActionPerformed

    private void dayViewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dayViewButtonActionPerformed
        viewState = 1;
        dayViewLoader();
    }//GEN-LAST:event_dayViewButtonActionPerformed

    private void diaryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diaryButtonActionPerformed
        if (evt.getSource() == diaryButton){
            JEditorPaneSave jdiary = new JEditorPaneSave();
            jdiary.run();
        }
    }//GEN-LAST:event_diaryButtonActionPerformed

    private void weekViewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weekViewButtonActionPerformed
        viewState = 2;
        weekViewLoader();
    }//GEN-LAST:event_weekViewButtonActionPerformed

    private void LeftButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LeftButtonActionPerformed
        if(viewState == 1){ //Day View
            if(currentDate.compareTo(firstDate) == 0){ //If we are on the first date, wrap-around to the last date
                LeftButton.setEnabled(false);
            } else {
                Calendar cal = Calendar.getInstance();
                cal.setTime(currentDate);
                cal.add(Calendar.DATE, -1);
                currentDate = cal.getTime();
            }
            dayViewLoader();
        }
        if(viewState == 2){ //Week View
            if(weekNumber == 1){
            weekNumber = 3;
            weekViewLoader();
            }else{
            weekNumber--;
            weekViewLoader();
            }
        }
    }//GEN-LAST:event_LeftButtonActionPerformed

    private void RightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RightButtonActionPerformed
        if(viewState == 1){ //Day View
            if(currentDate.compareTo(lastDate) == 0){ //If we are on the last date, wrap-around to the first date
                RightButton.setEnabled(false);
            } else {
                Calendar cal = Calendar.getInstance();
                cal.setTime(currentDate);
                cal.add(Calendar.DATE, 1);
                currentDate = cal.getTime();
            }
            dayViewLoader();
        }
        if(viewState == 2){ //Week View
            if(weekNumber == 3){
            weekNumber = 1;
            weekViewLoader();
            }else{
            weekNumber++;
            weekViewLoader();
            }
        }
    }//GEN-LAST:event_RightButtonActionPerformed
    

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]){
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run(){ 
        mainFrame mf = new mainFrame();
        new mainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton LeftButton;
    private javax.swing.JButton RightButton;
    private javax.swing.JButton addButton;
    private javax.swing.JButton dayViewButton;
    private javax.swing.JButton diaryButton;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable;
    private javax.swing.JButton logButton;
    private javax.swing.JButton removeButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton weekViewButton;
    // End of variables declaration//GEN-END:variables
    private DefaultTableModel tableModel;
    private ArrayList<Activity> activities;
    private Date currentDate;
    private Date firstDate;
    private Date lastDate;
    private int weekNumber;
    private int viewState;//0 for logview, 1 for dayview, 2 for weekview
}
