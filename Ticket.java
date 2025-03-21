import java.io.FileWriter;
import java.io.IOException;
public class Ticket {
    String row;
    int seat;
    Double price;
    Person person;
    Ticket(String row, int seat, Double price, Person person){
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;

    }
    public String getRow(){
        return row;
    }
    public int getSeat() {
        return seat;
    }
    public double getPrice() {
        return price;
    }

    public Person getPerson() {
        return person;
    }

    public void save() {
        String filename = row + seat + ".txt"; // Generating filename based on row and seat number
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write("Ticket Information:\n");
            writer.write("Row: " + row + "\n");
            writer.write("Seat: " + seat + "\n");
            writer.write("Price: £" + price + "\n");
            writer.write("Passenger: " + person.getName() + " " + person.getSurname() + "\n");
            writer.write("Email: " + person.getEmail() + "\n");
            writer.close();
            System.out.println("Ticket information saved to file: " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred while saving ticket information to file: " + e.getMessage());
        }
    }


}
