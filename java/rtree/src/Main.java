
public class Main {

    static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }


    //-------------PRUEBAS CHICAS, descomentar esto para correr---------------------------
    /*public static void main(String[] args) {
        int M= 10000;
        int m= 1000;
        RTree tree = new RTree(new Dato(0, 0, 1, 1), "linear", M, m);

        for (int i = 0; i < 99; i++) {
            int x = randomWithRange(1, 10);
            int y = randomWithRange(1, 10);
            int ancho = randomWithRange(1, 30);
            int alto = randomWithRange(1, 30);
            Dato dato = new Dato(x, y, ancho, alto);
            tree.insertarDato(dato);
        }


    }*/


    //------------------ EXPERIMENTO NO CORRER ----------------------------------------
    /*"las coordenadas de uno de los vértices deben ser reales uniformemente distribuidos en el rango [0, 500000],
     el ancho y el largo deben ser uniformemente distribuidos en [1, 100]".*/

    public static void main(String[] args) {
        int M= 10000;
        int m= 1000;

        RTree tree = new RTree(new Dato(0, 0, 1, 1), "linear", M, m);

        /*habra un solo arbol, en el cual se insertaran los datos de cada intervalo
        y luego se prosigue con la búsqueda
         esto se debe hacer para n en΅{2^9, ... ,2^25}*/

        int anterior=0;
        for(int i= 9; i< 26; i++) {

            double limite = Math.pow(2, i) - Math.pow(2, anterior);
            for (double j = 0; j < limite; j++) {
                int x = randomWithRange(1, 500000);
                int y = randomWithRange(1, 500000);
                int ancho = randomWithRange(1, 100);
                int alto = randomWithRange(1, 100);
                Dato dato = new Dato(x, y, ancho, alto);
                tree.insertarDato(dato);


            }
            anterior = i - 1;

        }


    }



}
