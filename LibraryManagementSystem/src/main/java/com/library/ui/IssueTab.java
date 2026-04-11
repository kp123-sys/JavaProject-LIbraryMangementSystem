package com.library.ui;

import com.library.model.IssueRecord;
import com.library.service.LibraryService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.Group;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class IssueTab extends VBox {
    private LibraryService service;
    private TableView<IssueRecord> table;
    
    private TextField bookIdField;
    private TextField memberIdField;
    private TextField returnBookIdField;

    private Runnable onDataChanged;

    public IssueTab(LibraryService service, Runnable onDataChanged) {
        this.service = service;
        this.onDataChanged = onDataChanged;
        setSpacing(20);
        getStyleClass().add("content-area");

        // Header
        Label catLabel = new Label("TRANSACTIONS");
        catLabel.getStyleClass().add("page-category");
        Label title = new Label("Issue & Return Books");
        title.getStyleClass().add("title-label");
        Label subTitle = new Label("Manage book lending and returns with full history tracking.");
        subTitle.getStyleClass().add("subtitle-label");
        
        // Info Card
        HBox infoCard = new HBox(25);
        infoCard.getStyleClass().add("info-card");
        Group illustration = IllustrationFactory.getIssueReturnIllustration();
        VBox tbox = new VBox(2);
        Label iTag = new Label("Book Transactions"); iTag.getStyleClass().add("info-card-title");
        Label iSub = new Label("Issue books to registered members and process returns. All transactions are logged below."); iSub.getStyleClass().add("info-card-desc");
        tbox.getChildren().addAll(iTag, iSub);
        infoCard.getChildren().addAll(illustration, tbox);

        // Forms Side by Side
        HBox formsPane = new HBox(20);
        
        // Issue Form
        VBox issueContainer = new VBox(15);
        issueContainer.getStyleClass().add("form-container");
        Label issueTitle = new Label("⮂ ISSUE BOOK");
        issueTitle.getStyleClass().add("form-label");
        
        VBox iFields = new VBox(5);
        Label bIdL = new Label("BOOK ID"); bIdL.getStyleClass().add("form-label");
        bookIdField = new TextField(); bookIdField.setPromptText("Enter Book ID");
        Label mIdL = new Label("MEMBER ID"); mIdL.getStyleClass().add("form-label");
        memberIdField = new TextField(); memberIdField.setPromptText("Enter Member ID");
        iFields.getChildren().addAll(bIdL, bookIdField, new Label(), mIdL, memberIdField);

        Button issueButton = new Button("⮂ Issue Book");
        issueButton.getStyleClass().add("button-primary");
        issueButton.setMaxWidth(Double.MAX_VALUE);
        issueButton.setOnAction(e -> issueBook());
        
        issueContainer.getChildren().addAll(issueTitle, iFields, issueButton);
        HBox.setHgrow(issueContainer, Priority.ALWAYS);

        // Return Form
        VBox returnContainer = new VBox(15);
        returnContainer.getStyleClass().add("form-container");
        Label returnTitle = new Label("⮂ RETURN BOOK");
        returnTitle.getStyleClass().add("form-label");
        
        VBox rFields = new VBox(5);
        Label rIdL = new Label("BOOK ID TO RETURN"); rIdL.getStyleClass().add("form-label");
        returnBookIdField = new TextField(); returnBookIdField.setPromptText("Enter Book ID");
        rFields.getChildren().addAll(rIdL, returnBookIdField);

        Region spacer = new Region(); VBox.setVgrow(spacer, Priority.ALWAYS);

        Button returnButton = new Button("⮂ Return Book");
        returnButton.getStyleClass().add("button-secondary"); // As per design
        returnButton.setStyle("-fx-border-color: #2F2A21; -fx-text-fill: #4CAF50; -fx-background-color: #19221C;");
        returnButton.setMaxWidth(Double.MAX_VALUE);
        returnButton.setOnAction(e -> returnBook());
        
        returnContainer.getChildren().addAll(returnTitle, rFields, spacer, returnButton);
        HBox.setHgrow(returnContainer, Priority.ALWAYS);

        formsPane.getChildren().addAll(issueContainer, returnContainer);

        // History Label
        Label histLabel = new Label("Issue History");
        histLabel.getStyleClass().add("section-title");

        // Table
        table = new TableView<>();
        setupTable();
        refreshTable();
        VBox.setVgrow(table, Priority.ALWAYS);

        getChildren().addAll(catLabel, title, subTitle, infoCard, formsPane, histLabel, table);
    }

    private void setupTable() {
        TableColumn<IssueRecord, String> idCol = new TableColumn<>("ISSUE ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("issueId"));

        TableColumn<IssueRecord, String> bookIdCol = new TableColumn<>("BOOK ID");
        bookIdCol.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        
        TableColumn<IssueRecord, String> memberIdCol = new TableColumn<>("MEMBER ID");
        memberIdCol.setCellValueFactory(new PropertyValueFactory<>("memberId"));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, yyyy");

        TableColumn<IssueRecord, String> issueDateCol = new TableColumn<>("ISSUE DATE");
        issueDateCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getIssueDate().format(dtf)));

        TableColumn<IssueRecord, String> returnDateCol = new TableColumn<>("RETURN DATE");
        returnDateCol.setCellValueFactory(cell -> {
            LocalDate rDate = cell.getValue().getReturnDate();
            return new SimpleStringProperty(rDate != null ? rDate.format(dtf) : "-");
        });

        TableColumn<IssueRecord, String> statusCol = new TableColumn<>("STATUS");
        statusCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().isReturned() ? "Returned" : "Active"));

        table.getColumns().addAll(idCol, bookIdCol, memberIdCol, issueDateCol, returnDateCol, statusCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No transactions yet."));
    }

    public void refreshTable() {
        table.setItems(FXCollections.observableArrayList(service.getAllIssues()));
    }

    private void issueBook() {
        try {
            String bookId = bookIdField.getText().trim();
            String memberId = memberIdField.getText().trim();
            
            if(bookId.isEmpty() || memberId.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Book ID and Member ID are required.");
                return;
            }
            
            service.issueBook(bookId, memberId);
            refreshTable();
            bookIdField.clear(); memberIdField.clear();
            onDataChanged.run();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    private void returnBook() {
        try {
            String bookId = returnBookIdField.getText().trim();
            
            if(bookId.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Book ID is required.");
                return;
            }
            
            service.returnBook(bookId);
            refreshTable();
            returnBookIdField.clear();
            onDataChanged.run();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert a = new Alert(type, msg);
        a.showAndWait();
    }
}
