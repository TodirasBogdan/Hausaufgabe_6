package repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class EnrolledJDBCRepository {

    private String db_url;
    private String user;
    private String password;

    /**
     * opens connection to mysql using config.properties file
     */
    public void openConnection() throws IOException {
        FileInputStream fis = new FileInputStream("D:\\Downloads\\LECTII UBB\\An_2 Semestrul_1\\Metode Avansate de Programare\\Laborator\\Hausaufgabe_6\\src\\main\\resources\\config.properties");
        Properties prop = new Properties();
        prop.load(fis);
        db_url = prop.getProperty("db_url");
        user = prop.getProperty("user");
        password = prop.getProperty("password");
    }

    /**
     * closes connection with mysql
     */
    public void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

    /**
     * save tuple studentId and courseId into enrolled table
     */
    public void save(int studentId, int courseId) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(db_url, user, password); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO enrolled VALUES (?,?)")) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.executeUpdate();
            closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * delete tuple studentId and courseId from enrolled table
     */
    public void delete(int studentId, int courseId) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(db_url, user, password); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM enrolled WHERE studentId = ? AND courseId = ?")) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.executeUpdate();
            closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * delete all tuples with courseId from enrolled table
     */
    public void deleteAllStudentsFromCourse(int courseId) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(db_url, user, password); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM enrolled WHERE courseId = ?")) {
            preparedStatement.setInt(1, courseId);
            preparedStatement.executeUpdate();
            closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * delete all tuples with studentId from enrolled table
     */
    public void deleteAllCoursesFromStudent(int studentId) throws IOException {
        openConnection();
        try (Connection connection = DriverManager.getConnection(db_url, user, password); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM enrolled WHERE studentId = ?")) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.executeUpdate();
            closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * return a list of student Ids from a specific course
     */
    public ArrayList<Integer> findStudentsByCourseId(int courseId) throws IOException {
        openConnection();
        ArrayList<Integer> studentsIds = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(db_url, user, password); Statement statement = connection.createStatement()) {
            String QUERY = "SELECT studentId, courseId FROM enrolled";
            ResultSet resultSet = statement.executeQuery(QUERY);

            while (resultSet.next()) {
                if (resultSet.getInt("courseId") == courseId) {
                    studentsIds.add(resultSet.getInt("studentId"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentsIds;
    }

}
