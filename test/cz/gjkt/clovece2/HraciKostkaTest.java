package cz.gjkt.clovece2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HraciKostkaTest {

    @Test
    void hod() {
        HraciKostka kostka = new HraciKostka(12);
        int hod = kostka.hod();
        System.out.println(hod);
        assertTrue((hod>0)&&(hod<=12), "počet stěn 12 hod 1");
        kostka = new HraciKostka();
        hod = kostka.hod();
        System.out.println(hod);
        assertTrue((hod>0)&&(hod<=6), "počet stěn 6 hod 1");
        hod = kostka.hod();
        System.out.println(hod);
        assertTrue((hod>0)&&(hod<=6), "počet stěn 6 hod 2");
        hod = kostka.hod();
        System.out.println(hod);
        assertTrue((hod>0)&&(hod<=6), "počet stěn 6 hod 3");
    }
}