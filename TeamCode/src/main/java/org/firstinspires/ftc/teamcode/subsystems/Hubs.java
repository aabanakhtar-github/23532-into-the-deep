package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

public class Hubs extends SubsystemBase {
    private final List<LynxModule> modules;
    private final Telemetry telemetry;
    private final ElapsedTime timer;

    public Hubs(HardwareMap map, Telemetry telemetry) {
        modules = map.getAll(LynxModule.class);
        modules.forEach(x -> x.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL));
        this.telemetry = telemetry;
        timer = new ElapsedTime();
        clearCache();
    }

    public void clearCache() {
        modules.forEach(LynxModule::clearBulkCache);
        telemetry.addData("LOOP TIME (hz): ", 1.0 / timer.seconds());
    }
}