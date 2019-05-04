import java.util.ArrayList;

public class Main {

    static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }


    //------------------ EXPERIMENTO NO CORRER ----------------------------------------
    /*"las coordenadas de uno de los vértices deben ser reales uniformemente distribuidos en el rango [0, 500000],
     el ancho y el largo deben ser uniformemente distribuidos en [1, 100]".*/

    public static void main(String[] args) {
        int M= 148;
        int m= (int)(M*0.4);

        RTree ltree = new RTree(new Dato(0, 0, 1, 1), "linear", M, m);
        RTree gtree = new RTree(new Dato(0, 0, 1, 1), "greene", M, m);

        /*habra un solo arbol, en el cual se insertaran los datos de cada intervalo
        y luego se prosigue con la búsqueda
         esto se debe hacer para n en΅{2^9, ... ,2^25}*/

        int anterior=0;
        long tiempo_acc_l=0;
        long tiempo_acc_g=0;
        for(int i= 5; i< 26; i++) {
            // esta resta es lo que falta insertar para el siguiente rango
            double limite = Math.pow(2, i) - Math.pow(2, anterior);
            // se crean los rectángulos a insertar
            ArrayList<Dato> rects= new ArrayList<>();
            for (double j = 0; j < limite; j++) {
                int x = randomWithRange(1, 500000);
                int y = randomWithRange(1, 500000);
                int ancho = randomWithRange(1, 100);
                int alto = randomWithRange(1, 100);
                Dato dato = new Dato(x, y, ancho, alto);
                rects.add(dato);
            }
            // se insertan los rectangulos para linear
            long startTime = System.currentTimeMillis();
            for (Dato dato : rects) {
                ltree.insertarDato(dato);
            }
            long endTime = System.currentTimeMillis();
            tiempo_acc_l+= endTime-startTime;

            // se insertan rectangulos para greene
            long startTime2 = System.currentTimeMillis();
            for (Dato dato : rects) {
                gtree.insertarDato(dato);
            }
            long endTime2 = System.currentTimeMillis();
            tiempo_acc_g+= endTime2-startTime2;

            System.out.println("Tiempo total para "+ ltree.u.getSplit() +  "split, con n = 2^" + i + ":" +
                    tiempo_acc_l + "ms");
            System.out.println("Tiempo total para "+ gtree.u.getSplit() +  "split, con n = 2^" + i + ":" +
                    tiempo_acc_g + "ms");

            anterior = i - 1;
            /*luego se buscan los n/10 datos necesarios) y generados aleatoriamente*/


        }


    }



}
