package file.crypter;

import crypt.BlowfishEncrypter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created with IntelliJ IDEA.
 * User: Adrien
 * Date: 02/07/14
 * Time: 12:01
 * To change thi | ezrzerizjrzfjsdkfnxnvxF
 */
public class FileCrypter {
    /**
     *
     * @param f
     * @param writingDir
     * @param pass
     */
    public static void writeData(File f, File writingDir , String pass){
        if(f.isDirectory()) {
            for(File childFile : f.listFiles()){
                writeData(childFile,new File(writingDir.getAbsolutePath()+File.separator+f.getName()),pass);
            }
        }
        Path path = f.toPath();
        try {

            BlowfishEncrypter blowfishEncrypter = new BlowfishEncrypter(pass);

            byte[] data = Files.readAllBytes(path);
            byte[] encrypted = blowfishEncrypter.encrypt(data);
            File newFile = new File(writingDir.getAbsoluteFile()+File.separator+f.getName());
            Files.write(Paths.get(newFile.getAbsolutePath()),encrypted);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param f
     * @param writingDir
     * @param pass
     */
    public static void writeDataUncrypt(File f, File writingDir , String pass){
        if(f.isDirectory()) {
         for(File childFile : f.listFiles()){
             writeDataUncrypt(childFile,new File(writingDir.getAbsolutePath()+File.separator+f.getName()),pass);
         }
        }
        Path path = f.toPath();
        try {

            BlowfishEncrypter blowfishEncrypter = new BlowfishEncrypter(pass);

            byte[] data = Files.readAllBytes(path);
            byte[] encrypted = blowfishEncrypter.decrypt(data);
            File newFile = new File(writingDir.getAbsoluteFile()+File.separator+f.getName());
            Files.write(Paths.get(newFile.getAbsolutePath()),encrypted);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}


