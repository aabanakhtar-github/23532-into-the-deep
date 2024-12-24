package org.firstinspires.ftc.teamcode.wrapper;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
    Cached Motor for optimal performance
 */
public class CachedMotor implements DcMotorSimple {
    public DcMotorEx m;
    private double cache = 0.0f;

    public CachedMotor(HardwareMap map, String name) {
        m = map.get(DcMotorEx.class, name);
        if (m == null) {
            throw new RuntimeException("what the flip!");
        }
    }

    @Override
    public void setDirection(Direction direction) {
        m.setDirection(direction);
    }

    public void setMode(DcMotor.RunMode mode) {
        m.setMode(mode);
    }

    @Override
    public Direction getDirection() {
        return m.getDirection();
    }

    @Override
    public void setPower(double power) {
        if (power != cache) {
            cache = power;
            m.setPower(power);
        }
    }

    @Override
    public double getPower() {
        return cache;
    }

    @Override
    public Manufacturer getManufacturer() {
        return m.getManufacturer();
    }

    @Override
    public String getDeviceName() {
        return  m.getDeviceName();
    }

    @Override
    public String getConnectionInfo() {
        return m.getConnectionInfo();
    }

    @Override
    public int getVersion() {
        return m.getVersion();
    }

    @Override
    public void resetDeviceConfigurationForOpMode() {
        m.resetDeviceConfigurationForOpMode();
    }

    @Override
    public void close() {
        m.close();
    }
}