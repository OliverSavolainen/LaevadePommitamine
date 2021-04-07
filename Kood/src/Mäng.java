
public class Mäng extends Pommitamine{
    /**
     * Loome lauad arvutile ja mängijale ning paigutame laevad ning soutime mängijale temale tekkinud laua
     * Siis on tsükkel, mis vaatab, kas arvuti on võitnud esialgu, siis laseb mängijal pommitada, kontrollib, kas mängija võitis
     * ning kui pole, siis annab arvutile korra pommitada
     */
    public void mäng() {
        String[][] arvutiLaud = täidaLaud();
        String[][] mängijaLaud = täidaLaud();
        laevadePaigutus(arvutiLaud);
        laevadePaigutus(mängijaLaud);
        System.out.println("Teie laud:");
        prindiLaud(mängijaLaud);
        while (!kasLäbi(arvutiLaud)){
            pommita(arvutiLaud);
            if (kasLäbi(mängijaLaud)) {
                break;
            }
            arvuti_pommita(mängijaLaud);
        }
    }

    public Mäng(String mängijaNimi) {
        super(mängijaNimi);
    }
}
