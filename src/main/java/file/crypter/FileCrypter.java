package file.crypter;

import crypt.BlowfishEncrypter;
import model.FileFX;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * User: Adrien
 * Date: 02/07/14
 * Time: 12:01
 */
public class FileCrypter {
    /**
     * @param f
     * @param writingDir
     * @param pass
     */
    private static final Logger LOGGER = LogManager.getLogger(FileCrypter.class);

    public static void writeData(FileFX f, File writingDir, String pass) {
        if (!writingDir.exists()) {
            writingDir.mkdirs();
        }
        if (f.getFile().isDirectory()) {
            f.setIsProcessed(Boolean.TRUE);
//            for(File childFile : f.listFiles()){
//                writeData(childFile,new File(writingDir.getAbsolutePath()+File.separator+f.getName()),pass);
//            }
        } else {
            if(Boolean.TRUE.equals(f.getIsProcessed())){
                return;
            }
            Path path = f.getFile().toPath();
            try {

                BlowfishEncrypter blowfishEncrypter = new BlowfishEncrypter(pass);

                byte[] data = Files.readAllBytes(path);
                byte[] encrypted = blowfishEncrypter.encrypt(data);
                File dir = new File(writingDir.getAbsolutePath() + f.getParentPath() + File.separator);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File newFile = new File(writingDir.getAbsolutePath() + File.separator + f.getParentPath() + File.separator + f.getFileName());
                if (newFile.exists()) {

                }
                LOGGER.info("creating " + newFile.getAbsolutePath());
                newFile.createNewFile();
                Files.write(Paths.get(newFile.getAbsolutePath()), encrypted);

            } catch (IOException e) {
                LOGGER.error("Error while writing data", e);
            }
        }

    }

    /**
     * @param f
     * @param writingDir
     * @param pass
     */
    public static void writeDataUncrypt(FileFX f, File writingDir, String pass) {
        if (!writingDir.exists()) {
            writingDir.mkdirs();
        }
        if (f.getFile().isDirectory()) {
            f.setIsProcessed(Boolean.TRUE);

        } else {
            if(Boolean.TRUE.equals(f.getIsProcessed())){
                return;
            }
            Path path = f.getFile().toPath();
            try {

                BlowfishEncrypter blowfishEncrypter = new BlowfishEncrypter(pass);
                File dir = new File(writingDir.getAbsolutePath() + f.getParentPath() + File.separator);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                byte[] data = Files.readAllBytes(path);
                byte[] decrypted = blowfishEncrypter.decrypt(data);
                File newFile = new File(writingDir.getAbsolutePath() + File.separator + f.getParentPath() + File.separator + f.getFileName());
                if (newFile.exists()) {

                }
                LOGGER.info("creating " + newFile.getAbsolutePath());
                newFile.createNewFile();

                Files.write(Paths.get(newFile.getAbsolutePath()), decrypted);

            } catch (IOException e) {
                LOGGER.error("Error while writing data", e);
            }
        }
    }


}


