package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.util.ElapsedTime
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotorSimple
import kotlin.math.*

@TeleOp(name = "TeleOpBackUp_Kt", group = "TEST")
class TeleOpBackUp_Kt : OpMode() {
    private val runtime = ElapsedTime()

    lateinit var fL: DcMotor
    lateinit var fR: DcMotor
    lateinit var bL: DcMotor
    lateinit var bR: DcMotor
    lateinit var lduck: DcMotor
    lateinit var rduck: DcMotor
    lateinit var intake: DcMotor
    lateinit var arm: DcMotor
    lateinit var push: CRServo

    // limit switches
    // private DigitalChannel left, right, front, back, block;
    override fun init() {

        // initialize motor variables with references to the physical motors
        bL = hardwareMap.dcMotor["bL"]
        bR = hardwareMap.dcMotor["bR"]
        fL = hardwareMap.dcMotor["fL"]
        fR = hardwareMap.dcMotor["fR"]
        lduck = hardwareMap.dcMotor["lduck"]
        rduck = hardwareMap.dcMotor["rduck"]
        push = hardwareMap.crservo["push"]
        arm = hardwareMap.dcMotor["arm"]
        intake = hardwareMap.dcMotor["intake"]

        // set all motors to reverse
        // bR.setDirection(DcMotorSimple.Direction.REVERSE);
        fR.direction = DcMotorSimple.Direction.REVERSE
        bL.direction = DcMotorSimple.Direction.REVERSE
        // fL.setDirection(DcMotorSimple.Direction.REVERSE);

        // set all motors to brake
        arm.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        lduck.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        rduck.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        bL.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        bR.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        fR.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        fL.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    }

