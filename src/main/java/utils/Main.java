package utils;

import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        String port = System.getenv().getOrDefault("PORT", "8080");

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.parseInt(port));
        tomcat.getConnector(); // bắt buộc phải gọi để kích hoạt connector

        tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());

        System.out.println("Tomcat is running on port " + port);
        tomcat.start();
        tomcat.getServer().await();
    }
}
