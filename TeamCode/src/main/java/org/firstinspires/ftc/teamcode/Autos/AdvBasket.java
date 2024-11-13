package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autos.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Autos.trajectorysequence.TrajectorySequence;

@Autonomous
public class AdvBasket extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d myPose = new Pose2d(-35.5, -61, Math.toRadians(90));

        drive.setPoseEstimate(myPose);

        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(myPose)
                .lineToLinearHeading(new Pose2d(-19.5, -71, Math.toRadians(40)))
                .waitSeconds(2.25)
                .lineToLinearHeading(new Pose2d(-25, -83, Math.toRadians(90)))
                .waitSeconds(1.5)
                .lineToLinearHeading(new Pose2d(-19.25, -70.75, Math.toRadians(42)))
                .waitSeconds(2.25)
                .lineToLinearHeading(new Pose2d(-14.5, -83, Math.toRadians(93)))
                .waitSeconds(1.5)
                .lineToLinearHeading(new Pose2d(-19.25, -70.75, Math.toRadians(42)))
                .waitSeconds(2.25)
                .lineToLinearHeading(new Pose2d(-13, -86, Math.toRadians(131)))
                .waitSeconds(1.5)
                .lineToLinearHeading(new Pose2d(-19.25, -70.75, Math.toRadians(40)))
                .waitSeconds(2.25)
                .build();
        ;

        waitForStart();
        loop();
        drive.followTrajectorySequence(traj1);
    }
}
