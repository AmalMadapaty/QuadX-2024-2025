package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autos.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Autos.trajectorysequence.TrajectorySequence;

@Autonomous
public class Observatory extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d myPose = new Pose2d(-33, -61, Math.toRadians(270));

        drive.setPoseEstimate(myPose);

        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(myPose)
                // Scores the preload
                .strafeRight(25)
                // Heads over to the first sample
                .lineToLinearHeading(new Pose2d(-57, -110, Math.toRadians(270)))
                // Lines up with the observatory
                .turn(Math.toRadians(-75))
                // Scores the first sample
                .strafeRight(45)
                // Returns to the original position
                .strafeLeft(45)
                // Lines up with the second sample
                .lineToLinearHeading(new Pose2d(-69, -110, Math.toRadians(180)))
                // Scores the second sample
                .strafeRight(45)
                // Returns to the original position
                .strafeLeft(50)
                // Lines up with the third sample
                .forward(13)
                // Scores the third sample
                .strafeRight(50)
                // Returns to the original position and lines up with the park
                .strafeLeft(50)
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
