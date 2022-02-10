package org.firstinspires.ftc.teamcode;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="TestMotorTele",group = "TEST")
public class TestMotorTele extends OpMode {
    private DcMotor motor;
    boolean slowMode = true;

    @Override
    public void init(){
        motor = hardwareMap.get(DcMotor.class,"motor");

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Status", "Testing Motor");
        telemetry.update();

        motor.setPower(1.0);
        sleep(1000);
        motor.setPower(0);
        sleep(1000);
        motor.setPower(-1.0);
        sleep(1000);
        motor.setPower(0);

        telemetry.addData("Status","Testing Complete, Awaiting Start");
        telemetry.update();
    }

    @Override
    public void loop(){
        double stick = gamepad1.left_stick_y;
        boolean toggleSlowMode = gamepad1.a;

        if (toggleSlowMode) {
            slowMode = !slowMode;

            if (slowMode) {
                telemetry.addData("Mode","Slow");
            }
            else if (!slowMode){
                telemetry.addData("Mode","Fast");
            }

            telemetry.update();
        }

        if (slowMode){
            stick = stick * 0.5;
        }

        motor.setPower(stick);
        
        String powerAsString = String.valueOf(stick);

        telemetry.addData("Current Power",""+powerAsString);
        telemetry.update();
    }
}
