import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class CoffeeStorage {
    String nameOfCoffee;
    String coffeeType;
    String coffeeSize;
    double coffeePrice;

    public CoffeeStorage(String nameOfCoffee, String coffeeType, String coffeeSize, double coffeePrice) {
        this.nameOfCoffee = nameOfCoffee;
        this.coffeeType = coffeeType;
        this.coffeeSize = coffeeSize;
        this.coffeePrice = coffeePrice;
    }

    public void ChangePrice(double coffeePrice) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Select new price for coffee: ");
        String CoffeePriceUI = sc.nextLine();
        double CoffeePriceUIValue = Double.parseDouble(JOptionPane.showInputDialog("Select new price for coffee:"));
        this.coffeePrice = CoffeePriceUIValue;
    }

    public double GetCoffeePrice() {
        return coffeePrice;
    }

    public String GetNameOfCoffee() {
        return nameOfCoffee;
    }
}

class MyFrame extends JFrame {
    private JTextField textFieldAantal;
    private JComboBox<String> comboBoxType;
    private JLabel labelPrice;

    MyFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        // TextField voor Aantal
        textFieldAantal = new JTextField();
        textFieldAantal.setBounds(125, 150, 250, 40); // Position and size of the text field

        // Label voor Aantal
        JLabel labelAantal = new JLabel("Aantal:");
        labelAantal.setBounds(125, 120, 250, 30); // Position and size of the label
        labelAantal.setFont(new Font("MV Boli", Font.PLAIN, 20));

        // ComboBox voor Type Coffee
        comboBoxType = new JComboBox<>(new String[]{"Vietnamese IceCoffee", "Matcha Coffee", "Expresso Coffee"});
        comboBoxType.setBounds(125, 250, 250, 40); // Position and size of the combo box

        // Label voor Type Coffee
        JLabel labelType = new JLabel("Type Coffee:");
        labelType.setBounds(125, 220, 250, 30); // Position and size of the label
        labelType.setFont(new Font("MV Boli", Font.PLAIN, 20));

        // Label voor Prijs
        labelPrice = new JLabel("Prijs: €0.00");
        labelPrice.setBounds(125, 300, 250, 30);
        labelPrice.setFont(new Font("MV Boli", Font.PLAIN, 20));

        // Button
        JButton button = new JButton("Click Me");
        button.setBounds(200, 350, 100, 50); // Position and size of the button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int aantal = Integer.parseInt(textFieldAantal.getText());
                    double price = getPriceForSelectedCoffeeType();
                    double totalPrice = aantal * price;
                    labelPrice.setText("Prijs: €" + totalPrice);
                    saveToFile("storage.csv", textFieldAantal, comboBoxType, totalPrice);
                    JOptionPane.showMessageDialog(null, "Gegevens opgeslagen!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Fout bij opslaan: " + ex.getMessage());
                }
            }
        });

        this.setTitle("Coffee System");
        this.setResizable(false);
        this.setSize(500, 500);
        this.setVisible(true);

        this.add(textFieldAantal);
        this.add(labelAantal);
        this.add(comboBoxType);
        this.add(labelType);
        this.add(labelPrice);
        this.add(button);
    }

    public static void main(String[] args) {
        new MyFrame();
    }

    private double getPriceForSelectedCoffeeType() {
        String selectedType = (String) comboBoxType.getSelectedItem();
        if (selectedType != null) {
            switch (selectedType) {
                case "Vietnamese IceCoffee":
                    return 4.45;
                case "Matcha Coffee":
                    return 5.50;
                case "Expresso Coffee":
                    return 1.00;
            }
        }
        return 0.0;
    }

    public static void saveToFile(String fileName, JTextField textFieldAantal, JComboBox<String> comboBoxType, double totalPrice) throws IOException {
        String data = textFieldAantal.getText() + "," + comboBoxType.getSelectedItem() + "," + totalPrice + "\n";
        FileOutputStream out = new FileOutputStream(fileName, true);
        out.write(data.getBytes());
        out.close();
    }

    public static void cashierManagment() {
        double priceTotal = 0.0;
        int totalOfCoffee = 0;
        Scanner sc = new Scanner(System.in);

        CoffeeStorage vietnameseIceCoffee = new CoffeeStorage("Vietnamese IceCoffee", "IceCoffee", "M", 4.45);
        CoffeeStorage matchaCoffeeCold = new CoffeeStorage("Matcha Coffee", "IceCoffee", "M", 5.50);
        CoffeeStorage expressoCoffee = new CoffeeStorage("Expresso Coffee", "WarmCoffee", "S", 1.00);
        boolean isRunning = true;

        while (isRunning) {
            String UI = JOptionPane.showInputDialog("Choose an option (add, list, exit):");

            switch (UI) {
                case "add":
                    String coffeeName = JOptionPane.showInputDialog("Choose coffee name:");

                    double price = 0.0;

                    if (vietnameseIceCoffee.GetNameOfCoffee().equalsIgnoreCase(coffeeName)) {
                        price = vietnameseIceCoffee.GetCoffeePrice();
                    } else if (matchaCoffeeCold.GetNameOfCoffee().equalsIgnoreCase(coffeeName)) {
                        price = matchaCoffeeCold.GetCoffeePrice();
                    } else if (expressoCoffee.GetNameOfCoffee().equalsIgnoreCase(coffeeName)) {
                        price = expressoCoffee.GetCoffeePrice();
                    } else {
                        JOptionPane.showMessageDialog(null, "Coffee not found");
                        break;
                    }
                    totalOfCoffee += Integer.parseInt(JOptionPane.showInputDialog("How many:"));
                    double coffeePrice = totalOfCoffee * price;
                    priceTotal += coffeePrice;

                    JOptionPane.showMessageDialog(null, "Added " + totalOfCoffee + " " + coffeeName + "(s) for a total of €" + coffeePrice);
                    break;

                case "list":
                    // Voeg je lijstfunctionaliteit hier toe
                    break;

                case "exit":
                    String filePath = "C:\\Users\\martw\\IdeaProjects\\cafeBackend\\storage.csv";

                    String priceString = String.valueOf(priceTotal);
                    String customerName = JOptionPane.showInputDialog("Enter your name:");
                    try {
                        System.out.println("Huidige directory: " + System.getProperty("user.dir"));
                        FileWriter writer = new FileWriter(filePath);
                        writer.write(customerName + "," + priceTotal + "," + totalOfCoffee + "\n");
                        writer.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    isRunning = false;
                    break;

                default:
                    System.out.println("Invalid command.");
                    break;
            }
        }
    }

    public static void ManagmentCoffeeSystem() {
        cashierManagment();
    }
}
