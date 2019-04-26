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

    //Calcula si el rectangulo intersecta con un rectangulo rec
    @Override
<<<<<<< HEAD
    boolean intersects(IRectangulo rec){
        //vertice inferior izquierdo
        if(contains(rec.getX(), this.getX(), this.ancho()) && contains(rec.getY(), this.getY(), this.alto()))
            return true;

        //vertice inferior derecho 
        if(contains(rec.getX()+rec.ancho(), this.getX(), this.ancho()) && contains(rec.getY(), this.getY(), this.alto()))
            return true;

        //vertice superior derecho
        if(contains(rec.getX()+rec.ancho(), this.getX(), this.ancho()) && contains(rec.getY()+rec.largo(), this.getY(), this.alto()))
            return true;
=======
    boolean contains(IRectangulo rec){
        boolean contain = false;
       //lado izq
       if(rec.getX()+ rec.ancho()
       
>>>>>>> 56c33014f1d2ec4b4465c6e47104a2ef77c0d197
        
        //vertice inferior izquierdo
        if(contains(rec.getX(), this.getX(), this.ancho()) && contains(rec.getY()+rec.alto(), this.getY(), this.largo()))
            return true;              
        
        return false;
    }

    //Verifica si el rectangulo rec esta completamente contenido 
    @Override
    boolean contains(IRectangulo rec){
        //vertice inferior izquierdo
        if(!contains(rec.getX(), this.getX(), this.ancho()) && !contains(rec.getY(), this.getY(), this.alto()))
            return false;

        //vertice inferior derecho 
        if(!contains(rec.getX()+rec.ancho(), this.getX(), this.ancho()) && !contains(rec.getY(), this.getY(), this.alto()))
            return false;

        //vertice superior derecho
        if(!contains(rec.getX()+rec.ancho(), this.getX(), this.ancho()) && !contains(rec.getY()+rec.largo(), this.getY(), this.alto()))
            return false;
        
        //vertice inferior izquierdo
        if(!contains(rec.getX(), this.getX(), this.ancho()) && !contains(rec.getY()+rec.alto(), this.getY(), this.largo()))
            return false;              
        
        return true;
    }


    //Verifica si x esta contenido en el intervalo [y, y+a]
    boolean contains(int x, int y, int a){
        return x > y && x < y+a;
    }

}
