import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TravelManagementSystem extends JFrame implements ActionListener {
    // Declare UI components
    private JTextField tfFirstName, tfTelephone, tfCost, tfTax, tfSubtotal, tfTotal;
    private JTextArea taAddress;
    private JComboBox<String> departureChoice, destinationChoice, accommodationChoice;
    private JCheckBox cbAirportTax, cbAirMiles, cbInsurance, cbLuggage;
    private ArrayList<JCheckBox> foodCheckboxes = new ArrayList<>();
    private HashMap<String, Double> destinationCosts = new HashMap<>();
    private HashMap<String, Double> accommodationCosts = new HashMap<>();
    private HashMap<String, Double> foodCosts = new HashMap<>();
    private ArrayList<String> selectedFoodItems = new ArrayList<>();

    public TravelManagementSystem() {
        // Frame properties
        setTitle("Travel Management System");
        setSize(700, 800);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Customer Details Panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(new JLabel("Customer Details"), gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        add(new JLabel("Name:"), gbc);
        tfFirstName = new JTextField(25);
        gbc.gridx = 1;
        add(tfFirstName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Address:"), gbc);
        taAddress = new JTextArea(3, 25);
        JScrollPane scrollPane = new JScrollPane(taAddress);
        gbc.gridx = 1;
        add(scrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Telephone:"), gbc);
        tfTelephone = new JTextField(25);
        tfTelephone.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) || tfTelephone.getText().length() >= 10) {
                    e.consume();
                }
            }
        });
        gbc.gridx = 1;
        add(tfTelephone, gbc);

        // Travel Details Panel
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(new JLabel("Travel Details"), gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        add(new JLabel("Departure:"), gbc);
        departureChoice = new JComboBox<>(new String[] {
            "MM Airport", "YYZ - Toronto Pearson", "LAX - Los Angeles International",
            "JFK - New York JFK", "LHR - London Heathrow"
        });
        gbc.gridx = 1;
        add(departureChoice, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Destination:"), gbc);
        destinationChoice = new JComboBox<>(new String[] {
            "Canada - 7 Days in Ottawa", "USA - 5 Days in NYC", "France - 4 Days in Paris",
            "Australia - 10 Days in Sydney", "Japan - 6 Days in Tokyo",
            "Germany - 8 Days in Berlin", "Brazil - 7 Days in Rio", "South Africa - 5 Days in Cape Town"
        });
        gbc.gridx = 1;
        add(destinationChoice, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Accommodation:"), gbc);
        accommodationChoice = new JComboBox<>(new String[] {
            "Single", "Double", "Family Suite", "Luxury Suite"
        });
        gbc.gridx = 1;
        add(accommodationChoice, gbc);

        // Additional Services Checkbox Panel
        JPanel checkboxPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        cbAirportTax = new JCheckBox("Airport Tax");
        cbAirMiles = new JCheckBox("Air Miles Over 20000");
        cbInsurance = new JCheckBox("Traveling Insurance");
        cbLuggage = new JCheckBox("Extra Luggage");

        checkboxPanel.add(cbAirportTax);
        checkboxPanel.add(cbAirMiles);
        checkboxPanel.add(cbInsurance);
        checkboxPanel.add(cbLuggage);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(checkboxPanel, gbc);

        // Food Accommodation Panel
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(new JLabel("Food Accommodation (Select any):"), gbc);

        JPanel foodPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        foodCosts.put("Pizza", 10.0);
        foodCosts.put("Pasta", 15.0);
        foodCosts.put("Salad", 8.0);
        foodCosts.put("Sandwich", 12.0);
        foodCosts.put("Dessert", 5.0);
        foodCosts.put("Beverage", 3.0);

        for (String foodItem : foodCosts.keySet()) {
            JCheckBox foodCheckbox = new JCheckBox(foodItem);
            foodCheckbox.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectedFoodItems.add(foodItem);
                } else {
                    selectedFoodItems.remove(foodItem);
                }
            });
            foodCheckboxes.add(foodCheckbox);
            foodPanel.add(foodCheckbox);
        }
        gbc.gridy++;
        add(foodPanel, gbc);

        // Cost and Receipt Panel
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(new JLabel("Cost Summary"), gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        add(new JLabel("Cost:"), gbc);
        tfCost = new JTextField("0", 15);
        tfCost.setEditable(false);
        gbc.gridx = 1;
        add(tfCost, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Tax:"), gbc);
        tfTax = new JTextField("0", 15);
        tfTax.setEditable(false);
        gbc.gridx = 1;
        add(tfTax, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Subtotal:"), gbc);
        tfSubtotal = new JTextField("0", 15);
        tfSubtotal.setEditable(false);
        gbc.gridx = 1;
        add(tfSubtotal, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Total:"), gbc);
        tfTotal = new JTextField("0", 15);
        tfTotal.setEditable(false);
        gbc.gridx = 1;
        add(tfTotal, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton btnCalculate = new JButton("Calculate");
        btnCalculate.addActionListener(this);
        buttonPanel.add(btnCalculate);

        JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(this);
        buttonPanel.add(btnReset);

        JButton btnReceipt = new JButton("Receipt");
        btnReceipt.addActionListener(this);
        buttonPanel.add(btnReceipt);

        add(buttonPanel, gbc);

        // Initialize destination and accommodation costs
        destinationCosts.put("Canada - 7 Days in Ottawa", 500.0);
        destinationCosts.put("USA - 5 Days in NYC", 600.0);
        destinationCosts.put("France - 4 Days in Paris", 700.0);
        destinationCosts.put("Australia - 10 Days in Sydney", 800.0);
        destinationCosts.put("Japan - 6 Days in Tokyo", 900.0);
        destinationCosts.put("Germany - 8 Days in Berlin", 850.0);
        destinationCosts.put("Brazil - 7 Days in Rio", 750.0);
        destinationCosts.put("South Africa - 5 Days in Cape Town", 780.0);

        accommodationCosts.put("Single", 100.0);
        accommodationCosts.put("Double", 150.0);
        accommodationCosts.put("Family Suite", 200.0);
        accommodationCosts.put("Luxury Suite", 300.0);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Calculate":
                calculateCosts();
                break;

            case "Reset":
                resetFields();
                break;

            case "Receipt":
                showReceipt();
                break;

            default:
                break;
        }
    }

    private void calculateCosts() {
        double baseCost = destinationCosts.get(destinationChoice.getSelectedItem());
        double accommodationCost = accommodationCosts.get(accommodationChoice.getSelectedItem());
        double totalCost = baseCost + accommodationCost;

        if (cbAirportTax.isSelected()) {
            totalCost += 50;
        }
        if (cbAirMiles.isSelected()) {
            totalCost += 25;
        }
        if (cbInsurance.isSelected()) {
            totalCost += 100;
        }
        if (cbLuggage.isSelected()) {
            totalCost += 30;
        }

        // Calculate food costs
        double foodCost = 0;
        for (String foodItem : selectedFoodItems) {
            foodCost += foodCosts.get(foodItem);
        }
        totalCost += foodCost;

        // Update cost fields
        tfCost.setText(String.valueOf(totalCost));
        tfTax.setText(String.valueOf(totalCost * 0.05)); // Assuming 5% tax
        tfSubtotal.setText(String.valueOf(totalCost));
        tfTotal.setText(String.valueOf(totalCost + (totalCost * 0.05)));
    }

    private void resetFields() {
        tfFirstName.setText("");
        taAddress.setText("");
        tfTelephone.setText("");
        tfCost.setText("0");
        tfTax.setText("0");
        tfSubtotal.setText("0");
        tfTotal.setText("0");

        cbAirportTax.setSelected(false);
        cbAirMiles.setSelected(false);
        cbInsurance.setSelected(false);
        cbLuggage.setSelected(false);

        departureChoice.setSelectedIndex(0);
        destinationChoice.setSelectedIndex(0);
        accommodationChoice.setSelectedIndex(0);

        selectedFoodItems.clear();
        for (JCheckBox foodCheckbox : foodCheckboxes) {
            foodCheckbox.setSelected(false);
        }
    }

    private void showReceipt() {
        StringBuilder receipt = new StringBuilder("Receipt:\n");
        receipt.append("Name: ").append(tfFirstName.getText()).append("\n");
        receipt.append("Address: ").append(taAddress.getText()).append("\n");
        receipt.append("Telephone: ").append(tfTelephone.getText()).append("\n");
        receipt.append("Destination: ").append(destinationChoice.getSelectedItem()).append("\n");
        receipt.append("Accommodation: ").append(accommodationChoice.getSelectedItem()).append("\n");
        receipt.append("Total Cost: ").append(tfTotal.getText()).append("\n");

        JOptionPane.showMessageDialog(this, receipt.toString(), "Receipt", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new TravelManagementSystem();
    }
}