module org.example.class_searchrescue {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.class_searchrescue to javafx.fxml;
    exports org.example.class_searchrescue;
    exports org.example.class_searchrescue.Object;
    opens org.example.class_searchrescue.Object to javafx.fxml;
    exports org.example.class_searchrescue.App;
    opens org.example.class_searchrescue.App to javafx.fxml;
}