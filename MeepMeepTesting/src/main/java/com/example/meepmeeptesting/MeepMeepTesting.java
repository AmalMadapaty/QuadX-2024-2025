package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Vector;

public class MeepMeepTesting {
    public static void main(String[] args) {
        // Declare a MeepMeep instance
        // With a field size of 800 pixels
        MeepMeep meepMeep = new MeepMeep(800);
        Pose2d leftPose = new Pose2d(-35.5, -60.5, Math.toRadians(90));
        Pose2d rightPose = new Pose2d(35.5, -60.5, Math.toRadians(90));
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Required: Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->

                        drive.trajectorySequenceBuilder(leftPose)
                                .splineToConstantHeading(new Vector2d(-17, -35), Math.toRadians(0))
                                .waitSeconds(1.5)
                                .strafeLeft(31)
                                .forward(3)
                                .waitSeconds(1.5)
                                .lineToLinearHeading(new Pose2d(-48, -48, Math.toRadians(45)))
                                .waitSeconds(1.5)
                                .lineToLinearHeading(new Pose2d(-58, -32, Math.toRadians(90)))
                                .waitSeconds(1.5)
                                .lineToLinearHeading(new Pose2d(-48, -48, Math.toRadians(45)))
                                .waitSeconds(1.5)
                                .splineTo(new Vector2d(-60, -24), Math.toRadians(180))
                                .waitSeconds(1.5)
                                .back(5)
                                .lineToLinearHeading(new Pose2d(-48, -48, Math.toRadians(45)))
                                .waitSeconds(1.5)
                                .splineTo(new Vector2d(0, -35), Math.toRadians(90))
                                .waitSeconds(1.5)
                                .lineToLinearHeading(new Pose2d(-48, -48, Math.toRadians(45)))
                                .waitSeconds(1.5)
                                .build()
                        /*
                        drive.trajectorySequenceBuilder(rightPose)
                                .splineToConstantHeading(new Vector2d(17, -35), Math.toRadians(0))
                                .waitSeconds(1.5)
                                .strafeRight(31)
                                .forward(3)
                                .waitSeconds(1.5)
                                .lineToLinearHeading(new Pose2d(48, -48, Math.toRadians(135)))
                                .waitSeconds(1.5)
                                .lineToLinearHeading(new Pose2d(58, -32, Math.toRadians(90)))
                                .waitSeconds(1.5)
                                .lineToLinearHeading(new Pose2d(48, -48, Math.toRadians(135)))
                                .waitSeconds(1.5)
                                .splineTo(new Vector2d(60, -24), Math.toRadians(0))
                                .waitSeconds(1.5)
                                .back(5)
                                .lineToLinearHeading(new Pose2d(48, -48, Math.toRadians(135)))
                                .waitSeconds(1.5)
                                .splineTo(new Vector2d(0, -35), Math.toRadians(90))
                                .waitSeconds(1.5)
                                .lineToLinearHeading(new Pose2d(48, -48, Math.toRadians(135)))
                                .waitSeconds(1.5)
                                .build()
                                */
                );

        // Set field image
        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_OFFICIAL)
                // Background opacity from 0-1
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}