package org.tinygame.herostory;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public final class Broadcaster {
    private static final ChannelGroup _channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    private Broadcaster(){

    }

    public static void addChannel(Channel channel){
        _channelGroup.add(channel);
    }

    public static void removeChannel(Channel channel) {
        _channelGroup.remove(channel);
    }

    public static void broadcast(Object msg){
        if(msg == null){
            return;
        }

        _channelGroup.writeAndFlush(msg);
    }

}
