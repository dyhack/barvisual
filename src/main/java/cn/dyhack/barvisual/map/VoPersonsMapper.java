package cn.dyhack.barvisual.map;

import java.util.List;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


import cn.dyhack.barvisual.pojo.tables.pojos.Persons;
import cn.dyhack.barvisual.resp.PersonsByAge;
@Mapper
public interface VoPersonsMapper {
    
    
    VoPersonsMapper INSTATNCE = Mappers.getMapper(VoPersonsMapper.class);
    @Mappings({
        @Mapping(source = "id",target="id"),
        @Mapping(source = "name",target="name"),
        @Mapping(source = "areaid",target="areaid"),
        @Mapping(source = "birthday",target="birthday")
        
    })
    PersonsByAge personsToPersonsByage(Persons persons);
    
    List<PersonsByAge> ListPersonToPersonVos(List<Persons> persons);
}
