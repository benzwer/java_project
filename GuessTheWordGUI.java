
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GuessTheWordGUI extends Application{
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		VBox root = new VBox(new GuessTheWordPanel());
		root.setAlignment(Pos.CENTER);
		root.setSpacing(10);
		root.setStyle("-fx-padding: 20px; -fx-font-weight: bold; -fx-background-color: skyblue; -fx-font-size: 15; -fx-font-family: monospace;");
		Scene scene = new Scene(root, 680, 600);

		primaryStage.setTitle("Guess the word game");
		primaryStage.setScene(scene);
        primaryStage.show();
        	
	}

	public static void main(String[] args) {
		
		launch(args);
	}

}
