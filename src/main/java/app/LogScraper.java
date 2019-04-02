package app;

import app.kafka.KafkaSender;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author saikat
 * Created on 30/3/2019
 */
public class LogScraper implements Runnable {

    private File dirToScrapForLog;

    public LogScraper(File dirToScrapForLog) {
        this.dirToScrapForLog = dirToScrapForLog;
    }

    @Override
    public void run() {
        try{
            File logFile = new File(dirToScrapForLog.getAbsolutePath()+"/log");
            String content = FileUtils.readFileToString(logFile);
            KafkaSender.sendlogs(content);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
