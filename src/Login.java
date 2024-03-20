import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

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

         class RoundedBorder implements Border {

            private int radius;
            RoundedBorder(int radius) {
                this.radius = radius;
            }
            public Insets getBorderInsets(Component c) {
                return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
            }
            public boolean isBorderOpaque() {
                return true;
            }
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.drawRoundRect(x, y, width-1, height-1, radius, radius);
            }
        }

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
        btnLogin.setBorder(new RoundedBorder(7));  // Adjust the radius as needed
        btnLogin.addActionListener(e -> {
            String u = txtUsername.getText();
            String p = Integer.toString(txtPassword.getText().hashCode());

            if (p.equals(DB.makeGETRequest("Login/" + u).getJSONObject(0).getString("password"))) {
                JOptionPane.showMessageDialog(Login.this, "Login successful!");
                String idUser = String.valueOf(DB.makeGETRequest("getID/" + u).getJSONObject(0).getInt("idUser"));
                String idCoop = String.valueOf(DB.makeGETRequest("getCoopID/" + idUser).getJSONObject(0).getInt("idCoop"));
                MainScreen mainScreen = new MainScreen(idCoop);
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
        btnCreate.setBorder(new RoundedBorder(5));
        btnCreate.addActionListener(e -> {
            // Create an instance of the CreateAccountPanel
            createAccount createAccountPanel = new createAccount(Login.this);
            createAccountPanel.setVisible(true);
            setVisible(false);
        });
        c.gridx = 1;
        panel.add(btnCreate, c);

        add(panel);
        setVisible(true);
    }
}

