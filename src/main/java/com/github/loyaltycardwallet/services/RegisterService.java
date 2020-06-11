package com.github.loyaltycardwallet.services;

import com.github.loyaltycardwallet.dto.ManagerRegisterDTO;
import com.github.loyaltycardwallet.dto.NormalUserRegisterDTO;
import com.github.loyaltycardwallet.models.User;

public interface RegisterService {
    User normalUserRegister(NormalUserRegisterDTO normalUserRegisterDTO);

    User managerRegister(ManagerRegisterDTO managerRegisterDTO);
}
