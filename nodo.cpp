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

    fstream archivo;

public: 
//aqui deben ir los constructores
    
    // constructor raiz
    Nodo(char * id, Rectangulo * rec){
        id_archivo= id;
        archivo.open(id_archivo, ios::binary | ios::ate);
        archivo << rec->MBR;
        archivo.close;


    }
    // constructor nodo normal
    Nodo(char * id, Rectangulo rec, Rectangulo* padre)
    {
        rect_padre= padre;
        id_archivo= id;

    }
    // aca se debe serializar un nodo a un binario y escribirlo en un archivo
    
    void instert_rectangle(){

    }

private:
    // rectangulo padre
    Rectangulo * rect_padre;
    // lista de rectangulos hijos
    list<Rectangulo> rect_hijos;
    char * id_archivo;

};

/* un rectangulo respresenta la MBR del nodo al cual apunta*/
class Rectangulo{
    
public:
    char * MBR;

    //constructor para deserializar una linea de un archivo .txt 
    Rectangulo(){
    // aqui debe agregar los cuatro vertices al tributo mbr
    
    }
    /*
    string serialize(){
        string serialized; 
        for (it= MBR.begin(); it!= MBR.end(); it++){
            string mys= to_string(*it);
            serialized.append(mys);
            if(it!= MBR.end()){
                serialized.append(" ");
            }
            
        }
    }*/
private:

    Nodo * nodo_actual;
    Nodo * nodo_hijo; // añadir la opción de tener como hijo una hoja?
    //list<int> MBR;
    

};
