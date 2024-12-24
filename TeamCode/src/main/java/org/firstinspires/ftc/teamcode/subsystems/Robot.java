package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Robot {
    public RotatingExtensionArm rotatingExtensionArm;
    public MecanumDrive drive;

    public Robot(HardwareMap map, Telemetry telemetry) {
       rotatingExtensionArm = new RotatingExtensionArm(map, telemetry);
       drive = new MecanumDrive(map, telemetry);
    }

    public void periodic() {
        CommandScheduler.getInstance().run();
    }
}
