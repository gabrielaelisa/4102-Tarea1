#include <list>
#include <string>
#include <string.h> 
#include <stdlib.h>
#include <bitset>
#include <fstream>
#include <stdio.h>
using namespace std;
std::list<int>::iterator it;
FILE * arbol;
 
// Cantidad de datos
#define M 10000
#define TAMANO_LINEA 8+M*36
#define NODO_INTERNO 0
#define NODO_HOJA 1
#define MBR 0
#define DATO 1

/* clase nodo, un nodo almacena hasta M rectangulos*/
class Rectangulo;
class Nodo;

class Nodo{

public: 
    int tipo=NODO_INTERNO; // (0 interno, 1 hoja)
    // en que parte del archivo comienza
    int cantidadElementos=0;
    int this_linea;
    int * datos;
    Rectangulo * mbr_padre;
  
    Nodo(int linea, Rectangulo * rec, Rectangulo * padre){
        datos= (int*) malloc(TAMANO_LINEA);
        if(rec.tipo==DATO) tipo=NODO_HOJA;
        this_linea=linea;

        //raiz
        if (padre==NULL){
            arbol = fopen("archivo_tarea", "rw");
            setbuf(arbol, NULL);
        }

        // nodo
        else
        {
            mbr_padre= padre;
        }  
        append(*rec);
        datos[0]= tipo; 
    }
    void append(Rectangulo rec){
        set_rect(cantidadElementos, rec);
        cantidadElementos++;
    }
    
    void set_rect(int pos, Rectangulo rec){
        if (tipo==NODO_HOJA){
            memcpy(datos+8 + pos*36, rec.puntos, 32);
        }
        else{            
            memcpy(datos+8 + pos*36, &rec.nodo_hijo, 4);            
            memcpy(datos+8 + pos*36 +4, rec.puntos, 32);
        }     
    }


    Rectangulo get_rectangulo(int pos){ 
        int p= 4+36*pos+4;
        int tipoRect= tipo==NODO_HOJA? DATO: MBR;
        return Rectangulo(datos+p, tipoRect);
    }

    // aqui se implementa el metodo para mandar el nodo a disco cuando se descienda por algun rectangulo
    void guardar(){
        datos[1]= cantidadElementos;
        fseek(arbol, this_linea, SEEK_SET);
        fwrite(datos, 1 , TAMANO_LINEA, arbol );
    }

};

/* un rectangulo respresenta la MBR del nodo al cual apunta*/
class Rectangulo{
    
public:
    int tipo;
    int puntos[8];
    Nodo * nodo_actual;
    int nodo_hijo;


    Rectangulo(int* pts, int tipo){
        tipo=tipo;
        for(int i= 0; i<8; i++){
            puntos[i]=pts[i];
        }
    }  


};
