import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class SignupPage extends JFrame {
private JTextField usernameField, roleField, mentorNameField;
private JPasswordField passwordField;
private JButton signupButton;

public SignupPage() {
// Set up the frame
setTitle("Signup Page");
setSize(400, 300);
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

// Role label and field
JLabel roleLabel = new JLabel("Role (Student/Admin):");
roleLabel.setBounds(50, 110, 150, 30);
add(roleLabel);

roleField = new JTextField();
roleField.setBounds(200, 110, 100, 30);
add(roleField);

// Mentor name label and field (optional)
JLabel mentorNameLabel = new JLabel("Mentor Name:");
mentorNameLabel.setBounds(50, 150, 100, 30);
add(mentorNameLabel);

mentorNameField = new JTextField();
mentorNameField.setBounds(150, 150, 150, 30);
add(mentorNameField);

// Signup button

signupButton = new JButton("Signup");
signupButton.setBounds(150, 200, 80, 30);
add(signupButton);

// Add signup button action
signupButton.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
signup();
}
});

// Set visible
setVisible(true);
setLocationRelativeTo(null);
}

private void signup() {
String username = usernameField.getText();
String password = new String(passwordField.getPassword());
String role = roleField.getText();
String mentorName = mentorNameField.getText();

if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
JOptionPane.showMessageDialog(null, "All fields must be filled.");
return;
}

try (Connection conn = DBConnection.getConnection()) {
String sql = "INSERT INTO USERS (USERNAME, PASSWORD, ROLE,
MENTOR_NAME) VALUES (?, ?, ?, ?)";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, username);

ps.setString(2, password);
ps.setString(3, role);
ps.setString(4, mentorName);
ps.executeUpdate();

JOptionPane.showMessageDialog(null, "User registered successfully!");

// Redirect back to Login Page
new LoginPage();
dispose(); // Close signup page
} catch (Exception ex) {

JOptionPane.showMessageDialog(null, "An error occurred during registration: " +
ex.getMessage());
}
}
}