package com.zkzy.zyportal.system.provider.mapper;

import com.zkzy.zyportal.system.api.entity.EvaluationPoint;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface EvaluationPointMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table EVALUATION_POINT
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table EVALUATION_POINT
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    int insert(EvaluationPoint record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table EVALUATION_POINT
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    EvaluationPoint selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table EVALUATION_POINT
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    List<EvaluationPoint> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table EVALUATION_POINT
     *
     * @mbggenerated Sat May 20 15:38:59 CST 2017
     */
    int updateByPrimaryKey(EvaluationPoint record);
}