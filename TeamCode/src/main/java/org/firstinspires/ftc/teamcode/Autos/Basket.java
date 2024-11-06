package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autos.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Autos.trajectorysequence.TrajectorySequence;

@Autonomous
public class Basket extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d myPose = new Pose2d(-35.5, -61, Math.toRadians(90));

        drive.setPoseEstimate(myPose);

        Trajectory traj1 = drive.trajectoryBuilder(myPose, false)
                //.strafeLeft(30)
                .lineToLinearHeading(new Pose2d(-35.5, -110, Math.toRadians(90)))
                //.lineToConstantHeading(new Vector2d(-35.5, -30))
                .build();
;
        TrajectorySequence traj2 = drive.trajectorySequenceBuilder(traj1.end())
                .turn(Math.toRadians(72))
                .waitSeconds(1) // Waits 3 seconds
                .build();

        Trajectory traj3 = drive.trajectoryBuilder(traj2.end(), false)
                        .strafeRight(45)
                        .build();

        Trajectory traj4 = drive.trajectoryBuilder(traj3.end(), false)
                .strafeLeft(45)
                .build();

        Trajectory traj5 = drive.trajectoryBuilder(traj4.end(), false)
                .lineToLinearHeading(new Pose2d(-20, -110, Math.toRadians(180)))
                .build();

        Trajectory traj6 = drive.trajectoryBuilder(traj5.end(), false)
                .strafeRight(45)
                .build();

        TrajectorySequence traj7 = drive.trajectorySequenceBuilder(traj6.end())
                .strafeLeft(50)
                .back(10)
                .strafeRight(50)
                .strafeLeft(50)
                .build();

        TrajectorySequence traj8 = drive.trajectorySequenceBuilder(traj7.end())
                .forward(35)
                .build();


        waitForStart();
        loop();
        drive.followTrajectory(traj1);
        drive.followTrajectorySequence(traj2);
        drive.followTrajectory(traj3);
        drive.followTrajectory(traj4);
        drive.followTrajectory(traj5);
        drive.followTrajectory(traj6);
        drive.followTrajectorySequence(traj7);
        drive.followTrajectorySequence(traj8);
    }
}
