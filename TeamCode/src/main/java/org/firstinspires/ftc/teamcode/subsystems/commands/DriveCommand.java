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
        subsystem.drive(-gamepad.getLeftX(), gamepad.getLeftY(), -gamepad.getRightX(),
                1.0);
    }
}
