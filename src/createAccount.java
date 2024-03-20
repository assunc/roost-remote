import org.json.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;


public class createAccount extends JFrame {

    private JLabel lblUsername, lblPassword, lblConfirmPassword;
    private JTextField txtUsername;
    private JTextField txtPassword, txtConfirmPassword;
    private JButton btnCreateAccount, btnBackToLogIn;
    private Login loginScreen;

    public String getUsername()
    {
        return txtUsername.getText();
    }
    public String getPassword(){
        return txtPassword.getText();
    }
    public createAccount(Login logIn) {
        super("Create Account");
        this.loginScreen = logIn;
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
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

        // Confirm password label
        lblConfirmPassword = new JLabel("Confirm Password:");
        c.gridx = -1;
        c.gridy = 2;
        panel.add(lblConfirmPassword, c);

        // Confirm password field
        txtConfirmPassword = new JPasswordField(20);
        c.gridy = 2;
        c.gridx = 1;
        panel.add(txtConfirmPassword, c);

        // Create account button
        btnCreateAccount = new JButton("Create Account");
        btnCreateAccount.addActionListener(e -> {
            //String username = txtUsername.getText();
            String password = txtPassword.getText();
            String confirmPassword = txtConfirmPassword.getText();
            if (password.length() < 5) {
                if (password.equals(confirmPassword)) {

                    String username = txtUsername.getText();
                    String passwordHash = Integer.toString(txtPassword.getText().hashCode());

                    DB.makeGETRequest("CreateUser/" + username + "/" + passwordHash);
                    String idUser = new JSONArray(DB.makeGETRequest("getID/" + username)).getJSONObject(0).getString("idUser");

                    DB.makeGETRequest("CreateCoop/" + idUser + "/0/0/0/6:00/19:00/1");
                    JOptionPane.showMessageDialog(createAccount.this, "Account successfully created!");
                    setVisible(false);
                    loginScreen.setVisible(true); // Show the login screen again
                } else {
                    JOptionPane.showMessageDialog(createAccount.this, "Passwords don't match!");
                }
            } else {
                JOptionPane.showMessageDialog(createAccount.this, "Password must be at least 5 characters long");
            }
        });
        c.gridy = 3;
        c.weightx = 1.0; // Horizontal padding
        c.insets = new Insets(10, 10, 10, 10); // Add spacing around the buttons
        panel.add(btnCreateAccount, c);

        // Back to login button
        btnBackToLogIn = new JButton("Back to Log in");
        btnBackToLogIn.addActionListener(e -> {
            setVisible(false);
            loginScreen.setVisible(true); // Show the login screen again
        });
        c.gridx = 0;
        panel.add(btnBackToLogIn, c);

        add(panel);
        setVisible(true);
    }

}
