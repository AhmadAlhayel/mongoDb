import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MongoDBEmployeeFasade {
    MongoClient client;
    MongoDatabase db;
    MongoCollection<Document> collection;
    String connString;
    String collectionName;
    String databaseName;

    public MongoDBEmployeeFasade(String connString, String databaseName, String collectionName) {
        this.connString = connString;
        this.collectionName = collectionName;
        this.databaseName = databaseName;
        Connect();
    }

    public void Connect() {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connString))
                .serverApi(serverApi)
                .build();

        try {
            client = MongoClients.create(settings);
            db = client.getDatabase(databaseName);
            collection = db.getCollection(collectionName);
        } catch (Exception ex) {
            System.out.println("Ooops!");
            System.out.println(ex.getMessage());
        }

        try {
            createIndex();
        } catch (Exception ex) {
            System.out.println(";)");
        }
    }

    public void createIndex() {
        collection.createIndex(new Document("name", 1),
                new IndexOptions().unique(true));
    }

    public void insertEmployee(Employee employee) {
        Document doc = employee.toDoc();
        doc.remove("_id");

        var find = collection.find(doc);
        if (find.first() == null)
            collection.insertOne(doc);
    }

    public void delete(Document query) {
        collection.deleteOne(query);
    }

    public void updateEmployee(String name, String newName, String newAge, String address, String empNumber) {
        Document filter = new Document("name", name);
        Document update = new Document();
        update.append("name", newName);
        update.append("age", newAge);
        update.append("address", address);
        update.append("employeeNumber", empNumber);
        Document updateQuery = new Document("$set", update);
        this.collection.updateOne(filter, updateQuery);
    }

    public Employee getEmployeeById(String empNum) {
        MongoCollection<Document> collection = db.getCollection(collectionName);
        Document query = new Document("employeeNumber", empNum);
        Document result = collection.find(query).first();
        client.close();
        Employee employee = new Employee();
        employee.setName(result.getString("name"));
        employee.setAge(result.getString("age"));
        employee.setEmpNumber(result.getString("employeeNumber"));
        return employee;
    }

    public ArrayList<Employee> getAllEmployees() {
        MongoCollection<Document> collection = db.getCollection(collectionName);
        ArrayList<Employee> employees = new ArrayList<>();

        Document query = new Document();
        try (MongoCursor<Document> cursor = collection.find(query).iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Employee employee = new Employee();
                employee.setName(document.getString("name"));
                employee.setAge(document.getString("age"));
                employee.setEmpNumber(document.getString("employeeNumber"));
                employees.add(employee);
            }
        }
        client.close();
        return employees;
    }

    public ArrayList<Document> readEmployee(String name) {
        Document query = new Document("Name", name);
        FindIterable<Document> documents = collection.find(query);
        ArrayList<Document> result = new ArrayList<>();
        try (MongoCursor<Document> cursor = documents.iterator()) {
            while (cursor.hasNext()) {
                result.add(cursor.next());
            }
        }return result;
    }

    public List<Document> getEmployeesByAge(String age) {
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

    public List<Document> getEmployeesByAddress(String address) {
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
    public List<Document> getEmployeeByEmployeeNumber(String employeeNumber) {
        Document query = new Document("employeeNumber", employeeNumber);
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


// kod från Chat GPT
        static void searchByEmployeeNumber(MongoDBEmployeeFasade employeeDB) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Search by employee Number: ");
            String empNm = scanner.nextLine();
            List<Document> search = employeeDB.getEmployeeByEmployeeNumber(empNm);
            System.out.println(search);
        }

        static void searchbyKundNumber(MongoDBkundFasade kundDb) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Search by kund Number: ");
            String kundNm = scanner.nextLine();
            List<Document> search = kundDb.getKundByKundNumber(kundNm);
            System.out.println(search);
        }

        static void searchEmployeeByAddress(MongoDBEmployeeFasade employeeDB) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("which address would you like to show: ");
            String address = scanner.nextLine();
            List<Document> search = employeeDB.getEmployeesByAddress(address);
            System.out.println(search);
        }

        static void searchEmployeesByAddress(MongoDBkundFasade kundDb) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("which address would you like to show: ");
            String address = scanner.nextLine();
            List<Document> search = kundDb.getKundsByAddress(address);
            System.out.println(search);
        }

        static void searchPersonsByAddress(MongoDBFasad db) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("which address would you like to show: ");
            String address = scanner.nextLine();
            List<Document> search = db.getPersonsByAddress(address);
            System.out.println(search);
        }

        static void searchEmployeesByAge(MongoDBEmployeeFasade db) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("which age would you like to show: ");
            String age = scanner.nextLine();
            List<Document> search = db.getEmployeesByAge(age);
            System.out.println(search);
        }
        static void searchKundByAge(MongoDBkundFasade db) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("which age would you like to show: ");
            String age = scanner.nextLine();
            List<Document> search = db.getKundsByAge(age);
            System.out.println(search);
        }
        static void searchPersonByAge(MongoDBFasad db) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("which age would you like to show: ");
            String age = scanner.nextLine();
            List<Document> search = db.getPersonsByAge(age, "Mousa");
            System.out.println(search);
        }

        static void updatePerson(MongoDBFasad db) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("which name you want to update: ");
            String name = scanner.nextLine();
            System.out.print("Enter new name: ");
            String newName = scanner.nextLine();
            System.out.print("Enter new age: ");
            String newAge = scanner.nextLine();
            System.out.print("Enter new adress: ");
            String newAdress = scanner.nextLine();
            db.updatePerson(name, newName, newAge,newAdress);
            System.out.println("Done");
        }
        static void updateKund(MongoDBkundFasade db) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("which name you want to update: ");
            String name = scanner.nextLine();
            System.out.print("Enter new name: ");
            String newName = scanner.nextLine();
            System.out.print("Enter new age: ");
            String newAge = scanner.nextLine();
            System.out.print("Enter new adress: ");
            String newAdress = scanner.nextLine();
            System.out.print("Enter new kund number: ");
            String newKundNumber = scanner.nextLine();
            db.updateKund(name, newName, newAge,newAdress,newKundNumber);
            System.out.println("Done");
        }
        static void updateEmployee(MongoDBEmployeeFasade db) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("which name you want to update: ");
            String name = scanner.nextLine();
            System.out.print("Enter new name: ");
            String newName = scanner.nextLine();
            System.out.print("Enter new age: ");
            String newAge = scanner.nextLine();
            System.out.print("Enter new adress: ");
            String newAdress = scanner.nextLine();
            System.out.print("Enter new employee number: ");
            String newEmpNumber = scanner.nextLine();
            db.updateEmployee(name, newName, newAge,newAdress,newEmpNumber);
            System.out.println("Done");
        }

        static void searchByName(MongoDBFasad db) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the name: ");
            String name = scanner.nextLine();
            Document doc = new Document("name", name);
            Person person = db.readPerson(doc);
            System.out.println(person.getName());
            System.out.println(person.getAge());
            System.out.println(person.getAdress());
        }

        static void deleteObjectByName(MongoDBFasad db, MongoDBkundFasade kundDb, MongoDBEmployeeFasade empDB) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("   What do you eant to delete?:");
            System.out.println(" person. ");
            System.out.println(" Kund.");
            System.out.println(" Employee.");
            String choice = scanner.nextLine();
            switch (choice){
                case "person":
                    System.out.print("Enter the name: ");
                    String name = scanner.nextLine();
                    Document query = new Document("name" , name);
                    db.delete(query);
                    System.out.println("You deleted " + name);
                    break;
                case "kund":
                    System.out.print("Enter the name: ");
                    String kundName = scanner.nextLine();
                    Document query1 = new Document("name" , kundName);
                    kundDb.delete(query1);
                    System.out.println("You deleted " + kundName);
                    break;
                case "employee":
                    System.out.print("Enter the name: ");
                    String empName = scanner.nextLine();
                    Document query2 = new Document("name" , empName);
                    empDB.delete(query2);
                    System.out.println("You deleted " + empName);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }

        static void addEmployee(MongoDBEmployeeFasade db){
            Employee employee;
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter employee name: ");
            String kundName = scanner.nextLine();
            System.out.print("Enter employee age: ");
            String kundAge = scanner.nextLine();
            System.out.print("Enter employee adress: ");
            String kundAdress = scanner.nextLine();
            System.out.print("Enter employee number: ");
            String employeeNumber = scanner.nextLine();
            employee = new Employee(kundName, kundAge, kundAdress,employeeNumber);
            db.insertEmployee(employee);
            System.out.println("You added new employee " + kundName);
        }
        static void addKund(MongoDBkundFasade db) {
            Kund kund;
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter kund name: ");
            String kundName = scanner.nextLine();
            System.out.print("Enter kund age: ");
            String kundAge = scanner.nextLine();
            System.out.print("Enter kund adress: ");
            String kundAdress = scanner.nextLine();
            System.out.print("Enter kund number: ");
            String kundNumber = scanner.nextLine();
            kund = new Kund(kundName, kundAge, kundAdress,kundNumber);
            db.insertKund(kund);
            System.out.println("You added the kund " + kundName);
        }

        static void addPerson(MongoDBFasad db) {
            Person person;
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter your age: ");
            String age = scanner.nextLine();
            System.out.print("Enter your adress: ");
            String address = scanner.nextLine();
            person = new Person(name, age, address);
            db.insertPerson(person);
            System.out.println("You added " + name);
        }
    }





