import fx.builder.StageBuilder;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created with IntelliJ IDEA.
 * User: Adrien
 * Date: 30/06/14
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
public class Start extends Application {

    public static void main(String[] args){
      launch(args);

    }

    @Override
    public void start(final Stage stage) throws Exception {
        StageBuilder stageBuilder = new StageBuilder(stage);
        stageBuilder.addLogTextArea();
        stageBuilder.addDirectoryToEncryptLabel().addDirectoryToEncrypt().addCopyDirectoryLabel().addCopyDirectoryField();
        stageBuilder.addPopUpEncrypt()
                .addRadioButtonEncryptDecrypt().addGoButton().addDEcryptPopUp().addFileChooser().finalyseAndShow();




    }



}
