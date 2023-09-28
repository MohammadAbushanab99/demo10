package com.example.demo10;

public class RunningContainer {

    public  static  void startAllContainers(){

        try {
            String[] command = {"docker-compose", "up", "-d"};
            Process process = new ProcessBuilder(command).start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Containers started successfully.");
            } else {
                System.err.println("Failed to start containers.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
