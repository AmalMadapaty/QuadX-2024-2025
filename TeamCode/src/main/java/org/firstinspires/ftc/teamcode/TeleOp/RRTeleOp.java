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

    private Servo leftIn;
    private Servo rightIn;
    private Servo leftLink;
    private Servo rightLink;

    @Override
    public void init() {
        drive = new SampleMecanumDrive(hardwareMap);
        hardwareMap.dcMotor.get("fl");
        intake = hardwareMap.dcMotor.get("intake");
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intake = hardwareMap.dcMotor.get("intake");
        intake.setDirection(DcMotor.Direction.FORWARD);
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftIn = hardwareMap.servo.get("leftIn");
        rightIn = hardwareMap.servo.get("rightIn");
        leftLink = hardwareMap.servo.get("leftLink");
        rightLink = hardwareMap.servo.get("rightLink");
    }

    @Override
    public void loop() {
        drive.setWeightedDrivePower(
                new Pose2d(
                        gamepad1.left_stick_y,
                        gamepad1.left_stick_x,
                        gamepad1.right_stick_x
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

        if (gamepad2.a) {
            leftLink.setPosition(0.6);
            rightLink.setPosition(-0.6);
            telemetry.addData("leftLink position: ", leftLink.getPosition());
            telemetry.addData("rightLink position: ", rightLink.getPosition());
        } else if (gamepad2.b) {
            leftLink.setPosition(-1);
            rightLink.setPosition(1);
            leftIn.setPosition(1);
            rightIn.setPosition(1);
            telemetry.addData("leftLink position: ", leftLink.getPosition());
            telemetry.addData("rightLink position: ", rightLink.getPosition());
        }

        if (gamepad2.x) {
            leftIn.setPosition(1);
            rightIn.setPosition(1);
            telemetry.addData("leftIn position: ", leftIn.getPosition());
            telemetry.addData("rightIn position: ", rightIn.getPosition());
        } else if (gamepad2.y) {
            leftIn.setPosition(0);
            rightIn.setPosition(0);
            telemetry.addData("leftIn position: ", leftIn.getPosition());
            telemetry.addData("rightIn position: ", rightIn.getPosition());
        }

        if (gamepad2.left_trigger > 0.0) {
            intake.setPower(gamepad2.left_trigger);
        } else if (gamepad2.right_trigger > 0.0) {
            intake.setPower(-1 * gamepad2.right_trigger);
        } else {
            intake.setPower(gamepad2.right_trigger);
        }
    }
}