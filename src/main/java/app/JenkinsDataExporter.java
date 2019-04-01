package app;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author saikat
 * Created on 30/3/2019
 */
public class JenkinsDataExporter {

    private static ExecutorService executor= Executors.newFixedThreadPool(10);

    public static void exportJenkinsData(List<File> buildsDirectory){
        buildsDirectory.parallelStream().forEach(dir->{
            executor.submit(new LogScraper(dir));
            executor.submit(new XMLScraper(dir));
        });
    }

}
