import java.io.*;

public class AdminValidator {

    // This method validates the admin credentials against the content of admindocument.txt
    public static boolean validateAdminCredentials(String username, String password) {
        try {
            // Read the admin credentials from the file
            File file = new File("admindocument.txt");
            if (!file.exists()) {
                return false;  // File does not exist
            }

            // Read the file contents
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            reader.close();
            
            if (line == null) {
                return false; // File is empty
            }

            // Split the line by comma to extract username and password
            String[] credentials = line.split(",");
            if (credentials.length != 2) {
                return false; // Invalid format in the file
            }

            String storedUsername = credentials[0].trim();
            String storedPassword = credentials[1].trim();

            // Check if the entered username and password match the stored credentials
            return username.equals(storedUsername) && password.equals(storedPassword);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
