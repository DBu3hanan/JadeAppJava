import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminFunctions {

    // Method to save user data to a file
    public static void saveUserToFile(String firstName, String lastName, String username, String password) {
        try {
            // Open the file in append mode
            BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true));

            // Write the user's details to the file
            writer.write(firstName + "|" + lastName + "|" + username + "|" + password);
            writer.newLine(); // Add a new line for the next user
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to show the Create User form
    public static void showCreateUserForm(JFrame parentFrame) {
        JFrame createUserFrame = new JFrame("Create User");
        createUserFrame.setSize(400, 300);
        createUserFrame.setLocationRelativeTo(parentFrame); // Center relative to parent frame

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2)); // Grid layout for labels and text fields

        // Add fields for user information
        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField();
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField();
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        // Create the "Create" button
        JButton createButton = new JButton("Create User");

        createButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Save the user to a file
            saveUserToFile(firstName, lastName, username, password);

            // Close the Create User frame and show confirmation
            createUserFrame.dispose();
            JOptionPane.showMessageDialog(createUserFrame, "User created successfully!");
        });

        // Add components to the panel
        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(lastNameLabel);
        panel.add(lastNameField);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty label for layout purposes
        panel.add(createButton);

        // Add the panel to the frame
        createUserFrame.getContentPane().add(panel);
        createUserFrame.setVisible(true);
    }

