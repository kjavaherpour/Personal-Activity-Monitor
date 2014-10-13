/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package swing.pam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author Ricky Sidhu
 * @author Kamron Javaherpour
 */
public class addActivityFrame extends javax.swing.JFrame {

    /**
     * Creates new form addActivityFrame
     */
    public addActivityFrame() {
        initComponents();
    }
    
    public void setParent(MainFrame mf){
        parentFrame = mf;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        activityNameField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dateField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        timeOfDay = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        hoursOfActivity = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        activityNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activityNameFieldActionPerformed(evt);
            }
        });

        jLabel1.setText("The name of your activity");

        jLabel2.setText("Date in MMM dd yyyy format (between Sep 29 2014 and Oct 19 2014");

        dateField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateFieldActionPerformed(evt);
            }
        });

        jLabel3.setText("Time of day that you did your activity (e.g., 5:00AM)");

        timeOfDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeOfDayActionPerformed(evt);
            }
        });
        timeOfDay.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {

            }
            public void removeUpdate(DocumentEvent e) {
            }
            public void insertUpdate(DocumentEvent e) {
                timeEntered = true;
            }
        });

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        clearButton.setText("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        jLabel4.setText("Length of time, in hours, that you did your activity (e.g., .5, 1, 2)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(activityNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                    .addComponent(dateField)
                    .addComponent(timeOfDay, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(saveButton)
                        .addGap(31, 31, 31)
                        .addComponent(clearButton))
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(hoursOfActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {activityNameField, dateField, hoursOfActivity, timeOfDay});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(activityNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timeOfDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(hoursOfActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(clearButton))
                .addGap(33, 33, 33))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {activityNameField, dateField, hoursOfActivity, timeOfDay});

        dateField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
            }
            public void removeUpdate(DocumentEvent e) {
            }
            public void insertUpdate(DocumentEvent e) {
                nameEntered = true;
            }
        });
        this.getRootPane().setDefaultButton(saveButton);
        hoursOfActivity.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
            }
            public void removeUpdate(DocumentEvent e) {
            }
            public void insertUpdate(DocumentEvent e) {
                hoursEntered = true;
            }
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void activityNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activityNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_activityNameFieldActionPerformed

    private void dateFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateFieldActionPerformed
        nameEntered = true;
    }//GEN-LAST:event_dateFieldActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        boolean dateFormat = false, dateRange = false;
        newActivity = new Activity();
        Date activityDate = null;
        
        //Check if date was correctly entered.
        try{
           activityDate = new SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH).parse(dateField.getText());
        }
        catch(ParseException e){
           JOptionPane.showMessageDialog(this, "Incorrect date format, please re-enter your date.");
        }
        finally{
            if (activityDate != null){
                newActivity.setDate(activityDate);
                Calendar cal = Calendar.getInstance();
                cal.setTime(activityDate);
                //Day of week character
                newActivity.setDayOfWeek(new SimpleDateFormat("EEE", Locale.ENGLISH).format(activityDate).charAt(0));
                        //Now check if the date was in the correct range.
                Calendar start =  Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                start.set(2014, 8, 28);
                end.set(2014, 9, 20);
                if(activityDate.before(end.getTime()) && activityDate.after(start.getTime())){
                    dateRange = true;
                    //Set week number for weekview's purposes
                    //Start of first is already defined
                    Calendar endOfFirstWeek = Calendar.getInstance();
                    Calendar beginningOfSecondWeek = Calendar.getInstance();
                    Calendar endOfSecondWeek = Calendar.getInstance();
                    Calendar beginningOfThirdWeek = Calendar.getInstance();
                    //End of third is already defined

                    endOfFirstWeek.set(2014, 9, 5);        
                    beginningOfSecondWeek.set(2014, 9, 5); 
                    endOfSecondWeek.set(2014, 9, 12);
                    beginningOfThirdWeek.set(2014, 9, 12);
                    //im so sorry this code is horrifying
                    if(activityDate.after(start.getTime()) && activityDate.before(endOfFirstWeek.getTime())){
                        System.out.println("Week 1");
                        newActivity.setWeekNumber(1);
                    } //oh god
                    else if(activityDate.after(beginningOfSecondWeek.getTime()) && activityDate.before(endOfSecondWeek.getTime())){
                        System.out.println("Week 2");
                        newActivity.setWeekNumber(2);            
                    }
                    else if(activityDate.after(beginningOfThirdWeek.getTime()) && activityDate.before(end.getTime())){
                        System.out.println("Week 3");
                        newActivity.setWeekNumber(3);
                    }
                }
                        dateFormat = true;
            }
        }

            //Now check if there's an activity name, time entered, and length of time entered.
            if(dateFormat && dateRange && nameEntered && timeEntered && hoursEntered){
                newActivity.setName(activityNameField.getText());
                newActivity.setTime(timeOfDay.getText());
                newActivity.setLengthOfTime(Double.valueOf(hoursOfActivity.getText())); 
                parentFrame.addActivityFromDialogue(newActivity);
                this.dispose();
                //send up the activity
        }
        else{
            JOptionPane.showMessageDialog(this, "A field was either left blank or incorrectly entered.");
        }
    }//GEN-LAST:event_saveButtonActionPerformed
        public Activity getActivity(){
            return newActivity;
        }
    private void timeOfDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeOfDayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_timeOfDayActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        activityNameField.setText("");
        timeOfDay.setText("");
        hoursOfActivity.setText("");
        dateField.setText("");
        
    }//GEN-LAST:event_clearButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(addActivityFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addActivityFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addActivityFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addActivityFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addActivityFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField activityNameField;
    private javax.swing.JButton clearButton;
    private javax.swing.JTextField dateField;
    private javax.swing.JTextField hoursOfActivity;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField timeOfDay;
    // End of variables declaration//GEN-END:variables
    private Activity newActivity;
    private boolean nameEntered = false;
    private boolean timeEntered = false;
    private boolean hoursEntered = false;
    public MainFrame parentFrame;
}
