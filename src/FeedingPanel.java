import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class FeedingPanel extends JPanel {
    private JButton btnEdit, btnRemove;
    private JLabel lblTime, lblWeight;
    int weight;
    private ImageIcon edit, remove;

    public FeedingPanel(String time, int weight, FeederPanel panel) {
        super();
        this.weight = weight;
        setLayout(new FlowLayout());
        setSize(new Dimension(300, 100));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        
        lblTime = new JLabel(time);

        lblWeight = new JLabel(weight + "g");

        try {
            edit = new ImageIcon(ImageIO.read(new File("images/pencil-edit-button.png"))
                    .getScaledInstance(15, 15, Image.SCALE_AREA_AVERAGING));
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
        try {
            remove = new ImageIcon(ImageIO.read(new File("images/delete-button.png"))
                    .getScaledInstance(15, 15, Image.SCALE_AREA_AVERAGING));
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }

        btnEdit = new JButton(edit);
        btnEdit.addActionListener(e -> panel.editFeedingTime(this));

        btnRemove = new JButton(remove);
        btnRemove.addActionListener(e -> panel.removeFeedingTime(this));

        add(lblTime);
        add(lblWeight);
        add(btnEdit);
        add(btnRemove);
    }

    public int getTimeCompare() {
        String[] times = lblTime.getText().split(":");
        return 2*Integer.parseInt(times[0]) + (times[1].equals("30")?1:0);
    }

    public void setWeight(int newWeight) {
        lblWeight.setText(newWeight + "g");
    }
    public String getWeight() {
        return String.valueOf(weight);
    }
    public String getTime() {
        return lblTime.getText();
    }

}
//easteregg