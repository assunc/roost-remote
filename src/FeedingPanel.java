import java.awt.*;
import javax.swing.*;

public class FeedingPanel extends JPanel {
    private JButton btnEdit;
    private JButton btnRemove;
    private JLabel lblTime;
    private JLabel lblWeight;

    public FeedingPanel(String time, int weight, FeederPanel panel) {
        super();
        setLayout(new FlowLayout());
        setSize(new Dimension(300, 100));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        
        lblTime = new JLabel(time);

        lblWeight = new JLabel(weight + "g");

        btnEdit = new JButton("Edit");
        btnEdit.addActionListener(e -> panel.editFeedingTime(this));

        btnRemove = new JButton("Remove");
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
}
