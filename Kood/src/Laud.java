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
        String[][] tühiLaud = new String[10][10];
        for (int i = 0; i < tühiLaud.length; i++) {
            for (int j = 0; j < tühiLaud.length; j++) {
                tühiLaud[i][j] = " ";
            }

        }
        return tühiLaud;
    }

    public String[][] laevadePaigutus(String[][] tühiLaud) {
        String[] tähistused = {"5", "4", "3", "2"};

        for (int i = 5; i > 1; i--) {
            int vert_koordinaat = (int) Math.round(Math.random() * 9);
            int hori_koordinaat = (int) Math.round(Math.random() * 9);
            int suund = (int) Math.round(Math.random() * 3) + 1;
            while (!paigutusVõimalik(vert_koordinaat, hori_koordinaat, suund, i, tühiLaud)) {
                vert_koordinaat = (int) Math.round(Math.random() * 9);
                hori_koordinaat = (int) Math.round(Math.random() * 9);
                suund = (int) Math.round(Math.random() * 3) + 1;
            }
            String vastavTähistus = tähistused[tähistused.length + 1 - i];
            tühiLaud[vert_koordinaat][hori_koordinaat] = vastavTähistus;
            switch (suund) {
                case 1 -> {
                    for (int j = 1; j < i; j++) {
                        tühiLaud[vert_koordinaat + j][hori_koordinaat] = vastavTähistus;
                    }
                }
                case 2 -> {
                    for (int j = 1; j < i; j++) {
                        tühiLaud[vert_koordinaat - j][hori_koordinaat] = vastavTähistus;
                    }
                }
                case 3 -> {
                    for (int j = 1; j < i; j++) {
                        tühiLaud[vert_koordinaat][hori_koordinaat + j] = vastavTähistus;
                    }
                }
                case 4 -> {
                    for (int j = 1; j < i; j++) {
                        tühiLaud[vert_koordinaat][hori_koordinaat - j] = vastavTähistus;
                    }
                }

            }


        }
        return tühiLaud;
    }

    public boolean paigutusVõimalik(int x, int y, int suund, int pikkus, String[][] tühiLaud) {
        switch (suund) {
            case 1 -> {
                if (x + pikkus > 9) {
                    return false;
                }
                for (int j = 0; j < pikkus; j++) {
                    if (!tühiLaud[x + j][y].equals(" "))
                        return false;
                }
            }
            case 2 -> {
                if (x - pikkus < 0) {
                    return false;
                }
                for (int j = 0; j < pikkus; j++) {
                    if (!tühiLaud[x - j][y].equals(" "))
                        return false;
                }
            }
            case 3 -> {
                if (y + pikkus > 9) {
                    return false;
                }
                for (int j = 0; j < pikkus; j++) {
                    if (!tühiLaud[x][y + j].equals(" "))
                        return false;
                }
            }
            case 4 -> {
                if (y - pikkus < 0) {
                    return false;
                }
                for (int j = 0; j < pikkus; j++) {
                    if (!tühiLaud[x][y - j].equals(" "))
                        return false;
                }
            }

        }
        return true;
    }
    public String[][] pommita(String[][] tühiLaud){
        int x;
        int y;
        do{
            System.out.println("Rida: ");
            x = new Scanner(System.in).nextInt();
        } while (x < 1 || x > tühiLaud.length);
        do{
            System.out.println("Veerg: ");
            y = new Scanner(System.in).nextInt();
        } while (y < 1 || y > tühiLaud.length);
        if(!tühiLaud[x][y].equals(" ")){
            System.out.println("Said pihta!");
            if (kasPõhjas(tühiLaud, tühiLaud[x][y])){
                System.out.println("Laev põhjas!");
                if (kasLäbi(tühiLaud)){
                    System.out.println("Mäng läbi. Sina võitsid!");
                    return null;
                }
            }
            tühiLaud[x][y] = "X";
            pommita(tühiLaud);
        }
        else if(tühiLaud[x][y].equals("X")||tühiLaud[x][y].equals("O")){
            System.out.println("Oled seda ruutu juba pommitanud! Vali uuesti.");
            pommita(tühiLaud);
        }
        else {
            tühiLaud[x][y] = "O";
            System.out.println("Lasid mööda!");
        }
        return tühiLaud;
    }

    public String[][] arvuti_pommita (String[][] tühiLaud){
        int x = (int)(Math.random() * (9 + 1));
        int y  = (int)(Math.random() * (9 + 1));
        if(!tühiLaud[x][y].equals(" ") && !tühiLaud[x][y].equals("O") && !tühiLaud[x][y].equals("x")){
            System.out.println("Arvuti sai su laevale pihta!");
            pront(tühiLaud);
            if (kasPõhjas(tühiLaud, tühiLaud[x][y])){
                System.out.println("Laev põhjas!");
                if (kasLäbi(tühiLaud)){
                    System.out.println("Mäng läbi. Arvuti võitis");
                    return null;
                }
            }
            tühiLaud[x][y] = "X";
            arvuti_pommita(tühiLaud);
        }
        else if(tühiLaud[x][y].equals("X")||tühiLaud[x][y].equals("O")){
            arvuti_pommita(tühiLaud);
        }
        else {
            tühiLaud[x][y] = "O";
            System.out.println("Arvuti lasi mööda!");
        }
        return tühiLaud;
    }
    //Meetod mis leiab kas mingit laeva veel esineb lauas
    public boolean kasLeidub(String element,String[][] tühiLaud){
        List<String> nimekiri = new ArrayList<String>();
        for (String[] rida: tühiLaud) {
            nimekiri.addAll(Arrays.asList(rida));
        }
        return nimekiri.contains(element);
    }
    public boolean kasPõhjas(String[][] tühiLaud, String laevaOsa){
        for (int i = 0; i < tühiLaud.length; i++) {
            for (int j = 0; j < tühiLaud.length; j++) {
                if (tühiLaud[i][j].equals(laevaOsa)){
                    return false;
                }
            }
        }
        return true;
    }
    public boolean kasLäbi(String[][] tühiLaud){
        for (int i = 0; i < tühiLaud.length; i++) {
            for (int j = 0; j < tühiLaud.length; j++) {
                if (!tühiLaud[i][j].equals("X") || !tühiLaud[i][j].equals("0") ){
                    return false;
                }
            }
        }
        return true;
    }

}
