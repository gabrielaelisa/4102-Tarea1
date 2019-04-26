import java.io.*;

public class RTree implements Serializable{

    private int idRaiz;
    private int nextId= 0;
    public static final String DIR = "datos" + File.separator;

    public int getIdRaiz(){
        return idRaiz;
    }

    public void setIdRaiz(int idRaiz) {
        this.idRaiz = idRaiz;
    }

    public int popNextId(){
        return nextId++;
    }


}
