package br.com.house.digital.projetointegrador.service.impl;

import br.com.house.digital.projetointegrador.dto.profile.ApplicantProfileDTO;
import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile;
import br.com.house.digital.projetointegrador.repository.ApplicantProfileRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ApplicantProfileService extends ProfileService<ApplicantProfile> {

    @Autowired
    public ApplicantProfileService(ApplicantProfileRepository applicantProfileRepository,
                                   ModelMapper modelMapper) {
        super(applicantProfileRepository, modelMapper, ApplicantProfile.class);
        super.modelMapper.typeMap(ApplicantProfileDTO.class, ApplicantProfile.class).addMappings(mapper -> {
            mapper.skip(ApplicantProfile::setWorkExperiences);
            mapper.skip(ApplicantProfile::setSkills);
            mapper.skip(ApplicantProfile::setCourses);
        });
    }

    @Transactional
    public ApplicantProfile patch(ApplicantProfileDTO partial, Long id) {
        ApplicantProfile profile = super.findById(id);
        if (partial.getWorkExperiences() != null) {
            replaceList(partial.getWorkExperiences(), profile.getWorkExperiences());
        }
        if (partial.getSkills() != null) {
            replaceList(partial.getSkills(), profile.getSkills());
        }
        if (partial.getCourses() != null) {
            replaceList(partial.getCourses(), profile.getCourses());
        }
        return super.patch(partial, profile);
    }

}