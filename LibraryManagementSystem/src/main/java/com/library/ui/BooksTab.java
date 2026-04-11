package com.library.ui;

import com.library.model.Book;
import com.library.model.BookCategory;
import com.library.service.LibraryService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.Group;

public class BooksTab extends VBox {
    private LibraryService service;
    private TableView<Book> table;
    
    private TextField idField;
    private TextField titleField;
    private TextField authorField;
    private ComboBox<BookCategory> categoryBox;
    private TextField priceField;

    public BooksTab(LibraryService service) {
        this.service = service;
        setSpacing(20);
        getStyleClass().add("content-area");

        // Header
        Label catLabel = new Label("COLLECTION");
        catLabel.getStyleClass().add("page-category");
        Label title = new Label("Manage Books");
        title.getStyleClass().add("title-label");
        Label subTitle = new Label("Add, view, and remove books from the library collection.");
        subTitle.getStyleClass().add("subtitle-label");
        
        // Info Card
        HBox infoCard = new HBox(25);
        infoCard.getStyleClass().add("info-card");
        Group illustration = IllustrationFactory.getBooksIllustration();
        VBox tbox = new VBox(2);
        Label iTag = new Label("Library Collection"); iTag.getStyleClass().add("info-card-title");
        Label iSub = new Label("Select a row to auto-fill the form below. Add or remove books from the catalog."); iSub.getStyleClass().add("info-card-desc");
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
        Label formTitle = new Label("Add New Book");
        formTitle.getStyleClass().add("info-card-title");
        
        GridPane form = new GridPane();
        form.setHgap(20);
        form.setVgap(10);
        
        idField = new TextField(); idField.setPromptText("e.g. B001");
        titleField = new TextField(); titleField.setPromptText("Book title");
        authorField = new TextField(); authorField.setPromptText("Author name");
        categoryBox = new ComboBox<>(FXCollections.observableArrayList(BookCategory.values()));
        categoryBox.setPromptText("Select category");
        categoryBox.setMaxWidth(Double.MAX_VALUE);
        priceField = new TextField(); priceField.setPromptText("e.g. 299");

        Label lbId = new Label("BOOK ID"); lbId.getStyleClass().add("form-label");
        Label lbTr = new Label("TITLE"); lbTr.getStyleClass().add("form-label");
        Label lbAu = new Label("AUTHOR"); lbAu.getStyleClass().add("form-label");
        Label lbCt = new Label("CATEGORY"); lbCt.getStyleClass().add("form-label");
        Label lbPr = new Label("PRICE (₹)"); lbPr.getStyleClass().add("form-label");

        form.add(lbId, 0, 0); form.add(idField, 0, 1);
        form.add(lbTr, 1, 0); form.add(titleField, 1, 1);
        form.add(lbAu, 0, 2); form.add(authorField, 0, 3);
        form.add(lbCt, 1, 2); form.add(categoryBox, 1, 3);
        form.add(lbPr, 0, 4); form.add(priceField, 0, 5);
        
        // Ensure columns spread evenly
        ColumnConstraints cc1 = new ColumnConstraints(); cc1.setPercentWidth(50);
        ColumnConstraints cc2 = new ColumnConstraints(); cc2.setPercentWidth(50);
        form.getColumnConstraints().addAll(cc1, cc2);

        HBox buttons = new HBox(15);
        Button addButton = new Button("+ Add Book");
        addButton.getStyleClass().add("button-primary");
        addButton.setOnAction(e -> addBook());

        Button deleteButton = new Button("🗑 Delete Selected");
        deleteButton.getStyleClass().add("button-danger");
        deleteButton.setOnAction(e -> deleteBook());

        Button clearButton = new Button("Clear");
        clearButton.getStyleClass().add("button-secondary");
        clearButton.setOnAction(e -> clearForm());

        buttons.getChildren().addAll(addButton, deleteButton, clearButton);
        formContainer.getChildren().addAll(formTitle, form, buttons);

        getChildren().addAll(catLabel, title, subTitle, infoCard, table, formContainer);
        
        // Auto-fill logic
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if(newSel != null) {
                idField.setText(newSel.getBookId());
                titleField.setText(newSel.getTitle());
                authorField.setText(newSel.getAuthor());
                categoryBox.setValue(newSel.getCategory());
                priceField.setText(String.valueOf(newSel.getPrice()));
            }
        });
    }

    private void setupTable() {
        TableColumn<Book, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        idCol.setMaxWidth(100);

        TableColumn<Book, String> titleCol = new TableColumn<>("TITLE");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        
        TableColumn<Book, String> authorCol = new TableColumn<>("AUTHOR");
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, BookCategory> catCol = new TableColumn<>("CATEGORY");
        catCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Book, String> priceCol = new TableColumn<>("PRICE (₹)");
        priceCol.setCellValueFactory(cell -> new SimpleStringProperty("₹" + cell.getValue().getPrice()));

        TableColumn<Book, String> availCol = new TableColumn<>("AVAILABLE");
        availCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().isAvailable() ? "● Available" : "● Unavailable"));

        table.getColumns().addAll(idCol, titleCol, authorCol, catCol, priceCol, availCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void refreshTable() {
        table.setItems(FXCollections.observableArrayList(service.getAllBooks()));
    }

    private void addBook() {
        try {
            String id = idField.getText().trim();
            String t = titleField.getText().trim();
            String a = authorField.getText().trim();
            BookCategory cat = categoryBox.getValue();
            if(id.isEmpty() || t.isEmpty() || a.isEmpty() || cat == null || priceField.getText().trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "All fields are required.");
                return;
            }
            double price = Double.parseDouble(priceField.getText().trim());
            // Update if exists, otherwise save new
            if(service.getBookById(id).isPresent()){
                service.updateBook(new Book(id, t, a, cat, price, service.getBookById(id).get().isAvailable()));
            } else {
                service.addBook(new Book(id, t, a, cat, price, true));
            }
            refreshTable();
            clearForm();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    private void deleteBook() {
        Book selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.deleteBook(selected.getBookId());
            refreshTable();
            clearForm();
        }
    }

    private void clearForm() {
        idField.clear(); titleField.clear(); authorField.clear();
        categoryBox.setValue(null); priceField.clear();
        table.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert a = new Alert(type, msg);
        a.showAndWait();
    }
}
