import java.awt.*;
import java.awt.event.*;

public class CreditCardFraudDetection extends Frame implements ActionListener {
    // UI Components
    private TextField cardNumberField;
    private TextField transactionAmountField;
    private TextArea resultArea;
    private Button checkFraudButton;

    // Unique set of valid card numbers
    private final String[] validCardNumbers = {
            "1234567812345678",
            "8765432187654321",
            "1122334455667788",
            "9988776655443322"
    };

    public CreditCardFraudDetection() {
        // Set up the frame
        setTitle("Credit Card Fraud Detection");
        setSize(400, 300);
        setLayout(new FlowLayout());

        // Create UI components
        Label cardNumberLabel = new Label("Card Number:");
        cardNumberField = new TextField(16);

        Label transactionAmountLabel = new Label("Transaction Amount:");
        transactionAmountField = new TextField(10);

        checkFraudButton = new Button("Check Fraud");
        resultArea = new TextArea(5, 30);
        resultArea.setEditable(false);

        // Add components to the frame
        add(cardNumberLabel);
        add(cardNumberField);
        add(transactionAmountLabel);
        add(transactionAmountField);
        add(checkFraudButton);
        add(resultArea);

        // Add action listener for the button
        checkFraudButton.addActionListener(this);

        // Set frame visibility
        setVisible(true);

        // Close the application on close button click
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkFraudButton) {
            String cardNumber = cardNumberField.getText();
            String amountStr = transactionAmountField.getText();

            try {
                double amount = Double.parseDouble(amountStr);
                String result = checkForFraud(cardNumber, amount);
                resultArea.setText(result);
            } catch (NumberFormatException ex) {
                resultArea.setText("Invalid transaction amount. Please enter a valid number.");
            }
        }
    }

    // Simple fraud detection logic (for demonstration purposes)
    private String checkForFraud(String cardNumber, double amount) {
        // Check if the card number is valid
        boolean isValidCard = false;
        for (String validCard : validCardNumbers) {
            if (validCard.equals(cardNumber)) {
                isValidCard = true;
                break;
            }
        }

        if (!isValidCard) {
            return "Invalid card number: Card is not registered.";
        }

        // Check transaction amount
        if (amount > 1000) {
            return "Potential fraud detected: Transaction amount exceeds limit.";
        }

        return "Transaction is likely legitimate.";
    }

    public static void main(String[] args) {
        new CreditCardFraudDetection();
    }
}
