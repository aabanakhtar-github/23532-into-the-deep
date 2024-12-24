package org.firstinspires.ftc.teamcode.subsystems.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.RotatingExtensionArm;

public class SlidePitchCommand extends CommandBase {
    private RotatingExtensionArm subsystem;
    private double targetPos = 0.0;

    public SlidePitchCommand(RotatingExtensionArm subsystem, double targetPos) {
        addRequirements(subsystem);
        this.subsystem = subsystem;
        this.targetPos = targetPos;
    }

    @Override
    public void initialize() {
       subsystem.setTargetPitchPosition(targetPos);
    }

    @Override
    public boolean isFinished() {
       return true;
    }
}
