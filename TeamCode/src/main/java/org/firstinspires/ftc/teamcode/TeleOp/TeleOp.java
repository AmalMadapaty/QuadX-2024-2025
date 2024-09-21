package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {
    private DcMotor fl;
    private DcMotor fr;
    private DcMotor bl;
    private DcMotor br;
    private BNO055IMU imu;

    @Override
    public void init() {
        fl = hardwareMap.dcMotor.get("fl");
        fr = hardwareMap.dcMotor.get("fr");
        bl = hardwareMap.dcMotor.get("bl");
        br = hardwareMap.dcMotor.get("br");

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu.initialize(parameters);

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

    @Override
    public void loop() {
        float drive = gamepad1.left_stick_y;
        float turn = -gamepad1.right_stick_x;
        float strafe = gamepad1.left_stick_x;


        double flPower = Range.clip(drive + turn - strafe, -1.0, 1.0);
        double frPower = Range.clip(drive - turn + strafe, -1.0, 1.0);
        double blPower = Range.clip(drive + turn + strafe, -1.0, 1.0);
        double brPower = Range.clip(drive - turn - strafe, -1.0, 1.0);

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double heading = angles.firstAngle;
        telemetry.addData("Heading", heading); // Display the heading
        telemetry.update();

        if (gamepad1.right_trigger > 0.0) {
            flPower *= 0.5;
            frPower *= 0.5;
            blPower *= 0.5;
            brPower *= 0.5;
        }

        if (gamepad1.left_trigger > 0.0) {
            flPower *= 0.25;
            frPower *= 0.25;
            blPower *= 0.25;
            brPower *= 0.25;
        }

        if (gamepad1.x)
        {
            while (heading != 0) {
                double error = 0 - heading;
                double turnPower = (error/180) * 0.8;
                fl.setPower(turnPower);
                bl.setPower(turnPower);
                fr.setPower(-turnPower);
                br.setPower(-turnPower);
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                heading = angles.firstAngle;
                telemetry.addData("Heading", heading); // Display the heading
                telemetry.update();
            }
            fl.setPower(0);
            bl.setPower(0);
            fr.setPower(0);
            br.setPower(0);
        }

        fl.setPower(flPower);
        fr.setPower(frPower);
        bl.setPower(blPower);
        br.setPower(brPower);

        boolean light = gamepad1.right_bumper;
    }
}