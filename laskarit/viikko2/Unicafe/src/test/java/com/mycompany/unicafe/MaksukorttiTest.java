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
    public void saldoOnOikein() {
        String vastaus = kortti.toString();
        assertEquals("saldo: 10.0", vastaus);
    }
    
    @Test
    public void saldonLisaysToimii() {
        kortti.lataaRahaa(10);
        String vastaus = kortti.toString();
        assertEquals("saldo: 20.0", vastaus);
    }
    
    @Test
    public void saldoVaheneeOikein() {
        kortti.otaRahaa(540);
        String vastaus = kortti.toString();
        assertEquals("saldo: 4.60", vastaus);
    }
    
    @Test
    public void saldoEiVaheneJosEiKatetta() {
        kortti.otaRahaa(2000);
        String vastaus = kortti.toString();
        assertEquals("saldo: 10.0", vastaus);
    }
    
    @Test 
    public void riittavaKatePalauttaaTrue() {        
        assertTrue("saldoa riittävästi", kortti.otaRahaa(100));
        assertEquals(900, kortti.saldo());
    }
    
    @Test 
    public void liianPieniSaldoPalauttaaFalse() {
        assertFalse("saldoa riittävästi", kortti.otaRahaa(2000));
        assertEquals(1000, kortti.saldo());
    }
    
}
