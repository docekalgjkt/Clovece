package cz.gjkt.clovece2;

import cz.gjkt.clovece2.model.BarvaFigurky;
import cz.gjkt.clovece2.model.StartovniDomecek;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StartovniDomecekTest {


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void nasadFigurku() {
    }

    @Test
    void vratFigurku() {
        StartovniDomecek startovniDomecek = new StartovniDomecek(new BarvaFigurky(1, 0, 10), 3);
        startovniDomecek.vratFigurku();
    }

    @Test
    void jePrazdny() {
    }

    @Test
    void zbyvaFigurek() {
    }

    @Test
    void vytvorDomecek() {
        StartovniDomecek startovniDomecek = new StartovniDomecek(new BarvaFigurky(1, 0, 10), 3);
        System.out.println(startovniDomecek);
        assertEquals("|1|1|1|", startovniDomecek.toString());
    }
}