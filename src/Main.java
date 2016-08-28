import Algorithm.image.bmp.read.Read;
import Algorithm.image.bmp.write.Write;
import Algorithm.bin_morf.*;
import Algorithm.bin_oper_logicz.LogicalNegation;
import Algorithm.bin_oper_logicz.LogicalProduct;
import Algorithm.bin_oper_logicz.LogicalSum;
import Algorithm.bin_oper_logicz.LogicalXOR;
import Algorithm.color_oper_arytm.*;
import Algorithm.gray_oper_arytm.*;
import Algorithm.gray_oper_arytm.GrayImagesSum;
import Application.*;
import Algorithm.colour_histogram.*;
import Algorithm.filters.*;
import Algorithm.gray_hist.*;
import Algorithm.gray_morf.*;
import Algorithm.oper_geometr.*;
import Algorithm.image.bmp.BMPFile;
import javafx.application.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.*;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Krzysztof Macioszek
 */

public class Main extends Application {

    final ImageView iv1 = new ImageView();
    Image image;
    BufferedImage bufferedImage;
    final ScrollPane sp = new ScrollPane();
    private File file;
    Stage pS;
    ListHistoryWindow listHistoryWindow;

    public BMPFile bmpFile;

    Read read;
    Write write;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.pS = primaryStage;
        this.image = new Image(Main.class.getResourceAsStream(""));
        this.bufferedImage = SwingFXUtils.fromFXImage(image, null);
        iv1.setImage(image);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        sp.setContent(iv1);

        pS.setTitle("Wprowadzenie Przetwarzania Obrazów");

        listHistoryWindow = new ListHistoryWindow();

        VBox root = new VBox(5);
        HBox hbImage = new HBox();

        root.getChildren().addAll(new MyMenuBar());

        hbImage.getChildren().add(sp);
        root.getChildren().add(hbImage);

        Scene scene = new Scene(root, 800, 500, Color.BLANCHEDALMOND);
        pS.setScene(scene);

        primaryStage.show();

