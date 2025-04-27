import java.io.*; // use this to read and write files
import java.util.List; // use this to hold a list of students
import java.util.ArrayList; // use this to create the list
import java.util.Random; // use this to generate demo students randomly
import java.util.Set;
import java.util.Comparator; // use this to sort students by their grades
import java.util.HashSet;

import javax.swing.*; // use this to show the chart in a window
import java.awt.*; // use this to draw the chart and text

public class StudentManager {

  // We use this list to keep track of all our students
  private List<Student> studentList = new ArrayList<>();
  private static final String FILE_NAME = "students.csv"; // This is the file we save to

  // When we add a student, we first check that their ID is not already taken
  public boolean addStudent(Student s) {
    if (findStudentById(s.getId()) != null) {
      return false; // Someone with this ID already exists
    }
    studentList.add(s);
    return true;
  }

  // This lets us remove a student by their ID
  public void removeStudentById(String id) {
    studentList.removeIf(s -> s.getId().equals(id));
  }

  // We search for a student using their name
  public Student findStudentByName(String name) {
    for (Student s : studentList) {
      if (s.getName().equalsIgnoreCase(name)) {
        return s;
      }
    }
    return null;
  }

  // We search for a student using their ID
  public Student findStudentById(String id) {
    for (Student s : studentList) {
      if (s.getId().equals(id)) {
        return s;
      }
    }
    return null;
  }

  // We update a student’s grade by matching their ID
  public void updateGrade(String id, double newGrade) {
    for (Student s : studentList) {
      if (s.getId().equals(id)) {
        s.setGrade(newGrade);
        return;
      }
    }
  }

  // We print out all students currently in the list
  public void printAllStudents() {
    if (studentList.isEmpty()) {
      System.out.println("No students in the list.");
    } else {
      for (Student s : studentList) {
        System.out.println(s);
      }
    }
  }

  // ------- Start Sort Grade (High to Low) Section

  // We sort students from highest to lowest grade
  public void sortByGradeDescending() {
    studentList.sort((s1, s2) -> Double.compare(s2.getGrade(), s1.getGrade()));
    System.out.println("Students sorted by grade (highest to lowest):");
    printAllStudents();

  }

  // ------- End Sort Grade (High to Low) Section

  // ------- Start Sort Grade (Low to High) Section

  // We sort students from lowest to highest grade
  public void sortByGradeAscending() {
    studentList.sort(Comparator.comparingDouble(Student::getGrade));
    System.out.println("Students sorted by grade (lowest to highest):");
    printAllStudents();

  }

  // ------- End Sort Grade (Low to High) Section

  // ------- Start Fuzzy Search Section

  // We search for students using part of their ID or name
  public void fuzzySearch(String keyword) {
    boolean found = false;
    keyword = keyword.toLowerCase();

    for (Student s : studentList) {
      if (s.getId().toLowerCase().contains(keyword) || s.getName().toLowerCase().contains(keyword)) {
        System.out.println(s);
        found = true;
      }
    }

    if (!found) {
      System.out.println("No matching students found.");
    }
  }

  // ------- End Fuzzy Search Section

  // ------- Start Student Demo Data Section

  // We create 5 demo students to test the system, using 5 unique names
  // (alphabetical order) and unique IDs
  public void generateDemoStudents() {
    // Names are now in alphabetical order
    String[] sampleNames = { "Alice", "Arthur", "Bethany", "Cody", "Sha" };
    Random rand = new Random();
    Set<String> usedIds = new HashSet<>();

    for (String name : sampleNames) {
      // Keep generating IDs until we get one that's completely new
      String id;
      do {
       // id = "ID:" + (1000 + rand.nextInt(9000));
        id = Integer.toString(1000 + rand.nextInt(9000));
      } while (findStudentById(id) != null || usedIds.contains(id));
      usedIds.add(id);

      // Generate a random grade between 50 and 100, rounded to 2 decimals
      double grade = 50 + rand.nextDouble() * 50;
      grade = Math.round(grade * 100.0) / 100.0;

      // Add the student
      addStudent(new Student(id, name, grade));
    }

    System.out.println("5 unique demo students generated successfully.");
  }

  // ------- End Student Demo Data Section

  // ------- Start Chart Bar Section

