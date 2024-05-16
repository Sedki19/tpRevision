/**
 * 
 */
/**
 * 
 */
module TPTest {
	requires java.sql;
	requires javafx.fxml;
	requires javafx.controls;
	requires javafx.base;
	requires javafx.graphics;
	
	opens Test.application to javafx.base,javafx.graphics, javafx.fxml;
	opens Test.entities to javafx.base,javafx.graphics, javafx.fxml;
	
	
}