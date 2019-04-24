#include <string>
#include <list> 
using namespace std;

class Nodo {

public:
    //ATRIBUTOS

    // constructor clase nodo, recibe cuatro vertices posicionados en el espacio 
    Nodo(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4);

    // constructor que deserializa una linea de un archivo 
    Nodo(string line);
    
    // funcion que serializa un nodo
    string to_line();

private:
    //llave del nodo
    int x1, y1, x2, y2, x3, y3, x4, y4;
    # puntero a hijos
    list<Nodo> childs;
    
};