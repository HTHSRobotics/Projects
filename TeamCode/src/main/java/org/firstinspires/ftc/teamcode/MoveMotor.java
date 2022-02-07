package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous 

public class MoveMotor extends LinearOpMode {
    private DcMotor John;

    public void runOpMode(){
        
        John = hardwareMap.dcMotor.get("tR");
        John.setPower(0.8); //set Motor John's power to 0.5
        sleep(30000); //let Motor John spin for 0.9 seconds
        }//end of method 
} //end of class 
