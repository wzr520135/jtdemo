package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @auther wzr
 * @create 2019-10-30 14:08
 * @Description
 * @return
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUITable implements Serializable {

    private  Integer total;
    private List<?> rows;
}
