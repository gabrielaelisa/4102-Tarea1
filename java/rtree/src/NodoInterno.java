public class NodoInterno extends AbstractNodo {

    public NodoInterno(int id, IRectangulo mbr){

        super(id, mbr);
    }

    @Override
    public boolean esHoja(){
        return false;
    }

}
