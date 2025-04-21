public class Student {
    private String id;
    private String name;
    private double grade;
  
    public Student(String id, String name, double grade) {
      this.id = id;
      this.name = name;
      this.grade = grade;
    }
  
    public String getId() {
      return id;
    }
  
    public String getName() {
      return name;
    }
  
    public double getGrade() {
      return grade;
    }
  
    public void setGrade(double grade) {
      this.grade = grade;
    }
  
    @Override
    public String toString() {
      return id + " | " + name + " | " + grade;
    }
  }
