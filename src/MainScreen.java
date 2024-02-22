import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JFrame{
    private JButton btnSettings;
    private JButton btnProfile;
    private JPanel mainPanel;
    private FeedingPanel panel2;
    private static int SCREEN_WIDTH = 420;
    private static int SCREEN_HEIGHT = 800;

    public MainScreen(){
        //generate Jframe
        super("RoostRemote");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new MainPanel();
        mainPanel.setBounds(0, 0, SCREEN_WIDTH-15, SCREEN_HEIGHT-38);

        //add the Jpanel to the Jframe
        add(mainPanel);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
        setResizable(false);
        setVisible(true);
    }
}
