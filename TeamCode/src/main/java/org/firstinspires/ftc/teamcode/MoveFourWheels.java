package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class MoveFourWheels extends LinearOpMode{
  public DcMotor one;
  public DcMotor two;
  public DcMotor three;
  public DcMotor four;
  public gamepad1 gamepad;
  public void runOpMode(){
    one = hardwareMap.dcMotor.get("one");
    two = hardwareMap.dcMotor.get("two");
    three = hardwareMap.dcMotor.get("three");
    four = hardwareMap.dcMotor.get("four");
    one.setPower(-0.4);
    two.setPower(0.4);
    three.setPower(0.4);
    four.setPower(-0.4);
    
    sleep(2000);
    one.setPower(0);
    two.setPower(0);
    three.setPower(0);
    four.setPower(0);
    
  }
  }