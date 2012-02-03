package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DriverStationLCD;


public class Info extends Thread{
    double sensorread;
    
    public void run(){
        DriverStationLCD.getInstance().updateLCD();
        System.out.println("TEST");
    }
}
