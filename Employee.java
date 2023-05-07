import org.bson.Document;

public class Employee extends Person {

    // Attribut för anställdas anställningsnummer
    private String empNumber;

    // Konstruktor för att skapa en anställd med namn, ålder, adress och anställningsnummer
    public Employee(String name, String age, String address, String empNumber) {
        super(name, age, address);
        this.empNumber = empNumber;
    }

    // Standardkonstruktor
    public Employee() {
    }

    // Getter-metod för att hämta anställdas anställningsnummer
    public String getEmpNumber() {
        return empNumber;
    }

    // Setter-metod för att sätta anställdas anställningsnummer
    public void setEmpNumber(String empNumber) {
        this.empNumber = empNumber;
    }

    // Överskuggar toDoc-metoden för att konvertera en anställd till en Document
    @Override
    public Document toDoc() {
        return new Document("name", getName())
                .append("age", getAge())
                .append("address", getAdress())
                .append("employeeNumber", getEmpNumber())
                .append("_id", _id);
    }
}
