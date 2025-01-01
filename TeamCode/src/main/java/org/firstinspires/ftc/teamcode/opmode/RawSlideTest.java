package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "Raw Slide Test")
public class RawSlideTest extends OpMode {
    public static double power = 0.1;
    DcMotorEx right;
    DcMotorEx left;

    @Override
    public void init() {
        left = hardwareMap.get(DcMotorEx.class, "slideL");
        right = hardwareMap.get(DcMotorEx.class, "slideR");
        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        left.setPower(gamepad1.left_stick_x);
        right.setPower(gamepad1.left_stick_x);
    }
}