  // We show a pop-up window with a bar chart of all student grades
  public void displayBarChart() {

    // If we don’t have any students, we let the user know and skip the chart
    if (studentList.isEmpty()) {
      System.out.println("No students to display in graph.");
      return; // Exit the method early
    }

    // We copy all the current students into a new list
    List<Student> sorted = new ArrayList<>(studentList);

    // We sort the students so the highest grades are shown first in the chart
    sorted.sort((s1, s2) -> Double.compare(s2.getGrade(), s1.getGrade()));

    // We create a new window (frame) with the title "Student Grades Bar Chart"
    JFrame frame = new JFrame("Student Grades Bar Chart");

    // We set the size of the window (850 pixels wide and 500 pixels tall)
    frame.setSize(850, 500);

    // We allow the user to close just this chart window without closing the entire
    // app
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // We create a panel inside the window that will handle the drawing of the chart
    JPanel chartPanel = new JPanel() {

      // We override this method so we can draw our custom chart
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g); // We keep the default behavior first

        // We switch to a more advanced graphics tool for cleaner visuals
        Graphics2D g2 = (Graphics2D) g;

        // We find out how wide and tall the panel is
        int width = getWidth();
        int height = getHeight();

        // We set spacing around the edges of the chart to make room for labels
        int padding = 60;

        // This is extra space just for the Y-axis number labels
        int labelPadding = 40;

        // We figure out how tall and wide the actual chart area should be (excluding
        // padding)
        int chartHeight = height - 2 * padding - labelPadding;
        int chartWidth = width - 2 * padding;

        // We will draw horizontal lines for every 10 grade points (0 to 100)
        int numberYDivisions = 10;

        // === TITLE ===

        // We set the font for the chart title
        g.setFont(new Font("SansSerif", Font.BOLD, 18));

        // The title we want to display
        String title = "Student Scores";

        // We use this to calculate where to draw the title so it’s centered
        FontMetrics fm = g.getFontMetrics();

        // We calculate how far from the left edge the title should start
        int titleX = (width - fm.stringWidth(title)) / 2;

        // We draw the title at the top of the panel
        g.drawString(title, titleX, padding / 2);

        // === Y-AXIS LINES AND LABELS ===

        // We set the drawing color to black for the lines and labels
        g.setColor(Color.BLACK);

        // We draw horizontal lines and labels for 0, 10, ..., 100
        for (int i = 0; i <= numberYDivisions; i++) {
          // We calculate the vertical position of this division
          int y = height - padding - (i * chartHeight / numberYDivisions);

          // We draw a horizontal line across the chart at this position
          g.drawLine(padding, y, width - padding, y);

          // We convert the current value to text (like "70", "80", etc.)
          String yLabel = Integer.toString(i * 10);

          // We draw the number just to the left of the Y-axis
          g.drawString(yLabel, padding - 35, y + 5);
        }

        // === BARS + NAMES + GRADES ===

        // We figure out how wide each student’s bar should be
        int barWidth = chartWidth / sorted.size();

        // We go through every student and draw their bar
        for (int i = 0; i < sorted.size(); i++) {
          Student s = sorted.get(i); // We get the student

          double value = s.getGrade(); // We get their grade

          // We calculate how tall the bar should be based on their grade (out of 100)
          int barHeight = (int) ((value / 100.0) * chartHeight);

          // This is the X position (left side) where the bar starts
          int x = padding + i * barWidth;

          // This is the Y position (top of the bar)
          int y = height - padding - barHeight;

          // We set the color of the bar to a soft blue
          g.setColor(new Color(100, 149, 237));

          // We draw the filled bar for the student
          g.fillRect(x + 10, y, barWidth - 20, barHeight);

          // We set the color to black so we can draw the bar border
          g.setColor(Color.BLACK);

          // We draw the outline of the bar
          g.drawRect(x + 10, y, barWidth - 20, barHeight);

          // === LABELS ===

          // We change the font for the labels (smaller and cleaner)
          g.setFont(new Font("SansSerif", Font.PLAIN, 10));

          // We get the student’s name
          String name = s.getName();

          // We measure how wide the label text is
          FontMetrics labelFM = g.getFontMetrics();
          int labelWidth = labelFM.stringWidth(name);

          // We figure out where to place the name under the bar (centered)
          int labelX = x + (barWidth - labelWidth) / 2;

          // We draw the name below the bar
          g.drawString(name, labelX, height - padding + 15);

          // We also draw the grade above the bar
          String gradeLabel = String.format("%.2f", value); // Format to 2 decimal places
          int gradeLabelWidth = labelFM.stringWidth(gradeLabel);
          g.drawString(gradeLabel, x + (barWidth - gradeLabelWidth) / 2, y - 5);
        }

        // === X AND Y AXES ===

        // We draw the bottom X-axis line
        g.setColor(Color.BLACK);
        g.drawLine(padding, height - padding, width - padding, height - padding);

        // We draw the left Y-axis line
        g.drawLine(padding, height - padding, padding, padding);
      }
    };

    // We add the chart panel to the window
    frame.add(chartPanel);

    // We make the window pop up on the screen
    frame.setVisible(true);
  }

  // ------- End Chart Bar Section

  // We save student data to a file so it's not lost when the app closes
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

  // We load student data from the file when the app starts
  public void loadFromFile() {
    File file = new File(FILE_NAME);
    if (!file.exists())
      return;

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
