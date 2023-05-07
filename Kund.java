import org.bson.Document;

public class Kund extends Person {

    // Attribut för kundens kundnummer
    private String kundNumber;

    // Konstruktor för att skapa en kund med namn, ålder, adress och kundnummer
    public Kund(String name, String age, String address, String kundNumber) {
        super(name, age, address);
        this.kundNumber = kundNumber;
    }

    // Standardkonstruktor
    public Kund() {

    }

    // Getter-metod för att hämta kundens kundnummer
    public String getKundNumber() {
        return kundNumber;
    }

    // Setter-metod för att sätta kundens kundnummer
    public void setKundNumber(String kundNumber) {
        this.kundNumber = kundNumber;
    }

    // Överskuggar toDoc-metoden för att konvertera en kund till en Document
    @Override
    public Document toDoc() {
        return new Document("name", getName())
                .append("age", getAge())
                .append("address", getAdress())
                .append("kundNumber", getKundNumber())
                .append("_id", _id);
    }
}
