package com.password.manager.service.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Encryption {
    private String encryption;
    private int workNum;
    private boolean useCache;
}
