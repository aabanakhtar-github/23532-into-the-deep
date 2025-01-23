package org.firstinspires.ftc.teamcode.opmode;

import static java.lang.Math.abs;

import android.transition.Slide;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.internal.usb.exception.RobotUsbTooManySequentialErrorsException;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.subsystems.ClawArm;
import org.firstinspires.ftc.teamcode.subsystems.CommandStatics;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.RotatingExtensionArm;
import org.firstinspires.ftc.teamcode.subsystems.commands.ClawArmPositionCommand;
import org.firstinspires.ftc.teamcode.subsystems.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.commands.ForceClawOpenCommand;
import org.firstinspires.ftc.teamcode.subsystems.commands.SlideExtensionCommand;
import org.firstinspires.ftc.teamcode.subsystems.commands.SlidePitchCommand;

import java.util.InputMismatchException;
import java.util.concurrent.locks.Condition;

@TeleOp(name = "\uD83C\uDFAE TELEOP BOOM BOOM", group = "Final OpModes")
@Config
public class Teleop extends OpMode {
    private Robot robot;
    private Layer layer = Layer.SAMPLE;

    private enum Layer {
        SAMPLE,
        SPEC
    }

    private final Command modeChange = new InstantCommand(() -> {
        if (layer == Layer.SAMPLE) {
            layer = Layer.SPEC;
        } else {
            layer = Layer.SAMPLE;
        }
    });

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap, telemetry, false, new Pose2d(0,0,0));
        robot.rotatingExtensionArm.setConfig(false);
        // setup bindings
        GamepadEx gamepad1ex = new GamepadEx(gamepad1);
        CommandScheduler.getInstance().schedule(new DriveCommand(gamepad1ex, robot.drive));
        // layers
        gamepad1ex.getGamepadButton(GamepadKeys.Button.START).whenPressed(modeChange);
        // spec deposit / sample deposit
        gamepad1ex.getGamepadButton(GamepadKeys.Button.LEFT_STICK_BUTTON).whenPressed(
                new SequentialCommandGroup(
                        new ConditionalCommand(
                                new InstantCommand(),
                                new SequentialCommandGroup(
                                    new SlidePitchCommand(robot.rotatingExtensionArm, SlidePitchCommand.down+50),
                                    new WaitCommand(700),
                                    new SlideExtensionCommand(robot.rotatingExtensionArm, 0)
                                ),
                                () -> {
                                    return (90 - abs(RotatingExtensionArm.degrees(RotatingExtensionArm.targetPitchPosition))) > 70;
                                }
                        ),
                        CommandStatics.depositModeCommand(robot)
                )

        );
        // spec macro
        gamepad1ex.getGamepadButton(GamepadKeys.Button.Y).whenPressed(
                        new SequentialCommandGroup(
                                new SlideExtensionCommand(robot.rotatingExtensionArm, -120),
                                new WaitCommand(300),
                                new ClawArmPositionCommand(robot.clawArm, ClawArm.PresetSetting.SPEC_DEPOSIT),
                                new WaitCommand(500),
                                new ClawArmPositionCommand(robot.clawArm, ClawArm.PresetSetting.SPEC_DEPOSIT_2),
                                new SlideExtensionCommand(robot.rotatingExtensionArm, -60)
                        )
        ).whenReleased(
                new ClawArmPositionCommand(robot.clawArm, ClawArm.PresetSetting.DEPOSIT)
        );
        // intaking for samples
        gamepad1ex.getGamepadButton(GamepadKeys.Button.RIGHT_STICK_BUTTON).whenPressed(
                new ConditionalCommand(
                        new SequentialCommandGroup(
                                CommandStatics.intakeModeCommand(robot),
                                new SlideExtensionCommand(robot.rotatingExtensionArm, -200),
                                new SlidePitchCommand(robot.rotatingExtensionArm, -1300)
                        ),
                        new InstantCommand(),
                        () -> {return (90 - abs(RotatingExtensionArm.degrees(RotatingExtensionArm.targetPitchPosition))) > 70; }
                )
        );
        // change claw angle
        gamepad1ex.getGamepadButton(GamepadKeys.Button.X).whenPressed(() -> { robot.clawArm.otherAngle = !robot.clawArm.otherAngle; });
        // auto extend
        gamepad1ex.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(
                        new ConditionalCommand(
                                new SlideExtensionCommand(robot.rotatingExtensionArm, -750),
                                new SlideExtensionCommand(robot.rotatingExtensionArm, -200),
                                () -> {
                                    return (90 - abs(RotatingExtensionArm.degrees(RotatingExtensionArm.targetPitchPosition))) > 60;
                                }
                        )
        );

        gamepad1ex.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(
                () -> { RotatingExtensionArm.rawPower = 1.0; }
        ).whenReleased(
                () -> { RotatingExtensionArm.rawPower = 0.0; }
        );

        gamepad1ex.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(
                () -> { RotatingExtensionArm.rawPower = -1.0; }
        ).whenReleased(
                () -> { RotatingExtensionArm.rawPower = 0.0; }
        );
        gamepad1ex.getGamepadButton(GamepadKeys.Button.B).whenPressed(() -> robot.clawArm.claw.setPosition(0.0))
                        .whenInactive(() -> robot.clawArm.claw.setPosition(1.0));
        gamepad1ex.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
                        new ClawArmPositionCommand(robot.clawArm, ClawArm.PresetSetting.INTAKE_PRE)
        ).whenInactive(new ClawArmPositionCommand(robot.clawArm, ClawArm.PresetSetting.INTAKE));
    }

    @Override
    public void init_loop() {
        robot.periodic();
    }

    @Override
    public void loop() {
        telemetry.addData("mode", layer.toString());
        robot.periodic();
    }
}
