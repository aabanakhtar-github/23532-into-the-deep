package org.firstinspires.ftc.teamcode.opmode;

import android.transition.Slide;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.ClawArm;
import org.firstinspires.ftc.teamcode.subsystems.CommandStatics;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.RotatingExtensionArm;
import org.firstinspires.ftc.teamcode.subsystems.commands.ClawArmPositionCommand;
import org.firstinspires.ftc.teamcode.subsystems.commands.ForceClawOpenCommand;
import org.firstinspires.ftc.teamcode.subsystems.commands.SlideExtensionCommand;
import org.firstinspires.ftc.teamcode.subsystems.commands.SlidePitchCommand;
import org.firstinspires.ftc.teamcode.wrapper.ActionCommand;

import java.util.HashSet;
import java.util.Set;

@Config
@Autonomous(name = "SAMPLE AUTONOMOUS 4", group = "autos")
public class SampleAuto extends OpMode {
    Robot roboNemo;
    Vector2d scorePose = new Vector2d(-64.5, -59);


    public SequentialCommandGroup scoreSequence() {
        return new SequentialCommandGroup(
                new SlideExtensionCommand(roboNemo.rotatingExtensionArm, -730),
                new ClawArmPositionCommand(roboNemo.clawArm, ClawArm.PresetSetting.DEPOSIT),
                new WaitCommand(800),
                new ForceClawOpenCommand(roboNemo.clawArm, 0.0),
                new WaitCommand(500),
                new ClawArmPositionCommand(roboNemo.clawArm, ClawArm.PresetSetting.INTAKE),
                new WaitCommand(350),
                new SlideExtensionCommand(roboNemo.rotatingExtensionArm, 0)
        );
    }

    public SequentialCommandGroup intakeSequence() {
        return new SequentialCommandGroup(
                new WaitCommand(400),
                new ClawArmPositionCommand(roboNemo.clawArm, ClawArm.PresetSetting.INTAKE_PRE),
                new WaitCommand(400),
                new ForceClawOpenCommand(roboNemo.clawArm, 1),
                new WaitCommand(800),
                new SlidePitchCommand(roboNemo.rotatingExtensionArm, SlidePitchCommand.up)
        );
    }

    public SequentialCommandGroup resetSequence() {
        return new SequentialCommandGroup(
                new ClawArmPositionCommand(roboNemo.clawArm, ClawArm.PresetSetting.INTAKE),
                new SlidePitchCommand(roboNemo.rotatingExtensionArm, SlidePitchCommand.down)
        );
    }

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();

        roboNemo = new Robot(hardwareMap, telemetry, false, new Pose2d(-38.67, -63, 0));
        roboNemo.rotatingExtensionArm.setConfig(true);

        TrajectoryActionBuilder preload = roboNemo.drive.actionBuilder(roboNemo.drive.getPose())
                .strafeToLinearHeading(scorePose, Math.PI/4);
        TrajectoryActionBuilder sample0 = roboNemo.drive.actionBuilder(new Pose2d(scorePose, Math.PI/4))
                .strafeToLinearHeading(new Vector2d(-48.7, -43.3), Math.PI/2);
        TrajectoryActionBuilder sample0Score = roboNemo.drive.actionBuilder(new Pose2d(-48.7, -43.3, Math.PI/2))
                .strafeToLinearHeading(scorePose, Math.PI/4);
        TrajectoryActionBuilder sample1 = roboNemo.drive.actionBuilder(new Pose2d(scorePose, Math.PI/4))
                .strafeToLinearHeading(new Vector2d(-59, -43.7), Math.PI/2);
        TrajectoryActionBuilder sample1Score = roboNemo.drive.actionBuilder(new Pose2d(-59, -43.7, Math.PI/2))
                .strafeToLinearHeading(scorePose, Math.PI/4);
        TrajectoryActionBuilder sample2 = roboNemo.drive.actionBuilder(new Pose2d(scorePose, Math.PI/4))
                .splineToLinearHeading(new Pose2d(-54.4, -27.7, Math.PI), Math.PI/2);
        TrajectoryActionBuilder sample2Score = roboNemo.drive.actionBuilder(new Pose2d(-54.4, -27.7, Math.PI))
                .strafeToLinearHeading(scorePose, Math.PI/4);
        HashSet<Subsystem> actionRequirements = new HashSet<>();
        HashSet<Subsystem> action0Requirements = new HashSet<>();
        HashSet<Subsystem> action1Requirements = new HashSet<>();
        actionRequirements.add(roboNemo.drive);
        action0Requirements.add(roboNemo.drive);
        action1Requirements.add(roboNemo.drive);

        CommandScheduler.getInstance().schedule( new SequentialCommandGroup(
                new ForceClawOpenCommand(roboNemo.clawArm, 1),
                new ActionCommand(preload.build(), actionRequirements),
                scoreSequence(),
                new ParallelCommandGroup(
                        new ActionCommand(sample0.build(), action0Requirements),
                        resetSequence()
                ),
                intakeSequence(),
                new ActionCommand(sample0Score.build(), action1Requirements),
                scoreSequence(),
                new ParallelCommandGroup(
                        new ActionCommand(sample1.build(), actionRequirements),
                        resetSequence()
                ),
                intakeSequence(),
                new ActionCommand(sample1Score.build(), actionRequirements),
                scoreSequence(),
                new ParallelCommandGroup(
                        resetSequence(),
                        new ActionCommand(sample2.build(), actionRequirements),
                        new InstantCommand((() -> {roboNemo.clawArm.otherAngle = true;}))
                ),
                intakeSequence(),
                new ParallelCommandGroup(
                        new InstantCommand((() -> {roboNemo.clawArm.otherAngle = false; })),
                        new ActionCommand(sample2Score.build(), actionRequirements)
                ),
                scoreSequence()
        ));
    }

    @Override
    public void init_loop() {
        roboNemo.hubs.clearCache();
        roboNemo.rotatingExtensionArm.periodic();
        roboNemo.clawArm.periodic();
    }

    @Override
    public void loop() {
        roboNemo.periodic();
    }

}
