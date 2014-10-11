package swing.testing;

import java.awt.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ricky Sidhu
 * @author Kamron Javaherpour
 */
public final class mainFrame extends javax.swing.JFrame {

    /**
     * Creates new form mainFrame
     */
    public mainFrame(){
        
        
        //None of this happens until password is typed correctly
        arrayListLoader();
        logViewLoader();
        initComponents();
        viewState = 0;
        weekNumber = 1;
        LeftButton.setEnabled(false); //We load the logview before initcomponents,
        RightButton.setEnabled(false); //we need to disable the buttons somehow, and not before they've been instanced.
    }
    
    /**
     * Loads the log view, a list of all activities with all relevant stored information.
     */
    private void logViewLoader(){
        setTitle("Log View");
        if(LeftButton != null){ //If it's not the first time we're seeing the view
            LeftButton.setEnabled(false);
            RightButton.setEnabled(false);
            removeButton.setEnabled(true);
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
    
    /**
    * Loads data from the Activities ArrayList into a dayview table model for the JTable.
    */
    public void dayViewLoader(){ 
        if(!activities.isEmpty()){
            LeftButton.setEnabled(true);
            RightButton.setEnabled(true);
        }
        if(activities.isEmpty()){
            LeftButton.setEnabled(false);
            RightButton.setEnabled(false);
        }
        removeButton.setEnabled(false);
        if(currentDate != null)
        setTitle(new SimpleDateFormat("EEE, MMM dd yyyy").format(currentDate));
        else if(!activities.isEmpty()){
            currentDate = activities.get(0).getDate();
        }
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
    
    /**
     * Loads data from the Activities ArrayList into a dayview table model for the JTable.
     * 
     * Kind of hacky.
    */
    public void weekViewLoader(){
        if(!activities.isEmpty()){
            LeftButton.setEnabled(true);
            RightButton.setEnabled(true);
        }
        if(activities.isEmpty()){
            LeftButton.setEnabled(false);
            RightButton.setEnabled(false);
        }
        removeButton.setEnabled(false);
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
    
    /**
     * Password functionality for 1 user
     * @param pwd
     * @return true if the password was entered correctly.
     */
    private boolean isPasswordCorrect(char[] pwd){
        
        boolean isCorrect = false;
        char[] correctPass = {'b', 'a', 'r', 'n', 'e', 's'};
        
        if(pwd.length != correctPass.length){
            isCorrect = false;
        }else{
            isCorrect = Arrays.equals(pwd, correctPass);
        }
        Arrays.fill(correctPass, 'x');
        return isCorrect;
    }
    /**
     * Populates the ArrayList, our central data structure, with data from our text file.
     */
    public void arrayListLoader(){
        
        activities = new ArrayList<>();
        BufferedReader bufferedReader = null;
        File file = new File("data.txt");
        
        try{
            
            bufferedReader = new BufferedReader(new FileReader(file));
            String line; 
            if(bufferedReader.readLine() == null){
                    JOptionPane.showMessageDialog(this, "Please add an activity");
            }
            else bufferedReader = new BufferedReader(new FileReader(file)); //Re-open the file to start from the beginning
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
        if(!activities.isEmpty()){
            Collections.sort(activities);
            currentDate = activities.get(0).getDate();
            firstDate = activities.get(0).getDate();
            lastDate = activities.get(activities.size()-1).getDate();
        }
    }
    
    public void saveFile(){
        try{
        FileWriter out = new FileWriter("data.txt");
        for(Activity activity : activities){
            String text = activity.toString() + "\r\n";
            out.write(text);
        }
        out.close();
        }catch(IOException e){
        }
        JOptionPane.showMessageDialog(this, "Save successful");
    }
    
    /**
     * checks if a given date is between sep 29 and oct 19
     */
    private boolean dateInCorrectRange(Date d){
        Calendar start =  Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.set(2014, 8, 28);
        end.set(2014, 9, 20);
        if(d.before(end.getTime()) && d.after(start.getTime()))
            return true;
        return false;
    }
    /**
     * Triggered by the tablemodellistener, this method will check input in logview and 
     * make changes to our data structure accordingly.
     * @param row
     * @param column 
     */
    private void editEvent(int row, int column){
        if(viewState==0){
            Calendar endOfFirstWeek = Calendar.getInstance();
            Calendar beginningOfSecondWeek = Calendar.getInstance();
            Calendar endOfSecondWeek = Calendar.getInstance();
            Calendar beginningOfThirdWeek = Calendar.getInstance();
            Calendar start =  Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            start.set(2014, 8, 28);
            end.set(2014, 9, 20);
            endOfFirstWeek.set(2014, 9, 5);        
            beginningOfSecondWeek.set(2014, 9, 5); 
            endOfSecondWeek.set(2014, 9, 12);
            beginningOfThirdWeek.set(2014, 9, 12);
            try{
                //Activity name changed
                if(column == 0) activities.get(row).setName((String) tableModel.getValueAt(row, column));
                else if(column == 1){ //Changes to date require some special care.
                    Date changedDate = new SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH)
                            .parse((String) tableModel.getValueAt(row, column));
                    if(dateInCorrectRange(changedDate)){ //Make sure it's within our range.
                        activities.get(row).setDate(changedDate);
                        activities.get(row).setDayOfWeek(new SimpleDateFormat("EEE").format(changedDate).charAt(0));

                        //Setting the week number in the activity class: this is kind of hairy.
                        if(changedDate.after(start.getTime()) && changedDate.before(endOfFirstWeek.getTime()))
                            activities.get(row).setWeekNumber(1);
                        else if(changedDate.after(beginningOfSecondWeek.getTime()) && changedDate.before(endOfSecondWeek.getTime()))
                            activities.get(row).setWeekNumber(2);            
                        else if(changedDate.after(beginningOfThirdWeek.getTime()) && changedDate.before(end.getTime()))
                            activities.get(row).setWeekNumber(3);
                    }
                    else{ //Otherwise, just reload.
                        JOptionPane.showMessageDialog(this, "Incorrect date entry: must be between Sep 29 and Oct 19");
                        logViewLoader();
                    }
                }
                else if(column == 2){
                    activities.get(row).setTime((String) tableModel.getValueAt(row, column));
                }
                else if(column == 3){
                    JOptionPane.showMessageDialog(this, "If you would like to change the day of the week, change the date.");
                    logViewLoader();
                }  
                else if(column == 4){
                    activities.get(row).setLengthOfTime((String) tableModel.getValueAt(row, column));
                }
            }
            catch(ParseException e){
                JOptionPane.showMessageDialog(this, "Date incorrectly formatted: must be MMM dd yyyy");
            }
            finally{
                logViewLoader();
                jTable.getModel().addTableModelListener(new TableModelListener() {
                public void tableChanged(TableModelEvent e) {
                    editEvent(e.getFirstRow(), e.getColumn());
                }
            });
            }
        }
        else if (viewState == 1 || viewState == 2){
            JOptionPane.showMessageDialog(this, "Please select log view to make changes.");
            jTable.getModel().addTableModelListener(new TableModelListener() {
                public void tableChanged(TableModelEvent e) {
                    editEvent(e.getFirstRow(), e.getColumn());
                }
            });
            reloadView();
        }
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
        myMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        editMenu = new javax.swing.JMenu();
        aboutMenu = new javax.swing.JMenu();
        usageMenuItem = new javax.swing.JMenuItem();
        authorMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Log View");

        jTable.setModel(tableModel);
        jTable.setToolTipText("");
        jTable.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(jTable);
        jTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        //Registering the row clicks
        jTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected row
                rowSelected = jTable.getSelectedRow();
            }
        });
        jTable.getModel().addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent e) {
                editEvent(e.getFirstRow(), e.getColumn());
            }
        });

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
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        removeButton.setText("Remove");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        fileMenu.setText("File");
        myMenuBar.add(fileMenu);

        editMenu.setText("Edit");
        myMenuBar.add(editMenu);

        aboutMenu.setText("About");

        usageMenuItem.setText("Usage");
        usageMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usageMenuItemActionPerformed(evt);
            }
        });
        aboutMenu.add(usageMenuItem);

        authorMenuItem.setText("Author");
        authorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                authorMenuItemActionPerformed(evt);
            }
        });
        aboutMenu.add(authorMenuItem);

        myMenuBar.add(aboutMenu);

        setJMenuBar(myMenuBar);

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
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(diaryButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(addButton, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addComponent(removeButton, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(saveButton)
                            .addComponent(weekViewButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(logButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
                    .addComponent(dayViewButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(logButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dayViewButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(weekViewButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
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
/**
 * Button loading the log view.
 */
    private void logButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logButtonActionPerformed
        viewState = 0;
        logViewLoader();
    }//GEN-LAST:event_logButtonActionPerformed
/**
 * Button loading the day view.
 */
    private void dayViewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dayViewButtonActionPerformed
        viewState = 1;
        dayViewLoader();
    }//GEN-LAST:event_dayViewButtonActionPerformed
/**
 * Button loading the diary.
 */
    private void diaryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diaryButtonActionPerformed
        if (evt.getSource() == diaryButton){
            JEditorPaneSave jdiary = new JEditorPaneSave();
            jdiary.run();
        }
    }//GEN-LAST:event_diaryButtonActionPerformed
/**
 * Button loading the week view.
 */
    private void weekViewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weekViewButtonActionPerformed
        viewState = 2;
        weekViewLoader();
    }//GEN-LAST:event_weekViewButtonActionPerformed
    /**
     * Navigates backwards for day View/week view.
     */
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
    /**
     * Navigates forwards for day View/week view.
     */
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

    private void usageMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usageMenuItemActionPerformed
        // TODO add your handling code here:
        launchAbout();
    }//GEN-LAST:event_usageMenuItemActionPerformed

    private void authorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_authorMenuItemActionPerformed
        // TODO add your handling code here:
        launchAuthor();
    }//GEN-LAST:event_authorMenuItemActionPerformed
    private void reloadView(){
        if(viewState == 0) logViewLoader();
        if(viewState == 1) dayViewLoader();
        if(viewState == 2) weekViewLoader();
    }
    /**
     * From within any view, this allows you to add a new activity to the collection.
     */
    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        addActivityFrame addFrame = new addActivityFrame();
        addFrame.setParent(this);
        addFrame.setLocationByPlatform(true);
        addFrame.setVisible(true);
    }//GEN-LAST:event_addButtonActionPerformed
    /**
     * Allows the add-activity frame to communicate with this one.
     * @param activity 
     */
    public void addActivityFromDialogue(Activity activity){
        activities.add(activity);
        Collections.sort(activities);
        reloadView();
    }
    /**
     * Allows you to remove the currently selected row (in log view) from the collection of activities. 
     */
    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        if(viewState == 0){
           if(rowSelected != -1){
               activities.remove(rowSelected);
               rowSelected = -1;
               logViewLoader();
           }
           else JOptionPane.showMessageDialog(this, "Select an activity you would like to remove.");
        }
    }//GEN-LAST:event_removeButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
        saveFile();
    }//GEN-LAST:event_saveButtonActionPerformed
    
    /**
     * Shows a window that details the authors of this application.
     */
    private void launchAuthor(){
        
        JFrame authorFrame = new JFrame("Author");
        authorFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        authorFrame.setLocationByPlatform(true);
        authorFrame.setVisible(true);
        authorFrame.setSize(400,400);
//        
//        ImageIcon ricky = createImageIcon("ricky.png", "Ricky Sidhu");
//        ImageIcon kam = createImageIcon("kam.png", "Kamron Javaherpour");
//        
//        JLabel rickyPic = new JLabel(ricky);
//        JLabel kamPic = new JLabel(kam);
//        
//        rickyPic.setVisible(true);
//        kamPic.setVisible(true);
//        rickyPic.setPreferredSize(new Dimension(300,100));
//        kamPic.setPreferredSize(new Dimension(300,100));
//        
//        
//        authorFrame.add(rickyPic);
//        authorFrame.add(kamPic);
//        
//        
        JTextArea authorArea = new JTextArea("Authors: Ricky Sidhu, Kamron Javaherpour\n"
                + "Design and Development: Ricky Sidhu, Kamron Javaherpour\n"
                + "Implementation and Testing: Ricky Sidhu, Kamron Javaherpour\n"
                + "Contact: ricky@dearsaturn.com kam.javaherpour@gmail.com");
        authorArea.setVisible(true); 
        authorArea.setEditable(false);
        authorArea.setLineWrap(true);
        authorArea.setWrapStyleWord(true);
        
        authorFrame.add(authorArea);
    }
    
    protected ImageIcon createImageIcon(String path, String description) {
    java.net.URL imgURL = this.getClass().getResource(path);
    
    if (imgURL != null) {
        return new ImageIcon(imgURL, description);
    } else {
        System.err.println("Couldn't find file: " + path);
        return null;
    }
}
    /**
     * Details the application itself.
     */
    private void launchAbout(){
        
        JFrame aboutFrame = new JFrame("Usage");
        aboutFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        aboutFrame.setLocationByPlatform(true);
        aboutFrame.setVisible(true);
        aboutFrame.setSize(400,400);
        
        JTextArea aboutArea = new JTextArea("Personal Activity Monitor, version 0.1\n\n"
                + "The previous and next buttons let you browse between different days, or weeks,"
                + " depending on which view you are in.\n\nThe day view shows the activities that you"
                + " did on the given day.\n\nThe week shows you a summary of your activities for that week.\n\n"
                + "The log view is the most descriptive, as it shows all the activities stored by"
                + " the personal activity monitor. It is also the best representation of the"
                + " format the data is stored in.\n\n"
                + "The diary allows you to take notes, or write about your experiences using the Activity Monitor."
                + " It saves your entry, and opens it when it is ready to be edited.");
        
        aboutArea.setVisible(true); 
        aboutArea.setEditable(false);
        aboutArea.setLineWrap(true);
        aboutArea.setWrapStyleWord(true);
        aboutFrame.add(aboutArea);
        
    }

    /**
     * Some basic look-and-feel stuff that NetBeans generated and a main method. No big deal.
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
    private javax.swing.JMenu aboutMenu;
    private javax.swing.JButton addButton;
    private javax.swing.JMenuItem authorMenuItem;
    private javax.swing.JButton dayViewButton;
    private javax.swing.JButton diaryButton;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable;
    private javax.swing.JButton logButton;
    private javax.swing.JMenuBar myMenuBar;
    private javax.swing.JButton removeButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JMenuItem usageMenuItem;
    private javax.swing.JButton weekViewButton;
    // End of variables declaration//GEN-END:variables
    private DefaultTableModel tableModel;
    private ArrayList<Activity> activities;
    private Date currentDate;
    private Date firstDate;
    private Date lastDate;
    private int weekNumber;
    private int viewState;  //0 for logview, 1 for dayview, 2 for weekview
    private int rowSelected = -1;
}
