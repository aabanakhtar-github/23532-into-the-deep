package org.firstinspires.ftc.teamcode.subsystems.commands;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ClawArm;

@Config
public class ClawArmPositionCommand extends CommandBase {
    private final ClawArm subsystem;
    private final ClawArm.PresetSetting setting;

    public ClawArmPositionCommand(ClawArm subsystem, ClawArm.PresetSetting setting) {
        addRequirements(subsystem);
        this.subsystem = subsystem;
        this.setting = setting;
    }

    @Override
    public void execute() {
        subsystem.setPreset(setting);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
