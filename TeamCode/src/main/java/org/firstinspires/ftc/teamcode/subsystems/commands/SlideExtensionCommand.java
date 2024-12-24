package org.firstinspires.ftc.teamcode.subsystems.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.RotatingExtensionArm;

public class SlideExtensionCommand extends CommandBase {
    private final RotatingExtensionArm subsystem;
    private double targetPos = 0.0;

    public SlideExtensionCommand(RotatingExtensionArm subsystem, double targetPos) {
        addRequirements(subsystem);
        this.subsystem = subsystem;
        this.targetPos = targetPos;
    }

    @Override
    public void initialize() {
        subsystem.setTargetSlidePosition(targetPos);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
