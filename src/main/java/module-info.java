module org.example.class_searchrescue {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.class_searchrescue to javafx.fxml;
    exports org.example.class_searchrescue;
    exports org.example.class_searchrescue.Object;
    opens org.example.class_searchrescue.Object to javafx.fxml;
    exports org.example.class_searchrescue.Application;
    opens org.example.class_searchrescue.Application to javafx.fxml;
}