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
        int x = 0;
        int y = 0;
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
    public String[][] pommita(String[][] mänguLaud) {
        int x = 0;
        int y = 0;
        int[] xy = mängijaltKoordinaadid(mänguLaud);
        x = xy[0];
        y = xy[1];
        while (!mänguLaud[x][y].equals(" ") && !mänguLaud[x][y].equals("O") && !mänguLaud[x][y].equals("X")) {
            System.out.println("Said pihta!");
            String tähis = mänguLaud[x][y];
            mänguLaud[x][y] = "X";
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
            pommita(mänguLaud);
        } else {
            mänguLaud[x][y] = "O";
            System.out.println("Lasid mööda!");
            prindiLaud(mänguLaud);
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

    //Meetod mis leiab kas mingit laeva veel esineb lauas. Tagastab vastava tõeväärtuse.
    public boolean kasLeidub(String element, String[][] mänguLaud) {
        List<String> nimekiri = new ArrayList<String>();
        for (String[] rida : mänguLaud) {
            nimekiri.addAll(Arrays.asList(rida));
        }
        return nimekiri.contains(element);
    }

    /**
     * Kontrollib kasutades numbrit, mis tähitab laeva osa, kas seda laeva on veel osaliselt lauas, kui ei ole, siis
     * soutib sõnumi selle kohta
     */
    public boolean kasPõhjas(String[][] mänguLaud, String laevaOsa) {
        for (int i = 0; i < mänguLaud.length; i++) {
            for (int j = 0; j < mänguLaud.length; j++) {
                if (mänguLaud[i][j].equals(laevaOsa)) {
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
