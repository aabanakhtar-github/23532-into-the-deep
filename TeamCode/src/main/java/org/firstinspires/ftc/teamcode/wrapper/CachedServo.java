package org.firstinspires.ftc.teamcode.wrapper;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

public class CachedServo implements Servo {
    public Servo s;
    private double cache = 0.0;

    public CachedServo(HardwareMap map, String name) {
        s = map.get(Servo.class, name);
        if (s == null) {
            throw new RuntimeException("what the flip!");
        }
        cache = getPosition();
    }

    @Override
    public ServoController getController() {
        return s.getController();
    }

    @Override
    public int getPortNumber() {
        return s.getPortNumber();
    }

    @Override
    public void setDirection(Direction direction) {
        s.setDirection(direction);
    }

    @Override
    public Direction getDirection() {
        return s.getDirection();
    }

    @Override
    public void setPosition(double position) {
        if (position != cache) {
            cache = position;
            s.setPosition(position);
        }
    }

    @Override
    public double getPosition() {
        return cache;
    }

    @Override
    public void scaleRange(double min, double max) {
        s.scaleRange(min, max);
    }

    @Override
    public Manufacturer getManufacturer() {
        return s.getManufacturer();
    }

    @Override
    public String getDeviceName() {
        return s.getDeviceName();
    }

    @Override
    public String getConnectionInfo() {
        return s.getConnectionInfo();
    }

    @Override
    public int getVersion() {
        return s.getVersion();
    }

    @Override
    public void resetDeviceConfigurationForOpMode() {
        s.resetDeviceConfigurationForOpMode();
    }

    @Override
    public void close() {
        s.close();
    }
}
