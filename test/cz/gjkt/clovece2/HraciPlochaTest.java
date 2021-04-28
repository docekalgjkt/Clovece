package cz.gjkt.clovece2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HraciPlochaTest {

    private HraciPlocha hraciPlocha;

    @BeforeEach
    void setUp() {
        HraciPlocha hraciPlocha = new HraciPlocha(3,3,10);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void vytvorHraciPlochu(){
        HraciPlocha hraciPlocha = new HraciPlocha(3,3,10);
        System.out.println(hraciPlocha);
    }

    @Test
    void vratMozneFigurky() {
        HraciPlocha hraciPlocha = new HraciPlocha(3,3,10);
        List<BarvaFigurky> barvyFigurek = hraciPlocha.getBarvyFigurek();
        int hodKostkou = 6;
        List<Figurka> figurky = hraciPlocha.vratMozneFigurky(barvyFigurek.get(0),hodKostkou);
        assertEquals(0, figurky.size());
    }

    @Test
    void nasad() {
        HraciPlocha hraciPlocha = new HraciPlocha(3,3,10);
        for (BarvaFigurky bf : hraciPlocha.getBarvyFigurek()) {
            hraciPlocha.nasad(bf);
        }
        System.out.println(hraciPlocha);
    }

    @Test
    void posunFigurku() {
    }

    @Test
    void testPosunFigurku() {
    }
}