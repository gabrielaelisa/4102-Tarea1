public abstract class AbstractRectangulo implements IRectangulo {

    private int x;
    private int y;
    private int ancho;
    private int alto;
    protected int idNodo; // id del Nodo al que apunta

    // x e y son esquina inferior izquierda
    protected AbstractRectangulo(int idNodo, int x, int y, int ancho, int alto) {
        this.idNodo= idNodo;
        this.x= x;
        this.y= y;
        this.ancho= ancho;
        this.alto= alto;
    }

    @Override
    public int getX()
    {
        return x;
    }

    @Override
    public int getY() {

        return y;
    }

    @Override
    public int ancho() {
        return ancho;
    }

    @Override
    public int alto() {
        return alto;
    }

    //Calcula si un rectangulo esta contenido o intersecta 
    @Override
    boolean isContain(IRectangulo rec){
        boolean contain = false;
       //lado izq
       if(rec.getX()+ rec.ancho()
       
        
        return contain;
    }

}
