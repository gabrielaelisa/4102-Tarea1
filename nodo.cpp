
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
#include "Rectangulo.h"

/* clase nodo, un nodo almacena hasta M rectangulos*/

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
            arbol = fopen("archivo_tarea", "w+");
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
        printf("entra aqui");
        memcpy(datos, &tipo, 4);
        memcpy(datos +4, (int *)hijos.size(),4);
        int pos=0;
        for (it= hijos.begin(); it!= hijos.end(); it++)
        {
            printf("for :%d\n", pos);
            if (tipo==NODO_HOJA){
                memcpy(datos+8 + pos*36, it->puntos, 32);
            }
            else{            
                memcpy(datos+8 + pos*36, it->nodo_hijo, 4);            
                memcpy(datos+8 + pos*36 +4, it->puntos, 32);
            } 
            pos ++;
            


        }
        //fseek(arbol, this_linea, SEEK_SET);
        fwrite(datos, 4 , TAMANO_LINEA, arbol ); 
        fclose(arbol);
        free(datos);
    }
    void instertar(Rectangulo rec){
        if((int)hijos.size()==M){
            printf("entra a guardar");
            guardar();
        }
        else
        {
            hijos.push_back(rec);
        }
        

    }

};

main(int argc, char *argv[]){
    int val[8] = { 1,1,2,1, 1,2 ,2,2 };
    Rectangulo R= Rectangulo(val, DATO);
    Nodo n= Nodo(1, R, NULL);
    for(int i=0; i<M; i++){
        printf("%d\n", i);
        int val[8] = { 1,1,2,1, 1,2 ,2,2 };
        Rectangulo R= Rectangulo(val, DATO);
        n.instertar(R);
    }
}

