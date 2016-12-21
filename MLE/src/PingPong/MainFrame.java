package PingPong;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

    public static final int imageWidth = 360;
    public static final int imageHeight = 360;
    public InputOutput inputOutput = new InputOutput(this);
    public static final double[][][][][] Q = new double[11][12][10][2][2];
    public boolean stop = false;
    ImagePanel canvas = new ImagePanel();
    ImageObserver imo = null;
    Image renderTarget = null;
    public int mousex, mousey, mousek;
    public int key;
    static int counter = 0;
    public static int actualXBall;
    public static int actualYBall;
    public static int actualXSchlaeger;
    public static int actualXV;
    public static int actualYV;
    public static double alpha = 0.01;
    public static double gamma = 0.9;
    public static int trainingSteps = 10000;


    public MainFrame(String[] args) {
        super("PingPong");

        getContentPane().setSize(imageWidth, imageHeight);
        setSize(imageWidth + 50, imageHeight + 50);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        canvas.img = createImage(imageWidth, imageHeight);

        add(canvas);
        initializeQ();

        run();
    }

    public static void initializeQ() {
        for (int i = 0; i < Q.length; i++) {
            for (int j = 0; j < Q[i].length; j++) {
                for (int k = 0; k < Q[i][j].length; k++) {
                    for (int l = 0; l < Q[i][j][k].length; l++) {
                        for (int m = 0; m < Q[i][j][k][l].length; m++) {
                            Q[i][j][k][l][m] = 0;
                        }
                    }
                }
            }
        }
        System.out.println("Hat inizialisiert!");
    }

    public void learn(int xBall, int yBall, int xSchlaeger, int xV, int yV, int reward) {
    	
    	//Get QValue for saved state
    	double actualQ = Q[actualXBall][actualYBall][xSchlaeger][actualXV][actualYV];

        int xR = 0;
        int yR = 0;
        if (xV == 1) {
            xR = 1;
        }
        if (yV == 1) {
            yR = 1;
        }
        double QAfterAction = Q[xBall][yBall][xSchlaeger][xR][yR];
        double newQ = actualQ + alpha *(reward + gamma * (QAfterAction - actualQ));
        
        Q[actualXBall][actualYBall][xSchlaeger][actualXV][actualYV] = newQ;
        /* Qt=Qt+α(r t+γ maxat (Qt+1)−Qt)
       α∈[0..1]ist Lernrate z.B.α=0.01
       γ∈[0..1]ist Discountfaktor
       rt ist Reward (Belohnung)*/
    }

    public double runAction(int xBall, int yBall, int xSchlaeger, int xV, int yV){
    	
        actualXBall = xBall;
        actualYBall = yBall;
        actualXSchlaeger = xSchlaeger;
        actualXV = xV;
        actualYV = yV;
        if(actualXV == -1){
        	actualXV = 0;
        }
        if(actualYV == -1){
        	actualYV = 0;
        }

        double rewardLeft = Q[actualXBall][actualYBall][xSchlaeger][actualXV][actualYV];
        double rewardRight = Q[actualXBall][actualYBall][xSchlaeger][actualXV][actualYV];
        
        if(xSchlaeger != 0){ //only go left, if it is possible
        	rewardLeft = Q[actualXBall][actualYBall][xSchlaeger-1][actualXV][actualYV];
        }
        if(xSchlaeger < 9){ //only go right, if it is possible
        	rewardRight = Q[actualXBall][actualYBall][xSchlaeger+1][actualXV][actualYV];;
        }        
        if (rewardLeft > rewardRight)
        {
        	return -1;
        } else if (rewardRight > rewardLeft){
        	return 1;
        }else{ //if the reward is the same for both actions, take one at random
        	return (2.0 * Math.random() - 1.0);
        }
    }

    public void run() {

        int xBall = 5, yBall = 6, xSchlaeger = 5, xV = 1, yV = 1;
        int score = 0;

        while (!stop) {
            if(counter > trainingSteps) {
                inputOutput.fillRect(0, 0, imageWidth, imageHeight, Color.black);
                inputOutput.fillRect(xBall * 30, yBall * 30, 30, 30, Color.green);
                inputOutput.fillRect(xSchlaeger * 30, 11 * 30 + 20, 90, 10, Color.orange);
            }
            double action = runAction( xBall,  yBall,  xSchlaeger,  xV,  yV);
            if (action < -0.3) {
                xSchlaeger--;
            }
            if (action > 0.3) {
                xSchlaeger++;
            }
            if (xSchlaeger < 0) {
                xSchlaeger = 0;
            }
            if (xSchlaeger > 9) {
                xSchlaeger = 9;
            }

            xBall += xV;
            yBall += yV;
            if (xBall > 9 || xBall < 1) {
                xV = -xV;
            }
            if (yBall > 10 || yBall < 1) {
                yV = -yV;
            }

            if (yBall == 11) {
                if (xSchlaeger == xBall || xSchlaeger == xBall - 1 || xSchlaeger == xBall - 2) {
                    //positive reward
                    learn(xBall, yBall, xSchlaeger, xV, yV, 1);
                    score++;
                    counter++;
                    System.out.println(counter + ": score = " + score);
                } else {
                    //negative reward
                    score--;
                    counter++;
                    System.out.println(counter + ": score = " + score);
                    learn(xBall, yBall, xSchlaeger, xV, yV, -1);
                }
            } else {
                //nix reward
                learn(xBall, yBall, xSchlaeger, xV, yV, 0);
            }

            if (counter > trainingSteps){
            	   try {
                       Thread.sleep(100);                 //1000 milliseconds is one second.
                   } catch (InterruptedException ex) {
                       Thread.currentThread().interrupt();
                   }

                   repaint();
                   validate();
            }
         
        }

        setVisible(false);
        dispose();
    }

    public void mouseReleased(MouseEvent e) {
        mousex = e.getX();
        mousey = e.getY();
        mousek = e.getButton();
    }

    public void mousePressed(MouseEvent e) {
        mousex = e.getX();
        mousey = e.getY();
        mousek = e.getButton();
    }

    public void mouseExited(MouseEvent e) {
        mousex = e.getX();
        mousey = e.getY();
        mousek = e.getButton();
    }

    public void mouseEntered(MouseEvent e) {
        mousex = e.getX();
        mousey = e.getY();
        mousek = e.getButton();
    }

    public void mouseClicked(MouseEvent e) {
        mousex = e.getX();
        mousey = e.getY();
        mousek = e.getButton();
    }

    public void mouseMoved(MouseEvent e) {
        // System.out.println(e.toString());
        mousex = e.getX();
        mousey = e.getY();
        mousek = e.getButton();
    }

    public void mouseDragged(MouseEvent e) {
        mousex = e.getX();
        mousey = e.getY();
        mousek = e.getButton();
    }

    public void keyTyped(KeyEvent e) {
        key = e.getKeyCode();
    }

    public void keyReleased(KeyEvent e) {
        key = e.getKeyCode();
    }

    public void keyPressed(KeyEvent e) {
        System.out.println(e.toString());
    }

    /**
     * Construct main frame
     *
     * @param args passed to MainFrame
     */
    public static void main(String[] args) {
        new MainFrame(args);
    }
}
