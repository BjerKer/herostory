package org.tinygame.herostory.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class UserManger {
    private static final Map<Integer, User> _userMap = new HashMap<>();

    private UserManger(){

    }

    public static void addUser(User user){
        if(user != null){
            _userMap.put(user.getUserId(),user);
        }
    }

    public static void removeUserById(int userId){
        _userMap.remove(userId);
    }

    public static Collection<User> listUser(){
        return _userMap.values();
    }
}
