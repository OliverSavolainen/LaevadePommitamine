public class Mäng extends Laud {
    public void mäng() {
        String[][] arvutiLaud = täidaLaud();
        String[][] mängijaLaud = täidaLaud();
        laevadePaigutus(arvutiLaud);
        laevadePaigutus(mängijaLaud);
        pront(mängijaLaud);
        while (!kasLäbi(arvutiLaud)){
            pommita(mängijaLaud);
            if (kasLäbi(mängijaLaud)) {
                break;
            }
            arvuti_pommita(arvutiLaud);
        }
    }

    public Mäng(String mängijaNimi) {
        super(mängijaNimi);
    }
}
