package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.commands.ClawArmPositionCommand;
import org.firstinspires.ftc.teamcode.subsystems.commands.SlideExtensionCommand;
import org.firstinspires.ftc.teamcode.subsystems.commands.SlidePitchCommand;

/**
 * Just a bunch of commands condensed into a file
 */
public class CommandStatics {
    public static Command depositModeCommand(@NonNull Robot robot) {
        return new SequentialCommandGroup(
                new SlideExtensionCommand(robot.rotatingExtensionArm, -200),
                new InstantCommand(() -> {
                    RotatingExtensionArm.rawPower = 0.0;
                }),
                new SlidePitchCommand(robot.rotatingExtensionArm, SlidePitchCommand.up),
                new InstantCommand(() -> robot.clawArm.setPreset(ClawArm.PresetSetting.DEPOSIT)),
                new SlideExtensionCommand(robot.rotatingExtensionArm, 0)
        );
    }

    public static Command intakeModeCommand(@NonNull Robot robot) {
        return new SequentialCommandGroup(
                new InstantCommand(() -> {
                    RotatingExtensionArm.rawPower = 0.0;
                }),
                new SlideExtensionCommand(robot.rotatingExtensionArm, -200),
                new ClawArmPositionCommand(robot.clawArm, ClawArm.PresetSetting.INTAKE),
                new SlidePitchCommand(robot.rotatingExtensionArm, SlidePitchCommand.down),
                new SlideExtensionCommand(robot.rotatingExtensionArm, -500)
        );
    }
}
