public class NodoInterno extends AbstractNodo {

    // un nodo interno siempre se crea con 2 rectangulos, ya que  se crea a partir de overflow
    public NodoInterno(int id, IRectangulo mbr, int M){

        super(id, mbr, M);
    }

    @Override
    public boolean esHoja(){
        return false;
    }

    /*
    Busca el MBR en el nodo que apunta al mismo id apuntado por mbr y lo
    reemplaza.
     */
    public void replaceMRB(MBR mbr){
        for(int i= 0; i<this.cantidadRectangulos(); i++){
            if(this.getRectangulo(i).getId() == mbr.getId()){
                this.setRectangulo(i, mbr);
            }
        }
    }

    /* esta funcion retorna el rectangulo por el cual debo descender en el caso de que quiera
    insertar un dato*/
    public MBR target_rectangulo(IRectangulo dato, RTree tree){

        MBR target_rec= null;
        double min_grow= Double.MAX_VALUE;
        int min_area= Integer.MAX_VALUE;

        for (int x=0; x<this.cantidadRectangulos(); x++) {

            MBR rec = (MBR) this.getRectangulo(x);
            //este caso es facil, solo debo descender
            if (rec.contains(dato)) {
                target_rec = rec;
                break;

            }
            // aqui se debe escoger el MBR que crezca menos, para mantener el invariante 1
            else{
                double difArea= rec.difArea(dato);
                int area= rec.ancho()*rec.alto();
                // Nos quedamos con el el rectangulo que crece menos, en caso
                // de empate se baja por el MBR que tenga menor area:
                if (difArea < min_grow || difArea == min_grow && area < min_area) {
                    target_rec = rec;
                    min_grow= difArea;
                    min_area= area;
                }

            }

        }
        return target_rec;


    }

    @Override
    public void appendRectangulo(IRectangulo rect){
        // TODO Deshacernos de este metodo
        MBR m= (MBR) rect;
        super.appendRectangulo(m);
    }
}
