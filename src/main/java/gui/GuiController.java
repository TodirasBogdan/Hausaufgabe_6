package gui;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import model.Student;
import repository.CourseJDBCRepository;
import repository.EnrolledJDBCRepository;
import repository.StudentJDBCRepository;
import repository.TeacherJDBCRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class GuiController {

    CourseJDBCRepository courseJDBCRepository = new CourseJDBCRepository();
    StudentJDBCRepository studentJDBCRepository = new StudentJDBCRepository();
    TeacherJDBCRepository teacherJDBCRepository = new TeacherJDBCRepository();
    EnrolledJDBCRepository enrolledJDBCRepository = new EnrolledJDBCRepository();
    Controller controller = new Controller(courseJDBCRepository, studentJDBCRepository, teacherJDBCRepository, enrolledJDBCRepository);

    @FXML
    private TextField firstNameStudent;
    @FXML
    private TextField lastNameStudent;
    @FXML
    private TextField studentIdStudent;
    @FXML
    private TextField totalCreditsStudent;
    @FXML
    private TextField courseIdStudent;
    @FXML
    private Label numberOfCreditsStudent;
    @FXML
    private TextField firstNameTeacher;
    @FXML
    private TextField lastNameTeacher;
    @FXML
    private TextField teacherIdTeacher;
    @FXML
    private ListView<String> studentListTeacher;
    @FXML
    private TextField courseIdTeacher;


    @FXML
    protected void onStudentMenuButtonClick() throws IOException {
        GuiApplication.showMenu("studentMenu-view.fxml", "Student Menu");
    }

    @FXML
    protected void onTeacherMenuButtonClick() throws IOException {
        GuiApplication.showMenu("teacherMenu-view.fxml", "Teacher Menu");
    }

    @FXML
    protected void saveStudent() throws IOException {
        String firstName = firstNameStudent.getText();
        String lastName = lastNameStudent.getText();
        int studentId = Integer.parseInt(studentIdStudent.getText());
        int totalCredits = Integer.parseInt(totalCreditsStudent.getText());
        controller.addStudent(firstName, lastName, studentId, totalCredits);
    }

    @FXML
    protected void registerStudent() throws IOException {
        int studentId = Integer.parseInt(studentIdStudent.getText());
        int courseId = Integer.parseInt(courseIdStudent.getText());
        controller.registerById(studentId, courseId);
    }

    @FXML
    protected void showTotalCreditsStudent() throws IOException {
        int studentId = Integer.parseInt(studentIdStudent.getText());
        ArrayList<Student> students = controller.findAllStudents();
        boolean found = false;
        for (Student student : students) {
            if (student.getStudentId() == studentId) {
                numberOfCreditsStudent.setText("" + student.getTotalCredits());
                found = true;
            }
        }
        if (!found) {
            numberOfCreditsStudent.setText("Student not found");
        }
    }


    @FXML
    protected void saveTeacher() throws IOException {
        String firstName = firstNameTeacher.getText();
        String lastName = lastNameTeacher.getText();
        int teacherId = Integer.parseInt(teacherIdTeacher.getText());
        controller.addTeacher(firstName, lastName, teacherId);
    }

    @FXML
    protected void showStudents() throws IOException {
        if (teacherIdTeacher.getText() != null) {
            studentListTeacher.getItems().clear();
            ArrayList<Student> students = this.controller.findStudentsByTeacherId(Integer.parseInt(teacherIdTeacher.getText()));
            List<String> strings = students.stream().map(Student::toString).collect(Collectors.toList());
            studentListTeacher.getItems().addAll(strings);
            studentListTeacher.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }
    }

    @FXML
    protected void refresh() throws IOException {
        if (courseIdTeacher.getText() != null) {
            studentListTeacher.getItems().clear();
            ArrayList<Student> students = this.controller.findStudentsByCourseId(Integer.parseInt(courseIdTeacher.getText()));
            List<String> strings = students.stream().map(Student::toString).collect(Collectors.toList());
            studentListTeacher.getItems().addAll(strings);
            studentListTeacher.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }
    }

    @FXML
    protected void clearStudent(){
        firstNameStudent.clear();
        lastNameStudent.clear();
        studentIdStudent.clear();
        totalCreditsStudent.clear();
        courseIdStudent.clear();
    }

    @FXML
    protected void clearTeacher(){
        firstNameTeacher.clear();
        lastNameTeacher.clear();
        teacherIdTeacher.clear();
        courseIdTeacher.clear();
        studentListTeacher.getItems().clear();
    }


}