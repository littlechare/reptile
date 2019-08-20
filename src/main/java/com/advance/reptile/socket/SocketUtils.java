package com.advance.reptile.socket;

import org.springframework.util.StringUtils;

/**
 * 用于存放socket的公共方法
 */
public class SocketUtils {

    /**
     * 发送消息
     * @param table
     * @param mage
     */
    public static void sendMsg(String table, Message mage){
        InfomationOperateMap.map.get(table).forEach((id, iom) -> {
            try {
                iom.send(mage);
            } catch (Exception e) {
                System.err.println(e);
            }
        });
    }

}
