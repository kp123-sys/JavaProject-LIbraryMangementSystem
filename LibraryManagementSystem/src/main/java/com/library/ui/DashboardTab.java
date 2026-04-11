package com.library.ui;

import com.library.model.IssueRecord;
import com.library.service.LibraryService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Group;

import java.time.LocalDate;

public class DashboardTab extends VBox {
    private LibraryService service;
    
    private Label bVal;
    private Label mVal;
    private Label iVal;
    
    private TableView<IssueRecord> table;

    public DashboardTab(LibraryService service) {
        this.service = service;
        
        setSpacing(30);
        getStyleClass().add("content-area");

        // Top Banner
        HBox bannerContainer = new HBox();
        bannerContainer.getStyleClass().add("info-card");
        
        VBox bannerText = new VBox(10);
        Label welcome = new Label("WELCOME BACK");
        welcome.setStyle("-fx-text-fill: #5E584D; -fx-font-weight: bold; -fx-font-size: 11px; -fx-letter-spacing: 1.5;");
        Label title = new Label("Library Management\nDashboard");
        title.getStyleClass().add("title-label");
        Label sub = new Label("Track your collection, manage members, and oversee\nall book transactions in one place.");
        sub.getStyleClass().add("subtitle-label");
        bannerText.getChildren().addAll(welcome, title, sub);
        
        Region bannerSpacer = new Region();
        HBox.setHgrow(bannerSpacer, Priority.ALWAYS);
        
        Group heroBookshelf = IllustrationFactory.getHeroBookshelf();
        bannerContainer.getChildren().addAll(bannerText, bannerSpacer, heroBookshelf);

        // Metrics Layer
        HBox metrics = new HBox(20);
        
        VBox bCard = createMetricCard("metric-card-books", bVal = new Label("0"), "Total Books", "▲ Active", "badge-green");
        VBox mCard = createMetricCard("metric-card-members", mVal = new Label("0"), "Total Members", "▲ Registered", "badge-green");
        VBox iCard = createMetricCard("metric-card-issues", iVal = new Label("0"), "Active Issues", "● Current", "badge-gray");
        
        // Ensure they share width equally
        HBox.setHgrow(bCard, Priority.ALWAYS);
        HBox.setHgrow(mCard, Priority.ALWAYS);
        HBox.setHgrow(iCard, Priority.ALWAYS);
        metrics.getChildren().addAll(bCard, mCard, iCard);

        // Recent Activity
        HBox actHeader = new HBox();
        Label actLabel = new Label("Recent Activity");
        actLabel.getStyleClass().add("section-title");
        Region sp = new Region(); HBox.setHgrow(sp, Priority.ALWAYS);
        Label vAll = new Label("View all →");
        vAll.setStyle("-fx-text-fill: #C69C54; -fx-font-size: 13px; -fx-padding: 25 0 0 0; -fx-cursor: hand;");
        actHeader.getChildren().addAll(actLabel, sp, vAll);

        table = new TableView<>();
        setupTable();
        VBox.setVgrow(table, Priority.ALWAYS);

        getChildren().addAll(bannerContainer, metrics, actHeader, table);
        refreshData();
    }
    
    private VBox createMetricCard(String borderClass, Label valueTarget, String descStr, String badgeStr, String badgeClass) {
        VBox card = new VBox(10);
        card.getStyleClass().addAll("metric-card", borderClass);
        
        HBox top = new HBox();
        Label icon = new Label("❑"); // Placeholder
        icon.setStyle("-fx-text-fill: #5E584D; -fx-background-color: #26231C; -fx-padding: 5 10; -fx-background-radius: 4;");
        Region space = new Region(); HBox.setHgrow(space, Priority.ALWAYS);
        Label badge = new Label(badgeStr);
        badge.getStyleClass().addAll("metric-badge", badgeClass);
        top.getChildren().addAll(icon, space, badge);
        
        valueTarget.getStyleClass().add("metric-value");
        Label desc = new Label(descStr);
        desc.getStyleClass().add("metric-desc");
        
        card.getChildren().addAll(top, valueTarget, desc);
        return card;
    }

    private void setupTable() {
        TableColumn<IssueRecord, String> idCol = new TableColumn<>("ISSUE ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("issueId"));

        TableColumn<IssueRecord, String> bookCol = new TableColumn<>("BOOK ID");
        bookCol.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        
        TableColumn<IssueRecord, String> memCol = new TableColumn<>("MEMBER ID");
        memCol.setCellValueFactory(new PropertyValueFactory<>("memberId"));

        TableColumn<IssueRecord, LocalDate> dateCol = new TableColumn<>("DATE");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("issueDate"));

        TableColumn<IssueRecord, String> statusCol = new TableColumn<>("STATUS");
        statusCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().isReturned() ? "Returned" : "Active"));

        table.getColumns().addAll(idCol, bookCol, memCol, dateCol, statusCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No recent activity"));
    }

    public void refreshData() {
        bVal.setText(String.valueOf(service.getAllBooks().size()));
        mVal.setText(String.valueOf(service.getAllMembers().size()));
        
        long activeIssues = service.getAllIssues().stream().filter(i -> !i.isReturned()).count();
        iVal.setText(String.valueOf(activeIssues));
        
        table.setItems(FXCollections.observableArrayList(service.getAllIssues()));
    }
}
