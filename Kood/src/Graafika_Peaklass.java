package oop;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Graafika_Peaklass extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane juur = new GridPane();
        Ruudustik minuLaud = new Ruudustik(10,10,false);
        Ruudustik vastaseLaud = new Ruudustik(10,10,true);
        //Vahed ekraani äärte vahel
        juur.setPadding(new Insets(10,10,10,10));
        //Vahed elementide vahel
        juur.setVgap(8);
        juur.setHgap(10);
        GridPane.setConstraints(minuLaud, 0,1);
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                minuLaud.paigaldaLaud(i,j);
            }
        }
        GridPane.setConstraints(vastaseLaud,1,1);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                vastaseLaud.paigaldaLaud(i,j);
            }
        }
        juur.add(minuLaud,0,1);
        juur.add(vastaseLaud,1,1);
        Label laevade_arv_Minu = new Label("Minu laevi: ");
        laevade_arv_Minu.setFont(Font.font(15));
        Label laevade_arv_Vastane = new Label("Vastase laevi: ");
        laevade_arv_Vastane.setFont(Font.font(15));
        Label minu_tekst = new Label("Minu laud");
        Label vastase_tekst = new Label("Vastase laud");

        Label tegevus = new Label("Vastane sai pihta! vms");
        tegevus.setTextFill(Color.RED);
        tegevus.setFont(Font.font("Verdana",20));

        GridPane skooritekstid = new GridPane();
        skooritekstid.add(laevade_arv_Minu,0,0);
        skooritekstid.add(laevade_arv_Vastane,0,1);
        skooritekstid.add(tegevus,0,2);


        juur.add(skooritekstid,2,1);

        minu_tekst.setFont(Font.font(15));
        vastase_tekst.setFont(Font.font(15));
        minu_tekst.setAlignment(Pos.CENTER);
        vastase_tekst.setAlignment(Pos.CENTER);
        juur.add(minu_tekst,0,0);
        juur.add(vastase_tekst,1,0);
        Scene stseen = new Scene(juur);
        primaryStage.setScene(stseen);
        primaryStage.setTitle("Laevade pommitamine");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
