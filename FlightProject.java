import java.util.Scanner;
import java.io.*;
import java.time.LocalDate;

class RegistrationPage {

    public void registrationPage() {
        Scanner sc = new Scanner(System.in);
        ClearConsole clear = new ClearConsole();
        
        int option;
        boolean isValidOption = false;

        while (isValidOption == false) {
            System.out.println("\n\n");
            System.out.println("\n\n\n\n\t\t\t\t\t\t\t1. Registration\n\t\t\t\t\t\t\t2. Login\n\t\t\t\t\t\t\t3. Exit");
            System.out.print("\n\t\t\t\t\t\t\tEnter Your Choice: ");
            option = sc.nextInt();
            if (option == 1) {
                isValidOption = true;
                clear.clearConsole();
                register();
            }
            else if (option == 2) {
                isValidOption = true;
                clear.clearConsole();
                login();
            }
            else if (option == 3) {
                System.exit(0);
            }
        }
    }

    private void register() {
        Scanner sc = new Scanner(System.in);
        ClearConsole clear = new ClearConsole();

        boolean isSuccessfullyRegistered = true;

        System.out.print("\n\n\n\t\t\t\t\t\tEnter a Username: ");
        String userName = sc.nextLine();

        System.out.print("\n\t\t\t\t\t\tEnter a Password: ");
        String userPassword = sc.nextLine();

        try (FileWriter writer = new FileWriter("User_Registration.txt", true)) {
            writer.write(userName + " " + userPassword + "\n");
        } catch (IOException e) {
            isSuccessfullyRegistered = false;
            System.out.println("\n\n\n\t\t\t\t\t*** An error occurred while writing to the file ***");
            e.printStackTrace();
        }

        if (isSuccessfullyRegistered) {
            System.out.println("\n\n\n\t\t\t\t\t\t*** Registration Successful ***");
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException e) {
                System.out.println("Error: " + e.getMessage());
            }
            clear.clearConsole();
            System.out.println("\n\n\n\t\t\t\t\t\tDo You Want to Login?");
            System.out.println("\n\n\t\t\t\t\t\t1. Yes\n\t\t\t\t\t\t2. No");

            System.out.print("\n\n\t\t\t\t\t\tEnter Your Choice: ");
            int choice = sc.nextInt();
            if (choice == 1) {
                clear.clearConsole();
                login();
            } else {
                System.exit(0);
            }
        }
    }

    private void login() {
        Scanner sc = new Scanner(System.in);
        MainMenu mainMenu = new MainMenu();
        ClearConsole clear = new ClearConsole();
        
        boolean isAuthenticateUser = false;

        System.out.print("\n\n\n\t\t\t\t\t\tEnter Your Username: ");
        String userName = sc.nextLine();

        System.out.print("\n\t\t\t\t\t\tEnter Your Password: ");
        String userPassword = sc.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader("User_Registration.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(" ");
                if (credentials[0].equals(userName) && credentials[1].equals(userPassword)) {
                    isAuthenticateUser = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("\n\n\n\t\t\t\t\t\t*** Error: Unable to process login ***");
        }

        if (isAuthenticateUser) {
            System.out.println("\n\n\t\t\t\t\t\t  *** Login Successful ***");
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException e) {
                System.out.println("Error: " + e.getMessage());
            }
            clear.clearConsole();
            mainMenu.menu();

        }
        else {
            System.out.println("\n\n\t\t\t\t\t\t*** Login Failed ***");
            System.out.println("\n\n\t\t\t\t\t\tDo You Want to Try Again?\n\n\t\t\t\t\t\t1. Yes\n\t\t\t\t\t\t2. No");
            int choice = sc.nextInt();
            if (choice == 1) {
                login();
            } else {
                System.exit(0);
            }
        }
    }
}

public class FlightProject {
    public static void main(String args[]) {
        RegistrationPage r1 = new RegistrationPage();
        r1.registrationPage();
    }
}

class TicketBooking {
    private Scanner scanner;

    public TicketBooking() {
        scanner = new Scanner(System.in);
    }