// Method to show the users in a JTextArea
public static void showUsersInTextArea(JFrame parentFrame) {
    // Create a new frame to display the user details
    JFrame viewUsersFrame = new JFrame("View Users");
    viewUsersFrame.setSize(400, 300);
    viewUsersFrame.setLocationRelativeTo(parentFrame);

    // Create a panel to hold the components
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());

    // Create a JTextArea to display the users
    JTextArea userTextArea = new JTextArea();
    userTextArea.setEditable(false); // Make it read-only
    JScrollPane scrollPane = new JScrollPane(userTextArea);

    // Read users from the file and show only first and last names
    List<String> userNames = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            // Use "\\|" to split on the pipe character
            String[] userDetails = line.split("\\|");
            if (userDetails.length >= 2) {
                // Add only the first name and last name to the list
                userNames.add(userDetails[0] + " " + userDetails[1]);
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(viewUsersFrame, "Error reading users file: " + e.getMessage());
    }

    // Display the user names in the JTextArea
    userTextArea.setText(String.join("\n", userNames));

    // Add components to the panel
    panel.add(new JLabel("Users (First Name and Last Name):"), BorderLayout.NORTH);
    panel.add(scrollPane, BorderLayout.CENTER);

    // Add the panel to the frame
    viewUsersFrame.getContentPane().add(panel);
    viewUsersFrame.setVisible(true);
}



    // Method to create and save a resource
    public static void showCreateResourceForm(JFrame parentFrame) {
        // Create a new frame to input the resource details
        JFrame createResourceFrame = new JFrame("Create Resource");
        createResourceFrame.setSize(400, 400);
        createResourceFrame.setLocationRelativeTo(parentFrame); // Center relative to parent frame
    
        // Create a main panel to hold all components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
    
        // Create a JTextArea for entering the resource content
        JTextArea resourceTextArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(resourceTextArea);
    
        // Create a sub-panel for the resource name input and button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
    
        JLabel resourceNameLabel = new JLabel("Enter Resource Name (without .txt):");
        JTextField resourceNameField = new JTextField();
        resourceNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Ensure it stretches horizontally
    
        JButton addResourceButton = new JButton("Add Resource");
    
        // Add components to the bottom panel
        bottomPanel.add(resourceNameLabel);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Spacer
        bottomPanel.add(resourceNameField);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        bottomPanel.add(addResourceButton);
    
        // Action listener for the "Add Resource" button
        addResourceButton.addActionListener(e -> {
            // Get the file name from the JTextField
            String resourceName = resourceNameField.getText().trim();
            if (!resourceName.isEmpty()) {
                // Make sure the Resources directory exists
                File resourcesDir = new File("Resources");
                if (!resourcesDir.exists()) {
                    resourcesDir.mkdirs(); // Create the directory if it doesn't exist
                }
    
                // Create the file in the Resources folder
                File resourceFile = new File(resourcesDir, resourceName + ".txt");
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(resourceFile))) {
                    // Write the resource content into the file
                    writer.write(resourceTextArea.getText());
                    JOptionPane.showMessageDialog(createResourceFrame, "Resource saved successfully!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(createResourceFrame, "Error saving resource: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(createResourceFrame, "Please enter a valid resource name.");
            }
        });
    
        // Add components to the main panel
        mainPanel.add(new JLabel("Enter Resource Content:"), BorderLayout.NORTH); // Label at the top
        mainPanel.add(scrollPane, BorderLayout.CENTER); // Text area in the center
        mainPanel.add(bottomPanel, BorderLayout.SOUTH); // Resource name and button at the bottom
    
        // Add the main panel to the frame
        createResourceFrame.getContentPane().add(mainPanel);
        createResourceFrame.setVisible(true);
    }

    //UPDATE USER FORM
    
    public static void showUpdateUserForm(JFrame parentFrame) {
        // Create a new frame for updating user details
        JFrame updateUserFrame = new JFrame("Update User");
        updateUserFrame.setSize(500, 400);
        updateUserFrame.setLocationRelativeTo(parentFrame); // Center relative to parent frame
    
        // Panel for the update user form
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
    
        // Create a text area to display current users
        JTextArea userDisplayArea = new JTextArea();
        userDisplayArea.setEditable(false); // Make it read-only
        JScrollPane scrollPane = new JScrollPane(userDisplayArea);
    
        // Load users from the file and display them
        File userFile = new File("users.txt");
        if (userFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
                String line;
                StringBuilder content = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                userDisplayArea.setText(content.toString());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(updateUserFrame, "Error reading user file: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(updateUserFrame, "User file does not exist!");
            return;
        }
    
        // Panel for selecting and editing user details
        JPanel editPanel = new JPanel();
        editPanel.setLayout(new GridLayout(3, 2, 10, 10));
    
        // Fields for user editing
        JLabel usernameLabel = new JLabel("Enter Username:");
        JTextField usernameField = new JTextField();
    
        JLabel firstNameLabel = new JLabel("New First Name:");
        JTextField firstNameField = new JTextField();
    
        JLabel lastNameLabel = new JLabel("New Last Name:");
        JTextField lastNameField = new JTextField();
    
        // Add components to the edit panel
        editPanel.add(usernameLabel);
        editPanel.add(usernameField);
        editPanel.add(firstNameLabel);
        editPanel.add(firstNameField);
        editPanel.add(lastNameLabel);
        editPanel.add(lastNameField);
    
        // Button to save updates
        JButton saveButton = new JButton("Update User");
    
        saveButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String newFirstName = firstNameField.getText().trim();
            String newLastName = lastNameField.getText().trim();
    
            if (username.isEmpty() || (newFirstName.isEmpty() && newLastName.isEmpty())) {
                JOptionPane.showMessageDialog(updateUserFrame, "Please enter a valid username and at least one field to update.");
                return;
            }
    
            try {
                // Read the file and update the details
                File tempFile = new File("users_temp.txt");
                try (BufferedReader reader = new BufferedReader(new FileReader(userFile));
                     BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
    
                    String line;
                    boolean userFound = false;
                    while ((line = reader.readLine()) != null) {
                        String[] userDetails = line.split(","); // Format: FirstName,LastName,Username,Password
                        if (userDetails[2].equals(username)) { // Check the username
                            userFound = true;
                            if (!newFirstName.isEmpty()) {
                                userDetails[0] = newFirstName; // Update First Name
                            }
                            if (!newLastName.isEmpty()) {
                                userDetails[1] = newLastName; // Update Last Name
                            }
                            writer.write(String.join(",", userDetails) + "\n");
                        } else {
                            writer.write(line + "\n");
                        }
                    }
    
                    if (userFound) {
                        JOptionPane.showMessageDialog(updateUserFrame, "User updated successfully!");
                    } else {
                        JOptionPane.showMessageDialog(updateUserFrame, "Username not found.");
                    }
                }
    
                // Replace the original file with the updated one
                if (!userFile.delete() || !tempFile.renameTo(userFile)) {
                    throw new IOException("Failed to replace the original file.");
                }
    
                // Refresh the display area
                userDisplayArea.setText("");
                try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
                    String line;
                    StringBuilder content = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    userDisplayArea.setText(content.toString());
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(updateUserFrame, "Error updating user: " + ex.getMessage());
            }
        });
    
        // Add components to the main panel
        panel.add(scrollPane, BorderLayout.CENTER); // User display area
        panel.add(editPanel, BorderLayout.NORTH); // Edit fields
        panel.add(saveButton, BorderLayout.SOUTH); // Save button
    
        // Add panel to the frame
        updateUserFrame.getContentPane().add(panel);
        updateUserFrame.setVisible(true);
    }
    

}
