package com.soccer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FixtureEngineTest {

    File file;

    @Before
    public void setup(){
        ClassLoader classLoader = getClass().getClassLoader();
        file = new File(classLoader.getResource("input.txt").getFile());
    }

    @Test
    public void shouldProcessFixtureResultFromFile()throws Exception{
        Assert.assertTrue(file.exists());
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file) , "UTF8"));
        String str;
        while (( str = in.readLine() ) != null ) {
            FixtureEngine fixtureEngine = new FixtureEngine();
            fixtureEngine.processResultPerLine(str);
            Assert.assertEquals(2,fixtureEngine.teamsThatPlayed.size());
            Assert.assertEquals(2,fixtureEngine.getScores().size());
        }

    }

    @Test
    public void shouldRecordPoints(){
        String sampleLineText = "Tarantulas 3, Snakes 1";
       FixtureEngine fixtureEngine = new FixtureEngine();
       fixtureEngine.processResultPerLine(sampleLineText);
       fixtureEngine.recordPoints();
       Assert.assertEquals(2,fixtureEngine.getTeamsThatPlayed().size());
       Assert.assertEquals("Tarantulas",fixtureEngine.getTeamsThatPlayed().get(0).getName());
    }
}
