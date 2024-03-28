package com.sample.service.manager.controler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.sample.service.manager.database.entity.Dictionary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestDatabaseInit {
    
    @Test
    public void genJsonData() {
        JSON.config(JSONWriter.Feature.WriteMapNullValue);
        Dictionary dictionary = new Dictionary();
        dictionary.setKey(Dictionary.SESSION_OUT_TIME_KEY);
        dictionary.setValue("30");
        dictionary.setStatus(true);
        String jsonString = JSON.toJSONString(dictionary);
        Assertions.assertNotNull(jsonString);
    }
}
