package oop;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Pommitamine extends Laud {
    private String[][] mänguLaud;
    /**
     * Isendiväi, mis määrab, mis nimega on mängija/arvuti fail, kus kirjas pihtasaamised
     */
    public Pommitamine(String failiNimi) {
        super(failiNimi);
        this.mänguLaud = laevadePaigutus(täidaLaud());
    }

    /*
      Meetod pommita võtab parameetriteks laua ja ruudu koordinaadid, millele mängija klikkis, kontrollib, mis on selle ruudu väärtuseks, märgib selle vastava tuelmusega ja tagastab selle, kui see pole
      varem pommitatud ruut ja viskab erindi, kui on
    */
    public boolean pommita(int x, int y) throws KoordinaadiErind, IOException {
        boolean tulemus = true;
        if (!mänguLaud[x][y].equals(" ") && !mänguLaud[x][y].equals("O") && !mänguLaud[x][y].equals("X")) {
            System.out.println("Said pihta!");
            String laev = mänguLaud[x][y];
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getFailiNimi(),true), StandardCharsets.UTF_8))) {
                bw.write(laev + System.lineSeparator());
            }
            mänguLaud[x][y] = "X";
        } else if (mänguLaud[x][y].equals("X") || mänguLaud[x][y].equals("O")) {
            throw new KoordinaadiErind("Juba pommitasid seda");
        } else {
            mänguLaud[x][y] = "O";
            tulemus = false;

        }
        return tulemus;
    }

    /**
     *
     * @param ajalugu käikude ajaloo List, kus Stringina x ja y-koordinaat ja käik/olukord, mida kasutatakse järgmiste koordinaatide leidmiseks
     * @throws IOException failist lugemise ja sinna kirjutamise tõttu
     * saab faili põhjal viimaste käikude andmed, ssab järgmineKoordinaat meetodist uued koordinaadid, pommitab sinna
     * Kui saab pihta, pommitab uuesti ja kirjutab faili pihtasaanud laeva numbri ning kontrollib, kas laev põhjas ja kui on, kontrollib, kas mäng läbi
     * Kui lõpuks eksib, siis lisab ajalukku tähise, mis näitab kas lihtsalt viimast käiku või viimast pihtasaamist, mille ümber pommitatakse
     */
    public String[][] arvutiPommitamine(List<String> ajalugu) throws IOException {
        int[] järgmiseAndmed = järgmineKoordinaat(ajalugu.get(ajalugu.size() - 1), ajalugu.get(ajalugu.size() - 2));
        int x = järgmiseAndmed[0];
        int y = järgmiseAndmed[1];
        int kord = järgmiseAndmed[2];
        if (!mänguLaud[x][y].equals(" ") && !mänguLaud[x][y].equals("X") && !mänguLaud[x][y].equals("O")) {
            String laev = mänguLaud[x][y];
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getFailiNimi(),true), StandardCharsets.UTF_8))) {
                bw.write(laev  + System.lineSeparator());
            }
            mänguLaud[x][y] = "X";
            if (kasPõhjas( laev)) {
                if (kasLäbi()) {
                    return mänguLaud;
                }
            } else if (kord == 0) {
                ajalugu.add(x + "," + y + "," + "1");
            } else {
                ajalugu.add(x + "," + y + "," + "5");
            }
            arvutiPommitamine(ajalugu);
        } else {
            mänguLaud[x][y] = "O";
            int valik = Integer.parseInt(ajalugu.get(ajalugu.size() - 1).split(",")[2]);
            switch (valik) {
                case 1:
                    ajalugu.add((x-1) + "," + y + "," + 2);
                    break;
                case 2:
                case 3:
                    for (int i = ajalugu.size() - 2; i > -1; i--) {
                        if (Integer.parseInt(ajalugu.get(i).split(",")[2]) == 1) {
                            ajalugu.add(ajalugu.get(i).substring(0, 4) + (kord + 1));
                            break;
                        }
                    }
                    break;
                default:
                    ajalugu.add(x + "," + y + "," + 0);
            }

        }
        return mänguLaud;
    }

    /**
     * meetod JärgmineKoordinaat võtab parameetriteks laua ja arvuti kaks viimast käiku, teeb käikude Stringid arvudeks
     * Siis switch arvu põhjal, mis tähistab olukorda
     * 1: viimati saadi pihta ja esimesena proovitakse alla minna
     * 2: 1 ei läinud pihta ja üles proovitakse
     * 3: paremale
     * 4: vasakule
     * 5: mitu korda on järjest pihta saadud ja jätkatakse samas suunas pihta saamisega
     * 0: viimane kord lasti randomilt või 4. case juures mööda (või oli 4.case ruut juba pommitatud) ja genereeritakse uued koordinaadid
     */
    public int[] järgmineKoordinaat(String eelmine, String üleelmine) {
        String[] osad = eelmine.split(",");
        int x = Integer.parseInt(osad[0]);
        int y = Integer.parseInt(osad[1]);
        int kord = Integer.parseInt(osad[2]);
        switch (kord) {
            case 1: {
                x = x + 1;
                if (x > 9 || mänguLaud[x][y].equals("X") || mänguLaud[x][y].equals("0")) {
                    x--;
                    kord++;
                } else break;
            }
            case 2:
                x--;
                if (x < 0 || mänguLaud[x][y].equals("X") || mänguLaud[x][y].equals("0")) {
                    x++;
                    kord++;
                } else break;
            case 3:
                y++;
                if (y > 9 || mänguLaud[x][y].equals("X") || mänguLaud[x][y].equals("0")) {
                    y--;
                    kord++;
                } else break;
            case 4:
                y--;
                if (y < 0 || mänguLaud[x][y].equals("X") || mänguLaud[x][y].equals("0")) {
                    y++;
                    kord = 0;
                } else break;
            case 5: {
                String[] osad2 = üleelmine.split(",");
                int x2 = Integer.parseInt(osad2[0]);
                int y2 = Integer.parseInt(osad2[1]);
                x += x - x2;
                y += y - y2;
                if (!(-1 < x && x < 10 && -1 < y && y < 10 && !(mänguLaud[x][y].equals("X") || mänguLaud[x][y].equals("O")))) {
                    kord = 0;
                } else break;
            }
            case 0: {
                x = (int) Math.round(Math.random() * 9);
                y = (int) Math.round(Math.random() * 9);
                while (!(-1 < x && x < 10 && -1 < y && y < 10 && !(mänguLaud[x][y].equals("X") || mänguLaud[x][y].equals("O")))) {
                    x = (int) Math.round(Math.random() * 9);
                    y = (int) Math.round(Math.random() * 9);
                }
                break;
            }
        }
        //vb võiks case seoses laeva mittepõhja mineku aga 0 tulemisega teha
        //lisaks vaadata olukordi, kus laevad koos + olukorrad, kus osa hittitud, aga see on kahe mitte hittitud osa vahel
        return new int[]{x, y, kord};
    }




    /**
     * Kontrollib laua/mängija logifailist, kas pihtasaadud laeva numbrile on vastavalt see arv kordi pihta saadud ehk kas laev põhjas
     */
    public boolean kasPõhjas(String laevaOsa) throws IOException {
        int laevuPommitatud = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(getFailiNimi()), StandardCharsets.UTF_8))) {
            String rida;
            while ((rida = br.readLine()) != null) {
                if (rida.strip().equals(laevaOsa)) laevuPommitatud++;
            }
        }
        return laevuPommitatud == Integer.parseInt(laevaOsa);
    }

    public String[][] getMänguLaud() {
        return mänguLaud;
    }

    /**
     * Kontrollib, kas failis 14 rida, sest täpselt nii palju on laevade osi/ruute laual
     */
    public boolean kasLäbi() throws IOException {
        int laevuPommitatud = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(getFailiNimi()), StandardCharsets.UTF_8))) {
            while (br.readLine() != null) {
                laevuPommitatud++;
            }
        }
        return laevuPommitatud >= 14;
    }
}
