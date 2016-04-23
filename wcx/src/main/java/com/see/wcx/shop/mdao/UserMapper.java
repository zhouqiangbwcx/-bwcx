package com.see.wcx.shop.mdao;




import com.see.wcx.shop.entity.User;

public interface UserMapper {
    
    User selectByPrimaryKey(Integer id);

}