public class NodoInterno extends AbstractNodo {

    // un nodo interno siempre se crea con 2 rectangulos, ya que  se crea a partir de overflow
    public NodoInterno(int id, IRectangulo mbr){

        super(id, mbr);
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
}
