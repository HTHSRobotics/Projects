package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class mechanumDrive { // abstract mechanum drive logic

  private DcMotor fL, fR, bL, bR;
  private DcMotorController fL_ctrl, fR_ctrl, bL_ctrl, bR_ctrl;
  private String fL_id, fR_id, bL_id, bR_id;

  private double speedScale = 1.0;
  private double scaledPower;

  /**
   * Construct new mechanum drive object
   * @param frontLeft
   * @param frontRight
   * @param backLeft
   * @param backRight
   */
  public mechanumDrive(String frontLeft, String frontRight, String backLeft, String backRight) {
    fL_id = frontLeft;
    fR_id = frontRight;
    bL_id = backLeft;
    bR_id = backRight;
  }

  /**
   * Call on initialization
   * @param hwMap hardware map function
   */

  public void init(HardwareMap hwMap) { // constructor; sets basic settings for drivetrain
    fL = hwMap.dcMotor.get(fL_id);
    fR = hwMap.dcMotor.get(fR_id);
    bL = hwMap.dcMotor.get(bL_id);
    bR = hwMap.dcMotor.get(bR_id);

    // Set correct direction for mechanum drive
//    fL.setDirection(DcMotorSimple.Direction.FORWARD);
//    bL.setDirection(DcMotorSimple.Direction.REVERSE);
//    fR.setDirection(DcMotorSimple.Direction.FORWARD);
//    bR.setDirection(DcMotorSimple.Direction.REVERSE);

    // Set motors to stop when power is zero
    fR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    fL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    bL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    bR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    // Call reset method to ensure accuracy
    reset();
  }

  /**
   * Stop Robot and Reset Encoder positions
   */
  public void reset(){
    // Make sure all motors are stopped
    fL.setPower(0);
    fR.setPower(0);
    bL.setPower(0);
    bR.setPower(0);

    // Reset Encoder Positions
    fR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    fL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    bL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    bR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    // Set all motors to run with encoder feedback
    fL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    fR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    bL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    bR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
  }

  /**
   * Stop robot.
   */
  public void stop() { // stop all motors
    fL.setPower(0);
    fR.setPower(0);
    bL.setPower(0);
    bR.setPower(0);
  }

  public double setSpeedScale(double newScale){
    speedScale = newScale;
    return speedScale;
  }

  public void disableEncoders(){
    fL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    fR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    bL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    bR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
  }

  public void strafe(String direction, double power) { // strafe function

    scaledPower = power * speedScale;
    switch (direction) {
      case "forward":
        fL.setPower(power);
        fR.setPower(power);
        bL.setPower(power);
        bR.setPower(power);
        break;
      case "back":
        fL.setPower(-power);
        fR.setPower(-power);
        bL.setPower(-power);
        bR.setPower(-power);
        break;

      case "left":
        fL.setPower(-power);
        fR.setPower(power);
        bL.setPower(power);
        bR.setPower(-power);
        break;

      case "right":
        fL.setPower(power);
        fR.setPower(-power);
        bL.setPower(-power);
        bR.setPower(power);
        break;
    }

  }

  public void rotate(String direction, double power) { // rotate function
    switch (direction) {
      case "left":
        fL.setPower(-power);
        fR.setPower(power);
        bL.setPower(power);
        bR.setPower(-power);
        break;

      case "right":
        fL.setPower(power);
        fR.setPower(-power);
        bL.setPower(-power);
        bR.setPower(power);
        break;
    }
  }

  public void drive(Gamepad gamepad) { // drive function
    double max;

    double axial   = -gamepad.left_stick_x;  // Note: pushing stick forward gives negative value
    double lateral =  gamepad.left_stick_y;
    double yaw     =  gamepad.right_stick_x;

    // Calculate the power for each wheel
    double leftFrontPower  = axial + lateral + yaw;
    double rightFrontPower = axial - lateral - yaw;
    double leftBackPower   = axial - lateral + yaw;
    double rightBackPower  = axial + lateral - yaw;

    // Compute power levels proportionally
    max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
    max = Math.max(max, Math.abs(leftBackPower));
    max = Math.max(max, Math.abs(rightBackPower));

    if (max > 1.0) {
      leftFrontPower  /= max;
      rightFrontPower /= max;
      leftBackPower   /= max;
      rightBackPower  /= max;
    }

    // Run motors at power
    fL.setPower(leftFrontPower);
    fR.setPower(rightFrontPower);
    bL.setPower(leftBackPower);
    bR.setPower(rightBackPower);

  }

  private void moveDistance(double distance) {
  }

}