package org.firstinspires.ftc.teamcode.subsystems;

import static java.lang.Math.abs;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.wrapper.CachedMotor;

import java.util.Arrays;
import java.util.List;

/**
 * Rotating arm class
 */
// TODO: add voltage compensation
@Config
public class RotatingExtensionArm extends SubsystemBase {
    public static double degrees(double tick) {
        return 360 / 5980.8 * tick;
    }

    public enum PitchState {
        RELAXED,
        PID,
    };

    public static PitchState pitchState;

    public static boolean tuning = false;

    public static double pitchP = -0.025;
    public static double pitchD = 0.2;
    public static double pitchCos = 0.2;

    public static double slideP = 0.03;
    public static double slideD = 0.3;
    public static double slideSin = 0.0;
    public static double slideE = 0.13;

    public static double targetPitchPosition = 0.0;
    public static double targetSlidePosition = 0.0;
    public static double rawPower = 0.0;

    private final CachedMotor leftSlide;
    private final CachedMotor rightSlide;
    private final CachedMotor pitch;

    private final PIDController slideController = new PIDController(slideP, 0.0, slideD);
    private final PIDController pitchController = new PIDController(pitchP, 0.0, pitchD);

    private final Telemetry telemetry;

    public static double maxExtension = 750;
    public static double maxHorizontalExtension = 250;
    public static double armStartAngle = 90.0;

    public RotatingExtensionArm(HardwareMap hwMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        leftSlide = new CachedMotor(hwMap, "slideL");
        rightSlide = new CachedMotor(hwMap, "slideR");
        pitch = new CachedMotor(hwMap, "pitch");
        List<CachedMotor> motors = Arrays.asList(leftSlide, rightSlide, pitch);
        motors.forEach(x -> {
            x.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            x.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        });
        leftSlide.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void periodic() {
        if (tuning) {
            slideController.setPID(slideP, 0.0, slideD);
            pitchController.setPID(pitchP, 0.0, pitchD);
        }
        // arm offset at 123
        double armAngle = armStartAngle - abs(degrees(pitch.m.getCurrentPosition()));
        // run pid pitch
        double pitchOutput = pitchController.calculate(targetPitchPosition, pitch.m.getCurrentPosition()) + Math.cos(Math.toRadians(armAngle)) * pitchCos +  slideE * getExtensionRate();
        pitch.setPower(pitchOutput);
        telemetry.addData("pitch output on pid!", pitchOutput);
        // slide pid
        double slideOutput = slideController.calculate(targetSlidePosition, rightSlide.m.getCurrentPosition()) + Math.sin(Math.toRadians(armAngle)) * slideSin;
        if (-0.1 < rawPower && rawPower < 0.1) {
            leftSlide.setPower(slideOutput);
            rightSlide.setPower(slideOutput);
        }
        else {
            targetSlidePosition = getCurrentSlidePosition();
            // cutoff at max
            if (armAngle > 70 && abs(targetSlidePosition) > maxExtension) return;
            else if (armAngle < 45 && abs(targetSlidePosition) > maxHorizontalExtension) return;
            leftSlide.setPower(rawPower);
            rightSlide.setPower(rawPower);
        }
        log();
    }

    public void setConfig(boolean autonomous) {
        if (autonomous) {
            pitchController.setPID(Configs.Autonomous.pitchP, 0, Configs.Autonomous.pitchD);
            slideController.setPID(Configs.Autonomous.slideP, 0, Configs.Autonomous.slideD);
        } else {
            pitchController.setPID(Configs.TeleOp.pitchP, 0, Configs.TeleOp.pitchD);
            slideController.setPID(Configs.TeleOp.slideP, 0, Configs.TeleOp.slideD);
        }
    }

    public double getCurrentSlidePosition() {
        return rightSlide.m.getCurrentPosition();
    }

    public double getCurrentPitchPosition() {
        return pitch.m.getCurrentPosition();
    }

    private double getExtensionRate() {
        return abs(getCurrentSlidePosition()) / maxExtension;
    }

    public void log() {
        telemetry.addData("slide pos", rightSlide.m.getCurrentPosition());
        telemetry.addData("slide target", targetSlidePosition);
        telemetry.addData("slide pos", rightSlide.m.getCurrentPosition());
        telemetry.addData("pitch pos", pitch.m.getCurrentPosition());
        telemetry.addData("pitch angle", degrees(getCurrentPitchPosition()));
        telemetry.addData("power!", rightSlide.getPower());
        telemetry.addData("pitch power!", pitch.getPower());
    }
}
