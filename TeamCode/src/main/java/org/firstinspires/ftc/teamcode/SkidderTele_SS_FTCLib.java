package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.controller.PDController;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Skidder_SingleStick_New", group = "Skidder")
@Disabled
public class SkidderTele_SS_FTCLib extends OpMode {

    private GamepadEx driver;
    double  translateX, translateY, rotate;

    private Motor yL, yR, xF, xR;

    private PDController yL_curve, yR_curve, xF_curve, xR_curve;

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

        yL = new Motor(hardwareMap, "yL"); // Left Y-axis Motor
        yR = new Motor(hardwareMap, "yR"); // Right Y-axis Motor
        xF = new Motor(hardwareMap, "xF"); // Front X-axis Motor
        xR = new Motor(hardwareMap, "xR"); // Rear X-axis Motor

        telemetry.addData("Status", "Initialized");
        telemetry.speak("Ready to start");
    }

    public void init_loop () {
        translateX = driver.getLeftX();
        translateY = driver.getLeftY();
        rotate = driver.getRightX();

        // Add Deadzones
        double deadzoneBuffer = 0.019;
        translateX = translateX > deadzoneBuffer ? translateX : 0;
        translateY = translateY > deadzoneBuffer ? translateY : 0;
        rotate = rotate > deadzoneBuffer ? rotate : 0;

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

//        if (translateOnly) {
//            yL_power = -translateY;
//            yR_power = translateY;
//            xF_power = -translateX;
//            xR_power = translateX;
//        } else if (rotateOnly) {
//            yL_power = -translateY;
//            yR_power = translateY;
//            xF_power = -translateX;
//            xR_power = translateX;
//        }

        // Add Deadzones
        double deadzoneBuffer = 0.019;
        translateX = translateX > deadzoneBuffer ? translateX : 0;
        translateY = translateY > deadzoneBuffer ? translateY : 0;
        rotate = rotate > deadzoneBuffer ? rotate : 0;

        // Combine translate and rotation values
        yL_power = -translateY - rotate;
        yR_power = -translateY + rotate;

        xF_power = translateX + rotate;
        xR_power = -translateX + rotate;

        // Compute maximum value
        max = Math.max(Math.abs(yL_power), Math.abs(yR_power));
        max = Math.max(max, Math.abs(xF_power));
        max = Math.max(max, Math.abs(xR_power));

        // If max is greater than 1.0 (motor maximum power), scale power values
        if (max > 1.0) {
            yL_power /= max;
            yR_power /= max;

            xF_power /= max;
            xR_power /=max;
        }
        
        String movementStatus = getMovementStatus(translateX, translateY, rotate);

        telemetry.addLine("Robot Status");
        telemetry.addData("Status", movementStatus);
        telemetry.addData("Front Motor", xF_power);
        telemetry.addData("Rear Motor", xR_power);
        telemetry.addData("Left Motor", yL_power);
        telemetry.addData("Right Motor", yR_power);

        yL.set(yL_power);
        yR.set(yR_power);
        xF.set(xF_power);
        xR.set(xR_power);
    }
}
