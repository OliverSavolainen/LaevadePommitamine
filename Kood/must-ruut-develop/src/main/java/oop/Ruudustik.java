package oop;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Ruudustik extends Pane {

    private int pikkus;
    private int laius;
    private boolean vastaseLaud;
    private Rectangle[][] laud;
    private Pommitamine pommitamine;
    private boolean selleKäik = true;

    public boolean isSelleKäik() {
        return selleKäik;
    }

    public Rectangle[][] getLaud() {
        return laud;
    }

    public void setSelleKäik(boolean selleKäik) {
        this.selleKäik = selleKäik;
    }

    //Konstruktor
    public Ruudustik(int pikkus, int laius, boolean vastaseLaud, Pommitamine pommitamine) {
        this.pikkus = pikkus;
        this.laius = laius;
        this.vastaseLaud = vastaseLaud;
        this.pommitamine = pommitamine;
        laud = new Rectangle[laius][pikkus];
        // Tekitab laua
        for (int x = 0; x < laius; x++) {
            for (int y = 0; y < pikkus; y++) {
                laud[x][y] = new Rectangle();
                laud[x][y].setWidth(40);
                laud[x][y].setHeight(40);
                laud[x][y].setX(x * 40);
                laud[x][y].setY(y * 40);
                laud[x][y].setStroke(Color.TRANSPARENT);
                laud[x][y].setStrokeType(StrokeType.INSIDE);
                laud[x][y].setStrokeWidth(1);
                laud[x][y].setFill(Color.LIGHTBLUE);
                if (vastaseLaud) {
                    int finalX = x;
                    int finalY = y;
                    laud[x][y].setOnMouseClicked(event -> {
                        /*
                       Siin seadistatakse sündmused, mis juhtuvad kui pommitada vastase lauda.
                         */
                        Label silt = new Label("Laev põhjas");
                        silt.setFont(Font.font("Comic Sans MS", 30));
                        silt.setTextFill(Color.RED);
                        Silt popupiSilt = new Silt(silt);
                        FlowPane pane = new FlowPane(10, 10);
                        VBox popup = new VBox(popupiSilt.getSilt(), pane);
                        popup.setAlignment(Pos.CENTER);
                        Scene stseen = new Scene(popup, 200, 100);
                        Stage abi = new Stage();
                        if (selleKäik) {
                            try {
                                String tulemus = pommitamine.pommita(finalX, finalY);
                                selleKäik = !tulemus.equals("0");
                                if (!selleKäik) laud[finalX][finalY].setFill(Color.rgb(191, 249, 255, 0.5));
                                else {
                                    laud[finalX][finalY].setFill(Color.BLACK);
                                    boolean põhjas = pommitamine.kasPõhjas(tulemus);
                                    if (põhjas) {
                                        abi.setScene(stseen);
                                        abi.show();
                                        if (pommitamine.kasLäbi()) {
                                            Label silt2 = new Label("""
                                            SINA VÕITSID! 
                                            Lahkumiseks vajuta suure akna punast X
                                            """);
                                            silt.setFont(Font.font("Comic Sans MS", 20));
                                            silt.setTextFill(Color.RED);
                                            Silt popupiSilt2 = new Silt(silt2);
                                            FlowPane pane2 = new FlowPane(10, 10);
                                            VBox popup2 = new VBox(popupiSilt2.getSilt(), pane2);
                                            popup2.setAlignment(Pos.CENTER);
                                            Scene stseen2 = new Scene(popup2, 500, 100);
                                            Stage abi2 = new Stage();
                                            abi2.setScene(stseen2);
                                            abi2.show();
                                        }
                                    }
                                }


                            }
                            /*
                            Eelnevalt pommitatud ruudu erindi püüdmine
                             */
                            catch (KoordinaadiErind | IOException e) {
                                selleKäik = false;
                                popupiSilt.muudaSõnumit(e.getMessage());
                                abi.setScene(stseen);
                                abi.show();

                            }
                        }

                    });

                }
                /*
                Mängija laevad muudetakse halliks
                 */
                else {
                    if (!pommitamine.getMänguLaud()[x][y].equals(" "))
                        laud[x][y].setFill(Color.GRAY);
                }
            }
        }
    }

    // Siin meetodis paigaldatakse eraldi ruudud juurde
    public void paigaldaLaud(final int i, final int j) {
        getChildren().add(laud[i][j]);
    }

}