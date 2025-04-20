package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FlagGrid extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        // Create a GridPane and set spacing between cells
        GridPane grid = new GridPane();
        grid.setHgap(10);  // Horizontal gap between images
        grid.setVgap(10);  // Vertical gap between images

        // Load flag images from resources using GIF format.
        Image flag1 = new Image(getClass().getResourceAsStream("/flags/flag1.gif"));
        Image flag2 = new Image(getClass().getResourceAsStream("/flags/flag2.gif"));
        Image flag3 = new Image(getClass().getResourceAsStream("/flags/flag6.gif"));
        Image flag4 = new Image(getClass().getResourceAsStream("/flags/flag7.gif"));

        // Create ImageView objects for each image
        ImageView imageView1 = new ImageView(flag1);
        ImageView imageView2 = new ImageView(flag2);
        ImageView imageView3 = new ImageView(flag3);
        ImageView imageView4 = new ImageView(flag4);
        
        // Maintain aspect ratio to ensure images don't distort
        imageView1.setPreserveRatio(true);
        imageView2.setPreserveRatio(true);
        imageView3.setPreserveRatio(true);
        imageView4.setPreserveRatio(true);

        // Add images to the grid pane: two images per row
        grid.add(imageView1, 0, 0);
        grid.add(imageView2, 1, 0);
        grid.add(imageView3, 0, 1);
        grid.add(imageView4, 1, 1);

        // Create a scene containing the grid pane and set it in the stage
        Scene scene = new Scene(grid, 350, 250);
        primaryStage.setTitle("Flag Grid");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}
