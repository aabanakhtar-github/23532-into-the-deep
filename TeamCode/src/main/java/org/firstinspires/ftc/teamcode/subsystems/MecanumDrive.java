package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.wrapper.CachedMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;
import java.util.List;

public class MecanumDrive extends SubsystemBase {
    private org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive drive;
    private final Telemetry telemetry;

    // Constructor to initialize motors with HardwareMap and telemetry
    public MecanumDrive(HardwareMap hardwareMap, Telemetry telemetry, Pose2d pose) {
        this.telemetry = telemetry;
        drive = new org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive(hardwareMap, pose);
    }

    /**
     * Sets motor powers based on joystick inputs.
     * @param x The strafing input (-1 to 1).
     * @param y The forward/backward input (-1 to 1, negative for forward).
     * @param rotation The rotational input (-1 to 1).
     * @param speedMultiplier A multiplier to adjust overall speed (0 to 1).
     */
    public void drive(double x, double y, double rotation, double speedMultiplier) {
        // Invert y for correct forward/backward control
        y = -y;
        drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(x, y),
                rotation
        ));
    }

    public TrajectoryActionBuilder actionBuilder(Pose2d startPose) {
        return drive.actionBuilder(startPose);
    }

    public Pose2d getPose() {
        return drive.localizer.getPose();
    }

    @Override
    public void periodic() {
    }
}
