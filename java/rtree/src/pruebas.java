public class pruebas {

    static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    public static void main(String[] args) {
        int M = 40;
        int m = 1;
        RTree tree = new RTree(new Dato(0, 0, 1, 1), "greene", M, m);

        for (int i = 0; i < 1109; i++) {
            int x = randomWithRange(1, 10);
            int y = randomWithRange(1, 10);
            int ancho = randomWithRange(1, 30);
            int alto = randomWithRange(1, 30);
            Dato dato = new Dato(x, y, ancho, alto);
            tree.insertarDato(dato);
        }
        //tree.current_node.guardar();


    }
    /*
    public static void main(String[] args) {
        int M= 10;
        int m= 4;
        RTree tree = new RTree(new Dato(0, 0, 1, 1), "linear", M, m);

        for (int i = 0; i < 1000; i++) {
            int x = randomWithRange(1, 10);
            int y = randomWithRange(1, 10);
            int ancho = randomWithRange(1, 30);
            int alto = randomWithRange(1, 30);
            Dato dato = new Dato(x, y, ancho, alto);
            tree.insertarDato(dato);
        }


    }*/
}
