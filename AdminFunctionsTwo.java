import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdminFunctionsTwo {

// Method to edit a user's details
public static void editUser(JFrame parentFrame) {
    // Create a new frame for editing user details
    JFrame editUserFrame = new JFrame("Edit User");
    editUserFrame.setSize(400, 300);
    editUserFrame.setLocationRelativeTo(parentFrame); // Center relative to parent frame

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    // Create fields to capture user information
    JTextField usernameField = new JTextField();
    JTextField newFirstNameField = new JTextField();
    JTextField newLastNameField = new JTextField();

    // Add labels and fields to the panel
    panel.add(new JLabel("Enter Username of the User to Edit:"));
    panel.add(usernameField);
    panel.add(new JLabel("New First Name:"));
    panel.add(newFirstNameField);
    panel.add(new JLabel("New Last Name:"));
    panel.add(newLastNameField);

    // Create a button to save changes
    JButton saveChangesButton = new JButton("Save Changes");
    saveChangesButton.addActionListener(e -> {
        String username = usernameField.getText().trim();
        String newFirstName = newFirstNameField.getText().trim();
        String newLastName = newLastNameField.getText().trim();

        if (username.isEmpty() || newFirstName.isEmpty() || newLastName.isEmpty()) {
            JOptionPane.showMessageDialog(editUserFrame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (updateUserDetails(username, newFirstName, newLastName)) {
                JOptionPane.showMessageDialog(editUserFrame, "User details updated successfully!");
                editUserFrame.dispose(); // Close the edit frame
            } else {
                JOptionPane.showMessageDialog(editUserFrame, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });

    panel.add(saveChangesButton);

    editUserFrame.getContentPane().add(panel);
    editUserFrame.setVisible(true);
}




// Utility method to update user details in the "users.txt" file
private static boolean updateUserDetails(String username, String newFirstName, String newLastName) {
    File userFile = new File("users.txt");
    if (!userFile.exists()) {
        return false; // File not found
    }

    List<String> updatedLines = new ArrayList<>();
    boolean userFound = false;

    try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] userDetails = line.split(","); // Assuming | is the delimiter
            if (userDetails.length == 4 && userDetails[2].equals(username)) {
                // Update first and last name
                userDetails[0] = newFirstName;
                userDetails[1] = newLastName;
                userFound = true;
            }
            updatedLines.add(String.join("|", userDetails)); // Rebuild line without password
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







    public static void showEditResourceWindow(JFrame parentFrame) {
        // Create a new frame for editing resources
        JFrame editResourceFrame = new JFrame("Edit Resource");
        editResourceFrame.setSize(500, 500);
        editResourceFrame.setLocationRelativeTo(parentFrame);

        // Create a panel to hold buttons for each resource
        JPanel resourcePanel = new JPanel();
        resourcePanel.setLayout(new GridLayout(2, 2)); // Dynamic rows, 1 column

        File resourcesDir = new File("Resources");
        if (!resourcesDir.exists() || !resourcesDir.isDirectory()) {
            JOptionPane.showMessageDialog(parentFrame, "Resources folder not found.");
            return;
        }

        File[] resourceFiles = resourcesDir.listFiles((dir, name) -> name.endsWith(".txt"));
        if (resourceFiles == null || resourceFiles.length == 0) {
            JOptionPane.showMessageDialog(parentFrame, "No resources found.");
            return;
        }

        for (File resourceFile : resourceFiles) {
            JButton resourceButton = new JButton(resourceFile.getName());
            resourceButton.addActionListener(e -> openResourceEditor(editResourceFrame, resourceFile));
            resourcePanel.add(resourceButton);
        }

        JScrollPane scrollPane = new JScrollPane(resourcePanel);
        editResourceFrame.getContentPane().add(scrollPane);
        editResourceFrame.setVisible(true);
    }

    private static void openResourceEditor(JFrame parentFrame, File resourceFile) {
        // Create a new frame for editing the selected resource
        JFrame editorFrame = new JFrame("Edit Resource: " + resourceFile.getName());
        editorFrame.setSize(500, 500);
        editorFrame.setLocationRelativeTo(parentFrame);

        JTextArea resourceTextArea = new JTextArea(20, 40);
        try (BufferedReader reader = new BufferedReader(new FileReader(resourceFile))) {
            resourceTextArea.read(reader, null);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parentFrame, "Error loading resource: " + ex.getMessage());
            return;
        }

        JScrollPane scrollPane = new JScrollPane(resourceTextArea);
        JButton saveButton = new JButton("Save");

        saveButton.addActionListener(e -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(resourceFile))) {
                writer.write(resourceTextArea.getText());
                JOptionPane.showMessageDialog(editorFrame, "Resource saved successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(editorFrame, "Error saving resource: " + ex.getMessage());
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(saveButton, BorderLayout.SOUTH);

        editorFrame.getContentPane().add(panel);
        editorFrame.setVisible(true);
    }

    public static void showViewResourcesWindow(JFrame parentFrame) {
        // Create a new frame for viewing resources
        JFrame viewResourceFrame = new JFrame("View Resources");
        viewResourceFrame.setSize(500, 500);
        viewResourceFrame.setLocationRelativeTo(parentFrame);

        JTextArea resourcesTextArea = new JTextArea();
        resourcesTextArea.setEditable(false);

        File resourcesDir = new File("Resources");
        if (!resourcesDir.exists() || !resourcesDir.isDirectory()) {
            resourcesTextArea.setText("Resources folder not found.");
        } else {
            File[] resourceFiles = resourcesDir.listFiles((dir, name) -> name.endsWith(".txt"));
            if (resourceFiles == null || resourceFiles.length == 0) {
                resourcesTextArea.setText("No resources found.");
            } else {
                StringBuilder resourcesList = new StringBuilder("Available Resources:\n");
                for (File resourceFile : resourceFiles) {
                    resourcesList.append(resourceFile.getName()).append("\n");
                }
                resourcesTextArea.setText(resourcesList.toString());
            }
        }

        JScrollPane scrollPane = new JScrollPane(resourcesTextArea);
        viewResourceFrame.getContentPane().add(scrollPane);
        viewResourceFrame.setVisible(true);
    }

    // Method to display feedback content in a JTextArea
    public static void showFeedbackWindow(JFrame parentFrame) {
        // Create a new frame for displaying feedback
        JFrame feedbackFrame = new JFrame("Review Feedback");
        feedbackFrame.setSize(500, 500);
        feedbackFrame.setLocationRelativeTo(parentFrame);

        // Create a JTextArea to display feedback content
        JTextArea feedbackTextArea = new JTextArea(20, 40);
        feedbackTextArea.setEditable(false); // Make it non-editable

        // Try to read the feedback.txt file
        File feedbackFile = new File("feedback.txt");
        if (feedbackFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(feedbackFile))) {
                // Read the content of feedback.txt and display it in the JTextArea
                StringBuilder feedbackContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    feedbackContent.append(line).append("\n");
                }
                feedbackTextArea.setText(feedbackContent.toString());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parentFrame, "Error loading feedback: " + ex.getMessage());
            }
        } else {
            feedbackTextArea.setText("No feedback found.");
        }

        // Add the JTextArea to a JScrollPane for scrolling capability
        JScrollPane scrollPane = new JScrollPane(feedbackTextArea);

        // Add the JScrollPane to the frame
        feedbackFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Display the feedback frame
        feedbackFrame.setVisible(true);
    }

}
