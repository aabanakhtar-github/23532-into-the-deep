package org.firstinspires.ftc.teamcode.opmode;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierCurve;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.teamcode.subsystems.Robot;

@Autonomous(name = "Sample Auto 0+3")
public class SampleAuto extends OpMode {
    private Robot robot;
    private Follower drive;

    public static Point startPose = new Point(8.223776223776223, 87.44055944055943, Point.CARTESIAN);
    public static Point scorePose = new Point(13.594405594405593, 125.7062937062937, Point.CARTESIAN);
    public static Point sample0Pose = new Point(30, 120.16783216783216, Point.CARTESIAN);
    public static Point sample1Pose = new Point(30, 132, Point.CARTESIAN);
    public static BezierCurve ascentBezier = new BezierCurve(
            scorePose,
            new Point(67.133, 132.420, Point.CARTESIAN),
            new Point(67.133, 97.343, Point.CARTESIAN)
    );



    @Override
    public void init() {
       robot = new Robot(hardwareMap, telemetry, true);
       drive = new Follower(hardwareMap);
    }

    @Override
    public void loop() {
        drive.update();
        CommandScheduler.getInstance().run();
    }
}