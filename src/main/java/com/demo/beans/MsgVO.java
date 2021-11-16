package com.demo.beans;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class MsgVO implements Serializable {
    private String name;
    private String text;
    private Date date;
}
