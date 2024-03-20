import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Objects;

import org.json.*;

public class FeederPanel extends JPanel {
    private JButton btnBack, btnAdd, btnClear;
    private JPanel mainPanel, buttonsPanel, addPanel, totalFoodPanel;
    private TimePicker timePicker;
    private JLabel lblTime, totalFoodLbl;
    private JTextField totalFoodField;
    private MainScreen frame;
    private ImageIcon back;

    public FeederPanel(MainScreen parentFrame) {
        super();
        setLayout(new BorderLayout());
        frame = parentFrame;

        //title

        JLabel title = new JLabel("Feeding schedule:");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        // mainPanel

        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, 100, 15));
        mainPanel.setPreferredSize(new Dimension(100, 500));

        DB.makeGETRequest("getFeedingTimes/"+parentFrame.getCoopId()+"/")
                .forEach(time -> addFeedingTime(((JSONObject) time).getString("time")));
        revalidate();

        // addPanel

        addPanel = new JPanel();
        addPanel.setLayout(new GridLayout(2, 2));
        addPanel.setSize(new Dimension(300, 150));

        lblTime = new JLabel("Time:");
        lblTime.setSize(80, 50);
        
        timePicker = new TimePicker();
        timePicker.setSize(200, 50);

        addPanel.add(lblTime);
        addPanel.add(timePicker);

        // buttonsPanel

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonsPanel.setPreferredSize(new Dimension(100, 50));

        try {
            back = new ImageIcon(ImageIO.read(new File("images/back-arrow.png"))
                    .getScaledInstance(15, 15, Image.SCALE_AREA_AVERAGING));
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
        btnBack = new JButton(back);
        btnBack.addActionListener(e -> frame.openScreen("main"));

        btnAdd = new JButton("Add");
        btnAdd.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(frame, addPanel, "Add new feeding time",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
                if(addFeedingTime((String) Objects.requireNonNull(timePicker.getSelectedItem()))) {
                    DB.makeGETRequest("addFeedingTime/"+frame.getCoopId()+"/"+
                            timePicker.getSelectedItem());
                }
            }
            timePicker.setSelectedIndex(0);
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

        // totalFoodPanel

        totalFoodPanel = new JPanel();
        totalFoodPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        totalFoodPanel.setPreferredSize(new Dimension(200, 50));

        totalFoodField = new JTextField();
        totalFoodField.setSize(100, 50);
        totalFoodField.setEditable(false);
        float food = DB.makeGETRequest("getFood/"+frame.getCoopId())
                .getJSONObject(0).getFloat("food");
        totalFoodField.setText(food == 0 ? "Empty" : "Full");
        // Check every minute
        Timer timer = new Timer(6000, e -> {
            float food1 = DB.makeGETRequest("getFood/"+frame.getCoopId())
                    .getJSONObject(0).getInt("food");
            totalFoodField.setText(food1 == 0 ? "Empty" : "Full");
            totalFoodField.revalidate();
            totalFoodField.repaint();
        });
        timer.start();

        totalFoodLbl = new JLabel();
        totalFoodLbl.setSize(200, 50);
        totalFoodLbl.setText("The food container is currently:");

        totalFoodPanel.add(totalFoodLbl);
        totalFoodPanel.add(totalFoodField);

        // bottomPanel

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(totalFoodPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(title, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public boolean addFeedingTime(String time) {
        float timeCompare = 2*Integer.parseInt(time.split(":")[0]) + (time.split(":")[1].equals("30")?1:0);
        int index = -1;
        for (int i = 0; i < mainPanel.getComponentCount(); i++) {
            if (timeCompare < ((FeedingPanel)mainPanel.getComponent(i)).getTimeCompare()) {
                index = i;
                break;
            } else if (timeCompare == ((FeedingPanel)mainPanel.getComponent(i)).getTimeCompare()) {
                JOptionPane.showMessageDialog(frame, "There is already a feeding scheduled for this time.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        FeedingPanel panel = new FeedingPanel(time,this);
        mainPanel.add(panel, index);
        revalidate();
        return true;
    }

    public void removeFeedingTime(FeedingPanel feedingTime) {
        DB.makeGETRequest("removeFeedingTime/"+frame.getCoopId()+"/"+
                feedingTime.getTime());
        mainPanel.remove(feedingTime);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

}
