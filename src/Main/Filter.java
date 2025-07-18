package Main;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Filter {


    private BufferedImage darknessFilter;
    private float filter = 0f;


    public Filter(int screenWidth, int screenHeight, int circleSize, int circleCenterX, int circleCenterY) {

        //CREATE A BUFFERED IMAGE
        darknessFilter = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        //CREATE A SCREEN-SIZED RECTANGLE AREA
        Area screenArea = new Area(new Rectangle2D.Double(0, 0, screenWidth, screenHeight));

        //GET THE CENTER X AND Y

        double x = circleCenterX - ((double) circleSize / 2);
        double y = circleCenterY - ((double) circleSize / 2);

        //CREATE A LIGHT CIRCLE SHAPE
        Shape circleShape=new Ellipse2D.Double(x,y,circleSize,circleSize);

        //CREATE A LIGHT CIRCLE AREA
        Area lightArea = new Area(circleShape);

        //SUBSTRACT THE LIGHT CIRCLE FROM THE SCREEN RECTANGLE
        screenArea.subtract(lightArea);

        //CREATE A GRADATION EFFECT WITHIN THE LIGHT CIRCLE

        Color[] color=new Color[12];
        float[] fraction=new float[12];

        color[0]=new Color(0,0,0,0f);
        color[1]=new Color(0,0,0,0.42f);
        color[2]=new Color(0,0,0,0.52f);
        color[3]=new Color(0,0,0,0.61f);
        color[4]=new Color(0,0,0,0.69f);
        color[5]=new Color(0,0,0,0.76f);
        color[6]=new Color(0,0,0,0.82f);
        color[7]=new Color(0,0,0,0.87f);
        color[8]=new Color(0,0,0,0.91f);
        color[9]=new Color(0,0,0,0.94f);
        color[10]=new Color(0,0,0,0.96f);
        color[11]=new Color(0,0,0,0.98f);

        fraction[0]=0f;
        fraction[1]=0.4f;
        fraction[2]=0.5f;
        fraction[3]=0.6f;
        fraction[4]=0.65f;
        fraction[5]=0.7f;
        fraction[6]=0.75f;
        fraction[7]=0.8f;
        fraction[8]=0.85f;
        fraction[9]=0.9f;
        fraction[10]=0.95f;
        fraction[11]=1f;


        //CREATE A GRADATION PAINT SETTINGS FOR THE LIGHT CIRCLE
        RadialGradientPaint gradientPaint=new RadialGradientPaint(circleCenterX,circleCenterY,((float) circleSize /2),fraction,color);

        //SET THE GRADIENT DATA ON G2
        g2.setPaint(gradientPaint);

        g2.fillRect(0,0,screenWidth, screenHeight);

        g2.dispose();

    }

    public Filter(GamePanel gamePanel) {

        int screenWidth = gamePanel.getWidth();
        int screenHeight = gamePanel.getHeight();
        int circleSize = 300;
        int circleCenterX = gamePanel.player.screenX + gamePanel.tileSize / 2;
        int circleCenterY = gamePanel.player.screenY + gamePanel.tileSize / 2;


        //CREATE A BUFFERED IMAGE
        darknessFilter = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        //CREATE A SCREEN-SIZED RECTANGLE AREA
        Area screenArea = new Area(new Rectangle2D.Double(0, 0, screenWidth, screenHeight));

        //GET THE CENTER X AND Y

        double x = circleCenterX - ((double) circleSize / 2);
        double y = circleCenterY - ((double) circleSize / 2);

        //CREATE A LIGHT CIRCLE SHAPE
        Shape circleShape=new Ellipse2D.Double(x,y,circleSize,circleSize);

        //CREATE A LIGHT CIRCLE AREA
        Area lightArea = new Area(circleShape);

        //SUBSTRACT THE LIGHT CIRCLE FROM THE SCREEN RECTANGLE
        screenArea.subtract(lightArea);

        //CREATE A GRADATION EFFECT WITHIN THE LIGHT CIRCLE

        Color[] color=new Color[12];
        float[] fraction=new float[12];

        color[0]=new Color(0,0,0,0f);
        color[1]=new Color(0,0,0,0.42f);
        color[2]=new Color(0,0,0,0.52f);
        color[3]=new Color(0,0,0,0.61f);
        color[4]=new Color(0,0,0,0.69f);
        color[5]=new Color(0,0,0,0.76f);
        color[6]=new Color(0,0,0,0.82f);
        color[7]=new Color(0,0,0,0.87f);
        color[8]=new Color(0,0,0,0.91f);
        color[9]=new Color(0,0,0,0.94f);
        color[10]=new Color(0,0,0,0.96f);
        color[11]=new Color(0,0,0,0.98f);

        fraction[0]=0f;
        fraction[1]=0.4f;
        fraction[2]=0.5f;
        fraction[3]=0.6f;
        fraction[4]=0.65f;
        fraction[5]=0.7f;
        fraction[6]=0.75f;
        fraction[7]=0.8f;
        fraction[8]=0.85f;
        fraction[9]=0.9f;
        fraction[10]=0.95f;
        fraction[11]=1f;


        //CREATE A GRADATION PAINT SETTINGS FOR THE LIGHT CIRCLE
        RadialGradientPaint gradientPaint=new RadialGradientPaint(circleCenterX,circleCenterY,((float) circleSize /2),fraction,color);

        //SET THE GRADIENT DATA ON G2
        g2.setPaint(gradientPaint);

        g2.fillRect(0,0,screenWidth, screenHeight);

        g2.dispose();
    }

    public void draw(Graphics2D g2){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filter));
        if (filter != 0f)
            g2.drawImage(darknessFilter,0,0,null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }


    public void update(DayState state) {
        switch (state) {
            case MORNING -> filter = Math.max(filter - (4f/5)/5, 0f);
            case EVENING -> filter = Math.min(filter + (4f/5) / 3, 4f/5);
            case NIGHT -> filter = 4f/5;
        }
    }
}
