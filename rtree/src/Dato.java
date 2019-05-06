public class Dato extends AbstractRectangulo {

    protected Dato(int x, int y, int ancho, int alto) {
        super(-1, x, y, ancho, alto);
    }

    public Dato(Dato dato){
        super(-1, dato.getX(), dato.getY(), dato.ancho(), dato.alto());
    }

    @Override
    public boolean esDato(){ return  true;}

}
