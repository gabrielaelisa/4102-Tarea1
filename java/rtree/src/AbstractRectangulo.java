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
    public int getId(){
        return idNodo;
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
    public boolean intersects(IRectangulo rec){
        //vertice inferior izquierdo
        if(contains(rec.getX(), this.getX(), this.ancho()) && contains(rec.getY(), this.getY(), this.alto()))
            return true;

        //vertice inferior derecho 
        if(contains(rec.getX()+rec.ancho(), this.getX(), this.ancho()) && contains(rec.getY(), this.getY(), this.alto()))
            return true;

        //vertice superior derecho
        if(contains(rec.getX()+rec.ancho(), this.getX(), this.ancho()) && contains(rec.getY()+rec.alto(), this.getY(), this.alto()))
            return true;
        
        //vertice inferior izquierdo
        if(contains(rec.getX(), this.getX(), this.ancho()) && contains(rec.getY()+rec.alto(), this.getY(), this.alto()))
            return true;              
        
        return false;
    }

    //Verifica si el rectangulo rec esta completamente contenido 
    @Override
    public boolean contains(IRectangulo rec){
        //vertice inferior izquierdo
        if(!contains(rec.getX(), this.getX(), this.ancho()) && !contains(rec.getY(), this.getY(), this.alto()))
            return false;

        //vertice inferior derecho 
        if(!contains(rec.getX()+rec.ancho(), this.getX(), this.ancho()) && !contains(rec.getY(), this.getY(), this.alto()))
            return false;

        //vertice superior derecho
        if(!contains(rec.getX()+rec.ancho(), this.getX(), this.ancho()) && !contains(rec.getY() + rec.alto(), this.getY(), this.alto()))
            return false;
        
        //vertice inferior izquierdo
        if(!contains(rec.getX(), this.getX(), this.ancho()) && !contains(rec.getY()+rec.alto(), this.getY(), this.alto()))
            return false;              
        
        return true;
    }


    //Verifica si x esta contenido en el intervalo [y, y+a]
    public boolean contains(int x, int y, int a){
        return x > y && x < y+a;
    }

    //Retorna el MBR que crecio para contener a rec
    //Si el MBR contiene a rec, este no debe crecer
    //Si no, se debe calcular la distancia euclidiana entre lados opuestos entre rectangulos(izq-der) y (sup-inf)
    //Para calcular el punto (x,y) inferior izquierdo se toma el menor x, y de los puntos inferiores izquierdos de rec y this
    public IRectangulo grewArea(IRectangulo rec){
        if (this.contains(rec)){
            return this;
        }
        else{
            int dist_x = 0;
            if(this.getX()< rec.getX()){
                dist_x = (int) rec.getX() + rec.ancho() - this.getX();
            }
            else{
                dist_x = (int) this.getX() + this.ancho() - rec.getX();
            }
            
            int dist_y = 0;
            if(this.getY() < rec.getY()){
                dist_y = (int) rec.getY() + rec.alto() - this.getY();
            }else{
                dist_y = (int) this.getY() + this.alto() - rec.getY();
            }
            //actualizamos valores
            this.ancho= dist_x;
            this.alto = dist_y;
            this.x = (this.getX() < rec.getX()) ? this.getX() : rec.getX();
            this.y = (this.getY() < rec.getY()) ? this.getY() : rec.getY();  

            return this;                     
        }
    }

    //retorna diferencia entre Area aumentada dado un rec, menos la area original
    public double difArea(IRectangulo rec){
        IRectangulo replica = this;
        IRectangulo area_max = replica.grewArea(rec);

        return (area_max.alto() * area_max.ancho()) - (this.alto() * this.ancho());


    }

}
