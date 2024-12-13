module org.example.snake {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.snake to javafx.fxml;
    exports org.example.snake;
}