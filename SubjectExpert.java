import java.io.*;
import java.util.*;

public class SubjectExpert {
    private String firstName;
    private String lastName;
    private String expertise;
    private String qualification;

    // Constructor
    public SubjectExpert(String firstName, String lastName, String expertise, String qualification) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.expertise = expertise;
        this.qualification = qualification;
    }

    // Save expert details to file
    public void saveSpecialistToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("subjectexperts.txt", true))) {
            writer.write(this.firstName + "," + this.lastName + "," + this.expertise + "," + this.qualification);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load subject specialists from file
    public static List<SubjectExpert> loadSubjectSpecialists() {
        List<SubjectExpert> specialists = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("subjectexperts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                specialists.add(new SubjectExpert(data[0], data[1], data[2], data[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return specialists;
    }
}
