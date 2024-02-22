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
        
        lblTime = new JLabel(time);
        lblTime.setBounds(20, 50, 100, 50);

        lblWeight = new JLabel(String.valueOf(weight) + "g");
        lblWeight.setBounds(150, 50, 100, 50);

        btnEdit = new JButton("Edit");
        btnEdit.setBounds(250, 50, 50, 50);

        btnRemove = new JButton("Remove");
        btnRemove.setBounds(250, 100, 50, 50);


        add(lblTime);
        add(lblWeight);
        add(btnEdit);
        add(btnRemove);
    }
}
