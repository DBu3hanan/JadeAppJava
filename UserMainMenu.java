import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.geom.RoundRectangle2D;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserMainMenu {

    private JFrame frame;

    public void display() {
        frame = new JFrame("User Main Menu");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Set layout to BoxLayout

        // Add top label "User Main Menu"
        JLabel titleLabel = new JLabel("User Main Menu");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 20)); // Large bold font for the title
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label

        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Blank space at the top before the title
        panel.add(titleLabel);

        // Add buttons for Register, Login, and Go Back
        JButton registerButton = new JButton("Register Account");
        JButton loginButton = new JButton("Account Login");
        JButton backButton = new JButton("Go Back");

        // Apply green button styling
        styleButton(registerButton);
        styleButton(loginButton);
        styleButton(backButton);

        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(registerButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(loginButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(backButton);

        registerButton.addActionListener(e -> {
            try {
                showRegistrationForm();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage());
            }
        });

        loginButton.addActionListener(e -> login());

        backButton.addActionListener(e -> {
            frame.dispose();
            new MainGUI().initialize(); // Go back to the main menu
        });

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void showRegistrationForm() {
        JFrame registerFrame = new JFrame("Register Account");
        registerFrame.setSize(400, 400);
        registerFrame.setLocationRelativeTo(null);

        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridLayout(6, 2));

        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField();
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField();
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JButton registerButton = new JButton("Register");
        styleButton(registerButton);

        registerButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String email = emailField.getText();

            if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(registerFrame, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(registerFrame, "Enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (usernameExists(username)) {
                JOptionPane.showMessageDialog(registerFrame, "Username already exists. Choose another.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                saveUserToFile(firstName, lastName, username, password, email);
                JOptionPane.showMessageDialog(registerFrame, "Registration successful!");
                registerFrame.dispose();
            }
        });

        registerPanel.add(firstNameLabel);
        registerPanel.add(firstNameField);
        registerPanel.add(lastNameLabel);
        registerPanel.add(lastNameField);
        registerPanel.add(usernameLabel);
        registerPanel.add(usernameField);
        registerPanel.add(passwordLabel);
        registerPanel.add(passwordField);
        registerPanel.add(emailLabel);
        registerPanel.add(emailField);
        registerPanel.add(new JLabel());
        registerPanel.add(registerButton);

        registerFrame.getContentPane().add(registerPanel);
        registerFrame.setVisible(true);
    }

    private void login() {
        JFrame loginFrame = new JFrame("Account Login");
        loginFrame.setSize(400, 200);
        loginFrame.setLocationRelativeTo(null);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        styleButton(loginButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (validateLogin(username, password)) {
                JOptionPane.showMessageDialog(loginFrame, "Login successful!");
                loginFrame.dispose();
                new UserDashboard().display();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);

        loginFrame.getContentPane().add(loginPanel);
        loginFrame.setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(0x4CAF50));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        button.setPreferredSize(new Dimension(200, 40));
        button.setMaximumSize(new Dimension(200, 40));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new RoundBorder(30));
    }

    public class RoundBorder extends AbstractBorder {
        private int radius;

        public RoundBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.draw(new RoundRectangle2D.Float(x, y, width - 1, height - 1, radius, radius));
        }
    }

    private boolean usernameExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split("\\|");
                if (userDetails.length >= 3 && userDetails[2].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    

    private void saveUserToFile(String firstName, String lastName, String username, String password, String email) {
        File userFile = new File("users.txt");
        if (!userFile.exists()) {
            try {
                userFile.createNewFile(); // Create the file if it doesn't exist
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, true))) {
            writer.write(firstName + "|" + lastName + "|" + username + "|" + password + "|" + email);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    private boolean validateLogin(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split("\\|");
                if (userDetails.length >= 4 && userDetails[2].equals(username) && userDetails[3].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
