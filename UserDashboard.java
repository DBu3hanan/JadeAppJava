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
        JButton gobackButton = new JButton("Main Menu");

        // Add action listeners for each button
        readGuidelinesButton.addActionListener(e -> showGuidelines());
        editUserInfoButton.addActionListener(e -> editUserInfo());
        viewResourcesButton.addActionListener(e -> viewResources());
        giveFeedbackButton.addActionListener(e -> giveFeedback());

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
                + "4. Users can create, update, view, and delete their accounts.\n\n"
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
    
    //FIXXXXX   
    // Method to edit user information
    private void editUserInfo() {  
        JFrame editFrame = new JFrame("Edit User Information");
        editFrame.setSize(400, 400);
        editFrame.setLocationRelativeTo(null);
    
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2)); // Fields for editing user info
    
        // Add fields for updating user info
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(); // Pre-fill with current username (if applicable)
        JLabel passwordLabel = new JLabel("New Password:");
        JPasswordField passwordField = new JPasswordField();
    
        // Save button to update user information
        JButton saveButton = new JButton("Save Changes");
    
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String newPassword = new String(passwordField.getPassword());
    
                // Code to update the user information (you may need to save it to a file or database)
                if (username.isEmpty() || newPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(editFrame, "Please fill in both fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    updateUserInfo(username, newPassword); // Method to save updated information
                    JOptionPane.showMessageDialog(editFrame, "User information updated successfully!");
                    editFrame.dispose(); // Close the edit window
                }
            }
        });
    
        // Add components to the edit panel
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty space
        panel.add(saveButton);
    
        editFrame.add(panel);
        editFrame.setVisible(true);
    }
    
    // Method to update user information
    private void updateUserInfo(String username, String newPassword) {
        // Example of updating user info in a file (you would need to enhance this logic to handle file operations)
        // For simplicity, let's just show the username and new password in a dialog or log it.
        System.out.println("Updating user: " + username + " with new password: " + newPassword);
    
        // You would need to implement logic to actually save the updated username and password
        // Here’s an example of updating a file with the new details:
    
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("users_temp.txt"))) {
    
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split("\\|");
    
                // Assuming the username is the first field and we're updating it
                if (userDetails[0].equals(username)) {
                    userDetails[1] = newPassword; // Assuming the password is the second field, update it.
                }
    
                // Write the updated user details to the temporary file
                writer.write(String.join("|", userDetails));
                writer.newLine();
            }
    
            // Replace the old file with the updated one
            File oldFile = new File("users.txt");
            File newFile = new File("users_temp.txt");
            if (oldFile.delete()) {
                newFile.renameTo(oldFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating user information: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
}
