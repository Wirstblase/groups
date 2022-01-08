module com.wapps.groups {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires mongo.java.driver;
    requires org.json;
    requires json.simple;


    opens com.wapps.groups to javafx.fxml;
    exports com.wapps.groups;
}