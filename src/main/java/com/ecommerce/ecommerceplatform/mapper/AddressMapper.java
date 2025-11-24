package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.requestdto.AddressRequestDTO;
import com.ecommerce.ecommerceplatform.dto.responsedto.AddressResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressMapper {
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

    public static Address toEntity(AddressRequestDTO addressRequestDTO) {
        Address address = new Address();
        address.setFullName(addressRequestDTO.getFullName());
        address.setLine1(addressRequestDTO.getLine1());
        address.setLine2(addressRequestDTO.getLine2());
        address.setCity(addressRequestDTO.getCity());
        address.setRegion(addressRequestDTO.getRegion());
        address.setPostalCode(addressRequestDTO.getPostalCode());
        address.setCountry(addressRequestDTO.getCountry());
        address.setPhone(addressRequestDTO.getPhone());
        return address;
    }
}
