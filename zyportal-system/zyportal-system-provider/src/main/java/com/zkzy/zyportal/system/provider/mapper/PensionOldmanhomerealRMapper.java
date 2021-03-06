package com.zkzy.zyportal.system.provider.mapper;

import com.zkzy.zyportal.system.api.entity.PensionOldmanhomerealR;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PensionOldmanhomerealRMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PENSION_OLDMANHOMEREAL_R
     *
     * @mbggenerated Mon May 08 17:32:08 CST 2017
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PENSION_OLDMANHOMEREAL_R
     *
     * @mbggenerated Mon May 08 17:32:08 CST 2017
     */
    int insert(PensionOldmanhomerealR record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PENSION_OLDMANHOMEREAL_R
     *
     * @mbggenerated Mon May 08 17:32:08 CST 2017
     */
    PensionOldmanhomerealR selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PENSION_OLDMANHOMEREAL_R
     *
     * @mbggenerated Mon May 08 17:32:08 CST 2017
     */
    List<PensionOldmanhomerealR> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PENSION_OLDMANHOMEREAL_R
     *
     * @mbggenerated Mon May 08 17:32:08 CST 2017
     */
    int updateByPrimaryKey(PensionOldmanhomerealR record);




}