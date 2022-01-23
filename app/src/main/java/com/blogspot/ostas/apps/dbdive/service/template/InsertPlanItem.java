package com.blogspot.ostas.apps.dbdive.service.template;

import com.blogspot.ostas.apps.dbdive.model.DbTable;
import lombok.Data;

@Data
public class InsertPlanItem {
    private DbTable dbTable;
    private Integer count;
}
