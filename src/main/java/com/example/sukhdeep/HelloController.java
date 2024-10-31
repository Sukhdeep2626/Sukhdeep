package com.example.sukhdeep;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public TextField iClientName;
    public TextField iage;
    public TextField iaddress;

    @FXML
    private TableView<FamilyDoctor> tableView;
    @FXML
    private TableColumn<FamilyDoctor, Integer> id;
    @FXML
    private TableColumn<FamilyDoctor, String> ClientName;
    @FXML
    private TableColumn<FamilyDoctor, String> age;
    @FXML
    private TableColumn<FamilyDoctor, String> address;

    ObservableList<FamilyDoctor> list = FXCollections.observableArrayList();

    // Database connection details
    private String jdbcUrl = "jdbc:mysql://localhost:3306/familydoctor";
    private String dbUser = "root";
    private String dbPassword = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        ClientName.setCellValueFactory(new PropertyValueFactory<>("ClientName"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableView.setItems(list);
    }

    @FXML
    protected void onHelloButtonClick() {
        populateTable();
    }

    public void populateTable() {
        // Clear the existing data
        list.clear();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM familydoctor_appointment";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String ClientName = resultSet.getString("ClientName");
                int age = resultSet.getInt("age");
                String address = resultSet.getString("address");
                list.add(new FamilyDoctor(id, ClientName, age, address));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert new data into the database
    public void insert(ActionEvent actionEvent) {
        String name = iClientName.getText();
        int ageValue = Integer.parseInt(iage.getText());
        String addressValue = iaddress.getText();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "INSERT INTO familydoctor_appointment (ClientName, age, address) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, ageValue);
            preparedStatement.setString(3, addressValue);
            preparedStatement.executeUpdate();

            // Refresh the table view
            populateTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update existing data in the database
    public void update(ActionEvent actionEvent) {
        FamilyDoctor selectedRecord = tableView.getSelectionModel().getSelectedItem();
        if (selectedRecord == null) {
            showAlert("No record selected", "Please select a record to update.");
            return;
        }

        String name = iClientName.getText();
        int ageValue = Integer.parseInt(iage.getText());
        String addressValue = iaddress.getText();
        int selectedId = selectedRecord.getId();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "UPDATE familydoctor_appointment SET ClientName = ?, age = ?, address = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, ageValue);
            preparedStatement.setString(3, addressValue);
            preparedStatement.setInt(4, selectedId);
            preparedStatement.executeUpdate();

            // Refresh the table view
            populateTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete data from the database
    public void delete(ActionEvent actionEvent) {
        FamilyDoctor selectedRecord = tableView.getSelectionModel().getSelectedItem();
        if (selectedRecord == null) {
            showAlert("No record selected", "Please select a record to delete.");
            return;
        }

        int selectedId = selectedRecord.getId();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "DELETE FROM familydoctor_appointment WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, selectedId);
            preparedStatement.executeUpdate();

            // Refresh the table view
            populateTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View data from the database (same as populateTable but separate button)
    public void viewdata(ActionEvent actionEvent) {
        populateTable();
    }

    // Helper method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}