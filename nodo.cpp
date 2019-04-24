#include <string>
#include <list> 
using namespace std;

class Nodo {

public:

    // constructor que deserializa una linea de un archivo 
    Nodo(string line);
    
    // funcion que serializa un nodo
    string to_line();

private:
    //llave del nodo
    int x1, y1, x2, y2, x3, y3, x4, y4;
     //puntero a hijos
    list<Nodo> childs;
    
};
class Rectangulo {
public:
    Nodo nodo_actual;
    Nodo hijo; // añadir la opción de tener como hijo una hoja?
    list<int> MBR;

    //constructor
    Rectangulo(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4){
        
    }
        //agregar los cuatro vertices a la lista de puntos
    
}