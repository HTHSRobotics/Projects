package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.controller.wpilibcontroller.ElevatorFeedforward;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;


@TeleOp(name = "BerthaOperate", group = "NEW")
@Disabled
public class New_BerthaOperate extends OpMode {
    private GamepadEx driver, operator;
    private Gamepad driverCtrl, operatorCtrl;

    private Gamepad.RumbleEffect speedNormModeNotify, speedSlowModeNotify;

    private Motor fL, fR, bL, bR, arm, intake, duckL, duckR;
    private CRServo eject;
    private MotorGroup ducks;

    private MecanumDrive drive;
    private ElevatorFeedforward lift;


    public void init() {
        // -------------------- GAMEPAD -------------------- //
        driver = new GamepadEx(gamepad1);
        operator = new GamepadEx(gamepad2);
        driverCtrl = gamepad1;
        operatorCtrl = gamepad2;

        speedNormModeNotify = new Gamepad.RumbleEffect.Builder()
                .addStep(1.0,0.0,100)
                .addStep(0.0,0.0,50)
                .addStep(1.0,0.0,100)
                .build();
        speedSlowModeNotify = new Gamepad.RumbleEffect.Builder()
                .addStep(0.75,0.0,100)
                .addStep(0.50,0.0,100)
                .addStep(0.25,0.0,100)
                .build();


        // -------------------- DRIVE -------------------- //
        fL = new Motor(hardwareMap, "fL");
        fR = new Motor(hardwareMap, "fR");
        bL = new Motor(hardwareMap, "bL");
        bR = new Motor(hardwareMap, "bR");

        drive = new MecanumDrive(fL, fR, bL, bR);


        // -------------------- IMPLEMENTS -------------------- //
        arm = new Motor(hardwareMap, "arm");
        intake = new Motor(hardwareMap, "intake");
        duckL = new Motor(hardwareMap, "lduck");
        duckR = new Motor(hardwareMap, "rduck");
        eject = new CRServo(hardwareMap, "push");

        ducks = new MotorGroup(duckL, duckR);

        ducks.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        arm.setRunMode(Motor.RunMode.VelocityControl);


        telemetry.log().setDisplayOrder(Telemetry.Log.DisplayOrder.OLDEST_FIRST);
    }

    public void loop(){

        // -------------------- DRIVER CONTROLS -------------------- //

        telemetry.addLine("DRIVER STATUS");

        double strafe = driver.getLeftX(),
                fwd = driver.getLeftY(),
                rotate = driver.getRightX();

        // Use left stick button to toggle slow mode
        ToggleButtonReader slowModeToggle = new ToggleButtonReader(driver, GamepadKeys.Button.LEFT_STICK_BUTTON);

        if (slowModeToggle.getState()) {
            telemetry.addData("Drive Mode","Slowed");
            strafe *= 0.5;
            fwd *= 0.5;
            rotate *= 0.5;
        } else {
            telemetry.addData("Drive Mode","Normal");
        }

        if (slowModeToggle.stateJustChanged() && !driverCtrl.isRumbling()) { // Trigger rumble to notify driver of speed mode
            if (slowModeToggle.getState()) {
                driverCtrl.runRumbleEffect(speedSlowModeNotify);
            } else {
                driverCtrl.runRumbleEffect(speedNormModeNotify);
            }
        }

        telemetry.addData("Lateral X Speed","%s", strafe);
        telemetry.addData("Lateral Y Speed", "%s", fwd);
        telemetry.addData("Rotational Speed","%s",rotate);

        drive.driveRobotCentric(strafe, fwd, rotate);


        // -------------------- OPERATOR CONTROLS -------------------- //


    }
}
