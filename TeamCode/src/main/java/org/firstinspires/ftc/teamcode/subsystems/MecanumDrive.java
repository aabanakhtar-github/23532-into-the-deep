package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.wrapper.CachedMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MecanumDrive extends SubsystemBase {
    private final CachedMotor leftFront;
    private final CachedMotor leftBack;
    private final CachedMotor rightFront;
    private final CachedMotor rightBack;

    private final Telemetry telemetry;

    // Constructor to initialize motors with HardwareMap and telemetry
    public MecanumDrive(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        leftFront = new CachedMotor(hardwareMap, "leftFront");
        rightFront = new CachedMotor(hardwareMap, "rightFront");
        rightBack = new CachedMotor(hardwareMap, "rightBack");
        leftBack = new CachedMotor(hardwareMap, "leftBack");

        // Reverse the direction of the backward motors
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
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

        // Calculate motor powers
        double leftFrontPower = y + x + rotation;
        double leftBackPower = y - x + rotation;
        double rightFrontPower = y - x - rotation;
        double rightBackPower = y + x - rotation;

        // Set motor powers
        leftFront.setPower(leftFrontPower);
        leftBack.setPower(leftBackPower);
        rightFront.setPower(rightFrontPower);
        rightBack.setPower(rightBackPower);
    }

}
