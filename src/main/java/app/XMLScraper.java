package app;

import app.kafka.KafkaSender;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author saikat
 * Created on 30/3/2019
 */
public class XMLScraper implements Runnable {

    private File dirToScrapForXml;

    public XMLScraper(File dirToScrapForXml) {
        this.dirToScrapForXml = dirToScrapForXml;
    }

    @Override
    public void run() {
        try {
            File buildXMl=new File(dirToScrapForXml.getAbsolutePath()+"/build.xml");
            String content = FileUtils.readFileToString(buildXMl);
            System.out.println(content);
            KafkaSender.sendXml(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
