package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.AddressDTO;
import com.ecommerce.ecommerceplatform.entity.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressMapper {
    public static Address toEntity(AddressDTO addressDTO) {
        Address address = new Address();
        address.setId(addressDTO.getId());
        address.setFullName(addressDTO.getFullName());
        address.setLine1(addressDTO.getLine1());
        address.setLine2(addressDTO.getLine2());
        address.setCity(addressDTO.getCity());
        address.setRegion(addressDTO.getRegion());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setCountry(addressDTO.getCountry());
        address.setPhone(addressDTO.getPhone());
        return address;
    }
    public static AddressDTO toDto(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(address.getId());
        addressDTO.setFullName(address.getFullName());
        addressDTO.setLine1(address.getLine1());
        addressDTO.setLine2(address.getLine2());
        addressDTO.setCity(address.getCity());
        addressDTO.setRegion(address.getRegion());
        addressDTO.setPostalCode(address.getPostalCode());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setPhone(address.getPhone());
        return addressDTO;
    }

    public static List<AddressDTO> toDtoList(List<Address> addresses) {
        List<AddressDTO> list = new ArrayList<>();
        for (Address address : addresses) {
            list.add(toDto(address));
        }
        return list;
    }
}
