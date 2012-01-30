package edu.wpi.first.wpilibj.templates;


public class Info extends RobotTemplate{
    public void run(){
        DriverStationLCD.getInstance().updateLCD();
        System.out.println(sensorread);
    }
    
    public static void main(){
        (new Info().start());
        
    }
}
