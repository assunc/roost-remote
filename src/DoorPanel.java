import org.json.JSONArray;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.LocalTime;

public class DoorPanel extends JPanel {

    private JButton btnBack;
    private TimePicker timeOpen, timeClose;
    private JButton btnSetOpenTime, btnSetCloseTime;
    private JToggleButton btnToggleDoor;
    private JPanel openingTimePanel, closingTimePanel;
    private JLabel lblOpeningTime, lblSun, lblClosingTime, lblMoon;
    private ImageIcon sun, moon, back, lock, unlock;

    public DoorPanel(MainScreen frame) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //load images
        try {
            sun = new ImageIcon(ImageIO.read(new File("images/rising-sun.png"))
                    .getScaledInstance(100, 100, Image.SCALE_AREA_AVERAGING));
            moon = new ImageIcon(ImageIO.read(new File("images/moon2.png"))
                    .getScaledInstance(100, 100, Image.SCALE_AREA_AVERAGING));
            lock = new ImageIcon(ImageIO.read(new File("images/padlock.png"))
                    .getScaledInstance(25, 25, Image.SCALE_AREA_AVERAGING));
            unlock = new ImageIcon(ImageIO.read(new File("images/padlock-unlock.png"))
                    .getScaledInstance(25, 25, Image.SCALE_AREA_AVERAGING));
            back = new ImageIcon(ImageIO.read(new File("images/back-arrow.png"))
                    .getScaledInstance(25, 25, Image.SCALE_AREA_AVERAGING));
        } catch (Exception e) {
            System.err.println("Error loading image sun: " + e.getMessage());
        }

        // Panel for Opening Time

        openingTimePanel = new JPanel();
        openingTimePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        lblOpeningTime = new JLabel("Opening Time:");

        timeOpen = new TimePicker();
        timeOpen.setSize(200, 50);
        timeOpen.setSelectedItem(new JSONArray(DB.makeGETRequest("getOpenTime/"+
                frame.getCoopId())).getJSONObject(0).getString("openTime"));

        btnSetOpenTime = new JButton("Save Changes");
        btnSetOpenTime.addActionListener(e -> DB.makeGETRequest("setOpenTime/"+
                timeOpen.getSelectedItem()+"/"+frame.getCoopId()));

        lblSun = new JLabel(sun);

        openingTimePanel.add(lblOpeningTime);
        openingTimePanel.add(timeOpen);
        openingTimePanel.add(btnSetOpenTime);
        openingTimePanel.add(lblSun);

        // Panel for Closing Time
        closingTimePanel = new JPanel();
        closingTimePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10 , 30));

        lblClosingTime = new JLabel("Closing Time:");

        timeClose = new TimePicker();
        timeClose.setSize(200, 50);
        timeClose.setSelectedItem(new JSONArray(DB.makeGETRequest("getCloseTime/"+
                frame.getCoopId())).getJSONObject(0).getString("closeTime"));

        btnSetCloseTime = new JButton("Save Changes");
        btnSetCloseTime.addActionListener(e -> DB.makeGETRequest("setCloseTime/"+
                timeClose.getSelectedItem()+"/"+frame.getCoopId()));

        lblMoon = new JLabel(moon);

        closingTimePanel.add(lblClosingTime);
        closingTimePanel.add(timeClose);
        closingTimePanel.add(btnSetCloseTime);
        closingTimePanel.add(lblMoon);

        // Toggle button for manual door control
        boolean doorIsOpen = new JSONArray(DB.makeGETRequest("getDoorIsOpen/" + frame.getCoopId()))
                .getJSONObject(0).getInt("doorIsOpen") == 1;
        btnToggleDoor = new JToggleButton();
        btnToggleDoor.setIcon(doorIsOpen ? lock : unlock);
        btnToggleDoor.addActionListener(e -> {
            if (((AbstractButton)e.getSource()).getModel().isSelected()) {
                btnToggleDoor.setIcon(unlock);
                DB.makeGETRequest("changeStateDoor/" + "0" + "/" + frame.getCoopId());
            } else {
                btnToggleDoor.setIcon(lock);
                DB.makeGETRequest("changeStateDoor/" + "1" + "/" + frame.getCoopId());
            }
        });
        btnToggleDoor.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Back button
        btnBack = new JButton(back);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.addActionListener(e -> frame.openScreen("main"));

        //add everything to panel
        add(openingTimePanel);
        add(closingTimePanel);
        add(btnToggleDoor);
        add(Box.createVerticalStrut(10)); // Added a vertical strut for spacing
        add(btnBack);

        // show a message if its time to close but some chickens are still out
        LocalTime currentTime = LocalTime.now();
        String nowTime = currentTime.getHour() + ":" + currentTime.getMinute();
        String closingTime = (new JSONArray(DB.makeGETRequest("getCloseTime/"+
                frame.getCoopId())).getJSONObject(0).getString("closeTime"));
        String totalChickens = String.valueOf(new JSONArray(DB.makeGETRequest("getTotalChicken/" + frame.getCoopId()))
                .getJSONObject(0).getInt("totalChicken"));
        String currentChicken = String.valueOf(new JSONArray(DB.makeGETRequest("getPresentChickens/" + frame.getCoopId()))
                .getJSONObject(0).getInt("presentChicken"));
        if (Integer.parseInt(currentChicken) < Integer.parseInt(totalChickens) && compareTimes(nowTime, closingTime))
            displayErrorMessage();
    }
    public boolean compareTimes (String t1, String t2)
    {
        String[] T1 = t1.split(":");
        String[] T2 = t2.split(":");
        if (Integer.parseInt(T1[0]) > Integer.parseInt(T2[0]))
            return true;
        else if (Integer.parseInt(T1[0]) == Integer.parseInt(T2[0]))
            return Integer.parseInt(T1[1]) >= Integer.parseInt(T2[1]);
        return false;
    }

    private void displayErrorMessage() {
        JFrame errorFrame = new JFrame("Error!");
        errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel errorPanel = new JPanel();
        errorPanel.setLayout(new BorderLayout());
        errorPanel.setBackground(Color.RED);

        JLabel errorMessageLabel = new JLabel("<html><center>WARNING! It is closing time, but not all chickens are inside.</center></html>");
        errorMessageLabel.setForeground(Color.WHITE);
        errorMessageLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> errorFrame.dispose());
        closeButton.setPreferredSize(new Dimension(100, 50));
        closeButton.setBackground(Color.WHITE);
        closeButton.setOpaque(true);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        errorPanel.add(errorMessageLabel, BorderLayout.CENTER);
        errorPanel.add(closeButton, BorderLayout.SOUTH);

        errorFrame.add(errorPanel);
        errorFrame.pack();
        errorFrame.setLocationRelativeTo(null);
        errorFrame.setVisible(true);
    }
}
