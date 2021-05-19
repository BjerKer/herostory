package org.tinygame.herostory;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import org.tinygame.herostory.cmdHandle.*;
import org.tinygame.herostory.model.UserManger;
import org.tinygame.herostory.msg.GameMsgProtocol;


public class GameMsgHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        Broadcaster.removeChannel(ctx.channel());

        Integer userId = (Integer) ctx.channel().attr(AttributeKey.valueOf("userID")).get();

        if(userId == null){
            return;
        }

        UserManger.removeUserById(userId);

        GameMsgProtocol.UserQuitResult.Builder resultBuilder = GameMsgProtocol.UserQuitResult.newBuilder();
        resultBuilder.setQuitUserId(userId);

        GameMsgProtocol.UserQuitResult newResult = resultBuilder.build();
        Broadcaster.broadcast(newResult);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        Broadcaster.addChannel(ctx.channel());

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("收到客服端信息， msgClazz =" + msg.getClass().getName() + " msg =" + msg);
        CmdHandler<? extends GeneratedMessageV3> cmdHandler = CmdHandlerFactory.creat(msg.getClass());

        if(cmdHandler != null) {
            cmdHandler.handle(ctx,cast(msg));
        }
    }



    private static <TCmd extends GeneratedMessageV3> TCmd cast(Object msg){
        if(msg == null) {
            return null;
        } else {
            return (TCmd) msg;
        }
    }

}
