
#include <list>
#include <string>
#include <string.h> 
#include <stdlib.h>
#include <bitset>
#include <fstream>
#include <stdio.h>
using namespace std;
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
std::list<Rectangulo>::iterator it;

class Nodo{

public: 
    int tipo=NODO_INTERNO; // (0 interno, 1 hoja)
    int this_linea;
    list<Rectangulo> hijos;
    Rectangulo mbr_padre;
  
    Nodo(int linea, Rectangulo  rec, Rectangulo * padre){

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
            mbr_padre= *padre;
        }  
        hijos.push_back(rec);
       
    }
    void guardar(){
        int * datos= (int*) malloc(TAMANO_LINEA);
        memcpy(datos, &tipo, 4);
        memcpy(datos +4, (int *)hijos.size(),4);
        int pos=0;
        for (it= hijos.begin(); it!= hijos.end(); it++)
        {
            if (tipo==NODO_HOJA){
                memcpy(datos+8 + pos*36, it->puntos, 32);
            }
            else{            
                memcpy(datos+8 + pos*36, it->nodo_hijo, 4);            
                memcpy(datos+8 + pos*36 +4, it->puntos, 32);
            } 
            pos ++;
            


        }
        fseek(arbol, this_linea, SEEK_SET);
        fwrite(datos, 1 , TAMANO_LINEA, arbol ); 
        free(datos);
    }


};

/* un rectangulo respresenta la MBR del nodo al cual apunta*/
class Rectangulo{
    
public:
    int tipo;
    int puntos[8];
    int * nodo_hijo;

    Rectangulo(int* pts, int tipo){
        tipo=tipo;
        for(int i= 0; i<8; i++){
            puntos[i]=pts[i];
        }
    }

};


