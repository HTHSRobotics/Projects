package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="TestMotorAuto",group="TEST")
public class TestMotorAuto extends LinearOpMode {
    private DcMotor motor;


    public void runOpMode(){
        motor = hardwareMap.get(DcMotor.class,"motor");
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        waitForStart();

        telemetry.addData("Speed", "0.1");
        motor.setPower(0.1);
        telemetry.update();
        sleep(2000);

        telemetry.addData("Speed", "0.2");
        motor.setPower(0.2);
        telemetry.update();
        sleep(2000);

        telemetry.addData("Speed", "0.4");
        motor.setPower(0.4);
        telemetry.update();
        sleep(2000);

        telemetry.addData("Speed", "0.8");
        motor.setPower(0.8);
        sleep(2000);

        telemetry.addData("Speed", "1.0");
        motor.setPower(1.0);
        telemetry.update();
        sleep(2000);
    }
}
