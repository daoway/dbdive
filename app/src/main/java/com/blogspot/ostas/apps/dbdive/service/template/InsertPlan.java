package com.blogspot.ostas.apps.dbdive.service.template;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InsertPlan {
    private List<InsertPlanItem> insert = new ArrayList<>();
}
