#include <list>
#include <string>
#include <bitset>
#include <fstream>
#include <stdio.h>
using namespace std;
std::list<int>::iterator it;
 

/* clase nodo, un nodo almacena hasta M rectangulos*/
class Rectangulo;
class Nodo;

class Nodo{

public: 
//aqui deben ir los constructores
    
    // constructor raiz
    Nodo(char * id, Rectangulo * rec){
        //crea el identificador del archivo que va a almacenar en disco
        es_raiz=true;
        id_archivo= id;
        FILE * f= fopen(id_archivo,"rw");
        fwrite(rec->MBR , 1 , sizeof(rec->MBR) , f );
        fclose(f);

    }
    // constructor nodo normal
    Nodo(char * id, Rectangulo rec, Rectangulo* padre)
    {
        es_raiz=false;
        rect_padre= padre;
        id_archivo= id;

    }
    // aca se debe serializar un nodo a un binario y escribirlo en un archivo
    
    void instert_rectangle(){

    }

private:
    // rectangulo padre
    bool es_raiz;
    Rectangulo * rect_padre;
    char * id_archivo;

};

/* un rectangulo respresenta la MBR del nodo al cual apunta*/
class Rectangulo{
    
public:
    char * MBR;


    Rectangulo(){
    // aqui debe agregar los cuatro vertices al tributo mbr
    
    }
    
private:

    Nodo * nodo_actual;
    Nodo * nodo_hijo; // añadir la opción de tener como hijo una hoja?
    

};
