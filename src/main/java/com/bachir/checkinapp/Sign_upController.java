package com.bachir.checkinapp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author user
 */
public class Sign_upController implements Initializable {
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private TextField firstNameField;
    
    @FXML
    private TextField lastNameField;
    
    @FXML
    private TextField telephoneNumberField;

    @FXML
    private Label Sign_up;

    private static final String EMAIL_PATTERN =
            "^[A-Za-z0-9+_.-]+@(.+)$";

    private Pattern pattern;

    public Sign_upController() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }
    
    
    @FXML
    private void Register() throws IOException {
        
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String tel = telephoneNumberField.getText();

        if (password.length() < 6) {
            Sign_up.setText("Password must be at least 6 characters long");
            return;
        }

        if (firstName.length() == 0 || lastName.length() == 0 || email.length() == 0 || tel.length() == 0 ) {
            Sign_up.setText("All fields must be filled");
            return;
        }
        
        PrimaryController.loadClients();

        // Generate a unique ID for the new client.
        int newID = PrimaryController.getClients().size() + 1;

        // Hash the password using MD5
        String hashedPassword = hashPassword(password);

        // Write the new client data to the client.txt file.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/com/bachir/checkinapp/client.txt", true))) {
            String clientData = newID + "\t" + firstName + "\t" + lastName + "\t" + hashedPassword + "\t" + email + "\t" + tel;
            writer.write(clientData);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        App.setRoot("primary");
    }
    
    private static String hashPassword(String input) {
        int hashValue = 0;
        for (char c : input.toCharArray()) {
            hashValue = (hashValue * 31 + c) % 1000000; // Basic hash function
        }
        return String.valueOf(hashValue);
    }

    @FXML
    private void validateEmail() {
        String email = emailField.getText();
        if (isValidEmail(email)) {
            emailField.setStyle("-fx-background-color: white;");
            Sign_up.setText("");
        } else {
            emailField.setStyle("-fx-background-color: #FFC0CB;");
            Sign_up.setText("Invalid email address");
        }
    }

    private boolean isValidEmail(String email) {
        return pattern.matcher(email).matches();
    }

    

    @FXML
    private void LoginPage() throws IOException {
        App.setRoot("primary");
    }

}
