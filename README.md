# PGR203 Avansert Java eksamen

[![Java CI with Maven](https://github.com/kristiania-pgr203-2021/pgr203-exam-Cooljean95/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/kristiania-pgr203-2021/pgr203-exam-Cooljean95/actions/workflows/maven.yml)

# Hvordan programmet skal testes:
### Bygg og test  executable jar-fil:
1. Åpne programmet i intelliJ, åpne maven på høyresiden, kjør mvn clean for å rense /target. Kjør deretter mvn package for å opprette.jar filen som skal kjøres.
2. Opprett en "pgr203.propertise" fil som skal ligge inne i targeten, sammen med jar filen"
3. Properties filen må hete "pgr203.properties" og må inneholde:
    - dataSource.url: jdbc:postgresql://localhost:5432/(Din database legges til her)
    - dataSource.user: brukernavn til database eieren.
    - dataSource.password: passordet til brukeren.
4. Etter det kan du kjøre java -jar target/pgr203-exam-Cooljean95-1.0-SNAPSHOT.jar inne på terimnalen i intellej 
5. Du kan også kjøre serveren utenfor intelliJ. Gå inn i target mappen til prosjektet, der skal det ligge et program med et kaffekopp ikon. Klikk på denne som heter 
pgr203-exam-Cooljean95-1.0-SNAPSHOT.jar , og tast inn http://localhost:85/index.html i browseren. 

ELSE

1. Bruk den "pgr203.propertise" som kommer med zip filen. 
2. Følg instruksene i propertis filen. 
3. Legg til Brukernavn, url databasen og passordet der det er forklart. 
4. Copy pased propertis filen inn i target mappen. Skal ligge sammen med jar filen. 


### Funksjonalitet
Denne nettsiden er laget med evnen til å lagre spørsmål, valg, og svar inn i 3 forskjellige databaser. 
Man kan også edite valg og sprøsmålene sine under veis om det skulle vært gjort en feil. Men ikke svar. 

1. Programmet skal kjøres i nettleseren ved å besøke: http://localhost:85/index.html
2. Add new question: Skriv inn tittel, text, ett ord for de to andre labels også trykk på knappen add.
3. Tilbake på hovedsiden skal alt av questions, options, answers bli framvist, så man kan ha en oversikt på hva som er i databsasene. 
4. Edit question: Velg question, endre tittel og tekst, Dette blir så da oppdatert i databasen. 
5. Add option: Her kan du velge en av spørsmålene fra Questions du har lageret i databasen, og deretter adde en option til det spørsmålet. 
6. Edit option: Velg et option du har skrevet før og endre det til noe nytt. Med å velge det ootionet som skal erstattes.  
7. Add answer: Velg option, skriv inn det samme som det står i option. Da vil det optionet bli lagret i databasen som et endelig svar. 
8. Skriver du noe ennet en hva som er valgt, vil det ikke bli godksjent. (ikke case sensetiv)

### Ekstra leveranse
1. I forelesningen fikk vi en rar feil med CSS når vi hadde `<!DOCTYPE html>`. Grunnen til det er feil content-type. Klarer dere å fikse det slik at det fungerer å ha `<!DOCTYPE html>` på starten av alle HTML-filer?
    - For å få til dette så puttet vi inn en "else if" i requestTarget at contentType er css.
2. Med en if så skiller koden på om requesten er en POST eller GET metode. Så at koden vet forskellene på api/questions når den blir kjørt.

# Designbeskrivelse

### Datamodell

![Java eksamen](https://user-images.githubusercontent.com/48331486/141649402-d01fa7a4-4d23-478c-8ca9-9139791454cc.jpeg)


### HttpServer Diagram

![Diagram av HttpServer](https://user-images.githubusercontent.com/48331486/141649012-461f1cdb-e458-4d1e-8dfd-d233f75201ac.jpeg)

### Egenevaluering



## Korreksjoner av eksamensteksten i Wiseflow:

**DET ER EN FEIL I EKSEMPELKODEN I WISEFLOW:**

* I `addOptions.html` skulle url til `/api/tasks` vært `/api/alternativeAnswers` (eller noe sånt)

**Det er viktig å være klar over at eksempel HTML i eksamensoppgaven kun er til illustrasjon. Eksemplene er ikke tilstrekkelig for å løse alle ekstraoppgavene og kandidatene må endre HTML-en for å være tilpasset sin besvarelse**


## Sjekkliste

## Vedlegg: Sjekkliste for innlevering

* [x] Dere har lest eksamensteksten
* [x] Dere har lastet opp en ZIP-fil med navn basert på navnet på deres Github repository
* [x] Koden er sjekket inn på github.com/pgr203-2021-repository
* [x] Dere har committed kode med begge prosjektdeltagernes GitHub konto (alternativt: README beskriver arbeidsform)

### README.md

* [x] `README.md` inneholder en korrekt link til Github Actions
* [x] `README.md` beskriver prosjektets funksjonalitet, hvordan man bygger det og hvordan man kjører det
* [x] `README.md` beskriver eventuell ekstra leveranse utover minimum
* [x] `README.md` inneholder et diagram som viser datamodellen

### Koden


* [x] `mvn package` bygger en executable jar-fil
* [x] Koden inneholder et godt sett med tester
* [x] `java -jar target/...jar` (etter `mvn package`) lar bruker legge til og liste ut data fra databasen via webgrensesnitt
* [x] Serveren leser HTML-filer fra JAR-filen slik at den ikke er avhengig av å kjøre i samme directory som kildekoden
* [x] Programmet leser `dataSource.url`, `dataSource.username` og `dataSource.password` fra `pgr203.properties` for å connecte til databasen
* [x] Programmet bruker Flywaydb for å sette opp databaseskjema
* [x] Server skriver nyttige loggmeldinger, inkludert informasjon om hvilken URL den kjører på ved oppstart

### Funksjonalitet

* [x] Programmet kan opprette spørsmål og lagrer disse i databasen (påkrevd for bestått)
* [x] Programmet kan vise spørsmål (påkrevd for D)
* [x] Programmet kan legge til alternativ for spørsmål (påkrevd for D)
* [x] Programmet kan registrere svar på spørsmål (påkrevd for C)
* [x] Programmet kan endre tittel og tekst på et spørsmål (påkrevd for B)

### Ekstraspørsmål (dere må løse mange/noen av disse for å oppnå A/B)

* [ ] Før en bruker svarer på et spørsmål hadde det vært fint å la brukeren registrere navnet sitt. Klarer dere å implementere dette med cookies? Lag en form med en POST request der serveren sender tilbake Set-Cookie headeren. Browseren vil sende en Cookie header tilbake i alle requester. Bruk denne til å legge inn navnet på brukerens svar
* [ ] Når brukeren utfører en POST hadde det vært fint å sende brukeren tilbake til dit de var før. Det krever at man svarer med response code 303 See other og headeren Location
* [ ] Når brukeren skriver inn en tekst på norsk må man passe på å få encoding riktig. Klarer dere å lage en <form> med action=POST og encoding=UTF-8 som fungerer med norske tegn? Klarer dere å få æøå til å fungere i tester som gjør både POST og GET?
* [x] Å opprette og liste spørsmål hadde vært logisk og REST-fult å gjøre med GET /api/questions og POST /api/questions. Klarer dere å endre måten dere hånderer controllers på slik at en GET og en POST request kan ha samme request target?
* [ ] Vi har sett på hvordan å bruke AbstractDao for å få felles kode for retrieve og list. Kan dere bruke felles kode i AbstractDao for å unngå duplisering av inserts og updates?
* [ ] Dersom noe alvorlig galt skjer vil serveren krasje. Serveren burde i stedet logge dette og returnere en status code 500 til brukeren
* [ ] Dersom brukeren går til http://localhost:8080 får man 404. Serveren burde i stedet returnere innholdet av index.html
* [ ] Et favorittikon er et lite ikon som nettleseren viser i tab-vinduer for en webapplikasjon. Kan dere lage et favorittikon for deres server? Tips: ikonet er en binærfil og ikke en tekst og det går derfor ikke an å laste den inn i en StringBuilder
* [ ] I forelesningen har vi sett på å innføre begrepet Controllers for å organisere logikken i serveren. Unntaket fra det som håndteres med controllers er håndtering av filer på disk. Kan dere skrive om HttpServer til å bruke en FileController for å lese filer fra disk?
* [ ] Kan dere lage noen diagrammer som illustrerer hvordan programmet deres virker?
* [ ] JDBC koden fra forelesningen har en feil ved retrieve dersom id ikke finnes. Kan dere rette denne?
* [x] I forelesningen fikk vi en rar feil med CSS når vi hadde `<!DOCTYPE html>`. Grunnen til det er feil content-type. Klarer dere å fikse det slik at det fungerer å ha `<!DOCTYPE html>` på starten av alle HTML-filer?
* [ ] Klarer dere å lage en Coverage-rapport med GitHub Actions med Coveralls? (Advarsel: Foreleser har nylig opplevd feil med Coveralls så det er ikke sikkert dere får det til å virke)
* [ ] FARLIG: I løpet av kurset har HttpServer og tester fått funksjonalitet som ikke lenger er nødvendig. Klarer dere å fjerne alt som er overflødig nå uten å også fjerne kode som fortsatt har verdi? (Advarsel: Denne kan trekke ned dersom dere gjør det feil!)
