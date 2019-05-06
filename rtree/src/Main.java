import java.util.ArrayList;

public class Main {

    static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }


    /*"las coordenadas de uno de los vértices deben ser reales uniformemente distribuidos en el rango [0, 500000],
     el ancho y el largo deben ser uniformemente distribuidos en [1, 100]".*/

    public static void main(String[] args) {
        int disk = Integer.parseInt(args[0]); // aca tomamos el argumento pasado por consola
        int M= (int)(disk-245)/26; // este debe ser el M de un usuario cualquiera

        
        //int M= 148;
        int m= (int)(M*0.4);

        int x1 = randomWithRange(1, 500000);
        int y1= randomWithRange(1, 500000);
        int ancho1 = randomWithRange(1, 100);
        int alto1 = randomWithRange(1, 100);

        RTree ltree = new RTree(new Dato(x1, y1, ancho1, alto1), "linear", M, m);
        RTree gtree = new RTree(new Dato(x1, y1, ancho1, alto1), "greene", M, m);
        RTree warmstate= new RTree(new Dato(x1, y1, ancho1, alto1), "dummy", M, m);


        // Se realiza un pequeño warm up
        for(int i=0;i< 2048;i++){
            int x = randomWithRange(1, 500000);
            int y = randomWithRange(1, 500000);
            int ancho = randomWithRange(1, 100);
            int alto = randomWithRange(1, 100);
            Dato dato = new Dato(x, y, ancho, alto);
            warmstate.insertarDato(dato);

        }
        warmstate=null;


        //----------------------------1 INSERCION --------------------------------------------
        /* se inserta y luego se prosigue en la busqueda sobre el mismo arbol*/

        int anterior=0;
        long tiempo_acc_l=0;
        long tiempo_acc_g=0;
        for(int i= 7; i< 21; i++) {
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

            System.out.println("Tiempo total para la insercion para "+ ltree.u.getSplit() +  "split, con n = 2^" + i + " : " +
                    tiempo_acc_l + "ms");
            System.out.println("Tiempo total para la insercion para "+ gtree.u.getSplit() +  "split, con n = 2^" + i + ": " +
                    tiempo_acc_g + "ms");



            //------------------------  2 PORCENTAJE DE USO DE DISCO ------------------------------------------------

            System.out.println("Porcentaje de uso de disco para "+ ltree.u.getSplit() +  "split, con n = 2^" + i + " : " +
                    ltree.Uso_Disco());
            System.out.println("Porcentaje de uso de disco para "+ gtree.u.getSplit() +  "split, con n = 2^" + i + ": " +
                    gtree.Uso_Disco());

            //-------------------------  3     BÚSQUEDA-------------------------------------------------------------

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

            System.out.println("Tiempo total para búsqueda para "+ ltree.u.getSplit() +  "split, con n = 2^" + i + " : " +
                    (endTime_b-startTime_b) + "ms");
            System.out.println("Accesos a disco  para "+ ltree.u.getSplit() +  "split, con n = 2^" + i + ": " +
                    ltree.accesos);
            System.out.println("Tiempo total para búsqueda para "+ gtree.u.getSplit() +  "split, con n = 2^" + i + " : " +
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
