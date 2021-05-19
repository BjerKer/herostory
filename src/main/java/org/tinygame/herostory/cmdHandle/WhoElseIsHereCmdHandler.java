package org.tinygame.herostory.cmdHandle;

import io.netty.channel.ChannelHandlerContext;
import org.tinygame.herostory.model.User;
import org.tinygame.herostory.model.UserManger;
import org.tinygame.herostory.msg.GameMsgProtocol;

public class WhoElseIsHereCmdHandler implements CmdHandler<GameMsgProtocol.WhoElseIsHereCmd>{
    @Override
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.WhoElseIsHereCmd msg) {
        GameMsgProtocol.WhoElseIsHereResult.Builder resultBuilder = GameMsgProtocol.WhoElseIsHereResult.newBuilder();

        for(User currUser : UserManger.listUser()){
            if(currUser == null) {
                continue;
            }

            GameMsgProtocol.WhoElseIsHereResult.UserInfo.Builder userInfoBuilder = GameMsgProtocol.WhoElseIsHereResult.UserInfo.newBuilder();
            userInfoBuilder.setUserId(currUser.getUserId());
            userInfoBuilder.setHeroAvatar(currUser.getHeroAvatar());
            resultBuilder.addUserInfo(userInfoBuilder);
        }

        GameMsgProtocol.WhoElseIsHereResult newResult = resultBuilder.build();
        ctx.writeAndFlush(newResult);
    }
}
