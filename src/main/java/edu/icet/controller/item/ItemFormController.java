package edu.icet.controller.item;

import com.jfoenix.controls.JFXTextField;
import edu.icet.model.Item;
import edu.icet.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colldesc;

    @FXML
    private TableColumn<?, ?> collitemcode;

    @FXML
    private TableColumn<?, ?> collpacksize;

    @FXML
    private TableColumn<?, ?> collqty;

    @FXML
    private TableColumn<?, ?> collunitprize;

    @FXML
    private TableView<Item> itemtable;

    @FXML
    private JFXTextField txtdesc;

    @FXML
    private JFXTextField txtid;

    @FXML
    private JFXTextField txtpacksize;

    @FXML
    private JFXTextField txtqty;

    @FXML
    private JFXTextField txtunitprize;

    ItemService service;

    @FXML
    void btnAdditemOnAction(ActionEvent event) {
        Item item=new Item(txtid.getText(),txtdesc.getText(), txtpacksize.getText(),Double.parseDouble(txtunitprize.getText()),Integer.parseInt(txtqty.getText()));
        if(service.addItem(item)){
            new Alert(Alert.AlertType.INFORMATION,"Item Added Successfully").show();
            loadtable();
        }else {
            new Alert(Alert.AlertType.ERROR,"Item Not Added").show();
        }
    }

    @FXML
    void btnDeleteItemOnAction(ActionEvent event) {
        if (service.deleteItem(txtid.getText())){
            new Alert(Alert.AlertType.INFORMATION,String.format("Item Name :- %s Added Successfully",txtid.getText())).show();
            loadtable();
        }else {
            new Alert(Alert.AlertType.ERROR,"Item Not Deleted").show();
        }
    }

    @FXML
    void btnUpdateItemOnAction(ActionEvent event) {
        Item item=new Item(txtid.getText(),txtdesc.getText(), txtpacksize.getText(),Double.parseDouble(txtunitprize.getText()),Integer.parseInt(txtqty.getText()));
        if (service.updateItem(item)){
            new Alert(Alert.AlertType.INFORMATION,String.format("Item Name :- %s Updated Successfully",item.getDesc())).show();
            loadtable();
        }else {
            new Alert(Alert.AlertType.ERROR, "Item Not Updated").show();
        }
    }

    @FXML
    void btnViewSearchOnAction(ActionEvent event) {
        Item item = service.saerchItem(txtid.getText());
            txtdesc.setText(item.getDesc());
            txtpacksize.setText(item.getPacksize());
            txtunitprize.setText(item.getUnitprize().toString());
            txtqty.setText(item.getQty().toString());

    }

    private void loadtable(){
        collitemcode.setCellValueFactory(new PropertyValueFactory<>("itemcode"));
        colldesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        collpacksize.setCellValueFactory(new PropertyValueFactory<>("packsize"));
        collunitprize.setCellValueFactory(new PropertyValueFactory<>("unitprize"));
        collqty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();
        String SQL="SELECT *  FROM item;";
        try {
            ResultSet res = CrudUtil.execute(SQL);
            while(res.next()){
                Item item=new Item(
                        res.getString("ItemCode"),
                        res.getString("Description"),
                        res.getString("PackSize"),
                        res.getDouble("UnitPrice"),
                        res.getInt("QtyOnHand")
                );
                itemObservableList.add(item);
            }
            itemtable.setItems(itemObservableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service=ItemServiceImpl.getInstance();
        loadtable();

        itemtable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextToValues(newValue);
        }));

    }

    private void setTextToValues(Item newValue) {
        if(newValue!=null){
           txtid.setText(newValue.getItemcode());
           txtdesc.setText(newValue.getDesc());
           txtpacksize.setText(""+newValue.getQty());
           txtunitprize.setText(""+newValue.getUnitprize());
           txtqty.setText(""+newValue.getQty());
        }
    }
}