    public void bookTicket() {
        MainMenu menu = new MainMenu();
        ClearConsole clear = new ClearConsole();

        System.out.println("\n\n\n\t\t\t\t\t\t\t*** Book Ticket ***");

        String username, firstName, lastName, passportNo, mobileNo, destinationFrom, destinationTo, seatNo;
        String flightTime = "", price = "";

        System.out.print("\n\n\t### Enter Your Username: ");
        username = scanner.nextLine();

        System.out.println("\n\n\n\t\t\t\t\t\t*** Provide Passenger's Information ***");
        System.out.print("\n\n\t### Enter First Name: ");
        firstName = scanner.nextLine();

        System.out.print("\n\t### Enter Last Name: ");
        lastName = scanner.nextLine();

        System.out.print("\n\t### Enter Passport/NID No: ");
        passportNo = scanner.nextLine();

        System.out.print("\n\t### Enter Mobile No: ");
        mobileNo = scanner.nextLine();

        destinationFrom = getValidDestination("From");
        destinationTo = getValidDestination("To");

        File flightFile = new File("Flight_Schedule.txt");
        if (!flightFile.exists() || flightFile.length() == 0) {
            
            System.out.println("\n\n\n\t\t\t\t\t*** No flights available in the schedule. Booking failed ***");
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException e) {
                System.out.println("Error: " + e.getMessage());
            }
            clear.clearConsole();
            menu.menu();
        }

