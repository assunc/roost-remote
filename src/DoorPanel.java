import javax.swing.*;
import java.awt.*;

public class DoorPanel extends JPanel {
    private JButton btnBack;
    
    public DoorPanel(MainScreen frame) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        btnBack = new JButton("Back");
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.addActionListener(e -> frame.openScreen("main"));
 
        add(btnBack);
        
    }
}