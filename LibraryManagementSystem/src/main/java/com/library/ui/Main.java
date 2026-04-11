package com.library.ui;

import com.library.service.LibraryService;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.Group;

public class Main extends Application {

    private LibraryService service;
    private StackPane contentArea;

    private DashboardTab dashboardTabNode;
    private BooksTab booksTabNode;
    private MembersTab membersTabNode;
    private IssueTab issueTabNode;

    @Override
    public void start(Stage primaryStage) {
        service = new LibraryService();

        dashboardTabNode = new DashboardTab(service);
        booksTabNode = new BooksTab(service);
        membersTabNode = new MembersTab(service);
        issueTabNode = new IssueTab(service, () -> {
            dashboardTabNode.refreshData();
            booksTabNode.refreshTable();
        });

        BorderPane root = new BorderPane();
        root.getStyleClass().add("app-root");

        contentArea = new StackPane();
        contentArea.getChildren().add(dashboardTabNode);
        root.setCenter(contentArea);

        // Sidebar 
        VBox sidebar = new VBox(2);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(220);

        // Brand
        VBox brandBox = new VBox(0);
        brandBox.setPadding(new Insets(0, 0, 30, 0));
        
        // Brand logo from SVG
        Group brandLogo = IllustrationFactory.getBrandLogo();
        
        Label brandName = new Label("BookBase");
        brandName.getStyleClass().add("brand-label");
        
        Label brandSub = new Label("LIBRARY SYSTEM");
        brandSub.getStyleClass().add("brand-subtext");
        
        HBox brandTop = new HBox(10, brandLogo, brandName);
        brandTop.setAlignment(Pos.CENTER_LEFT);
        brandBox.getChildren().addAll(brandTop, brandSub);

        // Categories
        Label mainLabel = new Label("MAIN");
        mainLabel.getStyleClass().add("sidebar-category");

        Button btnDashboard = createNavButton(" Dashboard", "🏠");
        Button btnBooks = createNavButton(" Books", "📖");
        Button btnMembers = createNavButton(" Members", "👥");
        Button btnIssues = createNavButton(" Issue / Return", "⮂");

        btnDashboard.getStyleClass().add("nav-active");

        setupNavigation(btnDashboard, dashboardTabNode);
        setupNavigation(btnBooks, booksTabNode);
        setupNavigation(btnMembers, membersTabNode);
        setupNavigation(btnIssues, issueTabNode);

        // Spacer
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // Profile Area
        HBox profileBox = new HBox(10);
        profileBox.setAlignment(Pos.CENTER_LEFT);
        profileBox.getStyleClass().add("profile-area");
        
        Circle avatar = new Circle(15, Color.web("#26231C")); // Avatar placeholder
        
        VBox profileDetails = new VBox();
        Label pName = new Label("Librarian"); pName.getStyleClass().add("profile-name");
        Label pRole = new Label("Admin"); pRole.getStyleClass().add("profile-role");
        profileDetails.getChildren().addAll(pName, pRole);
        
        profileBox.getChildren().addAll(avatar, profileDetails);

        sidebar.getChildren().addAll(brandBox, mainLabel, btnDashboard, btnBooks, btnMembers, btnIssues, spacer, profileBox);
        root.setLeft(sidebar);

        Scene scene = new Scene(root, 1200, 800);
        String css = getClass().getResource("/styles.css").toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.setTitle("BookBase - Library System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createNavButton(String text, String unicodeIcon) {
        // You can replace the text here with a graphic using an SVGPath later.
        Button btn = new Button(unicodeIcon + "   " + text);
        btn.getStyleClass().add("nav-button");
        btn.setMaxWidth(Double.MAX_VALUE);
        return btn;
    }

    private void setupNavigation(Button btn, VBox contentNode) {
        btn.setOnAction(e -> {
            VBox sidebar = (VBox) btn.getParent();
            sidebar.getChildren().stream()
                .filter(node -> node instanceof Button)
                .forEach(node -> node.getStyleClass().remove("nav-active"));
            btn.getStyleClass().add("nav-active");

            contentArea.getChildren().clear();
            contentArea.getChildren().add(contentNode);
            
            if (contentNode == dashboardTabNode) dashboardTabNode.refreshData();
            if (contentNode == booksTabNode) booksTabNode.refreshTable();
            if (contentNode == membersTabNode) membersTabNode.refreshTable();
            if (contentNode == issueTabNode) issueTabNode.refreshTable();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
