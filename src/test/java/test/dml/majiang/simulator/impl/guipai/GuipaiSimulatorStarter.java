package test.dml.majiang.simulator.impl.guipai;

import test.dml.majiang.simulator.base.controller.Controller;
import test.dml.majiang.simulator.impl.guipai.service.GuipaiPlayService;

public class GuipaiSimulatorStarter {
    public static void main(String[] args) {
        // 启动模拟器
        Controller.startup(new GuipaiPlayService());
    }
}
