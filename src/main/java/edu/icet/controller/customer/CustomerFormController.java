package edu.icet.controller.customer;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.model.Customer;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.CustomerService;
import edu.icet.util.CrudUtil;
import edu.icet.util.ServiceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    CustomerService1 service;
    @FXML
    private TableColumn<?, ?> colladdress;

    @FXML
    private TableColumn<?, ?> collbday;

    @FXML
    private TableColumn<?, ?> collcity;

    @FXML
    private TableColumn<?, ?> collid;

    @FXML
    private TableColumn<?, ?> collname;

    @FXML
    private TableColumn<?, ?> collpostal;

    @FXML
    private TableColumn<?, ?> collprovince;

    @FXML
    private TableColumn<?, ?> collsalary;

    @FXML
    private TableView<Customer> customertable;

    @FXML
    private JFXTextField txtaddress;

    @FXML
    private JFXTextField txtcity;

    @FXML
    private DatePicker txtdob;

    @FXML
    private JFXTextField txtid;

    @FXML
    private JFXTextField txtname;

    @FXML
    private JFXTextField txtpostalcode;

    @FXML
    private JFXTextField txtprovince;

    @FXML
    private JFXTextField txtsalary;

    @FXML
    private JFXComboBox<String> txttitle;

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        CustomerService service1 = ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);
        Customer customer = new Customer(
                txtid.getText(),
                txttitle.getValue(),
                txtname.getText(),
                txtdob.getValue(),
                Double.parseDouble(txtsalary.getText()),
                txtaddress.getText(),
                txtcity.getText(),
                txtprovince.getText(),
                txtpostalcode.getText());

        if (service1.addCustomer(customer)) {
            loadtable();
            new Alert(Alert.AlertType.INFORMATION, "Customer Added !!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Customer Not Added :(").show();
        }
    }

    @FXML
    void btnDeleteCustomerOnAction(ActionEvent event) {
        String SQL = "DELETE FROM customer WHERE CustID=?";
        try {
            Connection connection = DriverManager.getConnection("JDBC:mysql://localhost:3306/thogakade", "root", "123456");
            PreparedStatement pstm = connection.prepareStatement(SQL);
            pstm.setObject(1, txtid.getText());
            boolean b = pstm.executeUpdate() > 0;

            if (b) {
                loadtable();
                new Alert(Alert.AlertType.INFORMATION, "Customer Deleted !!").show();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer Not Deleted !!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Not Deleted !!").show();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSearchCustomerOnAction(ActionEvent event) {

        Customer customer = service.searchCustomer(txtid.getText());
        txttitle.setValue(customer.getTitle());
        txtname.setText(customer.getName());
        txtdob.setValue(customer.getDob());
        txtsalary.setText(customer.getSalary().toString());
        txtaddress.setText(customer.getAddress());
        txtcity.setText(customer.getCity());
        txtprovince.setText(customer.getProvince());
        txtpostalcode.setText(customer.getPostalcode());
    }

    @FXML
    void btnUpdateCustomerOnAction(ActionEvent event) {
        Customer customer = new Customer(txtid.getText(), txttitle.getValue(), txtname.getText(), txtdob.getValue(), Double.parseDouble(txtsalary.getText()), txtaddress.getText(), txtcity.getText(), txtprovince.getText(), txtpostalcode.getText());

        String SQL = "UPDATE customer SET CustTitle = ?, CustName = ?,DOB = ?,salary = ?,CustAddress = ?,City = ?,Province = ?, PostalCode = ? WHERE CustID=?;";
        try {
            boolean b = CrudUtil.execute(SQL, customer.getTitle(), customer.getName(), customer.getDob(), customer.getSalary(), customer.getAddress(), customer.getCity(), customer.getProvince(), customer.getPostalcode(), customer.getId());
            if (b) {
                loadtable();
                new Alert(Alert.AlertType.INFORMATION, "Customer Updated !!").show();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer Not Updated !!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Not Updated !!").show();
            throw new RuntimeException(e);
        }
    }

    private void loadtable() {
        collid.setCellValueFactory(new PropertyValueFactory<>("id"));
        collname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colladdress.setCellValueFactory(new PropertyValueFactory<>("address"));
        collbday.setCellValueFactory(new PropertyValueFactory<>("dob"));
        collcity.setCellValueFactory(new PropertyValueFactory<>("city"));
        collprovince.setCellValueFactory(new PropertyValueFactory<>("province"));
        collsalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        collpostal.setCellValueFactory(new PropertyValueFactory<>("postalcode"));

        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT *  FROM customer;";
            ResultSet res = CrudUtil.execute(SQL);
            while (res.next()) {
                Customer customer = new Customer(
                        res.getString("CustID"),
                        res.getString("CustTitle"),
                        res.getString("CustName"),
                        res.getDate("DOB").toLocalDate(),
                        res.getDouble("salary"),
                        res.getString("CustAddress"),
                        res.getString("City"),
                        res.getString("Province"),
                        res.getString("postalCode")
                );
                customerObservableList.add(customer);
            }
            customertable.setItems(customerObservableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("Mr. ");
        titles.add("Mrs. ");
        txttitle.setItems(titles);
        service = CustomerServiceImp1.getInstance();
        loadtable();

        customertable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextToValues(newValue);
        }));
    }

    private void setTextToValues(Customer newValue) {
        if (newValue != null) {
            txtid.setText(newValue.getId());
            txttitle.setValue(newValue.getTitle());
            txtname.setText(newValue.getName());
            txtdob.setValue(newValue.getDob());
            txtaddress.setText(newValue.getAddress());
            txtcity.setText(newValue.getCity());
            txtprovince.setText(newValue.getProvince());
            txtpostalcode.setText(newValue.getPostalcode());
            txtsalary.setText("" + newValue.getSalary());
        }
    }
}
