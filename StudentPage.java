import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentPage extends JFrame {
private JTextField eventNameField, eventDateField, statusField, certificateLinkField,
cashPrizeField;
private JTextArea achievementArea;
private String studentUsername;

public StudentPage(String studentUsername) {
this.studentUsername = studentUsername;
setTitle("Student Achievements");

setSize(600, 500);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLayout(new BorderLayout());

// Input fields
eventNameField = new JTextField(15);
eventDateField = new JTextField(10);
statusField = new JTextField(10);
certificateLinkField = new JTextField(20);
cashPrizeField = new JTextField(10);

// Buttons for actions
JButton addAchievementButton = new JButton("Add Achievement");
JButton deleteAchievementButton = new JButton("Delete Achievement");

// Text area to display achievements
achievementArea = new JTextArea();
achievementArea.setEditable(false);
JScrollPane scrollPane = new JScrollPane(achievementArea);

// Add button actions
addAchievementButton.addActionListener(e -> addAchievement());
deleteAchievementButton.addActionListener(e -> deleteAchievement());

// Panel for input fields
JPanel topPanel = new JPanel();
topPanel.setLayout(new GridLayout(6, 2)); // Organize input fields
topPanel.add(new JLabel("Event Name:"));
topPanel.add(eventNameField);
topPanel.add(new JLabel("Event Date (YYYY-MM-DD):"));
topPanel.add(eventDateField);

topPanel.add(new JLabel("Status:"));
topPanel.add(statusField);
topPanel.add(new JLabel("Certificate Link:"));
topPanel.add(certificateLinkField);
topPanel.add(new JLabel("Cash Prize:"));
topPanel.add(cashPrizeField);
topPanel.add(addAchievementButton);
topPanel.add(deleteAchievementButton);

add(topPanel, BorderLayout.NORTH);
add(scrollPane, BorderLayout.CENTER);

loadAchievements();
setVisible(true);
setLocationRelativeTo(null);
}

// Method to add an achievement
private void addAchievement() {
String eventName = eventNameField.getText();
String eventDate = eventDateField.getText();
String status = statusField.getText();
String certificateLink = certificateLinkField.getText();
String cashPrize = cashPrizeField.getText();

if (eventName.isEmpty() || eventDate.isEmpty() || status.isEmpty() ||
certificateLink.isEmpty() || cashPrize.isEmpty()) {
JOptionPane.showMessageDialog(null, "All fields must be filled out!");
return;
}

try (Connection conn = DBConnection.getConnection()) {

String sql = "INSERT INTO ACHIEVEMENTS (USERNAME, EVENT_NAME,
EVENT_DATE, STATUS, CERTIFICATE_LINK, CASH_PRIZE) VALUES (?, ?, ?, ?, ?,
?)";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, studentUsername);
ps.setString(2, eventName);
ps.setString(3, eventDate);
ps.setString(4, status);
ps.setString(5, certificateLink);
ps.setString(6, cashPrize);
ps.executeUpdate();
loadAchievements();
// Clear input fields after insertion
eventNameField.setText("");
eventDateField.setText("");
statusField.setText("");
certificateLinkField.setText("");
cashPrizeField.setText("");
} catch (Exception ex) {
ex.printStackTrace();
}
}

// Method to delete an achievement
private void deleteAchievement() {
String eventName = eventNameField.getText();
if (eventName.isEmpty()) {
JOptionPane.showMessageDialog(null, "Enter the event name to delete.");
return;
}

try (Connection conn = DBConnection.getConnection()) {

String sql = "DELETE FROM ACHIEVEMENTS WHERE USERNAME=? AND
EVENT_NAME=?";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, studentUsername);
ps.setString(2, eventName);
ps.executeUpdate();
loadAchievements();
} catch (Exception ex) {
ex.printStackTrace();
}
}

// Method to load and display achievements
private void loadAchievements() {
achievementArea.setText("");
try (Connection conn = DBConnection.getConnection()) {
String sql = "SELECT EVENT_NAME, EVENT_DATE, STATUS,
CERTIFICATE_LINK, CASH_PRIZE FROM ACHIEVEMENTS WHERE
USERNAME=?";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, studentUsername);
ResultSet rs = ps.executeQuery();
while (rs.next()) {
String achievementInfo = "Event Name: " + rs.getString("EVENT_NAME") +
"\nDate: " + rs.getString("EVENT_DATE") +
"\nStatus: " + rs.getString("STATUS") +
"\nCertificate Link: " + rs.getString("CERTIFICATE_LINK") +
"\nCash Prize: " + rs.getString("CASH_PRIZE") + "\n\n";
achievementArea.append(achievementInfo);
}
} catch (Exception ex) {
ex.printStackTrace();

}
}
}