package org.firstinspires.ftc.teamcode.subsystems.commands;

import static org.firstinspires.ftc.teamcode.subsystems.RotatingExtensionArm.PitchState.PID;
import static org.firstinspires.ftc.teamcode.subsystems.RotatingExtensionArm.PitchState.RELAXED;
import static java.lang.Math.abs;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.RotatingExtensionArm;

@Config
public class SlidePitchCommand extends CommandBase {
    private RotatingExtensionArm subsystem;
    private double targetPos = 0.0;

    public static double up = -500.0;
    public static double down = -1700.0;

    public SlidePitchCommand(RotatingExtensionArm subsystem, double targetPos) {
        addRequirements(subsystem);
        this.subsystem = subsystem;
        this.targetPos = targetPos;
        RotatingExtensionArm.pitchState = PID;
    }

    @Override
    public void execute() {
       RotatingExtensionArm.targetPitchPosition = targetPos;
    }

    @Override
    public boolean isFinished() {
        double error = abs(targetPos - subsystem.getCurrentPitchPosition());
        boolean isFinished = error < 150.0;
        if (isFinished && targetPos == down) {
            RotatingExtensionArm.pitchState = RELAXED;
        }
        return isFinished;
    }
}
