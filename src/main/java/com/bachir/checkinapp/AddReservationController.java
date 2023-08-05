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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;


public class AddReservationController implements Initializable {
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    private Seat selectedSeat;
    
    @FXML
    private Label message;
    
    @FXML
    private TextField flightNumberField;
    
    @FXML
    private GridPane seatGridPane;
            
    @FXML
    private void showSeats() throws IOException {
        String flightNumber = flightNumberField.getText();
        
        List<Seat> seats = new ArrayList<>();
        boolean found = false;
        
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/bachir/checkinapp/seat.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                String currentFlightId = parts[0].trim();
                
                if (currentFlightId.equals(flightNumber)) {
                    found = true;
                    // Assuming Seat constructor takes appropriate arguments based on seat.txt format
                    Seat seat = new Seat(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                    seats.add(seat);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if(!found) {
            message.setText("Not Found");
        }
        else{
            message.setText("");
        }
        
        int rowCount = 0;
        int colCount = 0;
        int MAX_COLUMNS = 4;

        ToggleGroup toggleGroup = new ToggleGroup(); // Create a toggle group for radio buttons

        for (Seat seat : seats) {

            if (colCount == MAX_COLUMNS / 2) {
                RadioButton emptyRadioButton = new RadioButton();
                emptyRadioButton.setVisible(false);
                seatGridPane.add(emptyRadioButton, colCount, rowCount);
                colCount++;
            }
            RadioButton radioButton = new RadioButton();
            if (seat.getAvailability().equals("reserved")) {
                radioButton.setDisable(true);
            }
            radioButton.setToggleGroup(toggleGroup); // Set the radio button to the toggle group
            radioButton.setOnAction(event -> handleRadioButtonAction(radioButton, seat));
            radioButton.setText(seat.getSeatNumber()); // Set the seat number as radio button label
            seatGridPane.add(radioButton, colCount, rowCount);

            colCount++;

            if (colCount == MAX_COLUMNS + 1) {
                colCount = 0;
                rowCount++;
            }
        }
        
    }
    
    
    private void handleRadioButtonAction(RadioButton radioButton, Seat seat) {
        if (radioButton.isSelected()) {
            selectedSeat = seat;
            // Radio button is selected, display seat information
            message.setText("Seat Number: " + seat.getSeatNumber() +
                    "\nClass: " + seat.getSeatClass() +
                    "\nOptions: " + seat.getOptions() +
                    "\nPrice: " + seat.getPrice() + "$");
        } else {
            // Radio button is deselected, clear seat information
            message.setText("");
        }
    }
    
    
    @FXML
    private void homePage() throws IOException {
        App.setRoot("secondary");
    }
    
    @FXML
    private void submit() throws IOException {
        
        int ReservationCount = 1;
        
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/bachir/checkinapp/reservation.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                String currentFlightId = parts[0].trim();
                
                if (currentFlightId.equals(selectedSeat.getFlightNumber())) {
                    ReservationCount++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        

        // Write the new seat data to the seat.txt file.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/com/bachir/checkinapp/reservation.txt", true))) {
            String seatData = selectedSeat.getFlightNumber() +"\t"+ ReservationCount + "\t" + PrimaryController.getClient().getID() + "\t" + selectedSeat.getSeatNumber();
            writer.write(seatData);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }       
        
    
        List<String> linesToUpdate = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/bachir/checkinapp/seat.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                String currentFlightId = parts[0].trim();
                String currebtSeatId = parts[1].trim();

                if (currentFlightId.equals(selectedSeat.getFlightNumber()) && currebtSeatId.equals(selectedSeat.getSeatNumber())) {
                    found = true;
                    // Modify the seat data as needed
                    String modifiedSeatData = selectedSeat.getFlightNumber() + "\t" + selectedSeat.getSeatNumber() + "\t" + selectedSeat.getSeatClass() + "\t" + selectedSeat.getPrice() + "\t" + "reserved" + "\t" + selectedSeat.getOptions();
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
        
        
        
        
        App.setRoot("secondary");
    }
    
}