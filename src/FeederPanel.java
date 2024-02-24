import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FeederPanel extends JPanel {
    private JButton btnBack;
    private JButton btnAdd;
    private JPanel mainPanel;
    private JPanel buttonsPanel;
    private JPanel addPanel;
    private JLabel lblTime;
    private JComboBox<String> timePicker;
    private JLabel lblWeight;
    private JTextField txtWeight;
    
    public FeederPanel(MainScreen frame) {
        super();
        setLayout(new BorderLayout());

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
        
        lblWeight = new JLabel("Weight:");
        lblWeight.setSize(80, 50);

        String[] times = new String[48];
        for(int i = 0; i < 48; i++) {
            times[i] = i/2 + ":" + (i%2==0?"00":"30");
        }
        timePicker = new JComboBox<String>(times);
        timePicker.setSize(200, 50);

        txtWeight = new JTextField();
        txtWeight.setSize(200, 50);

        addPanel.add(lblTime);
        addPanel.add(timePicker);
        addPanel.add(lblWeight);
        addPanel.add(txtWeight);

        // buttonsPanel

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonsPanel.setPreferredSize(new Dimension(100, 50));
        buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        btnBack = new JButton("Back");
        btnBack.addActionListener(e -> frame.openScreen("main"));

        btnAdd = new JButton("Add");
        btnAdd.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(frame, addPanel, "Add new feeding time", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                if (txtWeight.getText().matches("^\\d+$")) {
                    FeedingPanel temp = new FeedingPanel((String) timePicker.getSelectedItem(), Integer.valueOf(txtWeight.getText()));
                    temp.setAlignmentX(Component.CENTER_ALIGNMENT);
                    int index = -1;
                    for (int i = 0; i < mainPanel.getComponentCount(); i++) {
                        if (temp.getTimeCompare() < ((FeedingPanel)mainPanel.getComponent(i)).getTimeCompare()) {
                            index = i;
                            break;
                        }
                    } 
                    mainPanel.add(temp, index);
                    frame.revalidate();
                } else {
                    JOptionPane.showMessageDialog(frame, "Weight has to be a positive whole number", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            timePicker.setSelectedIndex(0);
            txtWeight.setText("");
        });

        buttonsPanel.add(btnBack);
        buttonsPanel.add(btnAdd);
 
        add(buttonsPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
}