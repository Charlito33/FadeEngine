package fr.charlito33.fadeengine.sdk;

import fr.charlito33.fadeengine.sdk.fsl.FSLScript;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public class ResourcesManager {
    public static File getResource(String path) {
        URL url = ResourcesManager.class.getClassLoader().getResource(path);
        if (url == null) {
            return null;
        }
        File file = new File(url.getPath());
        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }

    public static FSLScript getScript(String path) {
        File file = getResource(path);

        if (file == null) {
            System.err.println("File doesn't exists !");
            return null;
        }
        if (!file.canRead()) {
            System.err.println("File can't be read !");
            return null;
        }
        if (!file.getName().endsWith(".fsl")) {
            System.err.println("File is not a script !");
            return null;
        }

        StringBuilder script = new StringBuilder();

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                script.append(scanner.nextLine()).append("\n");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Can't read Script !");
            e.printStackTrace();
        }

        return new FSLScript(script.toString());
    }
}
