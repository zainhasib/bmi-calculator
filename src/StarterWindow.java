import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class StarterWindow extends Application {

    private double weight;
    private double height;
    private double bmi;
    private String category;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage base) throws Exception {
        base.setTitle("BMI Calculator");
        base.getIcons().add(new Image("https://image.ibb.co/mo4BDy/unnamed.png"));


        MenuItem cut = new MenuItem("Cut");
        MenuItem copy = new MenuItem("Copy");
        MenuItem paste = new MenuItem("Paste");

        final ContextMenu editMenu = new ContextMenu(cut, copy, paste);



        // Initialisation
        Label title = new Label("BMI CALCULATOR");
        title.setFont(new Font(20));
        title.setId("title");
        Label h = new Label("Height : ");
        Label w = new Label("Weight : ");
        TextField hField = new TextField();
        hField.setContextMenu(editMenu);
        TextField wField = new TextField();
        wField.setContextMenu(editMenu);
        Button submit = new Button("Submit");
        submit.setId("primary");
        submit.setOnAction(event -> {
            // Get TextField Content
            height = Double.parseDouble(hField.getText());
            weight = Double.parseDouble(wField.getText());
            calculateBMI();
            categoryBMI();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("BMI Result");
            alert.setHeaderText("Your BMI Category is: " + category + ". Wanna learn more?");
            alert.setContentText("Your BMI is " + String.valueOf(bmi));
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                openCategoriesWindow();
                //base.close();
            }


        });

        editMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = ((MenuItem)event.getTarget()).getText();
                if (name.equals("Copy")) {
                    Clipboard systemClipboard = Clipboard.getSystemClipboard();
                    ClipboardContent content = new ClipboardContent();
                    content.putString(hField.getSelectedText());
                    systemClipboard.setContent(content);
                    System.out.println(hField.getSelectedText());
                }
                if (name.equals("Paste")) {

                }
            }
        });

        GridPane grid = new GridPane();
        grid.setMinSize(0, 200);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);

        HBox h1 = new HBox();
        h1.setSpacing(10);
        h1.setAlignment(Pos.CENTER);

        h1.getChildren().addAll(title);

        HBox h2 = new HBox();
        h2.setSpacing(10);
        h2.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(0, 100);
        gridPane.setPadding(new Insets(2, 10, 2, 10));
        gridPane.setVgap(1);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(h, 0, 0);
        gridPane.add(hField, 1,0);
        gridPane.add(w, 0, 1);
        gridPane.add(wField, 1,1);

        h2.getChildren().addAll(gridPane);

        HBox h3 = new HBox();
        h3.setSpacing(10);
        h3.setAlignment(Pos.CENTER);

        h3.getChildren().addAll(submit);


        grid.add(h1, 0, 0);
        grid.add(h2, 0, 1);
        grid.add(h3, 0, 2);

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        StackPane stackPane = new StackPane(scrollPane);
        stackPane.setId("root");
        Scene scene = new Scene(stackPane, 300,300);
        scene.getStylesheets().add("https://raw.githubusercontent.com/dicolar/jbootx/master/src/main/resources/bootstrap3.css");

        base.setScene(scene);

        base.show();
    }

    private void openCategoriesWindow() {
        switch (category) {
            case "Underweight": {
                underWeightWindow();
                break;
            }
            case "Normal": {
                normalWindow();
                break;
            }
            case "Overweight": {
                overWeightWindow();
                break;
            }
            case "Obese": {
                obeseWindow();
                break;
            }
            default: System.out.println("ERROR");
        }
    }

    private void obeseWindow() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        Stage obeseStage = new Stage();
        obeseStage.setMaximized(true);
        obeseStage.setTitle("Obesity : Causes, Complications & Diagnosis");
        obeseStage.getIcons().add(new Image("https://image.ibb.co/mo4BDy/unnamed.png"));

        WebView browser = new WebView();
        browser.setCache(true);
        WebEngine webEngine = browser.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.load("https://medlineplus.gov/obesity.html");
        browser.setPrefSize(bounds.getWidth(),bounds.getHeight());


        Group group = new Group(browser);
        Scene scene = new Scene(group);
        obeseStage.setScene(scene);

        obeseStage.show();
    }

    private void overWeightWindow() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        Stage overWeightStage = new Stage();
        overWeightStage.setMaximized(true);
        overWeightStage.setTitle("Overweight : Causes, Risks & Solutions");
        overWeightStage.getIcons().add(new Image("https://image.ibb.co/mo4BDy/unnamed.png"));

        WebView browser = new WebView();
        browser.setCache(true);
        WebEngine webEngine = browser.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.load("https://www.medicinenet.com/script/main/art.asp?articlekey=91817");
        browser.setPrefSize(bounds.getWidth(),bounds.getHeight());


        Group group = new Group(browser);
        Scene scene = new Scene(group);
        overWeightStage.setScene(scene);

        overWeightStage.show();
    }

    private void normalWindow() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        Stage normalStage = new Stage();
        normalStage.setMaximized(true);
        normalStage.setTitle("Normal : Benefits, Diet & Healthy Life");
        normalStage.getIcons().add(new Image("https://image.ibb.co/mo4BDy/unnamed.png"));

        WebView browser = new WebView();
        browser.setCache(true);
        WebEngine webEngine = browser.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.load("http://www.health.com/");
        browser.setPrefSize(bounds.getWidth(),bounds.getHeight());


        Group group = new Group(browser);
        Scene scene = new Scene(group);
        normalStage.setScene(scene);

        normalStage.show();
    }

    private void underWeightWindow() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        Stage underWeightStage = new Stage();
        underWeightStage.setMaximized(true);
        underWeightStage.setTitle("Underweight : Causes, Risks & Solutions");
        underWeightStage.getIcons().add(new Image("https://image.ibb.co/mo4BDy/unnamed.png"));

        WebView browser = new WebView();
        browser.setCache(true);
        WebEngine webEngine = browser.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.load("https://www.verywellfit.com/signs-of-being-underweight-4108192");
        browser.setPrefSize(bounds.getWidth(),bounds.getHeight());


        Group group = new Group(browser);
        Scene scene = new Scene(group);
        underWeightStage.setScene(scene);

        underWeightStage.show();
    }

    private void calculateBMI() {
        double bmiTemp = weight / (height * height);
        bmi = Math.round(bmiTemp * 100000.0) / 10.0;
    }

    private void categoryBMI() {
        StringBuilder cat = new StringBuilder("");
        if (bmi < 18.50) {
            cat.append("Underweight");
        } else if (bmi<24.99 && bmi>18.50) {
            cat.append("Normal");
        } else if (bmi > 25.00 && bmi < 29.99) {
            cat.append("Overweight");
        } else if (bmi > 30.00) {
            cat.append("Obese");
        }
        category = cat.toString();
    }
}
