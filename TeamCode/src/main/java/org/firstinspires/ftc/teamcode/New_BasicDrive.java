package org.firstinspires.ftc.teamcode;


import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "JustDrive_Holo", group = "NEW")
public class New_BasicDrive extends OpMode {
    private GamepadEx driver, operator;

    private Motor tL, tR, bL, bR;

    private MecanumDrive drive;


    public void init() {
        driver = new GamepadEx(gamepad1);
        operator = new GamepadEx(gamepad2);

        tL = new Motor(hardwareMap, "tL");
        tR = new Motor(hardwareMap, "tR");
        bL = new Motor(hardwareMap, "bL");
        bR = new Motor(hardwareMap, "bR");

        drive = new MecanumDrive(tL, tR, bL, bR);

    }

    public void loop(){

        // ---------- DRIVER CONTROLS ---------- //

        telemetry.addLine("DRIVER STATUS");

        double strafe = driver.getLeftX(),
                fwd = driver.getLeftY(),
                rotate = driver.getRightX();

        // Use left stick button to toggle slow mode
        ToggleButtonReader slowModeToggle = new ToggleButtonReader(driver, GamepadKeys.Button.LEFT_STICK_BUTTON);

        if (slowModeToggle.getState()) {
            telemetry.addData("Drive Mode","Slowed");
            strafe /= 0.5;
            fwd /= 0.5;
            rotate /= 0.5;
        } else {
            telemetry.addData("Drive Mode","Normal");
        }

        telemetry.addData("Lateral X Speed","%s", strafe);
        telemetry.addData("Lateral Y Speed", "%s", fwd);
        telemetry.addData("Rotational Speed","%s",rotate);

        drive.driveRobotCentric(strafe, fwd, rotate);

        telemetry.update();
    }
}
