package br.com.house.digital.projetointegrador.service.impl;

import br.com.house.digital.projetointegrador.model.profile.CompanyProfile;
import br.com.house.digital.projetointegrador.repository.CompanyProfileRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyProfileService extends ProfileService<CompanyProfile> {

    @Autowired
    public CompanyProfileService(CompanyProfileRepository repository,
                                 ModelMapper modelMapper) {
        super(repository, modelMapper, CompanyProfile.class);
    }

}
