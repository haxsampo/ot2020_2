package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoOikeinLuodessa() {
        assertEquals("saldo: 0.10",kortti.toString());
    }
    
    @Test
    public void lataaminenToimii() {
        kortti.lataaRahaa(33);
        assertEquals("saldo: 0.43",kortti.toString());
        kortti.lataaRahaa(100);
        assertEquals("saldo: 1.43",kortti.toString());
    }
    
    @Test
    public void ottaminenTarpeeksi() {
        kortti.otaRahaa(7);
        System.out.println(kortti.toString());
        assertEquals("saldo: 0.03", kortti.toString());
    }
    
    @Test
    public void ottaminenLiikaa() {
        kortti.otaRahaa(20);
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void ottaminenReturn() {
        assertEquals(kortti.otaRahaa(5), true);
        assertEquals(kortti.otaRahaa(20), false);
    }
    
    @Test
    public void saldo() {
        assertEquals(10,kortti.saldo());
    }
}
