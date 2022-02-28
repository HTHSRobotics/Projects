package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Skidder_SingleStick", group = "Skidder")
public class SkidderTele_SS extends OpMode {

    private GamepadEx driver;
    double  translateX, translateY, rotate;
    double deadzoneBuffer = 0.045;

    private DcMotor yL, yR, xF, xR;



    // ---------- HELPER FUNCTIONS ---------- //
    private String getMovementStatus(double translateX, double translateY, double rotate) {
        boolean rotating = rotate != 0,
                movingLeftRight = translateX != 0,
                movingFwdBack = translateY != 0;
        if (rotating && movingFwdBack && movingLeftRight){
            return "Moving";
        } else if (!rotating && !movingFwdBack && !movingLeftRight){
            return "Stopped";
        } else if (( rotating ) && ( !movingLeftRight && !movingFwdBack )){
            return "Rotating";
        } else if (( !rotating ) && ( movingLeftRight && movingFwdBack )) {
            return "Translating";
        } else {
            return "Unknown Status";
        }
    }

    // ---------- OPMODE CODE ---------- //
    public void init(){
        driver = new GamepadEx(gamepad1);

        xF = hardwareMap.get(DcMotor.class,"xF"); // Front X-axis Motor
        xR = hardwareMap.get(DcMotor.class, "xR"); // Rear X-axis Motor
        yL = hardwareMap.get(DcMotor.class, "yL"); // Left Y-axis Motor
        yR = hardwareMap.get(DcMotor.class, "yR"); // Right Y-axis Moto

        xF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        xR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        yL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        yR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Initialized");
        telemetry.speak("Ready to start");
    }

    public void init_loop () {
        translateX = driver.getLeftX();
        translateY = driver.getLeftY();
        rotate = driver.getRightX();

        // Add Deadzones
        translateX = Math.abs(translateX) > deadzoneBuffer ? translateX : 0;
        translateY = Math.abs(translateY) > deadzoneBuffer ? translateY : 0;
        rotate = Math.abs(rotate) > deadzoneBuffer ? rotate : 0;

        telemetry.addLine("Reading Inputs");
        telemetry.addData("Translate X", translateX);
        telemetry.addData("Translate Y", translateY);
        telemetry.addData("Rotate", rotate);
    }

    public void start() {
        telemetry.clear();
    }

    public void loop(){
        translateX = driver.getLeftX();
        translateY = driver.getLeftY();
        rotate = driver.getRightX();

        double  yL_power = 0,
                yR_power = 0,
                xF_power = 0,
                xR_power = 0;

        double max;

        boolean translateOnly = rotate == 0,
                rotateOnly = (translateX == 0) && (translateY == 0);

        boolean zeroPwrBrake = false;

        ButtonReader enableZeroPwrBrake = new ButtonReader(driver, GamepadKeys.Button.DPAD_UP),
                    disableZeroPwrBrake = new ButtonReader(driver, GamepadKeys.Button.DPAD_DOWN);



        // Combine translate and rotation values
        yL_power = -translateY - rotate;
        yR_power = -translateY + rotate;

        xF_power = translateX + rotate;
        xR_power = -translateX + rotate;

        // Add Deadzones
        yL_power = Math.abs(yL_power) < deadzoneBuffer ? 0 : yL_power;
        yR_power = Math.abs(yR_power) < deadzoneBuffer ? 0 : yR_power;
        xF_power = Math.abs(xF_power) < deadzoneBuffer ? 0 : xF_power;
        xR_power = Math.abs(xR_power) < deadzoneBuffer ? 0 : xR_power;

        // Find highest power level
        max = Math.max(Math.abs(yL_power), Math.abs(yR_power));
        max = Math.max(max, Math.abs(xF_power));
        max = Math.max(max, Math.abs(xR_power));

        // If max is greater than 1.0 (motor maximum power), normalize power values
        if (max > 1.0) {
            yL_power /= max;
            yR_power /= max;

            xF_power /= max;
            xR_power /= max;
        }

        // Change zeroPwrBrake state from button inputs
        if (enableZeroPwrBrake.wasJustPressed()) {
            zeroPwrBrake = true;

            xF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            xR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            yL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            yR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } else if (disableZeroPwrBrake.wasJustReleased()) {
            zeroPwrBrake = false;

            xF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            xR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            yL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            yR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }

        String movementStatus = getMovementStatus(translateX, translateY, rotate);

        telemetry.addLine("Robot Status");
        telemetry.addData("Status", movementStatus);
        telemetry.addData("ZeroPwr", zeroPwrBrake ? "BRAKE" : "FLOAT");
        telemetry.addData("PowerScaling", max > 1.0 ? "SCALING ACTIVE" : "SCALING INACTIVE");
        telemetry.addData("MaxPowerValue", max);
        telemetry.addData("Front Motor", xF_power);
        telemetry.addData("Rear Motor", xR_power);
        telemetry.addData("Left Motor", yL_power);
        telemetry.addData("Right Motor", yR_power);

        yL.setPower(yL_power);
        yR.setPower(yR_power);
        xF.setPower(xF_power);
        xR.setPower(xR_power);
    }
}