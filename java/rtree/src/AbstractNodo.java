
public abstract class AbstractNodo implements Nodo {

    protected int M= 1000000; // Cantidad de datos (Rectangulos) en un nodo
    private int id;
    private MBR mbr;
    protected int indiceUltimo= -1; // Indice del ultimo Rectangulo

    protected AbstractNodo(int id, MBR mbr){
        this.id= id;
        this.mbr= mbr;
    }

    @Override
    public int getId(){
        return id;
    }

    @Override
    public MBR getMbr(){
        return mbr;
    }

}
