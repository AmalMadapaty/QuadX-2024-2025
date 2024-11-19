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

        Pose2d myPose = new Pose2d(-35.5, -61, Math.toRadians(270));

        drive.setPoseEstimate(myPose);

        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(myPose)
                // Scores the preload
                .strafeLeft(10)
                // Returns to the original position
                .strafeRight(11.5)
                // Heads over to the first sample
                .lineToLinearHeading(new Pose2d(-32, -110, Math.toRadians(270)))
                // Lines up with the net zone
                .turn(Math.toRadians(72))
                // Scores the first sample
                .strafeLeft(45)
                // Returns to the original position
                .strafeRight(45)
                // Lines up with the second sample
                .lineToLinearHeading(new Pose2d(-20, -110, Math.toRadians(0)))
                // Scores the second sample
                .strafeLeft(45)
                // Returns to the original position
                .strafeRight(50)
                // Lines up with the third sample
                .forward(10)
                // Scores the third sample
                .strafeLeft(50)
                // Returns to the original position and lines up for the park
                .strafeRight(50)
                // Parks
                .back(35)
                .build();

        // Waits for the start button to be pressed
        waitForStart();
        //Follows the trajectory
        loop();
        drive.followTrajectorySequence(traj1);
    }
}
