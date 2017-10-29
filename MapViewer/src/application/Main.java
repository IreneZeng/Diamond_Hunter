package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application 
{	
	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
				BorderPane root = new BorderPane();
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Parent content  = FXMLLoader.load(getClass().getClassLoader().getResource("Map.fxml"));
				
				root.setCenter(content);
				
				primaryStage.setResizable(false);	// fix the scene
				primaryStage.setScene(scene);
				primaryStage.show();
		}
		
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
