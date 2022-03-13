package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "RacerBot", group = "RobotExpo")
public class RacerBotTele extends OpMode {

  private GamepadEx driver;
  double pushLeft, pushRight;

  private Motor driveLeft, driveRight;

  // ---------- HELPER FUNCTIONS ---------- //
  // private String getMovementStatus(double translateX, double translateY, double
  // rotate) {
  // boolean rotating = rotate != 0,
  // movingLeftRight = translateX != 0,
  // movingFwdBack = translateY != 0;
  // if (rotating && movingFwdBack && movingLeftRight){
  // return "Moving";
  // } else if (!rotating && !movingFwdBack && !movingLeftRight){
  // return "Stopped";
  // } else if (( rotating ) && ( !movingLeftRight && !movingFwdBack )){
  // return "Rotating";
  // } else if (( !rotating ) && ( movingLeftRight && movingFwdBack )) {
  // return "Translating";
  // } else {
  // return "Unknown Status";
  // }
  // }

  // ---------- OPMODE CODE ---------- //

  public void init() {
    driver = new GamepadEx(gamepad1);

    driveLeft = new Motor(hardwareMap, "left"); // Left Motor
    driveRight = new Motor(hardwareMap, "right"); // Right Motor

    telemetry.addData("Status", "Initialized");
    telemetry.speak("Ready to start");
  }

  public void init_loop() {
    pushLeft = driver.getLeftY();
    pushRight = driver.getRightY();

    telemetry.addLine("Reading Inputs");
    telemetry.addData("Tank Left", driveLeft);
    telemetry.addData("Tank Right", driveRight);
  }

  public void start() {
    telemetry.clear();
  }

  public void loop() {
    pushLeft = driver.getLeftY();
    pushRight = driver.getRightY();

    telemetry.addLine("Robot Status");
    telemetry.addData("Tank Left", driveLeft);
    telemetry.addData("Tank Right", driveRight);

    driveLeft.set(pushLeft);
    driveRight.set(pushRight);
  }
}
