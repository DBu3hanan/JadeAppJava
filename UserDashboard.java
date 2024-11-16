import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserDashboard {

    private JFrame frame;

    public void display() {
        frame = new JFrame("User Dashboard");
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1)); // 5 rows for buttons

        // Add buttons for each action
        JButton readGuidelinesButton = new JButton("Read Guidelines");
        JButton editUserInfoButton = new JButton("Edit User Information");
        JButton viewResourcesButton = new JButton("View Resources");
        JButton giveFeedbackButton = new JButton("Give Feedback");

        // Action listeners for each button
        readGuidelinesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGuidelines();
            }
        });

        editUserInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editUserInfo();
            }
        });

        viewResourcesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewResources();
            }
        });

        giveFeedbackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                giveFeedback();
            }
        });

        // Add buttons to the panel
        panel.add(readGuidelinesButton);
        panel.add(editUserInfoButton);
        panel.add(viewResourcesButton);
        panel.add(giveFeedbackButton);

        // Add panel to the frame
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    // Method to show the guidelines
    private void showGuidelines() {
        
        // Create a new JFrame for displaying the guidelines
        JFrame guidelinesFrame = new JFrame("Guidelines");
        guidelinesFrame.setSize(700, 400); // Adjust the size to fit the guidelines text
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
                + "7. Give Feedback: Users can provide feedback on the application for improvement.\n";
    
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
    

    // Method to edit user information
    private void editUserInfo() {
        JFrame editFrame = new JFrame("Edit User Information");
        editFrame.setSize(400, 400);
        editFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2)); // Fields for editing user info

        // Add fields for updating user info
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(); // Pre-fill with current username
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
                updateUserInfo(username, newPassword);

                JOptionPane.showMessageDialog(editFrame, "User information updated successfully!");
                editFrame.dispose(); // Close the edit window
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
        // For simplicity, let's just show the username and new password in a dialog
        System.out.println("Updating user: " + username + " with new password: " + newPassword);
        // You can also write this logic to update the file or database as per your requirements
    }

    // Method to view resources
    private void viewResources() {
        JFrame resourcesFrame = new JFrame("Resources");
        resourcesFrame.setSize(400, 300);
        resourcesFrame.setLocationRelativeTo(null);

        JTextArea textArea = new JTextArea();
        textArea.setText("Here are the available resources:\n1. Resource 1\n2. Resource 2\n3. Resource 3\n...");
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        resourcesFrame.add(scrollPane);
        resourcesFrame.setVisible(true);
    }

    // Method to give feedback
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
                // Save feedback (you can save to a file or database)
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

    // Method to save feedback
    private void saveFeedback(String feedback) {
        // Code to save feedback (e.g., write to a file or save to a database)
        System.out.println("Feedback submitted: " + feedback);
        // For now, just printing the feedback to the console
    }
}
