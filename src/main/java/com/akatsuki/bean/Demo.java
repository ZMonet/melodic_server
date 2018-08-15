package com.akatsuki.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by yusee on 2018/4/9.
 */
@Entity
@Table(name = "DemoRepository")
public class Demo {

    //这里是一些关于数据库的注解，具体参考网上
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "demoUid",length = 36)
    private String demoUid;
}
