package br.com.house.digital.projetointegrador.service.impl;

import br.com.house.digital.projetointegrador.dto.profile.UpdateAvatarDTO;
import br.com.house.digital.projetointegrador.model.profile.Profile;
import br.com.house.digital.projetointegrador.repository.BaseRepository;
import org.modelmapper.ModelMapper;

public abstract class ProfileService<T extends Profile> extends BaseServiceImpl<T, Long> {

    public ProfileService(BaseRepository<T, Long> repository, ModelMapper modelMapper, Class<T> genericType) {
        super(repository, modelMapper, genericType);
    }

    public T patchAvatar(UpdateAvatarDTO dto, Long id) {
        T profile = super.findById(id);
        if (dto.getName() != null) profile.setName(dto.getName());
        if (dto.getImgSrc() != null) profile.setImgSrc(dto.getImgSrc());
        if (dto.getTitle() != null) profile.setTitle(dto.getTitle());
        return super.save(profile);
    }
}
