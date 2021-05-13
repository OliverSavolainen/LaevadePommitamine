package oop;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

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

    public int getPikkus() {
        return pikkus;
    }

    public int getLaius() {
        return laius;
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
                        if (selleKäik) {
                            try {
                                selleKäik = pommitamine.pommita(finalX, finalY);
                                if (!selleKäik) laud[finalX][finalY].setFill(Color.rgb(191, 249, 255, 0.5));
                                else laud[finalX][finalY].setFill(Color.GREEN);

                            } catch (KoordinaadiErind | IOException e) {
                                Text tekst = new Text("Juba pommitasid seda");
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