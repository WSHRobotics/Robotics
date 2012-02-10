package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DriverStationLCD;


public class Info extends Thread{
    double sensorread;
    
    public void run(){
        //DriverStationLCD.getInstance().updateLCD();
        
        System.out.println("TEST");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
