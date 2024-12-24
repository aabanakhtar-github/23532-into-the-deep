package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.subsystems.RotatingExtensionArm;

@Config
@TeleOp(name = "RotatingExtensionArm Tuning", group = "Tuning")
public class RotatingExtensionArmTuningOpMode extends OpMode {

    private RotatingExtensionArm rotatingExtensionArm;

    // These static variables will be accessible and modifiable via FTC Dashboard
    public static double targetPitchPosition = 0.0;
    public static double targetSlidePosition = 0.0;
    public static double rawPower = 0.0;

    @Override
    public void init() {
        rotatingExtensionArm = new RotatingExtensionArm(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
        // Update target positions and raw power from FTC Dashboard values
        rotatingExtensionArm.setTargetPitchPosition(targetPitchPosition);
        rotatingExtensionArm.setTargetSlidePosition(targetSlidePosition);
        rotatingExtensionArm.setRawPower(rawPower);

        // Update the subsystem to use the latest settings
        rotatingExtensionArm.periodic();

        // Optionally log additional data
        telemetry.addData("Target Pitch Position", targetPitchPosition);
        telemetry.addData("Target Slide Position", targetSlidePosition);
        telemetry.addData("Raw Power", rawPower);
        telemetry.update();
    }
}
