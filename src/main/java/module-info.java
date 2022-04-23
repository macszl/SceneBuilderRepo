module com.example.scenebuilderrepo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.scenebuilderrepo to javafx.fxml;
    exports com.example.scenebuilderrepo;
}