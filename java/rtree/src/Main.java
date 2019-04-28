
public class Main {

    static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }


    public static void main(String[] args) {

        RTree tree = new RTree(new Dato(0, 0, 1, 1), "Linear");

        for (int i = 0; i < 100; i++) {
            int x = randomWithRange(1, 10);
            int y = randomWithRange(1, 10);
            int ancho = randomWithRange(1, 30);
            int alto = randomWithRange(1, 30);
            IRectangulo dato = new Dato(x, y, ancho, alto);
            tree.insertar(dato);
        }


    }
}
