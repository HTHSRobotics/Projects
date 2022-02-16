package org.firstinspires.ftc.teamcode

import android.os.SystemClock.sleep
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo

@TeleOp(name = "TeleKotlin", group = "TEST")
class TeleKotlin : OpMode() {

    lateinit var motor1: DcMotor
    lateinit var motor2: DcMotor
    lateinit var servo: CRServo

    var slow1 = false
    var slow2 = false

    override fun init() {
        motor1 = hardwareMap.get(DcMotor::class.java, "motor")
        motor2 = hardwareMap.get(DcMotor::class.java, "motor2")
        servo = hardwareMap.get(CRServo::class.java, "servo")
    }

    override fun loop() {
        var stick1 = -gamepad1.left_stick_y.toDouble()
        var stick2 = -gamepad1.right_stick_y.toDouble()

        var bumperL = gamepad1.left_bumper
        var bumperR = gamepad1.right_bumper
        var triggerL = gamepad1.left_trigger
        var triggerR = gamepad1.right_trigger

        var slowToggle1 = gamepad1.left_stick_button
        var slowToggle2 = gamepad1.right_stick_button

        slow1 = if (slowToggle1) {
            !slow1
        } else {
            slow1
        }

        slow2 = if (slowToggle2) {
            !slow2
        } else {
            slow2
        }

        if (slow1) {
            stick1 *= 0.5
        }

        if (slow2) {
            stick2 *= 0.5
        }

        if (gamepad1.cross) {
            motor2.power = 0.75
            sleep(2500)
            motor2.power = 0.0
        }

        if (triggerL > 0) {
            servo.direction = DcMotorSimple.Direction.REVERSE
            servo.power = triggerL.toDouble()
        } else if (triggerR > 0) {
            servo.direction = DcMotorSimple.Direction.FORWARD
            servo.power = triggerR.toDouble()
        } else {
            servo.power = 0.0
        }

        motor1.power = stick1
        motor2.power = stick2
    }
}