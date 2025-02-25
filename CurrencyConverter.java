import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import javax.swing.*;

public class CurrencyConverter extends JFrame {
    private final JComboBox<String> fromCurrency, toCurrency;
    private final JTextField amountField;
    private final JLabel resultLabel;
    private final HashMap<String, Double> exchangeRates;
    private final HashMap<String, String> currencySymbols;

    public CurrencyConverter() {
        setTitle("Currency Converter");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout()); // GridBagLayout for alignment

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST; 

        // Exchange rate mapping
        exchangeRates = new HashMap<>();
        exchangeRates.put("USD", 1.0);
        exchangeRates.put("EUR", 0.85);
        exchangeRates.put("INR", 82.5);
        exchangeRates.put("GBP", 0.75);
        exchangeRates.put("JPY", 110.0);
        exchangeRates.put("CAD", 1.35);

        // Currency symbols mapping (Use Unicode)
        currencySymbols = new HashMap<>();
        currencySymbols.put("USD", "\u0024");  // $
        currencySymbols.put("EUR", "\u20AC");  // €
        currencySymbols.put("INR", "\u20B9");  // ₹
        currencySymbols.put("GBP", "\u00A3");  // £
        currencySymbols.put("JPY", "\u00A5");  // ¥
        currencySymbols.put("CAD", "C$");

        // Add UI Components
        addComponent(new JLabel("From:"), gbc, 0, 0);
        fromCurrency = new JComboBox<>(new String[]{"USD", "EUR", "INR", "GBP", "JPY", "CAD"});
        addComponent(fromCurrency, gbc, 1, 0);

        addComponent(new JLabel("To:"), gbc, 0, 1);
        toCurrency = new JComboBox<>(new String[]{"USD", "EUR", "INR", "GBP", "JPY", "CAD"});
        addComponent(toCurrency, gbc, 1, 1);

        addComponent(new JLabel("Amount:"), gbc, 0, 2);
        amountField = new JTextField();
        amountField.setPreferredSize(new Dimension(100, 25)); 
        addComponent(amountField, gbc, 1, 2);

        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener((ActionEvent e) -> convertCurrency());

        gbc.gridwidth = 2; 
        addComponent(convertButton, gbc, 0, 3);

        resultLabel = new JLabel("Converted Amount: ");
        resultLabel.setOpaque(false); 
        addComponent(resultLabel, gbc, 0, 4);

        setVisible(true);
    }

    private void addComponent(Component component, GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        add(component, gbc);
    }

    private void convertCurrency() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String from = (String) fromCurrency.getSelectedItem();
            String to = (String) toCurrency.getSelectedItem();

            
            double convertedAmount = (amount / exchangeRates.get(from)) * exchangeRates.get(to);

            
            String toSymbol = currencySymbols.getOrDefault(to, "");

            
            resultLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            resultLabel.setText("Converted Amount: " + toSymbol + String.format("%.2f", convertedAmount));

        } catch (NumberFormatException ex) {
            resultLabel.setText("<html><font color='red'>Invalid amount! Please enter a number.</font></html>");
        }
    }

    public static void main(String[] args) {
        CurrencyConverter converter = new CurrencyConverter();
        converter.setVisible(true);
    }
}
