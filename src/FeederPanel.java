import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FeederPanel extends JPanel {
    private JButton btnBack;
    private ArrayList<FeedingPanel> feedingPanels = new ArrayList<>();
    
    public FeederPanel(MainScreen frame) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for(int i = 0; i < 4; i++) {
            FeedingPanel temp = new FeedingPanel((10+2*i)+":00", 100);
            temp.setAlignmentX(Component.CENTER_ALIGNMENT);
            feedingPanels.add(temp);
            add(temp);
        }

        btnBack = new JButton("Back");
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.addActionListener(e -> frame.openScreen("main"));
 
        add(btnBack);
        
    }
}