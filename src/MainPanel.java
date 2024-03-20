import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainPanel extends JPanel {
    private JButton btnFeeder, btnDoor, upButton, downButton;
    private JLabel lblTitle, totalChickensLabel, currentChickensLabel;
    private JTextField totalChickensTextField, currentChickensTextField;
    private String totalChickens;
    private ImageIcon imgPlus, imgMinus;

    public MainPanel(MainScreen frame) {
        super();
        int coopId = frame.getCoopId();
        setLayout(new GridLayout(3, 1, 10, 0));
        JPanel chickenPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // title

        lblTitle = new JLabel("Rooster Remote");
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 25));

        //chickens menu

        totalChickens = Integer.toString(DB.makeGETRequest("getTotalChicken/" + coopId)
                .getJSONObject(0).getInt("totalChicken"));

        totalChickensLabel = new JLabel("Total of chickens inside the coop: ");
        totalChickensTextField = new JTextField(5);
        totalChickensTextField.setEditable(false);
        totalChickensTextField.setText(totalChickens); // Set initial value

        // load images
        try {
            imgPlus = new ImageIcon(ImageIO.read(new File("images/plus.png"))
                    .getScaledInstance(10, 10, Image.SCALE_FAST));
            imgMinus = new ImageIcon(ImageIO.read(new File("images/minus.png"))
                    .getScaledInstance(10, 10, Image.SCALE_FAST));
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }

        // +/- buttons
        upButton = new JButton(imgPlus);
        downButton = new JButton(imgMinus);

        upButton.addActionListener(e -> {
            String total = String.valueOf(Integer.parseInt(totalChickens)+1);
            totalChickensTextField.setText(total);
            totalChickens = total;
            revalidate();
            DB.makeGETRequest("setTotalChickens/" + total + "/" + coopId);
        });

        downButton.addActionListener(e -> {
            String total = String.valueOf(Math.max(0, Integer.parseInt(totalChickens)-1));
            totalChickensTextField.setText(total);
            totalChickens = total;
            revalidate();
            DB.makeGETRequest("setTotalChickens/" + total + "/" + coopId);
        });

        //number of chickens
        currentChickensLabel = new JLabel("Current chickens inside the coop: ");
        currentChickensTextField = new JTextField(5);
        currentChickensTextField.setEditable(false);
        String currentChicken = String.valueOf(DB.makeGETRequest("getPresentChickens/" + coopId)
                .getJSONObject(0).getInt("presentChicken"));
        currentChickensTextField.setText(currentChicken); // Set value from database
        // Check every 5 seconds
        Timer timer = new Timer(5000, e -> {
            String currentChicken1 = String.valueOf(DB.makeGETRequest("getPresentChickens/" + coopId)
                    .getJSONObject(0).getInt("presentChicken"));
            currentChickensTextField.setText(currentChicken1);
            revalidate();
        });
        timer.start();

        //add everything to chicken panel
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        chickenPanel.add(lblTitle, gbc);
        gbc.gridy = 2;
        chickenPanel.add(totalChickensLabel, gbc);
        gbc.gridx = 1;
        chickenPanel.add(totalChickensTextField, gbc);
        gbc.gridx = 2;
        chickenPanel.add(upButton, gbc);
        gbc.gridx = 3;
        chickenPanel.add(downButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        chickenPanel.add(currentChickensLabel, gbc);
        gbc.gridx = 1;
        chickenPanel.add(currentChickensTextField, gbc);

        //buttons for other screens

        btnFeeder = new JButton("Feeder");
        btnDoor = new JButton("Door");

        btnFeeder.addActionListener(e -> frame.openScreen("feeder"));
        btnDoor.addActionListener(e -> frame.openScreen("door"));

        //add everything to the panel
        add(chickenPanel);
        add(btnFeeder);
        add(btnDoor);

    }
}
