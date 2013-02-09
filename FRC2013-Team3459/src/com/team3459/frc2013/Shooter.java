/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team3459.frc2013;

/**
 *
 * @author Samsung
 */
public class Shooter {
    
    //Ramps motor speed from current to requested to avoid hurting the motor
    public static double rampMotor(double req, double cur){
        double output=0;
        double error=req-cur;
        if (error<=0.1){
            output=req;
        }
        else{
            output+=cur+(0.1)*(req-cur);
        }
        return output;
    }
    
}
