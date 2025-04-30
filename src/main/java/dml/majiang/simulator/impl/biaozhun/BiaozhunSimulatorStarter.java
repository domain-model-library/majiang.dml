package dml.majiang.simulator.impl.biaozhun;

import dml.majiang.simulator.base.controller.Controller;
import dml.majiang.simulator.impl.biaozhun.service.BiaozhunPlayService;

public class BiaozhunSimulatorStarter {
    public static void main(String[] args) {
        // 启动模拟器
        Controller.startup(new BiaozhunPlayService(), 1L);
    }
}
