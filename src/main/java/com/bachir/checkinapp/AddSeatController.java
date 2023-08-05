package com.bachir.checkinapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class AddSeatController implements Initializable {
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private TextField flightNumberField;

    @FXML
    private TextField classField;

    @FXML
    private TextField optionsField;
    
    @FXML
    private TextField Price;
    
    @FXML
    private TextField seatIdModify;
    
    @FXML
    private Label message;
    
    @FXML
    private TextField flightIdDelete;
    
    @FXML
    private TextField seatIdDelete;
    

    @FXML
    private void addSeat() throws IOException {
        String flightNumber = flightNumberField.getText();
        String seatClass = classField.getText();
        String options = optionsField.getText();
        String price = Price.getText();
        
        
        int seatCount = 1;
        
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/bachir/checkinapp/seat.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                String currentFlightId = parts[0].trim();
                
                if (currentFlightId.equals(flightNumber)) {
                    seatCount++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        

        // Write the new seat data to the seat.txt file.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/com/bachir/checkinapp/seat.txt", true))) {
            String seatData = flightNumber +"\t"+ seatCount + "\t" + seatClass + "\t" + price + "\tavailable" + "\t" + options;
            writer.write(seatData);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        message.setText("Successfully added");
        
        // Clear the input fields
        Price.clear();
        classField.clear();
        optionsField.clear();
    }
    
    
    @FXML
    private void modifySeat() throws IOException {
        String flightNumber = flightNumberField.getText();
        String seatNumber = seatIdModify.getText();
        String seatClass = classField.getText();
        String options = optionsField.getText();
        String price = Price.getText();
    
        List<String> linesToUpdate = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/bachir/checkinapp/seat.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                String currentFlightId = parts[0].trim();
                String currebtSeatId = parts[1].trim();

                if (currentFlightId.equals(flightNumber) && currebtSeatId.equals(seatNumber)) {
                    found = true;
                    // Modify the seat data as needed
                    String modifiedSeatData = flightNumber + "\t" + seatNumber + "\t" + seatClass + "\t" + price + "\t" + "available" + "\t" + options;
                    linesToUpdate.add(modifiedSeatData);
                } else {
                    linesToUpdate.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rewrite the seat.txt file with the modified seat
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/com/bachir/checkinapp/seat.txt"))) {
            for (String line : linesToUpdate) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if(!found){ message.setText("Not Found"); }
        else{ message.setText("Successfully modified"); }
        
        
    }
    
    
    
    @FXML
    private void Delete() throws IOException {
        String flightNumber = flightIdDelete.getText();
        String seatNumber = seatIdDelete.getText();

        List<String> linesToKeep = new ArrayList<>();
        boolean found = false;
        
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/bachir/checkinapp/seat.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                String currentFlightId = parts[0].trim();
                String currentSeatId = parts[1].trim();

                if (!currentFlightId.equals(flightNumber) || !currentSeatId.equals(seatNumber)) {
                    linesToKeep.add(line);
                }
                else{
                    found = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rewrite the seat.txt file without the deleted seat
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/com/bachir/checkinapp/seat.txt"))) {
            for (String line : linesToKeep) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if(!found){ message.setText("Not Found"); }
        else{ message.setText("Successfully deleted"); }
        
    }
    
    
    @FXML
    private void homePage() throws IOException {
        App.setRoot("secondary");
    }
    
}