        boolean validRoute = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(flightFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] flightDetails = line.split(" ");
                if (flightDetails.length >= 4 && flightDetails[0].equals(destinationFrom) && flightDetails[1].equals(destinationTo)) {
                    flightTime = flightDetails[2];
                    price = flightDetails[3];
                    validRoute = true;
                    break;
                }
            }
        } 
        catch (IOException e) {
            System.out.println("\n\n\n\t\t\t\t\t\t!!! Error reading flight schedule file !!!");
            clear.clearConsole();
            menu.menu();
        }

        if (!validRoute) {
            System.out.println("\n\n\n\t\t\t\t\t*** No available route found ***");
            clear.clearConsole();
            menu.menu();
        }

        LocalDate travelDate = getValidTravelDate();
        seatNo = getValidSeat(destinationFrom, destinationTo, travelDate);

        saveBookingToFile(username, firstName, lastName, passportNo, mobileNo, destinationFrom, destinationTo, seatNo, flightTime, price, travelDate);

        System.out.printf("\n\n\n\t\t\t\t\t\t*** Ticket Booking Successful ***\n\n\n\t### Ticket Price: %s\n\t### Flight Time: %s\n", price, flightTime);
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
        clear.clearConsole();
        menu.menu();
    }

    private String getValidDestination(String prompt) {
        System.out.printf("\n\t### Enter Destination %s: ", prompt);
        return scanner.nextLine();
    }

    private LocalDate getValidTravelDate() {
        LocalDate today = LocalDate.now();
        LocalDate minDate = today.plusDays(1);
        LocalDate maxDate = today.plusDays(30);

        System.out.printf("\n\t### Enter Travel Date Between (%s) and (%s): ", minDate, maxDate);

        while (true) {
            try {
                System.out.print("\n\n\t### Day: ");
                int day = Integer.parseInt(scanner.nextLine());

                System.out.print("\n\t### Month: ");
                int month = Integer.parseInt(scanner.nextLine());

                System.out.print("\n\t### Year: ");
                int year = Integer.parseInt(scanner.nextLine());

                LocalDate travelDate = LocalDate.of(year, month, day);
                if (!travelDate.isBefore(minDate) && !travelDate.isAfter(maxDate)) {
                    return travelDate;
                } else {
                    System.out.printf("\n\t### Invalid date! Enter a date between (%s) and (%s): ", minDate, maxDate);
                }
            } catch (Exception e) {
                System.out.println("\n\n\n\t\t\t\t\t\t!!! Invalid input. Please enter a valid date !!!");
            }
        }
    }

    private String getValidSeat(String destinationFrom, String destinationTo, LocalDate travelDate) {
        String[] seatLayout = {
                "A1 A2   A3 A4   A5 A6",
                "B1 B2   B3 B4   B5 B6",
                "C1 C2   C3 C4   C5 C6",
                "D1 D2   D3 D4   D5 D6",
                "E1 E2   E3 E4   E5 E6",
                "F1 F2   F3 F4   F5 F6"
        };

        while (true) {
            System.out.println("\n\n\n\t\t\t\t\t\t    *** Select a Seat From Below ***\n\n");
            for (String row : seatLayout) {
                System.out.println("\n\t\t\t\t\t\t");
                System.out.println("\t\t\t\t\t\t\t"+row);
            }

            System.out.print("\n\n\t### Enter Seat Number: ");
            String seatNo = scanner.nextLine();

            boolean seatTaken = false;

            try (BufferedReader reader = new BufferedReader(new FileReader("Passenger_Booking.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] bookingDetails = line.split(" ");
                    if (bookingDetails.length >= 13
                            && bookingDetails[5].equals(destinationFrom)
                            && bookingDetails[6].equals(destinationTo)
                            && Integer.parseInt(bookingDetails[10]) == travelDate.getDayOfMonth()
                            && Integer.parseInt(bookingDetails[11]) == travelDate.getMonthValue()
                            && Integer.parseInt(bookingDetails[12]) == travelDate.getYear()
                            && bookingDetails[7].equals(seatNo)) {
                            seatTaken = true;
                            break;
                    }
                }
            } catch (IOException e) {
                System.out.println("\n\n\n\t\t\t\t\t!!! Error reading passenger booking file !!!");
            }

            if (seatTaken) {
                System.out.println("\n\n\n\t\t\t\t\t\t!!! Seat Already Booked. Try Another One !!!");
            } else {
                return seatNo;
            }
        }
    }

    private void saveBookingToFile(String username, String firstName, String lastName, String passportNo,
                                   String mobileNo, String destinationFrom, String destinationTo,
                                   String seatNo, String flightTime, String price, LocalDate travelDate) {
        ClearConsole clear = new ClearConsole();
        MainMenu menu = new MainMenu();
        try (FileWriter writer = new FileWriter("Passenger_Booking.txt", true)) {
            writer.write(String.join(" ",
                    username, firstName, lastName, passportNo, mobileNo,
                    destinationFrom, destinationTo, seatNo, flightTime, price,
                    String.valueOf(travelDate.getDayOfMonth()), String.valueOf(travelDate.getMonthValue()), String.valueOf(travelDate.getYear())
            ));
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("\n\n\n\t\t\t\t!!! Error saving ticket booking. Please try again !!!");
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException i) {
                System.out.println("Error: " + i.getMessage());
            }
            clear.clearConsole();
            menu.menu();
        }
    }

    public void cancelTicket() {
        MainMenu menu = new MainMenu();
        ClearConsole clear = new ClearConsole();
        System.out.println("\n\n\n\t\t\t\t\t\t\t*** Cancel Ticket ***");

        System.out.print("\n\n\n\t### Enter Passport/NID No: ");
        String passportNo = scanner.nextLine();
        boolean found = false;

        try (
                BufferedReader reader = new BufferedReader(new FileReader("Passenger_Booking.txt"));
                FileWriter tempWriter = new FileWriter("Temporary.txt", true)
        ) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(" ");
                if (details.length >= 13 && details[3].equals(passportNo)) {
                    found = true;
                    System.out.printf("\n\t### Passenger Details:\n\n\t### Name: %s %s\n\t### Passport/NID No: %s\n\t### Mobile No: %s\n\t### Route: %s to %s\n\t### Travel Date: %02d-%02d-%04d\n\t### Seat No: %s\n\t### Ticket Price: %s\n\t### Flight Time: %s\n",
                            details[1], details[2], details[3], details[4], details[5], details[6],
                            Integer.parseInt(details[10]), Integer.parseInt(details[11]), Integer.parseInt(details[12]),
                            details[7], details[9], details[8]);
                } else {
                    tempWriter.write(line + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("\n\n\n\t\t\t\t\t\t!!! Error processing files: " + e.getMessage()+" !!!");
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException i) {
                System.out.println("Error: " + i.getMessage());
            }
            clear.clearConsole();
            menu.menu();
        }

        if (!found) {
            new File("Temporary.txt").delete();
            System.out.println("\n\n\n\t\t\t\t\t\t!!! No Passenger Found !!!");
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException i) {
                System.out.println("Error: " + i.getMessage());
            }
            clear.clearConsole();
            menu.menu();
        }

        System.out.println("\n\n\tDo you want to cancel this ticket?");
        System.out.println("\t1. Yes");
        System.out.println("\t2. No");
        System.out.print("\tEnter Your Choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        if (choice == 1) {
            if (!new File("Passenger_Booking.txt").delete()) {
                System.out.println("\n\n\n\t\t\t\t\t\tError deleting the original booking file.");
                try {
                    Thread.sleep(3000);
                }
                catch (InterruptedException i) {
                    System.out.println("Error: " + i.getMessage());
                }
                clear.clearConsole();
                menu.menu();
            }
            if (!new File("Temporary.txt").renameTo(new File("Passenger_Booking.txt"))) {
                System.out.println("\n\n\n\t\t\t\t\t\tError renaming the temporary file.");
                try {
                    Thread.sleep(3000);
                }
                catch (InterruptedException i) {
                    System.out.println("Error: " + i.getMessage());
                }
                clear.clearConsole();
                menu.menu();
            }
            System.out.println("\n\n\t\t\t\t\t\t*** Ticket Canceled Successfully ***");
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException i) {
                System.out.println("Error: " + i.getMessage());
            }
            clear.clearConsole();
            menu.menu();
        }
        else {
            new File("Temporary.txt").delete();
            System.out.println("\n\n\t\t\t\t\t\tTicket cancellation aborted.");
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException i) {
                System.out.println("Error: " + i.getMessage());
            }
            clear.clearConsole();
            menu.menu();
        }
    }
}

