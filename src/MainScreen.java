import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainScreen extends JFrame{
    private JPanel headerPanel, mainPanel, feederPanel, doorPanel, mainScreen;
    private static int SCREEN_WIDTH = 380;
    private static int SCREEN_HEIGHT = 680;
    private CardLayout card;
    private int coopId;
    private ImageIcon imageLogo;

    public MainScreen(String coopIdTemp) {
        //generate Jframe
        super("RoostRemote");
        coopId = Integer.parseInt(coopIdTemp);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // header panel with the logo

        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(100, 110));
//        headerPanel.setBackground(new Color(0x00FF37, false));

        try {
            imageLogo = new ImageIcon(ImageIO.read(new File("images/logo.png"))
                    .getScaledInstance(100, 100, Image.SCALE_AREA_AVERAGING));
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
        JLabel lblLogo = new JLabel(imageLogo);
        headerPanel.add(lblLogo, BorderLayout.SOUTH);

        // main panel that changes between screens

        mainScreen = new JPanel();
        card = new CardLayout(); //layout to switch between screens
        mainScreen.setLayout(card);

        //panel with main screen
        mainPanel = new MainPanel(this);

        //panel with feeder screen
        feederPanel = new FeederPanel(this);

        //panel with door screen
        doorPanel = new DoorPanel(this);

        //add all cards to main panel
        mainScreen.add(feederPanel, "feeder");
        mainScreen.add(doorPanel, "door");
        mainScreen.add(mainPanel, "main");
        card.show(mainScreen, "main"); //initialize in main screen

        //add the Jpanels to the Jframe
        add(headerPanel, BorderLayout.NORTH);
        add(mainScreen, BorderLayout.CENTER);
        setVisible(true);
    }

    //switches to a screen
    public void openScreen(String screen) {
        card.show(mainScreen, screen);
    }

    public int getCoopId(){
        return coopId;
    }
}
