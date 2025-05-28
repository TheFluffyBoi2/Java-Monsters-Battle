# Java Monster Battle
**Java Monster Battle** este un joc 2D turn-base dezvoltat in Java, folosind biblioteca Swing si PostgreSQL.
Jucatorii isi creeaza o echipa de monstri pe care o pot folosi intr-un sistem de lupta.

## Structura proiectului
src/
├── entities/ - Clasele pentru gestionarea monstrilor, obiectelor si echipei

├── game/ - Game states

├── input/ - Gestionarea inputului

├── sound/ - Clasa pentru gestionarea efectelor sonore

├── ui/ - Clasele utlilzate pentru implementarea interfetei grafice in Swing

├── database/ - Datele necesare pentru conectarea si operatiilor JDBC

└── Main.java - Clasa principala

## Cerinte
- Java 17 sau o versiune mai recenta
- JDBC + PostgreSQL
- Maven

## Configurare
- Clonarea proiectului
```bash
git clone https://github.com/TheFluffyBoi2/Java-Monsters-Battle.git
cd Java-Monsters-Battle
```
- Compilare si rulare
```bash
mvn clean install
mvn exec:java
```
