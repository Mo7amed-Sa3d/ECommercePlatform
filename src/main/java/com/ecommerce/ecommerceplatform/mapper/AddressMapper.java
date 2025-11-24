package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.responsedto.AddressResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressMapper {
    public static Address toEntity(AddressResponseDTO addressResponseDTO) {
        Address address = new Address();
        address.setId(addressResponseDTO.getId());
        address.setFullName(addressResponseDTO.getFullName());
        address.setLine1(addressResponseDTO.getLine1());
        address.setLine2(addressResponseDTO.getLine2());
        address.setCity(addressResponseDTO.getCity());
        address.setRegion(addressResponseDTO.getRegion());
        address.setPostalCode(addressResponseDTO.getPostalCode());
        address.setCountry(addressResponseDTO.getCountry());
        address.setPhone(addressResponseDTO.getPhone());
        return address;
    }
    public static AddressResponseDTO toDto(Address address) {
        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();
        addressResponseDTO.setId(address.getId());
        addressResponseDTO.setFullName(address.getFullName());
        addressResponseDTO.setLine1(address.getLine1());
        addressResponseDTO.setLine2(address.getLine2());
        addressResponseDTO.setCity(address.getCity());
        addressResponseDTO.setRegion(address.getRegion());
        addressResponseDTO.setPostalCode(address.getPostalCode());
        addressResponseDTO.setCountry(address.getCountry());
        addressResponseDTO.setPhone(address.getPhone());
        return addressResponseDTO;
    }

    public static List<AddressResponseDTO> toDtoList(List<Address> addresses) {
        List<AddressResponseDTO> list = new ArrayList<>();
        for (Address address : addresses) {
            list.add(toDto(address));
        }
        return list;
    }
}
