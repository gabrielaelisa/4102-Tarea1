public abstract class AbstractRectangulo implements IRectangulo {

    // El punto (x, y) es la esquina inferior izquierda.
    private int x;
    private int y;
    private int ancho;
    private int alto;
    protected int idNodo; // id del Nodo al que apunta


    protected AbstractRectangulo(int idNodo, int x, int y, int ancho, int alto) {
        this.idNodo = idNodo;
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }

    @Override
    public int getId(){
        return idNodo;
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
    

    /*
    Verifica si x esta contenido en el intervalo [y, y + offset]
     */
    private static boolean enIntervalo(int x, int y, int offset) {
        return (x >= y) && (x <= y + offset);
    }


    /*
    Retorna true si (x, y) esta contenido en rec.
     */
    private static boolean puntoEn(int x, int y, IRectangulo rec){
        return enIntervalo(x, rec.getX(), rec.ancho()) &&
                enIntervalo(y, rec.getY(), rec.alto());
    }


    /*
    Retorna true si contiene alguna esquina en rec.
     */
    private boolean esquinaEn(IRectangulo rec){
        return puntoEn(this.getX(), this.getY(), rec) || // Esquina inferior izquierda
            puntoEn(this.getX(), this.getY() + ancho, rec) || // Superior izquierda
            puntoEn(this.getX() + ancho, this.getY() + ancho, rec) || // Superior derecha
            puntoEn(this.getX() + ancho, this.getY(), rec); // Inferior derecha
    }


    /*
    Retorna true si intersecta a rec.
     */
    @Override
    public boolean intersects(IRectangulo rec){
        return this.esquinaEn(rec) || ((AbstractRectangulo) rec).esquinaEn(this);
    }


    /*
    Retorna true si esta contenido en rec (contiene todas sus esquinas en rec).
     */
    @Override
    public boolean contains(IRectangulo rec){
        return puntoEn(this.getX(), this.getY(), rec) && // Esquina inferior izquierda
                puntoEn(this.getX(), this.getY() + ancho, rec) && // Superior izquierda
                puntoEn(this.getX() + ancho, this.getY() + ancho, rec) && // Superior derecha
                puntoEn(this.getX() + ancho, this.getY(), rec); // Inferior derecha
    }


    /*
    Amplia el rectangulo para que contenga a rec.
     */
    @Override
    public void ampliar(IRectangulo rec){
        int xmax= x + ancho;
        int ymax= y + alto;
        if(rec.getX() < x)
            this.x= rec.getX();
        if(rec.getY() < y)
            this.y= rec.getY();

        if(rec.getX() + rec.ancho() > xmax)
            xmax = rec.getX() + rec.ancho();
        if(rec.getY() + rec.alto() > ymax)
            ymax = rec.getY() + rec.alto();
        ancho= xmax - x;
        alto= ymax - y;
    }


    /*
    Retorna diferencia entre Area aumentada dado un rec, menos la area original
     */
    @Override
    public double difArea(IRectangulo rec){
        IRectangulo replica = new MBR(-1, this.getX(), this.getY(), this.ancho(), this.alto());
        replica.ampliar(rec);
        int dif= (replica.alto() * replica.ancho()) - (this.alto() * this.ancho());
        return dif;
    }


    /*
    Retorna true si obj es un AbstractRectangulo y tiene id, x, y, ancho y alto
    iguales a los de este objeto.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AbstractRectangulo){
            AbstractRectangulo rec= (AbstractRectangulo) obj;
            return this.idNodo == rec.idNodo && this.x == rec.x && this.y == rec.y &&
                    this.ancho == rec.ancho && this.alto == rec.alto;
        }
        return false;
    }

}
