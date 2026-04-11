package com.library.ui;

import com.library.model.Member;
import com.library.service.LibraryService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.Group;

public class MembersTab extends VBox {
    private LibraryService service;
    private TableView<Member> table;
    
    private TextField idField;
    private TextField nameField;
    private TextField phoneField;
    private TextField emailField;

    public MembersTab(LibraryService service) {
        this.service = service;
        setSpacing(20);
        getStyleClass().add("content-area");

        // Header
        Label catLabel = new Label("PEOPLE");
        catLabel.getStyleClass().add("page-category");
        Label title = new Label("Manage Members");
        title.getStyleClass().add("title-label");
        Label subTitle = new Label("Register new members and manage existing ones.");
        subTitle.getStyleClass().add("subtitle-label");
        
        // Info Card
        HBox infoCard = new HBox(25);
        infoCard.getStyleClass().add("info-card");
        Group illustration = IllustrationFactory.getMembersIllustration();
        VBox tbox = new VBox(2);
        Label iTag = new Label("Member Registry"); iTag.getStyleClass().add("info-card-title");
        Label iSub = new Label("Select a row to auto-fill the form below. Manage member registrations and details."); iSub.getStyleClass().add("info-card-desc");
        tbox.getChildren().addAll(iTag, iSub);
        infoCard.getChildren().addAll(illustration, tbox);

        // Table
        table = new TableView<>();
        setupTable();
        refreshTable();
        VBox.setVgrow(table, Priority.ALWAYS);

        // Form Container
        VBox formContainer = new VBox(20);
        formContainer.getStyleClass().add("form-container");
        Label formTitle = new Label("Register New Member");
        formTitle.getStyleClass().add("info-card-title");
        
        GridPane form = new GridPane();
        form.setHgap(20);
        form.setVgap(10);
        
        idField = new TextField(); idField.setPromptText("e.g. M001");
        nameField = new TextField(); nameField.setPromptText("Member name");
        phoneField = new TextField(); phoneField.setPromptText("10-digit number");
        emailField = new TextField(); emailField.setPromptText("email@example.com");

        Label lbId = new Label("MEMBER ID"); lbId.getStyleClass().add("form-label");
        Label lbNm = new Label("FULL NAME"); lbNm.getStyleClass().add("form-label");
        Label lbPh = new Label("PHONE"); lbPh.getStyleClass().add("form-label");
        Label lbEm = new Label("EMAIL"); lbEm.getStyleClass().add("form-label");

        form.add(lbId, 0, 0); form.add(idField, 0, 1);
        form.add(lbNm, 1, 0); form.add(nameField, 1, 1);
        form.add(lbPh, 0, 2); form.add(phoneField, 0, 3);
        form.add(lbEm, 1, 2); form.add(emailField, 1, 3);
        
        ColumnConstraints cc1 = new ColumnConstraints(); cc1.setPercentWidth(50);
        ColumnConstraints cc2 = new ColumnConstraints(); cc2.setPercentWidth(50);
        form.getColumnConstraints().addAll(cc1, cc2);

        HBox buttons = new HBox(15);
        Button addButton = new Button("+ Add Member"); // or Update if selected
        addButton.getStyleClass().add("button-primary");
        addButton.setOnAction(e -> addMember());

        Button deleteButton = new Button("🗑 Delete Selected");
        deleteButton.getStyleClass().add("button-danger");
        deleteButton.setOnAction(e -> deleteMember());

        Button clearButton = new Button("Clear");
        clearButton.getStyleClass().add("button-secondary");
        clearButton.setOnAction(e -> clearForm());

        buttons.getChildren().addAll(addButton, deleteButton, clearButton);
        formContainer.getChildren().addAll(formTitle, form, buttons);

        getChildren().addAll(catLabel, title, subTitle, infoCard, table, formContainer);

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if(newSel != null) {
                idField.setText(newSel.getMemberId());
                nameField.setText(newSel.getName());
                phoneField.setText(newSel.getPhone());
                emailField.setText(newSel.getEmail());
            }
        });
    }

    private void setupTable() {
        TableColumn<Member, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        idCol.setMaxWidth(100);

        TableColumn<Member, String> nameCol = new TableColumn<>("NAME");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Member, String> phoneCol = new TableColumn<>("PHONE");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Member, String> emailCol = new TableColumn<>("EMAIL");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        TableColumn<Member, String> issuedCol = new TableColumn<>("BOOKS ISSUED");
        issuedCol.setCellValueFactory(cell -> {
            long count = service.getAllIssues().stream().filter(i -> i.getMemberId().equals(cell.getValue().getMemberId()) && !i.isReturned()).count();
            return new SimpleStringProperty(count > 0 ? String.valueOf(count) : "None");
        });

        table.getColumns().addAll(idCol, nameCol, phoneCol, emailCol, issuedCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void refreshTable() {
        table.setItems(FXCollections.observableArrayList(service.getAllMembers()));
    }

    private void addMember() {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            
            if(id.isEmpty() || name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "All fields are required.");
                return;
            }
            
            if(service.getMemberById(id).isPresent()) {
                service.updateMember(new Member(id, name, phone, email));
            } else {
                service.addMember(new Member(id, name, phone, email));
            }
            refreshTable();
            clearForm();
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    private void deleteMember() {
        Member selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.deleteMember(selected.getMemberId());
            refreshTable();
            clearForm();
        }
    }

    private void clearForm() {
        idField.clear(); nameField.clear(); phoneField.clear(); emailField.clear();
        table.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert a = new Alert(type, msg);
        a.showAndWait();
    }
}
