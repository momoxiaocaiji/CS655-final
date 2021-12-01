package com.password.manager.service.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Database {
    private Map<String, String> data = new HashMap<>();
}
