import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);        // We use this to read what the user types
    StudentManager manager = new StudentManager();   // We create a manager to handle student actions

    // We load any saved student data from the file
    manager.loadFromFile();

    // We start the menu loop and keep it running until the user exits
    while (true) {
      // We show the menu options
      System.out.println("1. Add student");
      System.out.println("2. Delete student");
      System.out.println("3. Search for student");
      System.out.println("4. Update grade");
      System.out.println("5. Display all students");
      System.out.println("6. Sort by grade (highest to lowest)");
      System.out.println("7. Sort by grade (lowest to highest)");
      System.out.println("8. Fuzzy search (partial match by ID or name)");
      System.out.println("9. Generate demo students");
      System.out.println("10. Show student grades as bar chart");
      System.out.println("0. Exit");

      // We ask the user to choose an option
      int choice = scanner.nextInt();
      scanner.nextLine(); // We clear the leftover newline

      // We handle whatever choice the user made
      switch (choice) {

        // Option 1: Add a student
        case 1:
          String id;
          while (true) {
            System.out.print("Input student ID (letters and digits only, max 20 chars): ");
            id = scanner.nextLine();
            if (id.matches("[A-Za-z0-9]+") && id.length() <= 20) break;
            System.out.println("Invalid ID. Use only letters/digits and max 20 characters.");
          }

          // We check if the ID already exists in the system
          if (manager.findStudentById(id) != null) {
            System.out.println("A student with this ID already exists. Student not added.");
            break;
          }

          String name;
          while (true) {
            System.out.print("Input student name (letters/spaces only, max 20 chars): ");
            name = scanner.nextLine();
            if (name.matches("[A-Za-z ]+") && name.length() <= 20) break;
            System.out.println("Invalid name. Use only letters/spaces and max 20 characters.");
          }

          double grade;
          while (true) {
            System.out.print("Input student grade (0-100, up to two decimal places, max 5 characters): ");
            String gradeInput = scanner.nextLine();
            if (gradeInput.matches("\\d{1,3}(\\.\\d{1,2})?") && gradeInput.length() <= 5) {
              grade = Double.parseDouble(gradeInput);
              if (grade >= 0 && grade <= 100) break;
            }
            System.out.println("Invalid grade. Must be a number between 0 and 100 with up to two decimal places and 5 total characters.");
          }

          // We attempt to add the student
          boolean added = manager.addStudent(new Student(id, name, grade));
          if (added) {
            System.out.println("Student added successfully.");
          } else {
            System.out.println("Failed to add student. ID already exists.");
          }
          break;

        // Option 2: Delete a student
        case 2:
          System.out.print("Input student ID to delete: ");
          String removeId = scanner.nextLine();
          manager.removeStudentById(removeId);
          break;

        // Option 3: Search for a student by exact name
        case 3:
          System.out.print("Enter student name to search: ");
          String searchName = scanner.nextLine();
          Student found = manager.findStudentByName(searchName);
          System.out.println(found != null ? found : "Student not found.");
          break;

        // Option 4: Update a student's grade
        case 4:
          System.out.print("Input student ID: ");
          String updateId = scanner.nextLine();

          Student studentToUpdate = manager.findStudentById(updateId);
          if (studentToUpdate == null) {
            System.out.println("Student with that ID not found.");
            break;
          }

          double newGrade;
          while (true) {
            System.out.print("Input new grade (0-100, up to two decimal places, max 5 characters): ");
            String gradeInput = scanner.nextLine();
            if (gradeInput.matches("\\d{1,3}(\\.\\d{1,2})?") && gradeInput.length() <= 5) {
              newGrade = Double.parseDouble(gradeInput);
              if (newGrade >= 0 && newGrade <= 100) break;
            }
            System.out.println("Invalid grade. Must be a number between 0 and 100 with up to two decimal places and 5 total characters.");
          }

          manager.updateGrade(updateId, newGrade);
          break;

        // Option 5: Show all students
        case 5:
          manager.printAllStudents();
          break;

        // Option 6: Sort students from highest to lowest grade
        case 6:
          manager.sortByGradeDescending();
          break;

        // Option 7: Sort students from lowest to highest grade
        case 7:
          manager.sortByGradeAscending();
          break;

        // Option 8: Search for any student whose name or ID contains the keyword
        case 8:
          System.out.print("Enter keyword to search (partial match on ID or name): ");
          String keyword = scanner.nextLine();
          manager.fuzzySearch(keyword);
          break;

        // Option 9: Quickly add 5 random students for testing
        case 9:
          manager.generateDemoStudents();
          break;

        // Option 10: Show a bar chart of grades for all students
        case 10:
          manager.displayBarChart();
          break;

        // Option 0: Exit the program
        case 0:
          manager.saveToFile(); // We save all data before leaving
          System.out.println("Data saved. Exiting...");
          scanner.close();
          return;

        // Anything else: Invalid input
        default:
          System.out.println("Invalid choice.");
      }
    }
  }
}
