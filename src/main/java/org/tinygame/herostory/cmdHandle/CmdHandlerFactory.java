package org.tinygame.herostory.cmdHandle;

import com.google.protobuf.GeneratedMessageV3;
import org.tinygame.herostory.msg.GameMsgProtocol;

import java.util.HashMap;
import java.util.Map;

public final class CmdHandlerFactory {
    private static Map<Class<?>, CmdHandler<? extends GeneratedMessageV3>> _handlerMap = new HashMap<>();
    private CmdHandlerFactory(){

    }

    public static void init(){
        _handlerMap.put(GameMsgProtocol.UserEntryCmd.class,new UserEntryCmdHandler());
        _handlerMap.put(GameMsgProtocol.WhoElseIsHereCmd.class,new WhoElseIsHereCmdHandler());
        _handlerMap.put(GameMsgProtocol.UserMoveToCmd.class,new UserMoveToCmdHandler());

    }

    public static CmdHandler<? extends GeneratedMessageV3> creat(Object msgClazz) {
        if(msgClazz == null) {
            return null;
        }

        return _handlerMap.get(msgClazz);
    }
}
