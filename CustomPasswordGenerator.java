import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.security.SecureRandom;
import java.util.List;
import javax.swing.*;

public class CustomPasswordGenerator extends JFrame {
    private final JTextField passwordField, lengthField;
    private final JButton generateButton, dislikeButton, copyButton;
    private final JCheckBox lettersBox, numbersBox, symbolsBox;
    private final SecureRandom random = new SecureRandom();

    public CustomPasswordGenerator() {
        setTitle("Customized Password Generator");
        setSize(450, 230);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        add(new JLabel("Password Length (5-16):")).setBounds(20, 20, 180, 20);
        lengthField = new JTextField();
        lengthField.setBounds(200, 20, 50, 20);
        add(lengthField);

        lettersBox = createCheckBox("Letters (A-Z, a-z)", 20, true);
        numbersBox = createCheckBox("Numbers (0-9)", 160, true);
        
        symbolsBox = createCheckBox("Symbols (!@#$%^&*)", 280, true);
        symbolsBox.setBounds(280, 50, 180, 20); 

        passwordField = new JTextField();
        passwordField.setBounds(20, 80, 380, 30);
        passwordField.setEditable(false);
        add(passwordField);

        generateButton = createButton("Generate Password", 20, e -> generatePassword());
        add(generateButton);
        dislikeButton = createButton("Dislike? Generate Again", 210, e -> generatePassword());
        dislikeButton.setEnabled(false);

        copyButton = createButton("Copy to Clipboard", 20, e -> copyToClipboard());
        copyButton.setEnabled(false);
        copyButton.setBounds(20, 160, 380, 30);
        add(copyButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JCheckBox createCheckBox(String text, int x, boolean selected) {
        JCheckBox checkBox = new JCheckBox(text, selected);
        checkBox.setBounds(x, 50, 120, 20); // Increased width to prevent text cutoff
        checkBox.setOpaque(true);
        add(checkBox);
        return checkBox;
    }

    private JButton createButton(String text, int x, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setBounds(x, 120, 180, 30);
        button.addActionListener(action);
        add(button);
        return button;
    }

    private void generatePassword() {
        int length = getValidLength();
        if (length == -1) return;

        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ", lower = "abcdefghijklmnopqrstuvwxyz",
                numbers = "0123456789", symbols = "!@#$%^&*";

        StringBuilder charSet = new StringBuilder();
        if (lettersBox.isSelected()) charSet.append(upper).append(lower);
        if (numbersBox.isSelected()) charSet.append(numbers);
        if (symbolsBox.isSelected()) charSet.append(symbols);

        if (charSet.length() == 0) {
            passwordField.setText(""); // Clear password field
            dislikeButton.setEnabled(false);
            copyButton.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Select at least one character type.");
            return;
        }

        List<String> requiredSets = List.of(upper, lower, numbers, symbols);
        passwordField.setText(generateValidPassword(length, charSet.toString(), requiredSets));
        dislikeButton.setEnabled(true);
        copyButton.setEnabled(true);
    }

    private int getValidLength() {
        try {
            int length = Integer.parseInt(lengthField.getText());
            if (length < 5 || length >= 16) throw new NumberFormatException();
            return length;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid length (5-16).");
            return -1;
        }
    }

    private String generateValidPassword(int length, String charSet, List<String> requiredSets) {
        while (true) {
            String password = random.ints(length, 0, charSet.length())
                    .mapToObj(charSet::charAt)
                    .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                    .toString();

            if (requiredSets.stream().allMatch(set -> password.chars().anyMatch(c -> set.indexOf(c) >= 0))) {
                return password;
            }
        }
    }

    private void copyToClipboard() {
        String password = passwordField.getText();
        if (!password.isEmpty()) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(password), null);
            JOptionPane.showMessageDialog(this, "Password copied to clipboard!");
        }
    }

    public static void main(String[] args) {
        CustomPasswordGenerator generator = new CustomPasswordGenerator();
        generator.setVisible(true);
    }
}