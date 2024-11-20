import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.border.AbstractBorder;
import java.awt.geom.RoundRectangle2D;

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
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center the window

        // Set up the main panel with BorderLayout to position label and buttons
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Add a blank space at the top of the panel (using a blank panel)
        JPanel spacerPanel = new JPanel();
        spacerPanel.setPreferredSize(new Dimension(0, 70)); // Adjust the height as needed (e.g., 30px for a tab-like effect)
        mainPanel.add(spacerPanel, BorderLayout.NORTH);

        // Add the "The Jamaican Association for Debating and Empowerment" label at the top
        JLabel titleLabel = new JLabel("<html><div style='width: 250px;'>The Jamaican Association for Debating and Empowerment</div></html>", JLabel.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24)); // Modern font and size
        titleLabel.setForeground(Color.DARK_GRAY);

        // Set the label size so it wraps text (you can adjust the width here)
        titleLabel.setPreferredSize(new Dimension(250, 50)); // Adjust the width as needed

        // Add the label below the spacer panel
        mainPanel.add(titleLabel, BorderLayout.NORTH); // Place it at the top of the panel



        // Create a panel for buttons (center part of the layout)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Create "User Main Menu" button
        JButton userButton = new JButton("User Main Menu");
        styleButton(userButton); // Apply modern button style
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUserMenu();
            }
        });

        // Create "Admin Main Menu" button
        JButton adminButton = new JButton("Admin Main Menu");
        styleButton(adminButton); // Apply modern button style
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAdminLogin();
            }
        });

        // Create "Exit" button
        JButton exitButton = new JButton("Exit");
        styleButton(exitButton); // Apply modern button style
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit the application
                int confirm = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to exit the program?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0); // Terminate the application
                }
            }
        });

        // Add buttons to the button panel
        buttonPanel.add(userButton);
        buttonPanel.add(adminButton);
        buttonPanel.add(exitButton); // Add the Exit button

        // Add the button panel to the center of the main panel
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add the main panel to the frame
        frame.getContentPane().add(mainPanel);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to style the buttons with modern features
    private void styleButton(JButton button) {
        button.setBackground(new Color(0x4CAF50)); // Green color
        button.setForeground(Color.WHITE); // White text
        button.setFont(new Font("Times New Roman", Font.PLAIN, 16)); // Modern font and size
        button.setPreferredSize(new Dimension(200, 40)); // Button size
        button.setFocusPainted(false); // Remove the focus border
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor on hover

        // Set rounded corners using custom border
        button.setBorder(new RoundBorder(30)); // Apply rounded corners with radius of 30
    }

    // Custom Border class to create rounded corners
    public class RoundBorder extends AbstractBorder {
        private int radius;

        public RoundBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(c.getBackground());
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.draw(new RoundRectangle2D.Float(x, y, width - 1, height - 1, radius, radius));
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 1, radius + 1);
        }
    }

    // Method to open the User Main Menu
    private void openUserMenu() {
        // Dispose the current frame and open the User Menu
        frame.dispose();
        UserMainMenu userMenu = new UserMainMenu();
        userMenu.display();
    }

    // Method to open the Admin Login form
    private void openAdminLogin() {
        // Create the Admin Login form
        JFrame loginFrame = new JFrame("Admin Login");
        loginFrame.setSize(300, 200);
        loginFrame.setLocationRelativeTo(frame);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        JButton backButton = new JButton("Back");

        // Add components to the panel
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(backButton);

        // Apply the button style to the login button
        styleButton(loginButton);

        // Add the panel to the frame
        loginFrame.getContentPane().add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();

                // Check the login credentials
                if (validateAdminCredentials(username, new String(password))) {
                    // If credentials are correct, open the Admin Main Menu
                    loginFrame.dispose();
                    openAdminMenu();
                } else {
                    // Show an error message if login fails
                    JOptionPane.showMessageDialog(loginFrame, "Invalid credentials, please try again.", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose();
                
            }
        });

        // Make the login frame visible
        loginFrame.setVisible(true);
    }

    // Method to validate admin credentials from the "admindocument.txt" file
    private boolean validateAdminCredentials(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("admindocument.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming the file contains the credentials in the format:
                // username,password
                String[] credentials = line.split(",");
                if (credentials.length == 2) {
                    String fileUsername = credentials[0];
                    String filePassword = credentials[1];
                    if (fileUsername.equals(username) && filePassword.equals(password)) {
                        return true; // Valid credentials found
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Invalid credentials
    }

    // Method to open the Admin Main Menu
    private void openAdminMenu() {
        // Dispose the current frame and open the Admin Menu
        frame.dispose();
        AdminMainMenu adminMenu = new AdminMainMenu();
        adminMenu.display();
    }
}
