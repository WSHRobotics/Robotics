/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Accelerometer;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends SimpleRobot {
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    
        
        RobotDrive drive = new RobotDrive(1,2);
        Joystick leftStick = new Joystick(1);
        Joystick rightStick = new Joystick(2);
        Joystick controller = new Joystick(3);
        
        Relay beater = new Relay(1);
        Relay feeder = new Relay(2);
        Relay turntable = new Relay(3);
        
//        private Compressor compressor = new Compressor(1,2);
       // Victor motor = new Victor(4);
       // AnalogChannel channel = new AnalogChannel(2);
       // Accelerometer sensor = new Accelerometer(channel);
        
        
        Gyro g = new Gyro(1); 
        int x;
        
        double leftc;
        double rightc;
        

        double left;
        double sensorread;
        
        Info i = new Info();
        
        //Camera code found on Chief Delphi.
        //AxisCamera camera = AxisCamera.getInstance();
        AxisCamera camera;          // the axis camera object (connected to the switch)
        CriteriaCollection cc;      // the criteria for doing the particle filter operation
        
        

        
    public void autonomous() {
        //for (int j = 0; j < 10; j++){
        //for (int i = 0; i < 50; i++) {
           // if (i < 10){
        camera = AxisCamera.getInstance();  // get an instance ofthe camera
        cc = new CriteriaCollection();      // create the criteria for the particle filter
        cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_WIDTH, 30, 400, false);
        cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_HEIGHT, 40, 400, false);
        
     while(isAutonomous() && isEnabled()){
        boolean get = false;
        int Defualt;
        int Next = 0;
        int Current = 0;
        
        drive.tankDrive(1, 1);
        
        //DriverStatiDriveronLCD.getInstance().updateLCD();
        
        Timer.delay(500);
        drive.tankDrive(0, 0);
        for(int r = 1; r<5; r = r++){
                if(get == false){
                  Defualt = (int) g.getAngle();
                  Next = Defualt+90;
                  
                }
                while(Current <= Next){
                Current = (int) g.getAngle();
                drive.tankDrive(-1, 1);
        
        }
                
        }     
          // }else{
        //drive.tankDrive(-1, 1); //turn right
        //Timer.delay(2.5); // wait 2 seconds
        try {
                /**
                 * Do the image capture with the camera and apply the algorithm described above. This
                 * sample will either get images from the camera or from an image file stored in the top
                 * level directory in the flash memory on the cRIO. The file name in this case is "10ft2.jpg"
                 * 
                 */
                ColorImage image = null;     
                try {
                    image = camera.getImage(); // comment if using stored images
                } catch (AxisCameraException ex) {
                    ex.printStackTrace();
                } catch (NIVisionException ex) {
                    ex.printStackTrace();
                }
                DriverStationLCD.getInstance().updateLCD();
                //ColorImage image;                           // next 2 lines read image from flash on cRIO
                //image =  new RGBImage("/10ft2.jpg");
                BinaryImage thresholdImage = image.thresholdRGB(100, 200, 100, 200, 100, 200);   // keep only red objects
                BinaryImage bigObjectsImage = thresholdImage.removeSmallObjects(false, 2);  // remove small artifacts
                BinaryImage convexHullImage = bigObjectsImage.convexHull(false);          // fill in occluded rectangles
                BinaryImage filteredImage = convexHullImage.particleFilter(cc);           // find filled in rectangles
                
                ParticleAnalysisReport[] reports = filteredImage.getOrderedParticleAnalysisReports();  // get list of results
                for (int i = 0; i < reports.length; i++) {                                // print results
                    ParticleAnalysisReport r = reports[i];
                    System.out.println("Particle: " + i + ":  Center of mass x: " + r.center_mass_x);
                }
                System.out.println(filteredImage.getNumberParticles() + "  " + Timer.getFPGATimestamp());

                /**
                 * all images in Java must be freed after they are used since they are allocated out
                 * of C data structures. Not calling free() will cause the memory to accumulate over
                 * each pass of this loop.
                 */
                filteredImage.free();
                convexHullImage.free();
                bigObjectsImage.free();
                thresholdImage.free();
                image.free();
                
//            } catch (AxisCameraException ex) {        // this is needed if the camera.getImage() is called
//                ex.printStackTrace();
            } catch (NIVisionException ex) {
                ex.printStackTrace();
            }
        
         // drive 0% forward with 75% turn
        //Timer.delay(0.01); // wait for the 90 degree turn to complete
        }}
       // }
        //drive.drive(-1.0, 0.0); // drive 0% forward with 0% turn (stop)
   // }
   // }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        
        while (isOperatorControl() && isEnabled())
        {
             drive.setSafetyEnabled(false);
             Beater();
             Beaterback();
             //NORMAL DRIVE
             //drive.tankDrive(leftStick, rightStick);
             //i.start();
             
             //CONTROLLER DRIVE
             
                //leftc = (leftStick.getAxis(Joystick2AxisType.kX))/2;
            leftc = (controller.getRawAxis(5));
            rightc = (controller.getRawAxis(2)); 
            
            if(controller.getRawButton(6)){
            drive.tankDrive(leftc*-.5,rightc*-.5);
            //System.out.println("SLOW");
            }else{
                drive.tankDrive(leftc*-1,rightc*-1);
                //System.out.println("FAST");
            }           
            // left = leftStick.getAxis(Joystick.AxisType.kY);
             //sensorread = sensor.getAcceleration();
             
             //Test images
             
             //
 
                   
             //drive.arcadeDrive(thirdstick);
             
             //sensorread = sensor.getAcceleration();
             
             //System.out.println(right);
             Timer.delay(0.005);
        }
    }
    
    public void Beater(){
       if(controller.getRawButton(1)){
           beater.set(Relay.Value.kOn);
           beater.set(Relay.Value.kReverse);

       }else{
           beater.set(Relay.Value.kOff);
       }     
    }
    
    public void Beaterback(){
       if(controller.getRawButton(4)){
           beater.set(Relay.Value.kOn);
           beater.set(Relay.Value.kForward);

       }else{
           beater.set(Relay.Value.kOff);
       }     
    }
    
    public void Feeder(){
       if(controller.getRawButton(2)){
           feeder.set(Relay.Value.kOn);
       }else{
           feeder.set(Relay.Value.kOff);
       }
    }
    public void TurnTable(){
       if(controller.getRawButton(3)){
           turntable.set(Relay.Value.kOn);
       }else{
           turntable.set(Relay.Value.kOff);
       }
    }
}
