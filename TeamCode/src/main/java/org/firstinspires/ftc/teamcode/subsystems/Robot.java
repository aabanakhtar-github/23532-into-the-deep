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

    public Robot(HardwareMap map, Telemetry telemetry) {
        this.telemetry = telemetry;
        rotatingExtensionArm = new RotatingExtensionArm(map, telemetry);
        drive = new MecanumDrive(map, telemetry);
        clawArm = new ClawArm(map, telemetry);
        hubs = new Hubs(map, telemetry);
        rotatingExtensionArm.setTargetPitchPosition(SlidePitchCommand.UP);
        clawArm.setPreset(ClawArm.PresetSetting.INIT);
        // setup robot cleanliness tasks
        CommandScheduler.getInstance().schedule(new CacheClear(hubs));
        // TODO: add logger
        //CommandScheduler.getInstance().schedule(new LoggerCommand());
    }

    public void periodic() {
        CommandScheduler.getInstance().run();
        telemetry.update();
    }
}
