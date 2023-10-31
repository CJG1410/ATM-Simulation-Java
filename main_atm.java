import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class main_atm extends JFrame {
    private JTextField balanceField;
    private JTextArea accountDetailsArea;
    private Connection connection;
    private String accountNumber;

    public main_atm() {
        setTitle("ATM Simulation");
        setSize(1000, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Establish a database connection
        try {
            String url = "jdbc:mysql://localhost:3306/atm_system";
            String username = "*****"; // username
            String password = "******"; // password
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to connect to the database.");
            System.exit(1);
        }

  

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));
        inputPanel.setOpaque(false);



        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel accountLabel = new JLabel("Account Number:");
        JTextField accountField = new JTextField();
        JLabel pinLabel = new JLabel("PIN:");
        JPasswordField pinField = new JPasswordField();
        JLabel balanceLabel = new JLabel("Balance:");
        balanceField = new JTextField();
        balanceField.setEditable(false);

        JButton checkBalanceButton = new JButton("Check Balance");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton showDetailsButton = new JButton("Show Account Details");

        panel.add(accountLabel);
        panel.add(accountField);
        panel.add(pinLabel);
        panel.add(pinField);
        panel.add(balanceLabel);
        panel.add(balanceField);
        panel.add(checkBalanceButton);
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(showDetailsButton);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());
        accountDetailsArea = new JTextArea();
        accountDetailsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(accountDetailsArea);
        detailsPanel.add(scrollPane, BorderLayout.CENTER);

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountNumber = accountField.getText();
                String pin = new String(pinField.getPassword());

                try {
                    String query = "SELECT balance FROM accounts WHERE account_number = ? AND pin = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, accountNumber);
                    preparedStatement.setString(2, pin);

                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        double balance = resultSet.getDouble("balance");
                        balanceField.setText(String.valueOf(balance));
                    } else {
                        balanceField.setText("Invalid account number or PIN");
                    }

                    preparedStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    balanceField.setText("Error occurred while checking balance.");
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pin = new String(pinField.getPassword());
                try {
                    double depositAmount = Double.parseDouble(JOptionPane.showInputDialog("Enter the deposit amount:"));

                    // Update the database with the new balance
                    double currentBalance = Double.parseDouble(balanceField.getText());
                    currentBalance += depositAmount;

                    String updateQuery = "UPDATE accounts SET balance = ? WHERE account_number = ? AND pin = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                    updateStatement.setDouble(1, currentBalance);
                    updateStatement.setString(2, accountNumber);
                    updateStatement.setString(3, pin);
                    updateStatement.executeUpdate();
                    updateStatement.close();

                    balanceField.setText(String.valueOf(currentBalance));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid deposit amount. Please enter a valid number.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred while depositing money.");
                }
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pin = new String(pinField.getPassword());
                try {
                    double withdrawalAmount = Double.parseDouble(JOptionPane.showInputDialog("Enter the withdrawal amount:"));

                    // Update the database with the new balance
                    double currentBalance = Double.parseDouble(balanceField.getText());
                    if (currentBalance >= withdrawalAmount) {
                        currentBalance -= withdrawalAmount;

                        String updateQuery = "UPDATE accounts SET balance = ? WHERE account_number = ? AND pin = ?";
                        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                        updateStatement.setDouble(1, currentBalance);
                        updateStatement.setString(2, accountNumber);
                        updateStatement.setString(3, pin);
                        updateStatement.executeUpdate();
                        updateStatement.close();

                        balanceField.setText(String.valueOf(currentBalance));
                    } else {
                        JOptionPane.showMessageDialog(null, "Insufficient funds.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid withdrawal amount. Please enter a valid number.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred while withdrawing money.");
                }
            }
        });

        showDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String query = "SELECT name, balance FROM accounts WHERE account_number = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, accountNumber);

                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        String name = resultSet.getString("name");
                        double balance = resultSet.getDouble("balance");
                        accountDetailsArea.setText("Name: " + name + "\n" +
                                "Account Number: " + accountNumber + "\n" +
                                "Balance: RS" + balance);
                    } else {
                        accountDetailsArea.setText("Account not found in the database.");
                    }

                    preparedStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    accountDetailsArea.setText("Error occurred while fetching account details.");
                }
            }
        });

        add(panel, BorderLayout.NORTH);
        add(detailsPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Register the MySQL JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "MySQL JDBC driver not found.");
            System.exit(1);
        }

        SwingUtilities.invokeLater(() -> new main_atm());
    }
}
