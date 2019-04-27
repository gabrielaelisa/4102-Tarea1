public abstract class AbstractRectangulo implements IRectangulo {

    private int x;
    private int y;
    private int ancho;
    private int alto;
    protected int idNodo; // id del Nodo al que apunta
    protected int idContainer;

    // x e y son esquina inferior izquierda
    protected AbstractRectangulo(int idNodo, int x, int y, int ancho, int alto) {
        this.idNodo = idNodo;
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }

    public void SetContainer(int id){
        this.idContainer= id;
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
    public int getIdNodo() {
        return idNodo;
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

    public void ampliar(IRectangulo rect){
        // todo esta funcion cambia las coordenadas de un rectangulo para abarcar a rect
        return;
    }

    @Override
    public boolean intersects(IRectangulo rec){
        //vertice inferior izquierdo
        if(contains2(rec.getX(), this.getX(), this.ancho()) && contains2(rec.getY(), this.getY(), this.alto()))
            return true;

        //vertice inferior derecho
        if(contains2(rec.getX()+rec.ancho(), this.getX(), this.ancho()) && contains2(rec.getY(), this.getY(), this.alto()))
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
        if(!contains2(rec.getX(), this.getX(), this.ancho()) && !contains2(rec.getY(), this.getY(), this.alto()))
            return false;

        //vertice inferior derecho
        if(!contains2(rec.getX()+rec.ancho(), this.getX(), this.ancho()) && !contains2(rec.getY(), this.getY(), this.alto()))
            return false;

        //vertice superior derecho
        if(!contains(rec.getX()+rec.ancho(), this.getX(), this.ancho()) && !contains(rec.getY() + rec.alto(), this.getY(), this.alto()))
            return false;
        
        //vertice inferior izquierdo
        if(!contains(rec.getX(), this.getX(), this.ancho()) && !contains(rec.getY()+rec.alto(), this.getY(), this.alto()))
            return false;              
        
        return true;
    }
    @Override
    //esta funcion debe entregar la diferencia entre el mbr nuevo y lainterseccion entre 2 mbr, 0 si son iguales
    // o esta contenido
    public int interseccion(IRectangulo rec){
        // todo vicente
        return 0;

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
