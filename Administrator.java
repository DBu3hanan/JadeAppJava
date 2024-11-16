import java.io.*;
import java.util.*;

public class Administrator {
    private String username;
    private String password;

    // Constructor
    public Administrator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Save admin data to file
    public void saveAdminToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("admindocument.txt", true))) {
            writer.write(this.username + "," + this.password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load admin data from file
    public static List<Administrator> loadAdminFromFile() {
        List<Administrator> admins = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("admindocument.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                admins.add(new Administrator(data[0], data[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return admins;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
