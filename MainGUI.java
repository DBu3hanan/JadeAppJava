import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI {

    private JFrame frame;
    private JPanel mainPanel;

    public static void main(String[] args) {
        // Create and display the main GUI window
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainGUI().initialize();
            }
        });
    }

    // Initialize the GUI components
    public void initialize() {
        // Set up the main frame
        frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null); // Center the window

        // Set up the main panel with a FlowLayout
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        // Create "User Main Menu" button
        JButton userButton = new JButton("User Main Menu");
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUserMenu();
            }
        });

        // Create "Admin Main Menu" button
        JButton adminButton = new JButton("Admin Main Menu");
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAdminMenu();
            }
        });

        // Add buttons to the main panel
        mainPanel.add(userButton);
        mainPanel.add(adminButton);

        // Add the panel to the frame
        frame.getContentPane().add(mainPanel);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to open the User Main Menu
    private void openUserMenu() {
        // Dispose the current frame and open the User Menu
        frame.dispose();
        UserMainMenu userMenu = new UserMainMenu();
        userMenu.display();
    }

    // Method to open the Admin Main Menu
    private void openAdminMenu() {
        // Dispose the current frame and open the Admin Menu
        frame.dispose();
        AdminMainMenu adminMenu = new AdminMainMenu();
        adminMenu.display();
    }
}
