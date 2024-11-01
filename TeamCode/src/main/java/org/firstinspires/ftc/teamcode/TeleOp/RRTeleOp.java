package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.Autos.drive.SampleMecanumDrive;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class RRTeleOp extends OpMode {
    private SampleMecanumDrive drive;
    private DcMotor intake;
    private Servo rightIntake;
    private Servo leftIntake;
    private Servo rightLink;
    private Servo leftLink;

    @Override
    public void init() {
        drive = new SampleMecanumDrive(hardwareMap);
        hardwareMap.dcMotor.get("fl");
        intake = hardwareMap.dcMotor.get("intake");
        rightIntake = hardwareMap.servo.get("rightIntake");
        leftIntake = hardwareMap.servo.get("leftIntake");
        rightLink = hardwareMap.servo.get("rightIntake");
        leftLink = hardwareMap.servo.get("rightIntake");
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    @Override
    public void loop() {
        drive.setWeightedDrivePower(
                new Pose2d(
                        -gamepad1.left_stick_y,
                        -gamepad1.left_stick_x,
                        -gamepad1.right_stick_x
                )
        );
        drive.update();

        if (gamepad2.right_trigger != 0) {
            intake.setPower(gamepad2.right_trigger);
        } else {
            intake.setPower(0);
        }



        double heading = drive.getPoseEstimate().getHeading();
        telemetry.addData("Heading", Math.toDegrees(heading));
        telemetry.update();
    }
}
