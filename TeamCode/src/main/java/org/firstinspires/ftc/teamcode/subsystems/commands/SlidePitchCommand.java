package org.firstinspires.ftc.teamcode.subsystems.commands;

import static java.lang.Math.abs;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.RotatingExtensionArm;

@Config
public class SlidePitchCommand extends CommandBase {
    private RotatingExtensionArm subsystem;
    private double targetPos = 0.0;

    public static double UP = -400.0;
    public static double DOWN = -1600.0;

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
