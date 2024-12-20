import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminFunctions {

    // Method to show the Create User form
    public static void showCreateUserForm(JFrame parentFrame) {
        JFrame createUserFrame = new JFrame("Create User");
        createUserFrame.setSize(400, 350);
        createUserFrame.setLocationRelativeTo(parentFrame); // Center relative to parent frame

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2)); // Grid layout for labels and text fields

        // Add fields for user information
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

        // Create the "Create" button
        JButton createButton = new JButton("Create User");

        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                String email = emailField.getText().trim();

                // Validate input fields
                if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(createUserFrame, "Please fill in all fields.");
                    return;
                }

                // Validate email format
                if (!isValidEmail(email)) {
                    JOptionPane.showMessageDialog(createUserFrame, "Please enter a valid email address.");
                    return;
                }

                // Check if the username already exists
                if (usernameExists(username)) {
                    JOptionPane.showMessageDialog(createUserFrame, "Username already exists. Choose another.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if the email already exists
                if (emailExists(email)) {
                    JOptionPane.showMessageDialog(createUserFrame, "Email already exists. Choose another.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Save the user to a file
                saveUserToFile(firstName, lastName, username, password, email);

                // Close the Create User frame and show confirmation
                createUserFrame.dispose();
                JOptionPane.showMessageDialog(createUserFrame, "User created successfully!");
            }
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
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(new JLabel()); // Empty label for layout purposes
        panel.add(createButton);

        // Add the panel to the frame
        createUserFrame.getContentPane().add(panel);
        createUserFrame.setVisible(true);
    }

    // Method to validate the email format
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Method to save the user data to a file (using | as delimiter)
    public static void saveUserToFile(String firstName, String lastName, String username, String password, String email) {
        File userFile = new File("users.txt");

        // Ensure the file exists or create it if it doesn't
        try {
            if (!userFile.exists()) {
                userFile.createNewFile(); // Creates the file if it doesn't exist
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, true))) {
                // Format the user data to be saved with | delimiter
                String userData = firstName + "|" + lastName + "|" + username + "|" + password + "|" + email;

                // Write the user data to the file
                writer.write(userData);
                writer.newLine(); // New line for the next user
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving user to file: " + e.getMessage());
        }
    }

    // Method to check if a username already exists in the file
    private static boolean usernameExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split("\\|");
                if (userDetails.length >= 3 && userDetails[2].equals(username)) {
                    return true; // Username found
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Username not found
    }

    // Method to check if an email already exists in the file
    private static boolean emailExists(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split("\\|");
                if (userDetails.length >= 5 && userDetails[4].equals(email)) {
                    return true; // Email found
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Email not found
    }





    public static void showUpdateUserForm(JFrame parentFrame) {
    JFrame updateUserFrame = new JFrame("Update User");
    updateUserFrame.setSize(600, 400);
    updateUserFrame.setLocationRelativeTo(parentFrame);

    JPanel mainPanel = new JPanel(new BorderLayout());

    File userFile = new File("users.txt");
    DefaultListModel<String> userListModel = new DefaultListModel<>();

    // Load usernames into the JList
    if (userFile.exists()) {
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split("\\|"); // Format: FirstName|LastName|Username|Password|Email
                if (userDetails.length >= 5) {
                    userListModel.addElement(userDetails[2]); // Add Username to the list
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(updateUserFrame, "Error reading user file: " + e.getMessage());
            return;
        }
    } else {
        JOptionPane.showMessageDialog(updateUserFrame, "User file does not exist!");
        return;
    }

    JList<String> userList = new JList<>(userListModel);
    userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane userScrollPane = new JScrollPane(userList);

    // Panel for editing fields
    JPanel editPanel = new JPanel(new GridLayout(3, 2, 10, 10));
    JLabel firstNameLabel = new JLabel("New First Name:");
    JTextField firstNameField = new JTextField();

    JLabel lastNameLabel = new JLabel("New Last Name:");
    JTextField lastNameField = new JTextField();

    JLabel emailLabel = new JLabel("New Email:");
    JTextField emailField = new JTextField();

    editPanel.add(firstNameLabel);
    editPanel.add(firstNameField);
    editPanel.add(lastNameLabel);
    editPanel.add(lastNameField);
    editPanel.add(emailLabel);
    editPanel.add(emailField);

    JButton updateButton = new JButton("Update User");

    updateButton.addActionListener(e -> {
        String selectedUsername = userList.getSelectedValue();
        if (selectedUsername == null) {
            JOptionPane.showMessageDialog(updateUserFrame, "Please select a user to update.");
            return;
        }

        String newFirstName = firstNameField.getText().trim();
        String newLastName = lastNameField.getText().trim();
        String newEmail = emailField.getText().trim();

        if (newFirstName.isEmpty() && newLastName.isEmpty() && newEmail.isEmpty()) {
            JOptionPane.showMessageDialog(updateUserFrame, "Please enter at least one field to update.");
            return;
        }

        try {
            File tempFile = new File("users_temp.txt");
            try (BufferedReader reader = new BufferedReader(new FileReader(userFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                String line;
                boolean userFound = false;

                while ((line = reader.readLine()) != null) {
                    String[] userDetails = line.split("\\|"); // FirstName|LastName|Username|Password|Email
                    if (userDetails.length >= 5 && userDetails[2].equals(selectedUsername)) {
                        userFound = true;
                        if (!newFirstName.isEmpty()) {
                            userDetails[0] = newFirstName;
                        }
                        if (!newLastName.isEmpty()) {
                            userDetails[1] = newLastName;
                        }
                        if (!newEmail.isEmpty()) {
                            userDetails[4] = newEmail;
                        }
                        writer.write(String.join("|", userDetails) + "\n");
                    } else {
                        writer.write(line + "\n");
                    }
                }

                if (userFound) {
                    JOptionPane.showMessageDialog(updateUserFrame, "User updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(updateUserFrame, "Selected user not found.");
                }
            }

            if (!userFile.delete() || !tempFile.renameTo(userFile)) {
                throw new IOException("Failed to replace the original file.");
            }

            // Refresh the user list
            userListModel.clear();
            try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] userDetails = line.split("\\|");
                    if (userDetails.length >= 5) {
                        userListModel.addElement(userDetails[2]);
                    }
                }
            }

            // Clear fields after update
            firstNameField.setText("");
            lastNameField.setText("");
            emailField.setText("");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(updateUserFrame, "Error updating user: " + ex.getMessage());
        }
    });

    mainPanel.add(userScrollPane, BorderLayout.WEST); // User list on the left
    mainPanel.add(editPanel, BorderLayout.CENTER);   // Edit fields in the center
    mainPanel.add(updateButton, BorderLayout.SOUTH); // Update button at the bottom

    updateUserFrame.getContentPane().add(mainPanel);
    updateUserFrame.setVisible(true);
}
//endupdateuserfrom


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
            // Skip empty lines
            if (line.trim().isEmpty()) continue;

            // Split the line using '|' as the delimiter
            String[] userDetails = line.split("\\|");
            if (userDetails.length >= 5) {
                // Add only the first name and last name to the list
                userNames.add(userDetails[0].trim() + " " + userDetails[1].trim() + " " + userDetails[4].trim());

            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(viewUsersFrame, "Error reading users file: " + e.getMessage());
    }

    // Display the user names in the JTextArea
    if (userNames.isEmpty()) {
        userTextArea.setText("No users found.");
    } else {
        userTextArea.setText(String.join("\n", userNames));
    }

    // Add components to the panel
    panel.add(new JLabel("Users (Names & Email):"), BorderLayout.NORTH);
    panel.add(scrollPane, BorderLayout.CENTER);

    // Add the panel to the frame
    viewUsersFrame.getContentPane().add(panel);
    viewUsersFrame.setVisible(true);
}




}
