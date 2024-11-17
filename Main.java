import java.io.File;

import javax.swing.*;


public class Main {

    public static void main(String[] args) {
        // Initialize the application
        SwingUtilities.invokeLater(() -> {
            createDefaultAdmin(); // Ensure default admin exists
            new MainGUI();         // Launch the main GUI
        });
    }

    // Create a default admin if no admin exists (this would be checked in a file)
    private static void createDefaultAdmin() {
        
        File file = new File("admindocument.txt");
        
        if (!file.exists()) {
            Administrator defaultAdmin = new Administrator("admin", "admin123");
            defaultAdmin.saveAdminToFile();
            System.out.println("Default admin account created.");
        }

    }
    
}
