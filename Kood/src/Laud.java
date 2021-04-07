import java.util.Arrays;
public class Laud {

    private String mängijaNimi;

    public String getMängijaNimi() {
        return mängijaNimi;
    }

    /**
     * Isendiväli, mis personaliseerib mängu nime kohta
     */
    public Laud(String mängijaNimi) {
        this.mängijaNimi = mängijaNimi;
    }

    /**
     * Meetod, mis arusaadavalt prindib välja laua hetkeolukorra
     */
    static void prindiLaud(String[][] mänguLaud){
        for (String[] rida: mänguLaud) {
            System.out.println(Arrays.toString(rida));
        }
    }

    /**
     * Täidame laua tühikutega
     */
    public String[][] täidaLaud() {
        String[][] mänguLaud = new String[10][10];
        for (int i = 0; i < mänguLaud.length; i++) {
            for (int j = 0; j < mänguLaud.length; j++) {
                mänguLaud[i][j] = " ";
            }

        }
        return mänguLaud;
    }

    /**
     * See meetod paigutab laevad tsükliga lauale randomilt, kusjuures iga laev on märgitud pikkust märkivate numbritega
     * (vastavTähistus). Kasutab paigutusVõimalik meetodit, kuni see meetod annab loa laeva paigutada
     * Suund numbritega 1-4, mis on vastavalt alla, üles, paremale ja vasakule. Kasutatakse switchi vastavalt suunale ja
     * tsüliga paigutatakse laev ning pärast returnitakse laud
     * Hetkel on ka mängija paigutus random, aga plaan 2.rühmatöös anda mängijale võimalus ise valida asukohad
     */
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

    /**
     * Kasutatakse laevadePaigutus meetodis. Vaatab kas laev olenevalt algasukohast, pikkusest ja suunast mahub laua sisse
     * ja ei lähe vastuollu teise laevaga
     */
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


}
