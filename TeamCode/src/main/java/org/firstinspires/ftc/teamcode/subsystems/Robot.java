package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.commands.CacheClear;
import org.firstinspires.ftc.teamcode.subsystems.commands.SlidePitchCommand;

public class Robot {
    public RotatingExtensionArm rotatingExtensionArm;
    public MecanumDrive drive;
    public ClawArm clawArm;
    public Hubs hubs;
    private final Telemetry telemetry;

    public Robot(HardwareMap map, Telemetry telemetry, boolean auto) {
        this.telemetry = telemetry;
        rotatingExtensionArm = new RotatingExtensionArm(map, telemetry);
        drive = auto ? null : new MecanumDrive(map, telemetry);
        clawArm = new ClawArm(map, telemetry);
        hubs = new Hubs(map, telemetry);
        RotatingExtensionArm.targetPitchPosition = SlidePitchCommand.up;
        clawArm.setPreset(ClawArm.PresetSetting.INIT);
        CommandScheduler.getInstance().schedule(new CacheClear(hubs));

    }

    public void periodic() {
        CommandScheduler.getInstance().run();
        telemetry.update();
    }
}