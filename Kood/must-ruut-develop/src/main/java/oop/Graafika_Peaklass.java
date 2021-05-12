
package oop;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Graafika_Peaklass extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene stseen = new Scene(mäng());
        primaryStage.setScene(stseen);
        primaryStage.setTitle("Laevade pommitamine");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private GridPane mäng() throws IOException {
        GridPane juur = new GridPane();
        Ruudustik minuLaud = new Ruudustik(10, 10, false);
        Ruudustik vastaseLaud = new Ruudustik(10, 10, true);
        //Vahed ekraani äärte vahel
        juur.setPadding(new Insets(10, 10, 10, 10));
        //Vahed elementide vahel
        juur.setVgap(8);
        juur.setHgap(10);
        GridPane.setConstraints(minuLaud, 0, 1);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                minuLaud.paigaldaLaud(i, j);
            }
        }
        GridPane.setConstraints(vastaseLaud, 1, 1);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                vastaseLaud.paigaldaLaud(i, j);
            }
        }
        juur.add(minuLaud, 0, 1);
        juur.add(vastaseLaud, 1, 1);

        Label laevade_arv_Minu = new Label("Minu laevi: ");
        laevade_arv_Minu.setFont(Font.font(25));
        Label vahe = new Label("--------------------------------------");
        vahe.setFont(Font.font(25));
        Label laevade_arv_Vastane = new Label("Vastase laevi: ");
        laevade_arv_Vastane.setFont(Font.font(25));
        Label vahe2 = new Label("--------------------------------------");
        vahe2.setFont(Font.font(25));
        Label minu_tekst = new Label("Minu laud");
        Label vastase_tekst = new Label("Vastase laud");
        Label tutvustus = new Label("""
                Teie laud luuakse randomi alusel ja siis saate hakata mängima arvuti vastu.
                Pommitamiseks vajutage vastase laua ruudul.
                Saate pommitada kuni eksite ja siis tuleb arvuti kord.
                Näete, kuhu arvuti pommitas iga tema korra järel.
                Samuti näete lauda, kus kõik teie pommitamised kirjas.
                Kui lasete ise või arvuti laseb laeva põhja, tuleb selle kohta sõnum
                ja kui mäng lõppeb, siis tuleb ka selle kohta sõnum.
                """);
        Label tegevus = new Label("Vastane sai pihta!");
        tegevus.setTextFill(Color.RED);
        tegevus.setFont(Font.font("Verdana", 20));
        Silt tegevusesilt = new Silt(tegevus);
        GridPane skooritekstid = new GridPane();
        skooritekstid.add(laevade_arv_Minu, 0, 0);
        skooritekstid.add(vahe, 0, 1);
        skooritekstid.add(laevade_arv_Vastane, 0, 2);
        skooritekstid.add(vahe2, 0, 3);
        skooritekstid.add(tegevusesilt.getSilt(),0,4);
        skooritekstid.add(tutvustus, 0, 5);
        tutvustus.setAlignment(Pos.BOTTOM_CENTER);
        juur.add(skooritekstid, 2, 1);
        minu_tekst.setFont(Font.font(15));
        vastase_tekst.setFont(Font.font(15));
        minu_tekst.setAlignment(Pos.CENTER);
        vastase_tekst.setAlignment(Pos.CENTER);
        vahe.setAlignment(Pos.CENTER);
        vahe.setAlignment(Pos.CENTER);
        juur.add(minu_tekst, 0, 0);
        juur.add(vastase_tekst, 1, 0);
        Pommitamine mängija = new Pommitamine("log1.txt");
        String[][] mängijaLaud = mängija.täidaLaud();
        mängija.laevadePaigutus(mängijaLaud);
        Pommitamine arvuti = new Pommitamine("log2.txt");
        String[][] arvutiLaud = arvuti.täidaLaud();
        arvuti.laevadePaigutus(arvutiLaud);
        List<String> ajalugu = new ArrayList<>();
        ajalugu.add("0,0,0");
        ajalugu.add("0,0,0");
        while (!mängija.kasLäbi(mängijaLaud)) {
            tegevus.setText("Sinu käik");
            AtomicReference<String> viimane = new AtomicReference<>("");
            while (!viimane.get().equals("0")) {
                vastaseLaud.addEventHandler(MouseEvent.MOUSE_PRESSED, me -> {
                    int x = (int) me.getSceneX();
                    int y = (int) me.getSceneY();
                    try {
                        viimane.set(mängija.pommita(arvutiLaud, x, y));

                    } catch (IOException | KoordinaadiErind e) {
                        tegevus.setText("Juba pommitasid seda");
                    }
                });
                if (mängija.kasLäbi(arvutiLaud)) {
                    tegevus.setText("Mäng läbi, sina võitsid!");
                    vastaseLaud.setVisible(false);
                    System.exit(0);

                }
            }
            tegevus.setText("Arvuti käik");

            arvuti.arvutiPommitamine(mängijaLaud, ajalugu);
        }
        return juur;
    }


    public static void main(String[] args)  {
        launch(args);
    }
}
