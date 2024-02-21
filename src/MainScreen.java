import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JFrame{
    private JButton btnFeeder;
    private JButton btnDoor;
    private JButton btnSettngs;
    private JButton btnProfile;
    private JLabel lblTitle;
    private JPanel panel;


    public MainScreen(String title){
        //generate Jframe
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();

        lblTitle = new JLabel("RoosterRemote");
        lblTitle.setBounds(110, 50, 200, 30);

        btnFeeder = new JButton("Feeder");
        btnFeeder.setBounds(110, 200, 200, 100);

        btnDoor = new JButton("Door");
        btnDoor.setBounds(110, 400, 200, 100);


        //configure the actionlistener for the button
        btnFeeder.addActionListener((ActionEvent actionEvent) -> {
                
        });
        btnDoor.addActionListener((ActionEvent actionEvent) -> {
                
        });
        panel.add(lblTitle);
        panel.add(btnFeeder);
        panel.add(btnDoor);

        //add the Jpanel to the Jframe
        setContentPane(panel);
        setSize(420, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);

    }
}
