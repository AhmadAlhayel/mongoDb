import com.mongodb.client.*;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;

public class MongoDBFasad {
    MongoClient client;
    MongoDatabase db;
    MongoCollection<Document> collection;
    String connString;
    String collectionName;
    String databaseName;


    // Konstruktor för att skapa en MongoDBFasad med anslutningssträng, databasnamn och samlingens namn
    public MongoDBFasad(String connString, String databaseName, String collectionName) {
        this.connString = connString;
        this.collectionName = collectionName;
        this.databaseName = databaseName;
        Connect();
    }

    // Ansluter till MongoDB-databasen och skapar en samling
    public  void Connect() {
        // Skapar instans av ServerApi
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        // Skapar instans av MongoClientSettings med angiven anslutningssträng och serverapi
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connString))
                .serverApi(serverApi)
                .build();

        try {
            // Skapar klient, databas och samling
            client = MongoClients.create(settings);
            db = client.getDatabase(databaseName);
            collection = db.getCollection(collectionName);
        } catch (Exception ex) {
            System.out.println("Ooops!");
            System.out.println(ex.getMessage());
        }

        try {
            // Skapar index
            createIndex();
        } catch (Exception ex) {
            System.out.println(";)");
        }
    }

    // Skapar ett index på namnfältet i samlingen
    public void createIndex() {
        collection.createIndex(new Document("name", 1),
                new IndexOptions().unique(true));
    }

    // Lägger till en person i samlingen om den inte redan finns
    public void insertPerson(Person person) {
        Document doc = person.toDoc();
        doc.remove("_id");

        var find = collection.find(doc);
        if (find.first() == null) {
            collection.insertOne(doc);
        }
    }
    // Raderar dokument i samlingen baserat på en given sökfråga
    public void delete(Document query) {
        collection.deleteOne(query);
    }
    // Hittar en person baserat på ID och returnerar en Person-objekt
    public Person FindById(String id) {
        Document doc = new Document("id", id);
        Document searsh = collection.find(doc).first();
        return Person.fromDoc(searsh);
    }
    // Hittar personer baserat på namn och returnerar en lista med Person-objekt
    public ArrayList<Person> Find(String name) {
        Document doc = new Document("name", name);
        FindIterable<Document> result = collection.find(doc);
        ArrayList<Person> persons = new ArrayList<>();

        result.forEach(person -> persons.add(Person.fromDoc(person)));

        return persons;
    }


    // Hämtar alla personer i samlingen och returnerar en lista med Person-objekt
    public ArrayList<Person> getAllPersons() {
        MongoCollection<Document> collection = db.getCollection(collectionName);
        ArrayList<Person> persons = new ArrayList<>();
        for (Document doc : collection.find()) {
            Person person = new Person();
            person.setName(doc.getString("name"));
            person.setAge(doc.getString("age"));
            person.setAdress(doc.getString("address"));
            persons.add(person);
        }
        return persons;
    }

    // Uppdaterar en person i samlingen baserat på namn med nya värden för namn, ålder och adress
    public void updatePerson(String name, String newName, String newAge, String address) {
        Document filter = new Document("name", name);
        Document update = new Document();
        update.append("name", newName);
        update.append("age", newAge);
        update.append("address", address);
        Document updateQuery = new Document("$set", update);
        this.collection.updateOne(filter, updateQuery);
    }

    // Läser och returnerar en person baserat på en given sökfråga
    public Person readPerson(Document query) {
        Document document = collection.find(query).first();
        Person person = null;
        if (document != null) {
            person = new Person(
                    document.getString("name"),
                    document.getString("age"),
                    document.getString("address"));
        }
        return person;
    }

    // Hämtar och returnerar en lista med dokument som matchar en given ålder och ett namn
    public List<Document> getPersonsByAge(String age, String name) {
        Document query = new Document("age", age);
        FindIterable<Document> documents = collection.find(query);
        List<Document> result = new ArrayList<>();
        try (MongoCursor<Document> cursor = documents.iterator()) {
            while (cursor.hasNext()) {
                System.out.println(result.add(cursor.next()));
            }
        }

        client.close();
        return result;
    }


    // Hämtar och returnerar en lista med dokument som matchar en given adress
    public List<Document> getPersonsByAddress(String address) {
        Document query = new Document("address", address);
        FindIterable<Document> documents = collection.find(query);
        List<Document> result = new ArrayList<>();
        try (MongoCursor<Document> cursor = documents.iterator()) {
            while (cursor.hasNext()) {
                result.add(cursor.next());
            }
        }
        client.close();
        return result;
    }

}

