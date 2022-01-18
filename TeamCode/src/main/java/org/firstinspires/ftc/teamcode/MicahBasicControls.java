package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.hardware.TouchSensor;
@Autonomous

public class Micah1 extends LinearOpMode{
  public TouchSensor limit;
  
  public void runOpMode(){
    limit = hardwareMap.TouchSensor.get("limit");
    //light = hardwareMap.dcMotor.get("name");
    while (True){
      
      if(limit.isPressed()){
        telemetry.clearAll();
        telemetry.addLine("limit");
        telemetry.update();
      }
      else{
        telemetry.clearAll();
        telemetry.addLine("notlimit");
        telemetry.update();
        
      }
    }
  }
  
  
  
 
    
  
  
  
   
}