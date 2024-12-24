package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.commands.DriveCommand;

@TeleOp(name = "Driver CONTROLLED ")
@Config
public class Teleop extends OpMode {
    Robot robot;

    @Override
    public void init() {
        robot = new Robot(hardwareMap, telemetry);
        GamepadEx gamepad1ex = new GamepadEx(gamepad1);
        CommandScheduler.getInstance().schedule(new DriveCommand(gamepad1ex, robot.drive));
    }

    @Override
    public void loop() {
        robot.periodic();
    }
}
