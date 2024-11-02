package com.bjpowernode.domain.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginData {

    private String jwt;

    private List<String> stringAuthorityList;
}
