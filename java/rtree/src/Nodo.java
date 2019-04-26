import java.io.Serializable;

public interface Nodo extends Serializable {

    public boolean isNodoInterno();

    public boolean isNodoHoja();

    public int getId();

    public MBR getMbr();
}
