package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor


@Autonomous(name="TestMotorAuto_Kt",group="TEST")
public class TestMotorAuto_Kt: LinearOpMode() {

    lateinit var motor: DcMotor;

    public override fun runOpMode(){
        motor = hardwareMap.get(DcMotor::class.java, "motor")
        telemetry.addData("Status", "Initialized")
        telemetry.update();

        waitForStart();

        telemetry.addData("Speed", "0.1")
        motor.power = 0.1
        telemetry.update()
        sleep(2000)

        telemetry.addData("Speed", "0.2")
        motor.power = 0.2
        telemetry.update()
        sleep(2000)

        telemetry.addData("Speed", "0.4")
        motor.power = 0.4
        telemetry.update()
        sleep(2000)

        telemetry.addData("Speed", "0.8")
        motor.power = 0.8
        sleep(2000)

        telemetry.addData("Speed", "1.0")
        motor.power = 1.0
        telemetry.update()
        sleep(2000)
    }
}