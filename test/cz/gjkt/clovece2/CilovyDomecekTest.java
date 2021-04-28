package cz.gjkt.clovece2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CilovyDomecekTest {

    @Test
    void testToString() {
        BarvaFigurky bf = new BarvaFigurky(1,0,50);
        CilovyDomecek cd = new CilovyDomecek(4, bf);
        assertEquals("||0|0|0|0", cd.toString(), "Test metody toString.");
        cd.vlozFigurku(new Figurka(bf),0);
        System.out.println(cd);
        assertEquals("||1|0|0|0", cd.toString(), "Test metody toString.");
        cd.vlozFigurku(new Figurka(bf),1);
        System.out.println(cd);
        assertEquals("||1|1|0|0", cd.toString(), "Test metody toString.");
        cd.vlozFigurku(new Figurka(bf),3);
        System.out.println(cd);
        assertEquals("||1|1|0|1", cd.toString(), "Test metody toString.");
    }

    @Test
    void jeVolno() {
        BarvaFigurky bf = new BarvaFigurky(1,0,50);
        CilovyDomecek cd = new CilovyDomecek(3, bf);
        for(int i = 0; i < 2; i++){cd.vlozFigurku(new Figurka(bf),i);}
        System.out.println(cd);
        assertFalse(cd.jeVolno(0));
        assertFalse(cd.jeVolno(1));
        assertTrue(cd.jeVolno(2));
        cd.vlozFigurku(new Figurka(bf), 2);
        System.out.println(cd);
        assertFalse(cd.jeVolno(2));

    }

    @Test
    void vlozFigurku() {
        BarvaFigurky bf = new BarvaFigurky(1,0,50);
        CilovyDomecek cd = new CilovyDomecek(4, bf);
        BarvaFigurky bf2 = new BarvaFigurky(10,0,50);
        System.out.println(cd);
    }

    @Test
    void jePlno() {
        BarvaFigurky bf = new BarvaFigurky(1,0,50);
        CilovyDomecek cd = new CilovyDomecek(3, bf);
        for(int i = 0; i < 2; i++){cd.vlozFigurku(new Figurka(bf),i);}
        System.out.println(cd);
        assertFalse(cd.jePlno());
        cd.vlozFigurku(new Figurka(bf), 2);
        System.out.println(cd);
        assertTrue(cd.jePlno());
    }

    @Test
    void posunO() {
    }

    @Test
    void jePohyb() {
    }

    @Test
    void getFigurkyKPohybu() {
    }
}