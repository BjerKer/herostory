package org.tinygame.herostory.cmdHandle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.tinygame.herostory.Broadcaster;
import org.tinygame.herostory.model.User;
import org.tinygame.herostory.model.UserManger;
import org.tinygame.herostory.msg.GameMsgProtocol;

public class UserEntryCmdHandler implements CmdHandler<GameMsgProtocol.UserEntryCmd>{
    @Override
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.UserEntryCmd msg) {
        GameMsgProtocol.UserEntryCmd cmd = msg;
        int userId = cmd.getUserId();
        String heroAvatar = cmd.getHeroAvatar();


        GameMsgProtocol.UserEntryResult.Builder resultBuilder = GameMsgProtocol.UserEntryResult.newBuilder();
        resultBuilder.setUserId(userId);
        resultBuilder.setHeroAvatar(heroAvatar);

        User newUser = new User();
        newUser.setUserId(userId);
        newUser.setHeroAvatar(heroAvatar);
        UserManger.addUser(newUser);

        ctx.channel().attr(AttributeKey.valueOf("userId")).set(userId);


        GameMsgProtocol.UserEntryResult newResult = resultBuilder.build();
        Broadcaster.broadcast(newResult);
    }
}
