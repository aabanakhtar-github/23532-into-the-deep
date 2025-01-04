package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.ClawArm;
import org.firstinspires.ftc.teamcode.subsystems.CommandStatics;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.RotatingExtensionArm;
import org.firstinspires.ftc.teamcode.subsystems.commands.ClawArmPositionCommand;
import org.firstinspires.ftc.teamcode.subsystems.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.commands.SlideExtensionCommand;
import org.firstinspires.ftc.teamcode.subsystems.commands.SlidePitchCommand;

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
        robot = new Robot(hardwareMap, telemetry, false);
        // setup bindings
        GamepadEx gamepad1ex = new GamepadEx(gamepad1);
        CommandScheduler.getInstance().schedule(new DriveCommand(gamepad1ex, robot.drive));
        // layers
        gamepad1ex.getGamepadButton(GamepadKeys.Button.START).whenPressed(modeChange);
        // spec intake / sample deposit
        gamepad1ex.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(CommandStatics.depositModeCommand(robot));
        // intaking for samples
        gamepad1ex.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(CommandStatics.intakeModeCommand(robot));
        // change claw angle
        gamepad1ex.getGamepadButton(GamepadKeys.Button.X).whenPressed(() -> { ClawArm.otherAngle = !ClawArm.otherAngle; });
        // open close claw
        gamepad1ex.getGamepadButton(GamepadKeys.Button.B).whenPressed(() -> {
                robot.clawArm.claw.setPosition(0.1);
        }).whenInactive(() -> robot.clawArm.claw.setPosition(0.8));

        gamepad1ex.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(() -> {
          robot.rotatingExtensionArm.rawPower = 1.0;
        }).whenReleased(() -> {
            robot.rotatingExtensionArm.rawPower = 0.0;
        });
        gamepad1ex.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(() -> {
            robot.rotatingExtensionArm.rawPower = -1.0;
        }).whenReleased(() -> {
            robot.rotatingExtensionArm.rawPower = 0.0;
        });
        // intake aiming
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
        robot.periodic();
    }
}
