package org.firstinspires.ftc.teamcode.opmode;

import android.transition.Slide;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.RotatingExtensionArm;
import org.firstinspires.ftc.teamcode.subsystems.commands.ForceClawOpenCommand;
import org.firstinspires.ftc.teamcode.subsystems.commands.SlideExtensionCommand;
import org.firstinspires.ftc.teamcode.subsystems.commands.SlidePitchCommand;
import org.firstinspires.ftc.teamcode.wrapper.ActionCommand;

import java.util.HashSet;
import java.util.Set;

@Config
@Autonomous(name = "SAMPLE AUTONOMOUS", group = "autos")
public class SampleAuto extends OpMode {
    Robot roboNemo;

    @Override
    public void init() {
        roboNemo = new Robot(hardwareMap, telemetry, false, new Pose2d(-38.67, -63, 0));
        roboNemo.rotatingExtensionArm.setConfig(true);

        TrajectoryActionBuilder followtraj = roboNemo.drive.actionBuilder(roboNemo.drive.getPose())
                .strafeToLinearHeading(new Vector2d(-55, -55), Math.PI/4);
                //.strafeToLinearHeading(new Vector2d(-48, -43), Math.PI/2)
                //.strafeToLinearHeading(new Vector2d(-55, -55), Math.PI/4)
                //.strafeToLinearHeading(new Vector2d(-58, -43), Math.PI/2)
                //.strafeToLinearHeading(new Vector2d(-55, -55), Math.PI/4);;
        HashSet<Subsystem> actionRequirements = new HashSet<>();
        actionRequirements.add(roboNemo.drive);

        roboNemo.clawArm.claw.setPosition(0);

        CommandScheduler.getInstance().schedule(
                new ActionCommand(followtraj.build(), actionRequirements),
                new ForceClawOpenCommand(roboNemo.clawArm, 0.8)
        );
    }

    @Override
    public void init_loop() {
        roboNemo.rotatingExtensionArm.periodic();
        roboNemo.clawArm.periodic();
    }

    @Override
    public void loop() {
        roboNemo.periodic();
    }

}
