package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="DriveClass",group = "NewModes")
public class DriveClass extends OpMode {

    mechanumDrive drive = new mechanumDrive("tL","tR","bL","bR");

    boolean leftBumper,
            rightBumper,
            dpadUp,
            dpadDown,
            dpadLeft,
            dpadRight;

    public void init(){
        drive.init(hardwareMap);

        leftBumper = gamepad1.left_bumper;
        rightBumper = gamepad1.right_bumper;
        dpadUp = gamepad1.dpad_up;
        dpadDown = gamepad1.dpad_down;
        dpadLeft = gamepad1.dpad_left;
        dpadRight = gamepad2.dpad_right;
    }

    public void init_loop(){
        telemetry.addData("Notice","Press B to disable encoder drive");
        if (gamepad1.b){
            drive.disableEncoders();
            telemetry.addData("Drive","Encoder based Control is disabled");
        }
        telemetry.update();
    }

    public void loop(){

        // Use bumpers to change overall speed
        if (leftBumper) {
            drive.setSpeedScale(1.0);
        } else if (rightBumper) {
            drive.setSpeedScale(0.3);
        }

        if (dpadUp){
            drive.strafe("forward",0.3);
        } else if (dpadDown) {
            drive.strafe("backward",0.3);
        } else if (dpadLeft) {
            drive.strafe("left",0.3);
        } else if (dpadRight) {
            drive.strafe("right",0.3);
        } else if (!dpadUp && !dpadDown && !dpadRight && !dpadLeft) {
            drive.stop();
        }

        drive.drive(gamepad1);
    }
}
