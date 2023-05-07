import org.bson.Document;

public class Person {

    // Attribut för personens namn
    private String name;

    // Attribut för personens ålder
    private String age;

    // Attribut för personens adress
    private String address;

    // Attribut för personens ID
    String _id;

    // Konstruktor för att skapa en person med namn, ålder och adress
    public Person(String name, String age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    // Konstruktor för att skapa en person med namn, ålder, adress och ID
    public Person(String name, String age, String address, String id) {
        this._id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    // Standardkonstruktor
    public Person() {

    }

    // Getter-metod för att hämta personens namn
    public String getName() {
        return name;
    }

    // Setter-metod för att sätta personens namn
    public void setName(String name) {
        this.name = name;
    }

    // Getter-metod för att hämta personens ålder
    public String getAge() {
        return age;
    }

    // Setter-metod för att sätta personens ålder
    public void setAge(String age) {
        this.age = age;
    }

    // Getter-metod för att hämta personens adress
    public String getAdress() {
        return address;
    }

    // Setter-metod för att sätta personens adress
    public void setAdress(String adress) {
        this.address = adress;
    }

    // Överskuggar toString-metoden för att returnera en strängrepresentation av personen
    @Override
    public String toString() {
        return name + " is " + age + " years old and lives in (" + address + ")";
    }

    // Statisk metod för att skapa en person från en Document
    public static Person fromDoc(Document doc) {
        if (doc == null) {
            return new Person("", "", "", "");
        }
        return new Person(
                doc.getString("name"),
                doc.getString("age"),
                doc.getString("address"),
                doc.getString("_id")
        );
    }

    // Metod för att skapa en person från en JSON-sträng
    public Person fromJson(String json) {
        Document doc = Document.parse(json);
        return fromDoc(doc);
    }

    // Metod för att konvertera personen till en JSON-sträng
    public String toJson() {
        return toDoc().toJson();
    }

    // Metod för att konvertera personen till en Document
    public Document toDoc() {
        return new Document("name", name)
                .append("age", age)
                .append("address", address)
                .append("_id", _id);
    }
}
