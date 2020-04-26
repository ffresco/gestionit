package com.gestionit.base.service;



import com.gestionit.base.domain.Item;
import com.gestionit.base.repository.ItemRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.springframework.stereotype.Service;


@Service
public class ItemService  {

    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


  
    public Item getById(Long id){
        return itemRepository.findOne(id);
    }

    @Transactional
    public Item saveOrUpdate(Item item){
        return  itemRepository.save(item);
    }

  
    public void delete(Item item){
        itemRepository.delete(item);
    }


    public List<Item> findByName(String name){
        return itemRepository.findByName(name);
    }

    public List<Item> findByNameAndQuantity(String name,Integer quantity){
        return itemRepository.findByNameAndQuantity(name,quantity);
    }



}
