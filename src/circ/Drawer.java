package Circ;


import java.util.ArrayList;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.text.Font;


public class Drawer implements DrawerInterface{
    private double spacing; //= 30.0f;
    private double radius; //= 20.0f;
    private double angle;// = 5.0f;
    private double distance;// = 2*spacing-2*radius;
    private Point start;
    private Point end;
    private Circle green;
    private Circle red;
    private Circle last;
    private static boolean f=false;
    public Drawer ()
    {
        spacing = 60;
        radius = 20;
        angle = 5;
        distance = spacing-2*radius;
        start = new Point();
        end = new Point();
        green = new Circle();
        red = new Circle();
        last = new Circle();
    }
    @Override
    public void setSpacing(double space)
    {
        spacing=space;
        distance = spacing-2*radius;
    }
    @Override
    public void setRadius(double rad)
    {
        radius=rad;
        spacing=radius*3;
        distance = spacing-2*radius;
    }
    @Override
    public void setDefault()
    {
        spacing = 30;
        radius = 20;
        angle = 5;
        distance = spacing-2*radius;
    }
    @Override
    public double getRadius()
    {
        return radius;
    }
    @Override
    public Point getStart()
    {
        return start;
    }
    @Override
    public Point getEnd()
    {
        return end;
    }
    public void resetGreen()
    {
        green = new Circle();
    }
    public void resetRed()
    {
        red = new Circle();
    }
    @Override
    public void draw(ArrayList<ArrayList<Integer>> outer,  Group group)//drawing circle and its connections
    {        

        int size=outer.size();
        for (int i=0;i<size;i++)
         {    
            for (int j=0;j<size;j++)
            {   
                int decision=outer.get(i).get(j);
                double centerX = 40 + spacing*j;
                double centerY = 70 + spacing*i;
                Circle circle = new Circle();
                circle.setCenterX(centerX);
                circle.setCenterY(centerY);
                circle.setRadius(radius);
                if((i==start.getI())&&(j==start.getJ()))
                {
                    if ((start.getI()==end.getI())&&(start.getJ()==end.getJ()))
                        circle.setFill(last.getFill());
                    else circle.setFill(green.getFill());
                    green = circle; 
                    start.setI(i);
                    start.setJ(j);
                }
                else if ((i==end.getI())&&(j==end.getJ()))
                {
                    if ((start.getI()==end.getI())&&(start.getJ()==end.getJ()))
                        circle.setFill(last.getFill());
                    else circle.setFill(red.getFill());
                    red = circle;
                    end.setI(i);
                    end.setJ(j);
                }
                else circle.setFill(javafx.scene.paint.Color.web("9D92C9"));
                circle.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent t) {
                        if(t.getButton() == MouseButton.PRIMARY) 
                        {
                            if (f)  
                            {
                                dropPath(group);
                                
                            }
                            green.setFill(javafx.scene.paint.Color.web("9D92C9"));
                            green = circle; 
                            circle.setFill(Color.GREEN);
                            start.setI((int)((circle.getCenterY()-70)/spacing));
                            start.setJ((int)((circle.getCenterX()-40)/spacing));
                            last=green;
                        }
                        else 
                        {
                            if (f)  
                            {
                                dropPath(group);
                            }
                            red.setFill(javafx.scene.paint.Color.web("9D92C9"));
                            red = circle;
                            circle.setFill(Color.RED);
                            end.setI((int)((circle.getCenterY()-70)/spacing));
                            end.setJ((int)((circle.getCenterX()-40)/spacing));
                            last=red;
                        }          
                    }
                });
                switch (decision)
                {
                    case 0:
                        break;
                    case 1:
                        if (j!=size-1)
                            arrow(centerX+radius, centerY, centerX+radius+distance, centerY, group, Color.BLACK);//right
                        break;
                    case 2:
                        if (i!=0)
                            arrow(centerX, centerY-radius, centerX, centerY-radius-distance, group, Color.BLACK);//up
                        break;
                    case 3:
                        if (j!=size-1)
                            arrow(centerX+radius, centerY, centerX+radius+distance, centerY, group, Color.BLACK);//right
                        if (i!=0)
                            arrow(centerX, centerY-radius, centerX, centerY-radius-distance, group, Color.BLACK);//up
                        break;
                    case 4:
                        if (j!=0)
                            arrow(centerX-radius, centerY, centerX-radius-distance, centerY, group, Color.BLACK);//left
                        break;
                    case 5:
                        if (j!=size-1)
                            arrow(centerX+radius, centerY, centerX+radius+distance, centerY, group, Color.BLACK);//right
                        if (j!=0)
                            arrow(centerX-radius, centerY, centerX-radius-distance, centerY, group, Color.BLACK);//left
                        break;
                    case 6:
                        if (j!=0)
                            arrow(centerX-radius, centerY, centerX-radius-distance, centerY, group, Color.BLACK);//left
                        if (i!=0)
                            arrow(centerX, centerY-radius, centerX, centerY-radius-distance, group, Color.BLACK);//up
                        break;
                    case 7:
                        if (j!=size-1)
                            arrow(centerX+radius, centerY, centerX+radius+distance, centerY, group, Color.BLACK);//right
                        if (j!=0)
                            arrow(centerX-radius, centerY, centerX-radius-distance, centerY, group, Color.BLACK);//left
                        if (i!=0)
                            arrow(centerX, centerY-radius, centerX, centerY-radius-distance, group, Color.BLACK);//up
                        break;
                    case 8:
                        if (i!=size-1)
                            arrow(centerX, centerY+radius, centerX, centerY+radius+distance, group, Color.BLACK);//down
                        break;
                    case 9:
                        if (j!=size-1)
                            arrow(centerX+radius, centerY, centerX+radius+distance, centerY, group, Color.BLACK);//right
                        if (i!=size-1)
                            arrow(centerX, centerY+radius, centerX, centerY+radius+distance, group, Color.BLACK);//down
                        break;
                    case 10:
                        if (i!=0)
                            arrow(centerX, centerY-radius, centerX, centerY-radius-distance, group, Color.BLACK);//up
                        if (i!=size-1)
                            arrow(centerX, centerY+radius, centerX, centerY+radius+distance, group, Color.BLACK);//down
                        break;
                    case 11:
                        if (j!=size-1)
                            arrow(centerX+radius, centerY, centerX+radius+distance, centerY, group, Color.BLACK);//right
                        if (i!=0)
                            arrow(centerX, centerY-radius, centerX, centerY-radius-distance, group, Color.BLACK);//up
                        if (i!=size-1)
                            arrow(centerX, centerY+radius, centerX, centerY+radius+distance, group, Color.BLACK);//down
                        break;
                    case 12:
                        if (j!=0)
                            arrow(centerX-radius, centerY, centerX-radius-distance, centerY, group, Color.BLACK);//left
                        if (i!=size-1)
                            arrow(centerX, centerY+radius, centerX, centerY+radius+distance, group, Color.BLACK);//down
                        break;
                    case 13:
                        if (j!=size-1)
                            arrow(centerX+radius, centerY, centerX+radius+distance, centerY, group, Color.BLACK);//right
                        if (j!=0)
                            arrow(centerX-radius, centerY, centerX-radius-distance, centerY, group, Color.BLACK);//left
                        if (i!=size-1)
                            arrow(centerX, centerY+radius, centerX, centerY+radius+distance, group, Color.BLACK);//down
                        break;
                    case 14:
                        if (i!=0)
                            arrow(centerX, centerY-radius, centerX, centerY-radius-distance, group, Color.BLACK);//up
                        if (j!=0)
                            arrow(centerX-radius, centerY, centerX-radius-distance, centerY, group, Color.BLACK);//left
                        if (i!=size-1)
                            arrow(centerX, centerY+radius, centerX, centerY+radius+distance, group, Color.BLACK);//down
                        break;
                    case 15:
                        if (j!=size-1)
                            arrow(centerX+radius, centerY, centerX+radius+distance, centerY, group, Color.BLACK);//right
                        if (i!=0)
                            arrow(centerX, centerY-radius, centerX, centerY-radius-distance, group, Color.BLACK);//up
                        if (j!=0)
                            arrow(centerX-radius, centerY, centerX-radius-distance, centerY, group, Color.BLACK);//left
                        if (i!=size-1)
                            arrow(centerX, centerY+radius, centerX, centerY+radius+distance, group, Color.BLACK);//down
                        break;
                }
               group.getChildren().add(circle);
            }
        }
        
    }
    @Override
    public void drawPath(Map<Integer,Point> sequence, Group group)
    {
        int size = sequence.size();
        IntegerStringConverter conv = new IntegerStringConverter();
        
        for (int i = 0; i < size; i++)
        {
            Point circleIndex = sequence.get(i);
            double centerX = 40 + spacing*circleIndex.getJ();
            double centerY = 70 + spacing*circleIndex.getI();
            Circle circle = new Circle();
            circle.setCenterX(centerX);
            circle.setCenterY(centerY);
            circle.setRadius(radius);
            circle.setFill(Color.GREEN);
            Label step = new Label(conv.toString(i));
            if (i==0)
            {
                step.setText("S"); 
                step.setTextFill(Color.RED);
                step.setFont(new Font("Helvetica", 10));
            }
            else if (i==size-1)
            {
                step.setText("E");
                step.setTextFill(Color.RED);
                step.setFont(new Font("Helvetica", 10));
            }
            step.setScaleX(2.5);
            step.setScaleY(2.5);
            step.setTranslateX(centerX-3);
            step.setTranslateY(centerY-8);
            step.setMouseTransparent(true);
            circle.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent t) { 
                    if(t.getButton() == MouseButton.PRIMARY) 
                    {
                        if (f)  
                        {
                            dropPath(group);
                        }
                        green.setFill(javafx.scene.paint.Color.web("9D92C9"));
                        green = circle; 
                        circle.setFill(Color.GREEN);
                        start.setI((int)((circle.getCenterY()-70)/spacing));
                        start.setJ((int)((circle.getCenterX()-40)/spacing));
                        last=green;
                    }
                    else 
                    {      
                        if (f)  
                        {
                            dropPath(group);
                        }
                        red.setFill(javafx.scene.paint.Color.web("9D92C9"));
                        red = circle;
                        circle.setFill(Color.RED);
                        end.setI((int)((circle.getCenterY()-70)/spacing));
                        end.setJ((int)((circle.getCenterX()-40)/spacing)); 
                        last=red;
                    }           
                }
            });
            ObservableList circles = group.getChildren();
            for (int k=0;k<circles.size();k++)
            {
                if (circles.get(k) instanceof Circle)
                {
                    Circle temp = (Circle)circles.get(k);
                    if ((temp.getCenterX()==circle.getCenterX())&&(temp.getCenterY()==circle.getCenterY()))
                    {
                        group.getChildren().remove(k);
                        break;
                    }
                }
            }
            if (i==0)
                green = circle;
            else if (i==size-1)
                red = circle;
            group.getChildren().add(circle);
            group.getChildren().add(step);
            
        }
        
        f = true;
    }
    @Override
    public void dropPath(Group group)
    {
        ObservableList circles = group.getChildren();
        for (int i=0;i<circles.size();i++)
            {
                if(circles.get(i) instanceof Circle)
                {
                    Circle temp = (Circle)circles.get(i); 
                    if ((temp.getCenterX()==red.getCenterX())&&(temp.getCenterY()==red.getCenterY()))
                        temp.setFill(Color.RED);
                    else
                    if (!((temp.getCenterX()==green.getCenterX())&&(temp.getCenterY()==green.getCenterY()))&&(temp.getFill()==Color.GREEN))
                        temp.setFill(javafx.scene.paint.Color.web("9D92C9"));
                }
            }
        for (int i=0;i<circles.size();i++)
            {
                if(circles.get(i) instanceof Label)
                    {
                        circles.remove(i);
                    }
                
            }
        f = false;
        Circ.isUp = false;
    } 
    @Override
    public void addEdge(Path path, Group group)
    {
        double centerX = 40 + spacing*path.circle.getJ();
        double centerY = 70 + spacing*path.circle.getI();
        for (int i=0; i<path.bitMask.size(); i++)
        {
            if (path.bitMask.get(i)==1)
            {
                switch (i)
                {
                    case 0:
                        arrow(centerX, centerY+radius, centerX, centerY+radius+distance, group, Color.ORANGE);//down
                        
                        break;
                    case 1:
                        arrow(centerX-radius, centerY, centerX-radius-distance, centerY, group, Color.ORANGE);//left
                        
                        break;
                    case 2:
                        arrow(centerX, centerY-radius, centerX, centerY-radius-distance, group, Color.ORANGE);//up
                        break;
                    case 3:
                        arrow(centerX+radius, centerY, centerX+radius+distance, centerY, group, Color.ORANGE);//right
                        break;
                }
                break;
            }
        }
    }
    @Override
    public void arrow(double startX, double startY, double endX, double endY,  Group group, Color col)
    {
        Line body = new Line();
        Line headOne = new Line();
        Line headTwo = new Line();
        body.setStartX(startX);
        body.setStartY(startY);
        body.setEndX(endX);
        body.setEndY(endY);
        headOne.setStartX(body.getEndX());
        headOne.setStartY(body.getEndY());
        headTwo.setStartX(body.getEndX());
        headTwo.setStartY(body.getEndY());
        if (body.getEndY() < body.getStartY())
            {
                 headOne.setEndX(body.getEndX()+ angle);
                 headOne.setEndY(body.getEndY()+ angle);
                 headTwo.setEndX(body.getEndX()- angle);
                 headTwo.setEndY(body.getEndY()+ angle);
            }
        else
            if (body.getEndX() > body.getStartX())
                    {
                        headOne.setEndX(body.getEndX()-angle);
                        headOne.setEndY(body.getEndY()-angle);
                        headTwo.setEndX(body.getEndX()-angle);
                        headTwo.setEndY(body.getEndY()+angle);
                    }
            else
                if (body.getEndY() > body.getStartY())
                {
                    headOne.setEndX(body.getEndX()+ angle);
                    headOne.setEndY(body.getEndY()- angle);
                    headTwo.setEndX(body.getEndX()- angle);
                    headTwo.setEndY(body.getEndY()- angle);                    
                }
                else
                    if (body.getEndX() < body.getStartX())
                    {
                        headOne.setEndX(body.getEndX()+angle);
                        headOne.setEndY(body.getEndY()-angle);
                        headTwo.setEndX(body.getEndX()+angle);
                        headTwo.setEndY(body.getEndY()+angle);
                    }
        body.setStroke(col);
        headOne.setStroke(col);
        headTwo.setStroke(col);
        group.getChildren().add(body);
        group.getChildren().add(headOne);
        group.getChildren().add(headTwo);
    }
}
