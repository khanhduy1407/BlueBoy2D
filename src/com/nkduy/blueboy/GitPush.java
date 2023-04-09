package com.nkduy.blueboy;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class GitPush {

    public static GameInfo gameInfo;

    public static void main(String[] args) throws IOException, InterruptedException {
        gameInfo = new GameInfo();

        File gitDir = new File(".");

        String addCommand = "git add .";
        String commitCommand = "git commit -m \"build: v" + gameInfo.version + "\"";
        String pushCommand = "git push";

        ProcessBuilder builder = new ProcessBuilder();
        builder.directory(gitDir);
        builder.command("cmd.exe", "/c", addCommand);
        Process process = builder.start();
        process.waitFor();

        builder.command("cmd.exe", "/c", commitCommand);
        process = builder.start();
        process.waitFor();

        builder.command("cmd.exe", "/c", pushCommand);
        process = builder.start();
        process.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}
