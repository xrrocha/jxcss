package org.plenix.jxcss;

import org.testng.annotations.Test;

public class RunMain {
    @Test
    public void runMan() {
        Main.main("-c", "-p", "batik", "src/site/jxcss.css", "target/jxcss.xml");
    }
}
