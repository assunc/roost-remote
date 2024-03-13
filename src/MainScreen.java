//original code
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainScreen extends JFrame{
    private JButton btnSettings, btnProfile;
    private JPanel headerPanel, mainPanel, feederPanel, doorPanel, mainScreen;
    private static int SCREEN_WIDTH = 380;
    private static int SCREEN_HEIGHT = 680;
    private CardLayout card;
    private int coopId;
    //public int coopId;

    public MainScreen(String coopIdTemp) {
        //generate Jframe
        super("RoostRemote");
        coopId = Integer.parseInt(coopIdTemp);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(100, 100));
        try {
            ImageIcon imageLogo = new ImageIcon(ImageIO.read(new File("images/logo.png"))
                    .getScaledInstance(100, 100, Image.SCALE_AREA_AVERAGING));
            JLabel lblLogo = new JLabel(imageLogo);
            headerPanel.add(lblLogo, BorderLayout.CENTER);
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }

        mainScreen = new JPanel();
        mainScreen.setPreferredSize(new Dimension(100, 100));
        card = new CardLayout();
        mainScreen.setLayout(card);
        mainScreen.setPreferredSize(new Dimension(100, 100));

        mainPanel = new MainPanel(this);
        mainPanel.setPreferredSize(new Dimension(100, 100));

        feederPanel = new FeederPanel(this);
        feederPanel.setPreferredSize(new Dimension(100, 100));

        doorPanel = new DoorPanel(this);

        mainScreen.add(feederPanel, "feeder");
        mainScreen.add(doorPanel, "door");
        mainScreen.add(mainPanel, "main");
        card.show(mainScreen, "main");

        //add the Jpanels to the Jframe
        add(headerPanel, BorderLayout.NORTH);
        add(mainScreen, BorderLayout.CENTER);
        setVisible(true);
    }

    public void openScreen(String screen) {
        card.show(mainScreen, screen);
    }

    public int getCoopId(){
        return coopId;
    }
}
//easteregg