package org.tinygame.herostory;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.tinygame.herostory.msg.GameMsgProtocol;

public class GameMsgDecoder extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(!(msg instanceof BinaryWebSocketFrame)){
            return;
        }
        BinaryWebSocketFrame frame = (BinaryWebSocketFrame)msg;
        ByteBuf byteBuf = frame.content();

        Message.Builder msgBuilder = null;


        byteBuf.readShort();//读取消息的长度
        int msgCode = byteBuf.readShort();//读取消息编号

        byte[] msgBody = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(msgBody);

        switch(msgCode){
            case GameMsgProtocol.MsgCode.USER_ENTRY_CMD_VALUE:
                msgBuilder = GameMsgProtocol.UserEntryCmd.newBuilder();
                break;


            case GameMsgProtocol.MsgCode.WHO_ELSE_IS_HERE_CMD_VALUE:
                msgBuilder = GameMsgProtocol.WhoElseIsHereCmd.newBuilder();
                break;


            case GameMsgProtocol.MsgCode.USER_MOVE_TO_CMD_VALUE:
                msgBuilder = GameMsgProtocol.UserMoveToCmd.newBuilder();
                break;
        }

        msgBuilder.clear();
        msgBuilder.mergeFrom(msgBody);
        Message newMsg = msgBuilder.build();


        if(newMsg != null) {
            ctx.fireChannelRead(newMsg);
        }


    }
}
