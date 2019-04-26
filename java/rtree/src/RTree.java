import java.io.*;

public class RTree {

    private int idRaiz;
    private int nextId= 0;
    private String dir;
    private String nombre;

    public RTree(String nombre){
        this.nombre= nombre;
        dir= nombre+"-";
    }

    public void setIdRaiz(int idRaiz) {
        this.idRaiz = idRaiz;
    }

    public int popNextId(){
        return nextId++;
    }

    public Nodo getNodo(int id) throws Exception{
        if(id>=nextId)
            throw new Exception("No existe el nodo");
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(dir + id + ".nodo"));
            return (Nodo) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    public void saveNodo(Nodo n){
        int id= n.getId();
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(dir + id + ".nodo"));
            out.writeObject(n);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void save(){
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(nombre + ".rtree"));
            out.writeObject(this);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    static RTree getRTree(String nombre){
        nombre= "datos" + File.separator + nombre;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(nombre + ".rtree"));
            return (RTree) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

}
