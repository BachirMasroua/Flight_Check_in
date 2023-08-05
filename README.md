# Flight Check In Database Documentation

## Overview

The Flight Check In application utilizes a simple file-based database to manage client information, seat availability, and reservations. The database is composed of three main files: `client.txt`, `seat.txt`, and `reservation.txt`. This documentation provides an overview of the database structure, tables, and relationships.

## File-Based Database

### client.txt

The `client.txt` file stores information about clients who use the Flight Check In application. Each line in the file represents a single client and contains the following fields:

- `clientId`: Unique identifier for the client.
- `firstName`: First name of the client.
- `lastName`: Last name of the client.
- `password`: Hashed password of the client.
- `email`: Email address of the client.
- `phoneNumber`: Phone number of the client.

Example `client.txt` entry:
1 John Doe 5f4dcc3b5aa765d61d8327deb882cf99 john.doe@example.com 123-456-7890
2 Jane Smith d5bfa27282c0b04b8c73337c17524a1d jane.smith@example.com 987-654-3210

### seat.txt

The `seat.txt` file contains information about available seats on different flights. Each line in the file represents a single seat and includes the following fields:

- `flightNumber`: Unique identifier for the flight.
- `seatNumberForTheFlight`: Seat number for the flight.
- `seatClass`: Class of the seat (e.g., Economy, Business).
- `priceWithoutTax`: Price of the seat without tax.
- `availability`: Availability status of the seat (e.g., Available, Reserved).
- `options`: Additional options associated with the seat.

Example `seat.txt` entry:
FL123 1A Economy 200 Available Window seat
FL123 2B Business 400 Reserved Extra legroom

### reservation.txt

The `reservation.txt` file maintains records of reservations made by clients. Each line in the file represents a single reservation and includes the following fields:

- `flightNumber`: Identifier of the flight.
- `reservationNumberForTheFlight`: Unique identifier for the reservation.
- `clientId`: Identifier of the client making the reservation.
- `seatNumberForTheFlight`: Seat number reserved for the flight.

Example `reservation.txt` entry:
FL123 R123 1 1A
FL123 R124 2 2B

## Relationships

- Clients in the `client.txt` file are related to reservations in the `reservation.txt` file through the `clientId` field.
- Seats in the `seat.txt` file are related to reservations in the `reservation.txt` file through the combination of `flightNumber` and `seatNumberForTheFlight` fields.

## Conclusion

The Flight Check In application's database is organized into three files: `client.txt`, `seat.txt`, and `reservation.txt`. These files collectively store client information, seat availability, and reservation records. The relationships between clients, seats, and reservations provide a foundation for managing flight check-in operations.
