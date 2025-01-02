package org.firstinspires.ftc.teamcode.subsystems.commands;

import static java.lang.Math.abs;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.RotatingExtensionArm;

public class SlideExtensionCommand extends CommandBase {
    private final RotatingExtensionArm subsystem;
    private final double targetPos;

    public SlideExtensionCommand(RotatingExtensionArm subsystem, double targetPos) {
        addRequirements(subsystem);
        this.subsystem = subsystem;
        this.targetPos = targetPos;
    }

    @Override
    public void initialize() {
        RotatingExtensionArm.targetSlidePosition = targetPos;
    }

    @Override
    public boolean isFinished() {
        return abs(targetPos - subsystem.getCurrentSlidePosition()) < 150;
    }
}
