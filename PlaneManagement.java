import java.util.Scanner;
import java.util.InputMismatchException;

public class PlaneManagement {

    static Scanner input = new Scanner(System.in);
    static String[][] seatArray = new String[4][14];    // 4 rows * 14 seats
    static Ticket[] tickets = new Ticket[52];     // Assuming each seat can have a ticket

    public static void main(String[] args) {
        System.out.println(" ");
        System.out.println("Welcome to the Plane Management application");
        System.out.println(" ");

        while (true) {
            Menu();
            int option = getOptionFromUser();

            switch (option) {
                case 1:
                    buy_seat();
                    break;
                case 2:
                    cancel_seat();
                    break;
                case 3:
                    find_first_available();
                    break;
                case 4:
                    show_seating_plan();
                    break;
                case 5:
                    print_tickets_info();
                    break;
                case 6:
                    search_ticket();
                    break;
                case 0:
                    input.close();
                    System.out.println("Exiting the program...");
                    return;
                default:
                    System.out.println("Enter a valid option");
            }
        }
    }
    public static int getRowIndex(String rowLetter) {
        switch (rowLetter) {
            case "A":
                return 0;
            case "B":
                return 1;
            case "C":
                return 2;
            case "D":
                return 3;
            default:
                return -1; // Return -1 for invalid row letter
        }
    }
    public static int getTicketIndex(int rowIndex, int seatIndex) {
        return rowIndex * 14 + seatIndex;
    }



    public static int getOptionFromUser() {
        int option = -1;
        do {
            try {
                System.out.print("Please select an option: ");
                option = input.nextInt();
                if (option < 0 || option > 6) {
                    System.out.println("Enter a valid option (0-6)");
                }
            } catch (InputMismatchException e) {
                System.out.println("Integer required");
                input.nextLine();
            }
        } while (option < 0 || option > 6);
        return option;
    }

    public static void Menu() {
        System.out.println("**************************************************");
        System.out.println("*                MENU OPTIONS                    *");
        System.out.println("**************************************************");
        System.out.println("     1) Buy a seat");
        System.out.println("     2) Cancel a seat");
        System.out.println("     3) Find first available seat");
        System.out.println("     4) Show seating plan");
        System.out.println("     5) Print tickets information and total sales");
        System.out.println("     6) Search tickets");
        System.out.println("     0) Quit");
        System.out.println("**************************************************");
    }

    public static String getRowLetter() {
        String rowLetter;
        do {
            System.out.print("Enter row letter (A, B, C, D): ");
            rowLetter = input.next().toUpperCase();

            if (!rowLetter.matches("[ABCD]")) {
                System.out.println("Invalid row letter");
            }
        } while (!rowLetter.matches("[ABCD]"));
        return rowLetter;
    }

    public static int getSeatnumber(String rowLetter) {
        int seatNumber = 0;
        do {
            try {
                System.out.print("Enter seat number (" + (rowLetter.matches("[BC]") ? "1-12" : "1-14") + "): ");
                seatNumber = input.nextInt();

                if (seatNumber < 1 || (rowLetter.matches("[BC]") && (seatNumber > 12)) || (!rowLetter.matches("[BC]") && (seatNumber > 14))) {
                    System.out.println("Invalid seat number");
                }
            } catch (InputMismatchException e) {
                System.out.println("Integer required");
                input.nextLine();
            }
        } while (seatNumber < 1 || (rowLetter.matches("[BC]") && (seatNumber > 12)) || (!rowLetter.matches("[BC]") && (seatNumber > 14)));
        return seatNumber;
    }

    public static void buy_seat() {
        String rowLetter = getRowLetter();
        int seatNumber = getSeatnumber(rowLetter);

        int rowIndex = getRowIndex(rowLetter);
        int seatIndex = seatNumber - 1;

        if (seatArray[rowIndex][seatIndex] != null) {
            System.out.println("Seat " + rowLetter + seatNumber + " is already booked.");

        } else {
            double price;
            if (seatNumber <= 5) {
                price = 200.0;
            } else if (seatNumber <= 9) {
                price = 150.0;
            } else {
                price = 180.0;
            }
            // Create Person object
            System.out.print("Enter name: ");
            String name = input.next();

            System.out.print("Enter surname: ");
            String surname = input.next();

            System.out.print("Enter email: ");
            String email = input.next();

            Person person = new Person(name, surname, email);

            Ticket ticket = new Ticket(rowLetter, seatNumber, price, person);
            seatArray[rowIndex][seatIndex] = rowLetter + seatNumber;
            tickets[getTicketIndex(rowIndex, seatIndex)] = ticket;

            ticket.save(); // Call save method here

            System.out.println("Seat " + rowLetter + seatNumber + " booked successfully for " + person.getName() + " " + person.getSurname());

        }
    }

    public static void cancel_seat() {
        String rowLetter = getRowLetter();
        int seatNumber = getSeatnumber(rowLetter);

        int rowIndex = getRowIndex(rowLetter);
        int seatIndex = seatNumber - 1;

        if (seatArray[rowIndex][seatIndex] != null) {
            seatArray[rowIndex][seatIndex] = null;
            tickets[getTicketIndex(rowIndex, seatIndex)] = null;
            System.out.println("Seat " + rowLetter + seatNumber + " canceled successfully.");
        } else {
            System.out.println("Seat " + rowLetter + seatNumber + " is not booked.");
        }
    }

    public static void find_first_available() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 14; j++) {
                if (seatArray[i][j] == null) {
                    char rowLetter = (char) ('A' + i);
                    int seatNumber = j + 1;
                    System.out.println("The first available seat is: " + rowLetter + seatNumber);
                    return;
                }
            }
        }
        System.out.println("Sorry, no available seats.");
    }

    public static void show_seating_plan() {
        System.out.println("Seating Plan:");

        for (int i = 0; i < 4; i++) {
            char rowLetter = (char) ('A' + i);
            System.out.print(rowLetter + "  ");
            for (int j = 0; j < 14; j++) {
                if (!((rowLetter == 'B' || rowLetter == 'C') && (j == 12 || j == 13))) {
                    if (seatArray[i][j] != null) {
                        System.out.print(" X ");
                    } else {
                        System.out.print(" O ");
                    }
                }
            }
            System.out.println();
        }
    }

    public static void print_tickets_info() {
        double totalAmount = 0;

        System.out.println("Tickets Information:");

        for (int i = 0; i < seatArray.length; i++) {
            for (int j = 0; j < seatArray[i].length; j++) {
                String seat = seatArray[i][j];
                if (seat != null) {
                    Ticket ticket = tickets[getTicketIndex(i, j)];
                    System.out.println("Ticket: " + seat + ", Price: £" + ticket.getPrice() + ", Passenger: " + ticket.getPerson().getName() + " " + ticket.getPerson().getSurname());
                    totalAmount += ticket.getPrice();
                }
            }
        }

        System.out.println("Total Amount: £" + totalAmount);
    }

    public static void search_ticket() {
        String rowLetter = getRowLetter();
        int seatNumber = getSeatnumber(rowLetter);

        int rowIndex = getRowIndex(rowLetter);
        int seatIndex = seatNumber - 1;

        if (seatArray[rowIndex][seatIndex] != null) {
            String seat = seatArray[rowIndex][seatIndex];
            Ticket ticket = tickets[getTicketIndex(rowIndex, seatIndex)];
            System.out.println("Ticket found:");
            System.out.println("Seat: " + seat + ", Price: £" + ticket.getPrice() + ", Passenger: " + ticket.getPerson().getName() + " " + ticket.getPerson().getSurname());
        } else {
            System.out.println("This seat is available.");
        }
    }
}
