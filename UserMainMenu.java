import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserMainMenu {

    private JFrame frame;
    private JTable userTable;
    private DefaultTableModel tableModel;

    public void display() {
        frame = new JFrame("User Main Menu");
        
        frame.setSize(400, 100);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Add buttons for Register, Login, View Users, and Go Back
        JButton registerButton = new JButton("Register Account");
        JButton loginButton = new JButton("Account Login");
       
        JButton backButton = new JButton("Go Back");

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



        // Back Button Action
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new MainGUI().initialize(); // Go back to the main menu
            }
        });

        // Add buttons to the panel
        panel.add(registerButton);
        panel.add(loginButton);
       
        panel.add(backButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void showRegistrationForm() {
        JFrame registerFrame = new JFrame("Register Account");
        registerFrame.setSize(400, 400);
        registerFrame.setLocationRelativeTo(null);

        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridLayout(6, 2));

        // Add fields for registration
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

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String email = emailField.getText();

                // Validate inputs
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
            }
        });

        // Add components to the panel
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

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (validateLogin(username, password)) {
                    JOptionPane.showMessageDialog(loginFrame, "Login successful!");
                    loginFrame.dispose(); // Close the login frame
                
                    // Open the user dashboard or the next window
                    UserDashboard userDashboard = new UserDashboard();
                    userDashboard.display(); // Call the method to display the dashboard
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
                }

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



    private boolean usernameExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails[2].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void saveUserToFile(String firstName, String lastName, String username, String password, String email) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            // Write the user's details separated by |
            writer.write(firstName + "|" + lastName + "|" + username + "|" + password + "|" + email);
            writer.newLine(); // Add a new line for the next user
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private boolean validateLogin(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split("\\|"); // Escape the pipe character
                if (userDetails[2].equals(username) && userDetails[3].equals(password)) {
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
