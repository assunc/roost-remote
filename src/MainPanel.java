import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

public class MainPanel extends JPanel {
    private JButton btnFeeder;
    private JButton btnDoor;
    private JLabel lblTitle;
    
    public MainPanel(JFrame frame) {
        super();

        lblTitle = new JLabel("RoosterRemote");
        lblTitle.setBounds(110, 80, 200, 50);
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 25));

        btnFeeder = new JButton("Feeder");
        btnFeeder.setBounds(110, 200, 200, 100);

        btnDoor = new JButton("Door");
        btnDoor.setBounds(110, 400, 200, 100);

        //configure the actionlistener for the button
        btnFeeder.addActionListener(e -> {
            this.setVisible(false);
        });
        btnDoor.addActionListener(e -> {
            this.setVisible(false);
        });
 
        add(lblTitle);
        add(btnFeeder);
        add(btnDoor);
        setLayout(null);
        
        setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
    }
}
