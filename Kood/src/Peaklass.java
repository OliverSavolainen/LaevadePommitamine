import java.util.Arrays;
import java.util.stream.Collectors;

public class Peaklass {
    public static void main(String[] args) {
        Laud mängija = new Laud("Oliver");
        mängija.laevadePaigutus();
        Laud arvuti = new Laud("Arvuti");
        arvuti.laevadePaigutus();
        System.out.println();
        int m2ngijaLaevadeArv = 5;
        int arvutiLaevadeArv = 5;
        //Skeem kuidas mänguväli välja näha võiks
        /*while(m2ngijaLaevadeArv> 0 || arvutiLaevadeArv >0) {
            // Mängija pommitab arvutit
            arvuti.pommita();
            pront(arvuti.getTühiLaud());
            
            // Arvuti pommitab mängijat
            mängija.arvuti_pommita();
            pront(mängija.getTühiLaud());
        }*/
    }
    static void pront(String[][] m){
        System.out.println(Arrays.stream(m).map(Arrays::toString).collect(Collectors.joining("\n")));
    }
}
