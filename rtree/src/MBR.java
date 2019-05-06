public class MBR extends AbstractRectangulo {

    public MBR(int idNodo, int x, int y, int ancho, int alto) {
        super(idNodo, x, y, ancho, alto);
    }

    public MBR(MBR mbr){
        super(mbr.getId(), mbr.getX(), mbr.getY(), mbr.ancho(), mbr.alto());
    }

    @Override
    public boolean esDato(){ return  false;}

}
