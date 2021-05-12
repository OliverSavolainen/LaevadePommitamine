package oop;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class Ruudustik extends Pane {

    private int pikkus;
    private int laius;
    private boolean vastaseLaud;
    private Rectangle[][] laud;

    public int getPikkus() {
        return pikkus;
    }

    public int getLaius() {
        return laius;
    }

    //Konstruktor
    public Ruudustik(int pikkus, int laius, boolean vastaseLaud) {
        this.pikkus = pikkus;
        this.laius = laius;
        this.vastaseLaud = vastaseLaud;
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
                        /*
                        Pane siia et kui pommitamisel tabab, muudab värvi vms
                         */
                            laud[finalX][finalY].setFill(Color.rgb(191, 249, 255, 0.5));
                    });

                }
            }
        }
    }


    public void paigaldaLaud(final int i, final int j) {
        getChildren().add(laud[i][j]);
    }

}