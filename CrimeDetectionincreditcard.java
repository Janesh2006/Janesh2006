import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreditCardFraudDetection {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FraudDetectionUI().createAndShowGUI());
    }
}

class FraudDetectionUI {

    private JFrame frame;
    private JTextField cardNumberField;
    private JTextField transactionAmountField;
    private JComboBox<String> cardTypeComboBox;
    private JTextArea resultArea;

    private final int SILVER_LIMIT = 15000;
    private final int GOLD_LIMIT = 25000;
    private final int PLATINUM_LIMIT = 50000;
    private final int DIAMOND_LIMIT = 100000;

    public void createAndShowGUI() {
        frame = new JFrame("Credit Card Fraud Detection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));

        JLabel cardNumberLabel = new JLabel("Enter Card Number:");
        cardNumberField = new JTextField();

        JLabel cardTypeLabel = new JLabel("Select Card Type:");
        String[] cardTypes = {"Silver", "Gold", "Platinum", "Diamond"};
        cardTypeComboBox = new JComboBox<>(cardTypes);

        JLabel transactionAmountLabel = new JLabel("Transaction Amount:");
        transactionAmountField = new JTextField();

        JButton detectButton = new JButton("Detect Fraud");
        detectButton.addActionListener(new DetectFraudActionListener());

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        panel.add(cardNumberLabel);
        panel.add(cardNumberField);
        panel.add(cardTypeLabel);
        panel.add(cardTypeComboBox);
        panel.add(transactionAmountLabel);
        panel.add(transactionAmountField);
        panel.add(new JLabel());
        panel.add(detectButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private class DetectFraudActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String cardNumber = cardNumberField.getText();
                if (cardNumber.isEmpty() || cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
                    resultArea.setText("Please enter a valid 16-digit card number.");
                    return;
                }

                String cardType = (String) cardTypeComboBox.getSelectedItem();
                double transactionAmount = Double.parseDouble(transactionAmountField.getText());
                String result = detectFraud(cardNumber, cardType, transactionAmount);
                resultArea.setText(result);
            } catch (NumberFormatException ex) {
                resultArea.setText("Please enter a valid transaction amount.");
            }
        }

        private String detectFraud(String cardNumber, String cardType, double transactionAmount) {
            int limit;
            switch (cardType) {
                case "Silver":
                    limit = SILVER_LIMIT;
                    break;
                case "Gold":
                    limit = GOLD_LIMIT;
                    break;
                case "Platinum":
                    limit = PLATINUM_LIMIT;
                    break;
                case "Diamond":
                    limit = DIAMOND_LIMIT;
                    break;
                default:
                    return "Invalid card type.";
            }

            if (transactionAmount > limit) {
                return "Transaction flagged as suspicious!\nCard Number: " + cardNumber + "\nExceeds " + cardType + " card limit of " + limit + ".";
            } else {
                return "Transaction approved.\nCard Number: " + cardNumber + "\nWithin " + cardType + " card limit of " + limit + ".";
            }
        }
    }
}