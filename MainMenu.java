import java.util.Scanner;

public class MainMenu {


    // Skapar en instans av MongoDBFasad för att hantera PersonDB
    MongoDBFasad db = new MongoDBFasad("mongodb://localhost:27017", "MongoDb", "PersonDB");
    // Skapar en instans av MongoDBkundFasade för att hantera KundDB
    MongoDBkundFasade kundDb = new MongoDBkundFasade("mongodb://localhost:27017", "MongoDb", "KundDB");
    // Skapar en instans av MongoDBEmployeeFasade för att hantera EmployeeDB
    MongoDBEmployeeFasade employeeDB = new MongoDBEmployeeFasade("mongodb://localhost:27017", "MongoDb", "EmployeeDB");

    // Metod för att köra huvudmenyn
    public void runMianMenu() {
        Scanner scanner = new Scanner(System.in);


        while (true) {
            // Skriver ut huvudmenyn
            System.out.println("   Main Menu:");
            System.out.println("1. Add person.");
            System.out.println("2. Add Kund.");
            System.out.println("3. Add Employee.");
            System.out.println("4. Delete by name.");
            System.out.println("5. Search by name.");
            System.out.println("6. Update person.");
            System.out.println("7. Update kund.");
            System.out.println("8. Update employee.");
            System.out.println("9. View all persons.");
            System.out.println("10. View all employees.");
            System.out.println("11. View all kunds.");
            System.out.println("12. Search person by age.");
            System.out.println("13. Search kund by age.");
            System.out.println("14. Search employee by age.");
            System.out.println("15. Search persons by address.");
            System.out.println("16. Search kunds by address.");
            System.out.println("17. Search employee by address.");
            System.out.println("18. Search kund by kund number.");
            System.out.println("19. Search employee by employee number.");
            System.out.print("Enter your choice (or any other key to exit): ");

            // Läser in användarens val
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            // Använder en switch-sats för att hantera användarens val
            switch (choice) {
                case 1:
                    MongoDBEmployeeFasade.addPerson(db);
                    break;
                case 2:
                    MongoDBEmployeeFasade.addKund(kundDb);
                    break;
                case 3:
                    MongoDBEmployeeFasade.addEmployee(employeeDB);
                    break;
                case 4:
                    MongoDBEmployeeFasade.deleteObjectByName(db, kundDb, employeeDB);
                    break;
                case 5:
                    MongoDBEmployeeFasade.searchByName(db);
                    break;
                case 6:
                    MongoDBEmployeeFasade.updatePerson(db);
                    break;
                case 7:
                    MongoDBEmployeeFasade.updateKund(kundDb);
                    break;
                case 8:
                    MongoDBEmployeeFasade.updateEmployee(employeeDB);
                    break;
                case 9:
                    System.out.println(db.getAllPersons());
                    break;
                case 10:
                    System.out.println(employeeDB.getAllEmployees());
                    break;
                case 11:
                    System.out.println(kundDb.getAllKund());
                    break;
                case 12:
                    MongoDBEmployeeFasade.searchPersonByAge(db);
                    break;
                case 13:
                    MongoDBEmployeeFasade.searchKundByAge(kundDb);
                    break;
                case 14:
                    MongoDBEmployeeFasade.searchEmployeesByAge(employeeDB);
                    break;
                case 15:
                    MongoDBEmployeeFasade.searchPersonsByAddress(db);
                    break;
                case 16:
                    MongoDBEmployeeFasade.searchEmployeesByAddress(kundDb);
                    break;
                case 17:
                    MongoDBEmployeeFasade.searchEmployeeByAddress(employeeDB);
                    break;
                case 18:
                    MongoDBEmployeeFasade.searchbyKundNumber(kundDb);
                    break;
                case 19:
                    MongoDBEmployeeFasade.searchByEmployeeNumber(employeeDB);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

    }
}