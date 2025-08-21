package factory;

import classes.Circle;
import classes.Rectangle;
import classes.Square;
import interfaces.Shape;

public class ShapeFactory {

    public Shape getShape(String shapeType){
        if(shapeType == null) return null;

        switch (shapeType.toUpperCase()){
            case "CIRCLE": return new Circle();
            case "RECTANGLE": return new Rectangle();
            case "SQUARE": return new Square();
            default: return null;
        }
    }

}
