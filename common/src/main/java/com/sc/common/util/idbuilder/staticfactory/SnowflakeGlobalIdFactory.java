package com.sc.common.util.idbuilder.staticfactory;

import com.sc.common.util.idbuilder.handler.SnowflakeArithmeticHandler;
import org.springframework.stereotype.Component;

/**
 * @author hp
 * @version 1.0
 * @description: ${description}
 * @date: 15:14 2018/10/15 0015
 */
@Component
public class SnowflakeGlobalIdFactory implements GlobalIdFactory<SnowflakeArithmeticHandler> {
    @Override
    public SnowflakeArithmeticHandler create() {
        return new SnowflakeArithmeticHandler(0, 0);
    }
}
