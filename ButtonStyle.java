import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ButtonStyle {

    // Static method to apply global style to all JButtons
    public static void applyStyle(JButton button) {
        // Set background color, text color, font, etc.
        button.setBackground(Color.GREEN);   // Set background color
        button.setForeground(Color.WHITE);   // Set text color
        button.setFont(new Font("Arial", Font.BOLD, 16));  // Set font style and size
        button.setFocusPainted(false);       // Disable focus outline on click
        
        // Create a rounded border using LineBorder and padding
        Border roundedBorder = BorderFactory.createLineBorder(Color.WHITE, 2);  // White border with 2px thickness
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10),  // Padding around the button
            roundedBorder  // Actual border
        ));
    }
}
