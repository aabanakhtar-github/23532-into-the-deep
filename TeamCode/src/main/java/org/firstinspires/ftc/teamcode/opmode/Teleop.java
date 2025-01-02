package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.ClawArm;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.commands.ClawArmPositionCommand;
import org.firstinspires.ftc.teamcode.subsystems.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.commands.SlideExtensionCommand;
import org.firstinspires.ftc.teamcode.subsystems.commands.SlidePitchCommand;

@TeleOp(name = "\uD83C\uDFAE TELEOP BOOM BOOM")
@Config
public class Teleop extends OpMode {
    private Robot robot;
    private Layer layer = Layer.SAMPLE;

    @Override
    public void init() {
        robot = new Robot(hardwareMap, telemetry);
        // setup bindings
        GamepadEx gamepad1ex = new GamepadEx(gamepad1);
        CommandScheduler.getInstance().schedule(new DriveCommand(gamepad1ex, robot.drive));
        // layers
        gamepad1ex.getGamepadButton(GamepadKeys.Button.START).whenPressed(() -> {
            if (layer == Layer.SAMPLE) {
                layer = Layer.SPEC;
            } else {
                layer = Layer.SAMPLE;
            }
        });
        // move the pitching arm up
        // spec intake / sample deposit
        gamepad1ex.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new SequentialCommandGroup(
                new SlideExtensionCommand(robot.rotatingExtensionArm, -200),
                new InstantCommand(() -> robot.rotatingExtensionArm.setRawPower(0.0)),
                new SlidePitchCommand(robot.rotatingExtensionArm, SlidePitchCommand.UP),
                new InstantCommand(() -> robot.clawArm.setPreset(ClawArm.PresetSetting.DEPOSIT)),
                new SlideExtensionCommand(robot.rotatingExtensionArm, 0)
        ));
        // intermediate positioning for samples TODO
        // intaking for samples
        gamepad1ex.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new SequentialCommandGroup(
                new InstantCommand(() -> robot.rotatingExtensionArm.setRawPower(0.0)),
                new SlideExtensionCommand(robot.rotatingExtensionArm, -200),
                new ClawArmPositionCommand(robot.clawArm, ClawArm.PresetSetting.INTAKE),
                new SlidePitchCommand(robot.rotatingExtensionArm, SlidePitchCommand.DOWN),
                new SlideExtensionCommand(robot.rotatingExtensionArm, -1000)
        ));
        gamepad1ex.getGamepadButton(GamepadKeys.Button.X).whenPressed(() ->{ ClawArm.otherAngle = !ClawArm.otherAngle; });
        // open close claw
        gamepad1ex.getGamepadButton(GamepadKeys.Button.B).whenPressed(() -> {
                robot.clawArm.claw.setPosition(0);
        }).whenInactive(() -> robot.clawArm.claw.setPosition(0.8));
        // intake aiming
        gamepad1ex.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
                new ParallelCommandGroup(
                        new ClawArmPositionCommand(robot.clawArm, ClawArm.PresetSetting.INTAKE_PRE)
                )
        ).whenInactive(new ClawArmPositionCommand(robot.clawArm, ClawArm.PresetSetting.INTAKE));
        gamepad1ex.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new SlideExtensionCommand(robot.rotatingExtensionArm, -3000));
    }

    @Override
    public void init_loop() {
        robot.periodic();
    }

    @Override
    public void loop() {
        robot.periodic();
    }

    private enum Layer {
        SAMPLE,
        SPEC
    }
}
