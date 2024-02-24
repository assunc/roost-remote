import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FeedingPanel extends JPanel {
    private JButton btnEdit;
    private JButton btnRemove;
    private JLabel lblTime;
    private JLabel lblWeight;

    public FeedingPanel(String time, int weight) {
        super();
        setLayout(new FlowLayout());
        setSize(new Dimension(300, 100));
        
        lblTime = new JLabel(time);

        lblWeight = new JLabel(String.valueOf(weight) + "g");

        btnEdit = new JButton("Edit");

        btnRemove = new JButton("Remove");


        add(lblTime);
        add(lblWeight);
        add(btnEdit);
        add(btnRemove);
    }
}
