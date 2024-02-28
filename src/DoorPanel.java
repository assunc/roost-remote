import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DoorPanel extends JPanel {

    private JButton btnBack;
    private JTextField txtOpeningTime;
    private JTextField txtClosingTime;
    private JButton btnChangeOpeningTime;
    private JButton btnChangeClosingTime;
    private JButton btnToggleDoor;

    public DoorPanel(MainScreen frame) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Panel for Opening Time
        JPanel openingTimePanel = new JPanel();
        openingTimePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lblOpeningTime = new JLabel("Opening Time:");
        openingTimePanel.add(lblOpeningTime);
        txtOpeningTime = new JTextField("06:00", 5);
        openingTimePanel.add(txtOpeningTime);
        btnChangeOpeningTime = new JButton("Save Changes");
        openingTimePanel.add(btnChangeOpeningTime);

        // Panel for Closing Time
        JPanel closingTimePanel = new JPanel();
        closingTimePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lblClosingTime = new JLabel("Closing Time:");
        closingTimePanel.add(lblClosingTime);
        txtClosingTime = new JTextField("18:00", 5);
        closingTimePanel.add(txtClosingTime);
        btnChangeClosingTime = new JButton("Save Changes");
        closingTimePanel.add(btnChangeClosingTime);

        // Toggle button for manual control
        btnToggleDoor = new JButton("Open Door");
        btnToggleDoor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnToggleDoor.getText().equals("Open Door")) {
                    btnToggleDoor.setText("Close Door");
                    // Replace with your door closing logic here
                } else {
                    btnToggleDoor.setText("Open Door");
                    // Replace with your door opening logic here
                }
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