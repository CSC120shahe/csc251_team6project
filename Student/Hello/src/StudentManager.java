import java.io.*;
import java.util.*;

public class StudentManager {
  private List<Student> studentList = new ArrayList<>();
  private static final String FILE_NAME = "students.csv";

  public void addStudent(Student s) {
    studentList.add(s);
  }

  public void removeStudentById(String id) {
    studentList.removeIf(s -> s.getId().equals(id));
  }

  public Student findStudentByName(String name) {
    for (Student s : studentList) {
      if (s.getName().equalsIgnoreCase(name)) {
        return s;
      }
    }
    return null;
  }

  public void updateGrade(String id, double newGrade) {
    for (Student s : studentList) {
      if (s.getId().equals(id)) {
        s.setGrade(newGrade);
        return;
      }
    }
  }

  public void printAllStudents() {
    if (studentList.isEmpty()) {
      System.out.println("No students in the list.");
    } else {
      for (Student s : studentList) {
        System.out.println(s);
      }
    }
  }

  // save data to the file
  public void saveToFile() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
      for (Student s : studentList) {
        writer.write(s.getId() + "," + s.getName() + "," + s.getGrade());
        writer.newLine();
      }
    } catch (IOException e) {
      System.out.println("Error saving data: " + e.getMessage());
    }
  }

  // read data from file
  public void loadFromFile() {
    File file = new File(FILE_NAME);
    if (!file.exists()) return;

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length == 3) {
          String id = parts[0];
          String name = parts[1];
          double grade = Double.parseDouble(parts[2]);
          studentList.add(new Student(id, name, grade));
        }
      }
    } catch (IOException e) {
      System.out.println("Error loading data: " + e.getMessage());
    }
  }
}