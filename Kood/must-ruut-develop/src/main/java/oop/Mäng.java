package oop;

import java.util.ArrayList;

public class Mäng extends Pommitamine{
    /**
     * Loome lauad arvutile ja mängijale ning paigutame laevad ning soutime mängijale temale tekkinud laua
     * Siis on tsükkel, mis vaatab, kas arvuti on võitnud esialgu, siis laseb mängijal pommitada, kontrollib, kas mängija võitis
     * ning kui pole, siis annab arvutile korra pommitada
     * mängijaNäeb laud näitab, kuhu mängija on pommitanud
     **/
    /*
    public void mäng() {
        String[][] arvutiLaud = täidaLaud();
        String[][] mängijaLaud = täidaLaud();
        String[][] mängijaNäeb = täidaLaud();
        ArrayList<String> arvutiAjalugu = new ArrayList<>();
        arvutiAjalugu.add("0,0,0");
        arvutiAjalugu.add("0,0,0");
        laevadePaigutus(arvutiLaud);
        laevadePaigutus(mängijaLaud);
        System.out.println("Teie laud:");
        prindiLaud(mängijaLaud);
        while (!kasLäbi(mängijaLaud)){
            if (kasLäbi(arvutiLaud)) {
                break;
            }
            arvutiPommitamine(mängijaLaud,arvutiAjalugu);
        }
    }
    */
    public Mäng(String mängijaNimi) {
        super(mängijaNimi);
    }
}