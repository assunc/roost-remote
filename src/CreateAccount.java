import org.json.JSONArray;
import org.json.JSONException;

import javax.swing.*;
import java.awt.*;


public class CreateAccount extends JFrame {

    private JLabel lblUsername, lblPassword, lblConfirmPassword;
    private JTextField txtUsername;
    private JTextField txtPassword, txtConfirmPassword;
    private JButton btnCreateAccount, btnBackToLogIn;
    private Login loginScreen;

//    public String getUsername()
//    {
//        return txtUsername.getText();
//    }
//    public String getPassword(){
//        return txtPassword.getText();
//    }
    public CreateAccount(Login logIn) {
        super("Create Account");
        this.loginScreen = logIn;
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Username label
        lblUsername = new JLabel("Username:");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblUsername, gbc);

        // Username text field
        txtUsername = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtUsername, gbc);

        // Password label
        lblPassword = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblPassword, gbc);

        // Password field
        txtPassword = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        // Confirm password label
        lblConfirmPassword = new JLabel("Confirm Password:");
        gbc.gridx = -1;
        gbc.gridy = 2;
        panel.add(lblConfirmPassword, gbc);

        // Confirm password field
        txtConfirmPassword = new JPasswordField(20);
        gbc.gridy = 2;
        gbc.gridx = 1;
        panel.add(txtConfirmPassword, gbc);

        // Create account button
        btnCreateAccount = new JButton("Create Account");
        btnCreateAccount.addActionListener(e -> {
            String password = txtPassword.getText();
            String confirmPassword = txtConfirmPassword.getText();
            if (password.length() > 5) {
                if (password.equals(confirmPassword)) {

                    String username = txtUsername.getText();

                    try {
                        // Attempt to create the user
                        DB.makeGETRequest("CreateUser/" + username + "/" + txtPassword.getText().hashCode());
                        // Retrieve the user ID
                        int idUser = new JSONArray(DB.makeGETRequest("getID/" + username)).getJSONObject(0).getInt("idUser");

                        // Create coop for the user
                        DB.makeGETRequest("CreateCoop/" + idUser + "/0/0/0/6:00/19:00/1");
                        JOptionPane.showMessageDialog(CreateAccount.this, "Account successfully created!");
                        setVisible(false);
                        loginScreen.setVisible(true); // Show the login screen again
                    } catch (JSONException ex) {
                        // Username already in use
                        JOptionPane.showMessageDialog(CreateAccount.this, "This username is already being used. Please try another username.");
                    }

                } else {
                    JOptionPane.showMessageDialog(CreateAccount.this, "Passwords don't match!");
                }
            } else {
                JOptionPane.showMessageDialog(CreateAccount.this, "Password must be at least 5 characters long");
            }
        });

        gbc.gridy = 3;
        gbc.weightx = 1.0; // Horizontal padding
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing around the buttons
        panel.add(btnCreateAccount, gbc);

        // Back to login button
        btnBackToLogIn = new JButton("Back to Log in");
        btnBackToLogIn.addActionListener(e -> {
            setVisible(false);
            loginScreen.setVisible(true); // Show the login screen again
        });
        gbc.gridx = 0;
        panel.add(btnBackToLogIn, gbc);

        add(panel);
        setVisible(true);
    }
}
