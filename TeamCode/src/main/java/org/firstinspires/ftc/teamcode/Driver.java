/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Driver", group = "old")
public class Driver extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor topright, bottomright, topleft, bottomleft, arm, armOut;
    private CRServo hand, flippy;
    // private DigitalChannel digitalTouch;

    @Override
    public void init() {
        // initialize motor variables with references to the physical motors
        bottomleft = hardwareMap.dcMotor.get("bL");
        bottomright = hardwareMap.dcMotor.get("bR");
        topleft = hardwareMap.dcMotor.get("tL");
        topright = hardwareMap.dcMotor.get("tR");
        // hand = hardwareMap.crservo.get("hand");
        // grabber = hardwareMap.servo.get("grabber");
        arm = hardwareMap.dcMotor.get("arm");
        armOut = hardwareMap.dcMotor.get("armOut");
        flippy = hardwareMap.crservo.get("flippy");
        // flippy2 = hardwareMap.crservo.get("flippy2");

        // set left motors to reverse
        bottomleft.setDirection(DcMotorSimple.Direction.REVERSE);
        topleft.setDirection(DcMotorSimple.Direction.REVERSE);
        flippy.setDirection(DcMotorSimple.Direction.REVERSE);
        arm.setDirection(DcMotorSimple.Direction.REVERSE);
        // bottomleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // bottomright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // topright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // topleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // armOut.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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
                yButton2 = gamepad2.y,
                aButton2 = gamepad2.a,
                xButton2 = gamepad2.x,
                bButton2 = gamepad2.b,
                leftBumper2 = gamepad2.left_bumper,
                rightBumper2 = gamepad2.right_bumper;
        double leftTrigger = gamepad1.left_trigger; // moves arm down
        double rightTrigger = gamepad1.right_trigger; // moves arm up
        double rightTrigger2 = gamepad2.right_trigger;
        double leftTrigger2 = gamepad2.left_trigger;
        double leftStickY2 = gamepad2.left_stick_y,
                rightStickY2 = gamepad2.right_stick_y;
        double ISX = gamepad1.left_stick_x,
                ISY = gamepad1.left_stick_y,
                rSX = gamepad1.right_stick_x,
                LF = 0, LR = 0, RF = 0, RR = 0, r = Math.sqrt(Math.pow(ISX, 2) + Math.pow(ISY, 2));
        double theta = Math.atan2(ISY, ISX);

        // Gamepad 1 controls
        // turning with bumpers
        if (leftBumper2) {
            topright.setPower(0.3);
            bottomright.setPower(0.3);
            topleft.setPower(-0.3);
            bottomleft.setPower(-0.3);
        }
        if (rightBumper2) {
            topleft.setPower(0.3);
            bottomleft.setPower(0.3);
            topright.setPower(-0.3);
            bottomright.setPower(-0.3);
        }

        // if something on button pad is pressed, strafe slowly for precision
        if (aButton2) {
            bottomleft.setPower(0.3);
            bottomright.setPower(0.3);
            topright.setPower(0.3);
            topleft.setPower(0.3);
        } else if (bButton2) {
            // b button : slowly moves to right
            topright.setPower(-0.3);
            bottomleft.setPower(-0.3);
            topleft.setPower(0.3);
            bottomright.setPower(0.3);
        } else if (xButton2) {
            // x button: slowly moves to left
            topright.setPower(0.3);
            bottomleft.setPower(0.3);
            topleft.setPower(-0.3);
            bottomright.setPower(-0.3);
        } else if (yButton2) {
            bottomleft.setPower(-0.3);
            bottomright.setPower(-0.3);
            topright.setPower(-0.3);
            topleft.setPower(-0.3);
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

        // cube the powers to provide the wheels with more control
        topleft.setPower(0.5 * Math.pow(LF + rSX, 3));
        bottomright.setPower(0.5 * Math.pow(RR - rSX, 3));
        topright.setPower(0.5 * Math.pow(RF - rSX, 3));
        bottomleft.setPower(0.5 * Math.pow(LR + rSX, 3));

        // Gamepad 2

        // b and x button control flippy
        if (rightTrigger2 != 0) {
            flippy.setPower(0.3);
        }
        if (leftTrigger2 != 0) {
            flippy.setPower(-0.3);
            // flippy2.setPower(-0.3);
        } else {
            flippy.setPower(0);

        }

        // move right stick forward to bring arm up and down
        if (rightStickY2 != 0) {
            arm.setPower(Math.pow(rightStickY2, 3));
        }

        // move left stick forward to bring arm out and back
        if (leftStickY2 != 0) {
            armOut.setPower(Math.pow(leftStickY2, 3));
        }

        if (yButton2) {
            arm.setPower(-0.9);
        }
        if (aButton2) {
            arm.setPower(0.5);
        }

        // default stop block
        topright.setPower(0);
        bottomright.setPower(0);
        topleft.setPower(0);
        bottomleft.setPower(0);
        arm.setPower(0);
        armOut.setPower(0);

        // code the limit switch
        // digitalTouch = hardwareMap.get(DigitalChannel.class, "sensor_digital");

        // // set the digital channel to input.
        // digitalTouch.setMode(DigitalChannel.Mode.INPUT);

        // // send the info back to driver station using telemetry function.
        // // if the digital channel returns true it's HIGH and the button is unpressed.
        // if (digitalTouch.getState() == true) {
        // telemetry.addData("Digital Touch", "Is Not Pressed");
        // } else {
        // telemetry.addData("Digital Touch", "Is Pressed");
        // arm.setPower(0);
        // }

    }
}
