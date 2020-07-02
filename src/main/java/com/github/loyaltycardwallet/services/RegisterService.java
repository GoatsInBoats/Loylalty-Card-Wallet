package com.github.loyaltycardwallet.services;

import com.github.loyaltycardwallet.dto.ManagerRegisterAndEditDTO;
import com.github.loyaltycardwallet.dto.NormalUserRegisterAndEditDTO;
import com.github.loyaltycardwallet.models.User;

public interface RegisterService {
    User normalUserRegister(NormalUserRegisterAndEditDTO normalUserRegisterAndEditDTO);

    User managerRegister(ManagerRegisterAndEditDTO managerRegisterAndEditDTO);
}
