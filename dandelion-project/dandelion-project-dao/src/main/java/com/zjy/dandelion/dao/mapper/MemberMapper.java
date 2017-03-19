package com.zjy.dandelion.dao.mapper;


import com.zjy.dandelion.dao.mapper.base.BaseMapper;
import com.zjy.dandelion.entity.Member;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MemberMapper extends BaseMapper<Member> {

    @ResultMap("DetailMap")
    Member selectByPrimaryKey(Long id);

    @Select(" select * from `member` where name = #{name}  ")
    Member selectByName(@Param("name") String name);

    List<Member> findAll();

    @Delete("delete from `member` where id = #{id} ")
    public int deleteById(@Param("id") Long id);

}