class FlightScheduleManagement {
    private Scanner scanner;

    public FlightScheduleManagement(){
        scanner = new Scanner(System.in);
    }
    public void addFlight() {
        MainMenu menu = new MainMenu();
        ClearConsole clear = new ClearConsole();

        System.out.println("\n\n\n\t\t\t\t\t\t*** Add Flight Details ***");

        String from, to, flightTime, price;

        while (true) {
            System.out.print("\n\n\n\t### From: ");
            from = scanner.nextLine();

            System.out.print("\n\t### To: ");
            to = scanner.nextLine();

            System.out.print("\n\t### Flight Time: ");
            flightTime = scanner.nextLine();

            System.out.print("\n\t### Price: ");
            price = scanner.nextLine();

            if (isFlightExist(from, to)) {
                System.out.println("\n\n\n\t\t\t\t\t\t*** Flight Already Exists. Try Again ***");
            } else {
                break;
            }
        }

        boolean check = false;
        try (FileWriter writer = new FileWriter("Flight_Schedule.txt", true)) {
            writer.write(from+" "+to+" "+flightTime+" "+price+"\n");
            check = true;
        }
        catch (IOException e) {
            System.out.println("\n\n\n\t\t\t\t\t\tError saving flight schedule.");
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException i) {
                System.out.println("Error: " + i.getMessage());
            }
            clear.clearConsole();
            menu.menu();
        }
        if(check){
            System.out.println("\n\n\n\t\t\t\t\t\t*** Flight Added Successfully ***");
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException i) {
                System.out.println("Error: " + i.getMessage());
            }
            clear.clearConsole();
            menu.menu();
        }
    }

    public void viewFlightSchedule() {
        MainMenu menu = new MainMenu();
        ClearConsole clear = new ClearConsole();

        System.out.println("\n\n\n\t\t\t\t\t\t*** All Flight Schedules ***");

        try (BufferedReader reader = new BufferedReader(new FileReader("Flight_Schedule.txt"))) {
            String line;
            int count = 1;

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(" ");
                if (details.length >= 4) {
                    System.out.printf("\n\n\n\t### Flight %d Details: %s to %s at %s (Everyday)\n\t### 1Price: %s\n",
                            count, details[0], details[1], details[2], details[3]);
                    count++;
                }
            }

            if (count == 1) {
                System.out.println("\n\n\n\t\t\t\t\t\t*** No flights available ***");
                try {
                    Thread.sleep(3000);
                }
                catch (InterruptedException i) {
                    System.out.println("Error: " + i.getMessage());
                }
            }
        }
        catch (IOException e) {
            System.out.println("\n\n\n\t\t\t\t\t!!! Error reading flight schedule file !!!");
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException i) {
                System.out.println("Error: " + i.getMessage());
            }
        }
        System.out.print("\n\n\tPress 1 to back to the main menu: ");
        int option = scanner.nextInt();
        if(option==1){
             try {
                Thread.sleep(100);
            }
            catch (InterruptedException i) {
                System.out.println("Error: " + i.getMessage());
            }
            clear.clearConsole();
            menu.menu();
            }
        else{
            System.exit(0);
        }
    }

    public void removeFlight() {
        MainMenu menu = new MainMenu();
        ClearConsole clear = new ClearConsole();

        System.out.println("\n\n\n\t\t\t\t\t\t\t*** Remove Flight ***");

        System.out.print("\n\n\t### Enter From: ");
        String from = scanner.nextLine();

        System.out.print("\n\t### Enter To: ");
        String to = scanner.nextLine();

        boolean flightFound = false;

        try (
                BufferedReader reader = new BufferedReader(new FileReader("Flight_Schedule.txt"));
                FileWriter tempWriter = new FileWriter("Temporary_1.txt", true)
        ) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(" ");
                if (details.length >= 4 && details[0].equals(from) && details[1].equals(to)) {
                    flightFound = true;
                    System.out.printf("\n\n\n\t### Flight Details: %s to %s at %s\n\t### Price: %s\n\n", details[0], details[1], details[2], details[3]);
                }
                else {
                    tempWriter.write(line + System.lineSeparator());
                }
            }
        }
        catch (IOException e) {
            System.out.println("\n\n\n\t\t\t\t\t\t!!! Error processing flight schedule file !!!");
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException i) {
                System.out.println("Error: " + i.getMessage());
            }
            clear.clearConsole();
            menu.menu();
        }

        if (!flightFound) {
            new File( "Temporary_1.txt").delete();
            System.out.println("\n\n\n\t\t\t\t\t\t*** No Flight Found ***");
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException i) {
                System.out.println("Error: " + i.getMessage());
            }
            clear.clearConsole();
            menu.menu();
        }

        System.out.println("\n\n\tDo you want to remove this flight?");
        System.out.println("\t1. Yes\n\t2. No");
        System.out.print("\tEnter Your Choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        if (choice == 1) {
            if (!new File("Flight_Schedule.txt").delete()) {
                System.out.println("\n\n\n\t\t\t\t\t\t!!! Error deleting the original flight schedule file !!!");
                try {
                    Thread.sleep(3000);
                }
                catch (InterruptedException i) {
                    System.out.println("Error: " + i.getMessage());
                }
                clear.clearConsole();
                menu.menu();
            }
            
            if (!new File("Temporary_1.txt").renameTo(new File("Flight_Schedule.txt"))) {
                System.out.println("\n\n\n\t\t\t\t\t\t!!! Error renaming the temporary file to flight schedule !!!");
                try {
                    Thread.sleep(3000);
                }
                catch (InterruptedException i) {
                    System.out.println("Error: " + i.getMessage());
                }
                clear.clearConsole();
                menu.menu();
            }
            System.out.println("\n\n\n\t\t\t\t\t\t\t*** Flight Removed Successfully ***");
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException i) {
                System.out.println("Error: " + i.getMessage());
            }
            clear.clearConsole();
            menu.menu();          
        }
        else {
            new File( "Temporary_1.txt").delete();
            System.out.println("\n\n\n\t\t\t\t\t\t\t*** Flight Removal Canceled ***");
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException i) {
                System.out.println("Error: " + i.getMessage());
            }
            clear.clearConsole();
            menu.menu();
        }
    }
    
    private boolean isFlightExist(String from, String to) {
        File file = new File("Flight_Schedule.txt");

        if (!file.exists()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(" ");
                if (details.length >= 4 && details[0].equals(from) && details[1].equals(to)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("\n\n\n\t\t\t\t\t\t!!! Error reading flight schedule file !!!");
        }
        return false;
    }
}

