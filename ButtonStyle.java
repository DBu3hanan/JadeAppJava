import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ButtonStyle {

    // Static method to apply global style to all JButtons
    public static void applyStyle(JButton button) {
        // Set the background color, text color, and font
        button.setBackground(new Color(240, 240, 240)); // Light gray background
        button.setForeground(new Color(60, 60, 60));   // Dark gray text color
        button.setFont(new Font("Times New Roman", Font.BOLD, 15)); // Modern, readable font
        button.setFocusPainted(false); // Disable focus outline on click
        button.setOpaque(true);
        button.setBorderPainted(false);

        // Add a rounded border
        Border roundedBorder = BorderFactory.createLineBorder(new Color(200, 200, 200), 2); // Subtle border
        button.setBorder(BorderFactory.createCompoundBorder(
                roundedBorder,
                BorderFactory.createEmptyBorder(10, 20, 10, 20) // Padding for button size
        ));

        // Add hover effect (change background on mouse hover)
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(220, 220, 220)); // Slightly darker on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(240, 240, 240)); // Restore original color
            }
        });
    }
}
