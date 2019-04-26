public class NodoInterno extends AbstractNodo {

    public NodoInterno(int id, MBR mbr){

        super(id, mbr);
    }

    @Override
    public boolean esHoja(){
        return false;
    }

}