class BookingSearch {

    public void searchBooking() {
        Scanner sc = new Scanner(System.in);
        MainMenu menu = new MainMenu();
        ClearConsole clear = new ClearConsole();

        System.out.print("\n\n\n\t\t\t\t\t\t*** Search Passenger's Booking ***");

        System.out.print("\n\n\t### Enter a Passport/NID No: ");
        String passportNo = sc.nextLine();
        System.out.println("\n\n\n");

        boolean bookingFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("Passenger_Booking.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] bookingDetails = line.split(" ");

                if (bookingDetails.length < 13) {
                    continue;
                }

                String username = bookingDetails[0];
                String firstName = bookingDetails[1];
                String lastName = bookingDetails[2];
                String passport = bookingDetails[3];
                String mobile = bookingDetails[4];
                String destinationFrom = bookingDetails[5];
                String destinationTo = bookingDetails[6];
                String seatNo = bookingDetails[7];
                String flightTime = bookingDetails[8];
                String price = bookingDetails[9];
                int day = Integer.parseInt(bookingDetails[10]);
                int month = Integer.parseInt(bookingDetails[11]);
                int year = Integer.parseInt(bookingDetails[12]);

                if (passport.equals(passportNo)) {
                    bookingFound = true;

                    System.out.println("\t### Passenger Name: " + firstName + " " + lastName);
                    System.out.println("\t### Passport/NID No: " + passport);
                    System.out.println("\t### Mobile No: " + mobile);
                    System.out.println("\t### Destination: " + destinationFrom + " to " + destinationTo);
                    System.out.printf("\t### Travelling Date: %02d-%02d-%d\n", day, month, year);
                    System.out.println("\t### Seat No: " + seatNo);
                    System.out.println("\t### Ticket Price: " + price);
                    System.out.println("\t### Flight Time (24-hour format): " + flightTime);
                    System.out.println("\n");
                }
            }
        } 
        catch (IOException e) {
            System.out.println("\n\n\t\t\t\t\t!!! Error: Unable to read the booking file !!!");
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException i) {
                System.out.println("Error: " + i.getMessage());
            }
            
        }
        if (!bookingFound) {
            System.out.println("\n\n\t\t\t\t### No booking found for Passport/NID No: " + passportNo);
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException i) {
                System.out.println("Error: " + i.getMessage());
            }
        }
    System.out.print("\n\n\tPress 1 to back to the main menu: ");
    int option = sc.nextInt();
    if(option==1){
         try {
            Thread.sleep(100);
        }
        catch (InterruptedException i) {
            System.out.println("Error: " + i.getMessage());
        }
        clear.clearConsole();
        menu.menu();
    }
    else{
        System.exit(0);
    }
    }
}

