package org.firstinspires.ftc.teamcode.subsystems.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.ClawArm;

import java.util.concurrent.TimeUnit;

public class ForceClawOpenCommand extends CommandBase {
    private final ElapsedTime timer;
    private final double pos;
    private final ClawArm clawArm;

    public ForceClawOpenCommand(ClawArm clawArm, double pos) {
       timer = new ElapsedTime();
       this.clawArm = clawArm;
       this.pos = pos;
    }

    @Override
    public boolean isFinished() {
       return timer.milliseconds() > 350;
    }

    @Override
    public void execute() {
        clawArm.claw.setPosition(pos);
    }
}
