package io.aharoj;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class VoltageLevelAnalyzerTest 
{
    
    @Test
    public void criticalVoltageLevelShouldReturnTrue() {
        VoltageLevelAnalyzer voltageLevelAnalyzer = new VoltageLevelAnalyzer(40);
    
        assertTrue(voltageLevelAnalyzer.isDangerous(45));
    }

    @Test
    public void nonCriticalVoltageLevelShouldReturnFalse(){
        VoltageLevelAnalyzer voltageLevelAnalyzer = new VoltageLevelAnalyzer(50);

        assertFalse(voltageLevelAnalyzer.isDangerous(45));
    }


    @Test
    public void criticalVoltageLowerLimitShouldReturnTrue() {
        VoltageLevelAnalyzer voltageLevelAnalyzer = new VoltageLevelAnalyzer(60);
    
        assertTrue(voltageLevelAnalyzer.isDangerous(60));
    }


    // this increased line coverage +1 and +1 mutation coverage
    @Test
    public void criticalVoltageLowerLimitReturnedFromGetterShouldReturnTrue() {
        VoltageLevelAnalyzer voltageLevelAnalyzer = new VoltageLevelAnalyzer(45);
    
        assertTrue(voltageLevelAnalyzer.isDangerous(voltageLevelAnalyzer.getDangerousVoltageLowerLimit()));
    }



    // THIS one cleared and killed all the mutations 
    @Test
    public void defaultVoltageLowerLimitShouldWork() 
    {
        VoltageLevelAnalyzer voltageLevelAnalyzer = new VoltageLevelAnalyzer();
    
        assertTrue(VoltageLevelAnalyzer.DANGEROUS_VOLTAGE_DEFAULT_LOWER_LIMIT == voltageLevelAnalyzer.getDangerousVoltageLowerLimit());
        assertTrue(voltageLevelAnalyzer.isDangerous(voltageLevelAnalyzer.getDangerousVoltageLowerLimit()));
        assertTrue(voltageLevelAnalyzer.isDangerous(voltageLevelAnalyzer.getDangerousVoltageLowerLimit() + 1));
        assertFalse(voltageLevelAnalyzer.isDangerous(voltageLevelAnalyzer.getDangerousVoltageLowerLimit() - 1));
    }        

}
