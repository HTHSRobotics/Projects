package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "PCTeleOp", group = "")
public class PCTeleOp extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor fL, fR, bL, bR, lduck, rduck, intake, arm;
    private CRServo push;

    // limit switches
    // private DigitalChannel left, right, front, back, block;

    @Override
    public void init() {

        // initialize motor variables with references to the physical motors
        bL = hardwareMap.dcMotor.get("bL");
        bR = hardwareMap.dcMotor.get("bR");
        fL = hardwareMap.dcMotor.get("fL");
        fR = hardwareMap.dcMotor.get("fR");
        lduck = hardwareMap.dcMotor.get("lduck");
        rduck = hardwareMap.dcMotor.get("rduck");
        push = hardwareMap.crservo.get("push");
        arm = hardwareMap.dcMotor.get("arm");
        intake = hardwareMap.dcMotor.get("intake");

        // set motors to reverse
        // bR.setDirection(DcMotorSimple.Direction.REVERSE);
        fR.setDirection(DcMotorSimple.Direction.REVERSE);
        bL.setDirection(DcMotorSimple.Direction.REVERSE);
        // tL.setDirection(DcMotorSimple.Direction.REVERSE);

        // set all motors to brake
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lduck.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rduck.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    @Override
    public void loop() {
        // gamepad1 is a reference to the gamepad
        // This gets the current gamepad positions to use in the program, initializes a
        // few
        // variables, and does a little Math

        // name the variables
        boolean aButton = gamepad1.a,
                bButton = gamepad1.b,
                xButton = gamepad1.x,
                yButton = gamepad1.y,
                leftBumper = gamepad1.left_bumper,
                rightBumper = gamepad1.right_bumper,
                yButton2 = gamepad2.y,
                aButton2 = gamepad2.a,
                xButton2 = gamepad2.x,
                bButton2 = gamepad2.b,
                leftBumper2 = gamepad2.left_bumper,
                rightBumper2 = gamepad2.right_bumper;
        double leftTrigger = gamepad1.left_trigger; // moves arm down
        double rightTrigger = gamepad1.right_trigger; // moves arm up
        // double rightBumper2 = gamepad2.right_bumper;
        double leftTrigger2 = gamepad2.left_trigger;
        double leftStickY2 = gamepad2.left_stick_y,
                rightStickY2 = gamepad2.right_stick_y;
        double ISX = gamepad1.left_stick_x,
                ISY = gamepad1.left_stick_y,
                rSX = gamepad1.right_stick_x,
                LF = 0, LR = 0, RF = 0, RR = 0, r = Math.sqrt(Math.pow(ISX, 2) + Math.pow(ISY, 2));
        double theta = Math.atan2(ISY, ISX);
        // double rightStickX = gamepad1.right_stick_x;

        // Gamepad 1 controls

        // if something on button pad is pressed, strafe slowly for precision
        if (aButton) {
            bL.setPower(0.3);
            bR.setPower(0.3);
            fR.setPower(0.3);
            fL.setPower(0.3);
        }
        if (bButton) {
            // b button : slowly moves to right
            fR.setPower(-0.3);
            bL.setPower(-0.3);
            fL.setPower(0.3);
            bR.setPower(0.3);
        }
        if (xButton) {
            // x button: slowly moves to left
            fR.setPower(0.3);
            bL.setPower(0.3);
            fL.setPower(-0.3);
            bR.setPower(-0.3);
        }
        if (yButton) {
            bL.setPower(-0.3);
            bR.setPower(-0.3);
            fR.setPower(-0.3);
            fL.setPower(-0.3);
        }

        telemetry.addData("moving", rSX);
        telemetry.update();
        // shifts theta by 45 degrees
        theta -= (Math.PI / 4);

        // convert magnitude/direction to x/y components
        double x = r * Math.cos(theta);
        double y = r * Math.sin(theta);

        // Convert unit circle values to unit square values
        double x2 = 0;
        double y2 = 0;

        if (Math.pow(x, 2) >= Math.pow(y, 2)) {
            x2 = Math.signum(x) * Math.abs(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
            y2 = Math.signum(y) * Math.abs((y * Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2))) / x);
        } else if (Math.pow(x, 2) < Math.pow(y, 2)) {
            x2 = Math.signum(x) * Math.abs((x * Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2))) / y);
            y2 = Math.signum(y) * Math.abs(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
        }

        if (Double.isNaN(x2)) {
            x2 = 0;
        }

        if (Double.isNaN(y2)) {
            y2 = 0;
        }

        // Set motor speeds
        LF = y2;
        RR = y2;
        RF = x2;
        LR = x2;
        if (ISY != 0 || ISX != 0 || rSX != 0) {

            // if right trigger is pressed, drive more slowly
            if (rightTrigger != 0) {
                // cube the powers to provide the wheels with more control
                fL.setPower(-0.3 * Math.pow(LF - rSX, 3));
                bR.setPower(-0.3 * Math.pow(RR + rSX, 3));
                fR.setPower(-0.3 * Math.pow(LR + rSX, 3));
                bL.setPower(-0.3 * Math.pow(LR - rSX, 3));
            }

            else {
                fL.setPower(0.5 * Math.pow(LF - rSX, 3));
                bR.setPower(0.5 * Math.pow(RR + rSX, 3));
                fR.setPower(0.5 * Math.pow(RF + rSX, 3));
                bL.setPower(0.5 * Math.pow(LR - rSX, 3));
            }

        }

        // give gamepad1 left duck spin on left bumper
        if (leftBumper) {
            lduck.setPower(0.7);
        }

        if (rightBumper) {
            rduck.setPower(0.7);
        }

        // Gamepad 2

        // right trigger controls intake
        if (rightBumper2) {
            intake.setPower(0.7);
        }

        if (leftBumper2) {
            intake.setPower(-0.7);
        }

        // move right stick forward to bring arm up and down
        if (rightStickY2 != 0) {
            arm.setPower(0.7 * Math.pow(rightStickY2, 3));
        }

        // move left stick forward to push
        if (leftStickY2 != 0) {
            push.setPower(Math.pow(leftStickY2, 3));
        }

        // automatic controls for second gamepad buttons

        // a intakes for five seconds
        if (aButton2) {
            long setTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - setTime < 500) {
                intake.setPower(0.5);
            }
        }

        // y outputs cargo on top
        if (yButton2) {
            // arm.setPower(0.7);
            // sleep(1500);
            long setTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - setTime < 1500) {
                arm.setPower(0.7);
            }
        }

        // x outputs cargo to left
        if (xButton2) {
            // arm.setPower(0.5);
            // sleep(700);
            // arm.setPower(0);
            // sleep(200);
            // push.setPower(0.5);
            // sleep(1000);
            // push.setPower(0);
            // sleep(200);

            long setTime1 = System.currentTimeMillis();
            while (System.currentTimeMillis() - setTime1 < 700) {
                arm.setPower(0.5);
            }
            arm.setPower(0);

            long setTime2 = System.currentTimeMillis();
            while (System.currentTimeMillis() - setTime2 < 1000) {
                push.setPower(0.5);
            }
            push.setPower(0);
        }

        // b outputs cargo to right
        if (bButton2) {
            // arm.setPower(0.5);
            // sleep(700);
            // arm.setPower(0);
            // sleep(200);
            // push.setPower(-0.5);
            // sleep(1000);
            // push.setPower(0);
            // sleep(200);

            long setTime1 = System.currentTimeMillis();
            while (System.currentTimeMillis() - setTime1 < 700) {
                arm.setPower(0.5);
            }
            arm.setPower(0);

            long setTime2 = System.currentTimeMillis();
            while (System.currentTimeMillis() - setTime2 < 1000) {
                push.setPower(-0.5);
            }
            push.setPower(0);

        }

        // default stop block

        if (gamepad1.left_stick_x == 0 && gamepad1.left_stick_y == 0
                && gamepad1.right_stick_x == 0 && gamepad1.right_stick_y == 0
                && !xButton && !yButton && !aButton && !bButton && rightStickY2 == 0
                && leftStickY2 == 0 && !rightBumper2 && !leftBumper && !rightBumper
                && !leftBumper2) {
            fR.setPower(0);
            bR.setPower(0);
            fL.setPower(0);
            bL.setPower(0);
            arm.setPower(0);
            push.setPower(0);
            intake.setPower(0);
            lduck.setPower(0);
            rduck.setPower(0);

        }

    }
}