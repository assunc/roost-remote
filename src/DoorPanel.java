import org.json.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DoorPanel extends JPanel {

    private JButton btnBack;
    private TimePicker timeOpen, timeClose;
    private JButton btnSetOpenTime, btnSetCloseTime;
    private JToggleButton btnToggleDoor;

    public DoorPanel(MainScreen frame) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Panel for Opening Time
        JPanel openingTimePanel = new JPanel();
        openingTimePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel lblOpeningTime = new JLabel("Opening Time:");

        timeOpen = new TimePicker();
        timeOpen.setSize(200, 50);
        timeOpen.setSelectedItem(new JSONArray(DB.makeGETRequest("getOpenTime/"+
                frame.getCoopId())).getJSONObject(0).getString("openTime"));

        btnSetOpenTime = new JButton("Save Changes");
        btnSetOpenTime.addActionListener(e -> DB.makeGETRequest("setOpenTime/"+
                timeOpen.getSelectedItem()+"/"+frame.getCoopId()));

        openingTimePanel.add(lblOpeningTime);
        openingTimePanel.add(timeOpen);
        openingTimePanel.add(btnSetOpenTime);

        // Panel for Closing Time
        JPanel closingTimePanel = new JPanel();
        closingTimePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel lblClosingTime = new JLabel("Closing Time:");

        timeClose = new TimePicker();
        timeClose.setSize(200, 50);
        timeClose.setSelectedItem(new JSONArray(DB.makeGETRequest("getCloseTime/"+
                frame.getCoopId())).getJSONObject(0).getString("closeTime"));

        btnSetCloseTime = new JButton("Save Changes");
        btnSetCloseTime.addActionListener(e -> DB.makeGETRequest("setCloseTime/"+
                timeClose.getSelectedItem()+"/"+frame.getCoopId()));


        closingTimePanel.add(lblClosingTime);
        closingTimePanel.add(timeClose);
        closingTimePanel.add(btnSetCloseTime);

        // Toggle button for manual control
       // boolean doorIsOpen = new JSONArray(DB.makeGETRequest("getDoorIsOpen/"+
       //         frame.getCoopId())).getJSONObject(0).getInt("doorIsOpen")==
        DBTest db = new DBTest();
        int doorIsOpenString = db.parseJSONgetDoor(db.makeGETRequest("https://studev.groept.be/api/a23ib2d05/getDoorIsOpen/" + frame.getCoopId()));
        //System.out.println("The value of doorIsOpen is:" + doorIsOpenString);

        boolean doorIsOpen = doorIsOpenString == 1;
        btnToggleDoor = new JToggleButton();
        if (doorIsOpen)
            btnToggleDoor.setText("Close Door");
        else
            btnToggleDoor.setText("Open Door");
        btnToggleDoor.addActionListener(e -> {
            if (((AbstractButton)e.getSource()).getModel().isSelected()) {
                btnToggleDoor.setText("Open Door");
                db.makeGETRequest("https://studev.groept.be/api/a23ib2d05/changeStateDoor/" + "0" + "/" + frame.getCoopId());
            } else {
                btnToggleDoor.setText("Close Door");
                db.makeGETRequest("https://studev.groept.be/api/a23ib2d05/changeStateDoor/" + "1" + "/" + frame.getCoopId());
            }
        });
        btnToggleDoor.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Back button
        btnBack = new JButton("Back");
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.addActionListener(e -> frame.openScreen("main"));

        add(openingTimePanel);
        add(closingTimePanel);
        add(btnToggleDoor); // Moved the toggle button here
        add(Box.createVerticalStrut(10)); // Added a vertical strut for spacing
        add(btnBack);
    }
}
//easteregg