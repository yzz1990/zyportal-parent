package com.zkzy.zyportal.system.provider.mapper;

import com.zkzy.zyportal.system.api.entity.AreaAdministrationareaB;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface AreaAdministrationareaBMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AREA_ADMINISTRATIONAREA_B
     *
     * @mbggenerated Wed May 10 14:33:53 CST 2017
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AREA_ADMINISTRATIONAREA_B
     *
     * @mbggenerated Wed May 10 14:33:53 CST 2017
     */
    int insert(AreaAdministrationareaB record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AREA_ADMINISTRATIONAREA_B
     *
     * @mbggenerated Wed May 10 14:33:53 CST 2017
     */
    AreaAdministrationareaB selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AREA_ADMINISTRATIONAREA_B
     *
     * @mbggenerated Wed May 10 14:33:53 CST 2017
     */
    List<AreaAdministrationareaB> selectAll(String  city);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AREA_ADMINISTRATIONAREA_B
     *
     * @mbggenerated Wed May 10 14:33:53 CST 2017
     */
    int updateByPrimaryKey(AreaAdministrationareaB record);

    /**
     * 通过区域查询是否建立
     * @param record
     * @return
     */
    List<AreaAdministrationareaB> selectByArea(AreaAdministrationareaB record);

    /**
     * 查询区域不重复
     * @return
     */
    List<AreaAdministrationareaB> selectAreas();
}