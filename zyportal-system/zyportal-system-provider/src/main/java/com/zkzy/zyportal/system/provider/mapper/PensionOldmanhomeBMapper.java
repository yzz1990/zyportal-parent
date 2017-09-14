package com.zkzy.zyportal.system.provider.mapper;

import com.zkzy.zyportal.system.api.entity.PensionOldmanhomeB;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface PensionOldmanhomeBMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PENSION_OLDMANHOME_B
     *
     * @mbggenerated Wed May 10 16:40:27 CST 2017
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PENSION_OLDMANHOME_B
     *
     * @mbggenerated Wed May 10 16:40:27 CST 2017
     */
    int insert(PensionOldmanhomeB record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PENSION_OLDMANHOME_B
     *
     * @mbggenerated Wed May 10 16:40:27 CST 2017
     */
    PensionOldmanhomeB selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PENSION_OLDMANHOME_B
     *
     * @mbggenerated Wed May 10 16:40:27 CST 2017
     */
    List<PensionOldmanhomeB> selectAll(String param);

    List<PensionOldmanhomeB> selectName();
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PENSION_OLDMANHOME_B
     *
     * @mbggenerated Wed May 10 16:40:27 CST 2017
     */
    int updateByPrimaryKey(PensionOldmanhomeB record);

    List<PensionOldmanhomeB> selectbyName(PensionOldmanhomeB record);
}