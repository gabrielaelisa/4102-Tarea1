import java.io.*;

public class RTree implements Serializable{

    private int idRaiz;
    public string method;
    private int nextId= 0;
    public static final String DIR = "datos" + File.separator;

    public int getIdRaiz(){

        return idRaiz;
    }

    public void setIdRaiz(int idRaiz) {

        this.idRaiz = idRaiz;
    }

    // crea nuevo id disponible para un nuevo archivo
    public int popNextId()
    {

        return nextId++;
    }


}
