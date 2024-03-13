import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainPanel extends JPanel {
    private JButton btnFeeder, btnDoor, upButton, downButton;
    private JLabel lblTitle, totalChickensLabel, currentChickensLabel, emptyLbl;
    private JTextField totalChickensTextField, currentChickensTextField;
    private String totalChickens;
    private ImageIcon imgPlus, imgMinus;

    public MainPanel(MainScreen frame) {
        super();
        int coopId = frame.getCoopId();
        setLayout(new GridLayout(3, 1));
        DBTest db = new DBTest();
        JPanel chickenPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        lblTitle = new JLabel("Rooster Remote");
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 25));

        totalChickens = db.parseJSONgetTotalChickens(db.makeGETRequest("https://studev.groept.be/api/a23ib2d05/getTotalChicken/" + coopId));

        emptyLbl = new JLabel("           ");
        totalChickensLabel = new JLabel("Total of chickens inside the coop: ");
        totalChickensTextField = new JTextField(5);
        totalChickensTextField.setEditable(false);
        totalChickensTextField.setText(totalChickens); // Set initial value

        try {
            imgPlus = new ImageIcon(ImageIO.read(new File("images/plus.png"))
                    .getScaledInstance(10, 10, Image.SCALE_DEFAULT));
            imgMinus = new ImageIcon(ImageIO.read(new File("images/minus.png"))
                    .getScaledInstance(10, 10, Image.SCALE_DEFAULT));
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }

        upButton = new JButton(imgPlus);
        downButton = new JButton(imgMinus);

        upButton.addActionListener(e -> {
            int total = Integer.parseInt(totalChickens)+1;
            totalChickensTextField.setText(String.valueOf(total));
            totalChickens = String.valueOf(total);
            revalidate();
            db.makeGETRequest("https://studev.groept.be/api/a23ib2d05/setTotalChickens/" + total + "/" + coopId);
        });

        downButton.addActionListener(e -> {
            int total = Math.max(0, Integer.parseInt(totalChickens)-1);
            totalChickensTextField.setText(String.valueOf(total));
            totalChickens = String.valueOf(total);
            revalidate();
            db.makeGETRequest("https://studev.groept.be/api/a23ib2d05/setTotalChickens/" + total + "/" + coopId);
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        chickenPanel.add(lblTitle, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        chickenPanel.add(totalChickensLabel, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        chickenPanel.add(totalChickensTextField, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 2;
        chickenPanel.add(upButton, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 2;
        chickenPanel.add(downButton, c);

        //chickenPanel.add(emptyLbl);
        currentChickensLabel = new JLabel("Current chickens inside the coop: ");
        currentChickensTextField = new JTextField(5);
        currentChickensTextField.setEditable(false);
        String currentChicken = db.parseJSONgetCurrentChickens(db.makeGETRequest("https://studev.groept.be/api/a23ib2d05/getPresentChickens/" + coopId));
        currentChickensTextField.setText(currentChicken); // Set value from database
        // Check every 5 seconds
        Timer timer = new Timer(5000, e -> {
            String currentChicken1 = db.parseJSONgetCurrentChickens(db.makeGETRequest("https://studev.groept.be/api/a23ib2d05/getPresentChickens/" + coopId));
            currentChickensTextField.setText(currentChicken1);
        });
        timer.start();


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        chickenPanel.add(currentChickensLabel, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        chickenPanel.add(currentChickensTextField, c);


        btnFeeder = new JButton("Feeder");
        btnFeeder.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnDoor = new JButton("Door");
        btnDoor.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnFeeder.addActionListener(e -> frame.openScreen("feeder"));
        btnDoor.addActionListener(e -> frame.openScreen("door"));

        add(chickenPanel);
        add(btnFeeder);
        add(btnDoor);

    }
}
