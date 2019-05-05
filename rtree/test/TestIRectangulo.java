import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestIRectangulo {

    IRectangulo rec1; // MBR
    IRectangulo rec2; // Dato
    IRectangulo rec3; // Dato
    IRectangulo rec4; // Dato

    @Before
    public void setUp(){
        rec1= new MBR(0, 0, 0, 10, 10 );
        rec2= new Dato(0, 0, 10, 10);
        rec3= new Dato(11, 0, 3, 3);
        rec4= new Dato(0, 10 , 7, 4);
    }

    @Test
    public void constructTest(){
        assertEquals(rec1.getId(), 0);
        assertEquals(rec1.esDato(), false);
        assertEquals(rec2.getId(), -1);
        assertEquals(rec2.esDato(), true);
    }

    @Test
    public void intersectTest(){
        assertEquals(rec1.intersects(rec2), true);
        assertEquals(rec2.intersects(rec1), true);
        assertEquals(rec1.intersects(rec3), false);
        assertEquals(rec3.intersects(rec1), false);
        assertEquals(rec1.intersects(rec4), true);
    }

    @Test
    public void containsTest(){
        assertEquals(rec1.contains(rec2), true);
        assertEquals(rec1.contains(rec3), false);
    }
}
