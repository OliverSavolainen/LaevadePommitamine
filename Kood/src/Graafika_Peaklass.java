/*package oop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Graafika_Peaklass extends Application {

    //Source: https://stackoverflow.com/questions/43761825/checkerboard-inside-a-window-using-javafx

    //Veel inspot: https://javabook.bloomu.edu/code-html/Chapter6/CheckerBoard.html

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane juur = new GridPane();
        Ruudustik minuLaud = new Ruudustik(10,10,false);

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                minuLaud.paigaldaLaud(i, j);
            }
        }
        juur.add(minuLaud,0,0);
        Scene stseen = new Scene(juur,1000,500);
        primaryStage.setScene(stseen);
        primaryStage.setTitle("Laevade pommitamine");
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
*/