import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    StudentManager manager = new StudentManager();

    // load student data
    manager.loadFromFile();

    while (true) {
      System.out.println("1. Add student");
      System.out.println("2. Delete student");
      System.out.println("3. Search for student");
      System.out.println("4. Update grade");
      System.out.println("5. Display all students");
      System.out.println("0. Exit");

      int choice = scanner.nextInt();
      scanner.nextLine(); // Clear newline

      switch (choice) {
        case 1:
          System.out.print("Input student ID: ");
          String id = scanner.nextLine();
          System.out.print("Input student name: ");
          String name = scanner.nextLine();
          System.out.print("Input student grade: ");
          double grade = scanner.nextDouble();
          scanner.nextLine();
          manager.addStudent(new Student(id, name, grade));
          break;

        case 2:
          System.out.print("Input student ID to delete: ");
          String removeId = scanner.nextLine();
          manager.removeStudentById(removeId);
          break;

        case 3:
          System.out.print("Enter student name to search: ");
          String searchName = scanner.nextLine();
          Student found = manager.findStudentByName(searchName);
          System.out.println(found != null ? found : "Student not found.");
          break;

        case 4:
          System.out.print("Input student ID: ");
          String updateId = scanner.nextLine();
          System.out.print("Input new grade: ");
          double newGrade = scanner.nextDouble();
          scanner.nextLine();
          manager.updateGrade(updateId, newGrade);
          break;

        case 5:
          manager.printAllStudents();
          break;

        case 0:
          // save student data when exiting.
          manager.saveToFile();
          System.out.println("Data saved. Exiting...");
          scanner.close();
          return;

        default:
          System.out.println("Invalid choice.");
      }
    }
  }
}