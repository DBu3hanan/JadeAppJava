import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class UserDashboard {

    private JFrame frame;


    public void display() {
        
        // Create the main frame
        frame = new JFrame("User Dashboard");
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); // Use BorderLayout for better placement

        // Create the title label with HTML for multiline text
        JLabel titleLabel = new JLabel("<html><center>User Dashboard<br></center></html>", JLabel.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        frame.add(titleLabel, BorderLayout.NORTH); // Add the label to the top (NORTH)


        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 rows with spacing between buttons

        // Add buttons for each action
        JButton readGuidelinesButton = new JButton("Read Guidelines");
        JButton editUserInfoButton = new JButton("Edit User Information");
        JButton viewResourcesButton = new JButton("View Resources");
        JButton giveFeedbackButton = new JButton("Give Feedback");
        JButton viewMyDetailsButton = new JButton("View My Details");
        JButton deleteAccountButton = new JButton("Delete My Account");
        JButton gobackButton = new JButton("Main Menu");

        // Add action listeners for each button
        readGuidelinesButton.addActionListener(e -> showGuidelines());
       
        editUserInfoButton.addActionListener(e -> editUser(frame));

        viewResourcesButton.addActionListener(e -> viewResources());
        giveFeedbackButton.addActionListener(e -> giveFeedback());
        viewMyDetailsButton.addActionListener(e -> viewMyDetails());
        deleteAccountButton.addActionListener(e -> deleteMyAccount(frame));
        

        // Action listener for the "Go Back" button
        gobackButton.addActionListener(e -> {
            frame.dispose(); // Close this window
            new MainGUI().initialize(); // Replace with your MainGUI implementation
        });

        // Add buttons to the button panel
        buttonPanel.add(readGuidelinesButton);
        buttonPanel.add(editUserInfoButton);
        buttonPanel.add(viewResourcesButton);
        buttonPanel.add(giveFeedbackButton);
        buttonPanel.add(deleteAccountButton);
        buttonPanel.add(viewMyDetailsButton);
        buttonPanel.add(gobackButton);
        

        // Add the button panel to the frame (CENTER)
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to show the guidelines
    private void showGuidelines() {
        
        // Create a new JFrame for displaying the guidelines
        JFrame guidelinesFrame = new JFrame("Guidelines");
        guidelinesFrame.setSize(700, 500); // Adjust the size to fit the guidelines text
        guidelinesFrame.setLocationRelativeTo(null); // Center the frame on the screen
    
        // Create a JTextArea to hold the guidelines text
        JTextArea textArea = new JTextArea();
        
        // Set the guidelines text with proper formatting
        String guidelinesText = "\t\t----SYSTEM GUIDELINES----\n"
                + "----WELCOME TO THE GUIDELINES SCREEN OF THE JADE APPLICATION----\n\n"
                + "User Account Management:\n"
                + "1. Register Account: To create a new account, you can use the Register Account option.\n"
                + "2. Login: To log in to your account, you can use the Login option.\n"
                + "3. Exit: To exit the application, you can use the Exit option.\n"
                + "4. Users can create, update, and view their accounts.\n\n"
                + "User Support:\n"
                + "4. Read Guidelines: To read the guidelines, you can use the Read Guidelines option.\n"
                + "5. Edit User Information: Users can update their account information, such as username and password.\n"
                + "6. View Resources: Users can access various resources that are available in the application.\n"
                + "7. Give Feedback: Users can provide feedback on the application for improvement.\n\n"
                + "Application Features:\n"
                + "8. User Dashboard: Once logged in, users can access their dashboard with personalized options.\n"
                + "9. Resources Access: A variety of resources are available for users based on their roles and permissions.\n"
                + "10. Feedback System: The application allows users to submit feedback for continuous improvement.\n\n"
                + "Security and Privacy:\n"
                + "11. Password Security: Ensure that your password is strong and not shared with others.\n"
                + "12. Session Management: Always log out when you are done using the application, especially on public devices.\n"
                + "13. Data Protection: Your personal information is stored securely, and access is protected by encryption.\n"
                + "14. Reporting Issues: If you encounter any issues, please report them to the support team through the feedback system.\n";

    
        // Set the text into the JTextArea and make it non-editable
        textArea.setText(guidelinesText);
        textArea.setEditable(false);
    
        // Add the JTextArea into a JScrollPane for better navigation
        JScrollPane scrollPane = new JScrollPane(textArea);
    
        // Add the JScrollPane to the JFrame
        guidelinesFrame.add(scrollPane);
    
        // Set the frame to be visible
        guidelinesFrame.setVisible(true);
    }
















    public static void editUser(JFrame parentFrame) {
        // Create a new frame for editing user details
        JFrame editUserFrame = new JFrame("Edit Your Details");
        editUserFrame.setSize(400, 350);
        editUserFrame.setLocationRelativeTo(parentFrame); // Center relative to parent frame
    
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        // Create fields to capture user information
        JTextField currentUsernameField = new JTextField();
        JTextField newFirstNameField = new JTextField();
        JTextField newLastNameField = new JTextField();
        JPasswordField newPasswordField = new JPasswordField();
        JTextField newEmailField = new JTextField();
    
        // Add labels and fields to the panel
        panel.add(new JLabel("Enter Your Current Username:"));
        panel.add(currentUsernameField);
        panel.add(new JLabel("New First Name (Leave blank if unchanged):"));
        panel.add(newFirstNameField);
        panel.add(new JLabel("New Last Name (Leave blank if unchanged):"));
        panel.add(newLastNameField);
        panel.add(new JLabel("New Password (Leave blank if unchanged):"));
        panel.add(newPasswordField);
        panel.add(new JLabel("New Email (Leave blank if unchanged):"));
        panel.add(newEmailField);
    
        // Create a button to save changes
        JButton saveChangesButton = new JButton("Save Changes");
        saveChangesButton.addActionListener(e -> {
            String username = currentUsernameField.getText().trim();
            String newFirstName = newFirstNameField.getText().trim();
            String newLastName = newLastNameField.getText().trim();
            String newPassword = new String(newPasswordField.getPassword()).trim();
            String newEmail = newEmailField.getText().trim();
    
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(editUserFrame, "Username is required!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Only validate non-empty fields
                if (!newPassword.isEmpty() && newPassword.length() < 6) {
                    JOptionPane.showMessageDialog(editUserFrame, "Password must be at least 6 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!newEmail.isEmpty() && !newEmail.contains("@")) {
                    JOptionPane.showMessageDialog(editUserFrame, "Enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (updateUserDetails(username, newFirstName, newLastName, newPassword, newEmail)) {
                        JOptionPane.showMessageDialog(editUserFrame, "Your details have been updated successfully!");
                        editUserFrame.dispose(); // Close the edit frame
                    } else {
                        JOptionPane.showMessageDialog(editUserFrame, "Username not found. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    
        panel.add(saveChangesButton);
    
        editUserFrame.getContentPane().add(panel);
        editUserFrame.setVisible(true);
    }
    






    private static boolean updateUserDetails(String username, String newFirstName, String newLastName, String newPassword, String newEmail) {
        File userFile = new File("users.txt");
        if (!userFile.exists()) {
            return false; // File not found
        }
    
        List<String> updatedLines = new ArrayList<>();
        boolean userFound = false;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split("\\|"); // Split by '|'
                if (userDetails.length == 5 && userDetails[2].equals(username)) {
                    // Update only the fields that are not empty
                    if (!newFirstName.isEmpty()) {
                        userDetails[0] = newFirstName;
                    }
                    if (!newLastName.isEmpty()) {
                        userDetails[1] = newLastName;
                    }
                    if (!newPassword.isEmpty()) {
                        userDetails[3] = newPassword;
                    }
                    if (!newEmail.isEmpty()) {
                        userDetails[4] = newEmail;
                    }
                    userFound = true;
                }
                updatedLines.add(String.join("|", userDetails)); // Rebuild line with updated details
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    
        if (userFound) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
                for (String updatedLine : updatedLines) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    
        return userFound;
    }
    




















// Method to view resources
private void viewResources() {
    // Create a new frame for viewing resources
    JFrame resourcesFrame = new JFrame("View Resources");
    resourcesFrame.setSize(500, 400);
    resourcesFrame.setLocationRelativeTo(null); // Center relative to parent frame

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Stack buttons vertically

    // The directory where resources are stored
    File resourcesDir = new File("Resources");
    if (!resourcesDir.exists() || !resourcesDir.isDirectory()) {
        // If the directory doesn't exist, show a message
        JLabel errorLabel = new JLabel("Resources folder not found.");
        panel.add(errorLabel);
    } else {
        // Fetch all .txt files in the "Resources" directory
        File[] resourceFiles = resourcesDir.listFiles((dir, name) -> name.endsWith(".txt"));
        if (resourceFiles == null || resourceFiles.length == 0) {
            // If no resources are found, show a message
            JLabel noResourcesLabel = new JLabel("No resources found.");
            panel.add(noResourcesLabel);
        } else {
            // Create a button for each resource file
            for (File resourceFile : resourceFiles) {
                JButton resourceButton = new JButton(resourceFile.getName());
                
                // Add action listener to open resource content when clicked
                resourceButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Display the content of the clicked resource
                        displayResourceContent(resourceFile);
                    }
                });

                // Add the button to the panel
                panel.add(resourceButton);
            }
        }
    }

    // Make the panel scrollable if there are many resources
    JScrollPane scrollPane = new JScrollPane(panel);
    resourcesFrame.getContentPane().add(scrollPane);
    resourcesFrame.setVisible(true);
}

// Method to display the content of a resource in a read-only JTextArea
private void displayResourceContent(File resourceFile) {
    // Create a new frame to display the resource content
    JFrame resourceFrame = new JFrame(resourceFile.getName());
    resourceFrame.setSize(600, 400);
    resourceFrame.setLocationRelativeTo(null); // Center the window

    // Create a JTextArea to display the resource content
    JTextArea textArea = new JTextArea();
    textArea.setEditable(false); // Make it read-only
    textArea.setCaretPosition(0); // Scroll to the top of the text area

    // Read the content of the resource file
    try (BufferedReader reader = new BufferedReader(new FileReader(resourceFile))) {
        String line;
        while ((line = reader.readLine()) != null) {
            textArea.append(line + "\n"); // Append each line to the text area
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(resourceFrame, "Error reading the resource file.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Add the text area to a scroll pane to make it scrollable
    JScrollPane scrollPane = new JScrollPane(textArea);
    resourceFrame.add(scrollPane);

    // Show the frame
    resourceFrame.setVisible(true);
}









    

    // Method to give feedback
    // Method to display the feedback form
    private void giveFeedback() {
        JFrame feedbackFrame = new JFrame("Give Feedback");
        feedbackFrame.setSize(400, 300);
        feedbackFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel feedbackLabel = new JLabel("Enter your feedback:");
        JTextArea feedbackTextArea = new JTextArea();
        JButton submitButton = new JButton("Submit Feedback");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String feedback = feedbackTextArea.getText();
                // Save feedback with numbering
                saveFeedback(feedback);
                JOptionPane.showMessageDialog(feedbackFrame, "Feedback submitted!");
                feedbackFrame.dispose();
            }
        });

        panel.add(feedbackLabel, BorderLayout.NORTH);
        panel.add(new JScrollPane(feedbackTextArea), BorderLayout.CENTER);
        panel.add(submitButton, BorderLayout.SOUTH);

        feedbackFrame.add(panel);
        feedbackFrame.setVisible(true);
    }













    // Method to save feedback with a number in the file
    private void saveFeedback(String feedback) {
        // File where feedback will be stored
        File feedbackFile = new File("feedback.txt");

        // Create a variable to keep track of feedback number
        int feedbackNumber = 1;

        // Check if the feedback file exists and read the existing feedback to determine the next number
        if (feedbackFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(feedbackFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Increment the feedback number for each existing feedback
                    feedbackNumber++;
                }
            } catch (IOException e) {
                System.err.println("Error reading the feedback file: " + e.getMessage());
            }
        }

        // Now save the new feedback with the next feedback number
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(feedbackFile, true))) {
            // Write the feedback with its number at the beginning
            writer.write("Feedback #" + feedbackNumber + ": " + feedback);
            writer.newLine();  // Move to the next line for the next feedback entry
            System.out.println("Feedback #" + feedbackNumber + " submitted: " + feedback);
        } catch (IOException e) {
            System.err.println("Error saving feedback: " + e.getMessage());
        }
    }




// Method to view user details
private void viewMyDetails() {
    String currentUsername = JOptionPane.showInputDialog(frame, "Enter your username to view details:", 
                                                        "View My Details", JOptionPane.QUESTION_MESSAGE);
    if (currentUsername == null || currentUsername.trim().isEmpty()) {
        JOptionPane.showMessageDialog(frame, "Username cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    File userFile = new File("users.txt");
    if (!userFile.exists()) {
        JOptionPane.showMessageDialog(frame, "User data file not found!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] userDetails = line.split("\\|"); // Split by '|'
            if (userDetails.length == 5 && userDetails[2].equals(currentUsername)) {
                // Create a new frame to display the user's details
                JFrame detailsFrame = new JFrame("My Details");
                detailsFrame.setSize(400, 300);
                detailsFrame.setLocationRelativeTo(frame);

                // Create a JTextArea to show the user's details
                JTextArea detailsArea = new JTextArea();
                detailsArea.setEditable(false);
                detailsArea.setText("First Name: " + userDetails[0] + "\n" +
                                    "Last Name: " + userDetails[1] + "\n" +
                                    "Username: " + userDetails[2] + "\n" +
                                    "Email: " + userDetails[4]); // Exclude password

                // Add the JTextArea to a scroll pane and set it in the frame
                detailsFrame.add(new JScrollPane(detailsArea));
                detailsFrame.setVisible(true);
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(frame, "Error reading user data file!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}




// Method to delete the user's account
private void deleteMyAccount(JFrame parentFrame) {
    // Prompt for confirmation before deleting
    int response = JOptionPane.showConfirmDialog(parentFrame,
            "Are you sure you want to delete your account? This action is irreversible.",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

    if (response == JOptionPane.YES_OPTION) {
        // Proceed with deletion if confirmed
        String username = JOptionPane.showInputDialog(parentFrame, "Enter your username to confirm:");

        if (username != null && !username.trim().isEmpty()) {
            if (deleteUserFromFile(username.trim())) {
                JOptionPane.showMessageDialog(parentFrame, "Your account has been deleted successfully.");
                parentFrame.dispose(); // Close the dashboard
                new MainGUI().initialize(); // Optionally, show the main menu again
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Account not found. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Username is required to confirm deletion.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

// Method to delete the user from the file
private boolean deleteUserFromFile(String username) {
    File userFile = new File("users.txt");
    if (!userFile.exists()) {
        return false; // File not found
    }

    List<String> updatedLines = new ArrayList<>();
    boolean userFound = false;

    try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] userDetails = line.split("\\|"); // Split by '|'
            if (userDetails.length == 5 && userDetails[2].equals(username)) {
                userFound = true; // User found, so we skip this line
            } else {
                updatedLines.add(line); // Add line to updated list if not matching
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }

    if (userFound) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true; // Successfully deleted
    }
    return false; // User not found
}





}