class MainMenu{
    public void menu(){
        Scanner sc = new Scanner(System.in);
        TicketBooking bookings = new TicketBooking();
        FlightScheduleManagement flightSchedule = new FlightScheduleManagement();
        BookingSearch search = new BookingSearch();
        ClearConsole clear = new ClearConsole();
        RegistrationPage regisPage = new RegistrationPage();

        boolean isValidOption = false;
        while(isValidOption == false){
            System.out.println("\n\n\n\t\t\t\t\t\t1. Book Ticket\n\t\t\t\t\t\t2. Cancel Ticket\n\t\t\t\t\t\t3. Add Flight Schedule\n\t\t\t\t\t\t4. View Flight Schedule\n\t\t\t\t\t\t5. Remove Flight Schedule\n\t\t\t\t\t\t6. Search Bookings\n\t\t\t\t\t\t7. Logout");
            System.out.print("\n\n\t\t\t\t\t\tEnter Your Choice: ");
            int option = sc.nextInt();
                if(option == 1){
                isValidOption = true;
                clear.clearConsole();
                bookings.bookTicket();
            }
            else if(option == 2){
                isValidOption = true;
                clear.clearConsole();
                bookings.cancelTicket();
            }
            else if(option == 3){
                isValidOption = true;
                clear.clearConsole();
                flightSchedule.addFlight();
            }
            else if(option == 4){
                isValidOption = true;
                clear.clearConsole();
                flightSchedule.viewFlightSchedule();
            }
            else if(option == 5){
                isValidOption = true;
                clear.clearConsole();
                flightSchedule.removeFlight();
            }
            else if(option == 6){
                isValidOption = true;
                clear.clearConsole();
                search.searchBooking();
            }
            else if(option == 7){
                isValidOption = true;
                clear.clearConsole();
                regisPage.registrationPage();
            }
        }
    }
}

class ClearConsole{
    public static void clearConsole(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}