import javax.swing.*;
import java.awt.*;


public class AdminMainMenu {

    private JFrame frame;

    public void display() {
        // Create the main frame for the Admin Menu
        frame = new JFrame("Administrator Main Menu");
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use BorderLayout for the panel so we can easily add a label at the top
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout()); // Use BorderLayout to position components

        // Add the "Administrator Main Menu" label at the top (NORTH)
        JLabel titleLabel = new JLabel("Administrator Main Menu\n", JLabel.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Set up the panel for the buttons (Center area)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 2, 10, 10)); // GridLayout for buttons with spacing

        JButton createUserButton = new JButton("Create User");
        JButton viewUserButton = new JButton("View User");
        JButton updateUserButton = new JButton("Update User");
        JButton createResourceButton = new JButton("Create Resource");
        JButton editResourceButton = new JButton("Edit Resource");
        JButton viewResourceButton = new JButton("View Resource List");
        JButton deleteUserButton = new JButton("Delete User");  // New delete user button
        JButton deleteResourceButton = new JButton("Delete Resource");  // New delete resource button
        JButton reviewFeedbackButton = new JButton("Review Feedback");
        JButton backButton = new JButton("Go Back");
        

        // Action listener for "Create User" button
        createUserButton.addActionListener(e -> {
            
            AdminFunctions.showCreateUserForm(frame);
        });

        // Action listener for "Create Resource" button
        createResourceButton.addActionListener(e -> {
            AdminFunctions.showCreateResourceForm(frame);
        });

        // Action listener for "View Users List" button
        viewUserButton.addActionListener(e -> {
            AdminFunctions.showUsersInTextArea(frame);
        });

        // Action listener for "Update User" button
        updateUserButton.addActionListener(e -> {
            AdminFunctions.showUpdateUserForm(frame);
        });

        // Action listener for "Edit Resource" button
        editResourceButton.addActionListener(e -> {
            AdminFunctionsTwo.showEditResourceWindow(frame);
        });

        // Action listener for "View Resource" button
        viewResourceButton.addActionListener(e -> {
            AdminFunctionsTwo.showViewResourcesWindow(frame);
        });

        // Action listener for "Review Feedback" button
        reviewFeedbackButton.addActionListener(e -> {
            AdminFunctionsTwo.showFeedbackWindow(frame);
        });

         // Action listener for deleting a user
         deleteUserButton.addActionListener(e -> AdminFunctionsTwo.deleteUser(frame));

         // Action listener for deleting a resource
         deleteResourceButton.addActionListener(e -> AdminFunctionsTwo.deleteResource(frame));

        // Action listener for the "Go Back" button
        backButton.addActionListener(e -> {
            frame.dispose();
            new MainGUI().initialize(); // Open the Main Menu
        });

        // Add buttons to the button panel
        buttonPanel.add(createUserButton);
        buttonPanel.add(viewUserButton);
        buttonPanel.add(updateUserButton);
        buttonPanel.add(createResourceButton);
        buttonPanel.add(editResourceButton);
        buttonPanel.add(viewResourceButton);
        buttonPanel.add(reviewFeedbackButton);
        buttonPanel.add(deleteUserButton);
        buttonPanel.add(deleteResourceButton);
        buttonPanel.add(backButton);

        // Add the button panel to the main panel (Center part of the frame)
        panel.add(buttonPanel, BorderLayout.CENTER);

        // Add the main panel to the frame
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    
}
