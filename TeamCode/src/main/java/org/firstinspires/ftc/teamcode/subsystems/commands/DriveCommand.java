package org.firstinspires.ftc.teamcode.subsystems.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;

public class DriveCommand extends CommandBase {
    private final MecanumDrive subsystem;
    private final GamepadEx gamepad;

    public DriveCommand(GamepadEx gamepad, MecanumDrive subsystem) {
        addRequirements(subsystem);
        this.gamepad = gamepad;
        this.subsystem = subsystem;
    }

    @Override
    public void execute() {
        subsystem.drive(-gamepad.getLeftX() * 0.8, gamepad.getLeftY() * 0.8, -gamepad.getRightX() * 0.8,
                1.0);
    }
}