        pS.setOnCloseRequest(event -> {
            Platform.exit();
        });
    }

    public class ListHistoryWindow {

        List<History> historyList = new ArrayList<>();
        ListView<History> listView;
        ObservableList<History> observableList;
        Stage stage;
        int i = 0;

        public ListHistoryWindow() {
            listView = new ListView<>();
            observableList = FXCollections.observableList(historyList);
            listView.setItems(observableList);

            listView.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    History selectedItem = listView.getSelectionModel().getSelectedItem();
                    if (event.getClickCount() == 2) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You want back to " + selectedItem.getId() + " " + selectedItem.getDescription() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                        alert.showAndWait();

                        if (alert.getResult() == ButtonType.YES) {
                            bufferedImage = selectedItem.getBufferedImage();
                            iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));

                            deleteHistoryFrom(selectedItem);
                        }
                    }
                }
            });

            listView.setCellFactory(new Callback<ListView<History>, ListCell<History>>() {
                @Override
                public ListCell<History> call(ListView<History> param) {
                    ListCell<History> cell = new ListCell<History>(){
                        @Override
                        protected void updateItem(History t, boolean bln) {
                            super.updateItem(t, bln);
                            if (t != null) {
                                setText(t.getId() + " " + t.getDescription());
                            }
                        }

                    };

                    return cell;
                }
            });

            setWindow();
        }

        private void deleteHistoryFrom(History selectedItem) {
//            int size = historyList.size();
//            for (int i = 0; i < size; i++)
//                if (selectedItem.getId() < historyList.get(i).getId())
//                    historyList.remove(i);
//
//
//            listView.setItems(FXCollections.observableArrayList());
//            listView.setItems(observableList);
        }

        public void setWindow() {
            VBox layout = new VBox();
            Scene secondScene = new Scene(layout, 300, 300);

            HBox hBox = new HBox();
            hBox.getChildren().addAll(listView);
            layout.getChildren().addAll(hBox);

            secondScene.setRoot(layout);

            stage = new Stage();
            stage.setTitle("History");
            stage.setScene(secondScene);

            stage.setX(pS.getX() + pS.getWidth() - 400);
            stage.setY(pS.getY() + pS.getHeight() - 300);

            stage.show();
        }

        public void addToHistoryList(BufferedImage bufferedImage, String description) {
            History history = new History(bufferedImage, description, i++);
            historyList.add(history);

            listView.setItems(FXCollections.observableArrayList());
            listView.setItems(observableList);
        }

        public Stage getStage() {
            return stage;
        }
    }

    public void addToHistory(BufferedImage bufferedImage, String description) {
        listHistoryWindow.addToHistoryList(bufferedImage, description);

    }

    public class MyMenuBar extends MenuBar {

        MenuFile menuFile = new MenuFile("File");
        MenuLogBinary menuBinLog = new MenuLogBinary("Binary logical operations");
        MenuGrayArytm menuGrayArytm = new MenuGrayArytm("Gray Arythmetics");
        MenuColorArytm menuColorArytm = new MenuColorArytm("Color Arythmetics");
        MenuGeometric menuGeometric = new MenuGeometric("Geometric");
        MenuGrayHistogram menuGrayHistogram = new MenuGrayHistogram("Gray Histogram");
        MenuRGBHistogram menuRGBHistogram = new MenuRGBHistogram("RGB Histogram");
        MenuBinaryMorphology menuBinaryMorphology = new MenuBinaryMorphology("Binary Morphology");
        MenuGrayMorphology menuGrayMorphology = new MenuGrayMorphology("Gray Morphology");
        MenuFilters menuFilters = new MenuFilters("Filters");
        Menu menuOptions = setMenuOptions();

        public MyMenuBar() {
            this.getMenus().addAll(menuFile, menuBinLog, menuGrayArytm, menuColorArytm, menuGeometric, menuGrayHistogram, menuRGBHistogram, menuBinaryMorphology, menuGrayMorphology, menuFilters, menuOptions);
        }

        public Menu setMenuOptions() {
            Menu menuOptions = new Menu("Options");

            MenuItem history = new MenuItem("History", null);
            history.setOnAction(event -> {
                if (!listHistoryWindow.getStage().isShowing())
                    listHistoryWindow.setWindow();
            });
            menuOptions.getItems().add(history);

            return menuOptions;
        }

    }

    public class MenuFile extends MyMenu {

        MenuItem openBMP = setOpenBMP("Open BMP", null),
            saveAsBMP = setSaveBMP("Save As BMP", null),
            exit = setExit("Exit", null);

        public MenuFile(String name) {
            super(name);

            this.getItems().addAll(openBMP, saveAsBMP, exit);
        }

        public MenuItem setOpenBMP(String name, ImageView imageView) {
            MenuItem open = new MenuItem(name, imageView);

            open.setOnAction(event -> {
                FileChooser fileChooser = new FileChooser();


                //Set to user directory or go to default if cannot access
                String userDirectoryString = System.getProperty("user.home");
                File userDirectory = new File(userDirectoryString);
                if(!userDirectory.canRead()) {
                    userDirectory = new File("c:/");
                }
                fileChooser.setInitialDirectory(userDirectory);

                file = fileChooser.showOpenDialog(pS);

                if (file != null) {
                    String sDir = file.toURI().getPath();
                    DataInputStream dis = null;
                    try {
                        dis = new DataInputStream(new FileInputStream(sDir));

                        read = new Read(dis);
                        bmpFile = read.getBMP();
                        iv1.setImage(SwingFXUtils.toFXImage(bmpFile.getBufferedImage(), null));
                        addToHistory(bmpFile.getBufferedImage(), "New BMP image");

                        bufferedImage = bmpFile.getBufferedImage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            return open;
        }


        public MenuItem setSaveBMP(String name, ImageView imageView) {
            MenuItem save = new MenuItem(name, imageView);

            save.setOnAction(event -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp")
                );
                fileChooser.setInitialDirectory(file.getParentFile());

                File file = fileChooser.showSaveDialog(pS);
                if (file != null && bmpFile != null) {
                    String sDir = file.toURI().getPath();

                    Write write = new Write();

                    try {
                        write.write(bmpFile, file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    addToHistory(bufferedImage, "Save image");
                }
            });

            return save;
        }

        public MenuItem setExit(String name, ImageView imageView) {
            MenuItem exit = new MenuItem(name, imageView);

            exit.setOnAction(event -> {
                Platform.exit();
                System.exit(0);
            });

            return exit;
        }
    }

    public class MenuGeometric extends MyMenu {

        MenuItem menuAspectRatioScale = setAspectRatioScale("Aspect Ratio Scale", null),
                menuScale = setScale("Scale", null),
                menuRotate = setRotate("Rotate", null);
        Menu menuSymmetry = new Menu("Symmetry");

        public MenuGeometric(String name) {
            super(name);

            menuSymmetry.getItems().addAll(setSymmetry(0, "OX", null), setSymmetry(1, "OY", null), setSymmetry(2, "OXOY", null));

            this.getItems().addAll(menuAspectRatioScale, menuScale, menuRotate, menuSymmetry);
        }

        public MenuItem setAspectRatioScale(String name, ImageView imageView) {
            MenuItem menuItem = new MenuItem(name, imageView);

            menuItem.setOnAction(event -> {
                VBox layout = new VBox();
                Scene scene = new Scene(layout, 150, 110);

                TextField txtWidth = new TextField();
                TextField txtHeight = new TextField();
                txtHeight.setEditable(false);

                txtWidth.setText(Integer.toString(bufferedImage.getWidth()));
                txtHeight.setText(Integer.toString(bufferedImage.getHeight()));

                AspectRatioScale ars = new AspectRatioScale(bufferedImage);

                // Width
                Label labelWidth = new Label("Width");
                layout.getChildren().add(labelWidth);
                txtWidth.textProperty().addListener(((observable, oldValue, newValue) -> {
                    ars.calculate(Integer.parseInt(newValue), Integer.parseInt(txtHeight.getText()));

                    txtHeight.setText(Integer.toString(ars.getNewHeight()));
                    System.out.println(ars.getNewWidth() + "x" + ars.getNewHeight());
                }));
                layout.getChildren().add(txtWidth);

                // Height
                Label labelHeight = new Label("Height");
                layout.getChildren().add(labelHeight);
                layout.getChildren().add(txtHeight);

                Button btnResize = new Button();
                btnResize.setText("Resize");

                btnResize.setOnAction(event1 -> {
                    ResizeImage resizeImage = new ResizeImage(bufferedImage, Integer.parseInt(txtWidth.getText()), Integer.parseInt(txtHeight.getText()));
                    bufferedImage = resizeImage.resize();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    scene.getWindow().hide();
                    addToHistory(bufferedImage, "Resize");
                });
                layout.getChildren().add(btnResize);

                scene.setRoot(layout);

                Stage stage = new Stage();
                stage.setTitle("Set Scale");
                stage.setScene(scene);

                stage.show();
            });

            return menuItem;
        }

        public MenuItem setScale(String name, ImageView imageView) {
            MenuItem menuItem = new MenuItem(name, imageView);

            menuItem.setOnAction(event -> {
                VBox layout = new VBox();
                Scene secondScene = new Scene(layout, 150, 110);

                TextField txtWidth = new TextField();
                TextField txtHeight = new TextField();

                txtWidth.setText(Integer.toString(bufferedImage.getWidth()));
                txtHeight.setText(Integer.toString(bufferedImage.getHeight()));

                // Width
                Label labelWidth = new Label("Width");
                layout.getChildren().add(labelWidth);
                layout.getChildren().add(txtWidth);

                // Height
                Label labelHeight = new Label("Height");
                layout.getChildren().add(labelHeight);
                layout.getChildren().add(txtHeight);

                Button btnResize = new Button();
                btnResize.setText("Resize");

                btnResize.setOnAction(event1 -> {
                    ResizeImage resizeImage = new ResizeImage(bufferedImage, Integer.parseInt(txtWidth.getText()), Integer.parseInt(txtHeight.getText()));
                    bufferedImage = resizeImage.resize();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    secondScene.getWindow().hide();
                    addToHistory(bufferedImage, "Scale");
                });
                layout.getChildren().add(btnResize);

                secondScene.setRoot(layout);
                Stage secondStage = new Stage();
                secondStage.setTitle("Set Scale");
                secondStage.setScene(secondScene);

                secondStage.show();
            });

            return menuItem;
        }

        public MenuItem setRotate(String name, ImageView imageView) {
            MenuItem menuRotate = new MenuItem(name, imageView);

            menuRotate.setOnAction(event -> {
                VBox layout = new VBox();
                Scene secondScene = new Scene(layout, 150, 110);

                TextField txtRotate = new TextField();

                txtRotate.setText("0");

                // Angle
                Label labelRotate = new Label("Angle");
                layout.getChildren().add(labelRotate);
                layout.getChildren().add(txtRotate);

                Button btnRotate = new Button();
                btnRotate.setText("Rotate");

                btnRotate.setOnAction(event1 -> {
                    RotateImage rotateImage = new RotateImage(bufferedImage, Integer.parseInt(txtRotate.getText()));
                    bufferedImage = rotateImage.rotate();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    secondScene.getWindow().hide();
                    addToHistory(bufferedImage, "Rotate");
                });
                layout.getChildren().add(btnRotate);

                secondScene.setRoot(layout);
                Stage secondStage = new Stage();
                secondStage.setTitle("Set Scale");
                secondStage.setScene(secondScene);

                secondStage.show();
            });

            return menuRotate;
        }

        public MenuItem setSymmetry(int axis, String name, ImageView imageView) {
            MenuItem menuSymmetry = new MenuItem(name, imageView);

            menuSymmetry.setOnAction(event -> {
                SymmetryImage symmetryImage = new SymmetryImage(bufferedImage);
                switch (axis) {
                    case 0:
                        symmetryImage.symmetryImageOX();
                        break;
                    case 1:
                        symmetryImage.symmetryImageOY();
                        break;
                    case 2:
                        symmetryImage.symmetryImageOXOY();
                        break;
                }
                bufferedImage = symmetryImage.getSymmetryImage();
                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                addToHistory(bufferedImage, "Symmetry");
            });

            return menuSymmetry;
        }
    }

    public class MenuGrayHistogram extends MyMenu {

        MenuItem menuHistogram = setHistogram("Histogram", null),
                menuEqualization = setOperation(0, "Equalization", null),
                menuStretching = setOperation(1, "Stretching", null);

        public MenuGrayHistogram(String name) {
            super(name);

            this.getItems().addAll(menuHistogram, menuEqualization, menuStretching);
        }

        public MenuItem setHistogram(String name, ImageView imageView) {
            MenuItem menuHistogram = new MenuItem(name, imageView);

            menuHistogram.setOnAction(event -> {
                VBox layout = new VBox();

                final CategoryAxis xAxis = new CategoryAxis();
                final NumberAxis yAxis = new NumberAxis();
                final LineChart<String, Number> chartHistogram = new LineChart<>(xAxis, yAxis);
                chartHistogram.setCreateSymbols(false);

                GrayHistogram grayHistogram = new GrayHistogram(bufferedImage);
                grayHistogram.histogram(bufferedImage);
                chartHistogram.getData().addAll(
                        grayHistogram.getSeriesGray()
                );

                chartHistogram.setStyle(".default-color0.chart-series-line { -fx-stroke: #b1b0af; }");

                HBox hBox = new HBox();
                hBox.getChildren().addAll(chartHistogram);
                layout.getChildren().addAll(hBox);

                Scene secondScene = new Scene(layout, chartHistogram.getPrefWidth(), chartHistogram.getPrefHeight());
                secondScene.setRoot(layout);

                Stage secondStage = new Stage();
                secondStage.setTitle("Gray Histogram");
                secondStage.setScene(secondScene);

                secondStage.show();
                addToHistory(bufferedImage, "Gray Histogram");
            });

            return menuHistogram;
        }

        public MenuItem setOperation(int operation, String name, ImageView imageView) {
            MenuItem menuHistogram = new MenuItem(name, imageView);

            menuHistogram.setOnAction(event -> {
                VBox layout = new VBox();

                final CategoryAxis xAxis = new CategoryAxis();
                final NumberAxis yAxis = new NumberAxis();
                final LineChart<String, Number> chartHistogram = new LineChart<>(xAxis, yAxis);
                chartHistogram.setCreateSymbols(false);

                GrayHistogram grayHistogram = new GrayHistogram(bufferedImage);
                switch (operation) {
                    case 0:
                        bufferedImage = grayHistogram.getEqualization();
                        addToHistory(bufferedImage, "Gray Equalization");
                        break;
                    case 1:
                        bufferedImage = grayHistogram.getStretching();
                        addToHistory(bufferedImage, "Gray Stretching");
                        break;
                }

                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                chartHistogram.getData().addAll(
                        grayHistogram.getSeriesGray()
                );

                chartHistogram.setStyle(".default-color0.chart-series-line { -fx-stroke: #b1b0af; }");

                HBox hBox = new HBox();
                hBox.getChildren().addAll(chartHistogram);
                layout.getChildren().addAll(hBox);

                Scene secondScene = new Scene(layout, chartHistogram.getPrefWidth(), chartHistogram.getPrefHeight());
                secondScene.setRoot(layout);

                Stage secondStage = new Stage();
                secondStage.setTitle("Gray Histogram");
                secondStage.setScene(secondScene);

                secondStage.show();
            });

            return menuHistogram;
        }

    }

    public class MenuRGBHistogram extends MyMenu {

        MenuItem menuHistogram = setHistogram("Histogram", null),
                menuEqualization = setOperation(0, "Equalization", null),
                menuStretching = setOperation(1, "Stretching", null),
                menuThreshold = setThreshold("Threshold", null),
                menuOtsuThreshold = setOtsuThreshold("Otsu Threshold", null);

        public MenuRGBHistogram(String name) {
            super(name);

            this.getItems().addAll(menuHistogram, menuEqualization, menuStretching, menuThreshold, menuOtsuThreshold);
        }

        public MenuItem setHistogram(String name, ImageView imageView) {
            MenuItem menuHistogram = new MenuItem(name, imageView);

            menuHistogram.setOnAction(event -> {
                VBox layout = new VBox();


                final CategoryAxis xAxis = new CategoryAxis();
                final NumberAxis yAxis = new NumberAxis();
                final LineChart<String, Number> chartHistogram
                        = new LineChart<>(xAxis, yAxis);
                chartHistogram.setCreateSymbols(false);

                RGBHistogram rgbHistogram = new RGBHistogram(bufferedImage);
                rgbHistogram.histogram(bufferedImage);
                chartHistogram.getData().addAll(
                        rgbHistogram.getSeriesRed(),
                        rgbHistogram.getSeriesGreen(),
                        rgbHistogram.getSeriesBlue()
                );

                chartHistogram.setStyle(".default-color0.chart-series-line { -fx-stroke: #ff0000; }");
                chartHistogram.setStyle(".default-color1.chart-series-line { -fx-stroke: #31ff00; }");
                chartHistogram.setStyle(".default-color2.chart-series-line { -fx-stroke: #0048ff; }");

                HBox hBox = new HBox();
                hBox.getChildren().addAll(chartHistogram);
                layout.getChildren().add(hBox);

                Scene secondScene = new Scene(layout, chartHistogram.getPrefWidth(), chartHistogram.getPrefHeight());
                secondScene.setRoot(layout);

                Stage secondStage = new Stage();
                secondStage.setTitle("RGB Histogram");
                secondStage.setScene(secondScene);

                secondStage.show();
                addToHistory(bufferedImage, "RGB Histogram");
            });

            return menuHistogram;
        }

        public MenuItem setOtsuThreshold(String name, ImageView imageView) {
            MenuItem menuOtsu = new MenuItem(name, imageView);

            menuOtsu.setOnAction(event -> {
                ThresholdHistogram thresholdHistogram = new ThresholdHistogram(bufferedImage);
                thresholdHistogram.otsuThreshold();

                bufferedImage = thresholdHistogram.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));

                addToHistory(bufferedImage, "Otsu Threshold");
            });

            return menuOtsu;
        }

        public MenuItem setThreshold(String name, ImageView imageView) {
            MenuItem menuThreshold= new MenuItem(name, imageView);

            menuThreshold.setOnAction(event -> {
                ThresholdHistogram thresholdHistogram =  new ThresholdHistogram(bufferedImage);

                VBox layout = new VBox();
                Scene secondScene = new Scene(layout, 400, 100);

                Label lblThreshold = new Label("Threshold");

                Slider slider = new Slider();
                slider.setMin(0);
                slider.setMax(255);
                slider.setValue(127);
                slider.setShowTickLabels(true);
                slider.setShowTickMarks(true);
                slider.setMajorTickUnit(17);
                slider.setMinorTickCount(5);
                slider.setBlockIncrement(10);

                Button btnThreshold = new Button();
                btnThreshold.setText("Threshold");

                slider.valueProperty().addListener((observable, oldValue, newValue) -> {
                    thresholdHistogram.threshold(newValue.intValue());
                    iv1.setImage(SwingFXUtils.toFXImage(thresholdHistogram.getTemplateImage(), null));
                });

                layout.getChildren().add(lblThreshold);
                layout.getChildren().add(slider);
//                layout.getChildren().add(btnThreshold);

                secondScene.setRoot(layout);
                Stage secondStage = new Stage();
                secondStage.setTitle("Set Threshold");
                secondStage.setScene(secondScene);

                secondStage.show();

                secondStage.setOnCloseRequest(event1 -> {
                    bufferedImage = thresholdHistogram.getTemplateImage();
                    iv1.setImage(SwingFXUtils.toFXImage(thresholdHistogram.getTemplateImage(), null));
                    addToHistory(bufferedImage, "Threshold");
                });
            });

            return menuThreshold;
        }

        public MenuItem setOperation(int operation, String name, ImageView imageView) {
            MenuItem menuHistogram = new MenuItem(name, imageView);

            menuHistogram.setOnAction(event -> {
                VBox layout = new VBox();

                final CategoryAxis xAxis = new CategoryAxis();
                final NumberAxis yAxis = new NumberAxis();
                final LineChart<String, Number> chartHistogram = new LineChart<>(xAxis, yAxis);
                chartHistogram.setCreateSymbols(false);

                RGBHistogram rgbHistogram = new RGBHistogram(bufferedImage);
                switch (operation) {
                    case 0:
                        bufferedImage = rgbHistogram.getEqualization();
                        addToHistory(bufferedImage, "RGB Equalization");
                        break;
                    case 1:
                        bufferedImage = rgbHistogram.getStretching();
                        addToHistory(bufferedImage, "RGB Stretching");
                        break;
                }

                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                chartHistogram.getData().addAll(
                        rgbHistogram.getSeriesRed(),
                        rgbHistogram.getSeriesGreen(),
                        rgbHistogram.getSeriesBlue()
                );

                chartHistogram.setStyle(".default-color0.chart-series-line { -fx-stroke: #ff0000; }");
                chartHistogram.setStyle(".default-color1.chart-series-line { -fx-stroke: #31ff00; }");
                chartHistogram.setStyle(".default-color2.chart-series-line { -fx-stroke: #0048ff; }");

                HBox hBox = new HBox();
                hBox.getChildren().addAll(chartHistogram);
                layout.getChildren().addAll(hBox);

                Scene secondScene = new Scene(layout, chartHistogram.getPrefWidth(), chartHistogram.getPrefHeight());
                secondScene.setRoot(layout);

                Stage secondStage = new Stage();
                secondStage.setTitle("RGB Histogram");
                secondStage.setScene(secondScene);

                secondStage.show();
            });

            return menuHistogram;
        }

    }

    public class MenuBinaryMorphology extends MyMenu {

        MenuItem erosion = setErosion("Erosion", null),
            dilation = setDilation("Dilation", null),
            opening = setOpening("Opening", null),
            closing = setClosing("Closing", null);

        public MenuBinaryMorphology(String name) {
            super(name);

            this.getItems().addAll(erosion, dilation, opening, closing);
        }

        public MenuItem setErosion(String name, ImageView imageView) {
            MenuItem menuErosion = new MenuItem(name, imageView);

            menuErosion.setOnAction(event -> {
                BinErosion binErosion = new BinErosion(bufferedImage);
                bufferedImage = binErosion.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(binErosion.getTemplateImage(), null));
                addToHistory(bufferedImage, "Binary Erosion");
            });

            return menuErosion;
        }

        public MenuItem setDilation(String name, ImageView imageView) {
            MenuItem menuErosion = new MenuItem(name, imageView);

            menuErosion.setOnAction(event -> {
                BinDilation binDilation = new BinDilation(bufferedImage);
                bufferedImage = binDilation.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(binDilation.getTemplateImage(), null));
                addToHistory(bufferedImage, "Binary Dilation");
            });

            return menuErosion;
        }

        public MenuItem setOpening(String name, ImageView imageView) {
            MenuItem menuErosion = new MenuItem(name, imageView);

            menuErosion.setOnAction(event -> {
                BinOpening binOpening = new BinOpening(bufferedImage);
                bufferedImage = binOpening.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(binOpening.getTemplateImage(), null));
                addToHistory(bufferedImage, "Binary Opening");
            });

            return menuErosion;
        }

        public MenuItem setClosing(String name, ImageView imageView) {
            MenuItem menuErosion = new MenuItem(name, imageView);

            menuErosion.setOnAction(event -> {
                BinClosing binClosing = new BinClosing(bufferedImage);
                bufferedImage = binClosing.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(binClosing.getTemplateImage(), null));
                addToHistory(bufferedImage, "Binary Closing");
            });

            return menuErosion;
        }

    }

    public class MenuGrayMorphology extends MyMenu {

        MenuItem erosion = setErosion("Erosion", null),
                dilation = setDilation("Dilation", null),
                opening = setOpening("Opening", null),
                closing = setClosing("Closing", null);

        public MenuGrayMorphology(String name) {
            super(name);

            this.getItems().addAll(erosion, dilation, opening, closing);
        }

        public MenuItem setErosion(String name, ImageView imageView) {
            MenuItem menuErosion = new MenuItem(name, imageView);

            menuErosion.setOnAction(event -> {
                GrayErosion grayErosion = new GrayErosion(bufferedImage);
                bufferedImage = grayErosion.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(grayErosion.getTemplateImage(), null));
                addToHistory(bufferedImage, "Gray Erosion");
            });

            return menuErosion;
        }

        public MenuItem setDilation(String name, ImageView imageView) {
            MenuItem menuErosion = new MenuItem(name, imageView);

            menuErosion.setOnAction(event -> {
                GrayDilation grayDilation = new GrayDilation(bufferedImage);
                bufferedImage = grayDilation.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(grayDilation.getTemplateImage(), null));
                addToHistory(bufferedImage, "Gray Dilation");
            });

            return menuErosion;
        }

        public MenuItem setOpening(String name, ImageView imageView) {
            MenuItem menuErosion = new MenuItem(name, imageView);

            menuErosion.setOnAction(event -> {
                GrayOpening grayOpening = new GrayOpening(bufferedImage);
                bufferedImage = grayOpening.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(grayOpening.getTemplateImage(), null));
                addToHistory(bufferedImage, "Gray Opening");
            });

            return menuErosion;
        }

        public MenuItem setClosing(String name, ImageView imageView) {
            MenuItem menuErosion = new MenuItem(name, imageView);

            menuErosion.setOnAction(event -> {
                GrayClosing grayClosing = new GrayClosing(bufferedImage);
                bufferedImage = grayClosing.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(grayClosing.getTemplateImage(), null));
                addToHistory(bufferedImage, "Gray Closing");
            });

            return menuErosion;
        }
    }

    public class MenuFilters extends MyMenu {

        Menu lowPass = new Menu("Low Pass", null),
            highPass = new Menu("High Pass", null),
            gradient = new Menu("Gradient", null),
            extreme = new Menu("Extreme", null);
        MenuItem median = setMedian("Median", null);

        public MenuFilters(String name) {
            super(name);

            lowPass.getItems().addAll(setLowPass(0, "Average", null), setLowPass(1, "Square", null), setLowPass(2, "Circular", null),
                    setLowPass(3, "LP 1", null), setLowPass(4, "LP 2", null), setLowPass(5, "LP 3", null), setLowPass(6, "Piramid", null),
                    setLowPass(7, "Conical", null), setLowPass(8, "Gauss 1", null), setLowPass(9, "Gauss 2", null), setLowPass(10, "Gauss 3", null),
                    setLowPass(11, "Gauss 4", null), setLowPass(12, "Gauss 5", null));
            highPass.getItems().addAll(setHighPass(0, "Delete average", null), setHighPass(1, "HP 1", null), setHighPass(2, "HP 2", null),
                    setHighPass(3, "HP 3", null));
            gradient.getItems().addAll(setGradient("East", null), setGradient("South-East", null), setGradient("North-East", null),
                    setGradient("West", null), setGradient("South-West", null), setGradient("North-West", null), setGradient("North", null),
                    setGradient("South", null));
            extreme.getItems().addAll(setExtreme("Minimum", null), setExtreme("Maximum", null));

            this.getItems().addAll(lowPass, highPass, gradient, median, extreme);
        }

        public MenuItem setLowPass(int operation, String name, ImageView imageView) {
            MenuItem menuLowPass = new MenuItem(name, imageView);

            menuLowPass.setOnAction(event -> {
                FilterPass filterPass = new FilterPass(bufferedImage);
                switch (operation) {
                    case 0:
                        filterPass.setArrayMask(FilterList.lowPassAverage());
                        break;
                    case 1:
                        filterPass.setArrayMask(FilterList.lowPassSquare());
                        break;
                    case 2:
                        filterPass.setArrayMask(FilterList.lowPassCircular());
                        break;
                    case 3:
                        filterPass.setArrayMask(FilterList.lowPass1());
                        break;
                    case 4:
                        filterPass.setArrayMask(FilterList.lowPass2());
                        break;
                    case 5:
                        filterPass.setArrayMask(FilterList.lowPass3());
                        break;
                    case 6:
                        filterPass.setArrayMask(FilterList.lowPassPiramid());
                        break;
                    case 7:
                        filterPass.setArrayMask(FilterList.lowPassConical());
                        break;
                    case 8:
                        filterPass.setArrayMask(FilterList.lowPassGauss1());
                        break;
                    case 9:
                        filterPass.setArrayMask(FilterList.lowPassGauss2());
                        break;
                    case 10:
                        filterPass.setArrayMask(FilterList.lowPassGauss3());
                        break;
                    case 11:
                        filterPass.setArrayMask(FilterList.lowPassGauss4());
                        break;
                    case 12:
                        filterPass.setArrayMask(FilterList.lowPassGauss5());
                        break;

                }
                filterPass.run();

                bufferedImage = filterPass.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                addToHistory(bufferedImage, "Filter LowPass " + operation);
            });

            return menuLowPass;
        }

        public MenuItem setHighPass(int operation, String name, ImageView imageView) {
            MenuItem menuHighPass = new MenuItem(name, imageView);

            menuHighPass.setOnAction(event -> {
                FilterPass filterPass = new FilterPass(bufferedImage);
                switch (operation) {
                    case 0:
                        filterPass.setArrayMask(FilterList.highPassDeleteAverage());
                        break;
                    case 1:
                        filterPass.setArrayMask(FilterList.highPass1());
                        break;
                    case 2:
                        filterPass.setArrayMask(FilterList.highPass2());
                        break;
                    case 3:
                        filterPass.setArrayMask(FilterList.highPass3());
                        break;
                }
                filterPass.run();

                bufferedImage = filterPass.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                addToHistory(bufferedImage, "Filter HighPass " + operation);
            });

            return menuHighPass;
        }

        public MenuItem setGradient(String name, ImageView imageView) {
            MenuItem menuHighPass = new MenuItem(name, imageView);

            menuHighPass.setOnAction(event -> {
                FilterPass filterPass = new FilterPass(bufferedImage);
                switch (name) {
                    case "East":
                        filterPass.setArrayMask(FilterList.gradientDirectionalEast());
                        break;
                    case "South-East":
                        filterPass.setArrayMask(FilterList.gradientDirectionalSoutheast());
                        break;
                    case "North-East":
                        filterPass.setArrayMask(FilterList.gradientDirectionalNortheast());
                        break;
                    case "West":
                        filterPass.setArrayMask(FilterList.gradientDirectionalWest());
                        break;
                    case "South-West":
                        filterPass.setArrayMask(FilterList.gradientDirectionalSouthwest());
                        break;
                    case "North-West":
                        filterPass.setArrayMask(FilterList.gradientDirectionalNorthwest());
                        break;
                    case "North":
                        filterPass.setArrayMask(FilterList.gradientDirectionalNorth());
                        break;
                    case "South":
                        filterPass.setArrayMask(FilterList.gradientDirectionalSouth());
                        break;
                }
                filterPass.run();

                bufferedImage = filterPass.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                addToHistory(bufferedImage, "Filter Gradient " + name);
            });

            return menuHighPass;
        }

        public MenuItem setExtreme(String name, ImageView imageView) {
            MenuItem menuExtreme = new MenuItem(name, imageView);

            menuExtreme.setOnAction(event -> {
                switch (name) {
                    case "Minimum":
                        FilterMinimum filterMinimum = new FilterMinimum(bufferedImage);
                        bufferedImage = filterMinimum.getTemplateImage();
                        break;
                    case "Maximum":
                        FilterMaximum filterMaximum = new FilterMaximum(bufferedImage);
                        bufferedImage = filterMaximum.getTemplateImage();
                        break;
                }

                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                addToHistory(bufferedImage, "Filter Extreme " + name);
            });

            return menuExtreme;
        }

        public MenuItem setMedian(String name, ImageView imageView) {
            MenuItem menuGradient = new MenuItem(name, imageView);

            menuGradient.setOnAction(event -> {
                FilterMedian filterMedian = new FilterMedian(bufferedImage);

                bufferedImage = filterMedian.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                addToHistory(bufferedImage, "Filter Median");
            });

            return menuGradient;
        }
    }

    public class MenuLogBinary extends MyMenu {

        MenuItem negation = setNegation("Logical negation", null),
            logicalSum = setLogicalSum("Logical sum", null),
            logicalProduct = setLogicalProduct("Logical product", null),
            logicalXOR = setLogicalXOR("Logical XOR", null);

        public MenuLogBinary(String name) {
            super(name);

            this.getItems().addAll(negation, logicalSum, logicalProduct, logicalXOR);
        }

        public MenuItem setNegation(String name, ImageView imageView) {
            MenuItem menuNegation = new MenuItem(name, imageView);

            menuNegation.setOnAction(event -> {
                LogicalNegation logicalNegation = new LogicalNegation(bufferedImage);

                bufferedImage = logicalNegation.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                addToHistory(bufferedImage, "Binary logicalNegation");
            });

            return menuNegation;
        }

        public MenuItem setLogicalSum(String name, ImageView imageView) {
            MenuItem menuLogicalSum = new MenuItem(name, imageView);

            menuLogicalSum.setOnAction(event -> {
                BufferedImage secondImage = null;

                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(file.getParentFile());

                file = fileChooser.showOpenDialog(pS);
                if (file != null) {
                    Image image1 = new Image(file.toURI().toString());
                    secondImage = SwingFXUtils.fromFXImage(image1, null);

                    LogicalSum logicalSum = new LogicalSum(bufferedImage, secondImage);

                    bufferedImage = logicalSum.getTemplateImage();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    addToHistory(bufferedImage, "Logical sum");
                }
            });

            return menuLogicalSum;
        }

        public MenuItem setLogicalProduct(String name, ImageView imageView) {
            MenuItem menuLogicalProduct = new MenuItem(name, imageView);

            menuLogicalProduct.setOnAction(event -> {
                BufferedImage secondImage = null;

                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(file.getParentFile());

                file = fileChooser.showOpenDialog(pS);
                if (file != null) {
                    Image image1 = new Image(file.toURI().toString());
                    secondImage = SwingFXUtils.fromFXImage(image1, null);

                    LogicalProduct logicalProduct = new LogicalProduct(bufferedImage, secondImage);

                    bufferedImage = logicalProduct.getTemplateImage();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    addToHistory(bufferedImage, "Logical product");
                }

            });

            return menuLogicalProduct;
        }

        public MenuItem setLogicalXOR(String name, ImageView imageView) {
            MenuItem menuXOR = new MenuItem(name, imageView);

            menuXOR.setOnAction(event -> {
                BufferedImage secondImage = null;

                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(file.getParentFile());

                file = fileChooser.showOpenDialog(pS);
                if (file != null) {
                    Image image1 = new Image(file.toURI().toString());
                    secondImage = SwingFXUtils.fromFXImage(image1, null);

                    LogicalXOR logicalXOR = new LogicalXOR(bufferedImage, secondImage);

                    bufferedImage = logicalXOR.getTemplateImage();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    addToHistory(bufferedImage, "Logical XOR");
                }

            });

            return menuXOR;
        }
    }

    public class MenuGrayArytm extends MyMenu {

        MenuItem imagesSum = setImagesSum("Images sum", null),
            imageSumConst = setImageSumWithConst("Image sum with constant", null),
            multiImages = setMultiplicationImages("Images multiplication", null),
            multiImagesConst = setImageMultWithConst("Images multiplication with constant", null),
            imageExp = setImageExponentation("Image exponentation", null),
            imagesDividing = setImageDividing("Image dividing", null),
            imageDivideConst = setImageDivideConst("Image divide with const", null),
            imageRoots = setImageRoots("Image root", null),
            imageLog = setImageLog("Image logarithm", null);

        public MenuGrayArytm(String name) {
            super(name);

            this.getItems().addAll(imagesSum, imageSumConst, multiImages, multiImagesConst, imageExp, imagesDividing,
                    imageDivideConst, imageRoots, imageLog);
        }

        public MenuItem setImagesSum(String name, ImageView imageView) {
            MenuItem menuImagesSum = new MenuItem(name, imageView);

            menuImagesSum.setOnAction(event -> {
                BufferedImage secondImage = null;

                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(file.getParentFile());

                file = fileChooser.showOpenDialog(pS);
                if (file != null) {
                    Image image1 = new Image(file.toURI().toString());
                    secondImage = SwingFXUtils.fromFXImage(image1, null);

                    GrayImagesSum grayImagesSum = new GrayImagesSum(bufferedImage, secondImage);

                    bufferedImage = grayImagesSum.getTemplateImage();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    addToHistory(bufferedImage, "Images sum");
                }
            });

            return menuImagesSum;
        }

        public MenuItem setImageSumWithConst(String name, ImageView imageView) {
            MenuItem menuImagesSum = new MenuItem(name, imageView);

            menuImagesSum.setOnAction(event -> {
                BufferedImage tBufferedImage = bufferedImage;
                VBox layout = new VBox();
                Scene scene = new Scene(layout, 150, 110);

                TextField txtConstant = new TextField();

                txtConstant.setText("0");

                // Constant
                Label labelConstant = new Label("Constant");
                layout.getChildren().add(labelConstant);
                txtConstant.textProperty().addListener(((observable, oldValue, newValue) -> {
//                    GrayImageSumConst isc = new GrayImageSumConst(bufferedImage, Integer.parseInt(newValue));
//                    bufferedImage = isc.getTemplateImage();
//                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                }));
                layout.getChildren().add(txtConstant);

                Button btnCalculate = new Button();
                btnCalculate.setText("Calculate");

                btnCalculate.setOnAction(event1 -> {
                    GrayImageSumConst isc = new GrayImageSumConst(bufferedImage, Integer.parseInt(txtConstant.getText()));
                    bufferedImage = isc.getTemplateImage();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    scene.getWindow().hide();
                    addToHistory(bufferedImage, "Image sum with constant");
                });
                layout.getChildren().add(btnCalculate);

                scene.setRoot(layout);

                Stage stage = new Stage();
                stage.setTitle("Image sum with constant");
                stage.setScene(scene);

                stage.setOnCloseRequest(event1 -> {
                    iv1.setImage(SwingFXUtils.toFXImage(tBufferedImage, null));
                });

                stage.show();
            });

            return menuImagesSum;
        }

        public MenuItem setMultiplicationImages(String name, ImageView imageView) {
            MenuItem menuMultImage = new MenuItem(name, imageView);

            menuMultImage.setOnAction(event -> {
                BufferedImage secondImage = null;

                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(file.getParentFile());

                file = fileChooser.showOpenDialog(pS);
                if (file != null) {
                    Image image1 = new Image(file.toURI().toString());
                    secondImage = SwingFXUtils.fromFXImage(image1, null);

                    GrayImageMultiplication multiImage = new GrayImageMultiplication(bufferedImage, secondImage);

                    bufferedImage = multiImage.getTemplateImage();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    addToHistory(bufferedImage, "Images multiplication");
                }
            });

            return menuMultImage;
        }

        public MenuItem setImageMultWithConst(String name, ImageView imageView) {
            MenuItem menuImagesSum = new MenuItem(name, imageView);

            menuImagesSum.setOnAction(event -> {
                BufferedImage tBufferedImage = bufferedImage;
                VBox layout = new VBox();
                Scene scene = new Scene(layout, 150, 110);

                TextField txtConstant = new TextField();

                txtConstant.setText("0");

                // Constant
                Label labelConstant = new Label("Constant");
                layout.getChildren().add(labelConstant);
                txtConstant.textProperty().addListener(((observable, oldValue, newValue) -> {
//                    GrayImageMultiplicationConst imc = new GrayImageMultiplicationConst(bufferedImage, Integer.parseInt(newValue));
//                    bufferedImage = imc.getTemplateImage();
//                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                }));
                layout.getChildren().add(txtConstant);

                Button btnCalculate = new Button();
                btnCalculate.setText("Calculate");

                btnCalculate.setOnAction(event1 -> {
                    GrayImageMultiplicationConst imc = new GrayImageMultiplicationConst(bufferedImage, Integer.parseInt(txtConstant.getText()));
                    bufferedImage = imc.getTemplateImage();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    scene.getWindow().hide();
                    addToHistory(bufferedImage, "Image multiplication with constant");
                });
                layout.getChildren().add(btnCalculate);

                scene.setRoot(layout);

                Stage stage = new Stage();
                stage.setTitle("Image multiplication with constant");
                stage.setScene(scene);

                stage.setOnCloseRequest(event1 -> {
                    iv1.setImage(SwingFXUtils.toFXImage(tBufferedImage, null));
                });

                stage.show();
            });

            return menuImagesSum;
        }

        public MenuItem setImageExponentation(String name, ImageView imageView) {
            MenuItem menuImagesExp = new MenuItem(name, imageView);

            menuImagesExp.setOnAction(event -> {
                BufferedImage tBufferedImage = bufferedImage;
                VBox layout = new VBox();
                Scene scene = new Scene(layout, 150, 110);

                TextField txtConstant = new TextField();

                txtConstant.setText("0");

                // Constant
                Label labelConstant = new Label("Constant");
                layout.getChildren().add(labelConstant);
                txtConstant.textProperty().addListener(((observable, oldValue, newValue) -> {
//                    GrayImageExponentation ie = new GrayImageExponentation(bufferedImage, Integer.parseInt(newValue));
//                    bufferedImage = ie.getTemplateImage();
//                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                }));
                layout.getChildren().add(txtConstant);

                Button btnCalculate = new Button();
                btnCalculate.setText("Calculate");

                btnCalculate.setOnAction(event1 -> {
                    GrayImageExponentation ie = new GrayImageExponentation(bufferedImage, Integer.parseInt(txtConstant.getText()));
                    bufferedImage = ie.getTemplateImage();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    scene.getWindow().hide();
                    addToHistory(bufferedImage, "Image exponentation with constant");
                });
                layout.getChildren().add(btnCalculate);

                scene.setRoot(layout);

                Stage stage = new Stage();
                stage.setTitle("Image exponentation with constant");
                stage.setScene(scene);

                stage.setOnCloseRequest(event1 -> {
                    iv1.setImage(SwingFXUtils.toFXImage(tBufferedImage, null));
                });

                stage.show();
            });

            return menuImagesExp;
        }

        public MenuItem setImageDividing(String name, ImageView imageView) {
            MenuItem menuDivide = new MenuItem(name, imageView);

            menuDivide.setOnAction(event -> {
                BufferedImage secondImage = null;

                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(file.getParentFile());

                file = fileChooser.showOpenDialog(pS);
                if (file != null) {
                    Image image1 = new Image(file.toURI().toString());
                    secondImage = SwingFXUtils.fromFXImage(image1, null);

                    GrayImagesDividing imageDivide = new GrayImagesDividing(bufferedImage, secondImage);

                    bufferedImage = imageDivide.getTemplateImage();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    addToHistory(bufferedImage, "Images dividing");
                }
            });

            return menuDivide;
        }

        public MenuItem setImageDivideConst(String name, ImageView imageView) {
            MenuItem menuDivide = new MenuItem(name, imageView);

            menuDivide.setOnAction(event -> {
                BufferedImage tBufferedImage = bufferedImage;
                VBox layout = new VBox();
                Scene scene = new Scene(layout, 150, 110);

                TextField txtConstant = new TextField();

                txtConstant.setText("0");

                // Constant
                Label labelConstant = new Label("Constant");
                layout.getChildren().add(labelConstant);
                txtConstant.textProperty().addListener(((observable, oldValue, newValue) -> {
//                    GrayImageDivideConst idc = new GrayImageDivideConst(bufferedImage, Integer.parseInt(newValue));
//                    bufferedImage = idc.getTemplateImage();
//                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                }));
                layout.getChildren().add(txtConstant);

                Button btnCalculate = new Button();
                btnCalculate.setText("Calculate");

                btnCalculate.setOnAction(event1 -> {
                    GrayImageDivideConst idc = new GrayImageDivideConst(bufferedImage, Integer.parseInt(txtConstant.getText()));
                    bufferedImage = idc.getTemplateImage();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    scene.getWindow().hide();
                    addToHistory(bufferedImage, "Image divide with constant");
                });
                layout.getChildren().add(btnCalculate);

                scene.setRoot(layout);

                Stage stage = new Stage();
                stage.setTitle("Image divide with constant");
                stage.setScene(scene);

                stage.setOnCloseRequest(event1 -> {
                    iv1.setImage(SwingFXUtils.toFXImage(tBufferedImage, null));
                });

                stage.show();
            });

            return menuDivide;
        }

        public MenuItem setImageRoots(String name, ImageView imageView) {
            MenuItem menuImageRoots = new MenuItem(name, imageView);

            menuImageRoots.setOnAction(event -> {
                GrayImageRoots grayImageRoots = new GrayImageRoots(bufferedImage);
                bufferedImage = grayImageRoots.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                addToHistory(bufferedImage, "Image Roots");
            });

            return menuImageRoots;
        }

        public MenuItem setImageLog(String name, ImageView imageView) {
            MenuItem menuImageLog = new MenuItem(name, imageView);

            menuImageLog.setOnAction(event -> {
                GrayImageLog grayImageLog = new GrayImageLog(bufferedImage);
                bufferedImage = grayImageLog.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                addToHistory(bufferedImage, "Image Logarithm");
            });

            return menuImageLog;
        }
    }

    public class MenuColorArytm extends MyMenu {

        MenuItem imagesSum = setImagesSum("Images sum", null),
                imageSumConst = setImageSumWithConst("Image sum with constant", null),
                multiImages = setMultiplicationImages("Images multiplication", null),
                multiImagesConst = setImageMultWithConst("Images multiplication with constant", null),
                imageExp = setImageExponentation("Image exponentation", null),
                imagesDividing = setImageDividing("Image dividing", null),
                imageDivideConst = setImageDivideConst("Image divide with const", null),
                imageRoots = setImageRoots("Image root", null),
                imageLog = setImageLog("Image logarithm", null);

        public MenuColorArytm(String name) {
            super(name);

            this.getItems().addAll(imagesSum, imageSumConst, multiImages, multiImagesConst, imageExp, imagesDividing,
                    imageDivideConst, imageRoots, imageLog);
        }

        public MenuItem setImagesSum(String name, ImageView imageView) {
            MenuItem menuImagesSum = new MenuItem(name, imageView);

            menuImagesSum.setOnAction(event -> {
                BufferedImage secondImage = null;

                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(file.getParentFile());

                file = fileChooser.showOpenDialog(pS);
                if (file != null) {
                    Image image1 = new Image(file.toURI().toString());
                    secondImage = SwingFXUtils.fromFXImage(image1, null);

                    ColorImagesSum colorImagesSum = new ColorImagesSum(bufferedImage, secondImage);

                    bufferedImage = colorImagesSum.getTemplateImage();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    addToHistory(bufferedImage, "Images sum");
                }
            });

            return menuImagesSum;
        }

        public MenuItem setImageSumWithConst(String name, ImageView imageView) {
            MenuItem menuImagesSum = new MenuItem(name, imageView);

            menuImagesSum.setOnAction(event -> {
                BufferedImage tBufferedImage = bufferedImage;
                VBox layout = new VBox();
                Scene scene = new Scene(layout, 150, 110);

                TextField txtConstant = new TextField();

                txtConstant.setText("0");

                // Constant
                Label labelConstant = new Label("Constant");
                layout.getChildren().add(labelConstant);
                txtConstant.textProperty().addListener(((observable, oldValue, newValue) -> {
//                    GrayImageSumConst isc = new GrayImageSumConst(bufferedImage, Integer.parseInt(newValue));
//                    bufferedImage = isc.getTemplateImage();
//                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                }));
                layout.getChildren().add(txtConstant);

                Button btnCalculate = new Button();
                btnCalculate.setText("Calculate");

                btnCalculate.setOnAction(event1 -> {
                    ColorImageSumConst isc = new ColorImageSumConst(bufferedImage, Integer.parseInt(txtConstant.getText()));
                    bufferedImage = isc.getTemplateImage();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    scene.getWindow().hide();
                    addToHistory(bufferedImage, "Image sum with constant");
                });
                layout.getChildren().add(btnCalculate);

                scene.setRoot(layout);

                Stage stage = new Stage();
                stage.setTitle("Image sum with constant");
                stage.setScene(scene);

                stage.setOnCloseRequest(event1 -> {
                    iv1.setImage(SwingFXUtils.toFXImage(tBufferedImage, null));
                });

                stage.show();
            });

            return menuImagesSum;
        }

        public MenuItem setMultiplicationImages(String name, ImageView imageView) {
            MenuItem menuMultImage = new MenuItem(name, imageView);

            menuMultImage.setOnAction(event -> {
                BufferedImage secondImage = null;

                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(file.getParentFile());

                file = fileChooser.showOpenDialog(pS);
                if (file != null) {
                    Image image1 = new Image(file.toURI().toString());
                    secondImage = SwingFXUtils.fromFXImage(image1, null);

                    ColorImageMultiplication multiImage = new ColorImageMultiplication(bufferedImage, secondImage);

                    bufferedImage = multiImage.getTemplateImage();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    addToHistory(bufferedImage, "Images multiplication");
                }
            });

            return menuMultImage;
        }

        public MenuItem setImageMultWithConst(String name, ImageView imageView) {
            MenuItem menuImagesSum = new MenuItem(name, imageView);

            menuImagesSum.setOnAction(event -> {
                BufferedImage tBufferedImage = bufferedImage;
                VBox layout = new VBox();
                Scene scene = new Scene(layout, 150, 110);

                TextField txtConstant = new TextField();

                txtConstant.setText("0");

                // Constant
                Label labelConstant = new Label("Constant");
                layout.getChildren().add(labelConstant);
                txtConstant.textProperty().addListener(((observable, oldValue, newValue) -> {
//                    GrayImageMultiplicationConst imc = new GrayImageMultiplicationConst(bufferedImage, Integer.parseInt(newValue));
//                    bufferedImage = imc.getTemplateImage();
//                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                }));
                layout.getChildren().add(txtConstant);

                Button btnCalculate = new Button();
                btnCalculate.setText("Calculate");

                btnCalculate.setOnAction(event1 -> {
                    ColorImageMultiplicationConst imc = new ColorImageMultiplicationConst(bufferedImage, Integer.parseInt(txtConstant.getText()));
                    bufferedImage = imc.getTemplateImage();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    scene.getWindow().hide();
                    addToHistory(bufferedImage, "Image multiplication with constant");
                });
                layout.getChildren().add(btnCalculate);

                scene.setRoot(layout);

                Stage stage = new Stage();
                stage.setTitle("Image multiplication with constant");
                stage.setScene(scene);

                stage.setOnCloseRequest(event1 -> {
                    iv1.setImage(SwingFXUtils.toFXImage(tBufferedImage, null));
                });

                stage.show();
            });

            return menuImagesSum;
        }

        public MenuItem setImageExponentation(String name, ImageView imageView) {
            MenuItem menuImagesExp = new MenuItem(name, imageView);

            menuImagesExp.setOnAction(event -> {
                BufferedImage tBufferedImage = bufferedImage;
                VBox layout = new VBox();
                Scene scene = new Scene(layout, 150, 110);

                TextField txtConstant = new TextField();

                txtConstant.setText("0");

                // Constant
                Label labelConstant = new Label("Constant");
                layout.getChildren().add(labelConstant);
                txtConstant.textProperty().addListener(((observable, oldValue, newValue) -> {
//                    GrayImageExponentation ie = new GrayImageExponentation(bufferedImage, Integer.parseInt(newValue));
//                    bufferedImage = ie.getTemplateImage();
//                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                }));
                layout.getChildren().add(txtConstant);

                Button btnCalculate = new Button();
                btnCalculate.setText("Calculate");

                btnCalculate.setOnAction(event1 -> {
                    ColorImageExponentation ie = new ColorImageExponentation(bufferedImage, Integer.parseInt(txtConstant.getText()));
                    bufferedImage = ie.getTemplateImage();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    scene.getWindow().hide();
                    addToHistory(bufferedImage, "Image exponentation with constant");
                });
                layout.getChildren().add(btnCalculate);

                scene.setRoot(layout);

                Stage stage = new Stage();
                stage.setTitle("Image exponentation with constant");
                stage.setScene(scene);

                stage.setOnCloseRequest(event1 -> {
                    iv1.setImage(SwingFXUtils.toFXImage(tBufferedImage, null));
                });

                stage.show();
            });

            return menuImagesExp;
        }

        public MenuItem setImageDividing(String name, ImageView imageView) {
            MenuItem menuDivide = new MenuItem(name, imageView);

            menuDivide.setOnAction(event -> {
                BufferedImage secondImage = null;

                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(file.getParentFile());

                file = fileChooser.showOpenDialog(pS);
                if (file != null) {
                    Image image1 = new Image(file.toURI().toString());
                    secondImage = SwingFXUtils.fromFXImage(image1, null);

                    ColorImagesDividing imageDivide = new ColorImagesDividing(bufferedImage, secondImage);

                    bufferedImage = imageDivide.getTemplateImage();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    addToHistory(bufferedImage, "Images dividing");
                }
            });

            return menuDivide;
        }

        public MenuItem setImageDivideConst(String name, ImageView imageView) {
            MenuItem menuDivide = new MenuItem(name, imageView);

            menuDivide.setOnAction(event -> {
                BufferedImage tBufferedImage = bufferedImage;
                VBox layout = new VBox();
                Scene scene = new Scene(layout, 150, 110);

                TextField txtConstant = new TextField();

                txtConstant.setText("0");

                // Constant
                Label labelConstant = new Label("Constant");
                layout.getChildren().add(labelConstant);
                txtConstant.textProperty().addListener(((observable, oldValue, newValue) -> {
//                    GrayImageDivideConst idc = new GrayImageDivideConst(bufferedImage, Integer.parseInt(newValue));
//                    bufferedImage = idc.getTemplateImage();
//                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                }));
                layout.getChildren().add(txtConstant);

                Button btnCalculate = new Button();
                btnCalculate.setText("Calculate");

                btnCalculate.setOnAction(event1 -> {
                    ColorImageDivideConst idc = new ColorImageDivideConst(bufferedImage, Integer.parseInt(txtConstant.getText()));
                    bufferedImage = idc.getTemplateImage();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    scene.getWindow().hide();
                    addToHistory(bufferedImage, "Image divide with constant");
                });
                layout.getChildren().add(btnCalculate);

                scene.setRoot(layout);

                Stage stage = new Stage();
                stage.setTitle("Image divide with constant");
                stage.setScene(scene);

                stage.setOnCloseRequest(event1 -> {
                    iv1.setImage(SwingFXUtils.toFXImage(tBufferedImage, null));
                });

                stage.show();
            });

            return menuDivide;
        }

        public MenuItem setImageRoots(String name, ImageView imageView) {
            MenuItem menuImageRoots = new MenuItem(name, imageView);

            menuImageRoots.setOnAction(event -> {
                ColorImageRoots colorImageRoots = new ColorImageRoots(bufferedImage);
                bufferedImage = colorImageRoots.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                addToHistory(bufferedImage, "Image Roots");
            });

            return menuImageRoots;
        }

        public MenuItem setImageLog(String name, ImageView imageView) {
            MenuItem menuImageLog = new MenuItem(name, imageView);

            menuImageLog.setOnAction(event -> {
                ColorImageLog colorImageLog = new ColorImageLog(bufferedImage);
                bufferedImage = colorImageLog.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                addToHistory(bufferedImage, "Image Logarithm");
            });

            return menuImageLog;
        }
    }
}