package com.damselfly.business.mapper;

import com.damselfly.business.entity.Dictionary;
import com.damselfly.business.entity.DictionaryCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictionaryMapper {
    int countByExample(DictionaryCriteria example);

    int deleteByExample(DictionaryCriteria example);

    int deleteByPrimaryKey(Integer pkId);

    int insert(Dictionary record);

    int insertSelective(Dictionary record);

    List<Dictionary> selectByExample(DictionaryCriteria example);

    Dictionary selectByPrimaryKey(Integer pkId);

    Dictionary selectByKeyAndName(@Param("dicKey") String dicKey, @Param("dicName") String dicName);

    int updateByExampleSelective(@Param("record") Dictionary record, @Param("example") DictionaryCriteria example);

    int updateByExample(@Param("record") Dictionary record, @Param("example") DictionaryCriteria example);

    int updateByPrimaryKeySelective(Dictionary record);

    int updateByPrimaryKey(Dictionary record);

    List<Dictionary> findAllDicListForExport(Dictionary record);

    int findMaxDicIdByName(@Param("dicName") String dicName);

    String findDicCodeByDicName(@Param("dicName") String dicName);
}