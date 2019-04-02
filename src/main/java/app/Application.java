package app;

import org.apache.commons.io.filefilter.AgeFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.util.*;
/**
 * @author saikat
 * Created on 30/3/2019
 */
public class Application {

    public static void main(String[] args) {
        System.out.println("Running Jenkins Exporter");
        String jenkinsHome= System.getenv("JENKINS_HOME");
       //String jenkinsHome = "/home/jenkins_home";
        if(jenkinsHome!=null && !"".equals(jenkinsHome.trim())) {
            System.out.println("Exporting from jenkins home : "+jenkinsHome);
            File jobsDirectory = new File(jenkinsHome + "/jobs");
            FileFilter filter = new AgeFileFilter(yesterday(), false);
            File[] fa = jobsDirectory.listFiles(filter);
            List<File> updatedBuilds = new ArrayList<File>(100);
            Arrays.asList(fa).stream().filter(dir-> !((File)dir).getName().equals("export-xml-log")).forEach(dir -> {
                File buildsDir = new File(dir.getAbsolutePath() + "/builds");
                updatedBuilds.addAll(Arrays.asList(buildsDir.listFiles(filter)));
            });
            JenkinsDataExporter.exportJenkinsData(updatedBuilds);
        }
        else{
            System.out.println("Jenkins Home not set");
        }
    }

    private static Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
}
