package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import java.util.ArrayList;
import java.util.List;

@Autonomous(name = "Duck", group = "old")
public class Duck extends LinearOpMode {
    private DcMotor tL, tR, bL, bR, rduck, arm, intake, lduck;
    private CRServo push;

    // private DigitalChannel block;
    public void move(double speed, int time) {
        tL.setPower(speed);
        tR.setPower(speed);
        bL.setPower(speed);
        bR.setPower(speed);
        sleep(time);
        tL.setPower(0);
        tR.setPower(0);
        bL.setPower(0);
        bR.setPower(0);
        sleep(200);
    }

    public void intake() {
        /*
         * while (!block.getstate()){
         * intake.setPower(0.5);
         * sleep(200);
         * }
         * intake.setPower(0);*
         */
    }

    // strafes right if speed is positive, strafes left if negative
    public void strafe(double speed, int time) {
        tL.setPower(speed);
        bL.setPower(-speed);
        tR.setPower(-speed);
        bR.setPower(speed);
        sleep(time);
        tL.setPower(0);
        bL.setPower(0);
        tR.setPower(0);
        bR.setPower(0);

    }

    public void rotate(double speed, int angle) {
        tL.setPower(speed);
        tR.setPower(-speed);
        bL.setPower(speed);
        bR.setPower(-speed);
        sleep(angle * 10);// this is a random guess number
        tL.setPower(0);
        tR.setPower(0);
        bL.setPower(0);
        bR.setPower(0);
    }

    public void drop() {
        arm.setPower(0.5);
        sleep(2000);
        arm.setPower(0);
    }

    public void runOpMode() {
        bL = hardwareMap.dcMotor.get("bL");
        bR = hardwareMap.dcMotor.get("bR");
        tL = hardwareMap.dcMotor.get("tL");
        tR = hardwareMap.dcMotor.get("tR");
        rduck = hardwareMap.dcMotor.get("rduck");
        lduck = hardwareMap.dcMotor.get("lduck");
        push = hardwareMap.crservo.get("push");
        arm = hardwareMap.dcMotor.get("arm");
        intake = hardwareMap.dcMotor.get("intake");
        // block = hardwareMap.get(DigitalChannel.class,"sensor_digital");
        // block.setMode(DigitalChannel.Mode.INPUT);

        // set all motors to reverse
        bR.setDirection(DcMotorSimple.Direction.REVERSE);
        // tR.setDirection(DcMotorSimple.Direction.REVERSE);
        // bL.setDirection(DcMotorSimple.Direction.REVERSE);
        tL.setDirection(DcMotorSimple.Direction.REVERSE);

        // set all motors to brake
        bL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        tR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        tL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // push.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rduck.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // BlueDuck anything = new BlueDuck();

        waitForStart();

        // declare duck motor, spins to get duck off platform
        this.move(1.0, 500);
        this.move(-0.5, 500);
        this.strafe(0.5, 3300);

        this.move(-0.4, 1500);

        rduck.setPower(1.0);
        lduck.setPower(1.0);
        sleep(7000);

        rduck.setPower(0);
        lduck.setPower(0);
        sleep(200);
        this.move(0.5, 900);

        // declare wheel motors, strafes to cargo
        // this.strafe(-0.5,3000);
        // this.move(0.3,3000);
        // this.strafe(-0.5,3000);

        // turns left 90 degrees
        // this.rotate(-0.5,90);
        // this.intake();
        // this.move(-0.5,5000);
        // this.rotate(0.5,90);
        // this.move(0.5,3000);

        // this.drop();
        // this.strafe(-5,2000);

    }

}
