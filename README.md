# MongoDB Facade & Crud
koden är en Java-kod av ett enkelt program för hantering av personer,
kunder och anställda i en MongoDB-databas. 
Programmet använder MongoDB Java Driver för att kommunicera med databasen.

## MongoDBModuler för Java
### Libraries
- mongodb.jdbc

### Javaversion
- I den program, används JDK 19.

## Här är om klasser som finns i koden:

1. Main-klassen: Detta är den huvud klassen som innehåller main-metoden. 
Den skapar en instans av MainMenu-klassen och kör sedan runMianMenu-metoden för att visa huvudmenyn för användaren.

2. MainMenu-klassen: Denna klass ansvarar för att visa huvudmenyn och hantera användarens val.

3. Person-klassen: Detta är en superklass som representerar en person med attribut som namn, ålder, adress och ID. Klassen har olika konstruktorer, getter- och setter-metoder.

4. Kund-klassen: Detta är en subklass till Person-klassen och representerar en kund. Den har ett extra attribut för kundnummer.

5. Employee-klassen: Detta är en annan subklass till Person-klassen och representerar en anställd. Den har ett extra attribut för anställningsnummer.

6. MongoDBFasade: Detta är en fasadklass som hanterar kommunikationen med MongoDB-databasen. Den ansvarar för att ansluta till databasen, 
den skapar index, utföra CRUD-operationer (skapa, läsa, uppdatera och ta bort) och hämta data från databasen. 





