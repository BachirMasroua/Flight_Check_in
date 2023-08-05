package com.bachir.checkinapp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PrimaryController {
    
    private static List<Client> clients = new ArrayList<>();
    private static Client authenticatedClient = null;
    
    public static Client getClient(){
        return authenticatedClient;
    }
    
    public static List<Client> getClients(){
        return clients;
    }
    
    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Label tryLogin;
    
    
    
    private static List<String> readDataFromFile(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
    
    public static void loadClients() {
        List<String> clientData = readDataFromFile("src/main/resources/com/bachir/checkinapp/client.txt");
        for (String line : clientData) {
            String[] parts = line.split("\\s+");
            int ID = Integer.parseInt(parts[0].trim());
            String firstName = parts[1].trim();
            String lastName = parts[2].trim();
            String password = parts[3].trim();
            String email = parts[4].trim();
            String tel = parts[5].trim();
            Client client = new Client(ID, firstName, lastName, password, tel, email);
            clients.add(client);
        }
    }

    @FXML
    private void switchToSecondary() throws IOException {
        loadClients();
        String email = emailField.getText();
        String password = passwordField.getText();

        // Hash the provided password using the same algorithm as during registration
        String hashedPassword = hashPassword(password);

        // Find the client with the provided email and hashed password
        authenticatedClient = clients.stream()
                .filter(client -> client.getEmail().equals(email) && client.getPassword().equals(hashedPassword))
                .findFirst()
                .orElse(null);

        if (authenticatedClient != null) {
            // User authentication successful
            System.out.println("User logged in successfully!");
            System.out.println("Welcome, " + authenticatedClient.getFirstName() + " " + authenticatedClient.getLastName());
            App.setRoot("secondary");
        } else {
            // User authentication failed
            tryLogin.setText("Invalid email or password. Please try again.");
        }
    }
    
    private static String hashPassword(String input) {
        int hashValue = 0;
        for (char c : input.toCharArray()) {
            hashValue = (hashValue * 31 + c) % 1000000; // Basic hash function
        }
        return String.valueOf(hashValue);
    }
    
    @FXML
    private void signup() throws IOException {
        App.setRoot("Sign_up");
    }
}
