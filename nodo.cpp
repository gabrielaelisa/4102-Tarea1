#include <list>
#include <string>
#include <fstream>
using namespace std;

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
private:

    Nodo * nodo;
    Nodo * hijo; // añadir la opción de tener como hijo una hoja?
    list<int> MBR;

};



int main(){

    return 0;
}
