package org.firstinspires.ftc.teamcode.subsystems.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Hubs;

public class CacheClear extends CommandBase {
    private final Hubs hubs;

    public CacheClear(Hubs hubs) {
        this.hubs = hubs;
    }

    @Override
    public void execute() {
        hubs.clearCache();
    }
}
