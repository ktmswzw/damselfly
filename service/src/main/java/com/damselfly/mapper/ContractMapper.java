package com.damselfly.mapper;

import com.damselfly.entity.Contract;
import com.damselfly.entity.ContractCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractMapper {
    int countByExample(ContractCriteria example);

    int deleteByExample(ContractCriteria example);

    int deleteByPrimaryKey(Integer contract_id);

    int insert(Contract record);

    int insertSelective(Contract record);

    List<Contract> selectByExample(ContractCriteria example);

    Contract selectByPrimaryKey(Integer contract_id);

    int updateByExampleSelective(@Param("record") Contract record, @Param("example") ContractCriteria example);

    int updateByExample(@Param("record") Contract record, @Param("example") ContractCriteria example);

    int updateByPrimaryKeySelective(Contract record);

    int updateByPrimaryKey(Contract record);
}