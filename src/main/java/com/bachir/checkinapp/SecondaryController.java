package com.bachir.checkinapp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SecondaryController {
    
    
    @FXML
    private Label WelcomClient;
    
    @FXML
    private Button NewReservation;
    @FXML
    private Button CancelReservation;
    @FXML
    private Button Addaseat;
    @FXML
    private Button generatePdf;
    
    
    @FXML
    public void initialize() {
        Client authenticatedClient = PrimaryController.getClient();
        WelcomClient.setText( "Welcome " + authenticatedClient.getFirstName());
        if(authenticatedClient.getID() == 1){
            NewReservation.setVisible(false);
            CancelReservation.setVisible(false);
            generatePdf.setVisible(false);
        }
        else{
            Addaseat.setVisible(false);
        }
    }
    
    @FXML
    private void newReservation() throws IOException {
        App.setRoot("addReservation");
    }
    
    @FXML
    private void newSeat() throws IOException {
        App.setRoot("addSeat");
    }
    
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    
    @FXML
    private void cancelReservation() throws IOException {
        App.setRoot("cancelReservation");
    }
    
    @FXML
    private void generatePDF() throws IOException {
        try {
            Client client = PrimaryController.getClient();
            List<Seat> selectedSeats = getReservedSeatsByClient(client);

            // Sort selectedSeats by price in ascending order
            selectedSeats.sort(Comparator.comparingDouble(seat -> Double.parseDouble(seat.getPrice())));

            FileWriter fileWriter = new FileWriter( client.getFullName() + ".txt");

            // Write user information
            fileWriter.write("User Information:\n");
            fileWriter.write("Client ID: " + client.getID() + "\n");
            fileWriter.write("Name: " + client.getFirstName() + " " + client.getLastName() + "\n");
            fileWriter.write("Email: " + client.getEmail() + "\n");
            fileWriter.write("Phone: " + client.getTel() + "\n\n");

            // Write reservation details
            fileWriter.write("Reservation Details:\n");
            double totalPrice = 0;
            double totalPriceWithTax = 0;

            for (Seat seat : selectedSeats) {
                fileWriter.write("Flight Number: " + seat.getFlightNumber() + "\n");
                fileWriter.write("Seat Number: " + seat.getSeatNumber() + "\n");
                double price = Double.parseDouble(seat.getPrice());
                double priceWithTax = price * 1.11; // Add 11% tax
                fileWriter.write("Price (Before Tax): $" + price + "\n");
                fileWriter.write("Price (After Tax): $" + priceWithTax + "\n");
                fileWriter.write("Options: " + seat.getOptions() + "\n\n");

                totalPrice += price;
                totalPriceWithTax += priceWithTax;
            }

            fileWriter.write("Total Price (Before Tax): $" + totalPrice + "\n");
            fileWriter.write("Total Price (After Tax): $" + totalPriceWithTax + "\n");

            fileWriter.close();

            System.out.println("Txt file generated successfully.");
            
            Platform.exit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Seat> getReservedSeatsByClient(Client client) {
        List<Seat> reservedSeats = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/bachir/checkinapp/reservation.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                String flightNumber = parts[0].trim();
                String clientId = parts[2].trim();
                String seatNumber = parts[3].trim();

                if (clientId.equals(String.valueOf(client.getID()))) {
                    try (BufferedReader seatReader = new BufferedReader(new FileReader("src/main/resources/com/bachir/checkinapp/seat.txt"))) {
                        String seatLine;
                        while ((seatLine = seatReader.readLine()) != null) {
                            String[] seatParts = seatLine.split("\\s+");
                            String currentFlightNumber = seatParts[0].trim();
                            String currentSeatNumber = seatParts[1].trim();

                            if (currentFlightNumber.equals(flightNumber) && currentSeatNumber.equals(seatNumber)) {
                                String _class = seatParts[2].trim();
                                String price = seatParts[3].trim();
                                String availability = seatParts[4].trim();
                                String options = seatParts[5].trim();

                                Seat seat = new Seat(flightNumber, seatNumber, _class, price, availability, options);
                                reservedSeats.add(seat);
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

        return reservedSeats;
    }
    
}