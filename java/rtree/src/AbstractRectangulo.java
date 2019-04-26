public abstract class AbstractRectangulo implements IRectangulo {

    private int x;
    private int y;
    private int ancho;
    private int alto;
    protected int idNodo; // id del Nodo al que apunta

    // x e y son esquina inferior izquierda
    protected AbstractRectangulo(int idNodo, int x, int y, int ancho, int alto) {
        this.idNodo = idNodo;
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }

    @Override
    public int getX() {
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


    //Verifica si x esta contenido en el intervalo [y, y+a]
    public boolean contains2(int x, int y, int a) {
        return x > y && x < y + a;

    }

    @Override
    //Calcula si el rectangulo intersecta con un rectangulo rec
    public boolean intersects(IRectangulo rec) {
        //vertice inferior izquierdo
        if (contains2(rec.getX(), this.getX(), this.ancho()) && contains2(rec.getY(), this.getY(), this.alto()))
            return true;

        //vertice inferior derecho 
        if (contains2(rec.getX() + rec.ancho(), this.getX(), this.ancho()) && contains2(rec.getY(), this.getY(), this.alto()))
            return true;

        //vertice superior derecho
        if (contains2(rec.getX() + rec.ancho(), this.getX(), this.ancho()) && contains2(rec.getY() + rec.alto(), this.getY(), this.alto()))
            return true;
    }


        

    @Override
    public boolean contains(IRectangulo rec){
        //vertice inferior izquierdo
        if(!contains2(rec.getX(), this.getX(), this.ancho()) && !contains2(rec.getY(), this.getY(), this.alto()))
            return false;

        //vertice inferior derecho 
        if(!contains2(rec.getX()+rec.ancho(), this.getX(), this.ancho()) && !contains2(rec.getY(), this.getY(), this.alto()))
            return false;

        //vertice superior derecho
        if(!contains2(rec.getX()+rec.ancho(), this.getX(), this.ancho()) && !contains2(rec.getY()+rec.alto(), this.getY(), this.alto()))
            return false;
        
        //vertice inferior izquierdo
        if(!contains2(rec.getX(), this.getX(), this.ancho()) && !contains2(rec.getY()+rec.alto(), this.getY(), this.alto()))
            return false;              
        
        return true;
    }


    }

    