    override fun loop() {
        // gamepad1 is a reference to the gamepad
        // This gets the current gamepad positions to use in the program, initializes a
        // few
        // variables, and does a little Math

        // name the variables
        val aButton = gamepad1.a
        val bButton = gamepad1.b
        val xButton = gamepad1.x
        val yButton = gamepad1.y
        val leftBumper = gamepad1.left_bumper
        val rightBumper = gamepad1.right_bumper
        val yButton2 = gamepad2.y
        val aButton2 = gamepad2.a
        val xButton2 = gamepad2.x
        val bButton2 = gamepad2.b
        val leftBumper2 = gamepad2.left_bumper
        val rightBumper2 = gamepad2.right_bumper
        val leftTrigger = gamepad1.left_trigger.toDouble() // moves arm down
        val rightTrigger = gamepad1.right_trigger.toDouble() // moves arm up
        // double rightBumper2 = gamepad2.right_bumper;
        val leftTrigger2 = gamepad2.left_trigger.toDouble()
        val leftStickY2 = gamepad2.left_stick_y.toDouble()
        val rightStickY2 = gamepad2.right_stick_y.toDouble()
        val ISX = gamepad1.left_stick_x.toDouble()
        val ISY = gamepad1.left_stick_y.toDouble()
        val rSX = gamepad1.right_stick_x.toDouble()
        var LF = 0.0
        var LR = 0.0
        var RF = 0.0
        var RR = 0.0
        val r = Math.sqrt(Math.pow(ISX, 2.0) + Math.pow(ISY, 2.0))
        var theta = Math.atan2(ISY, ISX)
        // double rightStickX = gamepad1.right_stick_x;

        // Gamepad 1 controls

        // if something on button pad is pressed, strafe slowly for precision
        if (aButton) {
            bL.power = 0.3
            bR.power = 0.3
            fR.power = 0.3
            fL.power = 0.3
        }
        if (bButton) {
            // b button : slowly moves to right
            fR.power = -0.3
            bL.power = -0.3
            fL.power = 0.3
            bR.power = 0.3
        }
        if (xButton) {
            // x button: slowly moves to left
            fR.power = 0.3
            bL.power = 0.3
            fL.power = -0.3
            bR.power = -0.3
        }
        if (yButton) {
            bL.power = -0.3
            bR.power = -0.3
            fR.power = -0.3
            fL.power = -0.3
        }
        telemetry.addData("moving", rSX)
        telemetry.update()
        // shifts theta by 45 degrees
        theta -= Math.PI / 4

        // convert magnitude/direction to x/y components
        val x = r * cos(theta)
        val y = r * sin(theta)

        // Convert unit circle values to unit square values
        var x2 = 0.0
        var y2 = 0.0
        if (x.pow(2.0) >= y.pow(2.0)) {
            x2 = sign(x) * abs(sqrt(x.pow(2.0) + y.pow(2.0)))
            y2 = sign(y) * abs(y * sqrt(x.pow(2.0) + y.pow(2.0)) / x)
        } else if (x.pow(2.0) < y.pow(2.0)) {
            x2 = sign(x) * abs(x * sqrt(x.pow(2.0) + y.pow(2.0)) / y)
            y2 = sign(y) * abs(sqrt(x.pow(2.0) + y.pow(2.0)))
        }
        if (java.lang.Double.isNaN(x2)) {
            x2 = 0.0
        }
        if (java.lang.Double.isNaN(y2)) {
            y2 = 0.0
        }

        // Set motor speeds
        LF = y2
        RR = y2
        RF = x2
        LR = x2
        if (ISY != 0.0 || ISX != 0.0 || rSX != 0.0) {

            // if right trigger is pressed, drive more slowly
            if (rightTrigger != 0.0) {
                // cube the powers to provide the wheels with more control
                fL.power = -0.3 * (LF - rSX).pow(3.0)
                bR.power = -0.3 * (RR + rSX).pow(3.0)
                fR.power = -0.3 * (LR + rSX).pow(3.0)
                bL.power = -0.3 * (LR - rSX).pow(3.0)
            } else {
                fL.power = 0.5 * (LF - rSX).pow(3.0)
                bR.power = 0.5 * (RR + rSX).pow(3.0)
                fR.power = 0.5 * (RF + rSX).pow(3.0)
                bL.power = 0.5 * (LR - rSX).pow(3.0)
            }
        }

        // give gamepad1 left duck spin on left bumper
        if (leftBumper) {
            lduck.power = 0.7
        }
        if (rightBumper) {
            rduck.power = 0.7
        }

        // Gamepad 2

        // right trigger controls intake
        if (rightBumper2) {
            intake.power = 0.7
        }
        if (leftBumper2) {
            intake.power = -0.7
        }

        // move right stick forward to bring arm up and down
        if (rightStickY2 != 0.0) {
            arm.power = 0.7 * rightStickY2.pow(3.0)
        }

        // move left stick forward to push
        if (leftStickY2 != 0.0) {
            push.power = leftStickY2.pow(3.0)
        }

        // automatic controls for second gamepad buttons

        // a intakes for five seconds
        if (aButton2) {
            val setTime = System.currentTimeMillis()
            while (System.currentTimeMillis() - setTime < 500) {
                intake.power = 0.5
            }
        }

        // y outputs cargo on top
        if (yButton2) {
            // arm.setPower(0.7);
            // sleep(1500);
            val setTime = System.currentTimeMillis()
            while (System.currentTimeMillis() - setTime < 1500) {
                arm.power = 0.7
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
            val setTime1 = System.currentTimeMillis()
            while (System.currentTimeMillis() - setTime1 < 700) {
                arm.power = 0.5
            }
            arm.power = 0.0
            val setTime2 = System.currentTimeMillis()
            while (System.currentTimeMillis() - setTime2 < 1000) {
                push.power = 0.5
            }
            push.power = 0.0
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
            val setTime1 = System.currentTimeMillis()
            while (System.currentTimeMillis() - setTime1 < 700) {
                arm.power = 0.5
            }
            arm.power = 0.0
            val setTime2 = System.currentTimeMillis()
            while (System.currentTimeMillis() - setTime2 < 1000) {
                push.power = -0.5
            }
            push.power = 0.0
        }

        // default stop block
        if (gamepad1.left_stick_x == 0f && gamepad1.left_stick_y == 0f && gamepad1.right_stick_x == 0f && gamepad1.right_stick_y == 0f && !xButton && !yButton && !aButton && !bButton && rightStickY2 == 0.0 && leftStickY2 == 0.0 && !rightBumper2 && !leftBumper && !rightBumper
            && !leftBumper2
        ) {
            fR.power = 0.0
            bR.power = 0.0
            fL.power = 0.0
            bL.power = 0.0
            arm.power = 0.0
            push.power = 0.0
            intake.power = 0.0
            lduck.power = 0.0
            rduck.power = 0.0
        }
    }
}