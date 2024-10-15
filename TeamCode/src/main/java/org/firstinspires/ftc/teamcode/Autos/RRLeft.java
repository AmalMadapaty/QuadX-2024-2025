package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autos.drive.SampleMecanumDrive;

@Autonomous
public class RRLeft extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d myPose = new Pose2d(-35.5, -61, Math.toRadians(90));

        Trajectory first = drive.trajectoryBuilder(myPose)
                .splineToConstantHeading(new Vector2d(-17, -35), Math.toRadians(0))
                .build();
;
        waitForStart();
        loop();
        drive.followTrajectory(first);
    }
}
