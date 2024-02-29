import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private JButton btnFeeder, btnDoor;
    private JLabel lblTitle;
    
    public MainPanel(MainScreen frame) {
        super();
        setLayout(new GridLayout(3, 1));

        lblTitle = new JLabel("RoosterRemote");
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 25));

        btnFeeder = new JButton("Feeder");
        btnFeeder.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnDoor = new JButton("Door");
        btnDoor.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnFeeder.addActionListener(e -> frame.openScreen("feeder"));
        btnDoor.addActionListener(e -> frame.openScreen("door"));
 
        add(lblTitle);
        add(btnFeeder);
        add(btnDoor);
        
    }
}
