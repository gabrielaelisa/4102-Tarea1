/* un rectangulo respresenta la MBR del nodo al cual apunta*/
class Rectangulo{
    
public:

    Rectangulo(){}
    Rectangulo(int* pts, int tipo){
        tipo=tipo;
        for(int i= 0; i<8; i++){
            puntos[i]=pts[i];
        }
    }
    int tipo;
    int puntos[8];
    int * nodo_hijo;


};
