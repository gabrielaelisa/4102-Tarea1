public class NodoHoja extends AbstractNodo {


    public NodoHoja(int id, MBR mbr){
        super(id, mbr);
    }

    @Override
    public boolean esHoja(){
        return true;
    }

}
