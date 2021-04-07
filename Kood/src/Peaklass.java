public class Peaklass {
    public static void main(String[] args) {
        Mäng uus = new Mäng("Oliver");
        System.out.println("Tere tulemast laevade pommitamisse, " + uus.getMängijaNimi());
        System.out.println("""
                Teie laud luuakse randomi alusel ja siis saate hakata mängima arvuti vastu. Valige arv 0st 9ni
                reaks ja 0st 9ni veeruks, kus 0,0 tähistab vasakut ülemist välja. Laevad on märgitud numbritega, mis näitavad laeva
                pikkust. Saate pommitada kuni eksite ja siis tuleb arvuti kord.
                Näete, kuhu arvuti pommitas iga tema korra järel. Kui lasete ise või arvuti laseb laeva põhja, tuleb selle kohta sõnum
                ja kui mäng lõppeb, siis tuleb ka selle kohta sõnum
                """);
        uus.mäng();
    }
}
