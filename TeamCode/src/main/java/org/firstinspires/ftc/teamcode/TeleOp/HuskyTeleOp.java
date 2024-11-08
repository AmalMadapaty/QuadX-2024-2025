package org.firstinspires.ftc.teamcode.TeleOp;

import android.renderscript.Sampler;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class HuskyTeleOp extends OpMode {
    private DcMotor fl;
    private DcMotor fr;
    private DcMotor bl;
    private DcMotor br;
    private Servo IntakeServoR;
    HuskyLens Husk;


    @Override
    public void init() {
        fl = hardwareMap.dcMotor.get("fl");
        fr = hardwareMap.dcMotor.get("fr");
        bl = hardwareMap.dcMotor.get("bl");
        br = hardwareMap.dcMotor.get("br");
        IntakeServoR = hardwareMap.servo.get("IntakeR");


        Husk = hardwareMap.get(HuskyLens.class,"Husky");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;

         Husk.selectAlgorithm(HuskyLens.Algorithm.COLOR_RECOGNITION);

        fl.setDirection(DcMotor.Direction.FORWARD);
        fr.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.REVERSE);

        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    //This checks if there is an object. If there is, return how many.
    //Mind you, this only checks for BLUE objects. TODO: DOES NOT WORK ON RED OR YELLOW YET.
    public int BlueObjectDetection(){
        HuskyLens.Block[] List = Husk.blocks();
       return(List.length);
    };

    //This returns the X position of a detected object (only blue at the moment).
    //For usage of the values, use Posi.
    public int BlueObjectOrientation() {
        Husk.selectAlgorithm(HuskyLens.Algorithm.COLOR_RECOGNITION);
        int Xpos = 0;
        for (HuskyLens.Block b : Husk.blocks()) {
            if (b.id == 1) {
                Xpos = b.x;
                break;
            }
        }
        return Xpos;
    }

    int Object;
    float Posi;

    @Override
    public void loop() {

        float drive = gamepad1.left_stick_y;
        float turn = -gamepad1.right_stick_x;
        float strafe = gamepad1.left_stick_x;

        double flPower = Range.clip(drive + turn - strafe, -1.0, 1.0);
        double frPower = Range.clip(drive - turn + strafe, -1.0, 1.0);
        double blPower = Range.clip(drive + turn + strafe, -1.0, 1.0);
        double brPower = Range.clip(drive - turn - strafe, -1.0, 1.0);


        if (gamepad1.right_trigger > 0.0) {
            flPower *= 0.5;
            frPower *= 0.5;
            blPower *= 0.5;
            brPower *= 0.5;
        }

        if (gamepad1.left_bumper) {
            flPower *= 0.25;
            frPower *= 0.25;
            blPower *= 0.25;
            brPower *= 0.25;
        }

        if (gamepad2.left_bumper) {
            flPower = 0.1;
            frPower = 0.1;
            blPower = 0.1;
            brPower = 0.1;
        }

        if (gamepad1.y){
            IntakeServoR.setPosition(0.0);
        }

        if(gamepad1.a){
            IntakeServoR.setPosition(0.5);
        }

           Object = BlueObjectDetection();

            if (Object >= 1){
                telemetry.addData("Vision", 1);
            }

            if (Object == 0){
                telemetry.addData("Vision",0);
            }

            Posi = BlueObjectOrientation() - 160;
            telemetry.addData("X Position",Posi);

            //TODO: THIS IS EXPERIMENTAL AND NEEDS TO BE TESTED/FIXED.
            //This function allows for alignment with detected objects. If there aren't any to align with, it attempts to find one.
            //NOTE - This might override all other Driver 1 functions temporarily. Whether or not this is bad or good is yet to be determined.
            if(gamepad1.b) {
                double time;
                if (Object == 0) {
                    time = getRuntime();
                    while (Object == 0) {
                        //This makes the robot rotate in an attempt to find an object.
                        //Should make at least a full 360 before forcefully stopping.
                        flPower = 0.2;
                        frPower = -0.2;
                        blPower = 0.2;
                        brPower = -0.2;

                        //Failsafe - this prevents infinite spinning from not detecting an object.
                        //Probably will be referred to as the "Spin Cycle"
                        if (getRuntime() >= time + 2000){
                            break;
                        }
                    }

                }

                if (Object == 1) {
                    //This sets the turn variable for the drive functions to an aligning and adaptive value.
                    //The 0.05 is effectively a derivative - it sets a minimum SOR (Speed Of Rotation).
                    //The division by 200 can be changed to be lower/higher.
                    //Higher division will result in a overall slower SOR, and vice versa for Lower division.
                    //May need to set a range of appropriate values.
                    //TODO: Check if the turns are proper. They may be skewed by other values. Delete this after.
                    while (!(Posi == 160)) {
                        flPower = Range.clip(drive + ((Posi / 200) + 0.05) - strafe, -1.0, 1.0);
                        frPower = Range.clip(drive - ((Posi / 200) - 0.05) + strafe, -1.0, 1.0);
                        blPower = Range.clip(drive + ((Posi / 200) - 0.05) + strafe, -1.0, 1.0);
                        brPower = Range.clip(drive - ((Posi / 200) + 0.05) - strafe, -1.0, 1.0);
                    }
                }
            }

        updateTelemetry(telemetry);

        fl.setPower(flPower);
        fr.setPower(frPower);
        bl.setPower(blPower);
        br.setPower(brPower);


    }
}