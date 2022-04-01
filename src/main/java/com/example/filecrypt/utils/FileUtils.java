package com.example.filecrypt.utils;

import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    public static void saveSystem(File file, byte[] content) {
        try (FileOutputStream stream = new FileOutputStream(file)) {
            stream.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] uploadFile(FileChooser fileChooser, Label label) throws IOException {
        File file = fileChooser.showOpenDialog(new Stage());
        label.setText(file.getName());
        return Files.readAllBytes(Path.of(file.getPath()));
    }


    public static void saveObject(File file, Object o) {
        try (FileOutputStream stream = new FileOutputStream(file)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(stream);
            outputStream.writeObject(o);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object uploadObject(File file) {
        try (FileInputStream stream = new FileInputStream(file)) {
            ObjectInputStream inputStream = new ObjectInputStream(stream);
            return inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
