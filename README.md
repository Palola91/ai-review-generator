# AI Review Generator System

## AI Projekt

---
Detta projekt är en Spring Boot‑baserad REST‑API‑applikation som automatiskt genererar produktrecensioner baserat på extern data från Cat Facts API. När en produkt skapas genereras fem AI‑liknande recensioner automatiskt och sparas i MongoDB.

Syfte med projektet:

- REST API‑utveckling med Spring Boot

- Integration med ett externt API

- Databaslagring med MongoDB

- Automatisk generering av produktrecensioner


## Tekniker som används

---

- Java 21

- Spring Boot

- Maven

- MongoDB

- Postman (för testning)

- Externt API: https://catfact.ninja

## Hur man kör projektet

---

### 1. Starta MongoDB

Se till att MongoDB körs lokalt innan du startar applikationen. Standardinställningen i projektet är:
``
localhost:27017
``

Om MongoDB inte körs kommer inga produkter eller recensioner kunna sparas.

---

### 2. Starta applikationen

Projektet kan köras på två sätt:

### Via IntelliJ:

- Högerklicka på AiReviewGeneratorApplication

- Välj Run

Via terminal (Maven):

```` 
mvn spring-boot:run 
````


När applikationen startat:

````
Tomcat started on port 8080
````

API:t kör då på:

````
http://localhost:8080
````
## Produktscenarier

---

API:t hanterar tre olika sätt att skapa produkter och recensioner:

### Scenario A – Endast produkt-ID


- Produkten skapas med endast ett ID

- Fem AI-recensioner genereras automatiskt

### Scenario B – Produkt med detaljer

- Produktens namn, kategori och taggar sparas

- Fem AI-recensioner genereras automatiskt

### Scenario C – Manuell recension

- En recension kan läggas till manuellt

- Ingen automatisk AI-generering sker


## API Endpoints

---


### Skapa produkt

POST /product

Exempel (Scenario B) JSON-body:

````
{
  "mode": "withDetails",
  "productId": "T123",
  "productName": "Cat Hoodie",
  "category": "Clothes",
  "tags": ["cat"]
}
````

När en produkt skapas:

- Produkten sparas i MongoDB

- Fem recensioner genereras automatiskt

- Recensionerna baseras på data från Cat Facts API

---

### Hämta produkt

GET /product/{id}

- Returnerar en produkt baserat på ID.

---


### Hämta recensioner

GET /reviews/{productId}

- Visar endast recensioner från de senaste två månaderna

- Recensionerna är kopplade till produkten

---

### Ta bort produkt

DELETE /product/{id}

- Tar bort en produkt från databasen.

---


### Hur recensionerna genereras

- Cat Facts API anropas när en produkt skapas

- Fem recensioner genereras automatiskt

- Varje recension innehåller ett kattfakta

- Alla recensioner sparas i MongoDB

Systemet kontrollerar även att samma recension inte sparas flera gånger.

---

## Felhantering

API:t hanterar fel genom att:

- Validera inputdata (t.ex. rating 1–5)

- Returnera tydliga felmeddelanden vid felaktiga requests

- Hantera externa API-fel utan att applikationen kraschar

- Recensioner får inte heller ha framtida datum.


## Testning

---

Projektet testades med Postman genom att:

- Skapa produkter via POST-anrop

- Testa alla tre produktscenarier

- Hämta produkter och recensioner via GET

- Kontrollera att recensioner genereras automatiskt

- Verifiera att endast recensioner från senaste två månader visas