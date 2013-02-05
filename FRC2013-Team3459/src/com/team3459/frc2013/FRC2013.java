/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team3459.frc2013;
    
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class FRC2013 extends SimpleRobot {
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    
    //PWM INPUT
    RobotDrive drive = new RobotDrive(1, 3);                                    //The drivetrain of spitfire
    Victor shooter = new Victor(5);                                             //victor controlling main shooter motor
    Victor turret = new Victor(7);                                              //victor controlling turret movement
    Victor Flipyfloppy = new Victor(6);                                         //victor controlling the aiming board
    Servo banana = new Servo(10);                                               //servo attached to the almighty banana
    //Jaguar SShootera = new Jaguar(5);

    
    //Relay
    Compressor compressor = new Compressor(1,1);                                //Compressor
    Relay collector = new Relay(2);                                             //Relay connected to collector spike
    Relay conveyor = new Relay(8);                                              //Relay connected to conveyor spike
    
    //Solenoid
    Solenoid bridgeout = new Solenoid(1);
    Solenoid bridgein = new Solenoid(2);
    
    //DIGITAL IO
    Joystick leftStick = new Joystick(1);                                       //Left drive Joystick
    Joystick rightStick = new Joystick(2);                                      //Right drive Joystick
    Joystick Actionjoy = new Joystick(3);                                       //Shooters Joystick
    JoystickButton Collectoron = new JoystickButton(Actionjoy, 7);              //Collector hold to turn on
    JoystickButton Collectorrev = new JoystickButton(Actionjoy, 6);             //Collector reverse button
    JoystickButton Conveyoron = new JoystickButton(Actionjoy, 10);              //Conveyor hold to turn on
    JoystickButton Conveyorrev = new JoystickButton(Actionjoy, 11);             //Conveyor reverse button
    JoystickButton bananabutton = new JoystickButton(Actionjoy, 1);             //<---- self explanatory
    JoystickButton bridge = new JoystickButton(Actionjoy, 8);
    //DigitalInput limitSwitch = new DigitalInput(1);
    
    public void autonomous() {
        if (true && isAutonomous() && isEnabled()) {
            for (int i = 0; i < 4; i++) {                                       //runs this code block four times
                drive.drive(0.5, 0.0);                                          //drive 50% fwd 0% turn 
                Timer.delay(2.0);                                               //wait 2 seconds 
            }
        }
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        while (true && isOperatorControl() && isEnabled())                      //Run this code while it is in Teleop
        {
            drive.tankDrive(leftStick.getY(), rightStick.getY());               //tank method in bens code
            Timer.delay(0.005);                                                 //necessarily delays loop
            
            //Conveyor
            if (Conveyoron.get() == true){                                      //if conveyor should be on
                 if (Conveyorrev.get() == true){                                //if it should be in reverse
                        conveyor.set(Relay.Value.kReverse);                     //set motor going in reverse
                 }
                 else {
                        conveyor.set(Relay.Value.kForward);                     //set motor going in standard direction
                 }
            }
            else {
                conveyor.set(Relay.Value.kOff);                                 //turn motor off
            }
            
            //Collector
            if (Collectoron.get() == true){                                     //if collector should be on
                 if (Collectorrev.get() == true){                               //if it should be in reverse
                        collector.set(Relay.Value.kReverse);                    //set motor reverse (flipped because motor is inverted)
                 }
                 else {
                        collector.set(Relay.Value.kForward);                    //set motor forward (flipped because motor is inverted)
                 }
            }
            else {
                collector.set(Relay.Value.kOff);                                //set motor off
            }            
            
            //Shooter
            double shootythingamajig = Actionjoy.getThrottle();
            shooter.set(Actionjoy.getY());                                      //set the motor the the value of the action joy Yaxis
            
            //Banana
            if (bananabutton.get() == true){                                    //if banana should be on
                banana.set(0.1);                                                //sets banana to the far right
            }
            else{                                               
                banana.set(0.35);                                               //pulls back to starting position
            }    
            
            //Turret
            turret.set(Actionjoy.getX());                                       //holding off on using this dont wana break things
            
            //Flipy-Floppy thingy (aka aiming board at request of Kyle B's mom)
            Flipyfloppy.set(Actionjoy.getY());
            
            //Compressor
            compressor.start();
            
            //Solenoid
            if (bridge.get() == true){
                if(bridgeout.get() == true){
                    bridgeout.set(false);
                    bridgein.set(true);
                }
                else{
                    bridgeout.set(true);
                    bridgein.set(false);
                }
            }
        }
    }
}