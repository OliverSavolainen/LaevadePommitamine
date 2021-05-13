
package oop;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Graafika_Peaklass extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane juur = new GridPane();
        Pommitamine mängija = new Pommitamine("log1.txt");
        Pommitamine arvuti = new Pommitamine("log2.txt");
        List<String> ajalugu = new ArrayList<>();
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
                Teie laud luuakse randomi alusel (näete seda vasakul)
                ja siis saate hakata mängima arvuti vastu.
                Pommitamiseks vajutage vastase laua ruudul.
                Kui ruut läheb roheliseks, saite pihta,
                kui ei saanud, läheb see heledamaks.
                Iga eksimuse järel uuesti pommitades näete, 
                kuhu arvuti oma käigul vahepeal pommitas.
                Kui ruut teie laual läks mustaks, sai arvuti pihta, 
                kui ei, läks ruut punaseks.
                Kui lasete ise või arvuti laseb laeva põhja, 
                tuleb selle kohta sõnum
                ja kui mäng lõppeb, siis tuleb ka selle kohta sõnum.
                """);
        tutvustus.setFont(Font.font("Verdana", 15));
        GridPane skooritekstid = new GridPane();
        skooritekstid.add(laevade_arv_Minu, 0, 0);
        skooritekstid.add(vahe, 0, 1);
        skooritekstid.add(laevade_arv_Vastane, 0, 2);
        skooritekstid.add(vahe2, 0, 3);
        skooritekstid.add(tutvustus, 0, 5);
        Label silt = new Label("Sina võitsid");
        Label võit = new Label("Sina võitsid");
        Silt popupiSilt = new Silt(silt);
        Popup popup = new Popup();
        popup.getContent().add(võit);
        popup.setAutoHide(true);
        popup.centerOnScreen();
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


        primaryStage.setOnHiding(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                // luuakse teine lava
                Stage kusimus = new Stage();
                // küsimuse ja kahe nupu loomine
                Label label = new Label("Kas tõesti tahad kinni panna?");
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
                vBox.getChildren().addAll(label, pane);

                //stseeni loomine ja näitamine
                Scene stseen2 = new Scene(vBox);
                kusimus.setScene(stseen2);
                kusimus.show();
            }
        });

        vastaseLaud.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                try {
                    try {
                        if (mängija.kasLäbi()) {
                            silt.setText("Sina võitsid");
                            popup.show(primaryStage);
                            //juur.getChildren().add(popupiSilt.getSilt());
                            PrintWriter pw = new PrintWriter(arvuti.getFailiNimi());
                            pw.close();
                            PrintWriter pw2 = new PrintWriter(mängija.getFailiNimi());
                            pw2.close();
                            Thread.sleep(5000);
                            System.exit(0);
                        }
                    } catch (IOException | InterruptedException e) {
                        popupiSilt.muudaSõnumit("Erind!");
                        popup.show(primaryStage);
                    }
                    if (!vastaseLaud.isSelleKäik()) {
                        try {
                            arvuti.arvutiPommitamine(ajalugu);
                            for (int i = 0; i < 10; i++) {
                                for (int j = 0; j < 10; j++) {
                                    if (arvuti.getMänguLaud()[i][j].equals("O")) {
                                        minuLaud.getLaud()[i][j].setFill(Color.RED);
                                    }
                                    if (arvuti.getMänguLaud()[i][j].equals("X")) {
                                        minuLaud.getLaud()[i][j].setFill(Color.BLACK);
                                    }
                                }
                            }

                        } catch (IOException e) {
                            popupiSilt.muudaSõnumit("Erind!");
                            popup.show(primaryStage);
                        }
                        try {
                            if (arvuti.kasLäbi()) {
                                silt.setText("Kahjuks kaotasid");
                                popup.show(primaryStage);
                                PrintWriter pw = new PrintWriter(arvuti.getFailiNimi());
                                pw.close();
                                PrintWriter pw2 = new PrintWriter(mängija.getFailiNimi());
                                pw2.close();
                                Thread.sleep(5000);
                                System.exit(0);
                            }
                        } catch (IOException | InterruptedException e) {
                            popupiSilt.muudaSõnumit("Erind!");
                            popup.show(primaryStage);
                        } finally {
                            vastaseLaud.setSelleKäik(true);
                        }
                    }
                } catch (KoordinaadiErind e) {
                    silt.setText("Juba pommitasid seda");
                    popup.show(primaryStage);
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
