public class Person {
    String name;
    String surname;
    String email;
    Person(String name, String surname, String email){
        this.name = name;
        this.surname = surname;
        this.email = email;

    }
    public String getName() {
        return name;
    }
    public String getSurname(){
        return surname;
    }
    public String getEmail(){
        return email;
    }


}
