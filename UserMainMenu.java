import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserMainMenu {

    private JFrame frame;

    public void display() {
        frame = new JFrame("User Main Menu");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Add buttons for Register and Login
        JButton registerButton = new JButton("Register Account");
        JButton loginButton = new JButton("Login");

        // Register Button Action
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegistrationForm();
            }
        });

        // Login Button Action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        panel.add(registerButton);
        panel.add(loginButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    // Method to show the registration form
    private void showRegistrationForm() {
        JFrame registerFrame = new JFrame("Register Account");
        registerFrame.setSize(400, 400);
        registerFrame.setLocationRelativeTo(null);

        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridLayout(5, 2)); // Grid layout for labels and text fields

        // Add fields for registration
        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField();
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField();
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        // Register button
        JButton registerButton = new JButton("Register");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Save the user to file
                saveUserToFile(firstName, lastName, username, password);

                // Close the register frame and go back to the main menu
                registerFrame.dispose();
                JOptionPane.showMessageDialog(registerFrame, "Account created successfully!");
            }
        });

        // Add components to the register panel
        registerPanel.add(firstNameLabel);
        registerPanel.add(firstNameField);
        registerPanel.add(lastNameLabel);
        registerPanel.add(lastNameField);
        registerPanel.add(usernameLabel);
        registerPanel.add(usernameField);
        registerPanel.add(passwordLabel);
        registerPanel.add(passwordField);
        registerPanel.add(new JLabel()); // Empty label for layout
        registerPanel.add(registerButton);

        registerFrame.getContentPane().add(registerPanel);
        registerFrame.setVisible(true);
    }

    // Method to save the user to a file
    private void saveUserToFile(String firstName, String lastName, String username, String password) {
        try {
            // Open the file in append mode
            BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true));

            // Write the user's details to the file
            writer.write(firstName + "," + lastName + "," + username + "," + password);
            writer.newLine(); // Add a new line for the next user
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method for user login
    private void login() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(400, 300);
        loginFrame.setLocationRelativeTo(null);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2));

        // Add fields for login
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        // Login button
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
        
                // Validate login credentials (you can expand this method to check against stored user data)
                if (validateLogin(username, password)) {
                    JOptionPane.showMessageDialog(loginFrame, "Login successful!");
                    
                    // Close the current login frame
                    loginFrame.dispose();
        
                    // Open the User Dashboard after successful login
                    UserDashboard userDashboard = new UserDashboard();  // Create an instance of UserDashboard
                    userDashboard.display();  // Show the User Dashboard
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid credentials. Please try again.");
                }
            }
        });
        

        // Add components to the login panel
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel()); // Empty label for layout
        loginPanel.add(loginButton);

        loginFrame.getContentPane().add(loginPanel);
        loginFrame.setVisible(true);
    }

    // Method to validate user login from the file
    private boolean validateLogin(String username, String password) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(",");
                String storedUsername = userDetails[2];
                String storedPassword = userDetails[3];

                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    return true; // User found and credentials match
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Username or password not found
    }
}
