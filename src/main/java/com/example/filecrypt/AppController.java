package com.example.filecrypt;

import com.example.filecrypt.algorithmes.AES;
import com.example.filecrypt.algorithmes.RSA;
import com.example.filecrypt.utils.CryptoUtils;
import com.example.filecrypt.utils.FileUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.SecretKey;
import java.io.*;
import java.net.URL;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ResourceBundle;


public class AppController implements Initializable {

    FileChooser fileChooser = new FileChooser();

    @FXML
    private ComboBox<String> selectAlgo;

    @FXML
    private ComboBox<String> selectDeAlgo;

    @FXML
    private Label fileName;

    @FXML
    private Label fileNameEn;

    @FXML
    private Label fileKeyName;

    private SecretKey secretKey;

    private byte[] fileBytes;

    private PrivateKey privateKey;

    private byte[] fileBytes2;

    public void uploadUnencryptedFile() throws Exception {
        fileBytes = FileUtils.uploadFile(fileChooser, fileName);
    }

    public void uploadEncryptedFile() throws Exception {
        fileBytes2 = FileUtils.uploadFile(fileChooser, fileNameEn);
    }

    public void saveKey() {
        File file = fileChooser.showSaveDialog(new Stage());
        if (file == null) return;
        String algorithm = selectAlgo.getValue().split("-")[0];
        if (algorithm.equals("AES")) {
            FileUtils.saveObject(file, secretKey);
        } else {
            FileUtils.saveObject(file, privateKey);
        }
    }

    public void uploadKey() {
        File file = fileChooser.showOpenDialog(new Stage());
        fileKeyName.setText(file.getName());
        String algorithm = selectAlgo.getValue().split("-")[0];
        if (algorithm.equals("AES")) {
            secretKey = (SecretKey) FileUtils.uploadObject(file);
        } else {
            privateKey = (PrivateKey) FileUtils.uploadObject(file);
        }

    }

    public void saveDecryptedFile() throws Exception {
        File file = fileChooser.showSaveDialog(new Stage());
        if (file == null) return;
        byte[] decryptedFileBytes = decryption(fileBytes2);
        FileUtils.saveSystem(file, decryptedFileBytes);
    }

    public void saveEncryptedFile() throws Exception {
        File file = fileChooser.showSaveDialog(new Stage());
        if (file == null) return;
        byte[] encryptedFileBytes = encryption(fileBytes);
        FileUtils.saveSystem(file, encryptedFileBytes);
    }


    public byte[] encryption(byte[] fileBytes) throws Exception {
        String[] algorithm = selectAlgo.getValue().split("-");
        int keySize = Integer.parseInt(algorithm[1]);
        if (algorithm[0].equals("AES")) {
            secretKey = CryptoUtils.getAESKey(keySize);
            byte[] iv = CryptoUtils.getRandomNonce(12);
            return AES.encryptWithPrefixIV(fileBytes, secretKey, iv);
        } else {
            KeyPair pair = CryptoUtils.generateKeyPair(keySize);
            PublicKey publicKey = pair.getPublic();
            privateKey = pair.getPrivate();
            return RSA.encrypt(fileBytes, publicKey);
        }
    }

    public byte[] decryption(byte[] fileBytes) throws Exception {
        String[] algorithm = selectDeAlgo.getValue().split("-");
        if (algorithm[0].equals("AES")) {
            return AES.decryptWithPrefixIV(fileBytes, secretKey);
        } else {
            return RSA.decrypt(fileBytes, privateKey);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("AES-128", "AES-192", "AES-256", "RSA-1024", "RSA-2048");
        selectAlgo.setItems(list);
        selectDeAlgo.setItems(list);
    }
}