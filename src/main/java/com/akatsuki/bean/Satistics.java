package com.akatsuki.bean;

import com.akatsuki.bean.system.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by think on 2018/4/21.
 */
@Entity
@Table(name = "Satistics")
public class Satistics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String satisticsUid;

    private String userUid;

    private int taskCompletionRate;

}