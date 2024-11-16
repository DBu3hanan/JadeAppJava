import java.io.*;
import java.util.*;

public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    // Constructor
    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    // Save user details to file
    public void saveUserToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("userdata.txt", true))) {
            writer.write(this.firstName + "," + this.lastName + "," + this.username + "," + this.password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load users from file
    public static List<User> loadUsersFromFile() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("userdata.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                users.add(new User(data[0], data[1], data[2], data[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + username + ")";
    }
}
