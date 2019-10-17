/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.*;
import java.awt.event.*;
import java.lang.reflect.Method;

/**
 *
 * @author ParkerC
 */
public class BetterDP extends DrawingPanel implements KeyListener, MouseListener, MouseMotionListener
{
    private Class mainClass;
    private Method mouseMoveCall;
    private Method keyUpdateCall;
    private Method mouseDownCall;
    private Method mouseUpCall;
    
    
    public BetterDP(int width, int height){
        super (width, height);
        addKeyListener(this);         
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    public void setMainClass(Class a){
        mainClass = a;
        Method[] allMethods = mainClass.getDeclaredMethods();
            for (Method m:allMethods){
                if (m.getName().equals("mouseMove")){
                    mouseMoveCall = m;
                }
                else if (m.getName().equals("keyUpdate")){
                    keyUpdateCall = m;
                }
                else if (m.getName().equals("mouseDown")){
                    mouseDownCall = m;
                }
                else if (m.getName().equals("mouseUp")){
                    mouseUpCall = m;
                }
            }
    }
    
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        
        try{                        
            mouseMoveCall.invoke(null, x, y);
        } catch (Exception ex) {
            
        }
        
    }
    @Override
    public void keyPressed(KeyEvent e)
    {
        try{   
            keyUpdateCall.invoke(null, e);        
        } catch (Exception ex) {
            
        }
        
    }
    
    
    public void mouseClicked(MouseEvent e)
    {
    	
    }
    
    @Override
    public void keyTyped(KeyEvent e)
    {
        
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        try{   
            mouseUpCall.invoke(null, e);        
        } catch (Exception ex) {
            
        }
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        try{   
            mouseDownCall.invoke(null, e.getX(), e.getY());        
        } catch (Exception ex) {
            
        }
    }
}

