module lista {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.controlsfx.controls;

    opens lista to javafx.fxml;
    exports lista;
}
