package dml.majiang.simulator.impl.guipai;

import dml.majiang.simulator.base.controller.Controller;
import dml.majiang.simulator.impl.guipai.service.GuipaiPlayService;

public class GuipaiSimulatorStarter {
    public static void main(String[] args) {
        // 启动模拟器
        Controller.startup(new GuipaiPlayService(), 1L);
    }
}
