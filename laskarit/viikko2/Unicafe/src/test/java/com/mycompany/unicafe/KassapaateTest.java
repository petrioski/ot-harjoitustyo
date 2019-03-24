/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author karhunko
 */
public class KassapaateTest {
    Kassapaate kassa;
    Maksukortti kortti;
    Maksukortti eiSaldoa;
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(10);
        eiSaldoa = new Maksukortti(0);
    }
    
    
    @Test
    public void kassaPerustuuOikein() {
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void syoEdullisestiLisaaOikein() {
        int vanhaSaldo = kassa.kassassaRahaa();
        kassa.syoEdullisesti(240);
        int uusiSaldo = kassa.kassassaRahaa();
        int erotus = uusiSaldo - vanhaSaldo;
        assertEquals(240, erotus);
    }
    
    @Test
    public void syoEdullisestiPalauttaaOikeinVaihtorahat() {
        int maksu = 280;
        
        int vanhaSaldo = kassa.kassassaRahaa();
        int palautus = kassa.syoEdullisesti(maksu);
        int uusiSaldo = kassa.kassassaRahaa();
        int erotus = uusiSaldo - vanhaSaldo;
        assertEquals(240, erotus);
        assertEquals(40, palautus);
    }
    
    @Test
    public void syoEdullisestiPalauttaaOikeinMaksun() {
        int maksu = 180;
        
        int vanhaSaldo = kassa.kassassaRahaa();
        int palautus = kassa.syoEdullisesti(maksu);
        int uusiSaldo = kassa.kassassaRahaa();
        int erotus = uusiSaldo - vanhaSaldo;
        assertEquals(0, erotus);
        assertEquals(maksu, palautus);
    }
    
    @Test
    public void syoMaukkaastiLisaaOikein() {
        int vanhaSaldo = kassa.kassassaRahaa();
        kassa.syoMaukkaasti(400);
        int uusiSaldo = kassa.kassassaRahaa();
        int erotus = uusiSaldo - vanhaSaldo;
        assertEquals(400, erotus);
    }
    
    @Test
    public void syoMaukkaastiPalauttaaOikeinVaihtorahat() {
        int maksu = 480;
        
        int vanhaSaldo = kassa.kassassaRahaa();
        int palautus = kassa.syoMaukkaasti(maksu);
        int uusiSaldo = kassa.kassassaRahaa();
        int erotus = uusiSaldo - vanhaSaldo;
        assertEquals(400, erotus);
        assertEquals(80, palautus);
    }
    
    @Test
    public void syoMaukkaastiPalauttaaOikeinMaksun() {
        int maksu = 180;
        
        int vanhaSaldo = kassa.kassassaRahaa();
        int palautus = kassa.syoMaukkaasti(maksu);
        int uusiSaldo = kassa.kassassaRahaa();
        int erotus = uusiSaldo - vanhaSaldo;
        assertEquals(0, erotus);
        assertEquals(maksu, palautus);
    }
    
    @Test
    public void edullisiaLounaitaMyyty() {
        assertEquals(0,  kassa.edullisiaLounaitaMyyty());
        kassa.syoEdullisesti(400);
        kassa.syoEdullisesti(400);
        assertEquals(2,  kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukkaitaLounaitaMyyty() {
        assertEquals(0,  kassa.maukkaitaLounaitaMyyty());
        kassa.syoMaukkaasti(400);
        kassa.syoMaukkaasti(400);
        assertEquals(2,  kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test 
    public void korttiToimiiEdullisissa() {
        assertTrue("kortilla oli rahaa", kassa.syoEdullisesti(kortti));
    }
    
    @Test
    public void kassaPalauttaaFalseKunEiSaldoaEdulliselle() {
        assertFalse("kortilla ei ollut rahaa", kassa.syoEdullisesti(eiSaldoa));
    }
    
    @Test
    public void korttiToimiiMaukkaissa() {
        assertTrue("kortilla oli rahaa", kassa.syoMaukkaasti(kortti));
    }
    
    @Test
    public void kassaPalauttaaFalseKunEiSaldoaMaukkaalle() {
        assertFalse("kortilla ei ollut rahaa", kassa.syoMaukkaasti(eiSaldoa));
    }
    
    @Test
    public void kassaLataaSaldoaOikein() {        
        int ladattavaMaara = 1000;
        int vanhaSaldo = kassa.kassassaRahaa();
        kassa.lataaRahaaKortille(kortti, ladattavaMaara);
        int uusiSaldo = kassa.kassassaRahaa();
        int erotus = uusiSaldo - vanhaSaldo;
        assertEquals(-ladattavaMaara, erotus);
    }
    
    @Test
    public void kassaLataaSaldoaOikeinNegRahalla() {        
        int ladattavaMaara = -10;
        int vanhaSaldo = kassa.kassassaRahaa();
        kassa.lataaRahaaKortille(kortti, ladattavaMaara);
        int uusiSaldo = kassa.kassassaRahaa();
        int erotus = uusiSaldo - vanhaSaldo;
        assertEquals(0, erotus);
    }
}
