package app;

import org.apache.commons.io.filefilter.AgeFileFilter;

import java.io.*;
import java.util.*;
/**
 * @author saikat
 * Created on 30/3/2019
 */
public class Application {

    public static void main(String[] args) throws IOException {
        System.out.println("Running Jenkins Exporter");
        String jenkinsHome= System.getenv("JENKINS_HOME");
       //String jenkinsHome = "/home/jenkins_home";
        if(jenkinsHome!=null && !"".equals(jenkinsHome.trim())) {
            System.out.println("Exporting from jenkins home : "+jenkinsHome);
            Properties properties = new Properties();
            String lastBuiltOn = "";
           if(new File(jenkinsHome+"/lastexportrun.properties").exists()){
               InputStream input = new FileInputStream(jenkinsHome+"/lastexportrun.properties");
               properties.load(input);
               lastBuiltOn = properties.get("lastbuild.run.time").toString();
            }
            if(lastBuiltOn==null || "".equals(lastBuiltOn)){
                //File file= new File(jenkinsHome+/"lastexportrun.properties");
                lastBuiltOn = new Date().getTime()+"";
                Writer fileWriter = new FileWriter(jenkinsHome+"/lastexportrun.properties", false);
                fileWriter.write("lastbuild.run.time="+lastBuiltOn);
                fileWriter.flush();
                File jobsDirectory = new File(jenkinsHome + "/jobs");
                File[] fa = jobsDirectory.listFiles();
                List<File> updatedBuilds = new ArrayList<File>(100);
                Arrays.asList(fa).stream().filter(dir-> !((File)dir).getName().equals("export-xml-log")).forEach(dir -> {
                    File buildsDir = new File(dir.getAbsolutePath() + "/builds");
                    updatedBuilds.addAll(Arrays.asList(buildsDir.listFiles()));
                });
                JenkinsDataExporter.exportJenkinsData(updatedBuilds);
            }
            else{
                Writer fileWriter = new FileWriter(jenkinsHome+"/lastexportrun.properties", false);
                fileWriter.write("lastbuild.run.time="+new Date().getTime());
                fileWriter.flush();
                System.out.println("Last Built on : "+lastBuiltOn);
                FileFilter filter =  new AgeFileFilter(new Date(Long.valueOf(lastBuiltOn)), false);
                File jobsDirectory = new File(jenkinsHome + "/jobs");
                File[] fa = jobsDirectory.listFiles(filter);
                List<File> updatedBuilds = new ArrayList<File>(100);
                Arrays.asList(fa).stream().filter(dir-> !((File)dir).getName().equals("export-xml-log")).forEach(dir -> {
                    File buildsDir = new File(dir.getAbsolutePath() + "/builds");
                    updatedBuilds.addAll(Arrays.asList(buildsDir.listFiles(filter)));
                });
                JenkinsDataExporter.exportJenkinsData(updatedBuilds);
            }



        }
        else{
            System.out.println("Jenkins Home not set");
        }
    }


}
