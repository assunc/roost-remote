import javax.swing.*;
import java.awt.*;
import org.json.*;

public class FeederPanel extends JPanel {
    private JButton btnBack, btnAdd, btnClear;
    private JPanel mainPanel, buttonsPanel, addPanel, editPanel;
    private TimePicker timePicker;
    private JLabel lblTime, lblWeightAdd, lblWeightEdit;
    private JTextField txtWeightAdd, txtWeightEdit;
    private MainScreen frame;

    public FeederPanel(MainScreen parentFrame) {
        super();
        setLayout(new BorderLayout());
        frame = parentFrame;

        // mainPanel

        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainPanel.setPreferredSize(new Dimension(100, 100));

        new JSONArray(DB.makeGETRequest("getFeedingTimes/"+MainScreen.coopId+"/"))
                .forEach(time -> addFeedingTime(((JSONObject) time).getString("time"),
                        ((JSONObject) time).getInt("weight")));
        revalidate();

        // addPanel

        addPanel = new JPanel();
        addPanel.setLayout(new GridLayout(2, 2));
        addPanel.setSize(new Dimension(300, 150));

        lblTime = new JLabel("Time:");
        lblTime.setSize(80, 50);
        
        lblWeightAdd = new JLabel("Weight:");
        lblWeightAdd.setSize(80, 50);

        timePicker = new TimePicker();
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
            if (JOptionPane.showConfirmDialog(frame, addPanel, "Add new feeding time",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
                if (txtWeightAdd.getText().matches("^\\d+$")) {
                    DB.makeGETRequest("addFeedingTime/"+MainScreen.coopId+"/"+
                            timePicker.getSelectedItem()+"/"+txtWeightAdd.getText());
                    addFeedingTime((String) timePicker.getSelectedItem(), Integer.parseInt(txtWeightAdd.getText()));
                } else {
                    JOptionPane.showMessageDialog(frame, "Weight has to be a positive whole number",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            timePicker.setSelectedIndex(0);
            txtWeightAdd.setText("");
        });

        btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> {
            for (int i = 0; i < mainPanel.getComponentCount();) {
                removeFeedingTime((FeedingPanel) mainPanel.getComponent(0));
            }
        });

        buttonsPanel.add(btnBack);
        buttonsPanel.add(btnAdd);
        buttonsPanel.add(btnClear);

        add(buttonsPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    public void addFeedingTime(String time, int weight) {
        FeedingPanel panel = new FeedingPanel(time, weight, this);
        int index = -1;
        for (int i = 0; i < mainPanel.getComponentCount(); i++) {
            if (panel.getTimeCompare() < ((FeedingPanel)mainPanel.getComponent(i)).getTimeCompare()) {
                index = i;
                break;
            }
        }
        mainPanel.add(panel, index);
        revalidate();
    }

    public void removeFeedingTime(FeedingPanel feedingTime) {
        DB.makeGETRequest("removeFeedingTime/"+MainScreen.coopId+"/"+
                feedingTime.getTime()+"/"+feedingTime.getWeight());
        mainPanel.remove(feedingTime);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void editFeedingTime(FeedingPanel feedingTime) {
        int result = JOptionPane.showConfirmDialog(frame, editPanel, "Edit feeding time",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if (txtWeightEdit.getText().matches("^\\d+$")) {
                DB.makeGETRequest("editFeedingTime/"+txtWeightEdit.getText()+"/"+
                        MainScreen.coopId+"/"+feedingTime.getTime()+"/"+feedingTime.getWeight());
                feedingTime.setWeight(Integer.parseInt(txtWeightEdit.getText()));
                revalidate();
            } else {
                JOptionPane.showMessageDialog(frame, "Weight has to be a positive whole number",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        txtWeightEdit.setText("");
    }
}