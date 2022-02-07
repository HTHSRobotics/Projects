package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import java.util.ArrayList;
import java.util.List;


//this is the triangle red side: Will move the foundation and park on the tape 
@Autonomous(name = "OldCode", group = "")
public class OldCode extends LinearOpMode {

  private DcMotor bottomleft;
  private DcMotor bottomright;
  private DcMotor topleft;
  private DcMotor topright;
  private DcMotor arm, armOut;
  private CRServo flippy, flippy2; 
 // private ColorSensor hawkeye;

    
  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    bottomleft = hardwareMap.dcMotor.get("bottomleft");
    bottomright = hardwareMap.dcMotor.get("bottomright");
    topleft = hardwareMap.dcMotor.get("topleft");
    topright = hardwareMap.dcMotor.get("topright");
    //`hawkeye = hardwareMap.colorSensor.get("hawkeye");
    arm = hardwareMap.dcMotor.get("arm");
    armOut = hardwareMap.dcMotor.get("armOut");
    flippy = hardwareMap.crservo.get("flippy");
    //flippy2 = hardwareMap.crservo.get("flippy2");
    
    
    //declare array holding all motors
    DcMotor [] all = {bottomleft, bottomright, topleft, topright};
    DcMotor [] lefts = {bottomleft, topleft};
    DcMotor [] rights = {bottomright, topright};

    // Put initialization blocks here.
    waitForStart();
    
    //set right motors to reverse
    bottomright.setDirection(DcMotorSimple.Direction.REVERSE);
    topright.setDirection(DcMotorSimple.Direction.REVERSE);
    //flippy.setDirection(DcMotorSimple.Direction.REVERSE);
    
    for (DcMotor motor : all){
        //set all motors' zero behavior to BRAKE
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    if (opModeIsActive()) {
      while (opModeIsActive()) {
        
        //strafe to the right
        topright.setPower(0.3);
        bottomleft.setPower(0.3);
        bottomright.setPower(-0.3);
        topleft.setPower(-0.3);
        sleep(1000);
        
        //set power of all motors to 0.8
        for (DcMotor motor : all){
            motor.setPower(0.8);
        }
        
        //move for 0.7 seconds
        sleep(700);
        
        for(DcMotor motor: all){
            motor.setPower(0.15);
        }
        sleep(1500);
        
        //grab the foundation
        for(DcMotor motor: all){
            motor.setPower(0.15);
        }
        flippy.setPower(0.7);
        sleep(1100);
        
        for (DcMotor motor: all){
            motor.setPower(0);
        }
        sleep (700);
        

        flippy.setPower(0);
        sleep(500);
        
        //stops for a sec 
        for (DcMotor motor: all){
            motor.setPower(0);
        }
        
        sleep(300);
    
        //back up

        for (DcMotor motor: all){
            motor.setPower(-0.4);
        }
        //flippy.setPower(0.3);
        //flippy2.setPower(0.3);
        sleep (2500);
        
        // //strafe to the left
        // topleft.setPower(-0.5);
        // bottomright.setPower(-0.5);
        // topright.setPower(0.5);
        // bottomleft.setPower(0.5);
        // sleep(1000);
        
        //turn so foundation is in site 
        topright.setPower(0.6);
        bottomright.setPower(0.6);
        topleft.setPower(-0.4);
        bottomleft.setPower(-0.4);
        // flippy.setPower(0);
        sleep (3500);
        
        for (DcMotor motor: all){
            motor.setPower(0);
        }
        sleep(500);
        
        
       //let go of the foundation
        flippy.setPower(0.7);
        sleep(800);
        flippy.setPower(0);
        sleep(500);
    
        for (DcMotor motor: all){
            motor.setPower(0);
        }
        sleep(300);
    
        sleep(1000);
        
        //go backwards until red  
        
        topleft.setPower(-0.4);
        bottomleft.setPower(-0.4);
        topright.setPower(-0.4);
        bottomright.setPower(-0.4);
        sleep(3000);
         
        //stop
        for (DcMotor motor: all){
            motor.setPower(0);
        }
        sleep (30000);
        
        
        
        }
      }
    }
  }

