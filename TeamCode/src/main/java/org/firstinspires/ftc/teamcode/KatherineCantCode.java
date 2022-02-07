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
@Autonomous(name = "KatherineCantCode", group = "")
public class KatherineCantCode extends LinearOpMode {

  private DcMotor test;
  
    
  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    test = hardwareMap.dcMotor.get("test");
    
    // Put initialization blocks here.
    waitForStart();
    
    test.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    
    if (opModeIsActive()) {
      while (opModeIsActive()) {
        
        //strafe to the right
        test.setPower(0.7);
        sleep(1500);
       
        //test.setPower(-0.3);
        //sleep(1500);

        
        }
      }
    }
  }

