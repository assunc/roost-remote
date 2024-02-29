import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;


public class Login extends JFrame{

        private JLabel lblUsername, lblPassword;
        private JTextField txtUsername;
        private JTextField txtPassword;
        private JButton btnLogin, btnCreate;

        public Login() {
            super("Login");
            setSize(400, 200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null); // Center the window

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            // Username label
            lblUsername = new JLabel("Username:");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 0;
            panel.add(lblUsername, c);

            // Username text field
            txtUsername = new JTextField(20);
            c.gridx = 1;
            c.gridy = 0;
            panel.add(txtUsername, c);

            // Password label
            lblPassword = new JLabel("Password:");
            c.gridx = 0;
            c.gridy = 1;
            panel.add(lblPassword, c);

            // Password field
            txtPassword = new JPasswordField(20);
            c.gridy = 1;
            c.gridx = 1;
            panel.add(txtPassword, c);

            // Login button
            btnLogin = new JButton("Log in");
            btnLogin.addActionListener(e -> {
                String u = txtUsername.getText();
                String p = Integer.toString(txtPassword.getText().hashCode());


                DBTest db = new DBTest();


                if (p.equals(db.parseJSONLogin(db.makeGETRequest("https://studev.groept.be/api/a23ib2d05/Login/" + u)))) {
                    JOptionPane.showMessageDialog(Login.this, "Login successful!");
                    MainScreen mainScreen = new MainScreen();
                    mainScreen.setVisible(true);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Invalid username or password!");
                }
            });
            c.gridy = 2;
            c.gridx = 0;
            panel.add(btnLogin, c);

            // Cancel button
            btnCreate = new JButton("Create a new account");
            //btnCancel.addActionListener(e -> System.exit(0));
            //btnCreate.addActionListener(e -> frame.openScreen("main"));
            btnCreate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Create an instance of the CreateAccountPanel
                    createAccount createAccountPanel = new createAccount(Login.this);
                    createAccountPanel.setVisible(true);
                    setVisible(false);
                }
            });
            c.gridx = 1;
            panel.add(btnCreate, c);

            add(panel);
            setVisible(true);
        }
    }