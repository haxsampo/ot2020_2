package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {
    
    Kassapaate kassa;

    @Before
    public void setUp() {
        kassa = new Kassapaate();
    }
    
    @Test
    public void konstruktoriTest() {
        assertEquals(1000, kassa.kassassaRahaa()/100);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void edullinen() {
        int vaihtoraha = kassa.syoEdullisesti(750);
        assertEquals(100240 , kassa.kassassaRahaa());
        assertEquals(510, vaihtoraha);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukas() {
        int vaihtoraha = kassa.syoMaukkaasti(1000);
        assertEquals(100400,kassa.kassassaRahaa());
        assertEquals(600, vaihtoraha);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void riittamatonMaukas() {
        int vaihtoraha = kassa.syoMaukkaasti(390);
        assertEquals(100000,kassa.kassassaRahaa());
        assertEquals(390, vaihtoraha);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void riittamatonEdullinen() {
        int vaihtoraha = kassa.syoEdullisesti(230);
        assertEquals(100000,kassa.kassassaRahaa());
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
        assertEquals(230, vaihtoraha);
    }
    
    @Test
    public void korttiostoEdullinen() {
        Maksukortti kortti = new Maksukortti(1000);
        boolean osto = kassa.syoEdullisesti(kortti);
        assertEquals(true, osto);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
        assertEquals(760, kortti.saldo());
        assertEquals(1000, kassa.kassassaRahaa()/100);
    }
    
    @Test
    public void korttiostoEdullinen2() {
        Maksukortti kortti = new Maksukortti(230);
        boolean osto = kassa.syoEdullisesti(kortti);
        assertEquals(false, osto);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
        assertEquals(230, kortti.saldo());
        assertEquals(1000, kassa.kassassaRahaa()/100);
    }
    
    @Test
    public void korttiostoMaukas() {
        Maksukortti kortti = new Maksukortti(1000);
        boolean osto = kassa.syoMaukkaasti(kortti);
        assertEquals(true, osto);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
        assertEquals(600, kortti.saldo());
        assertEquals(1000, kassa.kassassaRahaa()/100);
    }
    
    @Test
    public void korttiostoMaukas2() {
        Maksukortti kortti = new Maksukortti(300);
        boolean osto = kassa.syoMaukkaasti(kortti);
        assertEquals(false, osto);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
        assertEquals(300, kortti.saldo());
        assertEquals(1000, kassa.kassassaRahaa()/100);
    }
    
    @Test
    public void kortinLataus() {
        Maksukortti kortti = new Maksukortti(300);
        kassa.lataaRahaaKortille(kortti, 1000);
        assertEquals(101000,kassa.kassassaRahaa());
        assertEquals(1300,kortti.saldo());
    }
    
    @Test
    public void kortinLataus2() {
        Maksukortti kortti = new Maksukortti(300);
        kassa.lataaRahaaKortille(kortti, -1000);
        assertEquals(100000,kassa.kassassaRahaa());
        assertEquals(300,kortti.saldo());
    }
}
