package test.dml.majiang.simulator.impl.biaozhun;

import test.dml.majiang.simulator.base.controller.Controller;
import test.dml.majiang.simulator.impl.biaozhun.service.BiaozhunPlayService;

public class BiaozhunSimulatorStarter {
    public static void main(String[] args) {
        // 启动模拟器
        Controller.startup(new BiaozhunPlayService());
    }
}
