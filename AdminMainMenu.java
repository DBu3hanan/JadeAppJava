import javax.swing.*;
import java.awt.*;

public class AdminMainMenu {

    private JFrame frame;

    public void display() {
        frame = new JFrame("Admin Main Menu");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Add components for the Admin Menu (e.g., View Users, Settings)
        JButton viewUsersButton = new JButton("View All Users");
        JButton settingsButton = new JButton("Admin Settings");

        // Add action listeners for buttons (Implement these actions as needed)
        viewUsersButton.addActionListener(e -> {
            System.out.println("View Users clicked");
        });

        settingsButton.addActionListener(e -> {
            System.out.println("Settings clicked");
        });

        panel.add(viewUsersButton);
        panel.add(settingsButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
