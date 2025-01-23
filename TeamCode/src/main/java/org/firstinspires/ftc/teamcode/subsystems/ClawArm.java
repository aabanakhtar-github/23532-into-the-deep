package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.wrapper.CachedServo;

@Config
public class ClawArm extends SubsystemBase {
    public CachedServo leftArmPivot, rightArmPivot, clawAnglePivot, claw, clawRotationPivot;
    private final Telemetry telemetry;

    public static double initArmPos = 1.0;
    public static double initClawPos = 1.0;
    public static double initClawAngle = 0.65;
    public static PresetConfig initConfig = new PresetConfig(1.0, 1.0, 0.65);
    public static PresetConfig intakeConfig = new PresetConfig(0.25, 1.0, 0.65);
    public static PresetConfig preIntakeConfig = new PresetConfig(0.55, 1, 0.65);
    public static PresetConfig outtakeConfig = new PresetConfig(0.3, 0.3, 0.7);
    public static PresetConfig specDepositConfig2 = new PresetConfig(0.2, 0.55, 0.65);
    public static PresetConfig specDepositConfig = new PresetConfig(0.3, 0.55, 0.65);

    public static double secondClawAngle = 0.25;
    public boolean otherAngle = false;

    public static enum PresetSetting {
        INIT,
        INTAKE,
        INTAKE_PRE,
        DEPOSIT,
        SPEC_DEPOSIT_2, SPEC_DEPOSIT
    }

    public static class PresetConfig {
        // angle is the actual rotation relative to game element
        public double arm, clawRotation, clawAngle;

        public PresetConfig(double arm, double clawRotation, double clawAngle) {
           this.arm = arm;
           this.clawRotation = clawRotation;
           this.clawAngle = clawAngle;
        }
    }

    public static PresetSetting setting = PresetSetting.INIT;

    public ClawArm(HardwareMap hwmap, Telemetry telemetry) {
        leftArmPivot = new CachedServo(hwmap, "pivotL");
        rightArmPivot = new CachedServo(hwmap, "pivotR");
        clawAnglePivot = new CachedServo(hwmap, "pivotC");
        clawRotationPivot = new CachedServo(hwmap, "clawPivot");
        claw = new CachedServo(hwmap, "claw");
        leftArmPivot.setDirection(Servo.Direction.REVERSE);
        this.telemetry = telemetry;
        otherAngle = false;
    }


    public void setPreset(PresetSetting setting) {
        ClawArm.setting = setting;
    }

    public void acceptPreset(PresetConfig config, boolean override) {
        leftArmPivot.setPosition(config.arm);
        rightArmPivot.setPosition(config.arm);
        clawAnglePivot.setPosition(config.clawRotation);
        if (override) {
            clawRotationPivot.setPosition(secondClawAngle);
            return;
        }
        clawRotationPivot.setPosition(config.clawAngle);
    }

    @Override
    public void periodic() {
        telemetry.addData("mode:", setting.toString());
        switch (setting) {
            case INTAKE:
                acceptPreset(intakeConfig, otherAngle);
                break;
            case DEPOSIT:
                acceptPreset(outtakeConfig, otherAngle);
                break;
            case INIT:
                acceptPreset(initConfig, otherAngle);
                break;
            case INTAKE_PRE:
                acceptPreset(preIntakeConfig, otherAngle);
                break;
            case SPEC_DEPOSIT:
                acceptPreset(specDepositConfig, false);
                break;
            case SPEC_DEPOSIT_2:
                acceptPreset(specDepositConfig2, false);
                break;
        }
    }
}
