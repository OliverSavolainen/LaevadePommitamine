import java.util.Arrays;
import java.util.stream.Collectors;

public class Peaklass {
    public static void main(String[] args) {
        Laud mängija = new Laud("Oliver");
        pront(mängija.laevadePaigutus());
    }
    static void pront(String[][] m){
        System.out.println(Arrays.stream(m).map(Arrays::toString).collect(Collectors.joining("\n")));
    }
}
