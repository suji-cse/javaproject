import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminPage extends JFrame {
private JTextArea achievementArea;

public AdminPage() {
setTitle("Admin - View All Achievements");
setSize(600, 500);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLayout(new BorderLayout());

achievementArea = new JTextArea();
achievementArea.setEditable(false);
JScrollPane scrollPane = new JScrollPane(achievementArea);

add(scrollPane, BorderLayout.CENTER);
loadAllAchievements();
setVisible(true);
setLocationRelativeTo(null);
}

// Method to load all achievements from the database
private void loadAllAchievements() {
achievementArea.setText("");
try (Connection conn = DBConnection.getConnection()) {

String sql = "SELECT USERNAME, EVENT_NAME, EVENT_DATE, STATUS,
CERTIFICATE_LINK, CASH_PRIZE FROM ACHIEVEMENTS";
PreparedStatement ps = conn.prepareStatement(sql);
ResultSet rs = ps.executeQuery();
while (rs.next()) {
String username = rs.getString("USERNAME");
String eventName = rs.getString("EVENT_NAME");
String eventDate = rs.getString("EVENT_DATE");
String status = rs.getString("STATUS");
String certificateLink = rs.getString("CERTIFICATE_LINK");
String cashPrize = rs.getString("CASH_PRIZE");String achievementInfo =
"Username: " + username +
"\nEvent: " + eventName +
"\nDate: " + eventDate +
"\nStatus: " + status +
"\nCertificate: " + certificateLink +
"\nCash Prize: " + cashPrize + "\n\n";
achievementArea.append(achievementInfo);
}
} catch (Exception ex) {
ex.printStackTrace();
}
}
}