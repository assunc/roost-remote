import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.event.ActionEvent;

public class MainPanel extends JPanel {
    private JButton btnFeeder;
    private JButton btnDoor;
    private JLabel lblTitle;
    
    public MainPanel() {
        super();

        lblTitle = new JLabel("RoosterRemote");
        lblTitle.setBounds(110, 80, 200, 50);
        lblTitle.setHorizontalAlignment(JLabel.CENTER);

        btnFeeder = new JButton("Feeder");
        btnFeeder.setBounds(110, 200, 200, 100);

        btnDoor = new JButton("Door");
        btnDoor.setBounds(110, 400, 200, 100);

        //configure the actionlistener for the button
        // btnFeeder.addActionListener((ActionEvent actionEvent) -> {});
        // btnDoor.addActionListener((ActionEvent actionEvent) -> {});

        add(lblTitle);
        add(btnFeeder);
        add(btnDoor);
        setLayout(null);
        
        setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
    }
}
