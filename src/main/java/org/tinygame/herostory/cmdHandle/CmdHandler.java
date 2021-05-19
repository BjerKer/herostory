package org.tinygame.herostory.cmdHandle;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;

public interface CmdHandler<TCmd extends GeneratedMessageV3> {
    void handle(ChannelHandlerContext ctx, TCmd cmd);
}
