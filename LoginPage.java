import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame {
private JTextField usernameField;
private JPasswordField passwordField;
private JButton loginButton, signupButton;
private Connection conn;

public LoginPage() {
// Set up the frame
setTitle("Login Page");
setSize(400, 200);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLayout(null);

// Username label and field
JLabel usernameLabel = new JLabel("Username:");
usernameLabel.setBounds(50, 30, 80, 30);
add(usernameLabel);

usernameField = new JTextField();
usernameField.setBounds(150, 30, 150, 30);
add(usernameField);

// Password label and field
JLabel passwordLabel = new JLabel("Password:");
passwordLabel.setBounds(50, 70, 80, 30);

add(passwordLabel);

passwordField = new JPasswordField();
passwordField.setBounds(150, 70, 150, 30);
add(passwordField);

// Login button
loginButton = new JButton("Login");
loginButton.setBounds(150, 110, 80, 30);
add(loginButton);

// Signup button
signupButton = new JButton("Signup");
signupButton.setBounds(240, 110, 80, 30);
add(signupButton);

// Add login button action
loginButton.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
login();
}
});

// Add signup button action
signupButton.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
new SignupPage(); // Open signup page
dispose(); // Close login page
}
});

// Set visible
setVisible(true);
setLocationRelativeTo(null);
}

private void login() {
String username = usernameField.getText();
String password = new String(passwordField.getPassword());

try {
conn = DBConnection.getConnection(); // Establish a connection
String sql = "SELECT * FROM USERS WHERE USERNAME = ? AND
PASSWORD = ?";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, username);
ps.setString(2, password);
ResultSet rs = ps.executeQuery();

if (rs.next()) {
String role = rs.getString("ROLE");

if (role.equalsIgnoreCase("Student")) {
new StudentPage(username); // Redirect to Student Page
} else if (role.equalsIgnoreCase("Admin")) {
new AdminPage(); // Redirect to Admin Page
}
dispose(); // Close login page
} else {
JOptionPane.showMessageDialog(null, "Invalid username or password");
}
} catch (Exception ex) {

ex.printStackTrace();
}
}

public static void main(String[] args) {
new LoginPage();
}
}