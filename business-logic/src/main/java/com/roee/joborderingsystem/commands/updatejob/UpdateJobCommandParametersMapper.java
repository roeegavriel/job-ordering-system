package com.roee.joborderingsystem.commands.updatejob;

import com.roee.joborderingsystem.entities.Job;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UpdateJobCommandParametersMapper {

    public abstract Job updateJob(@MappingTarget Job job, UpdateJobCommandParameters updateJobCommandParameters);
}
