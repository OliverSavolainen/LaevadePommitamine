import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Pommitamine extends Laud {

    public Pommitamine(String mängijaNimi) {
        super(mängijaNimi);
    }

    /*
     Siin meetodis saab kasutaja ise valida koordinaate, mida pommitada. See on implementeeritud do/while tsükliga.
     */
    public int[] mängijaltKoordinaadid(String[][] mänguLaud) {
        int x = -1;
        int y = -1;
        do {
            System.out.print("Rida: ");
            Scanner skänner = new Scanner(System.in);
            // Erindite püüdmiseks
            if (skänner.hasNextInt())
                x = skänner.nextInt();
        } while (x < 0 || x > mänguLaud.length - 1);
        do {
            System.out.print("Veerg: ");
            Scanner sk2nner = new Scanner(System.in);
            if (sk2nner.hasNextInt())
                y = sk2nner.nextInt();
        } while (y < 0 || y > mänguLaud.length - 1);
        return new int[]{x, y};
    }

    /*
    Meetod pommita võtab sisendiks mängulaua ja tagastab selle peale pommitamist. Kasutab mängijaltKoordinaadid meetodit
     */
    public String[][] pommita(String[][] mänguLaud, String[][] nähtavLaud) {
        int x = 0;
        int y = 0;
        int[] xy = mängijaltKoordinaadid(mänguLaud);
        x = xy[0];
        y = xy[1];
        while (!mänguLaud[x][y].equals(" ") && !mänguLaud[x][y].equals("O") && !mänguLaud[x][y].equals("X")) {
            System.out.println("Said pihta!");
            String tähis = mänguLaud[x][y];
            mänguLaud[x][y] = "X";
            nähtavLaud[x][y] = "X";
            prindiLaud(nähtavLaud);
            if (kasPõhjas(mänguLaud, tähis)) {
                System.out.println("Laev põhjas!");
                if (kasLäbi(mänguLaud)) {
                    System.out.println("Mäng läbi. Sina võitsid!");
                    return mänguLaud;
                }
            }
            xy = mängijaltKoordinaadid(mänguLaud);
            x = xy[0];
            y = xy[1];
        }
        if (mänguLaud[x][y].equals("X") || mänguLaud[x][y].equals("O")) {
            System.out.println("Oled seda ruutu juba pommitanud! Vali uuesti.");
            pommita(mänguLaud, nähtavLaud);
        } else {
            mänguLaud[x][y] = "O";
            nähtavLaud[x][y] = "O";
            System.out.println("Lasid mööda!");
            prindiLaud(nähtavLaud);
        }
        return mänguLaud;
    }

    /**
     * Meetod, mida kasutatakse arvuti pommitamisel kui arvuti tabab
     * Kontrollib, kas laev on põhjas ja kui on, siis kontrollib kas mäng läbi
     */
    public String[][] arvutiSaiPihta(String[][] mänguLaud, int x, int y) {
        System.out.println("Arvuti sai su laevale pihta!");
        String tähis = mänguLaud[x][y];
        mänguLaud[x][y] = "X";
        prindiLaud(mänguLaud);
        System.out.println("---------------------------");
        System.out.println("---------------------------");
        if (kasPõhjas(mänguLaud, tähis)) {
            System.out.println("Laev põhjas!");
            if (kasLäbi(mänguLaud)) {
                System.out.println("Mäng läbi. Arvuti võitis");
                return mänguLaud;
            }
        }
        return mänguLaud;
    }

    /*
    Meetod arvuti_pommita võtab sisendiks mängulaua ja tagastab selle peale pommitamist.
    Pommitatavad koordinaadid genereeritakse suvaliselt, plaan ilmselt 2.rühmatöös teha see intelligentseks
     */
    public String[][] arvuti_pommita(String[][] mänguLaud) {
        int x = (int) (Math.random() * (9 + 1));
        int y = (int) (Math.random() * (9 + 1));
        while (!mänguLaud[x][y].equals(" ") && !mänguLaud[x][y].equals("O") && !mänguLaud[x][y].equals("X")) {
            arvutiSaiPihta(mänguLaud, x, y);
            x = (int) (Math.random() * (9 + 1));
            y = (int) (Math.random() * (9 + 1));
        }
        // Kui genereeritakse juba pommitatud koordinaadid, siis kutsutakse meetod uuesti välja.
        if (mänguLaud[x][y].equals("X") || mänguLaud[x][y].equals("O")) {
            arvuti_pommita(mänguLaud);
        } else {
            mänguLaud[x][y] = "O";
            System.out.println("Arvuti lasi mööda!");
            prindiLaud(mänguLaud);

        }

        return mänguLaud;
    }

    public int[] järgmineKoordinaat(String eelmine, String[][] mänguLaud, String üleelmine) {
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
                    x--;
                    kord = 0;
                } else break;
            case 5: {
                String[] osad2 = üleelmine.split(",");
                int x2 = Integer.parseInt(osad2[0]);
                int y2 = Integer.parseInt(osad2[1]);
                x += x - x2;
                y += y - y2;
                if (!(-1 < x && x < 10 && -1 < y && y < 10 && !(mänguLaud[x][y].equals("X") || mänguLaud[x][y].equals("O")))){
                    kord = 0;
                }
                else break;
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

    public String[][] arvutiPommitamine(String[][] mänguLaud, ArrayList<String> ajalugu) {
        int[] järgmiseAndmed = järgmineKoordinaat(ajalugu.get(ajalugu.size() - 1), mänguLaud, ajalugu.get(ajalugu.size() - 2));
        int x = järgmiseAndmed[0];
        int y = järgmiseAndmed[1];
        int kord = järgmiseAndmed[2];
        if (!mänguLaud[x][y].equals(" ")) {
            System.out.println("Arvuti sai pihta");
            String laev = mänguLaud[x][y];
            mänguLaud[x][y] = "X";
            if (kasPõhjas(mänguLaud, laev)) {
                ajalugu.add(x + "," + y + "," + "0");
                System.out.println("Laev põhjas!");
                if (kasLäbi(mänguLaud)) {
                    prindiLaud(mänguLaud);
                    System.out.println("Mäng läbi. Arvuti võitis");
                    return mänguLaud;
                }
            } else if (kord == 0) {
                ajalugu.add(x + "," + y + "," + "1");
                System.out.println(ajalugu);
            } else {
                ajalugu.add(x + "," + y + "," + "5");
            }
            prindiLaud(mänguLaud);
            arvutiPommitamine(mänguLaud, ajalugu);
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
            System.out.println("Arvuti lasi mööda!");
            prindiLaud(mänguLaud);
            System.out.println(ajalugu);
        }
        return mänguLaud;
    }


    /**
     * Kontrollib kasutades numbrit, mis tähistab laeva osa, kas seda laeva on veel osaliselt lauas, kui ei ole, siis
     * soutib sõnumi selle kohta
     */
    public boolean kasPõhjas(String[][] mänguLaud, String laevaOsa) {
        for (String[] read : mänguLaud) {
            for (int j = 0; j < mänguLaud.length; j++) {
                if (read[j].equals(laevaOsa)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Kontrollib, kas laual on alles välju, mis tähistavad laeva
     * Kui pole, mäng lõppeb
     */
    public boolean kasLäbi(String[][] mänguLaud) {
        for (String[] read : mänguLaud) {
            for (int j = 0; j < mänguLaud.length; j++) {
                if (read[j].equals("5") || read[j].equals("4") || read[j].equals("3") || read[j].equals("2")) {
                    return false;
                }
            }
        }
        return true;
    }
}
