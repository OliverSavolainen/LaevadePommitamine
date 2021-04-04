import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Laud {

    private String mängijaNimi;

    public Laud(String mängijaNimi) {
        this.mängijaNimi = mängijaNimi;
    }
    static void pront(String[][] m){
        System.out.println(Arrays.stream(m).map(Arrays::toString).collect(Collectors.joining("\n")));
    }

    public String[][] täidaLaud() {
        String[][] mänguLaud = new String[10][10];
        for (int i = 0; i < mänguLaud.length; i++) {
            for (int j = 0; j < mänguLaud.length; j++) {
                mänguLaud[i][j] = " ";
            }

        }
        return mänguLaud;
    }

    public String[][] laevadePaigutus(String[][] mänguLaud) {
        String[] tähistused = {"5", "4", "3", "2"};

        for (int i = 5; i > 1; i--) {
            int vert_koordinaat = (int) Math.round(Math.random() * 9);
            int hori_koordinaat = (int) Math.round(Math.random() * 9);
            int suund = (int) Math.round(Math.random() * 3) + 1;
            while (!paigutusVõimalik(vert_koordinaat, hori_koordinaat, suund, i, mänguLaud)) {
                vert_koordinaat = (int) Math.round(Math.random() * 9);
                hori_koordinaat = (int) Math.round(Math.random() * 9);
                suund = (int) Math.round(Math.random() * 3) + 1;
            }
            String vastavTähistus = tähistused[tähistused.length + 1 - i];
            mänguLaud[vert_koordinaat][hori_koordinaat] = vastavTähistus;
            switch (suund) {
                case 1 -> {
                    for (int j = 1; j < i; j++) {
                        mänguLaud[vert_koordinaat + j][hori_koordinaat] = vastavTähistus;
                    }
                }
                case 2 -> {
                    for (int j = 1; j < i; j++) {
                        mänguLaud[vert_koordinaat - j][hori_koordinaat] = vastavTähistus;
                    }
                }
                case 3 -> {
                    for (int j = 1; j < i; j++) {
                        mänguLaud[vert_koordinaat][hori_koordinaat + j] = vastavTähistus;
                    }
                }
                case 4 -> {
                    for (int j = 1; j < i; j++) {
                        mänguLaud[vert_koordinaat][hori_koordinaat - j] = vastavTähistus;
                    }
                }

            }


        }
        return mänguLaud;
    }

    public boolean paigutusVõimalik(int x, int y, int suund, int pikkus, String[][] mänguLaud) {
        switch (suund) {
            case 1 -> {
                if (x + pikkus > 9) {
                    return false;
                }
                for (int j = 0; j < pikkus; j++) {
                    if (!mänguLaud[x + j][y].equals(" "))
                        return false;
                }
            }
            case 2 -> {
                if (x - pikkus < 0) {
                    return false;
                }
                for (int j = 0; j < pikkus; j++) {
                    if (!mänguLaud[x - j][y].equals(" "))
                        return false;
                }
            }
            case 3 -> {
                if (y + pikkus > 9) {
                    return false;
                }
                for (int j = 0; j < pikkus; j++) {
                    if (!mänguLaud[x][y + j].equals(" "))
                        return false;
                }
            }
            case 4 -> {
                if (y - pikkus < 0) {
                    return false;
                }
                for (int j = 0; j < pikkus; j++) {
                    if (!mänguLaud[x][y - j].equals(" "))
                        return false;
                }
            }

        }
        return true;
    }
    public String[][] pommita(String[][] mänguLaud){
        int x;
        int y;
        do{
            System.out.println("Rida: ");
            x = new Scanner(System.in).nextInt();
        } while (x < 1 || x > mänguLaud.length);
        do{
            System.out.println("Veerg: ");
            y = new Scanner(System.in).nextInt();
        } while (y < 1 || y > mänguLaud.length);
        if(!mänguLaud[x][y].equals(" ")){
            System.out.println("Said pihta!");
            String tähis = mänguLaud[x][y];
            mänguLaud[x][y] = "X";
            if (kasPõhjas(mänguLaud, tähis)){
                System.out.println("Laev põhjas!");
                if (kasLäbi(mänguLaud)){
                    System.out.println("Mäng läbi. Sina võitsid!");
                    return null;
                }
            }

            pommita(mänguLaud);
        }
        else if(mänguLaud[x][y].equals("X")||mänguLaud[x][y].equals("O")){
            System.out.println("Oled seda ruutu juba pommitanud! Vali uuesti.");
            pommita(mänguLaud);
        }
        else {
            mänguLaud[x][y] = "O";
            System.out.println("Lasid mööda!");
        }
        pront(mänguLaud);
        return mänguLaud;
    }
    public String[][] arvutiSaiPihta(String[][] mänguLaud,int x,int y){
        System.out.println("Arvuti sai su laevale pihta!");
        String tähis = mänguLaud[x][y];
        mänguLaud[x][y] = "X";
        pront(mänguLaud);
        if (kasPõhjas(mänguLaud, tähis)){
            System.out.println("Laev põhjas!");
            if (kasLäbi(mänguLaud)){
                System.out.println("Mäng läbi. Arvuti võitis");
                return null;
            }
        }
        mänguLaud[x][y] = "X";
        return mänguLaud;
    }
    public String[][] arvuti_pommita (String[][] mänguLaud){
        int x = (int)(Math.random() * (9 + 1));
        int y  = (int)(Math.random() * (9 + 1));
        if(!mänguLaud[x][y].equals(" ") && !mänguLaud[x][y].equals("O") && !mänguLaud[x][y].equals("X")){
            arvutiSaiPihta(mänguLaud,x,y);
            x = (int)(Math.random() * (9 + 1));
            y  = (int)(Math.random() * (9 + 1));
            while (!mänguLaud[x][y].equals(" ") && !mänguLaud[x][y].equals("O") && !mänguLaud[x][y].equals("X")){
                arvutiSaiPihta(mänguLaud,x,y);
                x = (int)(Math.random() * (9 + 1));
                y  = (int)(Math.random() * (9 + 1));
            }
        }
        else if(mänguLaud[x][y].equals("X")||mänguLaud[x][y].equals("O")){
            arvuti_pommita(mänguLaud);
        }
        else {
            mänguLaud[x][y] = "O";
            System.out.println("Arvuti lasi mööda!");

        }
        pront(mänguLaud);
        return mänguLaud;
    }
    //Meetod mis leiab kas mingit laeva veel esineb lauas
    public boolean kasLeidub(String element,String[][] mänguLaud){
        List<String> nimekiri = new ArrayList<String>();
        for (String[] rida: mänguLaud) {
            nimekiri.addAll(Arrays.asList(rida));
        }
        return nimekiri.contains(element);
    }
    public boolean kasPõhjas(String[][] mänguLaud, String laevaOsa){
        for (int i = 0; i < mänguLaud.length; i++) {
            for (int j = 0; j < mänguLaud.length; j++) {
                if (mänguLaud[i][j].equals(laevaOsa)){
                    return false;
                }
            }
        }
        return true;
    }
    public boolean kasLäbi(String[][] mänguLaud){
        for (int i = 0; i < mänguLaud.length; i++) {
            for (int j = 0; j < mänguLaud.length; j++) {
                if (!mänguLaud[i][j].equals("X") || !mänguLaud[i][j].equals("0") || !mänguLaud[i][j].equals(" ") ){
                    return false;
                }
            }
        }
        return true;
    }

}
