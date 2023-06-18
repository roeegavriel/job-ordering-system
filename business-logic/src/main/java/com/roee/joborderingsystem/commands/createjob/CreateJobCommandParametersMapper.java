package com.roee.joborderingsystem.commands.createjob;

import com.roee.joborderingsystem.services.customer.CustomerService;
import com.roee.joborderingsystem.services.job.CreateJobParameters;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
@RequiredArgsConstructor
public abstract class CreateJobCommandParametersMapper {

    @Autowired
    @Setter
    protected CustomerService customerService;

    @Mapping(target = "customer", expression = "java(customerService.findById(createJobCommandParameters.customerId()))")
    public abstract CreateJobParameters toCreateJobParameters(CreateJobCommandParameters createJobCommandParameters);
}
