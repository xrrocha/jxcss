package org.plenix.jxcss;

import org.testng.annotations.Test;

public class RunMain {
    @Test
    public void runMain() {
        Main.main("-c", "-p", "batik", "src/main/docs/css/jxcss.css", "target/jxcss.xml");
    }
}
