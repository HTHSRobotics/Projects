package org.firstinspires.ftc.teamcode

import android.os.SystemClock.sleep
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Servo

@TeleOp(name = "TeleKotlin", group = "TEST")
class TeleKotlin : OpMode() {

    lateinit var motor1: DcMotor
    lateinit var motor2: DcMotor
    lateinit var servo: Servo

    var slow1 = false
    var slow2 = false

    override fun init() {
        motor1 = hardwareMap.get(DcMotor::class.java, "motor")
        motor2 = hardwareMap.get(DcMotor::class.java, "motor2")
        servo = hardwareMap.get(Servo::class.java, "servo")
    }

    override fun loop() {
        var stick1 = -gamepad1.left_stick_y.toDouble()
        var stick2 = -gamepad1.right_stick_y.toDouble()

        var bumperL = gamepad1.left_bumper
        var bumperR = gamepad1.right_bumper

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

        if (bumperL) {
            var newPosition = servo.position - 0.1
            servo.position = newPosition
        } else if (bumperR) {
            var newPosition = servo.position + 0.1
            servo.position = newPosition
        }

        motor1.power = stick1
        motor2.power = stick2
    }
}