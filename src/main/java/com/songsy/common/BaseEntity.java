package com.songsy.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体抽象基类 提取公共属性
 * @author songshuiyang
 * @date 2018/04/14 20:12
 */
@Getter
@Setter
@ToString
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -3873745966284869947L;
    /**
     * id
     */
    private Integer id;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 最后修改人
     */
    private String lastModifiedBy;
    /**
     * 最后修改时间
     */
    private Date lastModifiedDate;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 逻辑删除标志
     */
    private Boolean enable;

}
