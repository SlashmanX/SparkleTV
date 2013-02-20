package com.teamsparkle.sparkletv;

import java.io.File;
import org.apache.log4j.Level;
import android.os.Environment;
import de.mindpipe.android.logging.log4j.LogConfigurator;

/*
log4j.rootLogger=DEBUG, CONSOLE
log4j.appender.CONSOLE=org.apache.log4j.ConsoleAppender
log4j.appender.CONSOLE.layout=com.omertron.tvrageapi.tools.FilteringLayout
#log4j.appender.CONSOLE.layout=org.apache.log4j.PatternLayout
log4j.appender.CONSOLE.layout.ConversionPattern=[TVRage API-%C{1}] %m%n
#log4j.appender.CONSOLE.Threshold=DEBUG
log4j.appender.CONSOLE.Encoding=UTF-8

*/
public class ConfigureLog4J {
    public static void configure() {
        final LogConfigurator logConfigurator = new LogConfigurator();
                
        logConfigurator.setRootLevel(Level.DEBUG);
        logConfigurator.setFilePattern("[TVRage API-%C{1}] %m%n");
        // Set log level of a specific logger
        logConfigurator.setLevel("org.apache", Level.ERROR);
        logConfigurator.configure();
    }
}
