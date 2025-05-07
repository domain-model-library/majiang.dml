package dml.majiang.simulator.impl.xuezhandaodi;

import dml.majiang.simulator.base.controller.Controller;
import dml.majiang.simulator.impl.xuezhandaodi.service.XuezhandaodiPlayService;

public class XuezhandaodiSimulatorStarter {
    public static void main(String[] args) {
        // 启动模拟器
        Controller.startup(new XuezhandaodiPlayService(), 1L);
    }
}
