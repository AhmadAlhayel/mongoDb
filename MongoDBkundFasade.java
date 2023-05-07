import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class MongoDBkundFasade {
    MongoClient client;
    MongoDatabase db;
    MongoCollection<Document> collection;
    String connString;
    String collectionName;
    String databaseName;

    public MongoDBkundFasade(String connString, String databaseName, String collectionName) {
        this.connString = connString;
        this.collectionName = collectionName;
        this.databaseName = databaseName;
        Connect();
    }
    // Etablerar en anslutning till MongoDB-databasen
    public void Connect() {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connString))
                .serverApi(serverApi)
                .build();

        try {
            // Skapar en klient, databas och samling baserat på givna uppgifter
            client = MongoClients.create(settings);
            db = client.getDatabase(databaseName);
            collection = db.getCollection(collectionName);
        } catch (Exception ex) {
            System.out.println("Fel!");
            System.out.println(ex.getMessage());
        }

        try {
            // Skapar ett index på fältet "name" för att säkerställa unika värden
            createIndex();
        } catch (Exception ex) {
            System.out.println("Bra");
        }
    }
    // Skapar ett index på fältet "name" med unika värden
    public void createIndex() {
        collection.createIndex(new Document("name", 1),
                new IndexOptions().unique(true));
    }

    // Infogar en ny Kund-dokument i samlingen
    public void insertKund(Kund kund) {
        Document doc = kund.toDoc();
        doc.remove("_id");

        var find = collection.find(doc);
        if (find.first() == null)
            collection.insertOne(doc);
    }
    // Tar bort dokument som matchar den givna frågan från samlingen
    public void delete(Document query) {
        collection.deleteOne(query);
    }

    // Uppdaterar en Kund med nya värden baserat på namn
    public void updateKund(String name, String newName, String newAge, String address, String kundNumber) {
        Document filter = new Document("name", name);
        Document update = new Document();
        update.append("name", newName);
        update.append("age", newAge);
        update.append("address", address);
        update.append("kundNumber", kundNumber);
        Document updateQuery = new Document("$set", update);
        this.collection.updateOne(filter, updateQuery);
    }

    // Hämtar alla Kund-dokument i samlingen och returnerar dem som en ArrayList
    public ArrayList<Kund> getAllKund() {
        MongoCollection<Document> collection = db.getCollection(collectionName);
        ArrayList<Kund> kunds = new ArrayList<>();

        Document query = new Document();
        try (MongoCursor<Document> cursor = collection.find(query).iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Kund kund = new Kund();
                kund.setName(document.getString("name"));
                kund.setAge(document.getString("age"));
                kund.setKundNumber(document.getString("kundNumber"));
                kunds.add(kund);
            }
        }
        client.close();
        return kunds;
    }
    // Hämtar Kund-dokument som matchar den givna åldern

    public List<Document> getKundsByAge(String age) {
        Document query = new Document("age", age);
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
    // Hämtar Kund-dokument som matchar den givna adressen
    public List<Document> getKundsByAddress(String address) {
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
    // Hämtar Kund-dokument som matchar det givna kundnumret
    public List<Document> getKundByKundNumber(String kundNumber) {
        Document query = new Document("kundNumber", kundNumber);
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
