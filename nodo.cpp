#include <list>
#include <string>
#include <bitset>
#include <fstream>
#include <stdio.h>
using namespace std;
std::list<int>::iterator it;
 

// Cantidad de datos (rectangulos por nodo)
#define M 200
// Memoria total de un nodo:
// Un rectangulo consiste de un puntero (8 bytes) a nodo y 4 puntos, donde
// cada punto son dos enteros (4 bytes). La memoria total que utiliza un nodo
// es la cantidad de nodos multiplicada por el tamaño de un rectangulo.
#define MEMORIA_TOTAL (4+4*2)*M + 4


class Rectangulo;
class Nodo;


class Nodo{
    
    // La informacion de un nodo

    Rectangulo padre;
    int cantidadRec;
    char * datos; // Rectangulos
    fstream archivo;

public: 
    /* Se crea un Nodo nuevo*/
    Nodo(char * id, Rectangulo nodo_padre){
        padre= nodo_padre;
        archivo.open(id, ios::binary | ios::ate);
        int size= archivo.tellg();
        datos = (char *) malloc(MEMORIA_TOTAL);
        if(size!=0){
            archivo.seekg(0, ios::beg);
            archivo.read(datos, size);
        }
    }
    /* se debe leer archivo, ver si el rectángulo cae dentro de uno de los MBR */
    void insertar( Rectangulo * rec){
        //puntero a data
        for (char c = *datos; c!= '/0'; c) {
            int x1, x2, x3, x4;
            int y1, y2, y3, y4;
            memcpy( x1, c, 4 );
            c=*(datos+4)
            memcpy( y1, c, 4 );
            c=*(datos+4)
            memcpy( x2, c, 4 );
            c=*(datos+4)
            memcpy( y2, c, 4 );
            c=*(datos+4)
            memcpy( x3, c, 4 );
            c=*(datos+4)
            memcpy( y3, c, 4 );
            c=*(datos+4)
            memcpy( x4, c, 4 );
            c=*(datos+4)
            memcpy( y4, c, 4 );
            c=*(datos+4)
            // verificar si se cae dentro del rectangulo



        }


    }

    void guardar(){
        archivo.write(datos, cantidadRec*16+4);
    }



};

/* un rectangulo respresenta la MBR del nodo al cual apunta*/
class Rectangulo{
public:

    //constructor para deserializar una linea de un archivo .txt 
    Rectangulo(){
    // aqui debe agregar los cuatro vertices al tributo mbr
    
    }
    string serialize(){
        string serialized; 
        for (it= MBR.begin(); it!= MBR.end(); it++){
            string mys= to_string(*it);
            serialized.append(mys);
            if(it!= MBR.end()){
                serialized.append(" ");
            }
            
        }
    }
private:

    Nodo * nodo;
    Nodo * hijo; // añadir la opción de tener como hijo una hoja?
    list<int> MBR;

};

