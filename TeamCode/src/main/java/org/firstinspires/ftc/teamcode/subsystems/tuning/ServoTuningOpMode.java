package org.firstinspires.ftc.teamcode.subsystems.tuning;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.ClawArm;

@TeleOp(name = "Servo Arm tuning")
public class ServoTuningOpMode extends OpMode {
    ClawArm arm;

    @Override
    public void init() {
        arm = new ClawArm(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
        arm.periodic();
    }
}
