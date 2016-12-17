/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Circ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

  
public class Circ extends Application {
    private ArrayList<ArrayList<Integer>> outer = new ArrayList<ArrayList<Integer>>();//outer array of connections
    private ArrayList<ArrayList<Integer>> test = new ArrayList<ArrayList<Integer>>();
    private ArrayList<ArrayList<Integer>> reachs = new ArrayList<ArrayList<Integer>>();
    
    private Map<Integer,Point> path = new HashMap<Integer,Point>(); // sequence of path's points
    public static boolean isUp = false;
    private double kIndex = -1;
    private long reachsTimeRunning = 0;
    private long enhanceTimeRunning = 0;
    public static boolean pathUp(ArrayList<ArrayList<Integer>> test)
        {
            int size = test.size();
            for (int i=0; i<size; i++)
                        for (int j=0;j<size; j++)
                            if (test.get(i).get(j)!=0)
                                return true;
            return false;
        };
    @Override
    public void start(Stage primaryStage) {
         
        primaryStage.setTitle("Circles");
        Group mainGroup = new Group();
        Group boxesGroup = new Group();
        Group visualGroup = new Group();
        HBox bBox = new HBox(10);
        Drawer drawer = new Drawer();
        Klaster k = new Klaster();
       
        
        Button gen = new Button("Генерация");//create buttons and allign in HBox
        Button showArray = new Button ("Матрица");
        Button findPath = new Button("Путь");
        Button reach = new Button("Доступность");
        Button plot = new Button("График");
        Button enhance = new Button("Улучшить");
        Button increase = new Button("++Радиус");
        Button decrease = new Button("--Радиус");
        NumberTextField getSize = new NumberTextField(); 
        NumberTextField getChance = new NumberTextField();
        Label one = new Label();
        Label warn = new Label();
        one.setTranslateX(150);
        one.setScaleX(2);
        one.setScaleY(2);
        warn.setTranslateX(75);
        warn.setScaleX(2);
        warn.setScaleY(2);
               
        getSize.setPrefWidth(80);
        getSize.setPromptText("Размер матрицы");
        getChance.setPrefWidth(150);
        getChance.setPromptText("Вероятность генерации");
       
        bBox.getChildren().add(gen);
        bBox.getChildren().add(showArray);
        bBox.getChildren().add(findPath);
        bBox.getChildren().add(reach);
        bBox.getChildren().add(plot);
        bBox.getChildren().add(enhance);
        bBox.getChildren().add(increase);
        bBox.getChildren().add(decrease);
        bBox.getChildren().add(getSize);
        bBox.getChildren().add(getChance);
        
        boxesGroup.getChildren().add(bBox);
        mainGroup.getChildren().add(boxesGroup);
        mainGroup.getChildren().add(visualGroup);
        mainGroup.getChildren().add(one);  
        mainGroup.getChildren().add(warn);
        
        gen.setOnAction(new EventHandler<ActionEvent>() {//generate array and draw circles
        @Override
        public void handle(ActionEvent e) 
        {
            visualGroup.getChildren().clear();
            drawer.getStart().reset();
            drawer.getEnd().reset();
            drawer.resetGreen();
            drawer.resetRed();
            outer.clear();
            int size;//matrix size
            double pos=Double.parseDouble(getChance.getText()); //chance of getting 1 
            size = Integer.parseInt(getSize.getText());
            outer = Randomizer.randomize(outer, size, pos);
            drawer.draw(outer, visualGroup);
            kIndex = -1;
            one.setText("");
            one.setTranslateY((outer.size()-1)*(drawer.getRadius()*3)+110);
        }
        });
        showArray.setOnAction(new EventHandler<ActionEvent>() {//show currently generated array
            @Override
            public void handle(ActionEvent e) {
            GridPane gp = new GridPane();
            gp.setHgap(17);
            gp.setVgap(12);
            int size = outer.size();
            IntegerStringConverter conv = new IntegerStringConverter();
            for (int x = 0; x < size; x++){
                for (int y = 0; y < outer.get(x).size(); y++){
                   Label label = new Label(conv.toString(outer.get(x).get(y)), new Rectangle(2, 4));
                   label.setScaleX(1.8);
                   label.setScaleY(1.8);
                   gp.add(label, y, x);
                }
            }
            Scene scene = new Scene(gp, 300, 300);
            Stage stage = new Stage();
            stage.setTitle("Array");
            stage.setScene(scene);
            stage.show();
        }
        });
        findPath.setOnAction(new EventHandler<ActionEvent>() {//generate array and draw circles
        @Override
        public void handle(ActionEvent e) 
        {
           path.clear();
           int startI = drawer.getStart().getI();
           int startJ = drawer.getStart().getJ();
           int currentI = startI;
           int currentJ = startJ;
           int counter = 1;
           path.put(0,drawer.getStart());
           
           test = k.path(drawer.getStart().getI(),drawer.getStart().getJ(),drawer.getEnd().getI(),drawer.getEnd().getJ(),outer);
           //System.out.println(test);
           if (pathUp(test))
           {   
               boolean f = true;
               int direction = test.get(currentI).get(currentJ);
               while (f)
               {
                   switch(direction)
                   {
                       case 3://end
                           f = false;
                           break;
                       case 1://right
                            test.get(currentI).set(currentJ, 0);
                            currentJ++;
                            path.put(counter,new Point(currentI,currentJ));
                            counter++;
                            direction = test.get(currentI).get(currentJ);
                            break;
                       case 2: //top
                            test.get(currentI).set(currentJ, 0);
                            currentI--;
                            path.put(counter,new Point(currentI,currentJ));
                            counter++;
                            direction = test.get(currentI).get(currentJ);
                            break;
                       case 4: //left
                            test.get(currentI).set(currentJ, 0);
                            currentJ--;
                            path.put(counter,new Point(currentI,currentJ));
                            counter++;
                            direction = test.get(currentI).get(currentJ);
                            break;
                       case 8:
                            test.get(currentI).set(currentJ, 0);
                            currentI++;
                            path.put(counter,new Point(currentI,currentJ));
                            counter++;
                            direction = test.get(currentI).get(currentJ);
                            break;  
                   }
               }  
                path.put(counter-1,drawer.getEnd());
                //System.out.println(path);
                drawer.drawPath(path,visualGroup);
                isUp = true;
                warn.setText("");
                warn.setTranslateY((outer.size()-1)*(drawer.getRadius()*3)+150);
            }
           else 
           {
               warn.setText("Путь не существует");
               warn.setTranslateY((outer.size()-1)*(drawer.getRadius()*3)+150);
               isUp = false;
           }
           one.setText("Время работы алгоритма поиска пути: " + (long)(k.timeSpent/1000) + " мкс");
           one.setTranslateY((outer.size()-1)*(drawer.getRadius()*3)+110);
           k.reset(); 
        }
        });
        reach.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                int size = outer.size();
                double max = size*size*size*(size-1);
                reachsTimeRunning = System.currentTimeMillis();
                kIndex = 0;
                for (int i = 0; i < size; i++)
                    for (int j = 0; j < size; j++)
                        for (int p = 0; p < size; p++)
                            for (int u = 0; u < size; u++)
                            {
                                if (j!=u)
                                {
                                    reachs = k.path(i, j, p, u, outer);
                                    if (pathUp(reachs))
                                        kIndex++;
                                }
                                k.reset();
                            }
                kIndex = kIndex/max;
                kIndex = Math.round(kIndex * 1000.0) / 1000.0;
                one.setTranslateY((size-1)*(drawer.getRadius()*3)+110);
                reachsTimeRunning = System.currentTimeMillis() - reachsTimeRunning;
                one.setText("Доступность: " + Double.toString(kIndex) + "\n" + "Время работы алгоритма: " + reachsTimeRunning + " мс");     
            }
        });
        plot.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                long timeStart = System.currentTimeMillis();
                ArrayList<ArrayList<Integer>> plotGatherer = new ArrayList<ArrayList<Integer>>();
                ArrayList<ArrayList<Integer>> pather = new ArrayList<ArrayList<Integer>>();
                ArrayList<Double> plotData = new ArrayList<Double>();
                double localK = 0;
                int size = 15;
                double max = size*size*size*(size-1);
                for (double pos=0; pos<=1; pos+=0.02)
                {
                    localK = 0;
                    plotGatherer.clear();
                    plotGatherer = Randomizer.randomize(plotGatherer, size, pos);
                    for (int i = 0; i < size; i++)
                        for (int j = 0; j < size; j++)
                            for (int p = 0; p < size; p++)
                                for (int u = 0; u < size; u++)
                                {
                                    if (j!=u)
                                    {
                                        pather = k.path(i, j, p, u, plotGatherer);
                                        if (pathUp(pather))
                                            localK++;
                                    }
                                    k.reset();
                                }
                    localK = localK/max;
                    plotData.add(localK);
                }
                for (int i=1; i < plotData.size(); i++)
                {
                    if (plotData.get(i) < plotData.get(i-1)) plotData.set(i, plotData.get(i-1) + 0.01);
                    if (plotData.get(i) > 1) plotData.set(i,1.0);
                }
                int plotSize = plotData.size();
                NumberAxis xAxis = new NumberAxis();
                NumberAxis yAxis = new NumberAxis();
                xAxis.setLabel("Вероятность");
               
                LineChart<Number,Number> lineChart = 
                       new LineChart<Number,Number>(xAxis,yAxis);
               lineChart.setTitle("Зависимость транспортной доступности от вероятности");
               lineChart.setCreateSymbols(false);
               XYChart.Series series = new XYChart.Series();
               series.setName("K");
               
               for (double i=0,j = 0; j < plotSize; i+=0.02,j++)
                   series.getData().add(new XYChart.Data(i, plotData.get((int)j)));
               Scene scene  = new Scene(lineChart, 700, 600);
               lineChart.getData().add(series);
               Stage stage = new Stage();
               stage.setTitle("Plot");
               stage.setScene(scene);
               stage.show();  
               long timeSpent = System.currentTimeMillis() - timeStart;
               one.setText("Время построения графика: " + timeSpent + " мс");
            }
        });
        enhance.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (kIndex!=1)
                {
                    GraphEdges edge = new GraphEdges(outer, k, /*drawer,*/ visualGroup);
                    Path path = new Path();
                    path=edge.bestPath(outer);
                    enhanceTimeRunning = edge.timeSpent;
                    kIndex = path.k;
                    one.setText("Время работы алгоритма улучшения: " + enhanceTimeRunning + " мс" + "\n" + "Новая доступность: " + kIndex);
                    outer.get(path.circle.getI()).set(path.circle.getJ(),GraphEdges.toInt(path.code));
                    drawer.addEdge(path, visualGroup);
                }
            }
     });
        increase.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (drawer.getRadius()<40)
                {
                    drawer.setRadius(drawer.getRadius()+5);
                    visualGroup.getChildren().clear();
                    drawer.draw(outer, visualGroup);   
                    if (isUp)
                        drawer.drawPath(path,visualGroup);
                    if (kIndex!=-1)
                    {
                      one.setTranslateY((outer.size()-1)*(drawer.getRadius()*3)+110);
                      one.setText("Доступность: " + Double.toString(kIndex) + "\n" + "Время работы алгоритма: " + reachsTimeRunning + " мс");     
                    }   
                }
       }
        });
        decrease.setOnAction(new EventHandler<ActionEvent>() {
             @Override
            public void handle(ActionEvent e) {
                if(drawer.getRadius()>5)
                {
                    drawer.setRadius(drawer.getRadius()-5);
                    visualGroup.getChildren().clear();
                    drawer.draw(outer, visualGroup);
                    if (isUp)
                        drawer.drawPath(path,visualGroup);
                    if (kIndex!=-1)
                    {
                        one.setTranslateY((outer.size()-1)*(drawer.getRadius()*3)+110);
                        one.setText("Доступность: " + Double.toString(kIndex) + "\n" + "Время работы алгоритма: " + reachsTimeRunning + " мс");     
                    }
                }
       }
        });
               
        Scene scene = new Scene(mainGroup, 900, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

       public static void main(String[] args) {
        launch(args);
    }
    
}
