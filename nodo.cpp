#include <list>
#include <string>
using namespace std;

/* clase nodo, un nodo almacena hasta M rectangulos*/
class Rectangulo;
class Nodo;

class Nodo{
public: 
//aqui deben ir los constructores
    Nodo();
    // aca se debe serializar un nodo a un binario y escribirlo en un archivo
    string to_string();

private:
    // rectangulo padre
    Rectangulo * rect_padre;
    // lista de rectangulos hijos
    list<Rectangulo> rect_hijos;

};

/* un rectangulo respresenta la MBR del nodo al cual apunta*/
class Rectangulo{
public:

    //constructor para deserializar una linea de un archivo .txt 
    Rectangulo(){
    // aqui debe agregar los cuatro vertices al tributo mbr
    
    }
private:

    Nodo * nodo_actual;
    Nodo * nodo_hijo; // añadir la opción de tener como hijo una hoja?
    list<int> MBR;

};



int main(){

    return 0;
}
