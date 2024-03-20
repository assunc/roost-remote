import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class FeedingPanel extends JPanel {
    private JButton btnRemove;
    private JLabel lblTime;
    private ImageIcon remove;

    public FeedingPanel(String time, FeederPanel panel) {
        super();
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        setSize(new Dimension(300, 100));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        
        lblTime = new JLabel(time);


        try {
            remove = new ImageIcon(ImageIO.read(new File("images/delete-button.png"))
                    .getScaledInstance(15, 15, Image.SCALE_AREA_AVERAGING));
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }

        btnRemove = new JButton(remove);
        btnRemove.addActionListener(e -> panel.removeFeedingTime(this));

        add(lblTime);
        add(btnRemove);
    }

    public int getTimeCompare() {
        String[] times = lblTime.getText().split(":");
        return 2*Integer.parseInt(times[0]) + (times[1].equals("30")?1:0);
    }

    public String getTime() {
        return lblTime.getText();
    }

}
