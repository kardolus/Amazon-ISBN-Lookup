package us.kardol.amazon.utils;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Guillermo Kardolus
 */
public class TimeStampTest {
    /**
     * Test of convert method, of class TimeStamp.
     */
    @Test
    public void testConvert() {
        System.out.println("Testing TimeStamp.convert");
        
        Date d = new Date(1449862511021L);
        TimeStamp instance = new TimeStamp();
        String expResult = "2015-12-11T19:35:11Z";
        String result = instance.convert(d);
        
        System.out.print("Test 1. ");
        assertEquals(expResult, result);
        System.out.println("... ok");
    }
    
}
