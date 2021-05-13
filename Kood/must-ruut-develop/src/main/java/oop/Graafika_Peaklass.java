
package oop;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Graafika_Peaklass extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // abilava, mida kasutatakse VBoxi loomisel
        Stage abi = new Stage();
        GridPane juur = new GridPane();
        //Pommitamise isendid Ruudustiku isendite loomiseks
        Pommitamine mängija = new Pommitamine("log1.txt");
        Pommitamine arvuti = new Pommitamine("log2.txt");
        // List, mida arvutiPommitamises kasutada
        List<String> ajalugu = new ArrayList<>();
        // Vaja, et list oleks vähemalt 2se sizega, seega lisan sinna 2 elementi
        ajalugu.add("0,0,0");
        ajalugu.add("0,0,0");
        Ruudustik minuLaud = new Ruudustik(10, 10, false, arvuti);
        Ruudustik vastaseLaud = new Ruudustik(10, 10, true, mängija);
        //Vahed ekraani äärte vahel
        juur.setPadding(new Insets(10, 10, 10, 10));
        //Vahed elementide vahel
        juur.setVgap(8);
        juur.setHgap(10);
        GridPane.setConstraints(minuLaud, 0, 1);
        //Laudade loomine
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
        Label minu_tekst = new Label("Minu laud");
        Label vastase_tekst = new Label("Vastase laud");
        minu_tekst.setAlignment(Pos.CENTER);
        vastase_tekst.setAlignment(Pos.CENTER);
        juur.add(minu_tekst, 0, 0);
        juur.add(vastase_tekst, 1, 0);
        Label pealkiri = new Label("Laevade pommitamine");
        Label tutvustus = new Label( """ 
                                
                Teie laud luuakse suvaliselt paigutatuna (näete seda vasakul)
                ja siis saate hakata mängima arvuti vastu.
                Pommitamiseks vajutage vastase laua ruudul.
                Kui ruut läheb mustaks, saite pihta,
                kui ei saanud, läheb see heledamaks.
                Olge ettevaatlikud, eelnevalt juba pommitatud ruudu
                uuesti pommitamisel annate käigu vastasele!
                Iga eksimuse järel uuesti pommitades näete,
                kuhu arvuti oma käigul vahepeal pommitas.
                Kui ruut teie laual läks mustaks, sai arvuti pihta,
                kui ei, läks ruut heledamaks.
                Kui lasete ise või arvuti laseb laeva põhja,
                tuleb selle kohta sõnum
                ja kui mäng lõppeb, siis tuleb ka selle kohta sõnum.
                Meeldivat mängimist!
                """);
        pealkiri.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 35));
        tutvustus.setFont(Font.font("Comic Sans MS", 15));
        GridPane skooritekstid = new GridPane();
        skooritekstid.add(pealkiri, 0, 0);
        skooritekstid.add(tutvustus, 0, 2);
        Label silt = new Label("Sina võitsid");
        silt.setFont(Font.font("Comic Sans MS", 20));
        // Sildi isend, mille sõne saab muuta
        Silt popupiSilt = new Silt(silt);
        FlowPane pane = new FlowPane(10, 10);
        // VBox, mida vaja kasutada osades olukordades, selle näitamiseks loome ka stseeni ja lava
        VBox popup = new VBox(popupiSilt.getSilt(), pane);
        popup.setAlignment(Pos.CENTER);
        Scene stseen2 = new Scene(popup, 500, 100);

        tutvustus.setAlignment(Pos.BOTTOM_CENTER);
        juur.add(skooritekstid, 2, 1);


        primaryStage.setOnHiding(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                // väljudes kustutatakse andmed failidest, mis mängu olukorda kontrollivad
                PrintWriter pw = null;
                try {
                    pw = new PrintWriter(arvuti.getFailiNimi());
                } catch (FileNotFoundException e) {
                    popupiSilt.muudaSõnumit("Failierind!");
                    abi.setScene(stseen2);
                    abi.show();
                }
                assert pw != null;
                pw.close();
                PrintWriter pw2 = null;
                try {
                    pw2 = new PrintWriter(mängija.getFailiNimi());
                } catch (FileNotFoundException e) {
                    popupiSilt.muudaSõnumit("Failierind!");
                    abi.setScene(stseen2);
                    abi.show();
                }
                pw2.close();
                // luuakse teine lava
                Stage kusimus = new Stage();
                // küsimuse ja kahe nupu loomine
                Label label = new Label("Kas tõesti tahad kinni panna?");
                Label label2 = new Label("Kui mäng sai läbi, on Ei vajutades sul võimalik edasi pommitada lihtsalt lõbuks");
                Button okButton = new Button("Jah");
                Button cancelButton = new Button("Ei");

                // sündmuse lisamine nupule Jah
                okButton.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        kusimus.hide();
                    }
                });

                // sündmuse lisamine nupule Ei
                cancelButton.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        primaryStage.show();
                        kusimus.hide();
                    }
                });

                // nuppude grupeerimine
                FlowPane pane = new FlowPane(10, 10);
                pane.setAlignment(Pos.CENTER);
                pane.getChildren().addAll(okButton, cancelButton);

                // küsimuse ja nuppude gruppi paigutamine
                VBox vBox = new VBox(10);
                vBox.setAlignment(Pos.CENTER);
                vBox.getChildren().addAll(label, pane, label2);

                //stseeni loomine ja näitamine
                Scene stseen2 = new Scene(vBox);
                kusimus.setScene(stseen2);
                kusimus.show();
            }
        });

        /*
        EventHandler selle kohta, mis juhtub, kui mängija vajutab ruudule laual. Kontrollitakse, kas mäng on läbi, kui ei ole, siis toimub arvuti pommitamine ja pommitatud ruutude värvimine
        Kõik meetodid, mida kasutatakse, võivad visata erindeid, seega kasutatud try and catchi, kus catchis oleks erind näha tekstiväljana

         */
        vastaseLaud.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                    try {
                        // Kui !vastaseLaud.isSelleKäik(), siis mängija on vajutanud ja eksinud viimati
                        if (!vastaseLaud.isSelleKäik()) {
                            try {
                                // kutsutakse välja arvutiPommitamine ja värvitakse käigul pommitatud ruudud
                                arvuti.arvutiPommitamine(ajalugu);
                                for (int i = 0; i < 10; i++) {
                                    for (int j = 0; j < 10; j++) {
                                        if (arvuti.getMänguLaud()[i][j].equals("O")) {
                                            minuLaud.getLaud()[i][j].setFill(Color.rgb(191, 249, 255, 0.5));
                                        }
                                        if (arvuti.getMänguLaud()[i][j].equals("X")) {
                                            minuLaud.getLaud()[i][j].setFill(Color.BLACK);
                                        }
                                    }
                                }

                            } catch (IOException e) {
                                popupiSilt.muudaSõnumit("Erind!");
                                abi.setScene(stseen2);
                                abi.show();
                            }
                            // kui arvuti võidab mängu, tuleb VBoxiga vastav sõnum
                            try {
                                if (arvuti.kasLäbi()) {
                                    popupiSilt.muudaSõnumit("""
                                            Kahjuks kaotasid.
                                            Lahkumiseks vajuta suure akna punast X""");
                                    abi.setScene(stseen2);
                                    abi.show();
                                }
                            } catch (IOException e) {
                                popupiSilt.muudaSõnumit("Erind!");
                                abi.setScene(stseen2);
                                abi.show();
                            } finally {
                                // Kui arvuti käik tehtud, saab mängija käigu tagasi
                                vastaseLaud.setSelleKäik(true);
                            }
                        }
                    } catch (KoordinaadiErind e) {
                        popupiSilt.muudaSõnumit(e.getMessage());
                        abi.setScene(stseen2);
                        abi.show();
                    }
                }
        });
        Scene stseen = new Scene(juur);
        primaryStage.setScene(stseen);
        primaryStage.setTitle("Laevade pommitamine");
        primaryStage.setResizable(true);
        primaryStage.sizeToScene();
        primaryStage.show();
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMinHeight(primaryStage.getHeight());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
