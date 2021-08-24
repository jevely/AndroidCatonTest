# AndroidCatonTest
Android卡顿检测Demo

利用Choreographer获取到屏幕绘制每一帧的回调，在回调中根据阀值判断主线程方法栈是否有重复的，如果有，代表卡顿。
