package oop;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
        // Initializes new board
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
                laud[x][y].setStyle("-fx-border-style: solid; -fx-border-width: 5; -fx-border-color: black; -fx-min-width: 20; -fx-min-height:20; -fx-max-width:20; -fx-max-height: 20;");
                if (vastaseLaud) {
                    int finalX = x;
                    int finalY = y;
                    laud[x][y].setOnMouseClicked(event -> {
                        /*
                        Pane siia et kui pommitamisel tabab, muudab värvi vms
                         */
                        Label silt = new Label("Laev põhjas");
                        silt.setFont(Font.font("Verdana", 20));
                        Silt popupiSilt = new Silt(silt);
                        FlowPane pane = new FlowPane(10, 10);
                        VBox popup = new VBox(popupiSilt.getSilt(), pane);
                        popup.setAlignment(Pos.CENTER);
                        Scene stseen2 = new Scene(popup, 500, 100);
                        Stage abi = new Stage();
                        if (selleKäik) {
                            try {
                                String tulemus = pommitamine.pommita(finalX, finalY);
                                selleKäik = !tulemus.equals("0");
                                if (!selleKäik) laud[finalX][finalY].setFill(Color.rgb(191, 249, 255, 0.5));
                                else {
                                    laud[finalX][finalY].setFill(Color.GREEN);
                                    boolean põhjas = pommitamine.kasPõhjas(tulemus);
                                    if (põhjas) {
                                        popupiSilt.muudaSõnumit("Laev põhjas");
                                        abi.setScene(stseen2);
                                        abi.show();
                                        if (pommitamine.kasLäbi()) {
                                            popupiSilt.muudaSõnumit("Sina võitsid!!! Lahkumiseks vajuta suure akna punast X");
                                            abi.setScene(stseen2);
                                            abi.show();
                                        }
                                    }
                                }

                            } catch (KoordinaadiErind | IOException e) {
                                selleKäik = false;
                                popupiSilt.muudaSõnumit(e.getMessage());
                                abi.setScene(stseen2);
                                abi.show();

                            }
                        }

                    });

                } else {
                    if (!pommitamine.getMänguLaud()[x][y].equals(" "))
                        laud[x][y].setFill(Color.GRAY);
                }
            }
        }
    }


    public void paigaldaLaud(final int i, final int j) {
        getChildren().add(laud[i][j]);
    }

}