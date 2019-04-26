import java.util.ArrayList;

public class NodoInterno extends AbstractNodo {

    private ArrayList<MBR> mbrs= new ArrayList<>(M);

    public NodoInterno(int id, MBR mbr){
        super(id, mbr);
    }

    @Override
    public boolean isNodoInterno() {
        return true;
    }

    @Override
    public boolean isNodoHoja() {
        return false;
    }

    public void setMBR(int pos, MBR mbr){
        mbrs.set(pos, mbr);
    }

    public void appendMBR(MBR mbr){
        mbrs.add(++indiceUltimo, mbr);
    }

    public int cantidadMBRs(){
        return indiceUltimo+1;
    }
}
