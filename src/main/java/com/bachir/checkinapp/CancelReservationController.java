package com.bachir.checkinapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;



public class CancelReservationController implements Initializable {
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadReservations();
    }
    
    private List<Seat> checkedSeats = new ArrayList<>();
    
    
    @FXML
    private VBox reservationVBox;
    
    
    @FXML
    private void homePage() throws IOException {
        App.setRoot("secondary");
    }
    
    private void loadReservations() {
        Client user = PrimaryController.getClient();
        String userId = String.valueOf(user.getID());

        List<Seat> userSeats = new ArrayList<>();

        try (BufferedReader readerR = new BufferedReader(new FileReader("src/main/resources/com/bachir/checkinapp/reservation.txt"))) {
            String lineR;
            while ((lineR = readerR.readLine()) != null) {
                String[] partsR = lineR.split("\\s+");
                String flightNumber = partsR[0].trim();
                String ClientNumber = partsR[2].trim();
                String seatNumber = partsR[3].trim();

                if (ClientNumber.equals(userId)) {
                    try (BufferedReader readerS = new BufferedReader(new FileReader("src/main/resources/com/bachir/checkinapp/seat.txt"))) {
                        String lineS;
                        while ((lineS = readerS.readLine()) != null) {
                            String[] partsS = lineS.split("\\s+");
                            String currentFlightId = partsS[0].trim();
                            String currentSeatId = partsS[1].trim();

                            if (currentFlightId.equals(flightNumber) && currentSeatId.equals(seatNumber)) {
                                String _class = partsS[2].trim();
                                String price = partsS[3].trim();
                                String options = partsS[5].trim();
                                
                                Seat seat = new Seat(flightNumber, seatNumber, _class, price, "reserved", options);
                                userSeats.add(seat);
                                
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Seat seat : userSeats) {
            CheckBox checkBox = new CheckBox("Flight number: " + seat.getFlightNumber() + " | Seat number: " + seat.getSeatNumber() + " | Class : " + seat.getClass() + " | Price : " + seat.getPrice() + "$ | Options : " + seat.getOptions() );
            checkBox.setOnAction(event -> handleCheckBoxAction(checkBox, seat));
            reservationVBox.getChildren().add(checkBox);
        }
    }
    
    private void handleCheckBoxAction(CheckBox checkBox, Seat seat) {
        if (checkBox.isSelected()) {
            checkedSeats.add(seat);
            
        } else {
            checkedSeats.remove(seat);
        }
    }
    
    @FXML
    private void submit() throws IOException {
        updateReservations();
        updateSeats();
    }
    
    @FXML
    private void updateReservations() throws IOException {
        try {
            File reservationFile = new File("src/main/resources/com/bachir/checkinapp/reservation.txt");
            BufferedReader reader = new BufferedReader(new FileReader(reservationFile));
            List<String> lines = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                String currentFlightNumber = parts[0].trim();
                String currentSeatNumber = parts[3].trim();
                boolean matchFound = false;

                for (Seat seat : checkedSeats) {
                    if (seat.getFlightNumber().equals(currentFlightNumber) && seat.getSeatNumber().equals(currentSeatNumber)) {
                        matchFound = true;
                        break;
                    }
                }

                if (!matchFound) {
                    lines.add(line);
                }
            }

            reader.close();

            // Write updated data back to the reservation file
            BufferedWriter writer = new BufferedWriter(new FileWriter(reservationFile));
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateSeats() throws IOException {
        try {
            File seatFile = new File("src/main/resources/com/bachir/checkinapp/seat.txt");
            BufferedReader reader = new BufferedReader(new FileReader(seatFile));
            List<String> lines = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                String currentFlightNumber = parts[0].trim();
                String currentSeatNumber = parts[1].trim();

                for (Seat seat : checkedSeats) {
                    if (seat.getFlightNumber().equals(currentFlightNumber) && seat.getSeatNumber().equals(currentSeatNumber)) {
                        // Modify the availability field
                        parts[4] = "available";
                        break;
                    }
                }

                lines.add(String.join(" ", parts));
            }

            reader.close();

            // Write updated data back to the seat file
            BufferedWriter writer = new BufferedWriter(new FileWriter(seatFile));
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        App.setRoot("secondary");
    }

}







