package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@Autonomous(name = "RedWare", group = "Old")
public class RedWare extends LinearOpMode {
    private DcMotor fL, fR, bL, bR, duck, arm, intake;
    private CRServo push;
    private DigitalChannel block;

    public void move(double speed, int time) {
        fL.setPower(speed);
        fR.setPower(speed);
        bL.setPower(speed);
        bR.setPower(speed);
        sleep(time);
        fL.setPower(0);
        fR.setPower(0);
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
        fL.setPower(speed);
        bL.setPower(-speed);
        fR.setPower(-speed);
        bR.setPower(speed);
        sleep(time);
        fL.setPower(0);
        bL.setPower(0);
        fR.setPower(0);
        bR.setPower(0);

    }

    public void rotate(double speed, int angle) {
        fL.setPower(speed);
        fR.setPower(-speed);
        bL.setPower(speed);
        bR.setPower(-speed);
        sleep(angle * 100);// this is a random guess number
        fL.setPower(0);
        fR.setPower(0);
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
        fL = hardwareMap.dcMotor.get("fL");
        fR = hardwareMap.dcMotor.get("fR");
        duck = hardwareMap.dcMotor.get("duck");
        push = hardwareMap.crservo.get("push");
        arm = hardwareMap.dcMotor.get("arm");
        intake = hardwareMap.dcMotor.get("intake");
        block = hardwareMap.get(DigitalChannel.class, "sensor_digital");
        block.setMode(DigitalChannel.Mode.INPUT);

        // set all motors to reverse
        bR.setDirection(DcMotorSimple.Direction.REVERSE);
        // fR.setDirection(DcMotorSimple.Direction.REVERSE);
        // bL.setDirection(DcMotorSimple.Direction.REVERSE);
        fL.setDirection(DcMotorSimple.Direction.REVERSE);

        // set all motors to brake
        bL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // push.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // duck.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RedWare anything = new RedWare();

        // declare duck motor, spins to get duck off platform

        // declare wheel motors, strafes to cargo
        anything.strafe(0.5, 1500);

        // turns left 90 degrees
        anything.rotate(0.5, 90);
        anything.intake();
        anything.move(-0.5, 5000);
        anything.rotate(-0.5, 90);
        anything.move(0.5, 3000);

        drop();
        anything.strafe(5, 2000);

    }

}
