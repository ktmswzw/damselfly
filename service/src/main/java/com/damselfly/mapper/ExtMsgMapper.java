package com.damselfly.mapper;

import com.damselfly.entity.ExtMsg;
import com.damselfly.entity.ExtMsgCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtMsgMapper {
    int countByExample(ExtMsgCriteria example);

    int deleteByExample(ExtMsgCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(ExtMsg record);

    int insertSelective(ExtMsg record);

    List<ExtMsg> selectByExample(ExtMsgCriteria example);

    ExtMsg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ExtMsg record, @Param("example") ExtMsgCriteria example);

    int updateByExample(@Param("record") ExtMsg record, @Param("example") ExtMsgCriteria example);

    int updateByPrimaryKeySelective(ExtMsg record);

    int updateByPrimaryKey(ExtMsg record);
}