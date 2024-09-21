package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autos.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Autos.trajectorysequence.TrajectorySequence;

@Autonomous
public class RRAuto extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d myPose = new Pose2d(-35.5, -61, Math.toRadians(90));

        Trajectory test = drive.trajectoryBuilder(myPose)
                .splineToConstantHeading(new Vector2d(0, -35), Math.toRadians(0))
                .build();
        Trajectory second = drive.trajectoryBuilder(test.end())
                .lineToLinearHeading(new Pose2d(-48, -48, Math.toRadians(45)))
                .build();
        Trajectory third = drive.trajectoryBuilder(second.end())
                .lineToLinearHeading(new Pose2d(0, -35, Math.toRadians(90)))
                .build();
;
        waitForStart();
        drive.followTrajectory(test);
        drive.followTrajectory(second);
        drive.followTrajectory(third);
    }
}
