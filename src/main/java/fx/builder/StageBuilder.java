package fx.builder;

import file.crypter.FileCrypter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.FileFX;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Adrien
 * Date: 02/07/14
 * Time: 11:42
 * To change this template use File | Settings | File Templates.
 */
public final class StageBuilder {

    private final Stage stage;
    private GridPane grid;
    private TextArea logs;
    private TextField directoryToEncrypt;
    private TextField copyDirectory;
    private Stage popEncrypt;
    private Stage decryptPopUp;
    private PasswordField passwordField;
    private RadioButton encrypt;
    private RadioButton decrypt;
    TableView<FileFX> table = new TableView<FileFX>();
    /**
     * @param stage
     */
    public StageBuilder(Stage stage) {
        this.stage = stage;
        this.stage.setTitle("FicleCrypter");
        stage.setWidth(900);
        addGridPane();
    }

    /**
     * @return
     */
    private StageBuilder addGridPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        this.grid = grid;
        return this;
    }

    /**
     * @return
     */
    public StageBuilder addLogTextArea() {
        TextArea logs = new TextArea();
        logs.setScrollTop(60);
        logs.setPrefRowCount(1000);
        grid.add(logs, 1, 3);
        this.logs = logs;
        return this;
    }


    public StageBuilder addDirectoryToEncryptLabel() {
        Label userName = new Label("Files to encrypt directory:");
        grid.add(userName, 0, 1);
        return this;
    }

    public StageBuilder addDirectoryToEncrypt() {

        TableColumn<FileFX,String> fileName = new TableColumn<>();
        TableColumn<FileFX,String> processed = new TableColumn<>();
        fileName.setText("Files");
        processed.setText("isProcessed");
        fileName.setCellValueFactory(
                new PropertyValueFactory<FileFX, String>("fileName")
        );
        processed.setCellValueFactory(
                new PropertyValueFactory<FileFX, String>("isProcessed")

        );
        table.getColumns().add(fileName);
        table.getColumns().add(processed);
        grid.add(table,1,1);
        return this;
    }

    public StageBuilder addCopyDirectoryLabel() {
        Label userName = new Label("Copy File to:");
        grid.add(userName, 0, 2);
        return this;
    }

    public StageBuilder addCopyDirectoryField() {
        final TextField copyDirectoy = new TextField();
//        directory.setMaxWidth(200);
        grid.add(copyDirectoy, 1, 2);
        this.copyDirectory = copyDirectoy;
        return this;
    }

    public StageBuilder addFileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        Button valid = new Button();
        valid.setText("Choose file");
        valid.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                List<File> files = fileChooser.showOpenMultipleDialog(stage);
                ObservableList<FileFX> list = FXCollections.observableArrayList();

                        files.forEach((file) -> {
                            FileFX fileFX = new FileFX(file);
                            list.add(fileFX);

                        });
                table.setItems(list);
            }
        });
        grid.add(valid,3,1);
        return this;
    }

    public StageBuilder addRadioButtonEncryptDecrypt() {
        final ToggleGroup group = new ToggleGroup();

        RadioButton rb1 = new RadioButton("Decrypt");
        rb1.setToggleGroup(group);
        rb1.setSelected(true);

        RadioButton rb2 = new RadioButton("Encrypt");
        rb2.setToggleGroup(group);

        decrypt = rb1;

        encrypt = rb2;

        VBox vbox = new VBox(8);
        vbox.getChildren().addAll(rb1, rb2);
        grid.add(vbox, 2, 1);
        return this;
    }

    public StageBuilder addPopUpEncrypt() {
        final Stage popup = new Stage();
        popup.initModality(Modality.WINDOW_MODAL);
        final PasswordField field = new PasswordField();
        popEncrypt = popup;
        passwordField = field;
        Button valid = new Button();
        valid.setText("Start");
        valid.setDefaultButton(false);


        valid.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                File writing = new File(copyDirectory.getText());
                try {
                    writing.mkdirs();
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }


                table.getItems().forEach(
                        (file) -> {

                                try {
                                    FileCrypter.writeData(file.getFile(), writing, field.getText());
                                    logs.appendText(file.getFile().getName() + "OK\n");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    logs.appendText(file.getFile().getName() + "ERROR\n");
                                    logs.appendText("échec de l'écriture " +file.getFile().getName());
                                }
                        }

                );

                logs.appendText(" encrypting ok \n");
                popup.close();

            }
        });
        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(25, 25, 25, 25));

        Label label = new Label("Password: ");
        hbox.getChildren().addAll(label, field, valid);
        Scene scene = new Scene(hbox);

        popup.setScene(scene);

        return this;
    }

    public StageBuilder addGoButton() {
        Button go = new Button();
        go.setText("Go");
        go.setDefaultButton(true);
        go.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (encrypt.isSelected()) {
                    popEncrypt.show();
                } else if (decrypt.isSelected()) {
                    decryptPopUp.show();
                }

            }
        });
        grid.add(go, 6, 1);
        return this;
    }


    public StageBuilder addDEcryptPopUp() {
//        final Popup popup = new Popup(); popup.setX(300); popup.setY(200);

        final Stage popup = new Stage();
        popup.initModality(Modality.WINDOW_MODAL);


        HBox box = new HBox(10);
        box.setPadding(new Insets(25, 25, 25, 25));

        final PasswordField field = new PasswordField();

        decryptPopUp = popup;
        Button valid = new Button();
        valid.setText("Start");
        valid.setDefaultButton(false);


        valid.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                File writing = new File(copyDirectory.getText());
                try {
                    writing.mkdirs();
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }


                table.getItems().forEach(
                        (file) -> {
                            try {
                                FileCrypter.writeDataUncrypt(file.getFile(), writing, field.getText());
                                logs.appendText(file.getFile().getName() + "OK\n");
                            } catch (Exception e) {
                                e.printStackTrace();
                                logs.appendText(file.getFile().getName() + "ERROR\n");
                                logs.appendText("échec de l'écriture " +file.getFile().getName());
                            }
                        }
                );
                logs.appendText(" decrypting ok \n");
                popup.close();

            }
        });

        Label label = new Label("Password");
        box.getChildren().addAll(label, field, valid);


        Scene scene = new Scene(box);

        popup.setScene(scene);

        return this;

    }

    public StageBuilder addDecryptButton() {
        Button decrypt = new Button();
        decrypt.setText("Decrypt");
        decrypt.setDefaultButton(true);
        decrypt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                decryptPopUp.show();
            }
        });
        grid.add(decrypt, 3, 1);
        return this;
    }

    public void finalyseAndShow() {
        Scene scene = new Scene(grid, 700, 275);
        stage.setScene(scene);
        stage.show();
    }
}
