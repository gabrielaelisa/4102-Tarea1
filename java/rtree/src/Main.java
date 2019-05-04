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

        /* se inserta y luego se prosigue en la busqueda sobre el mismo arbol*/

        int anterior=0;
        long tiempo_acc_l=0;
        long tiempo_acc_g=0;
        for(int i= 7; i< 20; i++) {
            // esta resta es lo que falta insertar para el siguiente rango
            double limite = Math.pow(2, i) - Math.pow(2, anterior);
            // se crean los rectángulos a insertar
            ArrayList<Dato> rects_i= new ArrayList<>();
            for (double j = 0; j < limite; j++) {
                int x = randomWithRange(1, 500000);
                int y = randomWithRange(1, 500000);
                int ancho = randomWithRange(1, 100);
                int alto = randomWithRange(1, 100);
                Dato dato = new Dato(x, y, ancho, alto);
                rects_i.add(dato);
            }
            // se insertan los rectangulos para linear
            long startTime = System.currentTimeMillis();
            for (Dato dato : rects_i) {
                ltree.insertarDato(dato);
            }
            long endTime = System.currentTimeMillis();
            tiempo_acc_l+= endTime-startTime;

            // se insertan rectangulos para greene
            long startTime2 = System.currentTimeMillis();
            for (Dato dato : rects_i) {
                gtree.insertarDato(dato);
            }
            long endTime2 = System.currentTimeMillis();
            tiempo_acc_g+= endTime2-startTime2;

            System.out.println("Tiempo total para la insercion "+ ltree.u.getSplit() +  "split, con n = 2^" + i + " : " +
                    tiempo_acc_l + "ms");
            System.out.println("Tiempo total para la insercion "+ gtree.u.getSplit() +  "split, con n = 2^" + i + ": " +
                    tiempo_acc_g + "ms");

            //------------------------- BÚSQUEDA-----------------------------------------------------------------------

            // se crean reactangulos a buscar
            ArrayList<MBR> rects_b= new ArrayList<>();
            for (double j = 0; j < (double)limite/10; j++) {
                int x = randomWithRange(1, 500000);
                int y = randomWithRange(1, 500000);
                int ancho = randomWithRange(1, 100);
                int alto = randomWithRange(1, 100);
                MBR rect = new MBR(-1,x, y, ancho, alto);
                rects_b.add(rect);
            }
            // se insertan los rectangulos para linear
            long startTime_b = System.currentTimeMillis();
            for (MBR rect : rects_b) {
                ltree.buscar(rect);
            }
            long endTime_b = System.currentTimeMillis();


            // se insertan rectangulos para greene
            long startTime_b2 = System.currentTimeMillis();
            for (MBR rect : rects_b) {
                gtree.buscar(rect);
            }
            long endTime_b2 = System.currentTimeMillis();

            System.out.println("Tiempo total para búsqueda "+ ltree.u.getSplit() +  "split, con n = 2^" + i + " : " +
                    (endTime_b-startTime_b) + "ms");
            System.out.println("Accesos a disco  para "+ ltree.u.getSplit() +  "split, con n = 2^" + i + ": " +
                    ltree.accesos);
            System.out.println("Tiempo total para búsqueda "+ gtree.u.getSplit() +  "split, con n = 2^" + i + " : " +
                    (endTime_b2-startTime_b2) +"ms");

            System.out.println("Accesos a disco para "+ gtree.u.getSplit() +  "split, con n = 2^" + i + " : " +
                    gtree.accesos );


            // seteamos accesos en 0
            ltree.accesos=0;
            gtree.accesos=0;
            anterior = i - 1;



        }



    }



}
