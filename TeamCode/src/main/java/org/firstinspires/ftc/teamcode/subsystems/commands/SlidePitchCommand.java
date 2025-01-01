package org.firstinspires.ftc.teamcode.subsystems.commands;

import static java.lang.Math.abs;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.RotatingExtensionArm;

public class SlidePitchCommand extends CommandBase {
    private RotatingExtensionArm subsystem;
    private double targetPos = 0.0;

    public static double UP = 1600.0;
    public static double DOWN = 250.0;

    public SlidePitchCommand(RotatingExtensionArm subsystem, double targetPos) {
        addRequirements(subsystem);
        this.subsystem = subsystem;
        this.targetPos = targetPos;
    }

    @Override
    public void execute() {
       subsystem.setTargetPitchPosition(targetPos);
    }

    @Override
    public boolean isFinished() {
        double error = abs(subsystem.getTargetPitchPosition() - subsystem.getCurrentPitchPosition());
        return error < 150.0;
    }
}
