package io.dorum.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class KeyChainAccess {

    public static String getPasswordFromKeychain(String serviceName) {
        List<String> commands = new ArrayList<>();
        commands.add("security");
        commands.add("find-generic-password");
//        commands.add("-a");
//        commands.add(accountName);
        commands.add("-s");
        commands.add(serviceName);
        commands.add("-w");

        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String password = reader.readLine();
            process.waitFor();
            return password;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
