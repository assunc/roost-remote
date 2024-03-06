import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame{
    private JButton btnSettings, btnProfile;
    private JPanel headerPanel, mainPanel, feederPanel, doorPanel, mainScreen;
    private static int SCREEN_WIDTH = 380;
    private static int SCREEN_HEIGHT = 680;
    private CardLayout card;
    public static int coopId = 1;

    public MainScreen(){
        //generate Jframe
        super("RoostRemote");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
        setResizable(false);

        headerPanel = new JPanel();
        headerPanel.setPreferredSize(new Dimension(100, 50));
        headerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        mainScreen = new JPanel();
        mainScreen.setPreferredSize(new Dimension(100, 100));
        card = new CardLayout();
        mainScreen.setLayout(card);

        mainPanel = new MainPanel(this);
        mainPanel.setPreferredSize(new Dimension(100, 100));

        feederPanel = new FeederPanel(this);
        feederPanel.setPreferredSize(new Dimension(100, 100));

        doorPanel = new DoorPanel(this);
        doorPanel.setPreferredSize(new Dimension(100, 100));

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
}
