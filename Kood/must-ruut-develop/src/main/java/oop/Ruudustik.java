package oop;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

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
                laud[x][y].setWidth(50);
                laud[x][y].setHeight(50);
                laud[x][y].setX(x * 50);
                laud[x][y].setY(y * 50);
                laud[x][y].setStroke(Color.TRANSPARENT);
                laud[x][y].setStrokeType(StrokeType.INSIDE);
                laud[x][y].setStrokeWidth(1);
                laud[x][y].setFill(Color.LIGHTBLUE);
                if (vastaseLaud) {
                    int finalX = x;
                    int finalY = y;
                    laud[x][y].setOnMouseClicked(event -> {
                        if (selleKäik) {
                        /*
                        Pane siia et kui pommitamisel tabab, muudab värvi vms
                         */
                            laud[finalX][finalY].setFill(Color.rgb(191, 249, 255, 0.5));
                            try {
                                selleKäik = pommitamine.pommita(finalX, finalY);

                            } catch (KoordinaadiErind | IOException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                    });

                } else {
                    if (!pommitamine.getMänguLaud()[x][y].equals(" ")) laud[x][y].setFill(Color.rgb(191, 249, 255, 0.5));
                }
            }
        }
    }


    public void paigaldaLaud(final int i, final int j) {
        getChildren().add(laud[i][j]);
    }

}