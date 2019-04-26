import java.util.ArrayList;

public class NodoHoja extends AbstractNodo {

    private ArrayList<Dato> datos= new ArrayList<>(M);

    public NodoHoja(int id, MBR mbr){
        super(id, mbr);
    }

    @Override
    public boolean isNodoInterno() {
        return false;
    }

    @Override
    public boolean isNodoHoja() {
        return true;
    }

    public void setDato(int pos, Dato dato){
        datos.set(pos, dato);
    }

    public void appendDato(Dato dato){
        datos.add(++indiceUltimo, dato);
    }

    public int cantidadDatos(){
        return indiceUltimo+1;
    }

    public Dato getDato(int pos){
        return datos.get(pos);
    }
}
