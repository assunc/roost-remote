import javax.swing.*;
import java.awt.*;

public class FeederPanel extends JPanel {
    private JButton btnBack;
    private JButton btnAdd;
    private JPanel mainPanel;
    private JPanel buttonsPanel;
    private JPanel addPanel;
    private JPanel editPanel;
    private JLabel lblTime;
    private JComboBox<String> timePicker;
    private JLabel lblWeightAdd;
    private JTextField txtWeightAdd;
    private JLabel lblWeightEdit;
    private JTextField txtWeightEdit;
    private MainScreen frame;

    public FeederPanel(MainScreen parentFrame) {
        super();
        setLayout(new BorderLayout());
        frame = parentFrame;

        // mainPanel

        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainPanel.setPreferredSize(new Dimension(100, 100));

        // addPanel

        addPanel = new JPanel();
        addPanel.setLayout(new GridLayout(2, 2));
        addPanel.setSize(new Dimension(300, 150));

        lblTime = new JLabel("Time:");
        lblTime.setSize(80, 50);
        
        lblWeightAdd = new JLabel("Weight:");
        lblWeightAdd.setSize(80, 50);

        String[] times = new String[48];
        for(int i = 0; i < 48; i++) {
            times[i] = i/2 + ":" + (i%2==0?"00":"30");
        }
        timePicker = new JComboBox<String>(times);
        timePicker.setSize(200, 50);

        txtWeightAdd = new JTextField();
        txtWeightAdd.setSize(200, 50);

        addPanel.add(lblTime);
        addPanel.add(timePicker);
        addPanel.add(lblWeightAdd);
        addPanel.add(txtWeightAdd);

        // editPanel

        editPanel = new JPanel();
        editPanel.setLayout(new GridLayout(1, 2));
        editPanel.setSize(new Dimension(300, 150));
        
        lblWeightEdit = new JLabel("Weight:");
        lblWeightEdit.setSize(80, 50);
        
        txtWeightEdit = new JTextField();
        txtWeightEdit.setSize(200, 50);

        editPanel.add(lblWeightEdit);
        editPanel.add(txtWeightEdit);

        // buttonsPanel

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonsPanel.setPreferredSize(new Dimension(100, 50));

        btnBack = new JButton("Back");
        btnBack.addActionListener(e -> frame.openScreen("main"));

        btnAdd = new JButton("Add");
        btnAdd.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(frame, addPanel, "Add new feeding time", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                if (txtWeightAdd.getText().matches("^\\d+$")) {
                    FeedingPanel temp = new FeedingPanel((String) timePicker.getSelectedItem(), Integer.valueOf(txtWeightAdd.getText()), this);
                    int index = -1;
                    for (int i = 0; i < mainPanel.getComponentCount(); i++) {
                        if (temp.getTimeCompare() < ((FeedingPanel)mainPanel.getComponent(i)).getTimeCompare()) {
                            index = i;
                            break;
                        }
                    } 
                    mainPanel.add(temp, index);
                    revalidate();
                } else {
                    JOptionPane.showMessageDialog(frame, "Weight has to be a positive whole number", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            timePicker.setSelectedIndex(0);
            txtWeightAdd.setText("");
        });

        buttonsPanel.add(btnBack);
        buttonsPanel.add(btnAdd);
 
        add(buttonsPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    public void removeFeedingTime(FeedingPanel feedingTime) {
        mainPanel.remove(feedingTime);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void editFeedingTime(FeedingPanel feedingTime) {
        int result = JOptionPane.showConfirmDialog(frame, editPanel, "Edit feeding time", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if (txtWeightEdit.getText().matches("^\\d+$")) {
                feedingTime.setWeight(Integer.valueOf(txtWeightEdit.getText()));
                revalidate();
            } else {
                JOptionPane.showMessageDialog(frame, "Weight has to be a positive whole number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        txtWeightEdit.setText("");
    }
}