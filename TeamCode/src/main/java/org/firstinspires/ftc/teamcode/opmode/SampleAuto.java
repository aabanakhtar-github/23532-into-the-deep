package org.firstinspires.ftc.teamcode.opmode;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierCurve;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.commands.FollowBezierCommand;

@Autonomous(name = "Sample Auto 0+3")
public class SampleAuto extends OpMode {
    private Robot robot;
    private Follower drive;

    public static Pose startPose = new Pose(8.223776223776223, 87.44055944055943, Math.toRadians(0));
    public static Pose scorePose = new Pose(13.594405594405593, 125.7062937062937, Math.toRadians(-45));
    public static Pose sample0Pose = new Pose(30, 120.16783216783216, Math.toRadians(0));
    public static Pose sample1Pose = new Pose(30, 132, Math.toRadians(0));
    public static BezierCurve ascentBezier = new BezierCurve(
            new Point(scorePose.getX(), scorePose.getY(), Point.CARTESIAN),
            new Point(67.133, 132.420, Point.CARTESIAN),
            new Point(67.133, 97.343, Point.CARTESIAN)
    );

    Path scorePreload, scoreSample0, scoreSample1, ascent;

    @Override
    public void init() {
       robot = new Robot(hardwareMap, telemetry, true);
       drive = new Follower(hardwareMap);
       scorePreload = new Path(new BezierLine(new Point(startPose), new Point(scorePose)));
       CommandScheduler.getInstance().schedule(new FollowBezierCommand(drive, scorePreload));
    }

    @Override
    public void loop() {
        drive.update();
        CommandScheduler.getInstance().run();
    }
}