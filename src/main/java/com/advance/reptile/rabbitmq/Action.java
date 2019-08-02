package com.advance.reptile.rabbitmq;

public enum Action {

    ACCEPT,  // 处理成功
    RETRY,   // 可以重试的错误
    REJECT,  // 无需重试的错误

}
