Final_Summer2025_YourFullName - JavaFX Employment Application (Template)
==========================================================

What is included
-----------------
- src/application/Main.java         (JavaFX UI + validation + multi-table insert)
- src/application/DBUtil.java       (JDBC utility - update DB URL/credentials)
- resources/company_logo.png        (placeholder logo)
- sql/create_tables_mysql.sql       (SQL to create ApplicantTable + EmploymentTable)

Requirements
------------
- Java 21+
- JavaFX SDK (match Java version)
- MySQL server (or Oracle DB if you adapt SQL & JDBC driver)
- MySQL JDBC driver (add to project's classpath / module-path)

How to run (Eclipse)
---------------------
1. Import the project folder into Eclipse as a Java project (or create new and copy files).
2. Add JavaFX library to Module Path / Classpath. If using modules, add VM args like:
   --module-path "path/to/javafx/lib" --add-modules javafx.controls,javafx.fxml
3. Add MySQL JDBC driver to project's classpath (e.g. mysql-connector-java.jar).
4. Update DBUtil.java with your database URL, username and password.
5. Run the Main.java class as a Java application.

Demo checklist (for submission)
-------------------------------
- Show SQL developer / MySQL Workbench before insert (empty tables).
- Run app, fill form with valid data, click Submit.
- Show success alert.
- Show database after insert with the new rows in ApplicantTable and EmploymentTable.
- Record ~3 minutes demonstrating the above.

Notes
-----
- The salary validation expects a decimal with 2 fractional digits (e.g. 12345678.50).
- If using Oracle, change AUTO_INCREMENT to GENERATED ALWAYS AS IDENTITY and adjust JDBC URL & driver.
