package com.how2java.youyi.mapper;

import com.how2java.youyi.pojo.Admin;
import com.how2java.youyi.pojo.AdminExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by melon on 18-1-10.
 */
public interface AdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Admin admin);

    int insertSelective(Admin admin);

    List<Admin> selectByExample(AdminExample adminExample);

    Admin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    Admin selectByUsername(@Param("username") String username, @Param("password") String password);
}
