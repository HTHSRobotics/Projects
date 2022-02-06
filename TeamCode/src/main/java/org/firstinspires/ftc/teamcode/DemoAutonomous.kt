package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotorSimple

//this is the triangle red side: Will move the foundation and park on the tape 
@Autonomous(name = "Kotlin-Demo-Auto", group = "")
class DemoOpMode : LinearOpMode() {
    private var bottomleft: DcMotor? = null
    private var bottomright: DcMotor? = null
    private var topleft: DcMotor? = null
    private var topright: DcMotor? = null
    private var arm: DcMotor? = null
    private var armOut: DcMotor? = null
    private var flippy: CRServo? = null
    private val flippy2: CRServo? = null
    // private ColorSensor hawkeye;
    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    override fun runOpMode() {
        bottomleft = hardwareMap.dcMotor["bottomleft"]
        bottomright = hardwareMap.dcMotor["bottomright"]
        topleft = hardwareMap.dcMotor["topleft"]
        topright = hardwareMap.dcMotor["topright"]
        //`hawkeye = hardwareMap.colorSensor.get("hawkeye");
        arm = hardwareMap.dcMotor["arm"]
        armOut = hardwareMap.dcMotor["armOut"]
        flippy = hardwareMap.crservo["flippy"]
        //flippy2 = hardwareMap.crservo.get("flippy2");


        //declare array holding all motors
        val all = arrayOf(bottomleft, bottomright, topleft, topright)
        val lefts = arrayOf(bottomleft, topleft)
        val rights = arrayOf(bottomright, topright)

        // Put initialization blocks here.
        waitForStart()

        //set right motors to reverse
        bottomright.setDirection(DcMotorSimple.Direction.REVERSE)
        topright.setDirection(DcMotorSimple.Direction.REVERSE)
        //flippy.setDirection(DcMotorSimple.Direction.REVERSE);
        for (motor in all) {
            //set all motors' zero behavior to BRAKE
            motor!!.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        }
        if (opModeIsActive()) {
            while (opModeIsActive()) {

                //strafe to the right
                topright.setPower(0.3)
                bottomleft.setPower(0.3)
                bottomright.setPower(-0.3)
                topleft.setPower(-0.3)
                sleep(1000)

                //set power of all motors to 0.8
                for (motor in all) {
                    motor!!.power = 0.8
                }

                //move for 0.7 seconds
                sleep(700)
                for (motor in all) {
                    motor!!.power = 0.15
                }
                sleep(1500)

                //grab the foundation
                for (motor in all) {
                    motor!!.power = 0.15
                }
                flippy.setPower(0.7)
                sleep(1100)
                for (motor in all) {
                    motor!!.power = 0.0
                }
                sleep(700)
                flippy.setPower(0.0)
                sleep(500)

                //stops for a sec 
                for (motor in all) {
                    motor!!.power = 0.0
                }
                sleep(300)

                //back up
                for (motor in all) {
                    motor!!.power = -0.4
                }
                //flippy.setPower(0.3);
                //flippy2.setPower(0.3);
                sleep(2500)

                // //strafe to the left
                // topleft.setPower(-0.5);
                // bottomright.setPower(-0.5);
                // topright.setPower(0.5);
                // bottomleft.setPower(0.5);
                // sleep(1000);

                //turn so foundation is in site 
                topright.setPower(0.6)
                bottomright.setPower(0.6)
                topleft.setPower(-0.4)
                bottomleft.setPower(-0.4)
                // flippy.setPower(0);
                sleep(3500)
                for (motor in all) {
                    motor!!.power = 0.0
                }
                sleep(500)


                //let go of the foundation
                flippy.setPower(0.7)
                sleep(800)
                flippy.setPower(0.0)
                sleep(500)
                for (motor in all) {
                    motor!!.power = 0.0
                }
                sleep(300)
                sleep(1000)

                //go backwards until red  
                topleft.setPower(-0.4)
                bottomleft.setPower(-0.4)
                topright.setPower(-0.4)
                bottomright.setPower(-0.4)
                sleep(3000)

                //stop
                for (motor in all) {
                    motor!!.power = 0.0
                }
                sleep(30000)
            }
        }
    }
}