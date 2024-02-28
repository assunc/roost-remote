import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Login extends JFrame{

        private JLabel lblUsername, lblPassword;
        private JTextField txtUsername;
        private JPasswordField txtPassword;
        private JButton btnLogin, btnCancel;

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
            btnLogin = new JButton("Login");
            btnLogin.addActionListener(e -> {
                // Replace the following with your actual login logic
                if (txtUsername.getText().equals("admin") && txtPassword.getPassword().toString().equals("password")) {
                    JOptionPane.showMessageDialog(this, "Login successful!");
                    // Close the login window and open the main application window
                    setVisible(false);
                    // Replace with your main application window
                    // new MyMainApplicationWindow().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password!");
                }
            });
            c.gridy = 2;
            c.gridx = 0;
            panel.add(btnLogin, c);

            // Cancel button
            btnCancel = new JButton("Cancel");
            btnCancel.addActionListener(e -> System.exit(0));
            c.gridx = 1;
            panel.add(btnCancel, c);

            add(panel);
            setVisible(true);
        }
    }